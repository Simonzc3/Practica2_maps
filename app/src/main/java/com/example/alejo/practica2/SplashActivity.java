package com.example.alejo.practica2;

import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.ActivityInfo;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.Toast;

import com.example.alejo.practica2.Classes.TourClass;
import com.example.alejo.practica2.Classes.UserClass;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.Timer;
import java.util.TimerTask;

public class SplashActivity extends AppCompatActivity {

    SharedPreferences prefs;
    SharedPreferences.Editor editor;
    FirebaseDatabase database = FirebaseDatabase.getInstance();
    DatabaseReference myRef = database.getReference("Tours");


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);


        prefs = getSharedPreferences(Tags.TAG_PREFERENCES,MODE_PRIVATE);
        final int oplog  = prefs.getInt(Tags.LOGIN_OPTION,0);




        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        TimerTask task = new TimerTask() {
            @Override
            public void run() {
                Intent intent;
                if(oplog == 0) {
                    intent = new Intent(SplashActivity.this, LoguinActivity.class);
                }else{
                    intent = new Intent(SplashActivity.this, MainActivity.class);
                }
                startActivity(intent);
                finish();
            }
        };
        Timer timer = new Timer();
        timer.schedule(task,3000);


        myRef.push().setValue(new TourClass("$92 dollar","Welcome to Medellín, city of the eternal spring and the most innovative city in the world. In order to show you the most distinctive landmarks of the capital, we offer an interesting tour through the main avenues, squares and parks, the historic centre, churches and other places of interest. During the tour, you can observe the enormous social, cultural and architectonic transformation that this city has experienced over the last twenty years. This half-day outing would be the best option for you to get to know the city in the company of a highly knowledgeable and private guide. We have experts not only in the history of the town, but of the whole country. We can start the tour from the doorstep of your hotel, no matter in which part of the city you’re staying. If you are staying in the south, for example,i.e. El Poblado, we will begin with ‘La Milla de Oro’, the Golden Mile, which is the financial and hotel sector. Here we will find the Square ‘Parque de El Poblado’ which is the very place where the first settlements of the ‘Villa de San Lorenzo de Aná’  took place, later to become the city of Medellín.  We will continue along the Avenida El Poblado until we reach the city centre. The next stop will be the Square Parque de San Antonio, where we can appreciate some sculptures by Fernando Botero and discuss the transformation that the city experienced. To have a look at the San Antonio Church with its imposing dome is also a must. The next stop will be the cathedral Catedral Metropolitana, named after the Immaculate Conception of the Virgin Mary, a brick building in the neoclassic style. The cathedral is situated on the square Parque de Bolívar that is named after the liberator Simón Bolívar. On we go to Plaza de las Esculturas (Sculptures’ Square), just outside of Museo de Antioquia. Here we can walk around to have a look at the twenty three statues donated to the city by Fernando Botero, a good opportunity to take some interesting pictures. In this area we will also see the gothic styled Palacio de la Cultura and the building that houses the Hotel Nutibara- the first luxury hotel of the city, constructed in 1945.  Finally we head for the south again, this time visiting the old train station ‘Estación de Ferrocarril’ and the Square of Lights, Plaza de las Luces. After a short stop at Parque de los pies Descalzos (Square of the Barefooted) to have a look at the architecture, we will go up the hill of Cerro Nutibara to visit the life-sized replica of a typical village of the region called Pueblito Paisa. There we can enjoy a panoramic view of the city (360 degrees) and the surrounding mountains and breathing its fresh air. This would be, without a doubt, the best way to finish this private tour. The guide will then take you back to your hotel. If you need any extra information about where to stay, we recommend Trivago to find the best option.","Pickup at hotel and back to hotel in Medellin Private tour. Parque de El Poblado. Visit of Parque San Antonio . Catedral Metropolitana. Walk through the Plaza de las Esculturas (Sculptures by Fernando Botero). Panoramic view of the city (360 degrees) from the hill Cerro Nutibara."
                ,"Half day tour (4 hours) From 9 am or 2 pm","MEDELLIN CITY TOUR"
                ,"https://firebasestorage.googleapis.com/v0/b/practica2-144ce.appspot.com/o/Imagenes%2F_Media_Default_image__project_parque-de_eo__parque-de_eo_.png?alt=media&token=5d7f576e-d502-4fa0-bc16-8b65d063853e"
        ));












    }
}
