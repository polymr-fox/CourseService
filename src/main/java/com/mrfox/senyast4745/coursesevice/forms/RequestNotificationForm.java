package com.mrfox.senyast4745.coursesevice.forms;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.mrfox.senyast4745.coursesevice.model.Role;
import com.mrfox.senyast4745.coursesevice.model.UserModel;


public class RequestNotificationForm {
    private UserModel[] userModels;
    private Long id;
    private Long creatorId;
    private Role role;

    @JsonCreator
    public RequestNotificationForm(@JsonProperty("userModels") UserModel[] userModels, @JsonProperty("id") Long id,
                                   @JsonProperty("creatorId") Long creatorId, @JsonProperty("role") Role role) {
        this.userModels = userModels;
        this.id = id;
        this.creatorId= creatorId;
        this.role = role;
    }

    public UserModel[] getUserModels() {
        return userModels;
    }

    public void setUserModels(UserModel[] userModels) {
        this.userModels = userModels;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getCreatorId() {
        return creatorId;
    }

    public void setCreatorId(Long creatorId) {
        this.creatorId = creatorId;
    }

    public Role getRole() {
        return role;
    }

    public void setRole(Role role) {
        this.role = role;
    }
}
