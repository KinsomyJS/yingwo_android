package com.yingwo.yingwo;

import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.Gravity;
import android.view.View;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import com.yingwo.yingwo.PopUpWindow.Command_PopUp;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by FJS0420 on 2016/8/5.
 */

public class PostActivity extends AppCompatActivity {

    @BindView(R.id.tv_title)
    TextView tv_title;
//    @BindView(R.id.ib_Command)
//    ImageButton ibCommand;

    private Toolbar toolbar;
//    private Command_PopUp command_popUp;
    private RecyclerView mRecyclerVeiew;
    private PostRecyclerAdapter mAdapter;
    private List<String> data;


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_post);
        ButterKnife.bind(this);
        init();
    }

    private void init() {
        data = new ArrayList<>();
        data.add("hah");
        data.add("hdhh");
        data.add("Dddd");
        ButterKnife.bind(this);
        mRecyclerVeiew = (RecyclerView) findViewById(R.id.rv_post);
        mRecyclerVeiew.setLayoutManager(new LinearLayoutManager(this));
        mAdapter = new PostRecyclerAdapter(this, data);
        mRecyclerVeiew.setAdapter(mAdapter);

        toolbar = (Toolbar) findViewById(R.id.toolbar);
        toolbar.setTitle("");
        setSupportActionBar(toolbar);
        tv_title.setText("帖子");
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

    }

//    public View.OnClickListener Pop_onClick = new View.OnClickListener() {
//        @Override
//        public void onClick(View v) {
//            switch (v.getId()) {
//                case R.id.action_btn:
//                    Toast.makeText(PostActivity.this, "这是操作按钮", Toast.LENGTH_SHORT).show();
//                    break;
//                case R.id.copy_btn:
//                    Toast.makeText(PostActivity.this, "这是复制按钮", Toast.LENGTH_SHORT).show();
//                    break;
//                case R.id.report_btn:
//                    Toast.makeText(PostActivity.this, "这是举报按钮", Toast.LENGTH_SHORT).show();
//                    break;
//            }
//        }
//    };

//    @OnClick(R.id.ib_Command)
//    public void onClick() {
//        command_popUp = new Command_PopUp(this, Pop_onClick);
//        command_popUp.showAtLocation(findViewById(R.id.post_main), Gravity.BOTTOM, 0, 0);
//    }

}
