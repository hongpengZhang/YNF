package bw.com.yunifangstore.holder;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

import bw.com.yunifangstore.R;

/**
 * @author : 张鸿鹏
 * @date : 2016/12/11.
 */

public class AttributesHolder extends RecyclerView.ViewHolder {

    public  TextView attr_name1;
    public  TextView attr_value1;

    public AttributesHolder(View itemView) {
        super(itemView);
        attr_name1 = (TextView) itemView.findViewById(R.id.attr_name1);
        attr_value1 = (TextView) itemView.findViewById(R.id.attr_value1);
    }




}
