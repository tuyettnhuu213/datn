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
import com.example.foradmin.activity.ChiTietLoaiSanPhamActivity;
import com.example.foradmin.activity.QuanLiThucDonActivity;
import com.example.foradmin.model.loaisanpham;
public class LoaiSPAdapter  extends BaseAdapter {
    Context context;
    ArrayList<loaisanpham> arrayList;
    Activity activity;

    public LoaiSPAdapter(Context context, ArrayList<loaisanpham> arrayList,Activity activity) {
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
    public long getItemId(int id) {
        for(int i=0;i<arrayList.size();i++){
            if(id == QuanLiThucDonActivity.arrayList.get(i).getIdloaisp()){
                return i;
            }
        };
        return 0;
    }

    public class ViewHoler{
        public TextView txtloaisp;
        public ImageView imvloaisp;
        public Button btnSua, btnXoa;
    }
    @Override
    public boolean areAllItemsEnabled()
    {
         return true;
    }
    @Override
    public boolean isEnabled(int arg0)
    {
        return true;
    }
    @Override
    public View getView(int position, View view, ViewGroup parent) {
        ViewHoler viewHoler;
        if (view == null)
        {
            viewHoler = new ViewHoler();
            LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            view = inflater.inflate(R.layout.item_loaisp,null);
            viewHoler.imvloaisp = view.findViewById(R.id.imvHaLoaiSp);
            viewHoler.txtloaisp= view.findViewById(R.id.txtTenLoaiSp);
            viewHoler.btnSua = view.findViewById(R.id.btnSuaLoaiSp);
            viewHoler.btnXoa = view.findViewById(R.id.btnXoaLoaiSp);
            view.setTag(viewHoler);
        } else {
            viewHoler = (ViewHoler) view.getTag();

        }
        loaisanpham LoaiSP = (loaisanpham) getItem(position);
        viewHoler.txtloaisp.setText(LoaiSP.getTenloaisp());
        Picasso.get().load(APIUtils.Base_Url+LoaiSP.getHaloaisp())
                .placeholder(R.drawable.loading)
                .error(R.drawable.warning)
                .into(viewHoler.imvloaisp);
        viewHoler.btnSua.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ((Activity)context).finish();
                Intent intent = new Intent(context, ChiTietLoaiSanPhamActivity.class);
                intent.putExtra("idloaisp",arrayList.get( position));
                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                activity.finish();
                context.startActivity(intent);
            }
        });
        viewHoler.btnXoa.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialog.Builder b = new AlertDialog.Builder(context);
                b.setTitle("Thông báo");
                b.setMessage("Bạn có muốn xóa loại sản phẩm");
                b.setPositiveButton("Có", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        RequestQueue queue = Volley.newRequestQueue(context);
                        String url = APIUtils.deleteloaisp+"?IdLoaiSP="+LoaiSP.getIdloaisp();
                        StringRequest stringRequest = new StringRequest(Request.Method.GET, url, new Response.Listener<String>() {
                            @Override
                            public void onResponse(String response) {
                                if(response.equals("success")){
                                   arrayList.remove(LoaiSP);
                                   Toast.makeText(context,"Thành công",Toast.LENGTH_LONG).show();
                                   notifyDataSetChanged();
                               }
                               else{
                                   Toast.makeText(context,"Thất bại, loại sản phẩm đang được sử dụng! ",Toast.LENGTH_LONG).show();

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
