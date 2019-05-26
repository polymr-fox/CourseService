package com.mrfox.senyast4745.coursesevice.model;

import com.mrfox.senyast4745.coursesevice.converters.ListLongConverter;

import javax.persistence.*;
import java.util.Date;

@Entity
@Table(name = "posts")
public class PostModel {

    @Id
    @GeneratedValue
    @Column(name = "post_id", nullable = false, unique = true)
    private Long id;

    @Column(name = "post_name", nullable = false)
    private String articleName;

    @Column(name = "post_text", nullable = false)
    private String articleText;

    @Column(name = "parent_id")
    private Long parentId;

    @Column(name = "user_id")
    private Long userId;

    @Column(name = "type", nullable = false)
    private int type;


    @Column(name = "tags")
    @Convert(converter = ListLongConverter.class)
    private String[] tags;

    @Column(name = "creation_date", nullable = false)
    @Temporal(TemporalType.DATE)
    private Date date;


    public PostModel() {
        super();
    }

    public PostModel(String articleName, String articleText, Long parentId,Long userId ,int type, String[] tags, Date date) {

        this.articleName = articleName;
        this.articleText = articleText;
        this.parentId = parentId;
        this.userId = userId;
        this.type = type;
        this.tags = tags;

        this.date = date;
    }


    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }



    public String getArticleName() {
        return articleName;
    }

    public void setArticleName(String articleName) {
        this.articleName = articleName;
    }

    public String getArticleText() {
        return articleText;
    }

    public void setArticleText(String articleText) {
        this.articleText = articleText;
    }

    public Long getParentId() {
        return parentId;
    }

    public void setParentId(Long parentId) {
        this.parentId = parentId;
    }

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }

    public String[] getTags() {
        return tags;
    }

    public void setTags(String[] tags) {
        this.tags = tags;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }
}
