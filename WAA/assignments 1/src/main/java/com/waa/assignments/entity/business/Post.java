package com.waa.assignments.entity.business;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;

public class Post {

    private int id;
    private String title;
    private String content;
    private String author;

    public Post() {

    }

    public int getId() {
        return id;
    }

    public String getTitle() {
        return title;
    }


    public String getContent() {
        return content;
    }

    public String getAuthor() {
        return author;
    }

    public Post(int id, String title, String content, String author) {
        this.id = id;
        this.title = title;
        this.content = content;
        this.author = author;
    }

    public void  setId(int id) {
        this.id = id;
    }
    public void setTitle(String title) {
        this.title = title;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public void setAuthor(String author) {
        this.author = author;
    }


}
