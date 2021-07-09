package com.example.appbanhang.adapter;
import com.example.appbanhang.activity.SanPhamActivity;
import com.example.appbanhang.util.server;
import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.appbanhang.R;
import com.example.appbanhang.model.Loaisp;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

public class LoaiSPAdapter extends RecyclerView.Adapter<LoaiSPAdapter.ItemHolder> {
    Context context;
    ArrayList<Loaisp> arrayList;

    public LoaiSPAdapter(Context context, ArrayList<Loaisp> arrayList) {
        this.context = context;
        this.arrayList = arrayList;
    }

    @NonNull
    @Override
    public ItemHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_menu,null);
        ItemHolder itemHolder = new ItemHolder(view);
        return itemHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull ItemHolder holder, int position) {
        Loaisp LoaiSP = arrayList.get(position);
        holder.txtloaisp.setText(LoaiSP.getTenloaisp());
        Picasso.get().load(server.localhost+LoaiSP.getHinhanhloaisp())
                .placeholder(R.drawable.loading)
                .error(R.drawable.nosignal)
                .into(holder.imvloaisp);
        holder.vitri = arrayList.get(position).getId();
    }

    @Override
    public int getItemCount() {
        return arrayList.size();
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    public class ItemHolder extends RecyclerView.ViewHolder {
        public TextView txtloaisp;
        public ImageView imvloaisp;
        public int vitri;
        public ItemHolder(@NonNull View itemView) {
            super(itemView);
            imvloaisp = itemView.findViewById(R.id.imvHALoaiSP);
            txtloaisp = itemView.findViewById(R.id.txtTenLoaiSP);
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent(context, SanPhamActivity.class);
                    intent.putExtra("IdLoaiSP",arrayList.get(getPosition()).getId());
                    intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                   context.startActivity(intent);

                }
            });
        }
    }
}
