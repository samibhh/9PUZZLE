package com.example.a9puzz;

import android.view.View;
import android.view.ViewGroup;
import android.widget.AbsListView;
import android.widget.BaseAdapter;
import android.widget.Button;

import java.util.ArrayList;

public class CustomAdapter extends BaseAdapter {
    ArrayList<Button> mButtons;
    int mColumnWidth,mColumnHeight;

    public CustomAdapter(ArrayList<Button> mbuttons,int width,int height) {
        mButtons = mbuttons;
        mColumnWidth=width;
        mColumnHeight=height;
    }

    @Override
    public int getCount() {
        return mButtons.size();
    }

    @Override
    public Object getItem(int i) {
        return mButtons.get(i);
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
       Button button;
       if(view==null)
           button=mButtons.get(i);
       else
           button=(Button) view;

       AbsListView.LayoutParams params= new AbsListView.LayoutParams(mColumnWidth,mColumnHeight);
       button.setLayoutParams(params);
       return button;
    }
}
