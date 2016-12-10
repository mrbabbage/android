package com.timetracker;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Intent;
import android.support.design.widget.NavigationView;
import android.support.design.widget.NavigationView.OnNavigationItemSelectedListener;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.widget.FrameLayout;

//Sets up visual elements that are the foundation for every page
//AppCompatActivity
public class BaseActivity extends AppCompatActivity implements OnNavigationItemSelectedListener {

    private DrawerLayout drawer;
    private SmoothActionBarDrawerToggle toggle;

    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        int itemId = item.getItemId();

        switch (itemId) {
            // takes you to home
            case R.id.home:
                toggle.runWhenIdle(new Runnable() {
                    @Override
                    public void run() {
                        startActivity(new Intent(BaseActivity.this, HomeActivity.class));
                        finish();
                    }
                });
                drawer.closeDrawers();
                break;
// takes you to today
            case R.id.today:
                toggle.runWhenIdle(new Runnable() {
                    @Override
                    public void run() {
                        new UserData().setChart(BaseActivity.this, Utils.getDate());
                        startActivity(new Intent(BaseActivity.this, ChartActivity.class));
                        finish();
                    }
                });
                drawer.closeDrawers();
                break;
        }
        return true;
    }
// getting rid of warnings
    @SuppressLint("InflateParams")
    @Override
    public void setContentView(View view) {
        // base layout
        drawer = (DrawerLayout) getLayoutInflater().inflate(R.layout.activity_base, null);
        FrameLayout content = (FrameLayout) drawer.findViewById(R.id.content_layout);

        // fill content layout with the provided layout
        content.addView(view);

        // set content view
        super.setContentView(drawer);

        // call view initialization method
        viewInitialization();
    }

    @SuppressLint("InflateParams")
    @Override
    public void setContentView(int layoutResID) {
        // base layout
        drawer = (DrawerLayout) getLayoutInflater().inflate(R.layout.activity_base, null);
        FrameLayout content = (FrameLayout) drawer.findViewById(R.id.content_layout);

        // fill content layout with the provided layout
        getLayoutInflater().inflate(layoutResID, content, true);

        // set content view
        super.setContentView(drawer);

        // call view initialization method
        viewInitialization();
    }

    // setting up tool bar and drawer
    private void viewInitialization() {
        // initialize toolbar
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);

        // initialize drawer
        toggle = new SmoothActionBarDrawerToggle(this, drawer, toolbar);
        toggle.setDrawerIndicatorEnabled(false);
        drawer.addDrawerListener(toggle);
        toggle.syncState();

        // initialize base toolbar menu
        toolbar.inflateMenu(R.menu.base_menu);
        toolbar.setOnMenuItemClickListener(new MenuListener());

        // initialize navigation view
        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(BaseActivity.this);
        navigationView.setItemIconTintList(null);
    }

    private class MenuListener implements Toolbar.OnMenuItemClickListener {

        @Override
        public boolean onMenuItemClick(MenuItem item) {
            switch (item.getItemId()) {
                case R.id.today:
                    new UserData().setChart(BaseActivity.this, Utils.getDate());
                    startActivity(new Intent(BaseActivity.this, ChartActivity.class));
                    finish();
                    break;
                case R.id.menu:
                    drawer.openDrawer(GravityCompat.START);
                    break;
            }
            return true;
        }
    }

    private class SmoothActionBarDrawerToggle extends ActionBarDrawerToggle {

        private static final int CLOSE = R.string.drawer_close;
        private static final int OPEN = R.string.drawer_open;
        private Runnable runnable;

        SmoothActionBarDrawerToggle(Activity activity, DrawerLayout drawer, Toolbar toolbar) {
            super(activity, drawer, toolbar, OPEN, CLOSE);
        }

        @Override
        public void onDrawerClosed(View view) {
            super.onDrawerClosed(view);
            invalidateOptionsMenu();
        }

        @Override
        public void onDrawerOpened(View drawerView) {
            super.onDrawerOpened(drawerView);
            invalidateOptionsMenu();
        }

        @Override
        public void onDrawerStateChanged(int newState) {
            super.onDrawerStateChanged(newState);
            if ((runnable != null) && (newState == DrawerLayout.STATE_IDLE)) {
                runnable.run();
                runnable = null;
            }
        }

        void runWhenIdle(Runnable runnable) {
            this.runnable = runnable;
        }
    }
}