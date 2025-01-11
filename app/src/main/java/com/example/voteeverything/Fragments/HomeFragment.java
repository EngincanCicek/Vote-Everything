package com.example.voteeverything.Fragments;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.voteeverything.CreatePostActivity;
import com.example.voteeverything.R;
import com.example.voteeverything.ShowPostActivity;
import com.example.voteeverything.ViewModels.MainViewModel;
import com.example.voteeverything.adapters.PostAdapter;
import com.example.voteeverything.models.Post;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;

public class HomeFragment extends Fragment {

    private MainViewModel viewModel;
    private RecyclerView recyclerView;
    private PostAdapter postAdapter;
    private EditText searchBar;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_home, container, false);


        viewModel = new ViewModelProvider(requireActivity()).get(MainViewModel.class);


        recyclerView = view.findViewById(R.id.recyclerView);
        searchBar = view.findViewById(R.id.searchBar);
        Button searchButton = view.findViewById(R.id.searchButton);
        FloatingActionButton addPostButton = view.findViewById(R.id.addPostButton);


        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));


        postAdapter = new PostAdapter(new ArrayList<>(), post -> {
            Intent intent = new Intent(getActivity(), ShowPostActivity.class);
            intent.putExtra("post", post); // Post sınıfında Parcelable implementasyonu olmalı
            startActivity(intent);
        });

        recyclerView.setAdapter(postAdapter);


        viewModel.getAllPosts().observe(getViewLifecycleOwner(), posts -> {
            postAdapter = new PostAdapter(posts, post -> {
                Intent intent = new Intent(getActivity(), ShowPostActivity.class);
                intent.putExtra("post", post);
                startActivity(intent);
            });
            recyclerView.setAdapter(postAdapter);
        });

        // Search Button
        searchButton.setOnClickListener(v -> {
            String searchText = searchBar.getText().toString().trim();
            if (!searchText.isEmpty()) {
                viewModel.searchPosts(searchText).observe(getViewLifecycleOwner(), posts -> {
                    postAdapter = new PostAdapter(posts, post -> {
                        Intent intent = new Intent(getActivity(), ShowPostActivity.class);
                        intent.putExtra("post", post);
                        startActivity(intent);
                    });
                    recyclerView.setAdapter(postAdapter);
                });
            }
        });

        // Add Post Button
        addPostButton.setOnClickListener(v -> {
            Intent intent = new Intent(getActivity(), CreatePostActivity.class);
            startActivity(intent);
        });

        return view;
    }
}
