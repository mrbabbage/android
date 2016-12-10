package com.timetracker;

import android.content.Context;
import android.graphics.Point;
import android.view.Display;
import android.view.WindowManager;

import java.util.Calendar;

//HELPER FUNCTIONS
// This class is used for storing useful functions that will be used often
// throughout this application. The class itself cannot be instantiated. Instead,
// the methods should be called statically and used as a set of tools.

class Utils {

    private Utils() {
    }

    static public String getDate() {
        Calendar calendar = Calendar.getInstance();
        int year = calendar.get(Calendar.YEAR);
        int month = calendar.get(Calendar.MONTH) + 1;
        int day = calendar.get(Calendar.DAY_OF_MONTH);
        return month + "/" + day + "/" + year;
    }

    static public int getImage(String activity) {

        return R.drawable.leisure;
    }

    public static int screenHeight(Context context) {
        return screenDimensions(context).y;
    }

    public static int screenWidth(Context context) {
        return screenDimensions(context).x;
    }

    private static Point screenDimensions(Context context) {
        WindowManager wm = (WindowManager) context.getSystemService(Context.WINDOW_SERVICE);
        Display display = wm.getDefaultDisplay();
        Point screenDimensions = new Point();
        display.getSize(screenDimensions);
        return screenDimensions;
    }
}


