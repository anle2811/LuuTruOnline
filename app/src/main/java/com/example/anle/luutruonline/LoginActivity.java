package com.example.anle.luutruonline;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.anle.luutruonline.DataOnline.Api;
import com.example.anle.luutruonline.DataOnline.ThucHienYeuCauMang;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;

public class LoginActivity extends AppCompatActivity implements ThucHienYeuCauMang.DNThanhCong {

    private TextView tv_dktk;
    private EditText edt_taikhoan;
    private EditText edt_matkhau;
    private Button btn_dangnhap;
    private HashMap<String,String> params;
    public static ProcessDialog loadDangNhap;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        mapWidget();
        setLinkSignUp();
        laytkVuadk();
        btn_dangnhap.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dangnhap();
            }
        });
    }

    public void laytkVuadk(){
        Intent intent=getIntent();
        Bundle bundle=intent.getExtras();
        if (bundle!=null){
            edt_taikhoan.setText(bundle.getString("Tentk"));
            edt_matkhau.setText(bundle.getString("Matkhau"));
        }
    }

    public void mapWidget(){
        tv_dktk=findViewById(R.id.link_signup);
        edt_taikhoan=findViewById(R.id.input_tk);
        edt_matkhau=findViewById(R.id.input_mk);
        btn_dangnhap=findViewById(R.id.btn_login);
        loadDangNhap=new ProcessDialog(this,"Đang kiểm tra...");
    }

    public void setLinkSignUp(){
        tv_dktk.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(LoginActivity.this,DangKyActivity.class));
            }
        });
    }

    public void dangnhap() {
        params = new HashMap<>();
        params.put("Tentk", edt_taikhoan.getText().toString().trim());
        params.put("Matkhau", edt_matkhau.getText().toString().trim());
        ThucHienYeuCauMang request = new ThucHienYeuCauMang(Api.URL_DANGNHAP, Api.actionDangNhap, params, 2811, getApplicationContext(), this);
        request.execute();
    }

    @Override
    public void sauDangNhap(JSONObject tttaikhoan) {
        Intent intent = new Intent(LoginActivity.this, MainActivity.class);
        Bundle bundle = new Bundle();
        bundle.putString("Tentk", edt_taikhoan.getText().toString().trim());
        try {
            bundle.putString("Hoten",tttaikhoan.getString("Hoten"));
        } catch (JSONException e) {
            e.printStackTrace();
        }
        intent.putExtras(bundle);
        startActivity(intent);
        finish();
    }
}
