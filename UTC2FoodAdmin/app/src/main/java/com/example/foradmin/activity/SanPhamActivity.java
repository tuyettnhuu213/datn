package com.example.foradmin.activity;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import android.annotation.SuppressLint;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.ColorDrawable;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.provider.MediaStore;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.AbsListView;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.foradmin.R;
import android.app.Dialog;
import com.example.foradmin.Retrofit2.APIUtils;
import com.example.foradmin.Retrofit2.DataClient;
import com.example.foradmin.adapter.SanPhamAdapter;
import com.example.foradmin.model.loaisanpham;
import com.example.foradmin.model.sanpham;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.InputStream;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.Callback;

public class SanPhamActivity extends AppCompatActivity {
ListView lvSanPham;
Toolbar toolbar;
ArrayList<sanpham> arrayList;
SanPhamAdapter sanPhamAdapter;
int Request_Code_Image = 123,idloaisp=0;
String real_path = "",ten="";
Button btnadd,save,cancel;
ImageView imv;
EditText edtten,edtgia;
Spinner tinhtrang,loaisp;
View footer;
Boolean isLoading = false, limitdata = false;
mHandler mHandler;
int page =1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_san_pham);
        APIUtils.verifyStoragePermissions(this);
        addViews();
        idloaisp = getIntent().getIntExtra("idloaisp",0);
        actionToolbar();
        APIUtils.verifyStoragePermissions(this);
        getdata(page);
        getmoredate();
        btnadd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(getApplicationContext(),"ok",Toast.LENGTH_LONG).show();
                opendialogadd();
            }
        });
    }
    private void getmoredate() {
        lvSanPham.setOnScrollListener(new AbsListView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(AbsListView view, int scrollState) {

            }

            @Override
            public void onScroll(AbsListView view, int firstItem, int visibleItem, int totalItem) {
                if(firstItem + visibleItem == totalItem && totalItem!=0 && isLoading == false && limitdata == false )
                {
                    isLoading = true;
                    ThreadData threadData = new ThreadData();
                    threadData.start();

                }

            }
        });
    }
    private void getdata(int Page) {
        RequestQueue requestQueue = Volley.newRequestQueue(getApplicationContext());
        String link = APIUtils.getsp+String.valueOf(Page);
        StringRequest stringRequest = new StringRequest(Request.Method.POST, link, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                int id = 0,gia=0,tinhtrang=0;
                String ten="",hacom="";
                Boolean ttcom = true;
                if(response != null && response.length()!=2 )
                {
                    lvSanPham.removeFooterView(footer);
                    try {
                        JSONArray jsonArray = new JSONArray(response);
                        for(int i=0;i<jsonArray.length();i++){
                            JSONObject jsonObject = jsonArray.getJSONObject(i);
                            id = jsonObject.getInt("IdSP");
                            hacom = jsonObject.getString("HAnhSP");
                            ten = jsonObject.getString("TenSP");
                            gia = jsonObject.getInt("GiaSP");
                            tinhtrang = jsonObject.getInt("TTrangSP");
                            if(tinhtrang == 1)
                                ttcom = true;
                            else
                                ttcom = false;
                            arrayList.add(new sanpham(id,gia,tinhtrang,idloaisp,ten,hacom) );
                            sanPhamAdapter.notifyDataSetChanged();
                        }

                    } catch (JSONException e) {

                    }
                } else {
                    limitdata = true;
                    lvSanPham.removeFooterView(footer);
                    Toast.makeText(getApplicationContext(),"Hết dữ liệu",Toast.LENGTH_SHORT).show();
                }

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

            }
        }){
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                HashMap<String,String> param =new HashMap<String, String>();
                param.put("IdLoaiSP",String.valueOf(idloaisp));
                return param;
            }
        };
        requestQueue.add(stringRequest);
    }
    public class mHandler extends Handler {
        @Override
        public void handleMessage(@NonNull Message msg) {
            switch (msg.what){
                case 0:
                    lvSanPham.addFooterView(footer);
                    break;
                case 1:
                    getdata(++page);
                    isLoading = false;
                    break;
            }
            super.handleMessage(msg);
        }
    }
    public class ThreadData extends Thread{
        @Override
        public void run() {
            mHandler.sendEmptyMessage(0);
            try {
                Thread.sleep(3000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            Message message = mHandler.obtainMessage(1);
            mHandler.sendMessage(message);
            super.run();
        }
    }

    private void catchEventSpinner() {
        String[] arraytt = new String[]{"Còn hàng","Hết hàng"};
        ArrayAdapter<String> arrayAdapter = new ArrayAdapter<String>(this,R.layout.support_simple_spinner_dropdown_item,arraytt);
        tinhtrang.setAdapter(arrayAdapter);

        ArrayList<String> arrayloaisp = new ArrayList<>();
        for (int i=0;i<QuanLiThucDonActivity.arrayList.size();i++)
        {
            arrayloaisp.add(QuanLiThucDonActivity.arrayList.get(i).getTenloaisp());
        }
        ArrayAdapter<String> arrayAdapterr = new ArrayAdapter<String>(this,R.layout.support_simple_spinner_dropdown_item,arrayloaisp);
        loaisp.setAdapter(arrayAdapterr);
        for (int i=0;i<QuanLiThucDonActivity.arrayList.size();i++){
            if(QuanLiThucDonActivity.arrayList.get(i).getIdloaisp()==idloaisp){
                loaisp.setSelection(i);
                Log.d("tag",i+"");
                break;
            }

        }
    }
    @SuppressLint("ResourceAsColor")
    private void opendialogadd(){
        final Dialog dialog = new Dialog(this);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setContentView(R.layout.custom_add_sanpham_dialog);
        Window window = dialog.getWindow();
        if(window == null ){
            return;
        }
        window.setLayout(WindowManager.LayoutParams.WRAP_CONTENT,WindowManager.LayoutParams.WRAP_CONTENT);
        window.setBackgroundDrawable(new ColorDrawable(R.color.transfer));
        WindowManager.LayoutParams windowAttributes = window.getAttributes();
        windowAttributes.gravity = Gravity.CENTER;
        window.setAttributes(windowAttributes);
        dialog.setCancelable(false);
        imv = dialog.findViewById(R.id.imvDialogSP);
        save = dialog.findViewById(R.id.btnDialogLuuSP);
        cancel = dialog.findViewById(R.id.btnDiaLogHuySP);
        edtten = dialog.findViewById(R.id.txtTenSPDiaLog);
        edtgia = dialog.findViewById(R.id.txtGiaSPDiaLog);
        tinhtrang = dialog.findViewById(R.id.spinnerTinhTrangDiaLog);
        loaisp = dialog.findViewById(R.id.spinnerLoaiDiaLog);
        catchEventSpinner();
        imv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Intent.ACTION_PICK);
                intent.setType("image/*");
                startActivityForResult(intent,Request_Code_Image);
            }
        });

        cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
            }
        });

        save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(edtten.getText().toString().length()>0 && edtgia.getText().toString().length()>0){
                    int giasp = Integer.parseInt(edtgia.getText().toString()) ;
                    int ttsp = tinhtrang.getSelectedItemPosition();
                    if(real_path.length()>0){
                        File file = new File(real_path);
                        String file_path = file.getAbsolutePath();
                        String[] arrayfilename = file_path.split("\\.");
                        file_path = arrayfilename[0]+System.currentTimeMillis()+"."+arrayfilename[1];
                        RequestBody requestBody = RequestBody.create(MediaType.parse("multipart/form-data"),file);
                        MultipartBody.Part body = MultipartBody.Part.createFormData("uploaded_file",file_path,requestBody);
                        DataClient dataClient = APIUtils.getData();
                        retrofit2.Call<String> callback = dataClient.uploadphoto(body);
                        callback.enqueue(new Callback<String>() {
                            @Override
                            public void onResponse(Call<String> call, retrofit2.Response<String> response) {
                                if (response !=null){
                                    String message = response.body();
                                    if (message.length() > 0 ){
                                        DataClient InsertData = APIUtils.getData();
                                        String image = "image/"+message;
                                        Log.d("name",ten);
                                        retrofit2.Call<String> callback = InsertData.InsertSanPham(image,edtten.getText().toString(),giasp,ttsp,idloaisp);
                                        callback.enqueue(new Callback<String>() {
                                            @Override
                                            public void onResponse(Call<String> call, retrofit2.Response<String> response) {
                                                String result = response.body();
                                                Log.d("AAA",result);
                                                if(result.equals("success")){
                                                    Toast.makeText(getApplicationContext(),"Lưu thành công",Toast.LENGTH_LONG).show();
                                                    sanPhamAdapter.notifyDataSetChanged();
                                                    arrayList.clear();
                                                    getdata(page);
                                                }
                                                dialog.dismiss();
                                            }
                                            @Override
                                            public void onFailure(Call<String> call, Throwable t) {
                                            }
                                        });
                                    }
                                }
                            }

                            @Override
                            public void onFailure(Call<String> call, Throwable t) {

                            }
                        });
                    }else {
                        Toast.makeText(getApplicationContext(),"Hãy chọn hình ảnh sản phẩm",Toast.LENGTH_SHORT).show();
                    }

                } else {
                    Toast.makeText(getApplicationContext(),"Hãy nhập đầy đủ thông tin",Toast.LENGTH_LONG).show();
                }
            }
        });

        dialog.show();
    }

    private void actionToolbar() {
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

    }
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        if(requestCode == Request_Code_Image && resultCode == RESULT_OK && data != null){
            Uri uri = data.getData();
            real_path = getRealPathFromURI(uri);
            try {
                InputStream inputStream = getContentResolver().openInputStream(uri);
                Bitmap bitmap = BitmapFactory.decodeStream(inputStream);
                imv.setImageBitmap(bitmap);
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            }
        }
        super.onActivityResult(requestCode, resultCode, data);
    }
    public String getRealPathFromURI (Uri contentUri) {
        String path = null;
        String[] proj = { MediaStore.MediaColumns.DATA };
        Cursor cursor = getContentResolver().query(contentUri, proj, null, null, null);
        if (cursor.moveToFirst()) {
            int column_index = cursor.getColumnIndexOrThrow(MediaStore.MediaColumns.DATA);
            path = cursor.getString(column_index);
        }
        cursor.close();
        return path;
    }

    private void addViews() {
        btnadd =  findViewById(R.id.btnThemSP);
        lvSanPham = findViewById(R.id.lvSanPham);
        toolbar = findViewById(R.id.toolbarDSSP);
        arrayList = new ArrayList<>();
        sanPhamAdapter = new SanPhamAdapter(SanPhamActivity.this,arrayList,this);
        lvSanPham.setAdapter(sanPhamAdapter);
        LayoutInflater inflater = (LayoutInflater) getSystemService(LAYOUT_INFLATER_SERVICE);
        footer = inflater.inflate(R.layout.processbar,null);
        mHandler = new mHandler();
    }
}