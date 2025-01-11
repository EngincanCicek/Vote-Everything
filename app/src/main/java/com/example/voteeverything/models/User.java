package com.example.voteeverything.models;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.List;

public class User implements Parcelable {
    private String userId;
    private String username;
    private String password;
    private List<String> postIdList;
    private List<String> commentIdList;

    // Default constructor (Firestore için gerekli!)
    public User() {
    }


    public User(String userId, String username, String password, List<String> postIdList, List<String> commentIdList) {
        this.userId = userId;
        this.username = username;
        this.password = password;
        this.postIdList = postIdList;
        this.commentIdList = commentIdList;
    }


    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public List<String> getPostIdList() {
        return postIdList;
    }

    public void setPostIdList(List<String> postIdList) {
        this.postIdList = postIdList;
    }

    public List<String> getCommentIdList() {
        return commentIdList;
    }

    public void setCommentIdList(List<String> commentIdList) {
        this.commentIdList = commentIdList;
    }

    // Parcelable implementasyonu
    protected User(Parcel in) {
        userId = in.readString();
        username = in.readString();
        password = in.readString();
        postIdList = in.createStringArrayList();
        commentIdList = in.createStringArrayList();
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(userId);
        dest.writeString(username);
        dest.writeString(password);
        dest.writeStringList(postIdList);
        dest.writeStringList(commentIdList);
    }

    @Override
    public int describeContents() {
        return 0;
    }

    public static final Creator<User> CREATOR = new Creator<User>() {
        @Override
        public User createFromParcel(Parcel in) {
            return new User(in);
        }

        @Override
        public User[] newArray(int size) {
            return new User[size];
        }
    };
}
