package com.assess15.code.recyclerView;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import com.assess15.code.R;

/**
 * 手动实现ViewHolder
 */
public class FeedAdapter extends RecyclerView.Adapter<FeedAdapter.FeedHolder> {

    @NonNull
    @Override
    public FeedHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.item_feed, viewGroup, false);
        return new FeedHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull FeedHolder feedHolder, int i) {
//        //用户头像
//        Picasso.with(feedHolder.itemView.getContext())
//                .load(getAvatarResId(i))
//                .centerInside()
//                .fit()
//                .into(feedHolder.ivAvatar);
//
//        //内容图片
//        Picasso.with(feedHolder.itemView.getContext())
//                .load(getContentResId(i))
//                .centerInside()
//                .fit()
//                .into(feedHolder.ivContent);

        feedHolder.tvContent.setText("测试数据" + i);
    }

    private int getAvatarResId(int position){
        switch (position % 4){
            case 0:
                return R.drawable.avatar1;
            case 1:
                return R.drawable.avatar2;
            case 2:
                return R.drawable.avatar3;
            case 3:
                return R.drawable.avatar4;
        }
        return 0;
    }

    public int getContentResId(int position){
        switch (position % 4){
            case 0:
                return R.drawable.taeyeon_one;
            case 1:
                return R.drawable.taeyeon_two;
            case 2:
                return R.drawable.taeyeon_three;
            case 3:
                return R.drawable.taeyeon_four;
        }
        return 0;
    }



    @Override
    public int getItemCount() {
        return 100;
    }

    public class FeedHolder extends RecyclerView.ViewHolder {

        ImageView ivAvatar;
        ImageView ivContent;
        TextView tvContent;

        public FeedHolder(@NonNull View itemView) {
            super(itemView);
            ivAvatar = itemView.findViewById(R.id.iv_avatar);
            ivContent = itemView.findViewById(R.id.iv_content);
            tvContent = itemView.findViewById(R.id.tv_nickname);
        }
    }
}
