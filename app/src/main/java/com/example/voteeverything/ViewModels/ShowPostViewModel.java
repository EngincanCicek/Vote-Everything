package com.example.voteeverything.ViewModels;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.voteeverything.models.Post;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.FirebaseFirestore;

public class ShowPostViewModel extends ViewModel {

    private final FirebaseFirestore firestore = FirebaseFirestore.getInstance();
    private final FirebaseAuth auth = FirebaseAuth.getInstance();

    public LiveData<String> getPostOwner(String userId) {
        MutableLiveData<String> usernameLiveData = new MutableLiveData<>();
        firestore.collection("users").document(userId)
                .get()
                .addOnSuccessListener(documentSnapshot -> {
                    if (documentSnapshot.exists()) {
                        String username = documentSnapshot.getString("username");
                        usernameLiveData.setValue(username);
                    }
                });
        return usernameLiveData;
    }
    public LiveData<String> getCommentOwner(String userId) {
        MutableLiveData<String> usernameLiveData = new MutableLiveData<>();
        firestore.collection("users").document(userId)
                .get()
                .addOnSuccessListener(documentSnapshot -> {
                    if (documentSnapshot.exists()) {
                        String username = documentSnapshot.getString("username");
                        usernameLiveData.setValue(username);
                    }
                });
        return usernameLiveData;
    }


    public String getCurrentUserId() {
        return auth.getCurrentUser() != null ? auth.getCurrentUser().getUid() : null;
    }
}
