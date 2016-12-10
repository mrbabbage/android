package com.timetracker;

import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.widget.ListView;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

// Main screen
// list view basically

public class HomeActivity extends BaseActivity {

    //Android lifecycle

    @Override //java lingo that lets compiler know this method is defined in SUPER CLASS and im overriding it
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //sets up visuals from .xml
        setContentView(R.layout.activity_home);

        // initialize toolbar
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        toolbar.setTitle(R.string.app_name);

        // get user data
        Map<String, Day> data = new UserData().getData(this);
        if (data == null) {
            data = new HashMap<>();
        }

        // ensure that an entry for today exists
        String today = Utils.getDate();
        if (!data.containsKey(today)) {
            data.put(today, new Day(today));
            new UserData().setData(this, data);
        }

        // create list from map
        final List<Day> dataList = new ArrayList<>(data.values());

        // initialize list view
        // most of the work is done in HomeAdapter
        ListView listView = (ListView) findViewById(R.id.list_view);
        HomeAdapter adapter = new HomeAdapter(this, dataList);
        listView.setAdapter(adapter);
    }
}
