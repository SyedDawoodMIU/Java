package com.waa.assignments.controllers;

import com.waa.assignments.entity.dto.PostDto;
import com.waa.assignments.entity.dto.PostV2Dto;
import com.waa.assignments.services.PostService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

@RestController
@CrossOrigin(origins = {"http://localhost:3000"})
public class PostController {

    private final PostService postService;
    private final ModelMapper modelMapper;

    @Autowired
    public PostController(PostService postService, ModelMapper modelMapper) {
        this.postService = postService;
        this.modelMapper = modelMapper;
    }

    @ResponseStatus(HttpStatus.OK)
    @GetMapping("/v1/posts")
    public List<PostDto> getAll() {
        return postService.findAll();
    }

    @ResponseStatus(HttpStatus.CREATED)
    @PostMapping("/v1/posts")
    public void save(@RequestBody PostDto p) {
        postService.save(p);
    }

    @GetMapping("/v1/posts/{id}")
    public ResponseEntity<PostDto> getById(@PathVariable int id) {
        var post = postService.getById(id);
        return ResponseEntity.ok(post);
    }

    @ResponseStatus(HttpStatus.NO_CONTENT)
    @DeleteMapping("/v1/posts/{id}")
    public void delete(@PathVariable int id) {
        postService.delete(id);
    }

    @PutMapping("/v1/posts/{id}")
    public void update(@PathVariable("id") int postId, @RequestBody PostDto p) {
        postService.update(postId, p);
    }

    @GetMapping("/v2/posts")
    public List<PostV2Dto> getAllV2(@RequestParam(value = "filter" ,required = false) String author) {
        List<PostDto> posts = author == null || Objects.equals(author, "") ?  postService.findAll() : postService.findByAuthor(author);
        return posts.stream()
                .map(post -> modelMapper.map(post, PostV2Dto.class))
                .collect(Collectors.toList());
    }

    @ResponseStatus(HttpStatus.CREATED)

    @PostMapping("/v2/posts")
    public void saveV2(@RequestBody PostV2Dto p) {
        postService.save(modelMapper.map(p, PostDto.class));
    }

    @GetMapping("/v2/posts/{id}")
    public ResponseEntity<PostV2Dto> getByIdV2(@PathVariable int id) {
        var post = postService.getById(id);
        return ResponseEntity.ok(modelMapper.map(post, PostV2Dto.class));
    }

    @PutMapping("/v2/posts/{id}")
    public void updateV2(@PathVariable int id, @RequestBody PostV2Dto p) {
        postService.update(id, modelMapper.map(p, PostDto.class));
    }

    @DeleteMapping("/v2/posts/{id}")
    public void deleteV2(@PathVariable int id) {
        postService.delete(id);
    }


}
