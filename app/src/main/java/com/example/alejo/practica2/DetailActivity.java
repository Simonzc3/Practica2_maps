package com.example.alejo.practica2;

import android.content.Intent;
import android.content.SharedPreferences;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.support.v4.graphics.drawable.RoundedBitmapDrawable;
import android.support.v4.graphics.drawable.RoundedBitmapDrawableFactory;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.alejo.practica2.Classes.RequestClass;
import com.example.alejo.practica2.Classes.Tags;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class DetailActivity extends AppCompatActivity {

    private Toolbar toolbar;
    private String url,name,description,detail,cost,duration,tour_key;
    private TextView item_name,item_detail,item_cost,item_description,item_duration;
    private Button schedule;
    private ImageView item_image;
    FetchImage fetchImage;
    FirebaseDatabase database = FirebaseDatabase.getInstance();
    DatabaseReference myRef = database.getReference("Request");
    SharedPreferences prefs;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);

        toolbar = findViewById(R.id.toolbar);

        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        Bundle extras = getIntent().getExtras();
        url = extras.getString("url");
        name = extras.getString("name");
        description = extras.getString("description");
        detail = extras.getString("detail");
        cost = extras.getString("cost");
        duration = extras.getString("duration");
        tour_key = extras.getString("tour");
        //Toast.makeText(getApplicationContext(),tour_key,Toast.LENGTH_SHORT).show();

         item_name = findViewById(R.id.item_title);
         item_detail = findViewById(R.id.item_detail);
         item_cost = findViewById(R.id.item_cost);
         item_description = findViewById(R.id.item_description);
         item_duration = findViewById(R.id.item_duration);
         item_image = findViewById(R.id.image);
         schedule = findViewById(R.id.bSchedule);


         item_name.setText(name);
         item_detail.setText(detail);
         item_cost.setText(cost);
         item_description.setText(description);
         item_duration.setText(duration);
        fetchImage = new FetchImage(this, new FetchImage.AsyncResponse() {
            @Override
            public void processFinish(Bitmap bitmap) {
                if (bitmap != null) {
                    Resources res = getResources();
                    item_image.setImageBitmap(bitmap);
                }
            }
        });
        fetchImage.execute(url);

    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == android.R.id.home){
            //Intent intent = new Intent();
            Intent intent = new Intent(DetailActivity.this, ToursActivity.class);
            intent.setFlags(Intent.FLAG_ACTIVITY_REORDER_TO_FRONT);
            fetchImage.cancel(true);
            startActivity(intent);

            finish();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    public void schedule(View view) {
        prefs = getSharedPreferences(Tags.TAG_PREFERENCES,MODE_PRIVATE);
        myRef.push().setValue(new RequestClass("0",tour_key,prefs.getString(Tags.TAG_KEY,"")));
        Toast.makeText(getApplicationContext(),"Request generated",Toast.LENGTH_SHORT).show();

    }
}
