package com.example.foradmin.adapter;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.TextView;

import com.example.foradmin.R;
import com.example.foradmin.model.hoadon;

import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.TimeZone;

public class HDAdapter extends BaseAdapter {
    Context context;
    ArrayList<hoadon> arrayList;

    public HDAdapter(Context context, ArrayList<hoadon> arrayList) {
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
        return arrayList.get(position).getIdhd();
    }

    public class ViewHolder {
        public TextView TenKH, DiaChi, SDT, MaHD, TinhTrang, TongTien, ThoiGianDat, ThoiGianNhan, PhuongThucThanhToan,TinhTrangThanhToan ;
        public Button btnHoaDon;
//        public ListView lvChiTietHoaDon;
//        public ChiTietHoaDonAdapter chiTietHoaDonAdapter;
//        public ArrayList<chitiethoadon> arrayCTDH;

    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
     ViewHolder viewHolder;
        if (convertView == null) {
            viewHolder = new ViewHolder();
            LayoutInflater layoutInflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = layoutInflater.inflate(R.layout.item_hd, null);
            viewHolder.MaHD = (TextView) convertView.findViewById(R.id.txtIdHD);
            viewHolder.TenKH = convertView.findViewById(R.id.txtTenKHHD);
            viewHolder.DiaChi = convertView.findViewById(R.id.txtDiaChiHD);
            viewHolder.SDT = convertView.findViewById(R.id.txtSDTHD);
            viewHolder.ThoiGianDat = (TextView) convertView.findViewById(R.id.txtThoiGianDatHD);
            viewHolder.ThoiGianNhan = (TextView) convertView.findViewById(R.id.txtThoiGianNhanHD);
            viewHolder.PhuongThucThanhToan=(TextView) convertView.findViewById(R.id.txtPTThanhToanHD);
            viewHolder.TinhTrangThanhToan = (TextView) convertView.findViewById(R.id.txtTTThanhToanHD);
            viewHolder.TongTien = (TextView) convertView.findViewById(R.id.txtTongTienHD);viewHolder.TinhTrang = convertView.findViewById(R.id.txtTinhTrangHD);
            convertView.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolder) convertView.getTag();

        }
        hoadon hd = (hoadon) getItem(position);
        int IdHD = hd.getIdhd();

        viewHolder.MaHD.setText(" MÃ HÓA ĐƠN: " + IdHD);
        viewHolder.TenKH.setText("Tên: "+hd.getTenkh() + "");
        viewHolder.DiaChi.setText("Địa chỉ: "+hd.getDiachi() + "");
        viewHolder.SDT.setText("Số điện thoại: "+hd.getSdt() + "");
        DecimalFormat decimalFormat = new DecimalFormat("###,###,###");
       // int namhientai = hd.getNgay().getYear()+1900;
        if(hd.getTtthanhtoan()==0)
        {
            viewHolder.TinhTrangThanhToan.setText("Chưa thanh toán");
        } else {
            viewHolder.TinhTrangThanhToan.setText("Đã thanh toán");
        }
        viewHolder.PhuongThucThanhToan.setText(hd.getPttt()+"");
        Date date = hd.getNgay();
        SimpleDateFormat formatter = new SimpleDateFormat("dd/MM/yyyy");
        formatter.setTimeZone(TimeZone.getTimeZone("Etc/UTC"));
        String strDate= formatter.format(date);
        viewHolder.ThoiGianDat.setText("Thời gian đặt: "+strDate +" lúc "+ hd.getTgdat());
        if(hd.getTgnhan().equals("00:00:00"))
        {
            viewHolder.ThoiGianNhan.setText("Chưa giao");
        } else {
            viewHolder.ThoiGianNhan.setText("Thời gian nhận:" +strDate+" lúc "+hd.getTgnhan());
        }

        viewHolder.TongTien.setText(decimalFormat.format(hd.getTongtien())+"đ");
        if (hd.getTthoadon() == 0)
            viewHolder.TinhTrang.setText("Chờ xác nhận");
        else if (hd.getTthoadon() == 1) {
            viewHolder.TinhTrang.setText("Chờ đóng gói");
        } else if (hd.getTthoadon() == 2) {
            viewHolder.TinhTrang.setText("Chờ giao hàng");
        } else {
            viewHolder.TinhTrang.setText("Đã giao hàng"); }
        return convertView;
    }
}
