package com.example.alejo.practica2.Fragments;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.v4.app.ListFragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.TextView;

import com.example.alejo.practica2.DrawerActivity;
import com.example.alejo.practica2.ListToursAdapter;
import com.example.alejo.practica2.R;
import com.example.alejo.practica2.ToursList;

import java.util.ArrayList;

/**
 * Created by Usuario on 16/10/2017.
 */

public class ToursFragment extends ListFragment implements AdapterView.OnItemClickListener{
    private  int posit;
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
        ArrayList<ToursList> list = new ArrayList<>();
        switch(posit){
            case 0:
                list = ToursList.addAvailableTours();
                break;
            case 1:
                list = ToursList.addSchedduledTours();
                break;
        }

        ListToursAdapter adapter  = new ListToursAdapter((getActivity()).getApplicationContext(), list);
        setListAdapter(adapter);
        getListView().setOnItemClickListener(this);
    }

    protected Intent intent;
    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position,long id) {
    }
    protected Runnable delay = new Runnable() {
        @Override
        public void run() {
            startActivity(intent);
        }
    };
}
