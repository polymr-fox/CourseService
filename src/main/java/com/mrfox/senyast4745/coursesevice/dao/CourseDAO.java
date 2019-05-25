package com.mrfox.senyast4745.coursesevice.dao;

import com.mrfox.senyast4745.coursesevice.model.CourseModel;
import com.mrfox.senyast4745.coursesevice.model.TagModel;
import com.mrfox.senyast4745.coursesevice.model.UserModel;
import com.mrfox.senyast4745.coursesevice.repository.CoursesRepository;
import com.mrfox.senyast4745.coursesevice.repository.TagsRepository;
import com.mrfox.senyast4745.coursesevice.repository.UserRepository;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;

@Component
public class CourseDAO {
    private final TagsRepository tagsRepository;
    private final UserRepository userRepository;
    private final CoursesRepository coursesRepository;

    public CourseDAO(TagsRepository tagsRepository, UserRepository userRepository, CoursesRepository coursesRepository) {
        this.tagsRepository = tagsRepository;
        this.userRepository = userRepository;
        this.coursesRepository = coursesRepository;
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

    public CourseModel updateCourse(Long id, String name, String description, Long[] adminsId
            , Long[] usersId, String[] tags, Boolean isOpen) {
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
                    if (m.getRole().equals("ADMIN")) {
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

    public CourseModel subscibeUser(Long id, Long userId) {
        CourseModel courseModel = coursesRepository.findById(id).orElseThrow(() ->
                new IllegalArgumentException("Course with id " + id + " not exist."));
        courseModel.getUserIds().add(userId);
        int tmp = courseModel.getSubsCount();
        courseModel.setSubsCount(++tmp);
        return coursesRepository.save(courseModel);
    }

    public CourseModel unsubscibeUser(Long id, Long userId) {
        CourseModel courseModel = coursesRepository.findById(id).orElseThrow(() ->
                new IllegalArgumentException("Course with id " + id + " not exist."));
        courseModel.getUserIds().remove(userId);
        int tmp = courseModel.getSubsCount();
        courseModel.setSubsCount(--tmp);
        return coursesRepository.save(courseModel);
    }

    public CourseModel changeState(Long id, Boolean isOpen) {
        return updateCourse(id, null, null, null, null, null, isOpen);
    }

    public void delete(Long id) {
        CourseModel courseModel = coursesRepository.findById(id).orElseThrow(() ->
                new IllegalArgumentException("Course with id " + id + " not exist."));
        coursesRepository.delete(courseModel);
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
        ArrayList <CourseModel> courseModels = new ArrayList<>();
        for (UserModel u : userModels) {
            findAllByCreatorId(u.getUserId()).forEach(courseModels::add);
        }
        if(courseModels.isEmpty()){
            throw new IllegalArgumentException("Users with full " + fullName + " has not created an article yet.");
        }
        return courseModels;
    }
}
