package com.example.foradmin.Retrofit2;

import okhttp3.MultipartBody;
import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.Multipart;
import retrofit2.http.POST;
import retrofit2.http.Part;

public interface DataClient {
    @Multipart
    @POST("uploadimages.php")
    Call<String> uploadphoto(@Part MultipartBody.Part photo);

    @FormUrlEncoded
    @POST("insertloaisp.php")
    Call<String> InsertLoaiSanPham(@Field("HAnhLoaiSP") String HAnhLoaiSP
                            ,@Field("TenLoaiSP") String TenLoaiSP);

    @FormUrlEncoded
    @POST("insertsp.php")
    Call<String> InsertSanPham(@Field("HAnhSP") String HAnhLoaiSP
            ,@Field("TenSP") String TenLoaiSP,@Field("GiaSP") Integer GiaSP,@Field("TTrangSP") Integer TTrangSP,@Field("IdLoaiSP") Integer IdLoaiSP);

}
