package com.mrfox.senyast4745.coursesevice.model;

import javax.persistence.*;

@Entity
@Table(name = "tags")
public class TagModel {

    @Id
    @GeneratedValue
    @Column(name = "tag_id", unique = true, nullable = false)
    private Long id;

    @Column(name = "tag_name", unique = true, nullable = false)
    private String tagName;

    public TagModel() {
        super();
    }

    public TagModel(String tagName) {
        this.tagName = tagName;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getTagName() {
        return tagName;
    }

    public void setTagName(String tagName) {
        this.tagName = tagName;
    }
}
