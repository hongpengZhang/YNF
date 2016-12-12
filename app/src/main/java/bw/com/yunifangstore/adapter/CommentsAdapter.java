package bw.com.yunifangstore.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.View;
import android.view.ViewGroup;

import com.nostra13.universalimageloader.core.ImageLoader;

import java.util.List;

import bw.com.yunifangstore.R;
import bw.com.yunifangstore.bean.DetailsData;
import bw.com.yunifangstore.holder.CommentsHolder;
import bw.com.yunifangstore.utils.CommonUtils;
import bw.com.yunifangstore.utils.ImageLoaderUtils;

/**
 * @author : 张鸿鹏
 * @date : 2016/12/11.
 */
public class CommentsAdapter extends RecyclerView.Adapter<CommentsHolder>{
    private List<DetailsData.DataBean.CommentsBean> commentsList;
    private Context context;

    public CommentsAdapter(List<DetailsData.DataBean.CommentsBean> commentsList, Context context) {
        this.commentsList = commentsList;
        this.context = context;
    }

    @Override
    public CommentsHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View inflate = CommonUtils.inflate(R.layout.comments_item);
        CommentsHolder commentsHolder=new CommentsHolder(inflate);
        return commentsHolder;
    }

    @Override
    public void onBindViewHolder(CommentsHolder holder, int position) {
        holder.comments_des.setText(commentsList.get(position).getContent());
        holder.comments_name.setText(commentsList.get(position).getUser().getNick_name());
        holder.comments_time.setText(commentsList.get(position).getCreatetime());
        ImageLoader.getInstance().displayImage(commentsList.get(position).getUser().getIcon(),holder.user_icon, ImageLoaderUtils.initCircleOptions());
        String normal_url = commentsList.get(position).getPicture().get(0).getNormal_url();
        String normal_url2 = commentsList.get(position).getPicture().get(1).getNormal_url();
        if (TextUtils.isEmpty(normal_url)){
          holder.comments_iv1.setVisibility(View.GONE);
        }else {
        ImageLoader.getInstance().displayImage(normal_url,holder.comments_iv1, ImageLoaderUtils.initOptions());
        }
        if (TextUtils.isEmpty(normal_url2)){
            holder.comments_iv2.setVisibility(View.GONE);
        }else {
            ImageLoader.getInstance().displayImage(normal_url2,holder.comments_iv1, ImageLoaderUtils.initOptions());
        }
    }

    @Override
    public int getItemCount() {
        return commentsList.size();
    }

}
