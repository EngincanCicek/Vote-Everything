package com.example.voteeverything.models;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.List;

public class Post implements Parcelable {
    private String postId;
    private String description;
    private String userId;
    private List<Comment> comments;
    private String creationTime;
    private String updateTime;

    // Default constructor (Firebase Firestore için gerekli)
    public Post() {
    }

    public Post(String postId, String description, String userId, List<Comment> comments, String creationTime, String updateTime) {
        this.postId = postId;
        this.description = description;
        this.userId = userId;
        this.comments = comments;
        this.creationTime = creationTime;
        this.updateTime = updateTime;
    }

    // Getters and Setters
    public String getPostId() {
        return postId;
    }

    public void setPostId(String postId) {
        this.postId = postId;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public List<Comment> getComments() {
        return comments;
    }

    public void setComments(List<Comment> comments) {
        this.comments = comments;
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
    protected Post(Parcel in) {
        postId = in.readString();
        description = in.readString();
        userId = in.readString();
        comments = in.createTypedArrayList(Comment.CREATOR);
        creationTime = in.readString();
        updateTime = in.readString();
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(postId);
        dest.writeString(description);
        dest.writeString(userId);
        dest.writeTypedList(comments);
        dest.writeString(creationTime);
        dest.writeString(updateTime);
    }

    @Override
    public int describeContents() {
        return 0;
    }

    public static final Creator<Post> CREATOR = new Creator<Post>() {
        @Override
        public Post createFromParcel(Parcel in) {
            return new Post(in);
        }

        @Override
        public Post[] newArray(int size) {
            return new Post[size];
        }
    };
}
