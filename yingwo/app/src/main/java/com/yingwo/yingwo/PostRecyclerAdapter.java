package com.yingwo.yingwo;

import android.app.Activity;
import android.net.Uri;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.facebook.drawee.backends.pipeline.Fresco;
import com.facebook.drawee.view.SimpleDraweeView;
import com.facebook.imagepipeline.core.ImagePipelineConfig;
import com.yingwo.yingwo.PopUpWindow.Command_PopUp;
import com.yingwo.yingwo.model.PostListEntity.InfoBean;
import com.yingwo.yingwo.model.PostListEntity;
import com.yingwo.yingwo.model.PostTopModel;
import com.yingwo.yingwo.utils.ListViewUtil;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

/**
 * Created by wangyu on 8/29/16.
 */

public class PostRecyclerAdapter extends RecyclerView.Adapter<PostRecyclerAdapter.MyViewHolder> {
    private Activity context;
    private List<InfoBean> postData;
    private PostTopModel postTop;
    private Command_PopUp command_popUp;
    private PostImageItemAdapter postImageItemAdapter;
    private int POST_TOP = -1;
    private int POST_ITEM = 1;

    public PostRecyclerAdapter(Activity context, List<InfoBean> postData) {
        this.context = context;
        if (postData != null)
            Collections.reverse(postData);
        this.postData = postData;
//        this.postTop = postTop;
    }

    public void setDatas(List<InfoBean> datas) {
        if (datas != null && datas.size() > 0) {
            notifyDataSetChanged();
        }
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        MyViewHolder holder;
//        if (viewType == POST_TOP) {
//            holder = new MyViewHolder(LayoutInflater.from(context).inflate(R.layout.list_post_top_item, parent, false));
//        } else {
        holder = new MyViewHolder(LayoutInflater.from(context).inflate(R.layout.list_post_item, parent, false));

//        }
        return holder;
    }

//    @Override
//    public int getItemViewType(int position) {
//        if (position == 0) {
//            return POST_TOP;
//        }
//        return POST_ITEM;
//    }

    @Override
    public void onBindViewHolder(final MyViewHolder holder, int position) {
        final InfoBean infoBean = postData.get(position);
        if (infoBean.getUser_face_img() != null)
//            Toast.makeText(context, "Uri.parse(face_img):" + Uri.parse(face_img), Toast.LENGTH_SHORT).show();
            holder.header.setImageURI(Uri.parse((String) infoBean.getUser_face_img()));
        holder.tvLouCeng.setText("第" + (position + 1) + "楼");
        holder.tvContent.setText(infoBean.getContent());
        holder.ibMore.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                command_popUp = new Command_PopUp(context, Pop_onClick);
                command_popUp.showAtLocation(context.findViewById(R.id.post_main), Gravity.BOTTOM, 0, 0);
            }
        });
        setImage(holder.listView, infoBean.getImg());
        holder.userName.setText((String) infoBean.getUser_name());
    }

    @Override
    public int getItemCount() {
        return postData.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {
        TextView tvContent;
        TextView tvLouCeng;
        SimpleDraweeView header;
        TextView userName;
        ImageButton ibMore;
        ListView listView;

        public MyViewHolder(View itemView) {
            super(itemView);
            userName = (TextView) itemView.findViewById(R.id.tv_userName);
            header = (SimpleDraweeView) itemView.findViewById(R.id.iv_userhead);
            tvContent = (TextView) itemView.findViewById(R.id.tv_content);
            tvLouCeng = (TextView) itemView.findViewById(R.id.tv_louceng);
            ibMore = (ImageButton) itemView.findViewById(R.id.ib_Command);
            listView = (ListView) itemView.findViewById(R.id.ImageList);
        }
    }

    public View.OnClickListener Pop_onClick = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            switch (v.getId()) {
                case R.id.action_btn:
                    Toast.makeText(context, "这是操作按钮", Toast.LENGTH_SHORT).show();
                    break;
                case R.id.copy_btn:
                    Toast.makeText(context, "这是复制按钮", Toast.LENGTH_SHORT).show();
                    break;
                case R.id.report_btn:
                    Toast.makeText(context, "这是举报按钮", Toast.LENGTH_SHORT).show();
                    break;
            }
        }
    };

    public void setImage(ListView listView, String imageUrl) {
        if (!imageUrl.contains("obabu2buy.bkt.clouddn.com")) {
            listView.setVisibility(View.GONE);
            return;
        }
        String[] urls = imageUrl.split(",");
        List<String> data = Arrays.asList(urls);
        postImageItemAdapter = new PostImageItemAdapter(context, data);
        listView.setAdapter(postImageItemAdapter);
        ListViewUtil.setListViewHeightBasedOnChildren(listView);
    }

}
