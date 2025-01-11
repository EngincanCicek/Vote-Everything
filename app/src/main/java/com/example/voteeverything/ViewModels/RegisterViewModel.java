package com.example.voteeverything.ViewModels;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.voteeverything.models.User;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.ArrayList;

public class RegisterViewModel extends ViewModel {

    private final FirebaseAuth auth;
    private final FirebaseFirestore firestore;
    private final MutableLiveData<String> registrationStatus = new MutableLiveData<>();

    public RegisterViewModel() {
        auth = FirebaseAuth.getInstance();
        firestore = FirebaseFirestore.getInstance();
    }

    public LiveData<String> getRegistrationStatus() {
        return registrationStatus;
    }

    public void registerUser(String email, String password) {
        if (email.isEmpty() || password.isEmpty()) {
            registrationStatus.setValue("Please fill in both fields");
            return;
        }

        auth.createUserWithEmailAndPassword(email, password)
                .addOnCompleteListener(task -> {
                    if (task.isSuccessful()) {

                        String userId = auth.getCurrentUser().getUid();


                        User user = new User(userId, email, password, new ArrayList<>(), new ArrayList<>());


                        firestore.collection("users").document(userId).set(user)
                                .addOnCompleteListener(firestoreTask -> {
                                    if (firestoreTask.isSuccessful()) {
                                        registrationStatus.setValue("Registration successful");
                                    } else {
                                        registrationStatus.setValue("Firestore error: " + firestoreTask.getException().getMessage());
                                    }
                                });
                    } else {
                        registrationStatus.setValue("Registration failed: " + task.getException().getMessage());
                    }
                });
    }
}
