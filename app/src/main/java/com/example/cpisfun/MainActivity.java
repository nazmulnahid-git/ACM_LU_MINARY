package com.example.cpisfun;

import android.os.Bundle;
import android.view.MenuItem;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

import com.google.android.material.bottomnavigation.BottomNavigationView;

public class MainActivity extends AppCompatActivity {

    private static final int MENU_HOME = R.id.navigation_home;
    private static final int MENU_PROFILE = R.id.navigation_profile;
    private static final int MENU_SETTINGS = R.id.navigation_community;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        BottomNavigationView bottomNavigationView = findViewById(R.id.bottom_navigation);

        bottomNavigationView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                Fragment selectedFragment = null;

                if (item.getItemId() == MENU_HOME) {
                    selectedFragment = new HomeFragment();
                } else if (item.getItemId() == MENU_SETTINGS) {
                    selectedFragment = new CommunityFragment();
                } else if (
                        item.getItemId() == MENU_PROFILE) {
                    selectedFragment = new ProfileFragment();

                }

                if (selectedFragment != null) {
                    getSupportFragmentManager().beginTransaction()
                            .replace(R.id.nav_host_fragment, selectedFragment)
                            .commit();
                    return true;
                }
                return false;
            }
        });

        // Set default selection
        bottomNavigationView.setSelectedItemId(MENU_HOME);
    }
}
