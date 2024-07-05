package com.example.voteeverything.ui.fragments;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.voteeverything.R;
import com.example.voteeverything.dao.DummyDao;
import com.example.voteeverything.model.Comment;
import com.example.voteeverything.model.Post;
import com.example.voteeverything.ui.adapters.CommentAdapter;

import java.util.ArrayList;
import java.util.List;

public class FragmentPost extends Fragment {

    private TextView textViewTitle;
    private TextView textViewContent;
    private TextView textViewRating;
    private Post post;
    private RecyclerView recyclerViewComments;
    private DummyDao dummyDao;

    public FragmentPost() {
        // Required empty public constructor
    }

    public static FragmentPost newInstance(Post post) {
        FragmentPost fragment = new FragmentPost();
        fragment.post = post;
        return fragment;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        dummyDao = new DummyDao(); // DummyDao sınıfını burada oluşturun
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View rootView = inflater.inflate(R.layout.post_view_fragment, container, false);

        // Initialize views
        textViewTitle = rootView.findViewById(R.id.textViewTitle);
        textViewContent = rootView.findViewById(R.id.textViewContent);
        textViewRating = rootView.findViewById(R.id.textViewRating);
        recyclerViewComments = rootView.findViewById(R.id.recyclerViewComments);

        // Set post data
        textViewTitle.setText(post.getTitle());
        textViewContent.setText(post.getContent());
        textViewRating.setText(getString(R.string.rating_text, post.getRating(), post.getRateCount()));

        // Setup RecyclerView for comments
        setupRecyclerView();

        // Load comments
        loadComments();

        return rootView;
    }

    private void setupRecyclerView() {
        recyclerViewComments.setLayoutManager(new LinearLayoutManager(getContext()));
        List<Comment> comments = new ArrayList<>(); // Başlangıçta boş liste
        CommentAdapter commentAdapter = new CommentAdapter(comments);
        recyclerViewComments.setAdapter(commentAdapter);
    }

    private void loadComments() {
        dummyDao.getAllComments()
                .addOnSuccessListener(comments -> {
                    // Yorumları RecyclerView'e yükle
                    requireActivity().runOnUiThread(() -> {
                        CommentAdapter commentAdapter = new CommentAdapter(comments);
                        recyclerViewComments.setAdapter(commentAdapter);
                    });
                })
                .addOnFailureListener(e -> {
                    // Hata durumunda kullanıcıya bilgi verilebilir
                    requireActivity().runOnUiThread(() -> {
                        Toast.makeText(getContext(), "Yorumlar yüklenirken hata oluştu: " + e.getMessage(), Toast.LENGTH_SHORT).show();
                    });
                });
    }
}
