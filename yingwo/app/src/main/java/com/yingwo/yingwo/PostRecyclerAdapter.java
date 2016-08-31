package com.yingwo.yingwo;

import android.app.Activity;
import android.support.v7.widget.RecyclerView;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import com.yingwo.yingwo.PopUpWindow.Command_PopUp;
import com.yingwo.yingwo.model.PostItemModel;
import com.yingwo.yingwo.model.PostTopModel;

import java.util.List;

/**
 * Created by wangyu on 8/29/16.
 */

public class PostRecyclerAdapter extends RecyclerView.Adapter<PostRecyclerAdapter.MyViewHolder> {
    private Activity context;
    private List<String> postData;
    private PostTopModel postTop;
    private Command_PopUp command_popUp;
    private int POST_TOP = -1;
    private int POST_ITEM = 1;

    public PostRecyclerAdapter(Activity context, List<String> postData) {
        this.context = context;
        this.postData = postData;
//        this.postTop = postTop;
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        MyViewHolder holder;
        if (viewType == POST_TOP) {
            holder = new MyViewHolder(LayoutInflater.from(context).inflate(R.layout.list_post_top_item, parent, false));
        } else {
            holder = new MyViewHolder(LayoutInflater.from(context).inflate(R.layout.list_post_item, parent, false));

        }
        return holder;
    }

    @Override
    public int getItemViewType(int position) {
        if (position == 0) {
            return POST_TOP;
        }
        return POST_ITEM;
    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, int position) {
        holder.tvLouCeng.setText("第" + (position + 1) + "楼");
        holder.ibMore.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                command_popUp = new Command_PopUp(context, Pop_onClick);
                command_popUp.showAtLocation(context.findViewById(R.id.post_main), Gravity.BOTTOM, 0, 0);
            }
        });
    }

    @Override
    public int getItemCount() {
        return postData.size() + 1;
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {
        TextView tvContent;
        TextView tvLouCeng;
        ImageButton ibMore;

        public MyViewHolder(View itemView) {
            super(itemView);
            tvContent = (TextView) itemView.findViewById(R.id.tv_content);
            tvLouCeng = (TextView) itemView.findViewById(R.id.tv_louceng);
            ibMore = (ImageButton) itemView.findViewById(R.id.ib_Command);
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
}
