package com.example.com3104_group10;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.MenuItem;

import com.example.com3104_group10.fragments.CalculatorFragment;
import com.example.com3104_group10.fragments.DrawingFragment;
import com.example.com3104_group10.fragments.MapFragment;
import com.google.android.material.bottomnavigation.BottomNavigationView;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        getSupportFragmentManager().beginTransaction()
                .add(R.id.layout,new DrawingFragment()).commit();

        BottomNavigationView bottomNavigationView = findViewById(R.id.bottom_nav);
        bottomNavigationView.setSelectedItemId(R.id.dra);
        bottomNavigationView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                switch (item.getItemId()) {
                    case R.id.dra:
                        getSupportFragmentManager().beginTransaction()
                                .replace(R.id.layout,new DrawingFragment()).commit();
                        return true;
                    case R.id.cal:
                        getSupportFragmentManager().beginTransaction()
                                .replace(R.id.layout,new CalculatorFragment()).commit();
                        return true;
                    case R.id.map:
                        getSupportFragmentManager().beginTransaction()
                                .replace(R.id.layout,new MapFragment()).commit();
                        return true;
                }
                return false;
            }
        });
    }
}