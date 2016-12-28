package bw.com.yunifangstore.activity;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.zhy.autolayout.AutoLayoutActivity;

import org.xutils.ex.DbException;

import java.util.ArrayList;
import java.util.List;

import bw.com.yunifangstore.R;
import bw.com.yunifangstore.adapter.CommonAdapter;
import bw.com.yunifangstore.adapter.ViewHolder;
import bw.com.yunifangstore.bean.UserBean;
import bw.com.yunifangstore.utils.DBUtils;

public class AddressActivity extends AutoLayoutActivity implements View.OnClickListener {

    private ImageView address_news_back_but;
    private ListView adress_listView;
    private Button add_address;
    private ArrayList<UserBean> list = new ArrayList<>();
    private CommonAdapter<UserBean> commonAdapter;
    private ImageView check_check;
    private TextView address_news_baocun_tv;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        supportRequestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.activity_address);
        initView();
    }

    private void initView() {
        address_news_back_but = (ImageView) findViewById(R.id.address_news_back_but);
        address_news_back_but.setOnClickListener(this);
        adress_listView = (ListView) findViewById(R.id.adress_listView);
        add_address = (Button) findViewById(R.id.add_address);
        add_address.setOnClickListener(this);
        //管理
        address_news_baocun_tv = (TextView) findViewById(R.id.address_news_baocun_tv);
        address_news_baocun_tv.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            //退出
            case R.id.address_news_back_but:
                finish();
                overridePendingTransition(R.anim.login_in0, R.anim.login_out);
                break;
            //添加收货地址
            case R.id.add_address:
                Intent intent = new Intent(AddressActivity.this, ManageActivity.class);
                startActivity(intent);
                overridePendingTransition(R.anim.login_in, R.anim.login_in0);
                break;
            //管理
            case R.id.address_news_baocun_tv:

                break;


        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        try {
            List<UserBean> all = DBUtils.getDb().findAll(UserBean.class);
            if (all != null) {
                list.clear();
                list.addAll(all);
            }

        } catch (DbException e) {
            e.printStackTrace();
        }
        setAdapter();
        /**
         * 单击回跳
         */
        adress_listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                UserBean userBean = null;
                try {
                    for (int i = 0; i < list.size(); i++) {
                        if (i == position) {
                            list.get(position).setFlag(true);
                        } else {
                            list.get(i).setFlag(false);
                        }
                        userBean = list.get(i);
                        DBUtils.getDb().saveOrUpdate(userBean);
                    }

                } catch (DbException e) {
                    e.printStackTrace();
                }
                finish();
                overridePendingTransition(R.anim.login_in0, R.anim.login_out);
            }
        });
        //删除收货地址人
        adress_listView.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {
                dialogData(position);
                return true;
            }
        });
    }

    /**
     * 添加适配器
     */
    private void setAdapter() {
        commonAdapter = new CommonAdapter<UserBean>(AddressActivity.this, list, R.layout.address_lv_view) {
            @Override
            public void convert(ViewHolder helper, UserBean item) {
                helper.setText(R.id.check_name, item.getUserName());
                helper.setText(R.id.check_phone, item.getPhone());
                helper.setText(R.id.check_detail, item.getProvince() + item.getDetailAddress());
                check_check = helper.getView(R.id.check_check);
                if (item.isFlag()) {
                    check_check.setVisibility(View.VISIBLE);
                } else {
                    check_check.setVisibility(View.INVISIBLE);
                }
            }
        };
        adress_listView.setAdapter(commonAdapter);
    }

    /**
     * 显示删除地址的dialo框
     */
    private void dialogData(final int position) {
        AlertDialog.Builder builder = new AlertDialog.Builder(AddressActivity.this);
        builder.setMessage("是否删除此收货地址？");
        builder.setNegativeButton("确定", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                try {
                    DBUtils.getDb().delete(list.get(position));
                    list.remove(list.get(position));
                    commonAdapter.notifyDataSetChanged();
                } catch (DbException e) {
                    e.printStackTrace();
                }
            }

        });

        builder.setPositiveButton("取消", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
            }
        });
        builder.show();
    }
}
