package com.yingwo.yingwo;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.text.method.HideReturnsTransformationMethod;
import android.text.method.PasswordTransformationMethod;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by FJS0420 on 2016/7/10.
 */

public class RegisterActivity2 extends AppCompatActivity {

    @BindView(R.id.edit_passwd) EditText edit_passwd;
    @BindView(R.id.edit_code) EditText edit_code;
    @BindView(R.id.btn_lookpd) Button btn_lookpd;
    @BindView(R.id.btn_resend) Button btn_resend;
    @BindView(R.id.tv_phone) TextView tv_phone;
    @BindView(R.id.toolbar) Toolbar toolbar;
    @BindView(R.id.btn_done_reg) Button btn_done_reg;
    private boolean isvisible = false;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register2);
        ButterKnife.bind(this);
        Intent intent = this.getIntent();
        String phone = intent.getStringExtra("phone");
        tv_phone.setText(phone);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }

    @OnClick(R.id.btn_lookpd)
    public void lookpd(Button btn_lookpd){
        if(!isvisible){
            edit_passwd.setTransformationMethod(HideReturnsTransformationMethod.getInstance());
            isvisible = true;
            btn_lookpd.setBackgroundResource(R.mipmap.eye_close);
        }else{
            edit_passwd.setTransformationMethod(PasswordTransformationMethod.getInstance());
            isvisible = false;
            btn_lookpd.setBackgroundResource(R.mipmap.eye_open);
        }
    }

    @OnClick(R.id.btn_done_reg)
    public void done(Button btn_done_reg){
        startActivity(new Intent(this,MakeupinfoActivity.class));
    }
}
