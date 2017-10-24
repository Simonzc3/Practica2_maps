package com.example.alejo.practica2;

import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.MenuItem;
import android.widget.ImageView;

import com.example.alejo.practica2.Fragments.PerfilFragment;
import com.example.alejo.practica2.Fragments.TabFragment;

public class BasicActivity extends DrawerActivity {

    private BottomNavigationView mBottomNav;
    FragmentManager fm;
    FragmentTransaction ft;
    ImageView imagen;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_basic);

        BottomNavigationView navigation = (BottomNavigationView) findViewById(R.id.navigation);
        navigation.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener);

        mBottomNav = (BottomNavigationView) findViewById(R.id.navigation);


        imagen = (ImageView) findViewById(R.id.batman);


        fm = getSupportFragmentManager();
        ft = fm.beginTransaction();

        SuperFragment fragment = new SuperFragment();
        ft.add(R.id.fragment_container, fragment).commit();
    }
        private BottomNavigationView.OnNavigationItemSelectedListener mOnNavigationItemSelectedListener
                = new BottomNavigationView.OnNavigationItemSelectedListener() {

            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                switch (item.getItemId()) {
                    case R.id.menu_home:
                        //mTextMessage.setText(R.string.but_car);
                    /*fragment = new CarFragment();
                    getSupportFragmentManager().beginTransaction().
                            replace(R.id.frame_car, fragment).commit();*/

                        fm = getSupportFragmentManager();
                        ft = fm.beginTransaction();

                        FlashFragment frangment1 = new FlashFragment();
                        ft.replace(R.id.fragment_container, frangment1).commit();

                        return true;
                    case R.id.menu_search:
                        //mTextMessage.setText(R.string.but_compras);
                    /*fragment = new MisComprasFragment();
                    getSupportFragmentManager().beginTransaction().
                            replace(R.id.frame_car, fragment).commit();*/

                        fm = getSupportFragmentManager();
                        ft = fm.beginTransaction();


                        SuperFragment frangment2 = new SuperFragment();
                        imagen.setImageResource(R.drawable.people);
                        ft.replace(R.id.fragment_container, frangment2).commit();
                        return true;
                /*case R.id.navigation_notifications:
                    mTextMessage.setText(R.string.title_notifications);
                    return true;*/
                    case R.id.menu_notifications:
                        fm = getSupportFragmentManager();
                        ft = fm.beginTransaction();


                        SuperFragment frangment3 = new SuperFragment();
                        imagen.setImageResource(R.drawable.cto);
                        ft.replace(R.id.fragment_container, frangment3).commit();

                        return true;
                }
                return false;
            }

        };



}
