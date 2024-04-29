package com.example.voteeverything.ui;

import static com.example.voteeverything.util.ActionBarSetting.closeActionBar;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import android.os.Bundle;
import android.view.MenuItem;

import com.example.voteeverything.R;
import com.example.voteeverything.ui.fragments.FragmentHomePage;
import com.example.voteeverything.ui.fragments.FragmentSettings;
import com.example.voteeverything.ui.fragments.FragmentUserPage;
import com.google.android.material.bottomnavigation.BottomNavigationView;

public class MainActivity extends AppCompatActivity {
    private BottomNavigationView bottomNavigationView;

    private FragmentUserPage fragment1;
    private FragmentHomePage fragment2;
    private FragmentSettings fragment3;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        closeActionBar(this);


        bottomNavigationView = findViewById(R.id.bottom_navigation);

        fragment1 = new FragmentUserPage();
        fragment2 = new FragmentHomePage();
        fragment3 = new FragmentSettings();

        getSupportFragmentManager().beginTransaction()
                .replace(R.id.fragment_container, fragment1)
                .commit();

        bottomNavigationView.setOnItemSelectedListener(
                new BottomNavigationView.OnItemSelectedListener() {
                    @Override
                    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                        Fragment selectedFragment = null;

                        if (item.getItemId() == R.id.action_fragment1) {
                            selectedFragment = fragment1;
                        } else if (item.getItemId() == R.id.action_fragment2) {
                            selectedFragment = fragment2;
                        } else if (item.getItemId() == R.id.action_fragment3) {
                            selectedFragment = fragment3;
                        }


                        // Seçilen fragment gösterilir
                        getSupportFragmentManager().beginTransaction()
                                .replace(R.id.fragment_container, selectedFragment)
                                .setTransition(FragmentTransaction.TRANSIT_FRAGMENT_FADE)
                                .commit();

                        return true;
                    }
                }
        );

    }
}