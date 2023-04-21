package com.example.com3104_group10;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AbsListView;
import android.widget.ArrayAdapter;

import java.util.List;

public class CustomAdapter extends ArrayAdapter<String> {
    private int itemHeight;

    public CustomAdapter(Context context, int resource, List<String> items, int itemHeight) {
        super(context, resource, items);
        this.itemHeight = itemHeight;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View itemView = super.getView(position, convertView, parent);
        AbsListView.LayoutParams layoutParams = new AbsListView.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, itemHeight);
        itemView.setLayoutParams(layoutParams);
        return itemView;
    }
}
