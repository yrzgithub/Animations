package com.example.ncc;

import android.app.Activity;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.BaseAdapter;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.chaquo.python.PyObject;
import com.squareup.picasso.Picasso;

import java.util.Collection;
import java.util.Collections;
import java.util.List;
import java.util.Map;

public class HomeAdapter extends BaseAdapter {

    Map<PyObject,PyObject> map = null;
    Activity act = null;
    String[] urls;
    int size = 0;
    List<PyObject> url_object;

    HomeAdapter(Activity act, Map<PyObject,PyObject> map)
    {
        this.map = map;
        this.act = act;

        if(map!=null)
        {
            Log.e("sanjay",map.toString());
            url_object = map.get(PyObject.fromJava("home")).asList();
            url_object.remove(null);
            size = url_object.size();
            Collections.shuffle(url_object);
        }
    }

    @Override
    public int getCount() {
        return size;
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

        view = act.getLayoutInflater().inflate(R.layout.custom_home,null,true);
        String url = url_object.get(i).toString();
        Log.e("sanjay",url);
        ImageView img = view.findViewById(R.id.img);
        Glide.with(act.getApplicationContext()).load(url).into(img);

        Animation animation = AnimationUtils.loadAnimation(act,R.anim.scale);
        img.setAnimation(animation);

        return view;
    }
}
