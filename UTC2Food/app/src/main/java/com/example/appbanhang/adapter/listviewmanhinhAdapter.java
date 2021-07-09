package com.example.appbanhang.adapter;
import com.example.appbanhang.R;
import com.example.appbanhang.model.listviewmanhinh;
import com.example.appbanhang.model.sanpham;
import com.example.appbanhang.util.server;
import com.squareup.picasso.Picasso;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.text.DecimalFormat;
import java.util.ArrayList;

public class listviewmanhinhAdapter extends BaseAdapter {
    ArrayList<listviewmanhinh> arrayList;

    public listviewmanhinhAdapter(ArrayList<listviewmanhinh> arrayList, Context context) {
        this.arrayList = arrayList;
        this.context = context;
    }

    Context context;
    @Override
    public int getCount() {
        return arrayList.size();
    }

    @Override
    public Object getItem(int position) {
        return arrayList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }
    public class ViewHolder{
        public ImageView imageView;
        public TextView textView;
    }
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder viewHolder = null;
        if(convertView == null)
        {
            viewHolder = new ViewHolder();
            LayoutInflater layoutInflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = layoutInflater.inflate(R.layout.dong_listview,null);
            viewHolder.textView = (TextView) convertView.findViewById(R.id.textview);
            viewHolder.imageView = (ImageView) convertView.findViewById(R.id.image);
            convertView.setTag(viewHolder);
        }
        else {
            viewHolder = (ViewHolder) convertView.getTag();

        }
        listviewmanhinh sp = (listviewmanhinh ) getItem(position);
        viewHolder.textView.setText(sp.getTxt());
        if (sp.getImv()==null)
        {
            viewHolder.imageView.setImageResource(R.drawable.nosignal);
        } else {
            viewHolder.imageView.setImageResource(sp.getImv());
        }
        return convertView;
    }
}
