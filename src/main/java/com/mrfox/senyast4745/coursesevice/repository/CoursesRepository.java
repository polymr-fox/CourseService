package com.mrfox.senyast4745.coursesevice.repository;

import com.mrfox.senyast4745.coursesevice.model.CourseModel;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CoursesRepository extends CrudRepository<CourseModel, Long> {
    Iterable<CourseModel> findAllBySubsCount(int subsCount);
    Iterable<CourseModel> findAllByCreatorId(Long creatorId);
}
