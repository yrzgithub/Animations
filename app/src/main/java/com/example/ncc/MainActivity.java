package com.example.ncc;

import static java.lang.Thread.sleep;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.BaseAdapter;
import android.widget.ListView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.drawerlayout.widget.DrawerLayout;

import com.chaquo.python.PyObject;
import com.chaquo.python.Python;
import com.google.android.material.navigation.NavigationView;

import java.util.Map;


public class MainActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {

    ActionBarDrawerToggle toggle = null;
    DrawerLayout drawer_layout = null;
    Python main = null;
    ListView list = null;
    Animation animation = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        NavigationView navigation_view = findViewById(R.id.navigation_view);
        navigation_view.setNavigationItemSelectedListener(this);

        drawer_layout = findViewById(R.id.drawer);
        toggle = new ActionBarDrawerToggle(MainActivity.this,drawer_layout,R.string.open,R.string.close);
        drawer_layout.addDrawerListener(toggle);
        toggle.syncState();
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        main = Python.getInstance();
        Map<PyObject,PyObject> dict = null;

        //dict = main.getModule("main").callAttr("home").asMap();

        try {
            dict = main.getModule("main").callAttr("home").asMap();
            Log.e("sanjay",dict.toString());
        }

        catch (Exception e)
        {

        }

        list = findViewById(R.id.home_list);
        list.setAdapter(new HomeAdapter(MainActivity.this,dict));

        animation = AnimationUtils.loadAnimation(this,R.anim.property_animator);
        list.startAnimation(animation);
    }

    @Override
    protected void onRestart() {
        animation = AnimationUtils.loadAnimation(this,R.anim.scale);
        list.startAnimation(animation);
        super.onRestart();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        Log.e("sanjay","ID"+item.getItemId());
        if(toggle.onOptionsItemSelected(item)) return true;
        return super.onOptionsItemSelected(item);
    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId())
        {
            case R.id.Login:
                Intent intent = new Intent(MainActivity.this,LoginActivity.class);
                startActivity(intent);
                break;

            case R.id.achievements:
                intent = new Intent(MainActivity.this,AchievementsAct.class);
                startActivity(intent);
                break;

            case R.id.Logout:
                break;
        }

        drawer_layout.close();
        return false;
    }
}