package com.mrfox.senyast4745.coursesevice.forms;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

public class CreatePostForm {
    private String postName;
    private String postDescription;
    private Long parentId;
    private String[] tags;

    @JsonCreator
    public CreatePostForm(@JsonProperty(value = "postName") String postName,
                          @JsonProperty(value = "postDescription") String postDescription,
                          @JsonProperty(value = "parentId") Long parentId,
                          @JsonProperty(value = "tags") String[] tags) {
        this.postName = postName;
        this.postDescription = postDescription;
        this.parentId = parentId;
        this.tags = tags;
    }

    public String getPostName() {
        return postName;
    }

    public void setPostName(String postName) {
        this.postName = postName;
    }

    public String getPostDescription() {
        return postDescription;
    }

    public void setPostDescription(String postDescription) {
        this.postDescription = postDescription;
    }

    public Long getParentId() {
        return parentId;
    }

    public void setParentId(Long parentId) {
        this.parentId = parentId;
    }

    public String[] getTags() {
        return tags;
    }

    public void setTags(String[] tags) {
        this.tags = tags;
    }
}
