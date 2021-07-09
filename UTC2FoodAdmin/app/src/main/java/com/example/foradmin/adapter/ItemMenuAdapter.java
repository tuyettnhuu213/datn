package com.example.foradmin.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.foradmin.R;
import com.example.foradmin.model.ItemMenu;

import java.util.ArrayList;

public class ItemMenuAdapter extends BaseAdapter {
    ArrayList arrayList;
    Context context;

    public ItemMenuAdapter(ArrayList arrayList, Context context) {
        this.arrayList = arrayList;
        this.context = context;
    }

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
    public class ViewHoler{
        TextView txtMenu;
        ImageView imvMenu;

    }
    @Override
    public View getView(int position, View view, ViewGroup parent) {
        ViewHoler viewHoler = null;
        if (view == null)
        {
            viewHoler = new ViewHoler();
            LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            view = inflater.inflate(R.layout.item_menu,null);
            viewHoler.imvMenu = view.findViewById(R.id.imvMenu);
            viewHoler.txtMenu= view.findViewById(R.id.txtMenu);
            view.setTag(viewHoler);
        } else {
            viewHoler = (ViewHoler) view.getTag();

        }
        ItemMenu item = (ItemMenu) getItem(position);
        viewHoler.txtMenu.setText(item.getTxtMenu());
        viewHoler.imvMenu.setImageResource(item.getImvMenu());
        return view;
    }
}
