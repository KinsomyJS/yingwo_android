package com.yingwo.yingwo.Adapter;

import android.app.Activity;
import android.net.Uri;
import android.view.View;
import android.view.ViewGroup;

import com.facebook.drawee.view.SimpleDraweeView;
import com.yingwo.yingwo.R;

import java.util.List;

import cn.finalteam.galleryfinal.adapter.ViewHolderRecyclingPagerAdapter;

/**
 * Created by FJS0420 on 2016/9/5.
 */

public class PhotoPreviewAdapter extends ViewHolderRecyclingPagerAdapter<com.yingwo.yingwo.Adapter.PhotoPreviewAdapter.PreviewViewHolder, String> {

    private Activity mActivity;
    private List<String> urllist;

    public PhotoPreviewAdapter(Activity activity, List<String> list) {
        super(activity,list);
        this.mActivity = activity;
        urllist = list;
    }

    @Override
    public com.yingwo.yingwo.Adapter.PhotoPreviewAdapter.PreviewViewHolder onCreateViewHolder(ViewGroup parent, int position) {
        View view = getLayoutInflater().inflate(R.layout.bigpic_preview_viewpage_item, null);
        SimpleDraweeView simpleDraweeView = (SimpleDraweeView) view.findViewById(R.id.imgview);
        return new com.yingwo.yingwo.Adapter.PhotoPreviewAdapter.PreviewViewHolder(simpleDraweeView);
    }

    @Override
    public void onBindViewHolder(com.yingwo.yingwo.Adapter.PhotoPreviewAdapter.PreviewViewHolder holder, int position) {
        holder.mImageView.setImageURI(Uri.parse(urllist.get(position)));
    }

    static class PreviewViewHolder extends ViewHolderRecyclingPagerAdapter.ViewHolder{
        SimpleDraweeView mImageView;
        public PreviewViewHolder(View view) {
            super(view);
            mImageView = (SimpleDraweeView) view;
        }
    }
}
