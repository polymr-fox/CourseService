package com.mrfox.senyast4745.coursesevice.dao;

import com.mrfox.senyast4745.coursesevice.model.CourseModel;
import com.mrfox.senyast4745.coursesevice.model.TagModel;
import com.mrfox.senyast4745.coursesevice.repository.CoursesRepository;
import com.mrfox.senyast4745.coursesevice.repository.TagsRepository;
import com.mrfox.senyast4745.coursesevice.repository.UserRepository;
import org.springframework.stereotype.Component;

import java.awt.peer.CanvasPeer;
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

    public CourseModel createEvent(Long creatorId, String articleName, String articleDescription, Long[] adminsId
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
        return coursesRepository.save(new CourseModel(creatorId, articleName, articleDescription, new ArrayList<> (Arrays.asList( adminsId)),
                new ArrayList<>( Arrays.asList(usersId)), tags, isOpen, new Date(), usersId.length));
    }

    public CourseModel updateEvent(Long id, String name, String description, Long[] adminsId
            , Long[] usersId, String[] tags, Boolean isOpen){
        CourseModel courseModels =  coursesRepository.findById(id).orElseThrow(() ->
                new IllegalArgumentException("Event with id " + id + " not exist."));
        if(name != null && !name.isEmpty()){
            courseModels.setEventName(name);
        }
        if(description != null && !description.isEmpty()){
            courseModels.setEventDescription(description);
        }
        if(usersId != null && usersId.length> 0){
            courseModels.setUsersId(new ArrayList<>(Arrays.asList(usersId)));
        }

        if(adminsId != null && adminsId.length> 0){
            courseModels.setAdminsId(new ArrayList<>(Arrays.asList(adminsId)));
        }

        if(tags != null && tags.length > 0){
            courseModels.setTags(tags);
        }

        if(isOpen != null){
            courseModels.setOpen(isOpen);
        }

        return coursesRepository.save(courseModels);
    }

    public CourseModel subscibeUser(Long id , Long userId){
        CourseModel courseModel = coursesRepository.findById(id).orElseThrow(() ->
                new IllegalArgumentException("Event with id " + id + " not exist."));
        courseModel.getUsersId().add(userId);
        return coursesRepository.save(courseModel);
    }

    public CourseModel unsubscibeUser(Long id , Long userId){
        CourseModel courseModel = coursesRepository.findById(id).orElseThrow(() ->
                new IllegalArgumentException("Event with id " + id + " not exist."));
        courseModel.getUsersId().remove(userId);
        return coursesRepository.save(courseModel);
    }

    public CourseModel changeState(Long id, Boolean isOpen){
        return updateEvent(id, null, null, null, null, null, isOpen);
    }

    public void delete(Long id){
        CourseModel courseModel = coursesRepository.findById(id).orElseThrow(() ->
                new IllegalArgumentException("Event with id " + id + " not exist."));
        coursesRepository.delete(courseModel);
    }

    public ArrayList<CourseModel> findAllByRating
}
