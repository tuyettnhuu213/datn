package com.example.foradmin.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.foradmin.R;
import com.example.foradmin.Retrofit2.APIUtils;
import com.example.foradmin.activity.QuanLiThucDonActivity;
import com.example.foradmin.model.sanpham;
import com.example.foradmin.model.thongkesp;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

public class ThongKeSPAdapter  extends BaseAdapter {
    Context context;
    ArrayList<thongkesp> arrayList;

    public ThongKeSPAdapter(Context context, ArrayList<thongkesp> arrayList) {
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

    public class ViewHoler{
        public TextView txtten, txtgia, txtsoluongban, txtloaisp;
        public ImageView imv;
    }
    @Override
    public View getView(int position, View view, ViewGroup parent) {
        ViewHoler viewHoler;
        if (view == null)
        {
            viewHoler = new ViewHoler();
            LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            view = inflater.inflate(R.layout.item_thongkesp,null);
            viewHoler.imv = view.findViewById(R.id.HA);
            viewHoler.txtten= view.findViewById(R.id.TenSP);
            viewHoler.txtgia= view.findViewById(R.id.GiaSP);
            viewHoler.txtsoluongban= view.findViewById(R.id.TongSoLuongBan);
            viewHoler.txtloaisp= view.findViewById(R.id.LoaiSP);
            view.setTag(viewHoler);
        } else {
            viewHoler = (ViewHoler) view.getTag();
        }
        thongkesp sp = (thongkesp) getItem(position);
        viewHoler.txtten.setText(sp.getTensp());
        viewHoler.txtgia.setText("Giá: "+sp.getGia()+"đ");
        viewHoler.txtsoluongban.setText("Số lượng đã bán: "+sp.getTongsoluongban());
        viewHoler.txtloaisp.setText("Loại sản phẩm: "+sp.getTenloai());
        Picasso.get().load(APIUtils.Base_Url+sp.getHa())
                .placeholder(R.drawable.loading)
                .error(R.drawable.warning)
                .into(viewHoler.imv);
        return view;
    }
}
