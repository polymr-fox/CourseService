package com.mrfox.senyast4745.coursesevice.forms;

import com.mrfox.senyast4745.coursesevice.model.UserModel;

public class NotificationForm {

    private UserModel[] userModels;
    private int type;
    private Long id;

    public NotificationForm(UserModel[] userModels, int type, Long id) {
        this.userModels = userModels;
        this.type = type;
        this.id = id;
    }

    public UserModel[] getUserModels() {
        return userModels;
    }

    public void setUserModels(UserModel[] userModels) {
        this.userModels = userModels;
    }

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }
}
