package com.timetracker;

import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.github.mikephil.charting.charts.PieChart;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.data.PieData;
import com.github.mikephil.charting.data.PieDataSet;
import com.github.mikephil.charting.data.PieEntry;
import com.github.mikephil.charting.formatter.IValueFormatter;
import com.github.mikephil.charting.utils.ViewPortHandler;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class ChartActivity extends BaseActivity {

    private PieChart chart;
    private TextView noData;

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            startActivity(new Intent(this, HomeActivity.class));
            finish();
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chart);

        // set toolbar title
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        toolbar.setTitle(getString(R.string.app_name));

        // get date of chart to be plotted
        String date = new UserData().getChart(this);

        // set header
        TextView dateView = (TextView) findViewById(R.id.date);
        dateView.setText(date);
        ImageView headerImage = (ImageView) findViewById(R.id.image);
        Picasso.with(this).load(R.drawable.clouds).fit().into(headerImage);

        // initialize the add activity button
        Button addActivity = (Button) findViewById(R.id.add_activity_button);
        addActivity.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                new AddActivityDialog().show();
            }
        });

        // initialize the views
        chart = (PieChart) findViewById(R.id.chart);
        noData = (TextView) findViewById(R.id.no_data);

        // display chart
        updateChart();
    }

    private void updateChart() {
        // clear current data
        chart.clear();

        // get the day object of chart to be plotted
        String date = new UserData().getChart(this);
        Map<String, Day> data = new UserData().getData(this);
        final Day day = data.get(date);

        // check if there is data to show
        if (day.getTotalHours() > 0) {
            chart.setVisibility(View.VISIBLE);
            noData.setVisibility(View.GONE);
        } else {
            chart.setVisibility(View.GONE);
            noData.setVisibility(View.VISIBLE);
            return;
        }

        // initialize list of chart colors
        List<Integer> colorsList = new ArrayList<>();

        // create the entries which hold the raw data
        // Mike Phil library
        List<PieEntry> entries = new ArrayList<>();
        if (day.getDining() > 0) {
            entries.add(new PieEntry(day.getDining(), getString(R.string.dining)));
            colorsList.add(R.color.red);
        }
        if (day.getExercise() > 0) {
            entries.add(new PieEntry(day.getExercise(), getString(R.string.exercise)));
            colorsList.add(R.color.gray);
        }
        if (day.getLecture() > 0) {
            entries.add(new PieEntry(day.getLecture(), getString(R.string.lecture)));
            colorsList.add(R.color.blue);
        }
        if (day.getLeisure() > 0) {
            entries.add(new PieEntry(day.getLeisure(), getString(R.string.leisure)));
            colorsList.add(R.color.orange);
        }
        if (day.getStudy() > 0) {
            entries.add(new PieEntry(day.getStudy(), getString(R.string.study)));
            colorsList.add(R.color.green);
        }
        if (day.getWork() > 0) {
            entries.add(new PieEntry(day.getWork(), getString(R.string.work)));
            colorsList.add(R.color.pink);
        }

        // convert colors list to int array
        int[] colors = new int[colorsList.size()];
        for (int i = 0; i < colorsList.size(); i++) {
            colors[i] = colorsList.get(i);
        }

        // create the pie chart data set
        PieDataSet dataSet = new PieDataSet(entries, "");
        dataSet.setColors(colors, this);
        dataSet.setValueTextColor(Color.WHITE);
        dataSet.setValueTextSize(20);
        dataSet.setSelectionShift(0);
        dataSet.setValueFormatter(new IValueFormatter() {
            @Override
            public String getFormattedValue(float v, Entry e, int i, ViewPortHandler vPH) {
                int percentage = Math.round((v / day.getTotalHours()) * 100);
                return String.valueOf(percentage) + "%";
            }
        });

        // format the chart
        chart.getLegend().setEnabled(false);
        chart.setHoleRadius(30);
        chart.setHoleColor(Color.TRANSPARENT);
        chart.setTransparentCircleRadius(35);
        chart.setTransparentCircleAlpha(100);
        chart.setDescription(null);

        // populate the chart
        chart.setData(new PieData(dataSet));
    }

    private class AddActivityDialog extends Dialog {

        AddActivityDialog() {
            super(ChartActivity.this, R.style.AppTheme_Dialog);
        }

        @Override
        protected void onCreate(Bundle savedInstanceState) {
            super.onCreate(savedInstanceState);
            setContentView(R.layout.dialog_add_activity);
            // leaving dropdown menu by clicking background/back button
            setCancelable(true);

            // initialize spinner variables
            Context context = ChartActivity.this;
            int layout = R.layout.spinner_textview;

            // initialize activity select spinner
            final Spinner activitySelect = (Spinner) findViewById(R.id.activity_select_spinner);
            ArrayAdapter<CharSequence> activityAdapter;
            final int activities = R.array.activities;
            activityAdapter = ArrayAdapter.createFromResource(context, activities, layout);
            activityAdapter.setDropDownViewResource(R.layout.spinner_dropdown_textview);
            activitySelect.setAdapter(activityAdapter);

            // initialize hours select spinner
            final Spinner hoursSelect = (Spinner) findViewById(R.id.hours_select_spinner);
            ArrayAdapter<CharSequence> hoursAdapter;
            final int hours = R.array.hours;
            hoursAdapter = ArrayAdapter.createFromResource(context, hours, layout);
            hoursAdapter.setDropDownViewResource(R.layout.spinner_dropdown_textview);
            hoursSelect.setAdapter(hoursAdapter);

            // initialize go button
            Button goButton = (Button) findViewById(R.id.go_button);
            goButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    // get the selected activity
                    int activity = activitySelect.getSelectedItemPosition();
                    if (activity == 0) {
                        String message = getString(R.string.no_activity_selected);
                        Toast.makeText(ChartActivity.this, message, Toast.LENGTH_SHORT).show();
                        return;
                    }

                    // get the selected hours
                    int hours = hoursSelect.getSelectedItemPosition();
                    if (hours == 0) {
                        String message = getString(R.string.no_hours_selected);
                        Toast.makeText(ChartActivity.this, message, Toast.LENGTH_SHORT).show();
                        return;
                    }

                    // get the day object of chart to be plotted
                    String date = new UserData().getChart(ChartActivity.this);
                    Map<String, Day> data = new UserData().getData(ChartActivity.this);
                    Day day = data.get(date);

                    // update the day object
                    switch (activity) {
                        case 1:
                            day.addDining(hours);
                            break;
                        case 2:
                            day.addExercise(hours);
                            break;
                        case 3:
                            day.addLecture(hours);
                            break;
                        case 4:
                            day.addLeisure(hours);
                            break;
                        case 5:
                            day.addStudy(hours);
                            break;
                        case 6:
                            day.addWork(hours);
                            break;
                    }

                    // update the user data
                    data.put(day.getDate(), day);
                    new UserData().setData(ChartActivity.this, data);

                    // update the chart
                    updateChart();

                    // close dialog
                    dismiss();
                }
            });
        }
    }
}
