package bw.com.yunifangstore.activity;

import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.view.Window;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.zhy.autolayout.AutoLayoutActivity;

import org.xutils.ex.DbException;

import bw.com.yunifangstore.R;
import bw.com.yunifangstore.bean.UserBean;
import bw.com.yunifangstore.utils.DBUtils;


public class ManageActivity extends AutoLayoutActivity implements View.OnClickListener {

    private ImageView address_news_back_but;
    private TextView address_news_baocun_tv;
    private EditText address_news_shouhuoren_edit;
    private EditText address_news_phone_edit;
    private EditText address_news_shengshi_edit;
    private EditText address_news_detail_edit;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        supportRequestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.activity_manage);
        initView();
    }

    private void initView() {
        address_news_back_but = (ImageView) findViewById(R.id.address_news_back_but);
        address_news_back_but.setOnClickListener(this);
        address_news_baocun_tv = (TextView) findViewById(R.id.address_news_baocun_tv);
        address_news_baocun_tv.setOnClickListener(this);
        //收货人
        address_news_shouhuoren_edit = (EditText) findViewById(R.id.address_news_shouhuoren_edit);
        //电话
        address_news_phone_edit = (EditText) findViewById(R.id.address_news_phone_edit);
        //省份
        address_news_shengshi_edit = (EditText) findViewById(R.id.address_news_shengshi_edit);
        //详细地址
        address_news_detail_edit = (EditText) findViewById(R.id.address_news_detail_edit);
    }

    @Override
    public void onClick(View v) {

        switch (v.getId()) {
            //退出
            case R.id.address_news_back_but:
                finishActivity();
                break;
            //保存
            case R.id.address_news_baocun_tv:
                String shouhuoren = address_news_shouhuoren_edit.getText().toString().trim();
                String phone = address_news_phone_edit.getText().toString().trim();
                String shengshi = address_news_shengshi_edit.getText().toString().trim();
                String detail = address_news_detail_edit.getText().toString().trim();
                if (TextUtils.isEmpty(shouhuoren)) {
                    Toast.makeText(this, "请输入收货人", Toast.LENGTH_SHORT).show();
                    return;
                }
                if (TextUtils.isEmpty(phone)) {
                    Toast.makeText(this, "请输入联系电话", Toast.LENGTH_SHORT).show();
                    return;
                }
                if (TextUtils.isEmpty(shengshi)) {
                    Toast.makeText(this, "请输入省份", Toast.LENGTH_SHORT).show();
                    return;
                }
                if (TextUtils.isEmpty(detail)) {
                    Toast.makeText(this, "请输入详情地址", Toast.LENGTH_SHORT).show();
                    return;
                } else {
                    //保存数据库
                    try {
                        UserBean userBean = new UserBean(shouhuoren, phone, shengshi, detail,false);
                        DBUtils.getDb().saveOrUpdate(userBean);
                        Toast.makeText(this, "保存成功！", Toast.LENGTH_SHORT).show();
                        finishActivity();
                    } catch (DbException e) {
                        e.printStackTrace();
                    }
                }
                break;
        }
    }

    /**
     * 结束
     */
    private void finishActivity() {
        finish();
        overridePendingTransition(R.anim.login_in0, R.anim.login_out);
    }
}
