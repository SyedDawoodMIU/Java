package com.waa.assignments.services;

import com.waa.assignments.entity.business.Post;
import com.waa.assignments.entity.dto.PostDto;

import java.util.List;


public interface PostService extends BaseService<PostDto> {

    public List<PostDto> findAll();



    void update(int id, PostDto p);


    List<PostDto> findByAuthor(String author);
}