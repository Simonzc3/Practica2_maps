package com.example.alejo.practica2;

import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.MenuItem;
import android.widget.ImageView;

import com.example.alejo.practica2.Fragments.Image2Fragment;
import com.example.alejo.practica2.Fragments.ImageFragment;
import com.example.alejo.practica2.Fragments.PerfilFragment;
import com.example.alejo.practica2.Fragments.TabFragment;

public class BasicActivity extends DrawerActivity {

    private BottomNavigationView mBottomNav;
    FragmentManager fm;
    FragmentTransaction ft;
    ImageView imagen;
    int case1=0,case2=0,case3 =0;


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

        ImageFragment fragment = new ImageFragment();
        ft.add(R.id.fragment_container, fragment).commit();
    }
        private BottomNavigationView.OnNavigationItemSelectedListener mOnNavigationItemSelectedListener
                = new BottomNavigationView.OnNavigationItemSelectedListener() {

            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                switch (item.getItemId()) {
                    case R.id.menu_home:
                            fm = getSupportFragmentManager();
                            ft = fm.beginTransaction();

                            ImageFragment frangment1 = new ImageFragment();
                            ft.replace(R.id.fragment_container, frangment1).commit();
                            case1=1;
                            return true;
                    case R.id.menu_search:
                        case1 =0;
                        //mTextMessage.setText(R.string.but_compras);
                    /*fragment = new MisComprasFragment();
                    getSupportFragmentManager().beginTransaction().
                            replace(R.id.frame_car, fragment).commit();*/

                        fm = getSupportFragmentManager();
                        ft = fm.beginTransaction();


                        Image2Fragment frangment2 = new Image2Fragment();

                        ft.replace(R.id.fragment_container, frangment2).commit();
                        return true;
                /*case R.id.navigation_notifications:
                    mTextMessage.setText(R.string.title_notifications);
                    return true;*/
                    case R.id.menu_notifications:
                        case1 =0;
                        fm = getSupportFragmentManager();
                        ft = fm.beginTransaction();

                        SuperFragment frangment3 = new SuperFragment();
                        //imagen.setImageResource(R.drawable.cto);
                        ft.replace(R.id.fragment_container, frangment3).commit();

                        return true;
                }
                return false;
            }

        };



}
