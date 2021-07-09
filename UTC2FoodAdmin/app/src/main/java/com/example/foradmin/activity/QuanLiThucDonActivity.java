package com.example.foradmin.activity;
import androidx.appcompat.widget.Toolbar;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.core.view.GravityCompat;

import android.Manifest;
import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.Dialog;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.ColorDrawable;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
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

import com.example.foradmin.adapter.ItemMenuAdapter;
import com.example.foradmin.adapter.LoaiSPAdapter;
import com.example.foradmin.model.ItemMenu;
import com.example.foradmin.model.loaisanpham;


public class QuanLiThucDonActivity extends AppCompatActivity {
    ListView lvLoaiSanPham;
    public static ArrayList<loaisanpham> arrayList;
    public static LoaiSPAdapter loaiSPAdapter;
    Button btnadd,save,cancel;
    ImageView imv;
    Toolbar toolbarMain;
    EditText edtten;
    int Request_Code_Image = 123;
    String real_path = "",ten = "" , ha = "";
    ArrayList<ItemMenu> arrayListItemMenu;
    ItemMenuAdapter itemMenuAdapter;
    Button btnHetHang,btnConHang;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_quan_li_thuc_don);
        APIUtils.verifyStoragePermissions(this);
        addView();
        loadData();
        eventItemselected();
        eventBtnAdd();
        eventBtnStt();

    }

    private void eventBtnStt() {
        btnHetHang.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                eventChangeAllStatus(0);
            }
        });
        btnConHang.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                eventChangeAllStatus(1);
            }
        });
    }

    public void eventChangeAllStatus(int stt){
        RequestQueue requestQueue = Volley.newRequestQueue(getApplicationContext());
        String link = APIUtils.Base_Url + "setallstatusproduct.php?tinhtrang="+stt;
        Log.e("TAG",link);
        StringRequest stringRequest = new StringRequest(Request.Method.GET, link, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                Log.e("TAG",response);
                if(response.trim().equals("success")){
                    Toast.makeText(getApplicationContext(),"Thành công",Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(getApplicationContext(),"Thất bại. Hãy thử lại",Toast.LENGTH_SHORT).show();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

            }
        });
        requestQueue.add(stringRequest);
    }
    public void eventItemselected(){
        lvLoaiSanPham.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                view.setSelected(true);
                Intent intent = new Intent(getApplicationContext(),SanPhamActivity.class);
                intent.putExtra("idloaisp",arrayList.get(position).getIdloaisp());
                startActivity(intent);
            }
        });
    }

    @SuppressLint("ResourceAsColor")
    private void opendialogadd(int gravity){
        final Dialog dialog = new Dialog(this);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setContentView(R.layout.custom_add_loaisp_dialog);

        Window window = dialog.getWindow();
        if(window == null ){
            return;
        }
        window.setLayout(WindowManager.LayoutParams.WRAP_CONTENT,WindowManager.LayoutParams.WRAP_CONTENT);
        window.setBackgroundDrawable(new ColorDrawable(R.color.transfer));
        WindowManager.LayoutParams windowAttributes = window.getAttributes();
        windowAttributes.gravity = gravity;
        window.setAttributes(windowAttributes);
        if(Gravity.BOTTOM == gravity){
            dialog.setCancelable(true);
        } else {
            dialog.setCancelable(false);
        }
        imv = dialog.findViewById(R.id.imvDialogLoaiSP);
        save = dialog.findViewById(R.id.btnDialogLuuCT);
        cancel = dialog.findViewById(R.id.btnDiaLogHuyCT);
        edtten = dialog.findViewById(R.id.txtDialogTenLoaiSP);
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
                ten = edtten.getText().toString();
                if(ten.length()>0){
                    if(real_path.length()>0) {
                        File file = new File(real_path);
                        String file_path = file.getAbsolutePath();
                        String[] arrayfilename = file_path.split("\\.");
                        file_path = arrayfilename[0] + System.currentTimeMillis() + "." + arrayfilename[1];
                        RequestBody requestBody = RequestBody.create(MediaType.parse("multipart/form-data"), file);
                        MultipartBody.Part body = MultipartBody.Part.createFormData("uploaded_file", file_path, requestBody);
                        DataClient dataClient = APIUtils.getData();
                        retrofit2.Call<String> callback = dataClient.uploadphoto(body);
                        callback.enqueue(new Callback<String>() {
                            @Override
                            public void onResponse(Call<String> call, retrofit2.Response<String> response) {
                                Log.e("message",response.toString());
                                if (response != null) {
                                    String message = response.body();
                                    if (message.length() > 0) {
                                        DataClient InsertData = APIUtils.getData();
                                        String image = "image/" + message;
                                        if (image.length() > 0) {
                                            retrofit2.Call<String> callback = InsertData.InsertLoaiSanPham(image, ten);
                                            callback.enqueue(new Callback<String>() {
                                                @Override
                                                public void onResponse(Call<String> call, retrofit2.Response<String> response) {
                                                    String result = response.body();
                                                    if (result.equals("success")) {
                                                        Toast.makeText(getApplicationContext(), "Lưu thành công", Toast.LENGTH_LONG).show();
                                                        loadData();
                                                        dialog.dismiss();
                                                    }
                                                }

                                                @Override
                                                public void onFailure(Call<String> call, Throwable t) {
                                                }
                                            });
                                        } else {
                                            Toast.makeText(getApplicationContext(), "Hãy chọn hình ảnh", Toast.LENGTH_SHORT).show();
                                        }

                                    }
                                }
                            }

                            @Override
                            public void onFailure(Call<String> call, Throwable t) {
                                Log.e("error",t.getMessage().toString());
                                Toast.makeText(getApplicationContext(),"Mất kết nối giây lát. Hãy thử lại",Toast.LENGTH_SHORT).show();
                            }
                        });
                    } else {
                        Toast.makeText(getApplicationContext(),"Hãy chọn hình ảnh",Toast.LENGTH_SHORT).show();
                    }
                } else {
                    Toast.makeText(getApplicationContext(),"Hãy nhập tên loại sản phẩm",Toast.LENGTH_LONG).show();
                }
            }
        });

        dialog.show();
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
    private void eventBtnAdd() {
        btnadd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                opendialogadd(Gravity.CENTER);
            }
        });
    }

    private void loadData() {
        arrayList.clear();
        loaiSPAdapter.notifyDataSetChanged();;
        RequestQueue requestQueue = Volley.newRequestQueue(getApplicationContext());
        String link = APIUtils.getloaisp;
        StringRequest stringRequest = new StringRequest(Request.Method.GET, link, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                int id=0;
                String ten="",imv="";
                ArrayList<loaisanpham> arrayCTHD = new ArrayList<>();
                if (response != null && response.length() != 2) {
                    try {
                        JSONArray jsonArray = new JSONArray(response);
                        for (int i = 0; i < jsonArray.length(); i++) {
                            JSONObject jsonObject = jsonArray.getJSONObject(i);
                            id = jsonObject.getInt("IdLoaiSP");
                            imv = jsonObject.getString("HAnhLoaiSP");
                            ten = jsonObject.getString("TenLoaiSP");
                            if(id!=0)
                            {
                                arrayList.add(new loaisanpham(id,i,imv,ten) );
                                loaiSPAdapter.notifyDataSetChanged();
                            }

                        }

                    } catch (JSONException e) {

                    }
                }

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

            }
        });
        requestQueue.add(stringRequest);

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


    private void addView() {
        btnadd =  findViewById(R.id.btnThemLoaiSP);
        lvLoaiSanPham = findViewById(R.id.lvLoaiSanPham);
        arrayList = new ArrayList<>();
        loaiSPAdapter = new LoaiSPAdapter(QuanLiThucDonActivity.this,arrayList,this);
        lvLoaiSanPham.setAdapter(loaiSPAdapter);
        btnHetHang = findViewById(R.id.btnOver);
        btnConHang = findViewById(R.id.btnAvailable);
    }
}