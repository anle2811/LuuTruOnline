package com.example.anle.luutruonline.DataOnline;

import android.content.Context;
import android.net.Uri;
import android.os.AsyncTask;
import android.util.Log;
import android.widget.Toast;

import com.example.anle.luutruonline.DangKyActivity;
import com.example.anle.luutruonline.LoginActivity;
import com.example.anle.luutruonline.MainActivity;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.lang.ref.WeakReference;
import java.util.HashMap;

public class ThucHienYeuCauMang extends AsyncTask<Void,Void,String> {


    private WeakReference<Object> Callback;
    private String Url;
    private String Action;
    private Context Context;
    private HashMap<String,String> Params;
    private int RequestCode;

    public ThucHienYeuCauMang(String Url,String Action,HashMap<String,String> Params,int RequestCode,Context context,Object object){
        this.Url=Url;
        this.Action=Action;
        this.Params=Params;
        this.RequestCode=RequestCode;
        this.Context=context;
        this.Callback=new WeakReference<>(object);
    }

    public interface ThucHienTaoTK{
         void taotaikhoan();
         void dendangnhap();
    }

    public interface DNThanhCong{
        void sauDangNhap(JSONObject tttaikhoan);
    }

    public interface LayDSAlbum{
        void kiemtraDS(JSONArray dsAlbum);
    }

    public interface ThemAlbum{
        void reloadDSAlbum();
    }

    @Override
    protected void onPreExecute() { //Được gọi đầu tiên khi tiến trình kích hoạt
        super.onPreExecute();
        switch (this.Action){
            case Api.actionKttk:{
                DangKyActivity.load_dktk.show();
            }break;
            case Api.actionDangNhap:
                LoginActivity.loadDangNhap.show();
                break;
            case Api.actionLayDsAlbum:
                MainActivity.loadDSAlbum.show();
                break;
            case Api.actionThemAlbum:
                MainActivity.loadThemAlbum.show();
                break;
        }
    }

    @Override
    protected void onPostExecute(String s) {
        super.onPostExecute(s);
        if (s==""){
            switch (this.Action){
                case Api.actionKttk:
                    DangKyActivity.load_dktk.cancel();
                    Toast.makeText(Context,"Lỗi kết nối!",Toast.LENGTH_SHORT).show();
                    break;
                case Api.actionDktk:
                    DangKyActivity.load_dktk.cancel();
                    Toast.makeText(Context,"Lỗi kết nối!",Toast.LENGTH_SHORT).show();
                    break;
                case Api.actionDangNhap:
                    LoginActivity.loadDangNhap.cancel();
                    Toast.makeText(Context,"Lỗi kết nối!",Toast.LENGTH_SHORT).show();
                    break;
                case Api.actionLayDsAlbum:
                    MainActivity.loadDSAlbum.cancel();
                    Toast.makeText(Context,"Lỗi kết nối!",Toast.LENGTH_SHORT).show();
                    break;
                case Api.actionThemAlbum:
                    MainActivity.loadThemAlbum.cancel();
                    Toast.makeText(Context,"Lỗi kết nối!",Toast.LENGTH_SHORT).show();
                    break;
            }
        }else {
            try {
                JSONObject jsonObject=new JSONObject(s);
                switch (this.Action){
                    case Api.actionKttk:{
                        if (!jsonObject.getBoolean("error")){ // Lấy ra giá trị của key 'error' trong chuỗi json. Nếu giá trị khác true, mean: value=false thì:
                            final ThucHienTaoTK callBack=(ThucHienTaoTK) Callback.get();
                            callBack.taotaikhoan();
                        }else {
                            DangKyActivity.load_dktk.cancel();
                            Toast.makeText(Context,"Tên tài khoản đã được sử dụng!",Toast.LENGTH_SHORT).show();
                        }
                    }break;

                    case Api.actionDktk:{
                        DangKyActivity.load_dktk.cancel();
                        if (!jsonObject.getBoolean("error")){ // Lấy ra giá trị của key 'error' trong chuỗi json. Nếu giá trị khác true, mean: value=false thì:
                            final ThucHienTaoTK callBack=(ThucHienTaoTK) Callback.get();
                            callBack.dendangnhap();
                        }else {
                            Toast.makeText(Context,jsonObject.getString("message"),Toast.LENGTH_SHORT).show();
                        }
                    }break;

                    case Api.actionDangNhap:{
                        LoginActivity.loadDangNhap.cancel();
                        if (!jsonObject.getBoolean("error")) {
                            final DNThanhCong callback = (DNThanhCong) Callback.get();
                            callback.sauDangNhap(jsonObject.getJSONObject("tttaikhoan"));
                        }else {
                            Toast.makeText(Context,"Tài khoản hoặc mật khẩu không hợp lệ!",Toast.LENGTH_SHORT).show();
                        }
                    }break;

                    case Api.actionLayDsAlbum:{
                        MainActivity.loadDSAlbum.cancel();
                        if (!jsonObject.getBoolean("error")){
                            final LayDSAlbum callback=(LayDSAlbum) Callback.get();
                            callback.kiemtraDS(jsonObject.getJSONArray("albums"));
                        }else {
                            Toast.makeText(Context,jsonObject.getString("message"),Toast.LENGTH_SHORT).show();
                        }
                    }break;

                    case Api.actionThemAlbum:{
                        MainActivity.loadThemAlbum.cancel();
                        if (!jsonObject.getBoolean("error")){
                            final ThemAlbum callback=(ThemAlbum) Callback.get();
                            callback.reloadDSAlbum();
                        }else {
                            if (jsonObject.getInt("errorCode")==11){
                                Toast.makeText(Context,jsonObject.getString("message"),Toast.LENGTH_SHORT).show();
                            }else {
                                Toast.makeText(Context,jsonObject.getString("message"),Toast.LENGTH_SHORT).show();
                            }
                        }
                    }break;

                }
            }catch (JSONException e){
                e.printStackTrace();
            }
        }
    }

    @Override
    protected String doInBackground(Void... voids) {
        XuLyYeuCauUrl xuLyYeuCauUrl=new XuLyYeuCauUrl();
        if (RequestCode==2811){
            return xuLyYeuCauUrl.sendPostRequest(Url,Params);
        }
        return null;
    }
}
