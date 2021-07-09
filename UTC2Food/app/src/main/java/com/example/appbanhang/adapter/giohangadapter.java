package com.example.appbanhang.adapter;
import android.annotation.SuppressLint;
import android.content.Context;
import android.content.DialogInterface;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import androidx.appcompat.app.AlertDialog;
import com.example.appbanhang.R;
import com.example.appbanhang.activity.MainActivity;
import com.example.appbanhang.activity.SanPhamActivity;
import com.example.appbanhang.model.giohang;
import com.example.appbanhang.util.server;
import com.squareup.picasso.Picasso;
import com.example.appbanhang.util.checkconnection;
import java.text.DecimalFormat;
import java.util.ArrayList;
import static com.example.appbanhang.activity.GioHang.evenutil;

public class giohangadapter  extends BaseAdapter {
    public giohangadapter(Context context, ArrayList<giohang> arraygiohang) {
        this.context = context;
        this.arraygiohang = arraygiohang;
    }

    Context context;
    ArrayList<giohang> arraygiohang;
    @Override
    public int getCount() {
        return arraygiohang.size();
    }

    @Override
    public Object getItem(int position) {
        return arraygiohang.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }
    public class ViewHolder{
        public TextView txtxtengiohang,txtgiagiohang,txttinhtrang;
        public ImageView imvgiohang;
        public Button btnTru,btnCong,btnDelete;
        public EditText edtSoluong,edtGhiChuGio;


    }
    @SuppressLint("ResourceAsColor")
    @Override
    public View getView(int position, View view, ViewGroup parent) {
        ViewHolder viewHolder;
        if(view == null)
        {
            viewHolder = new ViewHolder();
            LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            view = inflater.inflate(R.layout.dong_giohang,null);
            viewHolder.txtxtengiohang = (TextView) view.findViewById(R.id.txtTenDongGioHang);
            viewHolder.txtgiagiohang = (TextView) view.findViewById(R.id.txtGiaDongGioHang);
            viewHolder.btnCong = (Button) view.findViewById(R.id.btnCong);
            viewHolder.btnTru = (Button) view.findViewById(R.id.btnTru);
            viewHolder.edtSoluong = (EditText) view.findViewById(R.id.edtSoLuongGio);
            viewHolder.edtGhiChuGio = (EditText) view.findViewById(R.id.edtGhiChuGioHang);
            viewHolder.txttinhtrang = (TextView) view.findViewById(R.id.txtTTDongGioHang);
            viewHolder.imvgiohang = view.findViewById(R.id.imvdonggiohang);
            viewHolder.btnDelete = (Button) view.findViewById(R.id.btnDelete);
            view.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolder) view.getTag();
        }
        giohang Giohang = (giohang) getItem(position);
        viewHolder.txtxtengiohang.setText(Giohang.getTen());
        DecimalFormat decimalFormat = new DecimalFormat("###,###,###");
        viewHolder.txtgiagiohang.setText("$"+decimalFormat.format(Giohang.getGia())+"đ");
        viewHolder.edtGhiChuGio.setText(Giohang.getGhichu());
        viewHolder.edtSoluong.setText(Giohang.getSl()+"");
        Picasso.get().load(server.localhost+Giohang.getHinhanh()).placeholder(R.drawable.loading).error(R.drawable.nosignal).into(viewHolder.imvgiohang);
        if(Giohang.getTinhtrangg())
            viewHolder.txttinhtrang.setText("Còn hàng");
        else {
            viewHolder.txttinhtrang.setText("Hết hàng");
            viewHolder.txttinhtrang.setTextColor(R.color.red);
            viewHolder.edtSoluong.setEnabled(false);
            viewHolder.edtSoluong.setTextColor(R.color.gray);
        }
        int sl = Integer.parseInt(viewHolder.edtSoluong.getText().toString());
        if(sl>=20)
        {
            viewHolder.edtSoluong.setText("20");
            viewHolder.btnCong.setVisibility(View.INVISIBLE);
            evenutil();
        } else{
            viewHolder.btnCong.setVisibility(View.VISIBLE);
            viewHolder.btnTru.setVisibility(View.VISIBLE);
        }
        viewHolder.btnCong.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int slmoi = Integer.parseInt(viewHolder.edtSoluong.getText().toString())+1;
                int slht = MainActivity.manggiohang.get(position).getSl();
                int giaht = MainActivity.manggiohang.get(position).getGia();
                MainActivity.manggiohang.get(position).setSl(slmoi);
                int giamoi = (giaht*slmoi)/slht;
                MainActivity.manggiohang.get(position).setGia(giamoi);
                DecimalFormat decimalFormat = new DecimalFormat("###,###,###");
                viewHolder.txtgiagiohang.setText("Giá: "+decimalFormat.format(giamoi)+"VND");
                evenutil();
                if(slmoi>=20)
                {
                   checkconnection.showToast_short(context,"Số lượng tối đa là 20");
                    viewHolder.btnCong.setVisibility(View.INVISIBLE);
                    viewHolder.edtSoluong.setText("20");
                    evenutil();
                } else if(slmoi>0) {
                    viewHolder.btnCong.setVisibility(View.VISIBLE);
                    viewHolder.btnTru.setVisibility(View.VISIBLE);
                    viewHolder.edtSoluong.setText(slmoi+"");

                }
                MainActivity.reloaiSL();
                SanPhamActivity.reloaiSL();
            }
        });
        viewHolder.btnTru.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int slmoi = Integer.parseInt(viewHolder.edtSoluong.getText().toString()) - 1;
                if (slmoi == 0) {
                    viewHolder.btnTru.setVisibility(View.INVISIBLE);
                    viewHolder.edtSoluong.setText("1");
                } else {
                    viewHolder.btnTru.setVisibility(View.VISIBLE);
                    viewHolder.btnCong.setVisibility(View.VISIBLE);
                    int slht = MainActivity.manggiohang.get(position).getSl();
                    int giaht = MainActivity.manggiohang.get(position).getGia();
                    MainActivity.manggiohang.get(position).setSl(slmoi);
                    int giamoi = (giaht * slmoi) / slht;
                    MainActivity.manggiohang.get(position).setGia(giamoi);
                    DecimalFormat decimalFormat = new DecimalFormat("###,###,###");
                    viewHolder.txtgiagiohang.setText("Giá: " + decimalFormat.format(giamoi) + "VND");
                    evenutil();
                    viewHolder.edtSoluong.setText(slmoi + "");
                }
                try{
                    MainActivity.reloaiSL();
                    SanPhamActivity.reloaiSL();
                }catch (Exception e){

                }


            }
        });
        viewHolder.btnDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialog.Builder builder = new AlertDialog.Builder(context);
                builder.setTitle("Xác nhận");
                builder.setMessage("Bạn có muốn xóa sản phẩm?");
                builder.setPositiveButton("Có", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        MainActivity.manggiohang.remove(position);
                        notifyDataSetChanged();
                        evenutil();
                        MainActivity.reloaiSL();
                        SanPhamActivity.reloaiSL();
                    }
                });
                builder.setNegativeButton("Không", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                    }
                });
                AlertDialog alert = builder.create();
                alert.show();
            }
        });
        return view;
    }
}
