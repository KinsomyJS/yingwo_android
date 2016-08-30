package com.yingwo.yingwo;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.view.View;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by wangyu on 8/29/16.
 */

public class HomePageActivity extends AppCompatActivity {
    @BindView(R.id.home_content)
    FrameLayout homeContent;
    @BindView(R.id.action_home)
    ImageView actionHome;
    @BindView(R.id.action_find)
    ImageView actionFind;
    @BindView(R.id.action_add)
    ImageView actionAdd;
    @BindView(R.id.action_bub)
    ImageView actionBub;
    @BindView(R.id.action_head)
    ImageView actionHead;
    private HomeFragment homeFragment;
    private PersonCenterFragment personCenterFragment;
    private Fragment CurrentPage;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        ButterKnife.bind(this);
        init();
    }

    public void init() {
        actionHome.setBackgroundResource(R.mipmap.home_g);
        homeFragment = new HomeFragment();
        CurrentPage = homeFragment;
        FragmentTransaction transaction = getSupportFragmentManager()
                .beginTransaction();
        transaction.add(R.id.home_content, CurrentPage);
        transaction.commit();
    }

    @OnClick({R.id.action_home, R.id.action_find, R.id.action_add, R.id.action_bub, R.id.action_head})
    public void switchContent(View view) {
        switch (view.getId()) {
            case R.id.action_home:
                if (homeFragment == null) {
                    homeFragment = new HomeFragment();
                }
                switchFragment(CurrentPage, homeFragment);
                clear();
                actionHome.setBackgroundResource(R.mipmap.home_g);
                CurrentPage = homeFragment;
                break;
            case R.id.action_bub:
                startActivity(new Intent(this, PostActivity.class));
                break;
            case R.id.action_head:
                if (personCenterFragment == null) {
                    personCenterFragment = new PersonCenterFragment();
                }
                switchFragment(CurrentPage, personCenterFragment);
                clear();
                actionHead.setBackgroundResource(R.mipmap.head_g);
                CurrentPage = personCenterFragment;
                break;
            case R.id.action_add:
                startActivity(new Intent(this, RefreshThingActivity.class));
                break;
        }
    }

    public void switchFragment(Fragment from, Fragment to) {
        if (from == null || to == null)
            return;
        FragmentTransaction transaction = getSupportFragmentManager()
                .beginTransaction();
//                .setCustomAnimations(R.anim.tran_pre_in, R.anim.tran_pre_out);
        if (!to.isAdded()) {
            // 隐藏当前的fragment，add下一个到Activity中
            transaction.hide(from).add(R.id.home_content, to).commit();
        } else {
            // 隐藏当前的fragment，显示下一个
            transaction.hide(from).show(to).commit();
        }
    }


    public void clear() {
        actionHome.setBackgroundResource(R.mipmap.home);
        actionHead.setBackgroundResource(R.mipmap.head);
    }

}
