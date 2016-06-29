package com.example.jurgenhaan.project4;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;
import java.util.Iterator;

import java.util.List;

public class MyListAdapter extends ArrayAdapter<String[]> {
    int groupid;
    List<String[]> items;
    Context context;
    String path;

    public MyListAdapter(Context context, int vg, int id, List<String[]> items){
        super(context,vg, id, items);
        this.context=context;
        groupid=vg;
        this.items=items;


    }
    static class ViewHolder {
        public TextView textid;
        public TextView textname;

    }

    public View getView(int position, View convertView, ViewGroup parent) {

        View rowView = convertView;
        if(rowView==null){
            LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            rowView= inflater.inflate(groupid, parent, false);
            ViewHolder viewHolder = new ViewHolder();
            viewHolder.textid = (TextView) rowView.findViewById(R.id.txtid);
            viewHolder.textname = (TextView) rowView.findViewById(R.id.txtname);
            rowView.setTag(viewHolder);
        }
        // Fill data
        ViewHolder holder = (ViewHolder) rowView.getTag();
        String[] row=items.get(position);
        holder.textid.setText(row[7]);
        holder.textname.setText(row[9]);



        return rowView;
    }

}