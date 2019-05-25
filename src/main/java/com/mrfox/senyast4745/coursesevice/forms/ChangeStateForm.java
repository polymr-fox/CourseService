package com.mrfox.senyast4745.coursesevice.forms;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

public class ChangeStateForm {
    private Long id;
    private Long creatorId;
    private Boolean isOpen;

    @JsonCreator
    public ChangeStateForm(@JsonProperty("id") Long id,@JsonProperty("creatorId") Long creatorId ,@JsonProperty("isOpen") Boolean isOpen) {
        this.id = id;
        this.creatorId = creatorId;
        this.isOpen = isOpen;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Boolean getOpen() {
        return isOpen;
    }

    public void setOpen(Boolean open) {
        isOpen = open;
    }

    public Long getCreatorId() {
        return creatorId;
    }

    public void setCreatorId(Long creatorId) {
        this.creatorId = creatorId;
    }
}
