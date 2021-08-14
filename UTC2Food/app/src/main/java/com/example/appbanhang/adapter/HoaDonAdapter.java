package com.example.appbanhang.adapter;

import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.appbanhang.R;
import com.example.appbanhang.activity.ChiTietHoaDonActivity;
import com.example.appbanhang.model.ChiTietHoaDon;
import com.example.appbanhang.model.HoaDon;
import com.example.appbanhang.model.sanpham;
import com.squareup.picasso.Picasso;

import java.text.DecimalFormat;
import java.time.LocalDateTime;
import java.util.ArrayList;

public class HoaDonAdapter extends BaseAdapter {
    Context context;

    public HoaDonAdapter(Context context, ArrayList<HoaDon> arrayHoaDon) {
        this.context = context;
        this.arrayHoaDon = arrayHoaDon;
    }

    ArrayList<HoaDon> arrayHoaDon;
    @Override
    public int getCount() {
        return arrayHoaDon.size() ;
    }

    @Override
    public Object getItem(int position) {
        return arrayHoaDon.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }
    public class ViewHolder{
        public TextView txtId,txtTien,txtThoiGian,txtTinhtrangHD;
        public Button btnMua;
    }
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
       ViewHolder viewHolder = null;
        if(convertView == null)
        {
            viewHolder = new ViewHolder();
            LayoutInflater layoutInflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = layoutInflater.inflate(R.layout.dong_hoadon,null);
            viewHolder.txtId = (TextView) convertView.findViewById(R.id.txtIdHoaDon);
            viewHolder.txtThoiGian =(TextView) convertView.findViewById(R.id.txtThoiGianHD);
            viewHolder.txtTien=(TextView) convertView.findViewById(R.id.txtTongTienHD);
            viewHolder.btnMua = convertView.findViewById(R.id.btnXem);
            viewHolder.txtTinhtrangHD = convertView.findViewById(R.id.txttinhtrangHD);
            convertView.setTag(viewHolder);
        }
        else {
            viewHolder =  (ViewHolder) convertView.getTag();

        }
        HoaDon hd = (HoaDon) getItem(position);
        viewHolder.txtId.setText("MÃ HÓA ĐƠN: "+hd.getIdHD());
        DecimalFormat decimalFormat = new DecimalFormat("###,###,###");
        viewHolder.txtThoiGian.setText("Thời gian: "+hd.getThoiGian());
        viewHolder.txtTien.setText("$"+decimalFormat.format(hd.getTongTien())+"đ");
        if(hd.getTinhtrang() == 0 )
            viewHolder.txtTinhtrangHD.setText("Chờ xác nhận");
        else if(hd.getTinhtrang() == 1 )
        {
            viewHolder.txtTinhtrangHD.setText("Chờ đóng gói");
        }
        else if(hd.getTinhtrang() == 2 )
        {
            viewHolder.txtTinhtrangHD.setText("Chờ giao hàng");
        }
        else    {
            viewHolder.txtTinhtrangHD.setText("Đã giao");
        }
            viewHolder.btnMua.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(context, ChiTietHoaDonActivity.class);
                intent.putExtra("idhd", hd.getIdHD());
                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                context.startActivity(intent);
            }
        });
        return convertView;
    }
}
