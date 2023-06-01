package com.waa.assignments.services.implementation;

import com.waa.assignments.entity.business.Post;
import com.waa.assignments.entity.dto.PostDto;
import com.waa.assignments.helper.ListMapper;
import com.waa.assignments.repo.PostRepository;
import com.waa.assignments.services.PostService;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;


@Service
@RequiredArgsConstructor
public class PostServiceImpl implements PostService {

    @Autowired
    private PostRepository postRepository;

    @Autowired
    ModelMapper modelMapper;

    @Autowired
    ListMapper listMapper;

    @Override
    public List<PostDto> findAll() {
        var posts = postRepository.findAll();
        return listMapper.mapList(posts, PostDto.class);
    }

    @Override
    public PostDto getById(int id) {

        return modelMapper.map(postRepository.getById(id), PostDto.class);
    }

    @Override
    public void delete(PostDto postDto) {

        postRepository.delete(modelMapper.map(postDto, Post.class));

    }

    @Override
    public int update(PostDto postDto) {

        return postRepository.update(modelMapper.map(postDto, Post.class));

    }


    @Override
    public void save(PostDto p) {

            postRepository.save(modelMapper.map(p, Post.class));

    }

    @Override
    public void delete(int id) {

        postRepository.delete(id);

    }

    @Override
    public void update(int id, PostDto p) {

            postRepository.update(id, modelMapper.map(p, Post.class));
    }

    @Override
    public List<PostDto> findByAuthor(String author) {
        return listMapper.mapList(postRepository.findByAuthor(author), PostDto.class);

    }

}
