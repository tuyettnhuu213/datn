package com.example.appbanhang.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.appbanhang.R;
import com.example.appbanhang.model.sanpham;
import com.example.appbanhang.util.server;
import com.squareup.picasso.Picasso;

import java.text.DecimalFormat;
import java.util.ArrayList;

public class ProductAdapter  extends BaseAdapter {
    Context context;
    ArrayList<sanpham> arrayList;

    public ProductAdapter(Context context, ArrayList<sanpham> arrayList) {
        this.context = context;
        this.arrayList = arrayList;
    }

    @Override
    public int getCount() {
        return arrayList.size();
    }
    public class ViewHolder{
        public TextView txtten,txtgia,txttt;
        public ImageView imv;
    }
    @Override
    public Object getItem(int position) {
        return arrayList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
     ViewHolder viewHolder = null;
        if(convertView == null)
        {
            viewHolder = new ViewHolder();
            LayoutInflater layoutInflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = layoutInflater.inflate(R.layout.item_product,null);
            viewHolder.txtten = (TextView) convertView.findViewById(R.id.txtnameproduct);
            viewHolder.txtgia =(TextView) convertView.findViewById(R.id.txtpriceproduct);
            viewHolder.txttt =(TextView) convertView.findViewById(R.id.txtstatusproduct);
            viewHolder.imv =  convertView.findViewById(R.id.imvproduct);
            convertView.setTag(viewHolder);
        }
        else {
            viewHolder =  (ViewHolder) convertView.getTag();

        }
        sanpham sp = (sanpham) getItem(position);
        viewHolder.txtten.setText(sp.getTensp());
        DecimalFormat decimalFormat = new DecimalFormat("###,###,###");
        viewHolder.txtgia.setText("$"+decimalFormat.format(sp.getGiasp())+"đ");
        if(sp.isTinhtrang() == true )
            viewHolder.txttt.setText("Còn hàng");
        else
            viewHolder.txttt.setText("Tạm hết");
        if (sp.getHinhanhsp().length()==0)
        {
            viewHolder.imv.setImageResource(R.drawable.nosignal);
        } else {
            Picasso.get().load(server.localhost+sp.getHinhanhsp()).placeholder(R.drawable.loading).error(R.drawable.nosignal).into(viewHolder.imv);
        }
        return convertView;
    }
}
