package com.yingwo.yingwo;

import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;
import android.animation.PropertyValuesHolder;
import android.graphics.RectF;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.AccelerateInterpolator;
import android.view.animation.BounceInterpolator;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.yingwo.yingwo.PopUpWindow.Command_PopUp;
import com.yingwo.yingwo.PopUpWindow.Home_PopUp;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by FJS0420 on 2016/8/5.
 */

public class HomeFragment extends Fragment {
    
    private Toolbar toolbar = null;
    private Home_PopUp home_popUp;


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
        init();
    }

    private void init() {
        toolbar = (Toolbar) getActivity().findViewById(R.id.toolbar);
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

                home_popUp = new Home_PopUp(getActivity(), new MenuOnClickListener());
                home_popUp.showAsDropDown(toolbar, 10, 15);
            }
        });

    }

    public class MenuOnClickListener implements View.OnClickListener {

        @Override
        public void onClick(View v) {
            switch (v.getId()) {
                case R.id.tv_all:
                    Toast.makeText(getActivity(), "toAll", Toast.LENGTH_SHORT).show();
                    break;
                case R.id.tv_new:
                    Toast.makeText(getActivity(), "toNew", Toast.LENGTH_SHORT).show();
                    break;
                case R.id.tv_attention:
                    Toast.makeText(getActivity(), "toAttention", Toast.LENGTH_SHORT).show();
                    break;
                case R.id.tv_friendthing:
                    Toast.makeText(getActivity(), "toFriendThing", Toast.LENGTH_SHORT).show();
                    break;
            }
        }
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
