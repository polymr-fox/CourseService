package com.mrfox.senyast4745.coursesevice.forms;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

public class UpdateAllForm {

    private Long id;

    private String name;
    private String description;
    private Long[] adminIds;
    private Long[] userIds;
    private String[] tags;
    private Boolean isOpen;

    @JsonCreator
    public UpdateAllForm(@JsonProperty("id")Long id ,@JsonProperty("name")String name,
                         @JsonProperty("description")String description, @JsonProperty("adminIds")Long[] adminsId,
                         @JsonProperty("usersId")Long[] usersId, @JsonProperty("tags")String[] tags,
                         @JsonProperty("isOpen")Boolean isOpen) {
        this.id = id;

        this.name = name;
        this.description = description;
        this.adminIds = adminsId;
        this.userIds = usersId;
        this.tags = tags;
        this.isOpen = isOpen;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
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

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Long[] getAdminsId() {
        return adminIds;
    }

    public void setAdminsId(Long[] adminsId) {
        this.adminIds = adminsId;
    }

    public Long[] getUsersId() {
        return userIds;
    }

    public void setUsersId(Long[] usersId) {
        this.userIds = usersId;
    }

    public String[] getTags() {
        return tags;
    }

    public void setTags(String[] tags) {
        this.tags = tags;
    }

    public Boolean getOpen() {
        return isOpen;
    }

    public void setOpen(Boolean open) {
        isOpen = open;
    }
}
