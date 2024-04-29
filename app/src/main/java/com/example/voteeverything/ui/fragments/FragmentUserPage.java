package com.example.voteeverything.ui.fragments;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.fragment.app.Fragment;

import com.example.voteeverything.R;

public class FragmentUserPage extends Fragment {

    public FragmentUserPage() {
        // Boş yapıcı metod gereklidir.
    }

    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Fragment'ın layout dosyasını yükler
        return inflater.inflate(R.layout.fragment_user_page, container, false);
    }

}
