package com.yingwo.yingwo;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import com.yingwo.yingwo.Adapter.PostRecyclerAdapter;
import com.yingwo.yingwo.View.AutoLoadRecyclerView;
import com.yingwo.yingwo.model.PostListEntity;
import com.yingwo.yingwo.model.TopicModel;
import com.yingwo.yingwo.utils.HttpControl;
import com.yingwo.yingwo.utils.UserinfoService;

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

public class PostActivity extends AppCompatActivity {

    @BindView(R.id.tv_title)
    TextView tv_title;
    @BindView(R.id.ib_like)
    ImageButton ibLike;
    @BindView(R.id.swipe_page)
    SwipeRefreshLayout swipePage;
//    @BindView(R.id.ib_Command)
//    ImageButton ibCommand;

    private Toolbar toolbar;
    //    private Command_PopUp command_popUp;
    private AutoLoadRecyclerView mRecyclerVeiew;
    private PostRecyclerAdapter mAdapter;
    private boolean firstFlag;
    private Intent topIntent;
    private TopicModel.InfoBean topBean;



    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_post);
        ButterKnife.bind(this);
        init();
    }

    private void init() {
        swipePage.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                getPostList();
            }
        });
        // 这句话是为了，第一次进入页面的时候显示加载进度条
        firstFlag = true;
        topIntent = getIntent();
        Bundle topBundle = topIntent.getBundleExtra("topBundle");
        topBean = (TopicModel.InfoBean) topBundle.getSerializable("top");
        Toast.makeText(this,topBean.getImg(),Toast.LENGTH_SHORT).show();
        getPostList();
//        data.add("我们知道section指的是索引条上选中字母的索引，我们假设这个字母是N；那secion-1就代表的是section所对应的字母在索引条上的前一个字母，如果section对应的是N的话，那section-1所对应的就是字母M;\n" +
//                "    这段话的思想其实就是，我们一般而言是要将二分查找的start位置定为0，但如果尽量往后的话那查找起来会更快些。所以，既然在map中没有section字符所对应item的索引，但如果能找到它前一个字母所对应索引的话，我们就不必从头开始找了，直接从它上一个字母所对应的");
//        data.add("我们知道section指的是索引条上选中字母的索引，我们假设这个字母是N；那secion-1就代表的是section所对应的字母在索引条上的前一个字母，如果section对应的是N的话，那section-1所对应的就是字母M;\n" +
//                "    这段话的思想其实就是，我们一般而言是要将二分查找的start位置定为0，但如果尽量往后的话那查找起来会更快些。所以，既然在map中没有section字符所对应item的索引，但如果能找到它前一个字母所对应索引的话，我们就不必从头开始找了，直接从它上一个字母所对应的item位置往后找即可。");
//        data.add("我们知道section指的是索引条上选中字母的索引，我们假设这个字母是N；那secion-1就代表的是section所对应的字母在索引条上的前一个字母，如果section对应的是N的话，那section-1所对应的就是字母M;\n" +
//                "    这段话的思想其实就是，我们一般而言是要将二分查找的start位置定为0，但如果尽量往后的话那查找起来会更快些。所以，既然在map中没有section字符所对应item的索引，但如果能找到它前一个字母所对应索引的话，我们就不必从头开始找了，直接从它上一个字母所对应的item位置往后找即可。");
        ButterKnife.bind(this);
        mRecyclerVeiew = (AutoLoadRecyclerView) findViewById(R.id.rv_post);
        mRecyclerVeiew.setLayoutManager(new LinearLayoutManager(this));

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

    public void buildItem(View view) {
//        data.add("我们知道section指的是索引条上选中字母的索引，我们假设这个字母是N；那secion-1就代表的是section所对应的字母在索引条上的前一个字母，如果section对应的是N的话，那section-1所对应的就是字母M;\n" +
//                "    这段话的思想其实就是，我们一般而言是要将二分查找的start位置定为0，但如果尽量往后的话那查找起来会更快些。所以，既然在map中没有section字符所对应item的索引，但如果能找到它前一个字母所对应索引的话，我们就不必从头开始找了，直接从它上一个字母所对应的");
//        mAdapter.notifyDataSetChanged();
        Intent intent = new Intent(this, PostBuildingActivity.class);
        intent.putExtra("post_id",topBean.getId());
        startActivity(intent);
    }

    @Override
    protected void onResume() {
        super.onResume();
        getPostList();
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
                            if (firstFlag) {
                                mAdapter = new PostRecyclerAdapter(PostActivity.this, postListEntity.getInfo(),topBean);
                                mRecyclerVeiew.setAdapter(mAdapter);
                                firstFlag = false;
                            } else {
                                mAdapter.setDatas(postListEntity.getInfo());
                            }
                            onCompleted();
                        }
                    }
                });

    }

    @OnClick(R.id.ib_like)
    public void Like() {
        getPostList();
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
