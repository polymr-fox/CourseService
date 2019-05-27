package com.mrfox.senyast4745.coursesevice.dao;

import com.mrfox.senyast4745.coursesevice.forms.NotificationForm;
import com.mrfox.senyast4745.coursesevice.model.*;
import com.mrfox.senyast4745.coursesevice.repository.CoursesRepository;
import com.mrfox.senyast4745.coursesevice.repository.PostRepository;
import com.mrfox.senyast4745.coursesevice.repository.TagsRepository;
import com.mrfox.senyast4745.coursesevice.repository.UserRepository;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jws;
import io.jsonwebtoken.Jwts;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.client.HttpServerErrorException;
import org.springframework.web.client.RestTemplate;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.regex.Pattern;

@Component
public class CourseDAO {
    private static final int TYPE = 0;
    private final TagsRepository tagsRepository;
    private final UserRepository userRepository;
    private final CoursesRepository coursesRepository;
    private final PostRepository postRepository;

    @Value("${jwt.secretKey:hello}")
    private String secretKey;

    @Autowired
    public CourseDAO(TagsRepository tagsRepository, UserRepository userRepository,
                     CoursesRepository coursesRepository, PostRepository postRepository) {
        this.tagsRepository = tagsRepository;
        this.userRepository = userRepository;
        this.coursesRepository = coursesRepository;
        this.postRepository = postRepository;
    }

    public CourseModel createCourse(String token, String courseName, String courseDescription, Long[] adminsId
            , Long[] usersId, String[] tags, boolean isOpen) {
        ArrayList<String> tmp = new ArrayList<>(Arrays.asList(tags));
        tmp.add(courseName);
        tmp.add(courseName);
        checkSymbols(tmp);
        if (tags.length == 0) {
            throw new IllegalArgumentException("No tags found");
        }
        checkingTags(tags);
        Long creatorId = getUserIdFromToken(token);
        return coursesRepository.save(new CourseModel(creatorId, courseName, courseDescription, new ArrayList<>(Arrays.asList(adminsId)),
                new ArrayList<>(Arrays.asList(usersId)), tags, isOpen, new Date(), usersId.length));
    }

    public CourseModel updateCourse(String token ,Long id, String name, String description, Long[] adminsId
            , Long[] usersId, String[] tags, Boolean isOpen) throws IllegalAccessException {
        checkAccess(id, getUserIdFromToken(token));
        ArrayList<String> tmp = new ArrayList<>();
        CourseModel courseModels = coursesRepository.findById(id).orElseThrow(() ->
                new IllegalArgumentException("Course with id " + id + " not exist."));
        if (name != null && !name.isEmpty()) {
            tmp.add(name);
            checkSymbols(tmp);
            tmp.clear();
            courseModels.setCourseName(name);
        }
        if (description != null && !description.isEmpty()) {
            tmp.add(description);
            checkSymbols(tmp);
            tmp.clear();
            courseModels.setCourseDescription(description);
        }
        if (usersId != null && usersId.length > 0) {
            courseModels.setUserIds(new ArrayList<>(Arrays.asList(usersId)));
            courseModels.setSubsCount(usersId.length);
        }

        if (adminsId != null && adminsId.length > 0) {
            ArrayList<Long> admins = new ArrayList<>();
            for (Long tmpId : adminsId) {
                userRepository.findById(tmpId).ifPresent(m -> {
                    if (m.getRole() == Role.ADMIN) {
                        admins.add(m.getUserId());
                    }
                });
            }
            courseModels.setAdminIds(admins);
        }

        if (tags != null && tags.length > 0) {
            tmp.addAll(Arrays.asList(tags));
            checkSymbols(tmp);
            tmp.clear();
            checkingTags(tags);
            courseModels.setTags(tags);
        }

        if (isOpen != null) {
            courseModels.setOpen(isOpen);
        }

        return coursesRepository.save(courseModels);
    }

    private void checkingTags(String[] tags) {
        Iterable<TagModel> tmpTag = tagsRepository.findAll();
        for (String s : tags) {
            AtomicBoolean find = new AtomicBoolean(false);
            tmpTag.forEach((t) -> {
                if(t.getTagName().equals(s)){
                    find.set(true);
                }
            });
            if (!find.get()) {
                tagsRepository.save(new TagModel(s));
            }
        }
    }

    public CourseModel subscribeUser(String token ,Long id) {
        Long userId = getUserIdFromToken(token);
        CourseModel courseModel = coursesRepository.findById(id).orElseThrow(() ->
                new IllegalArgumentException("Course with id " + id + " not exist."));
        if (!courseModel.getUserIds().contains(userId) && !userId.equals(courseModel.getCreatorId())) {
            courseModel.getUserIds().add(userId);
            int tmp = courseModel.getSubsCount();
            courseModel.setSubsCount(++tmp);
            return coursesRepository.save(courseModel);
        }
        throw new IllegalStateException("User with id " + userId + " can not subscribe.");
    }

