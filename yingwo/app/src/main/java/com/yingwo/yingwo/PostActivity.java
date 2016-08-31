package com.yingwo.yingwo;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

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
//        data.add("我们知道section指的是索引条上选中字母的索引，我们假设这个字母是N；那secion-1就代表的是section所对应的字母在索引条上的前一个字母，如果section对应的是N的话，那section-1所对应的就是字母M;\n" +
//                "    这段话的思想其实就是，我们一般而言是要将二分查找的start位置定为0，但如果尽量往后的话那查找起来会更快些。所以，既然在map中没有section字符所对应item的索引，但如果能找到它前一个字母所对应索引的话，我们就不必从头开始找了，直接从它上一个字母所对应的");
//        data.add("我们知道section指的是索引条上选中字母的索引，我们假设这个字母是N；那secion-1就代表的是section所对应的字母在索引条上的前一个字母，如果section对应的是N的话，那section-1所对应的就是字母M;\n" +
//                "    这段话的思想其实就是，我们一般而言是要将二分查找的start位置定为0，但如果尽量往后的话那查找起来会更快些。所以，既然在map中没有section字符所对应item的索引，但如果能找到它前一个字母所对应索引的话，我们就不必从头开始找了，直接从它上一个字母所对应的item位置往后找即可。");
//        data.add("我们知道section指的是索引条上选中字母的索引，我们假设这个字母是N；那secion-1就代表的是section所对应的字母在索引条上的前一个字母，如果section对应的是N的话，那section-1所对应的就是字母M;\n" +
//                "    这段话的思想其实就是，我们一般而言是要将二分查找的start位置定为0，但如果尽量往后的话那查找起来会更快些。所以，既然在map中没有section字符所对应item的索引，但如果能找到它前一个字母所对应索引的话，我们就不必从头开始找了，直接从它上一个字母所对应的item位置往后找即可。");
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

    public void buildItem(View view) {
        data.add("我们知道section指的是索引条上选中字母的索引，我们假设这个字母是N；那secion-1就代表的是section所对应的字母在索引条上的前一个字母，如果section对应的是N的话，那section-1所对应的就是字母M;\n" +
                "    这段话的思想其实就是，我们一般而言是要将二分查找的start位置定为0，但如果尽量往后的话那查找起来会更快些。所以，既然在map中没有section字符所对应item的索引，但如果能找到它前一个字母所对应索引的话，我们就不必从头开始找了，直接从它上一个字母所对应的");
        mAdapter.notifyDataSetChanged();
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
