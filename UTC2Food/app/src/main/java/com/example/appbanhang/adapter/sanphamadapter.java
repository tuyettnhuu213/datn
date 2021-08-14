package com.example.appbanhang.adapter;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.appbanhang.R;
import com.example.appbanhang.activity.ChiTietSanPham;
import com.example.appbanhang.model.sanpham;
import com.example.appbanhang.util.server;
import com.squareup.picasso.Picasso;

import java.text.DecimalFormat;
import java.util.ArrayList;
import com.example.appbanhang.util.checkconnection;

import de.hdodenhof.circleimageview.CircleImageView;

public class sanphamadapter extends RecyclerView.Adapter<sanphamadapter.ItemHolder> {
    Context context;

    public sanphamadapter(Context context, ArrayList<sanpham> mangsp) {
        this.context = context;
        this.mangsp = mangsp;
    }

    ArrayList<sanpham> mangsp;
    @NonNull
    @Override
    public ItemHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.dong_spmoinhat,null);
        ItemHolder itemHolder = new ItemHolder(view);
        return itemHolder;
    }

    @SuppressLint("ResourceAsColor")
    @Override
    public void onBindViewHolder(@NonNull ItemHolder holder, int position) {
        sanpham sp = mangsp.get(position);
        holder.txttensp.setText(sp.getTensp());
        DecimalFormat decimalFormat = new DecimalFormat("###,###,###");
        holder.txtgiasp.setText("$"+decimalFormat.format(sp.getGiasp())+"đ");
        if(sp.isTinhtrang() == true ){
            holder.txttinhtrang.setTextColor(R.color.blue);
            holder.txttinhtrang.setText("Còn hàng");
            }
        else
        {
            holder.txttinhtrang.setTextColor(R.color.red);
            holder.txttinhtrang.setText("Tạm hết");
        }
        if (sp.getHinhanhsp().length()==0)
        {
            holder.imageView.setImageResource(R.drawable.nosignal);
        } else {
            Picasso.get().load(server.localhost+sp.getHinhanhsp()).placeholder(R.drawable.loading).error(R.drawable.nosignal).into(holder.imageView);
        }

    }

    @Override
    public int getItemCount() {
        return mangsp.size();
    }

    public class ItemHolder extends RecyclerView.ViewHolder{
        public ImageView imageView;
        public TextView txttensp,txtgiasp,txttinhtrang;

        public ItemHolder(@NonNull View itemView) {
            super(itemView);
            imageView = itemView.findViewById(R.id.imvsp);
            txttensp =  (TextView) itemView.findViewById(R.id.txttensp);
            txtgiasp = (TextView) itemView.findViewById(R.id.txtgiasp);
            txttinhtrang = (TextView) itemView.findViewById(R.id.txttinhtrang);
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent(context, ChiTietSanPham.class);
                    intent.putExtra("thongtinsp",mangsp.get( getPosition()));
                    intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                    context.startActivity(intent);

                }
            });

        }
    }
}
