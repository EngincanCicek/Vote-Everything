package com.example.voteeverything.ui.fragments;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.voteeverything.R;
import com.example.voteeverything.dao.DummyDao;
import com.example.voteeverything.model.User;
import com.example.voteeverything.ui.PostActivity;
import com.example.voteeverything.ui.adapters.PostAdapter;

public class FragmentUserPage extends Fragment {

    TextView textViewName;
    private RecyclerView userPostsRecyclerView;
    private PostAdapter postAdapter;
    private User user;
    private DummyDao dummyDao;

    private ImageView imageView;

    public FragmentUserPage() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        dummyDao = new DummyDao(); // DummyDao sınıfını burada oluşturun
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_user_page, container, false);
        textViewName = view.findViewById(R.id.textViewUserName);
        userPostsRecyclerView = view.findViewById(R.id.userPostsRecyclerView);
        imageView = view.findViewById(R.id.imageViewButton);

        user = new User("id","Kullanıcı Adı", null, null); // Kullanıcı adı ve resmi buraya eklenebilir

        textViewName.setText(user.getUserName());
        loadUserPosts();

        imageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startPostCreationActivity();
            }
        });

        return view;
    }

    private void loadUserPosts() {
        dummyDao.getUserPosts(user.getUserId())
                .addOnSuccessListener(posts -> {
                    userPostsRecyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
                    postAdapter = new PostAdapter(posts, getContext());
                    userPostsRecyclerView.setAdapter(postAdapter);
                })
                .addOnFailureListener(e -> {
                    Toast.makeText(getContext(), "Kullanıcı postları yüklenirken hata oluştu: " + e.getMessage(), Toast.LENGTH_SHORT).show();
                });
    }

    private void startPostCreationActivity() {
        Intent intent = new Intent(getContext(), PostActivity.class);
        startActivity(intent);
    }
}
