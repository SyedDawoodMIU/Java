package com.waa.assignments.entity.business;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;

@Entity
public class PostV2 {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    private String title;


    private String content;

    private String author;

    public PostV2() {

    }

    public int getId() {
        return id;
    }

    public String getTitle() {
        return title;
    }


    public String getCategory() {
        return content;
    }

    public String getAuthor() {
        return author;
    }

    public PostV2(int id, String title, String content, String author) {
        this.id = id;
        this.title = title;
        this.content = content;
        this.author = author;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public void setCategory(String content) {
        this.content = content;
    }

    public void setAuthor(String author) {
        this.author = author;
    }


}
