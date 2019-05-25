package com.mrfox.senyast4745.coursesevice.forms;

public class UpdateAllForm {

    private Long id;
    private String name;
    private String description;
    private Long[] adminsId;
    private Long[] usersId;
    private String[] tags;
    private Boolean isOpen;

    public UpdateAllForm(Long id, String name, String description, Long[] adminsId, Long[] usersId, String[] tags, Boolean isOpen) {
        this.id = id;
        this.name = name;
        this.description = description;
        this.adminsId = adminsId;
        this.usersId = usersId;
        this.tags = tags;
        this.isOpen = isOpen;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
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
        return adminsId;
    }

    public void setAdminsId(Long[] adminsId) {
        this.adminsId = adminsId;
    }

    public Long[] getUsersId() {
        return usersId;
    }

    public void setUsersId(Long[] usersId) {
        this.usersId = usersId;
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
