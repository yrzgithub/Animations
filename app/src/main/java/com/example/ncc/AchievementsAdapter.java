package com.example.ncc;

import android.app.Activity;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.chaquo.python.PyObject;
import com.squareup.picasso.Picasso;

import java.util.Collections;
import java.util.List;
import java.util.Map;

public class AchievementsAdapter extends BaseAdapter {

    Activity act;
    List<PyObject> list;

    AchievementsAdapter(Activity act, Map<PyObject,PyObject> map)
    {
        this.act = act;
        list = map.get("achievements").asList();
        list.remove(null);
        Collections.shuffle(list);
    }

    @Override
    public int getCount() {
        return list.size();
    }

    @Override
    public Object getItem(int i) {
        return null;
    }

    @Override
    public long getItemId(int i) {
        return 0;
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        view = act.getLayoutInflater().inflate(R.layout.acheivements_list_view,null);

        Map<PyObject,PyObject> details = list.get(i).asMap();

        String url,name,year,dept,medals,achievement;

        url = details.get("image").toString();
        name = details.get("name").toString();
        year = details.get("year").toString();
        dept = details.get("dept").toString();
        medals = details.get("medals").toString();
        achievement = details.get("achievement").toString();

        Log.e("sanjay","URL SANJAY : " + url);

        ImageView img = view.findViewById(R.id.image);
        Glide.with(img).load(url).into(img);

        TextView name_textview = view.findViewById(R.id.name);
        TextView year_textview = view.findViewById(R.id.year);
        TextView achievement_textview = view.findViewById(R.id.achievement);
        TextView dept_view = view.findViewById(R.id.dept);
        TextView medals_view = view.findViewById(R.id.medals);


        name_textview.setText(name);
        year_textview.setText(year);
        achievement_textview.setText(achievement);
        dept_view.setText(dept);
        medals_view.setText(medals);

        Animation animation = AnimationUtils.loadAnimation(act,R.anim.scale);

        /*
        name_textview.setAnimation(animation);
        year_textview.setAnimation(animation);
        achievement_textview.setAnimation(animation);
        dept_view.setAnimation(animation);
        medals_view.setAnimation(animation);
         */

        img.setAnimation(animation);

        return view;
    }
}
