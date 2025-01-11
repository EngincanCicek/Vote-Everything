package com.example.voteeverything.ViewModels;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.voteeverything.models.User;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;

public class SignInViewModel extends ViewModel {

    private final FirebaseAuth auth;
    private final FirebaseFirestore firestore;
    private final MutableLiveData<String> signInStatus = new MutableLiveData<>();
    private final MutableLiveData<User> userLiveData = new MutableLiveData<>();

    public SignInViewModel() {
        auth = FirebaseAuth.getInstance();
        firestore = FirebaseFirestore.getInstance();
    }

    public LiveData<String> getSignInStatus() {
        return signInStatus;
    }

    public LiveData<User> getUserLiveData() {
        return userLiveData;
    }

    public void signInWithEmail(String email, String password) {
        if (email.isEmpty() || password.isEmpty()) {
            signInStatus.setValue("Email and password cannot be empty");
            return;
        }

        auth.signInWithEmailAndPassword(email, password)
                .addOnCompleteListener(task -> {
                    if (task.isSuccessful()) {
                        String userId = auth.getCurrentUser().getUid();
                        fetchUserFromFirestore(userId);
                    } else {
                        String errorMessage = task.getException() != null
                                ? task.getException().getMessage()
                                : "Invalid credentials";
                        signInStatus.setValue("Sign-in failed: " + errorMessage);
                    }
                });
    }


    private void fetchUserFromFirestore(String userId) {
        try {
            firestore.collection("users").document(userId)
                    .get()
                    .addOnCompleteListener(task -> {
                        if (task.isSuccessful() && task.getResult() != null && task.getResult().exists()) {
                            DocumentSnapshot document = task.getResult();
                            User user = document.toObject(User.class);
                            if (user != null) {
                                userLiveData.setValue(user);
                                signInStatus.setValue("Sign-in successful");
                            } else {
                                signInStatus.setValue("User data is incomplete or corrupted");
                            }
                        } else {
                            String errorMessage = task.getException() != null ? task.getException().getMessage() : "User not found";
                            signInStatus.setValue("Failed to fetch user data: " + errorMessage);
                        }
                    });
        } catch (Exception e) {
            signInStatus.setValue("An error occurred while fetching user data: " + e.getMessage());
        }
    }


}
