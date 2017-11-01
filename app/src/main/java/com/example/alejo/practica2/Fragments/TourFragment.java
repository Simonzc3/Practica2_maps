package com.example.alejo.practica2.Fragments;


import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.example.alejo.practica2.Classes.TourClass;
import com.example.alejo.practica2.R;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.net.HttpURLConnection;
import java.net.URL;
import java.util.HashMap;
import java.util.Map;

/**
 * A simple {@link Fragment} subclass.
 */
public class TourFragment extends Fragment{
    String foto="";
    TextView name, cost, description, detail,duration;
    Button bCreate, bUpdate, bRead, bDelete;
    ImageView ifoto;
    int cont=0;
    TourClass tourClass;

    public TourFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_tour, container, false);


        name = (TextView) view.findViewById(R.id.name);
        cost = (TextView) view.findViewById(R.id.cost);
        description = (TextView) view.findViewById(R.id.description);
        detail = (TextView) view.findViewById(R.id.detail);
        duration = (TextView) view.findViewById(R.id.duration);

        ifoto = (ImageView) view.findViewById(R.id.foto);

        bCreate = (Button) view.findViewById(R.id.bCreate);
        bUpdate = (Button) view.findViewById(R.id.bUpdate);
        bRead = (Button) view.findViewById(R.id.bRead);
        bDelete = (Button) view.findViewById(R.id.bDelete);

        FirebaseDatabase database = FirebaseDatabase.getInstance();
        DatabaseReference myRef = database.getReference("Tours");
        //leer cuando entra los datos en la base de datos

        myRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                if (dataSnapshot.child("Tour0").exists()){
                    tourClass = dataSnapshot.child("Tour0").getValue(TourClass.class);
                    name.setText(tourClass.getName());
                    cost.setText(tourClass.getCost());
                    description.setText(tourClass.getDescription());
                    detail.setText(tourClass.getDetail());
                    duration.setText(tourClass.getDuration());
                    foto = tourClass.getUrl();
                    iniciar();
                }
            }
            @Override
            public void onCancelled(DatabaseError databaseError) {
            }
        });



        bCreate.setOnClickListener(new View.OnClickListener() {
            FirebaseDatabase database = FirebaseDatabase.getInstance();
            DatabaseReference myRef = database.getReference("Tours").child("Tour"+cont);
            @Override
            public void onClick(View v) {
                Toast.makeText(getActivity(),"si entró al create ",Toast.LENGTH_SHORT).show();
                name.setText("si si funciono yey");
            }
        });

        bUpdate.setOnClickListener(new View.OnClickListener() {
            FirebaseDatabase database = FirebaseDatabase.getInstance();
            DatabaseReference myRef = database.getReference("Tours").child("Tour"+cont);
            @Override
            public void onClick(View v) {
                name.setText("si si funciono yey");
            }
        });
        bRead.setOnClickListener(new View.OnClickListener() {
            FirebaseDatabase database = FirebaseDatabase.getInstance();
            DatabaseReference myRef = database.getReference("Tours");
            @Override
            public void onClick(View v) {
                //Toast.makeText(getActivity(),"si entró al read ",Toast.LENGTH_SHORT).show();
                myRef.addValueEventListener(new ValueEventListener() {

                    @Override
                    public void onDataChange(DataSnapshot dataSnapshot) {


                        if (dataSnapshot.child("Tour0").exists()){
                            tourClass = dataSnapshot.child("Tour0").getValue(TourClass.class);
                            name.setText(tourClass.getName());

                            cost.setText(tourClass.getCost());
                            description.setText(tourClass.getDescription());
                            detail.setText(tourClass.getDetail());
                            duration.setText(tourClass.getDuration());
                            foto = tourClass.getUrl();

                            Toast.makeText(getActivity(),foto,Toast.LENGTH_SHORT).show();
                            iniciar();
                        }
                    }
                    @Override
                    public void onCancelled(DatabaseError databaseError) {
                        Toast.makeText(getActivity(),"Error al leer el tour",Toast.LENGTH_SHORT).show();
                    }
                });

            }
        });
        bDelete.setOnClickListener(new View.OnClickListener() {
            FirebaseDatabase database = FirebaseDatabase.getInstance();
            DatabaseReference myRef = database.getReference("Tours").child("Tour"+cont);

            @Override
            public void onClick(View v) {

                name.setText("si si funciono yey");
            }
        });


        return view;
    }


    public void iniciar(){
//        Glide.with(getContext())
//                .load(foto)
//                .into(ifoto);
        if(foto != null) Glide.with(getActivity().getApplicationContext()).load(foto)
                .thumbnail(0.5f)
                .crossFade()
                .diskCacheStrategy(DiskCacheStrategy.ALL)
                .into(ifoto);
    }

}
