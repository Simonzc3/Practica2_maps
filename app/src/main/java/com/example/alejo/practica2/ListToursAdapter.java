package com.example.alejo.practica2;

import android.content.Context;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.support.annotation.NonNull;
import android.support.v4.graphics.drawable.RoundedBitmapDrawable;
import android.support.v4.graphics.drawable.RoundedBitmapDrawableFactory;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.alejo.practica2.Classes.TourClass;

import java.util.ArrayList;

/**
 * Created by Usuario on 17/10/2017.
 */

public class ListToursAdapter extends ArrayAdapter<TourClass>{


    public ListToursAdapter(@NonNull Context context, ArrayList<TourClass> resource) {
        super(context, 0, resource);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        // Get the data item for this position
        TourClass itemCustomList = getItem(position);
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
        tvTitle.setText(itemCustomList.getName());
        tvDetail.setText(itemCustomList.getDuration());
        tvDetail2.setText(itemCustomList.getCost());
        Bitmap bitmap = itemCustomList.getBmap();
        if (itemCustomList.getBmap() != null) {
            Resources res = ivIcon.getResources();
            RoundedBitmapDrawable roundBitmap = RoundedBitmapDrawableFactory
                    .create(res, bitmap);
            roundBitmap.setCornerRadius(Math.max(bitmap.getWidth(), bitmap.getHeight()) / 2.0f);
            ivIcon.setImageDrawable(roundBitmap);
        }
        //ivIcon.setImageBitmap(itemCustomList.getBmap());

        /*if (itemCustomList.getActionItem() != 0){
            iVAction.setImageResource(itemCustomList.getActionItem());
        }*/
        // Return the completed view to render on screen
        return convertView;
    }
}