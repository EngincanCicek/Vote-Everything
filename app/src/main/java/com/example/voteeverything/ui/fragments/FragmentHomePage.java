package com.example.voteeverything.ui.fragments;

import android.graphics.Color;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.SearchView;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.voteeverything.R;
import com.example.voteeverything.model.DummyPostCreatorForTest;
import com.example.voteeverything.model.Post;
import com.example.voteeverything.ui.adapters.PostAdapter;

import java.util.ArrayList;
import java.util.List;

public class FragmentHomePage extends Fragment {

    private SearchView searchView;
    private RecyclerView recyclerView;
    private List<Post> itemList;
    private PostAdapter adapter;

    public FragmentHomePage() {}

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_home_page, container, false);


        searchView = view.findViewById(R.id.searchView);
        recyclerView = view.findViewById(R.id.recyclerViewHome);



        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        itemList = new ArrayList<>();
        itemList.addAll(DummyPostCreatorForTest.createDummyPosts());

        adapter = new PostAdapter(itemList);
        recyclerView.setAdapter(adapter);

        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                adapter.getFilter().filter(newText);
                return true;
            }
        });

        return view;
    }
}
