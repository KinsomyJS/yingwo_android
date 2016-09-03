package com.yingwo.yingwo;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.text.method.HideReturnsTransformationMethod;
import android.text.method.PasswordTransformationMethod;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.yingwo.yingwo.utils.UserinfoService;
import com.yingwo.yingwo.utils.HttpControl;

import java.io.IOException;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import okhttp3.FormBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import rx.Subscriber;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

/**
 * Created by FJS0420 on 2016/7/10.
 */

public class RegisterActivity2 extends AppCompatActivity {

    @BindView(R.id.edit_passwd)
    EditText edit_passwd;
    @BindView(R.id.edit_code)
    EditText edit_code;
    @BindView(R.id.btn_lookpd)
    Button btn_lookpd;
    @BindView(R.id.btn_resend)
    Button btn_resend;
    @BindView(R.id.tv_phone)
    TextView tv_phone;
    @BindView(R.id.toolbar)
    Toolbar toolbar;
    @BindView(R.id.btn_done_reg)
    Button btn_done_reg;
    private boolean isvisible = false;
    private String phone;
    private String code;
    private String passwd;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register2);
        ButterKnife.bind(this);
        Intent intent = this.getIntent();
        phone = intent.getStringExtra("phone");
//        SmsSend(phone);
        tv_phone.setText(phone);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }

    @OnClick(R.id.btn_lookpd)
    public void lookpd(Button btn_lookpd) {
        if (!isvisible) {
            edit_passwd.setTransformationMethod(HideReturnsTransformationMethod.getInstance());
            isvisible = true;
            btn_lookpd.setBackgroundResource(R.mipmap.eye_close);
        } else {
            edit_passwd.setTransformationMethod(PasswordTransformationMethod.getInstance());
            isvisible = false;
            btn_lookpd.setBackgroundResource(R.mipmap.eye_open);
        }
    }

    @OnClick(R.id.btn_done_reg)
    public void done(Button btn_done_reg) {
        code = edit_code.getText().toString().trim();
        passwd = edit_passwd.getText().toString();
        register(passwd, phone);
    }

    public void SmsSend(String number) {
        Retrofit retrofit = HttpControl.getInstance().getRetrofit();
        UserinfoService api = retrofit.create(UserinfoService.class);
        api.requestVerify(number).enqueue(new Callback<String>() {
            @Override
            public void onResponse(Call<String> call, Response<String> response) {
                if (BuildConfig.DEBUG) Log.d("RegisterActivity2", response.body());
            }

            @Override
            public void onFailure(Call<String> call, Throwable t) {
                Toast.makeText(RegisterActivity2.this, "failure", Toast.LENGTH_SHORT).show();
            }
        });
    }

    public void SmsCheck(String number, String code) {
        OkHttpClient client = HttpControl.getInstance().getClient();
        RequestBody body = new FormBody.Builder()
                .add("mobile", number)
                .add("code", code)
                .build();

        Request request = new Request.Builder()
                .url("http://yw.zhibaizhi.com/yingwophp/api/v1/Sms/check")
                .addHeader("X-Requested-With", "XMLHttpRequest")
                .post(body)
                .build();

        client.newCall(request).enqueue(new okhttp3.Callback() {
            @Override
            public void onFailure(okhttp3.Call call, IOException e) {
                Toast.makeText(RegisterActivity2.this, "failure", Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onResponse(okhttp3.Call call, okhttp3.Response response) throws IOException {
                final String content = response.body().string();
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        Toast.makeText(RegisterActivity2.this, content, Toast.LENGTH_SHORT).show();
                    }
                });
            }
        });

    }

    public void register(final String passwd, String mobile) {
        Retrofit retrofit = HttpControl.getInstance().getRetrofit();
        UserinfoService service = retrofit.create(UserinfoService.class);
        service.register(passwd, mobile)
                .subscribeOn(Schedulers.io())
                .unsubscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Subscriber<String>() {
                    @Override
                    public void onCompleted() {
                        Toast.makeText(RegisterActivity2.this, "注册成功,正在登录", Toast.LENGTH_SHORT).show();
                        login(phone, passwd);
                    }

                    @Override
                    public void onError(Throwable e) {
                        Toast.makeText(RegisterActivity2.this, "注册失败", Toast.LENGTH_SHORT).show();
                    }

                    @Override
                    public void onNext(String s) {
                        Log.i("register", s);
                    }
                });
    }

    public void login(String phone, String passwd) {
        UserinfoService userinfoService = HttpControl.getInstance().getRetrofit().create(UserinfoService.class);
        userinfoService.login(phone, passwd)
                .subscribeOn(Schedulers.io())
                .unsubscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Subscriber<String>() {
                    @Override
                    public void onCompleted() {
                        Toast.makeText(RegisterActivity2.this, "登录成功", Toast.LENGTH_SHORT).show();
                        startActivity(new Intent(RegisterActivity2.this, MakeupinfoActivity.class));
                    }

                    @Override
                    public void onError(Throwable e) {
                        Toast.makeText(RegisterActivity2.this, "登录失败", Toast.LENGTH_SHORT).show();
                    }

                    @Override
                    public void onNext(String s) {
                        Log.i("register", s);
                    }
                });
    }
}
