package com.timetracker;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import java.util.List;

class HomeAdapter extends ArrayAdapter<Day> {

    private final Context context;
    private final List<Day> data;

    public HomeAdapter(Context context, List<Day> data) {
        super(context, -1, data);
        this.context = context;
        this.data = data;
    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        ViewHolder viewHolder;

        if (convertView == null) {
            // initialize view holder and layout inflater
            viewHolder = new ViewHolder();
            LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = inflater.inflate(R.layout.view_home, parent, false);

            // initialize views
            viewHolder.layout = (FrameLayout) convertView.findViewById(R.id.list_item_layout);
            viewHolder.date = (TextView) convertView.findViewById(R.id.date);
            viewHolder.image = (ImageView) convertView.findViewById(R.id.image);

            // set tag
            convertView.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolder) convertView.getTag();
        }

        // set the date
        viewHolder.date.setText(data.get(position).getDate());

        // set the image
        int width = Utils.screenWidth(context);
        int height = Utils.screenHeight(context) / 4;
        viewHolder.image.getLayoutParams().width = width;
        viewHolder.image.getLayoutParams().height = height;
        viewHolder.image.setLayoutParams(viewHolder.image.getLayoutParams());
        int resourceId = Utils.getImage(data.get(position).getMax());
        Picasso.with(context).load(resourceId).resize(width, height)
                .centerCrop().into(viewHolder.image);

        // set onclick listener
        viewHolder.layout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                new UserData().setChart(context, data.get(position).getDate());
                context.startActivity(new Intent(context, ChartActivity.class));
                ((Activity) context).finish();
            }
        });

        return convertView;
    }

    // sets up individual days in list view
    private class ViewHolder {

        FrameLayout layout;
        TextView date;
        ImageView image;
    }
}