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
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.foradmin.R;
import com.example.foradmin.Retrofit2.APIUtils;
import com.example.foradmin.Retrofit2.DataClient;
import com.example.foradmin.model.loaisanpham;
import com.example.foradmin.model.sanpham;
import com.squareup.picasso.Picasso;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.InputStream;
import java.util.ArrayList;

import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.Callback;

public class ChiTietSanPhamActivity extends AppCompatActivity {
    Button btnLuu, btnHuy;
    ImageView imv;
    Spinner tinhtrang,loaisp;
    EditText edtten,edtgia;
    int Request_Code_Image = 123, idsp,idloai,tt,gia;
    String real_path = "",ten = "" , ha = "";
    String image="";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chi_tiet_san_pham);
        addViews();
        loaddata();
        eventImage();
        eventbtnLuu();
    }
    private void sendrequest(){
        tt=tinhtrang.getSelectedItemPosition();
        gia = Integer.parseInt(edtgia.getText().toString());
        for (int i=0;i<QuanLiThucDonActivity.arrayList.size();i++)
        {
            if(loaisp.getSelectedItem().toString().equals(QuanLiThucDonActivity.arrayList.get(i).getTenloaisp())){
                idloai = QuanLiThucDonActivity.arrayList.get(i).getIdloaisp();
                break;
            }
        }
        RequestQueue queue = Volley.newRequestQueue(getApplicationContext());
        String url = APIUtils.updatesanpham+"?IdSP="+idsp+"&HAnhSP="+
                image+"&TenSP="+edtten.getText().toString()+"&GiaSP="+gia+"&TTrangSP="+tt+"&IdLoaiSP="+idloai;
        Log.e("url",url);
        StringRequest stringRequest = new StringRequest(Request.Method.GET, url, new com.android.volley.Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                if(response.equals("success") ||response.equals("success2" )){
                    Toast.makeText(getApplicationContext(),"Thành công",Toast.LENGTH_LONG).show();
                    Intent intent = new Intent(getApplicationContext(), SanPhamActivity.class);
                    intent.putExtra("idloaisp",idloai);
                    startActivity(intent);
                    finish();
                }
                else{
                    Toast.makeText(getApplicationContext(),"Thất bại",Toast.LENGTH_LONG).show();
                    finish();
                }
            }
        }, new com.android.volley.Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

            }
        }); queue.add(stringRequest);

    }

    private void eventbtnLuu() {
        btnHuy.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        btnLuu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(edtten.getText().toString().length()>0 && edtgia.getText().toString().length()>0){
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
                                       image = "image/"+message;
                                       sendrequest();
                                   }
                               }
                           }

                           @Override
                           public void onFailure(Call<String> call, Throwable t) {
                           }
                       });
                   } else {
                       sendrequest();
                   }

                }
            }
        });


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

    private void checkPermistion() {
        if (ContextCompat.checkSelfPermission(this,
                Manifest.permission.READ_EXTERNAL_STORAGE)
                != PackageManager.PERMISSION_GRANTED) {
            if (ActivityCompat.shouldShowRequestPermissionRationale(this,
                    Manifest.permission.READ_EXTERNAL_STORAGE)) {

            } else {
                ActivityCompat.requestPermissions(this,
                        new String[]{Manifest.permission.READ_EXTERNAL_STORAGE},
                        1);
            }
        }
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
    private void catchEventSpinner() {
        String[] arraytt = new String[]{"Hết hàng","Còn hàng"};
        ArrayAdapter<String> arrayAdapter = new ArrayAdapter<String>(this,R.layout.support_simple_spinner_dropdown_item,arraytt);
        tinhtrang.setAdapter(arrayAdapter);

        ArrayList<String> arrayloaisp = new ArrayList<>();
        for (int i=0;i<QuanLiThucDonActivity.arrayList.size();i++)
        {
            arrayloaisp.add(QuanLiThucDonActivity.arrayList.get(i).getTenloaisp());
        }
        ArrayAdapter<String> arrayAdapterr = new ArrayAdapter<String>(this,R.layout.support_simple_spinner_dropdown_item,arrayloaisp);
        loaisp.setAdapter(arrayAdapterr);
    }
    private void loaddata() {
        catchEventSpinner();
        idsp = getIntent().getIntExtra("idsp", -1);
        idloai = getIntent().getIntExtra("idloai", -1);
        tt = getIntent().getIntExtra("tt", -1);
        gia = getIntent().getIntExtra("giasp", -1);
        ten = getIntent().getStringExtra("tensp");
        ha = getIntent().getStringExtra("hasp");
        edtten.setText(ten);
        edtgia.setText(gia+"");
        tinhtrang.setSelection(tt);
        for (int i=0;i<QuanLiThucDonActivity.arrayList.size();i++)
        {
            if( idloai == QuanLiThucDonActivity.arrayList.get(i).getIdloaisp()){
                loaisp.setSelection(i);
                break;
            }
        }
        String image = APIUtils.Base_Url+ha;
        Picasso.get().load(image)
                .placeholder(R.drawable.loading)
                .error(R.drawable.warning)
                .into(imv);

    }

    private void addViews() {
        btnLuu = findViewById(R.id.btnLuuCTSP);
        btnHuy = findViewById(R.id.btnHuyCTSP);
        edtten = findViewById(R.id.txtTenCTSP);
        edtgia = findViewById(R.id.txtGiaCTSP);
        imv = findViewById(R.id.imvCTSP);
        tinhtrang = findViewById(R.id.spinnerTinhTrangCTSP);
        loaisp = findViewById(R.id.spinnerLoaiCTSP);
    }
}