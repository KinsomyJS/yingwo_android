package com.yingwo.yingwo;

import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;
import android.animation.PropertyValuesHolder;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
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

public class HomeFragment extends Fragment {


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

    private Toolbar toolbar = null;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.activity_homepage, container, false);
        ButterKnife.bind(this, view);
        return view;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        init(view);
    }

    private void init(View view) {
        toolbar = (Toolbar) view.findViewById(R.id.toolbar);
        toolbar.setTitle("");
        ((AppCompatActivity) getActivity()).setSupportActionBar(toolbar);
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
    private void showSelector() {
        layout_selector.setVisibility(View.VISIBLE);
        ObjectAnimator translationX = ObjectAnimator.ofFloat(layout_selector, "translationX", -300, 0);
        ObjectAnimator alpha = ObjectAnimator.ofFloat(layout_selector, "alpha", 0, 1);
        translationX.setInterpolator(new BounceInterpolator());
        AnimatorSet animatorSet = new AnimatorSet();
        animatorSet.playTogether(translationX, alpha);
        animatorSet.setDuration(1500);
        animatorSet.start();
    }

    //隐藏左上角筛选器
    private void hideSelector() {
        layout_selector.setVisibility(View.INVISIBLE);
    }


    @OnClick(R.id.tv_all)
    public void toAll() {
        Toast.makeText(getActivity(), "ToAll", Toast.LENGTH_SHORT).show();
    }

    @OnClick(R.id.tv_new)
    public void toNew() {
        Toast.makeText(getActivity(), "toNew", Toast.LENGTH_LONG).show();

    }

    @OnClick(R.id.tv_attention)
    public void toAttention() {
        Toast.makeText(getActivity(), "toAttention", Toast.LENGTH_LONG).show();
    }

    @OnClick(R.id.tv_friendthing)
    public void toFriendthing() {
        Toast.makeText(getActivity(), "toFriendthing", Toast.LENGTH_LONG).show();
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        inflater.inflate(R.menu.menu_homepage, menu);
        super.onCreateOptionsMenu(menu, inflater);
    }


    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == R.id.action_search) {

        }

        return super.onOptionsItemSelected(item);
    }
}
