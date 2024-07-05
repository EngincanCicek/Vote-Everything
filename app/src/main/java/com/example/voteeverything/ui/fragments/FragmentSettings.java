package com.example.voteeverything.ui.fragments;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.fragment.app.Fragment;

import com.example.voteeverything.R;
import com.example.voteeverything.ui.SplashActivity;
import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.firebase.auth.FirebaseAuth;

public class FragmentSettings extends Fragment {

    private TextView textViewChangeName;
    private TextView textViewDeleteAccount;
    private TextView textViewDisableNotifications;
    private TextView textViewLogout;

    private GoogleSignInClient mGoogleSignInClient;
    private FirebaseAuth mAuth;

    public FragmentSettings() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_settings, container, false);

        // Initialize Google Sign-in
        configureGoogleSignIn();

        textViewChangeName = view.findViewById(R.id.textViewChangeName);
        textViewDeleteAccount = view.findViewById(R.id.textViewDeleteAccount);
        textViewDisableNotifications = view.findViewById(R.id.textViewDisableNotifications);
        textViewLogout = view.findViewById(R.id.textViewLogout);

        textViewChangeName.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Handle change name functionality
            }
        });

        textViewDeleteAccount.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                deleteAllUsersFromFirestore();

            }
        });

        textViewDisableNotifications.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Handle disable notifications functionality
            }
        });

        textViewLogout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                signOut();
            }
        });

        return view;
    }

    private void deleteAllUsersFromFirestore() {
        /* Firestore'dan tüm kullanıcıları al ve her birini sil
        DummyDao dummyDao = new DummyDao();
        dummyDao.getAllUsers()
                .addOnSuccessListener(users -> {
                    for (User user : users) {
                        dummyDao.deleteUser(user.getUserId())
                                .addOnSuccessListener(aVoid -> {
                                    // Kullanıcı başarıyla silindi
                                    Toast.makeText(requireContext(), "Kullanıcı başarıyla silindi!", Toast.LENGTH_SHORT).show();
                                })
                                .addOnFailureListener(e -> {
                                    // Kullanıcı silinirken hata oluştu
                                    Toast.makeText(requireContext(), "Kullanıcı silinirken hata oluştu: " + e.getMessage(), Toast.LENGTH_SHORT).show();
                                });
                    }
                })
                .addOnFailureListener(e -> {
                    // Kullanıcıları alırken hata oluştu
                    Toast.makeText(requireContext(), "Kullanıcılar alınırken hata oluştu: " + e.getMessage(), Toast.LENGTH_SHORT).show();
                }); */

    }

    private void configureGoogleSignIn() {
        // Make sure to replace "YOUR_WEB_CLIENT_ID" with your actual client ID (refer to previous explanation)
        GoogleSignInOptions gso = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestIdToken(getString(R.string.default_web_client_id))
                .requestEmail()
                .build();

        mGoogleSignInClient = GoogleSignIn.getClient(requireActivity(), gso);
        mAuth = FirebaseAuth.getInstance();
    }

    private void signOut() {
        mAuth.signOut();

        // Sign out from Google Sign-in if applicable
        mGoogleSignInClient.signOut().addOnCompleteListener(requireActivity(), task -> {
            if (task.isSuccessful()) {
                // Sign out successful, update UI and shared preferences
                SharedPreferences sharedPreferences = requireContext().getSharedPreferences("MyPreferences", Context.MODE_PRIVATE);
                SharedPreferences.Editor editor = sharedPreferences.edit();
                editor.putBoolean("isLoggedIn", false);
                editor.apply();

                Toast.makeText(requireContext(), "Logged out successfully!", Toast.LENGTH_SHORT).show();

                new Handler().postDelayed(() -> {
                    Intent splashIntent = new Intent(getActivity(), SplashActivity.class);
                    startActivity(splashIntent);
                    requireActivity().finish();
                }, 1000);
            } else {
                // Sign out failed, handle error
                Toast.makeText(requireContext(), "Sign out failed!", Toast.LENGTH_SHORT).show();
            }
        });
    }
}