    public CourseModel unsubscribeUser(String token ,Long id) {
        Long userId = getUserIdFromToken(token);
        CourseModel courseModel = coursesRepository.findById(id).orElseThrow(() ->
                new IllegalArgumentException("Course with id " + id + " not exist."));

        if (courseModel.getUserIds().remove(userId)) {
            int tmp = courseModel.getSubsCount();
            courseModel.setSubsCount(--tmp);
            return coursesRepository.save(courseModel);
        }
        throw new IllegalStateException("User with id " + userId + " can not subscribe.");
    }

    public CourseModel changeState(String token ,Long id, Boolean isOpen) throws IllegalAccessException {
        return updateCourse(token ,id, null, null, null, null, null, isOpen);

    }

    public void delete(String token ,Long id) throws IllegalAccessException {
        checkAccess(id, getUserIdFromToken(token));
        coursesRepository.deleteById(id);
    }

    public Iterable<CourseModel> findAllByRating(int count) {
        Iterable<CourseModel> courseModels = coursesRepository.findAllBySubsCount(count);
        if (!courseModels.iterator().hasNext()) {
            throw new IllegalArgumentException("Courses with rating " + count + " not exist.");
        }
        return courseModels;
    }

    public Iterable<CourseModel> findAll() {
        Iterable<CourseModel> courseModels = coursesRepository.findAll();
        if (!courseModels.iterator().hasNext()) {
            throw new IllegalArgumentException("Now courses not exist.");
        }
        return courseModels;
    }

    private Iterable<CourseModel> findAllByCreatorId(Long creatorId) {
        Iterable<CourseModel> courseModels = coursesRepository.findAllByCreatorId(creatorId);
        if (!courseModels.iterator().hasNext()) {
            throw new IllegalArgumentException("User with id " + creatorId + " has not created an article yet.");
        }
        return courseModels;
    }

    public Iterable<CourseModel> findAllByCreatorFullName(String fullName) {
        Iterable<UserModel> userModels = userRepository.findByFullName(fullName);
        ArrayList<CourseModel> courseModels = new ArrayList<>();
        for (UserModel u : userModels) {
            findAllByCreatorId(u.getUserId()).forEach(courseModels::add);
        }
        if (courseModels.isEmpty()) {
            throw new IllegalArgumentException("Users with full " + fullName + " has not created an article yet.");
        }
        return courseModels;
    }

    private void checkAccess(Long id, Long creatorId) throws IllegalAccessException {
        CourseModel tmp = coursesRepository.findById(id).orElse(null);
        UserModel tmpUser = userRepository.findById(id).orElseThrow(() -> new IllegalArgumentException("Incorrect user with user id " + id));
        if (tmp == null || (!tmp.getCreatorId().equals(creatorId) && tmpUser.getRole() != (Role.MODERATOR))) {
            throw new IllegalAccessException();
        }

    }

    public Iterable<PostModel> getPosts(Long id) {
        Iterable<PostModel> postModels = postRepository.findAllByTypeAndParentIdOrderByDate(TYPE, id);
        if (!postModels.iterator().hasNext()) {
            throw new IllegalArgumentException("Posts with parent id " + id + " not exist.");
        }
        return postModels;
    }

    public void sendNotification(UserModel[] userModels, Long id, Long creatorId, Role role, String token) throws IllegalAccessException {
        checkAccess(id, creatorId);
        RestTemplate restTemplate = new RestTemplate();

        final String url = "127.0.0.1:9996/" + "notification" + role.name();

        HttpHeaders headers = new HttpHeaders();
        headers.set("Authorization", token);

        NotificationForm form = new NotificationForm(userModels, TYPE, id);

        HttpEntity<NotificationForm> request = new HttpEntity<>(form, headers);
        ResponseEntity<Void> response = restTemplate.postForEntity(url, request, Void.class);

        if (response.getStatusCode() != HttpStatus.OK) {
            throw new HttpServerErrorException(response.getStatusCode());
        }


    }

    public PostModel createPost(String token, String postName, String postDescription, Long parentId, String[] tags) throws IllegalAccessException {
        ArrayList<String> tmp = new ArrayList<>(Arrays.asList(tags));
        tmp.add(postName);
        tmp.add(postDescription);
        checkSymbols(tmp);
        Long userId = getUserIdFromToken(token);
        CourseModel eventModel = coursesRepository.findById(parentId).orElseThrow(() -> new IllegalArgumentException("Can not find event with id " + parentId));
        if (eventModel.getCreatorId().equals(userId)) {
            PostModel postModel = new PostModel(postName, postDescription, parentId, userId, TYPE, tags, new Date());
            return postRepository.save(postModel);
        }
        throw new IllegalAccessException("User with id " + userId + " have no permission to create post.");

    }

    private Long getUserIdFromToken(String token) {
        Jws<Claims> claims = parseToken(token);
        return (Long) claims.getBody().get("userId");
    }

    private Jws<Claims> parseToken(String token) {
        return Jwts.parser().setSigningKey(secretKey).parseClaimsJws(token);

    }

    private void checkSymbols(ArrayList<String> text){
        String regex = "[};]";

        for (String s: text
        ) {
            if(Pattern.matches(regex, s)){
                throw new IllegalArgumentException("incorrect symbols in " + s);
            }
        }

    }
}
