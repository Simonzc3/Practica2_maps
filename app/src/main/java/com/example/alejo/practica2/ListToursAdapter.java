package com.example.alejo.practica2;

import android.content.Context;
import android.support.annotation.NonNull;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;

/**
 * Created by Usuario on 17/10/2017.
 */

public class ListToursAdapter extends ArrayAdapter<ToursList>{


    public ListToursAdapter(@NonNull Context context, ArrayList<ToursList> resource) {
        super(context, 0, resource);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        // Get the data item for this position
        ToursList itemCustomList = getItem(position);
        // Check if an existing view is being reused, otherwise inflate the view
        if (convertView == null) {
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.list_tours, parent, false);
        }

        // Lookup view for data population
        TextView tvTitle = (TextView) convertView.findViewById(R.id.list_title);
        TextView tvDetail = (TextView) convertView.findViewById(R.id.list_detail);
        TextView tvDetail2 = (TextView) convertView.findViewById(R.id.list_detail2);
        ImageView ivIcon = (ImageView) convertView.findViewById(R.id.list_time);
        ImageView iVAction = (ImageView) convertView.findViewById(R.id.list_action);

        // Populate the data into the template view using the data object
        tvTitle.setText(itemCustomList.getTitle());
        tvDetail.setText(itemCustomList.getTime());
        tvDetail2.setText(itemCustomList.getCost());
        ivIcon.setImageResource(itemCustomList.getImageID());

        if (itemCustomList.getActionItem() != 0){
            iVAction.setImageResource(itemCustomList.getActionItem());
        }
        // Return the completed view to render on screen
        return convertView;
    }
}