package com.waa.assignments.repo;

import com.waa.assignments.entity.business.Post;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;

@Repository
public class PostRepository implements BaseRepository<Post>{

    private static final List<Post> posts;
    static {
        posts = new ArrayList<>();
        posts.add(new Post(1, "Post 1", "Post 1 description", "Author"));
        posts.add(new Post(2, "Post 2", "Post 2 description", "Author"));
        posts.add(new Post(3, "Post 3", "Post 3 description", "Author"));
        posts.add(new Post(4, "Post 4", "Post 4 description", "Author"));
        posts.add(new Post(5, "Post 5", "Post 5 description", "Author"));
        posts.add(new Post(6, "Post 6", "Post 6 description", "Author"));

    }

    @Override
    public List<Post> findAll() {
        return posts;

    }

    @Override
    public void save(Post post) {
        posts.add(post);
    }

    @Override
    public Post getById(int id) {
        return posts.stream().filter(p -> p.getId() == id).findFirst().orElse(null);
    }

    @Override
    public void delete(Post post) {
        posts.remove(post);
    }

    @Override
    public int update(Post post) {
        posts.stream().filter(p -> p.getId() == post.getId()).findFirst().ifPresent(p -> {
            p.setTitle(post.getTitle());
            p.setContent(post.getContent());
            p.setAuthor(post.getAuthor());
        });
        return post.getId();
    }

    @Override
    public void delete(int id) {
        posts.removeIf(p -> p.getId() == id);
    }

    @Override
    public void update(int id, Post post) {
        posts.stream().filter(p -> p.getId() == id).findFirst().ifPresent(p -> {
            p.setTitle(post.getTitle());
            p.setContent(post.getContent());
            p.setAuthor(post.getAuthor());
        });
    }

    public List<Post> findByAuthor(String author) {
        return posts.stream().filter(p -> p.getAuthor().equals(author)).toList();
    }
}
