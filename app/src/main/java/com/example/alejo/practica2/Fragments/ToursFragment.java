package com.example.alejo.practica2.Fragments;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.nfc.Tag;
import android.os.Bundle;
import android.support.v4.app.ListFragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;

import com.example.alejo.practica2.Classes.Tags;
import com.example.alejo.practica2.Classes.TourClass;
import com.example.alejo.practica2.DetailActivity;
import com.example.alejo.practica2.DrawerActivity;
import com.example.alejo.practica2.FetchImage;
import com.example.alejo.practica2.ListToursAdapter;
import com.example.alejo.practica2.R;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.io.OutputStream;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.prefs.Preferences;

/**
 * Created by Usuario on 16/10/2017.
 */

public class ToursFragment extends ListFragment{
    private  int posit;

    FirebaseDatabase firebaseDatabase;
    DatabaseReference databaseReference;
    private int index = 0;
    private int index1 = 0;

    ArrayList<TourClass> toursList = new ArrayList<>();
    ArrayList<TourClass> requestList = new ArrayList<>();
    ArrayList<String> tour_ID = new ArrayList<>();
    @Override
    public View onCreateView(LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.list_fragment, container, false);

        posit = getArguments().getInt("position");
        ((DrawerActivity) getActivity()).getSupportActionBar().setTitle("Tours");
        return view;
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        final SharedPreferences prefs = getContext().getSharedPreferences(Tags.TAG_PREFERENCES, Context.MODE_PRIVATE);
        switch(posit){
            case 0:
                firebaseDatabase = FirebaseDatabase.getInstance();
                databaseReference = firebaseDatabase.getReference("Tours");

                databaseReference.addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(DataSnapshot dataSnapshot) {
                        for(DataSnapshot child: dataSnapshot.getChildren()){
                            TourClass tour = new TourClass();
                            tour = child.getValue(TourClass.class);
                            tour.setKey(child.getKey());
                            toursList.add(tour);
                            //toursList.add(child.getValue(TourClass.class));
                        }
                        FetchImage fetchImage = new FetchImage(getContext(), fetchListener);
                        fetchImage.execute(toursList.get(index).getUrl());
                    }

                    @Override
                    public void onCancelled(DatabaseError databaseError) {

                    }
                });
                break;
            case 1:
                firebaseDatabase = FirebaseDatabase.getInstance();
                databaseReference = firebaseDatabase.getReference("Request");


                databaseReference.addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(DataSnapshot dataSnapshot) {
                        for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
                            if(snapshot.child("user_id").exists() && snapshot.child("tour_id").exists()){
                                if (snapshot.child("user_id").getValue().equals(prefs.getString(Tags.TAG_KEY, ""))) {

                                    tour_ID.add(snapshot.child("tour_id").getValue().toString());
                            }


                            }
                        }
                        databaseReference = firebaseDatabase.getReference("Tours");
                        databaseReference.addListenerForSingleValueEvent(new ValueEventListener() {
                            @Override
                            public void onDataChange(DataSnapshot dataSnapshot) {
                                for (String tourItems:tour_ID.toArray(new String[tour_ID.size()])){
                                    if (dataSnapshot.child(tourItems).exists()){
                                        TourClass tour = new TourClass();
                                        tour = dataSnapshot.child(tourItems).getValue(TourClass.class);
                                        tour.setKey(dataSnapshot.child(tourItems).getKey());
                                        requestList.add(tour);
                                        //requestList.add( dataSnapshot.child(tourItems).getValue(TourClass.class));
                                    }

                                }
                                if (!requestList.isEmpty()){
                                    FetchImage fetchImage = new FetchImage(getContext(), fetchListener1);
                                    fetchImage.execute(requestList.get(index1).getUrl());
                                }

                            }
                            public void onCancelled(DatabaseError databaseError) {

                            }
                        });

                    }
                    public void onCancelled(DatabaseError databaseError) {

                    }
                });

               // list = TourClass.addSchedduledTours();
                break;
        }


    }

    protected Intent intent;

    protected Runnable delay = new Runnable() {
        @Override
        public void run() {
            startActivity(intent);
        }
    };

    private FetchImage.AsyncResponse fetchListener = new FetchImage.AsyncResponse() {
        @Override
        public void processFinish(Bitmap bitmap) {
                toursList.get(index).setBmap(bitmap);
                index++;
                if (index < toursList.size()) {
                    FetchImage fetchImage = new FetchImage(getContext(), fetchListener);
                    fetchImage.execute(toursList.get(index).getUrl());
                }else{
                    ListToursAdapter adapter  = new ListToursAdapter(getContext(), toursList);
                    setListAdapter(adapter);
                    getListView().setOnItemClickListener(listener);
                }


        }
    };

    private FetchImage.AsyncResponse fetchListener1 = new FetchImage.AsyncResponse() {
        public void processFinish(Bitmap bitmap) {
            requestList.get(index1).setBmap(bitmap);
            index1++;
            if (index1 < requestList.size()) {
                FetchImage fetchImage = new FetchImage(getContext(), fetchListener1);
                fetchImage.execute(requestList.get(index1).getUrl());
            }else{
                ListToursAdapter adapter  = new ListToursAdapter(getContext(), requestList);
                setListAdapter(adapter);
                getListView().setOnItemClickListener(listener1);
            }


        }
    };

    private AdapterView.OnItemClickListener listener = new AdapterView.OnItemClickListener() {
        @Override
        public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
            Intent intent = new Intent(getContext(), DetailActivity.class);
            intent.putExtra("name", toursList.get(i).getName());
            intent.putExtra("detail", toursList.get(i).getDetail());
            intent.putExtra("description", toursList.get(i).getDescription());
            intent.putExtra("duration", toursList.get(i).getDuration());
            intent.putExtra("cost", toursList.get(i).getCost());
            intent.putExtra("url", toursList.get(i).getUrl());
            intent.putExtra("tour", toursList.get(i).getKey());
            startActivity(intent);
            //   intent.putExtra("tour", toursList.get(i));
           // startActivity(intent);
        }
    };

    private AdapterView.OnItemClickListener listener1 = new AdapterView.OnItemClickListener() {
        @Override
        public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {

        }
    };
}
