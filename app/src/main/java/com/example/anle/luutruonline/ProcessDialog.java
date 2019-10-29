package com.example.anle.luutruonline;

import android.app.Dialog;
import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.view.Window;
import android.widget.TextView;

public class ProcessDialog extends Dialog {
    private String trangthai;

    public ProcessDialog(Context context,String trangthai){
        super(context);
        this.trangthai=trangthai;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.process_dialog);
        getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        TextView tv_trangthai=findViewById(R.id.tv_trangthai);
        tv_trangthai.setText(trangthai);
        setCanceledOnTouchOutside(false);
    }
}
