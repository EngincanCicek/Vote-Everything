package com.example.voteeverything.ui.fragments;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.voteeverything.R;
import com.example.voteeverything.model.DummyPostCreatorForTest;
import com.example.voteeverything.model.Post;
import com.example.voteeverything.ui.adapters.PostAdapter;
import com.example.voteeverything.model.User;

import java.util.List;

public class FragmentUserPage extends Fragment {

    TextView textViewName;
    private RecyclerView userPostsRecyclerView;
    private PostAdapter postAdapter;
    private User user;

    public FragmentUserPage() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_user_page, container, false);
        textViewName = view.findViewById(R.id.textViewUserName);

        userPostsRecyclerView = view.findViewById(R.id.userPostsRecyclerView);

        user = new User("id","Kullanıcı Adı", null, null); // Kullanıcı adı ve resmi buraya eklenebilir

        textViewName.setText(user.getUserName());
        loadUserPosts();

        return view;
    }

    private void loadUserPosts() {
        List<Post> userPosts = DummyPostCreatorForTest.createDummyPosts();

        userPostsRecyclerView.setLayoutManager(new LinearLayoutManager(getContext()));

        postAdapter = new PostAdapter(userPosts);
        userPostsRecyclerView.setAdapter(postAdapter);
    }
}
