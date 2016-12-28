package bw.com.yunifangstore.fragment;

import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.BitmapDrawable;
import android.support.annotation.NonNull;
import android.view.Gravity;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.RadioButton;
import android.widget.TextView;
import android.widget.Toast;

import org.xutils.ex.DbException;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;

import bw.com.yunifangstore.R;
import bw.com.yunifangstore.activity.MainActivity;
import bw.com.yunifangstore.activity.OrderActivity;
import bw.com.yunifangstore.adapter.CommonAdapter;
import bw.com.yunifangstore.adapter.ViewHolder;
import bw.com.yunifangstore.base.BaseFragment;
import bw.com.yunifangstore.bean.DetailsData;
import bw.com.yunifangstore.intent.IntentDetailActivity;
import bw.com.yunifangstore.utils.CommonUtils;
import bw.com.yunifangstore.utils.DBUtils;
import bw.com.yunifangstore.view.MyListView;
import bw.com.yunifangstore.view.ShowingPage;


public class CartFragment extends BaseFragment implements View.OnClickListener {

    private TextView bianji;
    private Button bt_guang;
    private MyListView cart_myListView;
    private View empty_cartfragment;
    private LinearLayout allselecor_goods;
    private TextView tv_title;
    public boolean isEdit = true;
    public int isSelect = 0;
    public CommonAdapter<DetailsData.DataBean.GoodsBean> commonAdapter;
    private Button bt_jiesun;
    private ArrayList<DetailsData.DataBean.GoodsBean> beanList = new ArrayList<>();
    private TextView cart_sumMoney;
    private CheckBox all_selectGoods;
    private View inflate;
    private PopupWindow popupWindow;



    @Override
    public void onLoad() {
        CartFragment.this.showCurrentPage(ShowingPage.StateType.STATE_LOAD_SUCCESS);
    }

    @Override
    protected View createSuccessView() {
        inflate = initView();
        return inflate;
    }

    @Override
    public void onResume() {
        super.onResume();
        CartFragment.this.showCurrentPage(ShowingPage.StateType.STATE_LOAD_SUCCESS);
        try {
            List<DetailsData.DataBean.GoodsBean> all = DBUtils.getDb().findAll(DetailsData.DataBean.GoodsBean.class);
            if (all != null) {
                beanList.clear();
                beanList.addAll(all);
            }
        } catch (DbException e) {
            e.printStackTrace();
        }
        /**
         * 当再次进入时会重新设置
         */
        all_selectGoods.setChecked(false);
        setNoSelecor();
        getMoney();
        setMyCartAdapter();
    }


