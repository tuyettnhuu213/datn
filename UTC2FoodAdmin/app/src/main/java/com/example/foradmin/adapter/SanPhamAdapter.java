package com.example.foradmin.adapter;

import android.app.Activity;
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
import android.widget.Toast;
import android.content.DialogInterface;
import androidx.appcompat.app.AlertDialog;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import com.example.foradmin.R;
import com.example.foradmin.Retrofit2.APIUtils;

import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import com.example.foradmin.activity.ChiTietSanPhamActivity;
import com.example.foradmin.activity.QuanLiThucDonActivity;
import com.example.foradmin.model.sanpham;
public class SanPhamAdapter extends BaseAdapter {
    Context context;
    ArrayList<sanpham> arrayList;
    Activity activity;


    public SanPhamAdapter(Context context, ArrayList<sanpham> arrayList, Activity activity) {
        this.context = context;
        this.arrayList = arrayList;
        this.activity = activity;
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
        public TextView txtten, txtgia, txttinhtrang, txtloaisp;
        public ImageView imv;
        public Button btnSua,btnXoa;
    }
    @Override
    public View getView(int position, View view, ViewGroup parent) {
        ViewHoler viewHoler;
        if (view == null)
        {
            viewHoler = new ViewHoler();
            LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            view = inflater.inflate(R.layout.item_sanpham,null);
            viewHoler.imv = view.findViewById(R.id.imvHaSp);
            viewHoler.txtten= view.findViewById(R.id.txtTenSP);
            viewHoler.txtgia= view.findViewById(R.id.txtGiaSP);
            viewHoler.txttinhtrang= view.findViewById(R.id.txtTinhTrang);
            viewHoler.txtloaisp= view.findViewById(R.id.txtLoai);
            viewHoler.btnSua = view.findViewById(R.id.btnSuaSp);
            viewHoler.btnXoa = view.findViewById(R.id.btnXoaSp);
            view.setTag(viewHoler);
        } else {
            viewHoler = (ViewHoler) view.getTag();
        }
        sanpham sp = (sanpham) getItem(position);
        viewHoler.txtten.setText(sp.getTensp());
        viewHoler.txtgia.setText("Giá: "+sp.getGia()+"đ");
        if(sp.getTinhtrang() == 1) viewHoler.txttinhtrang.setText("Còn hàng"); else viewHoler.txttinhtrang.setText("Hết hàng");
        for (int i=0;i<QuanLiThucDonActivity.arrayList.size();i++){
            if(sp.getIdloai()==QuanLiThucDonActivity.arrayList.get(i).getIdloaisp())
            {
                viewHoler.txtloaisp.setText(QuanLiThucDonActivity.arrayList.get(i).getTenloaisp());
                break;
            }
        }
        Picasso.get().load(APIUtils.Base_Url+sp.getHASP())
                .placeholder(R.drawable.loading)
                .error(R.drawable.warning)
                .into(viewHoler.imv);
        viewHoler.btnSua.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(context, ChiTietSanPhamActivity.class);
                intent.putExtra("idsp",sp.getIdsp());
                intent.putExtra("hasp",sp.getHASP());
                intent.putExtra("tensp",sp.getTensp());
                intent.putExtra("giasp",sp.getGia());
                intent.putExtra("tt",sp.getTinhtrang());
                intent.putExtra("idloai",sp.getIdloai());
                context.startActivity(intent);
            }
        });
        viewHoler.btnXoa.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialog.Builder b = new AlertDialog.Builder(context);
                b.setTitle("Thông báo");
                b.setMessage("Bạn có muốn xóa sản phẩm");
                b.setPositiveButton("Có", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        RequestQueue queue = Volley.newRequestQueue(context);
                        String url = APIUtils.deletesp+"?IdSP="+sp.getIdsp();
                        StringRequest stringRequest = new StringRequest(Request.Method.GET, url, new Response.Listener<String>() {
                            @Override
                            public void onResponse(String response) {
                                if(response.equals("success")){
                                    Toast.makeText(context,"Thành công",Toast.LENGTH_LONG).show();
                                    arrayList.remove(sp);
                                    notifyDataSetChanged();
                                    dialog.cancel();
                                }
                                else{
                                    Toast.makeText(context,"Thất bại, sản phẩm đang được sử dụng! ",Toast.LENGTH_LONG).show();

                                }
                            }
                        }, new Response.ErrorListener() {
                            @Override
                            public void onErrorResponse(VolleyError error) {

                            }
                        }); queue.add(stringRequest);
                    }
                });
                b.setNegativeButton("Không", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.cancel();
                    }
                });
                AlertDialog al = b.create();
                al.show();
            }
        });
        return view;
    }
}
