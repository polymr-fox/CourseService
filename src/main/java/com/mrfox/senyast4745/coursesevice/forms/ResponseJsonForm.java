package com.mrfox.senyast4745.coursesevice.forms;

import com.mrfox.senyast4745.coursesevice.model.CourseModel;

public class ResponseJsonForm {

    private Iterable<CourseModel> courses;

    public ResponseJsonForm(Iterable<CourseModel> courses) {
        this.courses = courses;
    }

    public Iterable<CourseModel> getCourses() {
        return courses;
    }

    public void setCourses(Iterable<CourseModel> courses) {
        this.courses = courses;
    }
}
