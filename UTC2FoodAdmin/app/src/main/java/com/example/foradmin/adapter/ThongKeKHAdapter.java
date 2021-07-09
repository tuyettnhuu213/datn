package com.example.foradmin.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.foradmin.R;
import com.example.foradmin.Retrofit2.APIUtils;
import com.example.foradmin.model.thongkekh;
import com.example.foradmin.model.thongkesp;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

public class ThongKeKHAdapter extends BaseAdapter {
    Context context;
    ArrayList<thongkekh> arrayList;

    public ThongKeKHAdapter(Context context, ArrayList<thongkekh> arrayList) {
        this.context = context;
        this.arrayList = arrayList;
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

    @Override
    public View getView(int position, View view, ViewGroup parent) {
       ViewHoler viewHoler;
        if (view == null)
        {
            viewHoler = new ViewHoler();
            LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            view = inflater.inflate(R.layout.item_thongkekhachhang,null);
            viewHoler.mssv = view.findViewById(R.id.MSSV);
            viewHoler.ten= view.findViewById(R.id.TenKH);
            viewHoler.sodon= view.findViewById(R.id.SoDonMua);
            viewHoler.sotien= view.findViewById(R.id.SoTienDaMua);
            viewHoler.dtl= view.findViewById(R.id.DTL);
            view.setTag(viewHoler);
        } else {
            viewHoler = (ViewHoler) view.getTag();
        }
        thongkekh sp = (thongkekh) getItem(position);
        viewHoler.ten.setText("Tên: "+sp.getTenKH());
        viewHoler.sotien.setText("Số tiền đã mua: "+sp.getSoTien());
        viewHoler.sodon.setText("Số đơn đã mua: "+sp.getSoDonMua());
        viewHoler.dtl.setText("Điểm tích lũy: "+sp.getDTL());
        viewHoler.mssv.setText("Tài khoản: "+sp.getMSSV());
        return view;
    }
    public class ViewHoler{
        public TextView mssv,ten,sodon,sotien,dtl;
    }
}
