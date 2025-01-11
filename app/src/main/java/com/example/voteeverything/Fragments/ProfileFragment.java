package com.example.voteeverything.Fragments;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.PopupMenu;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.voteeverything.R;
import com.example.voteeverything.SplashActivity;
import com.example.voteeverything.ViewModels.MainViewModel;
import com.example.voteeverything.adapters.CommentAdapter;
import com.example.voteeverything.adapters.PostAdapter;
import com.example.voteeverything.models.Comment;
import com.example.voteeverything.models.Post;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.firebase.auth.FirebaseAuth;

import java.util.ArrayList;

public class ProfileFragment extends Fragment {

    private MainViewModel viewModel;
    private RecyclerView recyclerView;
    private PostAdapter postAdapter;
    private CommentAdapter commentAdapter;
    private TextView usernameTextView;
    private Button showPostsButton;
    private Button showCommentsButton;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_profile, container, false);


        viewModel = new ViewModelProvider(requireActivity()).get(MainViewModel.class);


        usernameTextView = view.findViewById(R.id.usernameTextView);
        showPostsButton = view.findViewById(R.id.showPostsButton);
        showCommentsButton = view.findViewById(R.id.showCommentsButton);
        recyclerView = view.findViewById(R.id.recyclerView);
        FloatingActionButton actionButton = view.findViewById(R.id.profileActionButton);

        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));


        loadUserInfo();

        loadUserPosts();

        showPostsButton.setOnClickListener(v -> loadUserPosts());

        showCommentsButton.setOnClickListener(v -> loadUserComments());

        actionButton.setOnClickListener(v -> showPopupMenu(v));

        return view;
    }

    private void loadUserInfo() {
        String userId = viewModel.getCurrentUserId();
        if (userId != null) {
            viewModel.getUserInfo(userId).observe(getViewLifecycleOwner(), user -> {
                if (user != null) {
                    usernameTextView.setText("Username: " + user.getUsername());
                }
            });
        }
    }

    private void loadUserPosts() {
        String userId = viewModel.getCurrentUserId();
        if (userId != null) {
            viewModel.getUserPosts(userId).observe(getViewLifecycleOwner(), posts -> {
                if (posts != null) {
                    postAdapter = new PostAdapter(posts, post -> {

                    });
                    recyclerView.setAdapter(postAdapter);
                }
            });
        }
    }

    private void loadUserComments() {
        String userId = viewModel.getCurrentUserId();
        if (userId != null) {
            viewModel.getAllPosts().observe(getViewLifecycleOwner(), posts -> {
                if (posts != null) {
                    ArrayList<Comment> userCommentsList = new ArrayList<>();
                    for (Post post : posts) {
                        if (post.getComments() != null) {
                            for (Comment comment : post.getComments()) {
                                if (comment.getUserId().equals(userId) && comment.getDescription() != null && !comment.getDescription().isEmpty()) {
                                    userCommentsList.add(comment);
                                }
                            }
                        }
                    }
                        // Users comments
                    commentAdapter = new CommentAdapter(userCommentsList, comment -> {
                        // comments click
                    });
                    recyclerView.setAdapter(commentAdapter);
                }
            });
        }
    }

    private void showPopupMenu(View view) {
        PopupMenu popupMenu = new PopupMenu(requireContext(), view);
        MenuInflater inflater = popupMenu.getMenuInflater();
        inflater.inflate(R.menu.profile_menu, popupMenu.getMenu());

        popupMenu.setOnMenuItemClickListener(item -> {
            if (item.getItemId() == R.id.logoutMenuItem) {
                logoutUser();
                return true;
            } else if (item.getItemId() == R.id.deleteAccountMenuItem) {
                // TODO HESAP-KULLANICI SİLME İŞLEMİ YAP
                return true;
            }
            return false;
        });
        popupMenu.show();
    }

    private void logoutUser() {
        FirebaseAuth.getInstance().signOut();
        Intent intent = new Intent(requireContext(), SplashActivity.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
        startActivity(intent);
    }
}
