package com.example.voteeverything.ui;

import static com.example.voteeverything.util.ActionBarSetting.closeActionBar;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;

import com.example.voteeverything.R;
import com.example.voteeverything.dao.DummyDao;
import com.example.voteeverything.model.User;
import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.common.api.ApiException;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthCredential;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.GoogleAuthProvider;

import java.util.ArrayList;

public class LoginActivity extends AppCompatActivity {

    private GoogleSignInClient mGoogleSignInClient;
    private FirebaseAuth mAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        closeActionBar(this);



        // Configure Google Sign-in
        configureGoogleSignIn();

        CardView cardView = findViewById(R.id.cardViewLoggin);

        cardView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Initiate Google Sign-in
                signInWithGoogle();
            }
        });
    }

    private void configureGoogleSignIn() {
        // Replace "YOUR_WEB_CLIENT_ID" with your actual client ID
        GoogleSignInOptions gso = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestIdToken(getString(R.string.default_web_client_id))
                .requestEmail()
                .build();

        mGoogleSignInClient = GoogleSignIn.getClient(this, gso);
        mAuth = FirebaseAuth.getInstance();
    }

    private void signInWithGoogle() {
        Intent signInIntent = mGoogleSignInClient.getSignInIntent();
        startActivityForResult(signInIntent, /* RC_SIGN_IN */ 0);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        // Result returned from launching the Intent from GoogleSignInApi.getSignInIntent(...)
        if (requestCode == /* RC_SIGN_IN */ 0) {
            Task<GoogleSignInAccount> task = GoogleSignIn.getSignedInAccountFromIntent(data);
            handleSignInResult(task);
        }
    }

    private void handleSignInResult(Task<GoogleSignInAccount> completedTask) {
        try {
            GoogleSignInAccount account = completedTask.getResult(ApiException.class);

            if (account != null) {
                // Signed in successfully, proceed with Firebase authentication
                firebaseAuthWithGoogle(account.getIdToken());
            } else {
                // Sign in failed, update UI appropriately
                Toast.makeText(this, "Sign in failed!", Toast.LENGTH_SHORT).show();
            }
        } catch (ApiException e) {
            // Handle exception
            Toast.makeText(this, "Sign in error: " + e.getMessage(), Toast.LENGTH_SHORT).show();
            e.printStackTrace();
        }
    }

    private void firebaseAuthWithGoogle(String idToken) {
        AuthCredential credential = GoogleAuthProvider.getCredential(idToken, null);
        mAuth.signInWithCredential(credential)
                .addOnCompleteListener(this, task -> {
                    if (task.isSuccessful()) {
                        // Sign in success, update UI with the signed-in user's information
                        Toast.makeText(LoginActivity.this, "Sign in successful!", Toast.LENGTH_SHORT).show();
                        DummyDao dummyDao = new DummyDao();
                        FirebaseUser firebaseUser = mAuth.getCurrentUser();


                        String userId = firebaseUser.getUid();
                        String userName = firebaseUser.getDisplayName();
                        String email = firebaseUser.getEmail();

                        User user = new User(userId, userName, email, new ArrayList<>());

                        dummyDao.createUser(user);


                        SharedPreferences sharedPreferences = getSharedPreferences("MyPreferences", Context.MODE_PRIVATE);
                        SharedPreferences.Editor editor = sharedPreferences.edit();
                        editor.putBoolean("isLoggedIn", true);
                        editor.apply();


                        goToMainActivity();
                    } else {
                        // Sign in failed, display a message to the user.
                        Toast.makeText(LoginActivity.this, "Firebase authentication failed!", Toast.LENGTH_SHORT).show();
                    }
                });
    }

    private void goToMainActivity() {
        Intent intent = new Intent(LoginActivity.this, MainActivity.class);
        startActivity(intent);
        finish();
    }
}
