package com.yingwo.yingwo;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by FJS0420 on 2016/7/13.
 */

public class LoginActivity extends AppCompatActivity{

    @BindView(R.id.tv_register)
    TextView tv_register;
    @BindView(R.id.tv_forgetpd)
    TextView tv_forgetpd;
    @BindView(R.id.edit_phone)
    EditText edit_phone;
    @BindView(R.id.edit_passwd)
    EditText edit_passwd;
    @BindView(R.id.btn_login)
    Button btn_login;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        ButterKnife.bind(this);
        init();

    }


    public void init(){
        Intent intent = this.getIntent();
        String phone = intent.getStringExtra("phone");
        edit_phone.setText(phone);
        tv_forgetpd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getApplicationContext(),ResetPasswdActivity1.class));
            }
        });
        tv_register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getApplicationContext(),RegisterActivity1.class));
            }
        });
    }
    @OnClick(R.id.btn_login)
    public void login(){
        startActivity(new Intent(this,HomePageActivity.class));
    }

}
