package com.yingwo.yingwo;

import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;
import android.animation.PropertyValuesHolder;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.animation.AccelerateInterpolator;
import android.view.animation.BounceInterpolator;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by FJS0420 on 2016/8/5.
 */

public class HomePageActivity extends AppCompatActivity {


    @BindView(R.id.action_home)
    ImageView action_home;
    @BindView(R.id.action_find)
    ImageView action_find;
    @BindView(R.id.action_add)
    ImageView action_add;
    @BindView(R.id.action_bub)
    ImageView action_bub;
    @BindView(R.id.action_head)
    ImageView action_head;
    @BindView(R.id.layout_selector)
    RelativeLayout layout_selector;
    @BindView(R.id.tv_all)
    TextView tv_all;
    @BindView(R.id.tv_new)
    TextView tv_new;
    @BindView(R.id.tv_attention)
    TextView tv_attention;
    @BindView(R.id.tv_friendthing)
    TextView tv_friendthing;

    private Toolbar toolbar = null;


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_homepage);
        init();
    }

    private void init(){
        ButterKnife.bind(this);
        toolbar = (Toolbar) findViewById(R.id.toolbar);
        toolbar.setTitle("");
        setSupportActionBar(toolbar);
        action_home.setBackgroundResource(R.mipmap.home_g);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                PropertyValuesHolder rotationHolder = PropertyValuesHolder.ofFloat("Rotation", 60f, -60f, 40f, -40f, -20f, 20f, 10f, -10f, 0f);
                ObjectAnimator animator = ObjectAnimator.ofPropertyValuesHolder(v, rotationHolder);
                animator.setDuration(2000);
                animator.setInterpolator(new AccelerateInterpolator());
                animator.start();
                if (layout_selector.getVisibility() == View.INVISIBLE)
                    showSelector();
                else
                    hideSelector();
            }
        });


    }


    //显示左上角筛选器动画
    private void showSelector(){
        layout_selector.setVisibility(View.VISIBLE);
        ObjectAnimator translationX = ObjectAnimator.ofFloat(layout_selector, "translationX", -300, 0);
        ObjectAnimator alpha = ObjectAnimator.ofFloat(layout_selector, "alpha", 0, 1);
        translationX.setInterpolator(new BounceInterpolator());
        AnimatorSet animatorSet = new AnimatorSet();
        animatorSet.playTogether(translationX,alpha);
        animatorSet.setDuration(1500);
        animatorSet.start();
    }
    //隐藏左上角筛选器
    private void hideSelector(){
        layout_selector.setVisibility(View.INVISIBLE);
    }

    @OnClick(R.id.action_bub)
    public void toPost(){
        startActivity(new Intent(this,PostActivity.class));
    }

    @OnClick(R.id.action_head)
    public void toHome(){
        startActivity(new Intent(this,PersonCenterActivity.class));
    }

    @OnClick(R.id.action_add)
    public void toRefreshthing(){
        startActivity(new Intent(this,RefreshThingActivity.class));
    }

    @OnClick(R.id.tv_all)
    public void toAll(){
        Toast.makeText(getApplicationContext(),"toAll",Toast.LENGTH_LONG).show();
    }
    @OnClick(R.id.tv_new)
    public void toNew(){
        Toast.makeText(getApplicationContext(),"toNew",Toast.LENGTH_LONG).show();

    }
    @OnClick(R.id.tv_attention)
    public void toAttention(){
        Toast.makeText(getApplicationContext(),"toAttention",Toast.LENGTH_LONG).show();
    }
    @OnClick(R.id.tv_friendthing)
    public void toFriendthing(){
        Toast.makeText(getApplicationContext(),"toFriendthing",Toast.LENGTH_LONG).show();
    }



    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_homepage, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == R.id.action_search){

        }

        return super.onOptionsItemSelected(item);
    }
}
