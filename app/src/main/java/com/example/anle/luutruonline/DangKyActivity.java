package com.example.anle.luutruonline;

import android.content.DialogInterface;
import android.content.Intent;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.anle.luutruonline.DataOnline.Api;
import com.example.anle.luutruonline.DataOnline.ThucHienYeuCauMang;

import java.util.HashMap;

public class DangKyActivity extends AppCompatActivity implements ThucHienYeuCauMang.ThucHienTaoTK {

    private static final int REQUEST_CODE=2811;

    private AlertDialog.Builder thongbaothieu;
    private EditText edt_dkten;
    private EditText edt_dktk;
    private EditText edt_dkmk;
    private Button btn_dangky;
    private TextView tv_quaylaidn;

    public static ProcessDialog load_dktk;

    private HashMap<String,String> params;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dang_ky);
        mapWidget();
        init();
        initDialogTBthieu();
    }

    public void mapWidget(){
        edt_dkten=findViewById(R.id.input_hoten);
        edt_dktk=findViewById(R.id.input_dktk);
        edt_dkmk=findViewById(R.id.input_dkmk);
        btn_dangky=findViewById(R.id.btn_signup);
        tv_quaylaidn=findViewById(R.id.link_login);
    }

    public void init(){
        load_dktk=new ProcessDialog(this,"Đang tạo tài khoản...");
        btn_dangky.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (kiemTraDieuKienDu()==0) {
                    taotk();
                }
            }
        });
        tv_quaylaidn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(DangKyActivity.this,LoginActivity.class));
            }
        });
    }

    public int kiemTraDieuKienDu(){
        int check=0;
        String noidungthieu="";
        if (edt_dkten.getText().toString().trim().isEmpty()){
            noidungthieu=noidungthieu+"[Chưa nhập họ tên]"+"\n";
            check=1;
        }
        if (edt_dktk.getText().toString().trim().isEmpty()){
            noidungthieu=noidungthieu+"[Chưa nhập tên tài khoản]"+"\n";
            check=1;
        }
        if (edt_dkmk.getText().toString().trim().isEmpty()){
            noidungthieu=noidungthieu+"[Chưa nhập mật khẩu]"+"\n";
            check=1;
        }
        if (check!=0){
            thongbaothieu.setMessage(noidungthieu);
            AlertDialog thongbao=thongbaothieu.create();
            thongbao.show();
        }
        return check;
    }

    public void initDialogTBthieu(){
        thongbaothieu=new AlertDialog.Builder(this);
        thongbaothieu.setTitle("Không thể tiếp tục!");
        thongbaothieu.setPositiveButton("Đóng", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {

            }
        });
    }
    public void taotk(){
        params=new HashMap<>();
        params.put("Tentk",edt_dktk.getText().toString().trim());
        ThucHienYeuCauMang request=new ThucHienYeuCauMang(Api.URL_KTTK,Api.actionKttk,params,REQUEST_CODE,getApplicationContext(),this);
        request.execute();
    }

    @Override
    public void taotaikhoan() {
        params=new HashMap<>();
        params.put("Tentk",edt_dktk.getText().toString().trim());
        params.put("Matkhau",edt_dkmk.getText().toString().trim());
        params.put("Hoten",edt_dkten.getText().toString().trim());
        ThucHienYeuCauMang request=new ThucHienYeuCauMang(Api.URL_DKTK,Api.actionDktk,params,REQUEST_CODE,getApplicationContext(),this);
        request.execute();
    }

    @Override
    public void dendangnhap() {
        Toast.makeText(DangKyActivity.this,"Tạo tài khoản thành công",Toast.LENGTH_SHORT).show();
        Intent intent=new Intent(DangKyActivity.this,LoginActivity.class);
        Bundle bundle=new Bundle();
        bundle.putString("Tentk",params.get("Tentk"));
        bundle.putString("Matkhau",params.get("Matkhau"));
        intent.putExtras(bundle);
        startActivity(intent);
        finish();
    }
}
