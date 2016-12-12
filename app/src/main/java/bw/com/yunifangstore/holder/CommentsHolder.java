package bw.com.yunifangstore.holder;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import bw.com.yunifangstore.R;

/**
 * @author : 张鸿鹏
 * @date : 2016/12/11.
 */
public class CommentsHolder extends RecyclerView.ViewHolder{

    public  TextView comments_des;
    public  TextView comments_name;
    public  TextView comments_time;
    public  ImageView comments_iv1;
    public  ImageView comments_iv2;
    public  ImageView user_icon;

    public CommentsHolder(View itemView) {
        super(itemView);
        comments_des = (TextView) itemView.findViewById(R.id.comments_des);
        comments_name = (TextView) itemView.findViewById(R.id.comments_name);
        comments_time = (TextView) itemView.findViewById(R.id.comments_time);
        comments_iv1 = (ImageView) itemView.findViewById(R.id.comments_iv1);
        comments_iv2 = (ImageView) itemView.findViewById(R.id.comments_iv2);
        user_icon = (ImageView) itemView.findViewById(R.id.user_icon);
    }
}
