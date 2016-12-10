package com.timetracker;

import android.content.Context;
import android.content.SharedPreferences;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.Map;

// User data is written to and read from disk. This way, data persists even
// if the device is turned off.

class UserData {
// storing in map object
    // saving all the data
    public void setData(Context context, Map<String, Day> data) {
        SharedPreferences.Editor editor = sharedPreferences(context).edit();
        if (data == null) {
            editor.putString("data", "");
        } else {
            String dataJson = new Gson().toJson(data);
            editor.putString("data", dataJson);
        }
        editor.apply();
    }
// passing the day to the chartActivity
    public void setChart(Context context, String date) {
        SharedPreferences.Editor editor = sharedPreferences(context).edit();
        editor.putString("chart", date);
        editor.apply();
    }

    public Map<String, Day> getData(Context context) {
        String dataJson = sharedPreferences(context).getString("data", "");
        if (dataJson.equals("")) {
            return null;
        } else {
            Type dataType = new TypeToken<Map<String, Day>>() {
            }.getType();
            return new Gson().fromJson(dataJson, dataType);
        }
    }

    public String getChart(Context context) {
        return sharedPreferences(context).getString("chart", "");
    }

    private SharedPreferences sharedPreferences(Context context) {
        return context.getSharedPreferences("user_data", Context.MODE_PRIVATE);
    }
}
