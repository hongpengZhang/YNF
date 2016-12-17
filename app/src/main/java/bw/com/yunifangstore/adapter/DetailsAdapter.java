package bw.com.yunifangstore.adapter;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;

import com.nostra13.universalimageloader.core.ImageLoader;

import bw.com.yunifangstore.R;
import bw.com.yunifangstore.bean.LargePictureData;
import bw.com.yunifangstore.utils.CommonUtils;
import bw.com.yunifangstore.utils.ImageLoaderUtils;

import static bw.com.yunifangstore.R.id.detailpic_iv;

/**
 * @author : 张鸿鹏
 * @date : 2016/12/10.
 */
public class DetailsAdapter extends BaseAdapter {
    private LargePictureData[] largePictureDatas;
    private Context context;

    public DetailsAdapter(LargePictureData[] largePictureDatas, Context context) {
        this.largePictureDatas = largePictureDatas;
        this.context = context;
    }

    @Override
    public int getCount() {
        return largePictureDatas.length;
    }

    @Override
    public Object getItem(int position) {
        return null;
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder viewHolder = null;
        if (convertView == null) {
            convertView = CommonUtils.inflate(R.layout.detailpic_item);
            viewHolder = new ViewHolder(convertView);
            convertView.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolder) convertView.getTag();
        }
        ImageLoader.getInstance().displayImage(largePictureDatas[position].getUrl(),viewHolder.detailpiciv, ImageLoaderUtils.initOptions());

        return convertView;
    }

    public class ViewHolder {
        public final ImageView detailpiciv;
        public final View root;

        public ViewHolder(View root) {
            detailpiciv = (ImageView) root.findViewById(detailpic_iv);
            this.root = root;
        }
    }
}
