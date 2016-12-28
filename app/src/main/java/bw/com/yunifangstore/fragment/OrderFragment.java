package bw.com.yunifangstore.fragment;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.liaoinstan.springview.container.DefaultHeader;
import com.liaoinstan.springview.widget.SpringView;

import org.xutils.ex.DbException;

import java.util.ArrayList;
import java.util.List;

import bw.com.yunifangstore.R;
import bw.com.yunifangstore.adapter.CommonAdapter;
import bw.com.yunifangstore.adapter.MyOrderDataAdapter;
import bw.com.yunifangstore.adapter.ViewHolder;
import bw.com.yunifangstore.base.BaseFragment;
import bw.com.yunifangstore.bean.OrderGoods;
import bw.com.yunifangstore.bean.OrderOutSideGoods;
import bw.com.yunifangstore.interfaceclass.OnMoneyChangeListenter;
import bw.com.yunifangstore.utils.CommonUtils;
import bw.com.yunifangstore.utils.DBUtils;
import bw.com.yunifangstore.view.ShowingPage;

import static bw.com.yunifangstore.utils.DBUtils.getDb;

/**
 * @author : 张鸿鹏
 * @date : 2016/12/26.
 */

public class OrderFragment extends BaseFragment implements SpringView.OnFreshListener {

    private ArrayList<OrderOutSideGoods> list = new ArrayList<>();
    private SpringView myorder_springView;
    private ListView myorder_listView;
    private CommonAdapter<OrderOutSideGoods> adapter;
    private boolean order = false;

    @Override
    public void onLoad() {
        OrderFragment.this.showCurrentPage(ShowingPage.StateType.STATE_LOAD_SUCCESS);
    }

    @Override
    protected View createSuccessView() {
        View inflate = initView();
        String orderType = getArguments().getString("orderType");
        List<OrderOutSideGoods> all = null;
        try {
            if (orderType.equals("全部")) {
                all = DBUtils.getDb().findAll(OrderOutSideGoods.class);
            } else {
                all = getDb().selector(OrderOutSideGoods.class).where("INDENT_TYPE", "=", orderType).findAll();
            }
            if (all != null) {
                list.clear();
                list.addAll(all);
            }else {
                OrderFragment.this.showCurrentPage(ShowingPage.StateType.STATE_LOAD_SUCCESS);
            }
        } catch (DbException e) {
            e.printStackTrace();
        }

        setAdapter();
        return inflate;
    }

    /**
     * 添加适配器
     */
    private void setAdapter() {
        //取消订单
        adapter = new CommonAdapter<OrderOutSideGoods>(getActivity(), list, R.layout.myorder_item) {

            @Override
            public void convert(final ViewHolder helper, OrderOutSideGoods item) {

                final int position = helper.getPosition();
                TextView delete_order = helper.getView(R.id.delete_order);
                if (item.getOrderType().equals("交易关闭")) {
                    delete_order.setVisibility(View.VISIBLE);
                    helper.getView(R.id.cancel_order).setVisibility(View.GONE);
                    helper.getView(R.id.pay_order).setVisibility(View.GONE);
                    //删除订单
                    delete_order.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            dialogData(position, "确定删除这个订单吗?");
                        }
                    });
                } else {
                    delete_order.setVisibility(View.GONE);
                    helper.getView(R.id.cancel_order).setVisibility(View.VISIBLE);
                    helper.getView(R.id.pay_order).setVisibility(View.VISIBLE);
                }
                setData(helper, item, position);

            }

            private void setData(final ViewHolder helper, OrderOutSideGoods item, final int position) {
                List<OrderGoods> goodsList = item.getGoodsList();
                helper.setText(R.id.status_order, item.getOrderType());
                ListView listView_item = helper.getView(R.id.listView_item);

                MyOrderDataAdapter myOrderDataAdapter = new MyOrderDataAdapter(getActivity(), goodsList, R.layout.myorder_listview_item, new OnMoneyChangeListenter() {
                    @Override
                    public void setMoneyChange(float sum, int count) {
                        helper.setText(R.id.gong_price, "共:" + count + "件商品 合计:￥" + sum);
                    }
                });
                //取消订单
                helper.getView(R.id.cancel_order).setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        dialogData(position, "确定取消这个订单吗?");
                    }
                });
                listView_item.setAdapter(myOrderDataAdapter);
            }
        };
        myorder_listView.setAdapter(adapter);
    }

    private void dialogData(final int position, final String data) {
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());

        builder.setMessage(data);
        builder.setNegativeButton("确定", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                if (data.equals("确定取消这个订单吗?")) {
                    try {
                        OrderOutSideGoods orderOutSideGoods = list.get(position);
                        orderOutSideGoods.setOrderType("交易关闭");
                        DBUtils.getDb().saveOrUpdate(orderOutSideGoods);
                        list.remove(position);
                    } catch (DbException e) {
                        e.printStackTrace();
                    }
                }
                if (data.equals("确定删除这个订单吗?")) {
                    try {
                        DBUtils.getDb().delete(list.get(position));
                        list.remove(list.get(position));
                    Toast.makeText(getActivity(), "删除", Toast.LENGTH_SHORT).show();
                    } catch (DbException e) {
                        e.printStackTrace();
                    }
                }
                adapter.notifyDataSetChanged();
            }
        });

        builder.setPositiveButton("取消", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {

            }
        });
        builder.show();
        adapter.notifyDataSetChanged();
    }

    /**
     * 初始化控件
     */
    private View initView() {
        View inflate = CommonUtils.inflate(R.layout.orderfragment_layout);
        myorder_springView = (SpringView) inflate.findViewById(R.id.myorder_SpringView);
        myorder_springView.setListener(this);
        myorder_springView.setHeader(new DefaultHeader(getActivity()));
        myorder_springView.setType(SpringView.Type.FOLLOW);
        myorder_listView = (ListView) inflate.findViewById(R.id.myorder_listView);
        return inflate;
    }


    public static OrderFragment getStatus(String orderType) {
        OrderFragment orderFragment = new OrderFragment();
        Bundle bundle = new Bundle();
        bundle.putString("orderType", orderType);
        orderFragment.setArguments(bundle);
        return orderFragment;

    }

    @Override
    public void onRefresh() {
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                myorder_springView.onFinishFreshAndLoad();
            }
        }, 2000);
    }

    @Override
    public void onLoadmore() {

    }
}
