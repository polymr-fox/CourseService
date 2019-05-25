package com.mrfox.senyast4745.coursesevice.repository;

import com.mrfox.senyast4745.coursesevice.model.TagModel;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TagsRepository extends CrudRepository<TagModel, Long> {
    Iterable<TagModel> findAllByTagName(String tagName);
}
