// FragmentSettings.java
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

import androidx.fragment.app.Fragment;

import com.example.voteeverything.R;
import com.example.voteeverything.ui.SplashActivity;

public class FragmentSettings extends Fragment {
    private TextView textViewChangeName;
    private TextView textViewDeleteAccount;
    private TextView textViewDisableNotifications;
    private TextView textViewLogout;

    public FragmentSettings() {}

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_settings, container, false);

        textViewChangeName = view.findViewById(R.id.textViewChangeName);
        textViewDeleteAccount = view.findViewById(R.id.textViewDeleteAccount);
        textViewDisableNotifications = view.findViewById(R.id.textViewDisableNotifications);
        textViewLogout = view.findViewById(R.id.textViewLogout);

        textViewChangeName.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
            }
        });

        textViewDeleteAccount.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
            }
        });

        textViewDisableNotifications.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
            }
        });

        textViewLogout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SharedPreferences sharedPreferences = requireContext().getSharedPreferences("MyPreferences", Context.MODE_PRIVATE);

                SharedPreferences.Editor editor = sharedPreferences.edit();
                editor.putBoolean("isLoggedIn", false);
                editor.apply();
                new Handler().postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        Intent splashIntent = new Intent(getActivity(), SplashActivity.class);
                        startActivity(splashIntent);
                        getActivity().finish();
                    }
                }, 1000);


            }
        });

        return view;
    }
}
