package com.example.voteeverything.util;

import androidx.appcompat.app.AppCompatActivity;

public class ActionBarSetting {

    public static void closeActionBar(AppCompatActivity activity) {
        if (activity.getSupportActionBar() != null) {
            activity.getSupportActionBar().hide();
        }
    }
}
