package com.example.karat.instagramclone;

import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;

/**
 * Created by karat on 16/01/2018.
 */

public class CustomList extends ArrayAdapter<String> {

    private final Activity context;
    private final ArrayList<String> web;
    private final ArrayList<String> subTitle;
    private final ArrayList<Integer> imageId;

    public CustomList(Activity context, ArrayList<String> web, ArrayList<String> subTitle, ArrayList<Integer> imageId) {
        super(context, R.layout.list_single, web);

        this.context = context;
        this.web = web;
        this.imageId = imageId;
        this.subTitle = subTitle;

    }

    @Override
    public View getView(int position, View view, ViewGroup parent){

        LayoutInflater inflater = context.getLayoutInflater();

        View rowView = inflater.inflate(R.layout.list_single, null, true);

        TextView textTitle = rowView.findViewById(R.id.txt);
        TextView textSubTitle = rowView.findViewById(R.id.txt2);
        ImageView imageView = rowView.findViewById(R.id.imageView);

        textTitle.setText(web.get(position));
        textSubTitle.setText(subTitle.get(position));
        imageView.setImageResource(imageId.get(position));

        return rowView;

    }
}

