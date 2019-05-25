package com.mrfox.senyast4745.coursesevice.forms;


import com.mrfox.senyast4745.coursesevice.model.PostModel;

public class ResponseJsonPostForm {
    private Iterable<PostModel> posts;

    public ResponseJsonPostForm(Iterable<PostModel> posts) {
        this.posts = posts;
    }

    public Iterable<PostModel> getEvents() {
        return posts;
    }

    public void setEvents(Iterable<PostModel> posts) {
        this.posts = posts;
    }
}
