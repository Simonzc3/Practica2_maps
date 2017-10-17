package com.example.alejo.practica2;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.example.alejo.practica2.Fragments.TabFragment;

public class ToursActivity extends DrawerActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tours);

        if (findViewById(R.id.fragment_container) != null){
            if (savedInstanceState != null)
                return;

            TabFragment fragment = new TabFragment();

            getSupportFragmentManager().beginTransaction()
                    .add(R.id.fragment_container, fragment).commit();
        }
    }
}