    /**
     * 添加数据适配器
     */
    private void setMyCartAdapter() {

        //为空时的逛一逛购物车
        if (beanList.size() == 0) {
            bianji.setText("编辑");
            bt_jiesun.setText("结算");
            tv_title.setText("购物车");
            isEdit = true;
            empty_cartfragment.setVisibility(View.VISIBLE);
            allselecor_goods.setVisibility(View.GONE);
            bianji.setVisibility(View.INVISIBLE);

        } else {
            empty_cartfragment.setVisibility(View.GONE);
            allselecor_goods.setVisibility(View.VISIBLE);
            bianji.setVisibility(View.VISIBLE);
            tv_title.setText("购物车(" + beanList.size() + ")");
            commonAdapter = new CommonAdapter<DetailsData.DataBean.GoodsBean>(getActivity(), beanList, R.layout.cart_item) {
                @Override
                public void convert(ViewHolder helper, final DetailsData.DataBean.GoodsBean item) {
                    View convertView = helper.getConvertView();
                    final int position = helper.getPosition();
                    //跳转到详情
                    convertView.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            IntentDetailActivity.intentDetailActivity(getActivity(), beanList.get(position).getId());
                        }
                    });

                    ImageView juan = helper.getView(R.id.juan);
                    TextView goods_nummber = helper.getView(R.id.goods_nummber);
                    ImageView iv_dijuan = helper.getView(R.id.iv_dijuan);

                    final TextView shoppingcar_alertNum = (TextView) convertView.findViewById(R.id.shoppingcar_alertNum);
                    ImageButton shoppingcar_alertSubtra = (ImageButton) convertView.findViewById(R.id.shoppingcar_alertSubtra);
                    ImageButton shoppingcar_alertAdd = (ImageButton) convertView.findViewById(R.id.shoppingcar_alertAdd);
                    shoppingcar_alertNum.setText(item.getGoodsCounts() + "");
                    //减
                    shoppingcar_alertSubtra.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            int count = item.getGoodsCounts() - 1;
                            if (count > 0) {
                                item.setGoodsCounts(count);
                                shoppingcar_alertNum.setText(count + "");
                            }
                        }
                    });
                    //加
                    shoppingcar_alertAdd.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            int count = item.getGoodsCounts() + 1;
                            item.setGoodsCounts(count);
                            shoppingcar_alertNum.setText(count + "");
                        }
                    });

                    //全选
                    final CheckBox cart_select = helper.getView(R.id.cart_select);
                    cart_select.setChecked(beanList.get(position).isClick());
                    cart_select.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            if (cart_select.isChecked()) {
                                beanList.get(position).setClick(true);
                                isSelect++;
                            } else {
                                beanList.get(position).setClick(false);
                                isSelect--;
                            }
                            if (isSelect == beanList.size()) {
                                all_selectGoods.setChecked(true);
                            } else {
                                all_selectGoods.setChecked(false);
                            }
                            getMoney();
                        }
                    });

                    LinearLayout cart_middle = (LinearLayout) convertView.findViewById(R.id.cart_middle);
                    if (isEdit) {
                        cart_middle.setVisibility(View.GONE);
                        goods_nummber.setVisibility(View.VISIBLE);
                        cart_sumMoney.setVisibility(View.VISIBLE);
                    } else {
                        goods_nummber.setVisibility(View.INVISIBLE);
                        cart_sumMoney.setVisibility(View.INVISIBLE);
                        cart_middle.setVisibility(View.VISIBLE);
                    }
                    helper.setImageByUrl(R.id.cart_image, item.getGoods_img());
                    helper.setText(R.id.goods_price, "￥" + item.getShop_price());
                    helper.setText(R.id.goods_nummber, "数量:" + item.getGoodsCounts() + "");
                    helper.setText(R.id.goods_name, item.getGoods_name());
                    if (item.isIs_coupon_allowed()) {
                        juan.setVisibility(View.VISIBLE);
                    } else {
                        juan.setVisibility(View.INVISIBLE);
                    }
                    if (item.isIs_coupon_allowed()) {
                        iv_dijuan.setVisibility(View.VISIBLE);
                    } else {
                        iv_dijuan.setVisibility(View.INVISIBLE);
                    }
                }
            };
            cart_myListView.setAdapter(commonAdapter);

        }
    }

    @NonNull
    private View initView() {
        View inflate = CommonUtils.inflate(R.layout.cartfragment_layout);
        ImageView but_title_left_image = (ImageView) inflate.findViewById(R.id.but_title_left_image);
        but_title_left_image.setVisibility(View.GONE);
        //编辑
        bianji = (TextView) inflate.findViewById(R.id.bianji);
        bianji.setOnClickListener(this);
        tv_title = (TextView) inflate.findViewById(R.id.tv_title);
        tv_title.setText("购物车");
        tv_title.setTextColor(Color.BLACK);
        bt_guang = (Button) inflate.findViewById(R.id.bt_guang);
        bt_guang.setOnClickListener(this);
        cart_myListView = (MyListView) inflate.findViewById(R.id.cart_myListView);
        empty_cartfragment = inflate.findViewById(R.id.empty_cartfragment);
        //全选布局
        allselecor_goods = (LinearLayout) inflate.findViewById(R.id.allselecor_goods);
        //结算按钮
        bt_jiesun = (Button) inflate.findViewById(R.id.bt_jiesun);
        bt_jiesun.setOnClickListener(this);
        //合计价钱
        cart_sumMoney = (TextView) inflate.findViewById(R.id.cart_sumMoney);
        //全选
        all_selectGoods = (CheckBox) inflate.findViewById(R.id.all_selectGoods);
        all_selectGoods.setOnClickListener(this);
        return inflate;
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            //逛一逛
            case R.id.bt_guang:
                //第一种跳转方式
               /* Intent intent = new Intent(getActivity(), MainActivity.class);
                intent.putExtra("cart", true);
                getActivity().startActivity(intent);*/
                RadioButton rb = (RadioButton) MainActivity.radioGroup.getChildAt(0);
                rb.setChecked(true);
                break;
            //编辑
            case R.id.bianji:
                if (isEdit) {
                    bianji.setText("完成");
                    bt_jiesun.setText("删除");
                } else {
                    bianji.setText("编辑");
                    bt_jiesun.setText("结算");
                }
                isEdit = !isEdit;
                commonAdapter.notifyDataSetChanged();
                break;
            //结算
            case R.id.bt_jiesun:
                SettlementMoney();
                break;
            //全选按钮
            case R.id.all_selectGoods:
                SelectButton();
                break;

        }
    }

    /**
     * 全选
     */
    private void SelectButton() {
        if (all_selectGoods.isChecked()) {
            for (int i = 0; i < beanList.size(); i++) {
                beanList.get(i).setClick(true);
            }
            isSelect = beanList.size();

        } else {
            //全部不选
            setNoSelecor();
        }
        getMoney();
        commonAdapter.notifyDataSetChanged();
    }

    /**
     * 结算按钮
     */
    private void SettlementMoney() {
        if (isSelect == 0) {
            Toast.makeText(getActivity(), "您还没有选中任何商品", Toast.LENGTH_SHORT).show();
        } else {
            if (bt_jiesun.getText().equals("删除")) {
                View inflate = initpopView();
                addGoodsCart(inflate);
            } else {
                //跳转到订单界面
                Intent intent = new Intent(getActivity(), OrderActivity.class);
                intent.putExtra("sum",getMoney());
                intent.putExtra("list", beanList);
                startActivity(intent);
                getActivity().overridePendingTransition(R.anim.login_in, R.anim.login_in0);

            }
        }
        commonAdapter.notifyDataSetChanged();
    }

    private View initpopView() {
        View inflate = CommonUtils.inflate(R.layout.delete_goods_popwindow);
        TextView sure_deletegoods = (TextView) inflate.findViewById(R.id.sure_deletegoods);
        TextView cancle_deletegoods = (TextView) inflate.findViewById(R.id.cancle_deletegoods);
        sure_deletegoods.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                for (int i = beanList.size() - 1; i >= 0; i--) {
                    if (beanList.get(i).isClick()) {
                        //删除数据库
                        try {
                            DBUtils.getDb().delete(beanList.get(i));
                            //删除集合
                            beanList.remove(beanList.get(i));
                        } catch (DbException e) {
                            e.printStackTrace();
                        }
                    }
                }
                Toast.makeText(getActivity(), "删除成功", Toast.LENGTH_SHORT).show();
                popupWindow.dismiss();
                setMyCartAdapter();
            }
        });
        cancle_deletegoods.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                popupWindow.dismiss();
            }
        });
        return inflate;
    }

    private void addGoodsCart(View view) {
        popupWindow = new PopupWindow(view, WindowManager.LayoutParams.MATCH_PARENT,
                WindowManager.LayoutParams.WRAP_CONTENT);
        // 设置popWindow弹出窗体可点击，这句话必须添加，并且是true
        popupWindow.setFocusable(true);
        popupWindow.setOutsideTouchable(true);
        popupWindow.setBackgroundDrawable(new BitmapDrawable());
        popupWindow.setAnimationStyle(R.style.deletepopwindow_style);
        // 在底部显示
        popupWindow.showAtLocation(inflate, Gravity.CENTER, 0, 0);

        backgroundAlpha(0.4f);
        popupWindow.setOnDismissListener(new PopupWindow.OnDismissListener() {
            @Override
            public void onDismiss() {
                backgroundAlpha(1);
            }
        });

    }

    public void backgroundAlpha(float bgAlpha) {
        WindowManager.LayoutParams lp = getActivity().getWindow().getAttributes();
        lp.alpha = bgAlpha; //0.0-1.0
        getActivity().getWindow().setAttributes(lp);

    }

    /**
     * 保存数据库并更新
     */
    @Override
    public void onPause() {
        super.onPause();
        try {
            DBUtils.getDb().saveOrUpdate(beanList);
        } catch (DbException e) {
            e.printStackTrace();
        }
    }

    /**
     * 非选
     */
    private void setNoSelecor() {
        for (int i = 0; i < beanList.size(); i++) {
            beanList.get(i).setClick(false);
        }
        isSelect = 0;
    }

    /**
     * 获取价格
     */
    public float getMoney() {

        float GoodsSum = 0;
        DecimalFormat df = new DecimalFormat("###.00");
        for (int j = 0; j < beanList.size(); j++) {
            if (beanList.get(j).isClick()) {
                GoodsSum = (float) (GoodsSum + (beanList.get(j).getShop_price() * beanList.get(j).getGoodsCounts()));
                cart_sumMoney.setText("总计 :￥" + df.format(GoodsSum));
            } else {
                cart_sumMoney.setText("总计 :￥" + df.format(GoodsSum));
            }

        }
        return GoodsSum;
    }
}
