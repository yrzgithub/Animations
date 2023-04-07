package com.example.ncc;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ListView;

import com.chaquo.python.PyObject;
import com.chaquo.python.Python;

import java.util.List;
import java.util.Map;

public class AchievementsAct extends AppCompatActivity {

    Python py;
    PyObject main;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_achievements);

        py = Python.getInstance();
        main = py.getModule("main");

        Map<PyObject,PyObject> achievements = main.callAttr("home").asMap();

        ListView listview = findViewById(R.id.achieved_list);
        listview.setAdapter(new AchievementsAdapter(this,achievements));

        Animation animation = AnimationUtils.loadAnimation(this,R.anim.property_animator);
        listview.setAnimation(animation);
    }
}