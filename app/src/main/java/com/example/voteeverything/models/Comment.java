package com.example.voteeverything.models;

import android.os.Parcel;
import android.os.Parcelable;

public class Comment implements Parcelable {
    private String commentId;
    private String postId;
    private String userId;
    private int rating;
    private String description;
    private String creationTime;
    private String updateTime;

    // Default constructor (Firebase Firestore için gerekli)
    public Comment() {
    }

    public Comment(String commentId, String postId, String userId, int rating, String description, String creationTime, String updateTime) {
        this.commentId = commentId;
        this.postId = postId;
        this.userId = userId;
        this.rating = rating;
        this.description = description;
        this.creationTime = creationTime;
        this.updateTime = updateTime;
    }

    // Getters and Setters
    public String getCommentId() {
        return commentId;
    }

    public void setCommentId(String commentId) {
        this.commentId = commentId;
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

    public int getRating() {
        return rating;
    }

    public void setRating(int rating) {
        this.rating = rating;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getCreationTime() {
        return creationTime;
    }

    public void setCreationTime(String creationTime) {
        this.creationTime = creationTime;
    }

    public String getUpdateTime() {
        return updateTime;
    }

    public void setUpdateTime(String updateTime) {
        this.updateTime = updateTime;
    }

    // Parcelable implementation
    protected Comment(Parcel in) {
        commentId = in.readString();
        postId = in.readString();
        userId = in.readString();
        rating = in.readInt();
        description = in.readString();
        creationTime = in.readString();
        updateTime = in.readString();
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(commentId);
        dest.writeString(postId);
        dest.writeString(userId);
        dest.writeInt(rating);
        dest.writeString(description);
        dest.writeString(creationTime);
        dest.writeString(updateTime);
    }

    @Override
    public int describeContents() {
        return 0;
    }

    public static final Creator<Comment> CREATOR = new Creator<Comment>() {
        @Override
        public Comment createFromParcel(Parcel in) {
            return new Comment(in);
        }

        @Override
        public Comment[] newArray(int size) {
            return new Comment[size];
        }
    };
}
