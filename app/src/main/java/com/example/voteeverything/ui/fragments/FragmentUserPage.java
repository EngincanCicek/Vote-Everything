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
import com.example.voteeverything.StaticShorCut;
import com.example.voteeverything.dao.DummyDao;
import com.example.voteeverything.model.User;
import com.example.voteeverything.ui.PostActivity;
import com.example.voteeverything.ui.adapters.PostAdapter;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class FragmentUserPage extends Fragment {

    private TextView textViewName;
    private RecyclerView userPostsRecyclerView;
    private PostAdapter postAdapter;
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

        FirebaseUser currentUser = FirebaseAuth.getInstance().getCurrentUser();
        if (currentUser != null) {
            dummyDao.getUser(currentUser.getUid())
                    .addOnSuccessListener(new OnSuccessListener<User>() {
                        @Override
                        public void onSuccess(User user) {
                            if (user != null) {
                                textViewName.setText(user.getUserName());
                                loadAllPosts();
                            } else {
                                textViewName.setText(StaticShorCut.DEFAULTNAME);
                            }
                        }
                    })
                    .addOnFailureListener(e -> {
                        Toast.makeText(getContext(), "Kullanıcı bilgileri alınamadı: " + e.getMessage(), Toast.LENGTH_SHORT).show();
                    });
        }

        imageView.setOnClickListener(v -> startPostCreationActivity());

        return view;
    }

    private void loadAllPosts() {
        dummyDao.getAllPosts()
                .addOnSuccessListener(posts -> {
                    userPostsRecyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
                    postAdapter = new PostAdapter(posts, getContext());
                    userPostsRecyclerView.setAdapter(postAdapter);
                })
                .addOnFailureListener(e -> {
                    Toast.makeText(getContext(), "Postlar yüklenirken hata oluştu: " + e.getMessage(), Toast.LENGTH_SHORT).show();
                });
    }

    private void startPostCreationActivity() {
        Intent intent = new Intent(getContext(), PostActivity.class);
        startActivity(intent);
    }
}
