package com.example.appbanhang.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import com.example.appbanhang.R;
import com.example.appbanhang.model.ChiTietHoaDon;
import com.example.appbanhang.model.sanpham;
import com.example.appbanhang.util.server;
import com.squareup.picasso.Picasso;

import java.text.DecimalFormat;
import java.util.ArrayList;

public class CTHDAdapter extends BaseAdapter {
    Context context;
    ArrayList<ChiTietHoaDon> chiTietHoaDons;

    public CTHDAdapter(Context context, ArrayList<ChiTietHoaDon> chiTietHoaDons) {
        this.context = context;
        this.chiTietHoaDons = chiTietHoaDons;
    }
    public class ViewHolder{
        public TextView txtten,txtgia,txtsl,txtghichu;
        public ImageView imv;

    }
    @Override
    public int getCount() {
        return chiTietHoaDons.size();
    }

    @Override
    public Object getItem(int position) {
        return chiTietHoaDons.get(position);
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
            convertView = layoutInflater.inflate(R.layout.dong_chitiethoadon,null);
            viewHolder.imv = convertView.findViewById(R.id.imvdongcthd);
            viewHolder.txtten =(TextView) convertView.findViewById(R.id.txtTenDongCTHD);
            viewHolder.txtgia =(TextView) convertView.findViewById(R.id.txtGiaDongCTHD);
            viewHolder.txtsl =(TextView) convertView.findViewById(R.id.txtSLDongCTHD);
            viewHolder.txtghichu =(TextView) convertView.findViewById(R.id.txtGhiChuDongCTHD);
            convertView.setTag(viewHolder);
        }
        else {
            viewHolder =  (ViewHolder) convertView.getTag();

        }
        ChiTietHoaDon sp = (ChiTietHoaDon) getItem(position);
        viewHolder.txtten.setText(sp.getTensp());
        viewHolder.txtghichu.setText("Ghi chú: "+sp.getGhichu());
        viewHolder.txtsl.setText("Số lượng: "+sp.getSL());
        DecimalFormat decimalFormat = new DecimalFormat("###,###,###");
        viewHolder.txtgia.setText("$"+decimalFormat.format(sp.getGia())+"đ");
        if (sp.getHa().length()==0)
        {
            viewHolder.imv.setImageResource(R.drawable.nosignal);
        } else {
            Picasso.get().load(server.localhost+sp.getHa()).placeholder(R.drawable.loading).error(R.drawable.nosignal).into(viewHolder.imv);
        }
        return convertView;
    }

}
