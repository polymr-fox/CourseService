package com.mrfox.senyast4745.coursesevice.forms;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

public class CreateForm {



    private String courseName;
    private String courseDescription;
    private Long[] adminIds;
    private Long[] userIds;
    private String[] tags;
    private boolean isOpen;

    @JsonCreator
    public CreateForm( @JsonProperty("courseName") String courseName,
                      @JsonProperty("courseDescription") String courseDescription,
                      @JsonProperty("adminIds") Long[] adminIds, @JsonProperty("userIds") Long[] userIds,
                      @JsonProperty("tags") String[] tags, @JsonProperty("isOpen") boolean isOpen) {

        this.courseName = courseName;
        this.courseDescription = courseDescription;
        this.adminIds = adminIds;
        this.userIds = userIds;
        this.tags = tags;
        this.isOpen = isOpen;
    }

    public String getCourseName() {
        return courseName;
    }

    public void setCourseName(String courseName) {
        this.courseName = courseName;
    }

    public String getCourseDescription() {
        return courseDescription;
    }

    public void setCourseDescription(String courseDescription) {
        this.courseDescription = courseDescription;
    }

    public Long[] getAdminIds() {
        return adminIds;
    }

    public void setAdminIds(Long[] adminIds) {
        this.adminIds = adminIds;
    }

    public Long[] getUserIds() {
        return userIds;
    }

    public void setUserIds(Long[] userIds) {
        this.userIds = userIds;
    }

    public String[] getTags() {
        return tags;
    }

    public void setTags(String[] tags) {
        this.tags = tags;
    }

    public boolean isOpen() {
        return isOpen;
    }

    public void setOpen(boolean open) {
        isOpen = open;
    }
}
