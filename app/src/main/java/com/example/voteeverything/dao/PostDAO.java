package com.example.voteeverything.dao;

import com.example.voteeverything.model.Post;

import java.util.List;

public interface PostDAO {
    void insert(Post post);
    void update(Post post);
    void delete(Post post);
    Post getPostById(int postId);
    List<Post> getAllPosts();
    List<Post> getPostsByUserId(int userId);
}
