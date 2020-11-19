package com.example.androidlabs;

import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.drawerlayout.widget.DrawerLayout;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;

import com.google.android.material.navigation.NavigationView;

public class TestToolbar extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener{

    Toolbar toolbar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_test_toolbar);
        toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.toolbar_items, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.item1:
                Toast.makeText(this, R.string.clickitem1, Toast.LENGTH_LONG).show();
                break;
            case R.id.item2:
                Toast.makeText(this, R.string.clickitem2, Toast.LENGTH_LONG).show();
                break;
            case R.id.item3:
                Toast.makeText(this, R.string.clickitem3, Toast.LENGTH_LONG).show();
                break;
            case R.id.overflow:
                Toast.makeText(this, R.string.thisIsOverflow, Toast.LENGTH_LONG).show();
                break;
        }
        return true;
    }

    @Override
    public boolean onNavigationItemSelected(MenuItem item) {

        switch (item.getItemId()) {
            case R.id.chat:
                Intent goToChat = new Intent(TestToolbar.this, ChatRoomActivity.class);
                startActivity(goToChat);
                break;
            case R.id.weather:
                Intent goToWeather = new Intent(TestToolbar.this, WeatherForecast.class);
                startActivity(goToWeather);
                break;
            case R.id.home:
                setResult(500);
                finish();
                break;
        }
        return true;
    }
}