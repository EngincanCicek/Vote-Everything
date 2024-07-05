package com.example.voteeverything.model;

import java.util.ArrayList;
import java.util.List;

public class Post {
    private String postId;
    private String userId;
    private String title;
    private String content;
    private double rating;

    private int rateCount;

    private List<com.example.voteeverything.model.Comment> comments;

    public Post(String postId, String userId, String title, String content, double rating, List<Comment> comments, int rateCount) {
        this.postId = postId;
        this.userId = userId;
        this.title = title;
        this.content = content;
        this.rating = rating;
        this.comments = comments;
        this.rateCount = rateCount;
    }

    public String getPostId() {
        return postId;
    }

    public void setPostId(String postId) {
        this.postId = postId;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public double getRating() {
        return rating;
    }

    public void setRating(double rating) {
        this.rating = rating;
    }

    public List<Comment> getComments() {
        return comments;
    }

    public void setComments(List<Comment> comments) {
        this.comments = comments;
    }

    public int getRateCount() {
        return rateCount;
    }

    public void setRateCount(int rateCount) {
        this.rateCount = rateCount;
    }

    public void addComment(Comment comment) {
        if (comments == null) {
            comments = new ArrayList<>();
        }
        comments.add(comment);
    }


    public boolean deleteComment(Comment comment) {
        if (comments != null && comments.contains(comment)) {
            comments.remove(comment);
            return true;
        }
        return false;
    }
}
