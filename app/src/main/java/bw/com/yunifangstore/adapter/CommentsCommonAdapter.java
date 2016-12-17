package bw.com.yunifangstore.adapter;

import android.content.Context;
import android.widget.ImageView;

import com.nostra13.universalimageloader.core.ImageLoader;

import java.util.List;

import bw.com.yunifangstore.R;
import bw.com.yunifangstore.bean.DetailsData;
import bw.com.yunifangstore.utils.ImageLoaderUtils;

/**
 * @author : 张鸿鹏
 * @date : 2016/12/13.
 */
public class CommentsCommonAdapter extends CommonAdapter<DetailsData.DataBean.CommentsBean>{

    public CommentsCommonAdapter(Context context, List<DetailsData.DataBean.CommentsBean> mDatas, int itemLayoutId) {
        super(context, mDatas, itemLayoutId);
    }

    @Override
    public void convert(ViewHolder helper, DetailsData.DataBean.CommentsBean item) {
        helper.setText(R.id.comments_des, item.getContent());
        helper.setText(R.id.comments_name, item.getUser().getNick_name());
        helper.setText(R.id.comments_time, item.getCreatetime());
        ImageView user_icon = helper.getView(R.id.user_icon);
        ImageLoader.getInstance().displayImage(item.getUser().getIcon(), user_icon, ImageLoaderUtils.initCircleOptions());

    }
}
