package com.example.anle.luutruonline;

import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.GridView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.anle.luutruonline.CustomAdapter.GridViewAlbumAdapter;
import com.example.anle.luutruonline.DataOnline.Api;
import com.example.anle.luutruonline.DataOnline.ThucHienYeuCauMang;
import com.example.anle.luutruonline.Model.Album;
import com.example.anle.luutruonline.Model.Anh;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.zip.Inflater;

public class MainActivity extends AppCompatActivity implements ThucHienYeuCauMang.LayDSAlbum,ThucHienYeuCauMang.ThemAlbum {

    private static String TENTK;
    private TextView tv_hoten;
    private HashMap<String,String> params;
    public static ProcessDialog loadDSAlbum;
    private GridView grv_danhsachalbum;
    private GridViewAlbumAdapter adapter_dsAlbum;
    private List<Album> listAlbum;
    private static LinearLayout ln_chuacoAlbum;

    private LinearLayout ln_themalbum;
    private Dialog dialogThemAlbum;
    private View view;
    private EditText edt_tenalbum;
    private Button btn_oceAddAlbum;
    public static ProcessDialog loadThemAlbum;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        init();
        getTentk();
        setUpGrvDanhSachAlbum();
        layDSAlbum();
        setUpDialogThemAlbum();
    }

    public void getTentk(){
        Intent intent=getIntent();
        Bundle bundle=intent.getExtras();
        TENTK=bundle.getString("Tentk");
        tv_hoten.setText(bundle.getString("Hoten"));
    }

    public void init(){
        tv_hoten=findViewById(R.id.tv_tencuaban);
        listAlbum=new ArrayList<>();
        grv_danhsachalbum=findViewById(R.id.grv_danhsachalbum);
        loadDSAlbum=new ProcessDialog(this,"Đang tải danh sách Album ảnh");
        loadThemAlbum=new ProcessDialog(this,"Đang tạo Album mới...");
        ln_chuacoAlbum=findViewById(R.id.ln_chuacoAlbum);
        ln_themalbum=findViewById(R.id.ln_themalbum);
        ln_themalbum.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialogThemAlbum.show();
            }
        });
    }

    public void setUpDialogThemAlbum(){
        dialogThemAlbum=new Dialog(this);
        view=LayoutInflater.from(this).inflate(R.layout.tenalbum,(ViewGroup)findViewById(R.id.ln_dialogThemAlbum),false);
        dialogThemAlbum.setContentView(view);
        dialogThemAlbum.setCanceledOnTouchOutside(true);
        dialogThemAlbum.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));

        edt_tenalbum=view.findViewById(R.id.edt_tenalbum);
        btn_oceAddAlbum=view.findViewById(R.id.btn_oceAddAlbum);

        btn_oceAddAlbum.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                themAlbum();
            }
        });
    }

    public void themAlbum(){
        params=new HashMap<>();
        params.put("Tenalbum",edt_tenalbum.getText().toString().trim());
        params.put("Tentk",TENTK);
        ThucHienYeuCauMang request=new ThucHienYeuCauMang(Api.URL_THEM_ALBUM,Api.actionThemAlbum,params,2811,getApplicationContext(),this);
        request.execute();
    }

    @Override
    public void reloadDSAlbum() {
        dialogThemAlbum.cancel();
    }

    public void layDSAlbum(){
        params=new HashMap<>();
        params.put("Tentk",TENTK);
        ThucHienYeuCauMang request=new ThucHienYeuCauMang(Api.URL_LAY_DSALBUM,Api.actionLayDsAlbum,params,2811,getApplicationContext(),this);
        request.execute();
    }

    public void setUpGrvDanhSachAlbum(){
        adapter_dsAlbum=new GridViewAlbumAdapter(this,R.layout.gridview_album,listAlbum);
        grv_danhsachalbum.setAdapter(adapter_dsAlbum);
    }

    @Override
    public void kiemtraDS(JSONArray dsAlbum) {
        if (dsAlbum.length()>0){
            listAlbum.clear();
            for (int k=0;k<dsAlbum.length();k++){
                try{
                    List<Anh> anhList=new ArrayList<>();
                    JSONObject object=dsAlbum.getJSONObject(k);
                    for (int i=0;k<object.getJSONArray("Anh").length();i++){
                        anhList.add(new Anh(object.getJSONArray("Anh").getJSONObject(i).getInt("Id"),
                                object.getJSONArray("Anh").getJSONObject(i).getString("Url")));
                    }
                    listAlbum.add(new Album(object.getInt("Id"),object.getString("Tenalbum"),anhList));
                }catch (JSONException e){
                    e.printStackTrace();
                }
            }
            adapter_dsAlbum.notifyDataSetChanged();
            ln_chuacoAlbum.setVisibility(View.GONE);
        }else {
            ln_chuacoAlbum.setVisibility(View.VISIBLE);
        }
    }
}
