package com.yingwo.yingwo.Adapter;

import android.app.Activity;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.facebook.drawee.view.SimpleDraweeView;
import com.yingwo.yingwo.R;
import com.yingwo.yingwo.model.PostListEntity.InfoBean;
import com.yingwo.yingwo.model.TopicModel;
import com.yingwo.yingwo.utils.ImageUtil;
import com.yingwo.yingwo.utils.ListViewUtil;
import com.yingwo.yingwo.utils.TImeUtiil;

import java.util.Arrays;
import java.util.List;

/**
 * Created by wangyu on 8/29/16.
 */

public class PostRecyclerAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private Activity context;
    private List<InfoBean> postData;
    private TopicModel.InfoBean topBean;
    private PostImageItemAdapter postImageItemAdapter;
    private View.OnClickListener mOnClickListener = null;
    private View.OnClickListener popUpClickListener = null;
    private int POST_TOP = -1;
    private int POST_ITEM = 1;

    public PostRecyclerAdapter(Activity context, List<InfoBean> postData, TopicModel.InfoBean topBean) {
        this.context = context;
        this.postData = postData;
        this.topBean = topBean;
    }


    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        RecyclerView.ViewHolder holder;
        if (viewType == POST_TOP) {
            holder = new TopViewHolder(LayoutInflater.from(context).inflate(R.layout.list_post_top_item, parent, false));

        } else {
            holder = new ItemViewHolder(LayoutInflater.from(context).inflate(R.layout.list_post_item, parent, false));
        }

        return holder;
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        MyViewHolder myHolder = (MyViewHolder) holder;
        String content = null;
        String releaseTime = null;
        String face_img = null;
        String user_name = null;
        String img_urls = null;
        if (holder instanceof TopViewHolder) {
            img_urls = topBean.getImg();
            user_name = topBean.getUser_name();
            releaseTime = topBean.getCreate_time();
            content = topBean.getContent();
            face_img = topBean.getUser_face_img();
        } else if (holder instanceof ItemViewHolder) {
            setReply(((ItemViewHolder) holder).iv_reply);
            InfoBean infoBean = postData.get(position - 1);
            if (infoBean.getUser_name().equals(topBean.getUser_name())){
                ((ItemViewHolder) holder).louzhu_icon.setVisibility(View.VISIBLE);
            }else {
                ((ItemViewHolder) holder).louzhu_icon.setVisibility(View.GONE);
            }
            img_urls = infoBean.getImg();
            user_name = (String) infoBean.getUser_name();
            releaseTime = infoBean.getCreate_time();
            content = infoBean.getContent();
            face_img = (String) infoBean.getUser_face_img();
        }

        if (!content.isEmpty())

        {
            myHolder.tvContent.setVisibility(View.VISIBLE);
            myHolder.tvContent.setText(content);
        } else

        {
            myHolder.tvContent.setVisibility(View.GONE);
        }

        if (face_img != null)

        {
            myHolder.face_img.setImageURI(face_img);
        } else

        {
            myHolder.face_img.setImageURI(ImageUtil.resourceIdToUri(context, R.mipmap.touxiang));
        }

        if (img_urls.contains("http"))

        {
            myHolder.listView.setVisibility(View.VISIBLE);
            setImage(myHolder.listView, img_urls);
        } else

        {
            myHolder.listView.setVisibility(View.GONE);
        }

        myHolder.tvLouCeng.setText("第" + (position + 1) + "楼");

        setPopup(myHolder.ibMore);
//        myHolder.ibMore.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//
//                command_popUp = new Command_PopUp(context, Pop_onClick);
//                command_popUp.showAtLocation(context.findViewById(R.id.post_main), Gravity.BOTTOM, 0, 0);
//            }
//        });
        myHolder.userName.setText(user_name);
        myHolder.time.setText(TImeUtiil.getStandardDate(releaseTime));
    }


    @Override
    public int getItemViewType(int position) {

        if (position == 0) {
            return POST_TOP;
        } else
            return POST_ITEM;

    }

    @Override
    public int getItemCount() {
        return postData.size() + 1;
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {
        TextView tvContent;
        TextView tvLouCeng;
        SimpleDraweeView header;
        TextView userName;
        ImageButton ibMore;
        SimpleDraweeView face_img;
        TextView time;
        ListView listView;
        TextView louzhu_icon;

        public MyViewHolder(View itemView) {
            super(itemView);
            louzhu_icon = (TextView) itemView.findViewById(R.id.tv_louzhu);
            time = (TextView) itemView.findViewById(R.id.tv_releasetime);
            face_img = (SimpleDraweeView) itemView.findViewById(R.id.iv_userhead);
            userName = (TextView) itemView.findViewById(R.id.tv_userName);
            header = (SimpleDraweeView) itemView.findViewById(R.id.iv_userhead);
            tvContent = (TextView) itemView.findViewById(R.id.tv_content);
            tvLouCeng = (TextView) itemView.findViewById(R.id.tv_louceng);
            ibMore = (ImageButton) itemView.findViewById(R.id.ib_Command);
            listView = (ListView) itemView.findViewById(R.id.ImageList);
        }
    }

    public class ItemViewHolder extends MyViewHolder {
        ImageView iv_reply;

        public ItemViewHolder(View itemView) {
            super(itemView);
            iv_reply = (ImageView) itemView.findViewById(R.id.post_reply);
        }
    }

    public class TopViewHolder extends MyViewHolder {
        public TopViewHolder(View itemView) {
            super(itemView);
        }

    }

    public void setImage(ListView listView, String imageUrl) {
        if (!imageUrl.contains("http")) {
            listView.setVisibility(View.GONE);
            return;
        }

        String[] urls = imageUrl.split(",");
        for (int i = 0; i < urls.length; i++) {
            urls[i] += "?imageMogr2/thumbnail/!75p";
        }
        List<String> data = Arrays.asList(urls);
        postImageItemAdapter = new PostImageItemAdapter(context, data);
        listView.setAdapter(postImageItemAdapter);
        ListViewUtil.setListViewHeightBasedOnChildren(listView);
    }

    public void setData(List<InfoBean> postData) {
        this.postData = postData;
    }

    public void setReply(ImageView imageView) {
        if (mOnClickListener != null) {
            imageView.setOnClickListener(mOnClickListener);
        }
    }

    public void setPopup(ImageButton imageButton) {
        if (popUpClickListener != null) {
            imageButton.setOnClickListener(popUpClickListener);
        }
    }

    public void setOnClickListener(View.OnClickListener onClickListener) {
        mOnClickListener = onClickListener;
    }

    public void setPopUpClickListener(View.OnClickListener onClickListener) {
        popUpClickListener = onClickListener;
    }
}
