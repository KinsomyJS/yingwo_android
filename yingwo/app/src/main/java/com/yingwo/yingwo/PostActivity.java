package com.yingwo.yingwo;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.Nullable;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.yingwo.yingwo.Adapter.PostRecyclerAdapter;
import com.yingwo.yingwo.Listener.SoftKeyBoardListener;
import com.yingwo.yingwo.PopUpWindow.Command_PopUp;
import com.yingwo.yingwo.View.AutoLoadRecyclerView;
import com.yingwo.yingwo.model.PostListEntity;
import com.yingwo.yingwo.model.TopicModel;
import com.yingwo.yingwo.utils.HttpControl;
import com.yingwo.yingwo.utils.UserinfoService;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import retrofit2.Retrofit;
import rx.Subscriber;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

/**
 * Created by FJS0420 on 2016/8/5.
 */

public class PostActivity extends AppCompatActivity implements SwipeRefreshLayout.OnRefreshListener {

    @BindView(R.id.tv_title)
    TextView tv_title;
    @BindView(R.id.ib_like)
    ImageButton ibLike;
    @BindView(R.id.swipe_page)
    SwipeRefreshLayout swipePage;
    @BindView(R.id.block_keyboardview)
    LinearLayout block_keyboardview;
    @BindView(R.id.tv_input)
    EditText tvInput;

    private Toolbar toolbar;
    private int POSTLIST = 10;
    private boolean isFirst;
    private Command_PopUp command_popUp;
    private AutoLoadRecyclerView mRecyclerVeiew;
    private PostRecyclerAdapter mAdapter;
    private Intent topIntent;
    private TopicModel.InfoBean topBean;
    private List<PostListEntity.InfoBean> data;
    private LinearLayoutManager layoutManager;
    private InputMethodManager imm;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_post);
        AppManager.getAppManager().addActivity(this);
        ButterKnife.bind(this);
        init();
    }

    private void init() {
        isFirst = true;
        topIntent = getIntent();
        Bundle topBundle = topIntent.getBundleExtra("topBundle");
        topBean = (TopicModel.InfoBean) topBundle.getSerializable("top");
        getPostList();
        tvInput.setFocusable(true);
        swipePage.post(new Runnable() {
            @Override
            public void run() {
                swipePage.setRefreshing(true);
            }
        });
        swipePage.setOnRefreshListener(this);
        mRecyclerVeiew = (AutoLoadRecyclerView) findViewById(R.id.rv_post);
        layoutManager = new LinearLayoutManager(this);
        mRecyclerVeiew.setLayoutManager(layoutManager);
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
        imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
        //注册软键盘的监听
        SoftKeyBoardListener.setListener(this,
                new SoftKeyBoardListener.OnSoftKeyBoardChangeListener()

                {
                    @Override
                    public void keyBoardShow(int height) {
                        block_keyboardview.setVisibility(View.VISIBLE);
                    }

                    @Override
                    public void keyBoardHide(int height) {
                        block_keyboardview.setVisibility(View.GONE);

                    }
                });

    }

    public void buildItem(View view) {
        Intent intent = new Intent(this, PostBuildingActivity.class);
        intent.putExtra("post_id", topBean.getId());
        startActivity(intent);
    }

    @Override
    protected void onResume() {
        super.onResume();
        swipePage.post(new Runnable() {
            @Override
            public void run() {
                swipePage.setRefreshing(true);
                getPostList();
            }
        });
    }

    public void getPostList() {
        Retrofit retrofit = HttpControl.getInstance().getRetrofit();
        UserinfoService userinfoService = retrofit.create(UserinfoService.class);
        userinfoService.getPostList(Integer.parseInt(topBean.getId()))
                .subscribeOn(Schedulers.io())
                .unsubscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Subscriber<PostListEntity>() {
                    @Override
                    public void onCompleted() {
                        Log.d("PostActivity", "Completed");

                    }

                    @Override
                    public void onError(Throwable e) {
                        Log.d("PostActivity", "Error");
                    }

                    @Override
                    public void onNext(PostListEntity postListEntity) {
                        if (postListEntity.getStatus() != 1) {
                            onError(new Exception());
                        } else {
                            data = postListEntity.getInfo();
                            Message message = handler.obtainMessage();
                            message.what = POSTLIST;
                            message.sendToTarget();
                        }
                    }
                });
    }

    @OnClick(R.id.ib_like)
    public void Like() {
    }

    Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            if (msg.what == POSTLIST) {
                if (isFirst) {
                    mAdapter = new PostRecyclerAdapter(PostActivity.this, data, topBean);
                    mAdapter.setOnClickListener(new postReplyListener());
                    mAdapter.setPopUpClickListener(new popUpClickListener());
                    mRecyclerVeiew.setAdapter(mAdapter);
                    isFirst = false;
                } else {
                    mAdapter.setData(data);
                }
                mAdapter.notifyDataSetChanged();
                if (swipePage.isRefreshing())
                    swipePage.setRefreshing(false);
            }
        }
    };


    @Override
    public void onRefresh() {
        getPostList();
    }

    public class postReplyListener implements View.OnClickListener {

        @Override
        public void onClick(View v) {
            tvInput.requestFocus();
            imm.showSoftInput(tvInput, InputMethodManager.SHOW_FORCED);
        }
    }

    public class popUpClickListener implements View.OnClickListener {

        @Override
        public void onClick(View v) {
            if (imm.isActive()){
                block_keyboardview.setVisibility(View.GONE);
                imm.hideSoftInputFromWindow(tvInput.getWindowToken(), 0);
            }
            command_popUp = new Command_PopUp(PostActivity.this, Pop_onClick);
            command_popUp.showAtLocation(findViewById(R.id.post_main), Gravity.BOTTOM, 0, 0);
        }
    }

    public View.OnClickListener Pop_onClick = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            switch (v.getId()) {
                case R.id.action_btn:
                    Toast.makeText(PostActivity.this, "这是操作按钮", Toast.LENGTH_SHORT).show();
                    break;
                case R.id.copy_btn:
                    Toast.makeText(PostActivity.this, "这是复制按钮", Toast.LENGTH_SHORT).show();
                    break;
                case R.id.report_btn:
                    Toast.makeText(PostActivity.this, "这是举报按钮", Toast.LENGTH_SHORT).show();
                    break;
            }
        }
    };

}
