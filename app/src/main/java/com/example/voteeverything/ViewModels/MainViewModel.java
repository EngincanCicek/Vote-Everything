package com.example.voteeverything.ViewModels;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.voteeverything.models.Comment;
import com.example.voteeverything.models.Post;
import com.example.voteeverything.models.User;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.List;

public class MainViewModel extends ViewModel {




    private String homeFragmentText = "Home Fragment";
    private String profileFragmentText = "Profile Fragment";

    public String getHomeFragmentText() {
        return homeFragmentText;
    }

    public String getProfileFragmentText() {
        return profileFragmentText;
    }

    private final FirebaseFirestore firestore = FirebaseFirestore.getInstance();
    private final MutableLiveData<List<Post>> userPosts = new MutableLiveData<>();
    private final MutableLiveData<List<Comment>> userComments = new MutableLiveData<>();

    public LiveData<List<Post>> getUserPosts(String userId) {
        firestore.collection("posts")
                .whereEqualTo("userId", userId)
                .get()
                .addOnSuccessListener(queryDocumentSnapshots -> {
                    List<Post> posts = queryDocumentSnapshots.toObjects(Post.class);
                    userPosts.setValue(posts);
                });
        return userPosts;
    }

    public LiveData<List<Comment>> getUserComments(String userId) {
        firestore.collection("comments")
                .whereEqualTo("userId", userId)
                .get()
                .addOnSuccessListener(queryDocumentSnapshots -> {
                    List<Comment> comments = queryDocumentSnapshots.toObjects(Comment.class);
                    userComments.setValue(comments);
                });
        return userComments;
    }
    public LiveData<List<Post>> searchPosts(String searchText) {
        MutableLiveData<List<Post>> searchResults = new MutableLiveData<>();
        firestore.collection("posts")
                .whereGreaterThanOrEqualTo("postId", searchText)
                .whereLessThanOrEqualTo("postId", searchText + "\uf8ff")
                .get()
                .addOnSuccessListener(queryDocumentSnapshots -> {
                    List<Post> posts = queryDocumentSnapshots.toObjects(Post.class);
                    searchResults.setValue(posts);
                });
        return searchResults;
    }
    public LiveData<List<Post>> getAllPosts() {
        MutableLiveData<List<Post>> allPosts = new MutableLiveData<>();
        firestore.collection("posts")
                .get()
                .addOnSuccessListener(queryDocumentSnapshots -> {
                    List<Post> posts = queryDocumentSnapshots.toObjects(Post.class);
                    allPosts.setValue(posts);
                });
        return allPosts;
    }

    public String getCurrentUserId() {
        return FirebaseAuth.getInstance().getCurrentUser() != null
                ? FirebaseAuth.getInstance().getCurrentUser().getUid()
                : null;
    }
    public LiveData<User> getUserInfo(String userId) {
        MutableLiveData<User> userLiveData = new MutableLiveData<>();
        firestore.collection("users").document(userId)
                .get()
                .addOnSuccessListener(documentSnapshot -> {
                    if (documentSnapshot.exists()) {
                        User user = documentSnapshot.toObject(User.class);
                        userLiveData.setValue(user);
                    }
                });
        return userLiveData;
    }
}
