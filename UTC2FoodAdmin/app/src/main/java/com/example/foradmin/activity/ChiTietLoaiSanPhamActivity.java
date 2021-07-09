package com.example.foradmin.activity;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.foradmin.R;
import com.example.foradmin.Retrofit2.APIUtils;
import com.example.foradmin.Retrofit2.DataClient;
import com.example.foradmin.model.loaisanpham;
import com.squareup.picasso.Picasso;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.InputStream;

import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ChiTietLoaiSanPhamActivity extends AppCompatActivity {
    Button btnLuu, btnHuy;
    EditText txtTen;
    ImageView imv;
    int Request_Code_Image = 123, IdLoaiSP = 0;
    String real_path = "",image="",ten="";
    loaisanpham loai = new loaisanpham();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chi_tiet_loai_san_pham);
        addViews();
        loai = (loaisanpham) getIntent().getSerializableExtra("idloaisp");
        if(loai!=null)
        {
            IdLoaiSP = loai.getIdloaisp();
            Picasso.get().load(APIUtils.Base_Url+loai.getHaloaisp())
                    .placeholder(R.drawable.loading)
                    .error(R.drawable.warning)
                    .into(imv);
            txtTen.setText(loai.getTenloaisp());
            eventImage();
            eventbtnLuu();
        } else Toast.makeText(getApplicationContext(),"Không nhận được dữ liệu. Hãy thử lại",Toast.LENGTH_SHORT).show();

    }
    private void sendrequest(String ha){
        RequestQueue queue = Volley.newRequestQueue(getApplicationContext());
        String url = APIUtils.updateloaisp+"?IdLoaiSP="+IdLoaiSP+"&HAnhLoaiSP="+ha+"&TenLoaiSP='"+txtTen.getText().toString()+"'";
        Log.e("image",url);
        StringRequest stringRequest = new StringRequest(Request.Method.GET, url, new com.android.volley.Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                Log.e("image",response);
                if(response.equals("success1") || response.equals("success2")){
                    for (int i=0;i<QuanLiThucDonActivity.arrayList.size();i++)
                    {
                        if (QuanLiThucDonActivity.arrayList.get(i).getIdloaisp() == IdLoaiSP){
                            QuanLiThucDonActivity.arrayList.set(2,loai);
                        }
                    }
                    Toast.makeText(getApplicationContext(),"Thành công",Toast.LENGTH_LONG).show();
                    finish();
                    Intent intent = new Intent(getApplicationContext(), QuanLiThucDonActivity.class);
                    startActivity(intent);
                }
                else{
                    Toast.makeText(getApplicationContext(),"Thất bại",Toast.LENGTH_LONG).show();
                    finish();
                    Intent intent = new Intent(getApplicationContext(), QuanLiThucDonActivity.class);
                    startActivity(intent);

                }
            }
        }, new com.android.volley.Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

            }
        }); queue.add(stringRequest);
    }
    private void eventbtnLuu() {
        btnLuu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ten = txtTen.getText().toString();
                if(ten.length() > 0 ){
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
                            public void onResponse(Call<String> call, Response<String> response) {
                                if (response !=null){
                                    String message = response.body();
                                    if (message.length() > 0 ){
                                        image ="image/"+message;
                                        sendrequest(image);
                                    }
                                }
                            }

                            @Override
                            public void onFailure(Call<String> call, Throwable t) {
                            }
                        });
                    } else {
                        String ha="";
                        sendrequest(ha);
                    }

                } else
                {
                    Toast.makeText(getApplicationContext(),"Hãy nhập tên loại sản phẩm",Toast.LENGTH_LONG).show();
                }


            }
        });
        btnHuy.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
                Intent intent = new Intent(getApplicationContext(), QuanLiThucDonActivity.class);
                startActivity(intent);
            }
        });
    }

    private void eventImage() {
        imv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                checkPermistion();
                Intent intent = new Intent(Intent.ACTION_PICK);
                intent.setType("image/*");
                startActivityForResult(intent,Request_Code_Image);
            }
        });
    }
    private void checkPermistion() {
        if (ContextCompat.checkSelfPermission(ChiTietLoaiSanPhamActivity.this,
                Manifest.permission.READ_EXTERNAL_STORAGE)
                != PackageManager.PERMISSION_GRANTED) {
            if (ActivityCompat.shouldShowRequestPermissionRationale(ChiTietLoaiSanPhamActivity.this,
                    Manifest.permission.READ_EXTERNAL_STORAGE)) {

            } else {
                ActivityCompat.requestPermissions(ChiTietLoaiSanPhamActivity.this,
                        new String[]{Manifest.permission.READ_EXTERNAL_STORAGE},
                        1);
            }
        }
    }
    @Override
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

    private void addViews() {
        btnLuu = findViewById(R.id.btnLuuCT);
        btnHuy = findViewById(R.id.btnHuyCT);
        txtTen = findViewById(R.id.txtTenLoaiSPCT);
        imv = findViewById(R.id.imvLoaiSPCT);
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
}