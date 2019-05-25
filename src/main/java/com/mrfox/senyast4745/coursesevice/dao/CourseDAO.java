package com.mrfox.senyast4745.coursesevice.dao;

import com.mrfox.senyast4745.coursesevice.forms.NotificationForm;
import com.mrfox.senyast4745.coursesevice.model.*;
import com.mrfox.senyast4745.coursesevice.repository.CoursesRepository;
import com.mrfox.senyast4745.coursesevice.repository.PostRepository;
import com.mrfox.senyast4745.coursesevice.repository.TagsRepository;
import com.mrfox.senyast4745.coursesevice.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
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

@Component
public class CourseDAO {
    private static final int TYPE = 0;
    private final TagsRepository tagsRepository;
    private final UserRepository userRepository;
    private final CoursesRepository coursesRepository;
    private final PostRepository postRepository;

    @Autowired
    public CourseDAO(TagsRepository tagsRepository, UserRepository userRepository,
                     CoursesRepository coursesRepository, PostRepository postRepository) {
        this.tagsRepository = tagsRepository;
        this.userRepository = userRepository;
        this.coursesRepository = coursesRepository;
        this.postRepository = postRepository;
    }

    public CourseModel createCourse(Long creatorId, String courseName, String courseDescription, Long[] adminsId
            , Long[] usersId, String[] tags, boolean isOpen) {
        if (tags.length == 0) {
            throw new IllegalArgumentException("No tags found");
        }
        for (String tmp : tags) {
            Iterable<TagModel> tmpTag = tagsRepository.findAllByTagName(tmp);
            if (!tmpTag.iterator().hasNext()) {
                tagsRepository.save(new TagModel(tmp));
            }
        }
        return coursesRepository.save(new CourseModel(creatorId, courseName, courseDescription, new ArrayList<>(Arrays.asList(adminsId)),
                new ArrayList<>(Arrays.asList(usersId)), tags, isOpen, new Date(), usersId.length));
    }

    public CourseModel updateCourse(Long id, Long userId ,String name, String description, Long[] adminsId
            , Long[] usersId, String[] tags, Boolean isOpen) throws IllegalAccessException {
        checkAccess(id, userId);
        CourseModel courseModels = coursesRepository.findById(id).orElseThrow(() ->
                new IllegalArgumentException("Course with id " + id + " not exist."));
        if (name != null && !name.isEmpty()) {
            courseModels.setCourseName(name);
        }
        if (description != null && !description.isEmpty()) {
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
                    if (m.getRole()== Role.ADMIN) {
                        admins.add(m.getUserId());
                    }
                });
            }
            courseModels.setAdminIds(admins);
        }

        if (tags != null && tags.length > 0) {
            courseModels.setTags(tags);
        }

        if (isOpen != null) {
            courseModels.setOpen(isOpen);
        }

        return coursesRepository.save(courseModels);
    }

    public CourseModel subscribeUser(Long id, Long userId) {
        CourseModel courseModel = coursesRepository.findById(id).orElseThrow(() ->
                new IllegalArgumentException("Course with id " + id + " not exist."));
        courseModel.getUserIds().add(userId);
        int tmp = courseModel.getSubsCount();
        courseModel.setSubsCount(++tmp);
        return coursesRepository.save(courseModel);
    }

    public CourseModel unsubscribeUser(Long id, Long userId) {
        CourseModel courseModel = coursesRepository.findById(id).orElseThrow(() ->
                new IllegalArgumentException("Course with id " + id + " not exist."));
        courseModel.getUserIds().remove(userId);
        int tmp = courseModel.getSubsCount();
        courseModel.setSubsCount(--tmp);
        return coursesRepository.save(courseModel);
    }

    public CourseModel changeState(Long id, Long userId, Boolean isOpen) throws IllegalAccessException {
        return updateCourse(id, userId, null, null, null,null, null, isOpen);

    }

    public void delete(Long id, Long userId) throws IllegalAccessException {
        checkAccess(id, userId);
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
        if (tmp == null || (!tmp.getCreatorId().equals(creatorId) && tmpUser.getRole()!=(Role.MODERATOR))) {
            throw new IllegalAccessException();
        }

    }

    public Iterable<PostModel> getPosts(Long id){
        Iterable<PostModel> postModels = postRepository.findAllByTypeAndParentIdOrderByDate(TYPE, id);
        if (!postModels.iterator().hasNext()) {
            throw new IllegalArgumentException("Posts with parent id " + id + " not exist.");
        }
        return postModels;
    }

    public void sendNotification(UserModel[] userModels, Long id, Role role, String token) {

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
}
