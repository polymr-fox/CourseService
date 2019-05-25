package com.mrfox.senyast4745.coursesevice.forms;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

public class RatingForm {
    private int rating;

    @JsonCreator
    public RatingForm(@JsonProperty("rating")int rating) {
        this.rating = rating;
    }

    public int getRating() {
        return rating;
    }

    public void setRating(int rating) {
        this.rating = rating;
    }
}
