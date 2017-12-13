package tarce.myodoo.activity.outsourcing;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.text.InputType;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import java.util.HashMap;
import java.util.List;

import butterknife.ButterKnife;
import butterknife.InjectView;
import butterknife.OnClick;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import tarce.api.RetrofitClient;
import tarce.api.api.InventoryApi;
import tarce.model.inventory.OutsourceBean;
import tarce.myodoo.R;
import tarce.myodoo.activity.BaseActivity;
import tarce.myodoo.activity.inquiriesstock.StockDetailActivity;
import tarce.myodoo.activity.salesout.SalesDetailActivity;
import tarce.myodoo.uiutil.TipDialog;
import tarce.myodoo.utils.StringUtils;
import tarce.support.AlertAialogUtils;
import tarce.support.ToastUtils;

/**
 * Created by zouwansheng on 2017/11/10.
 */

public class OutSourceDetailActivity extends BaseActivity {
    @InjectView(R.id.tv_state_outsource)
    TextView tvStateOutsource;
    @InjectView(R.id.num_product)
    TextView numProduct;
    @InjectView(R.id.name_product)
    TextView nameProduct;
    @InjectView(R.id.origin_product)
    TextView originProduct;
    @InjectView(R.id.gongxu_product)
    TextView gongxuProduct;
    @InjectView(R.id.people_product_buy)
    TextView peopleProductBuy;
    @InjectView(R.id.name_product_supplier)
    TextView nameProductSupplier;
    @InjectView(R.id.contact_product)
    TextView contactProduct;
    @InjectView(R.id.phone_product)
    TextView phoneProduct;
    @InjectView(R.id.address_product)
    TextView addressProduct;
    @InjectView(R.id.tv_state_click)
    TextView tvStateClick;
    private OutsourceBean.ResultBean.ResDataBean resDataBean;
    private String state;
    private InventoryApi inventoryApi;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_outsorce_detail);
        ButterKnife.inject(this);

        inventoryApi = RetrofitClient.getInstance(OutSourceDetailActivity.this).create(InventoryApi.class);
        Intent intent = getIntent();
        resDataBean = (OutsourceBean.ResultBean.ResDataBean) intent.getSerializableExtra("data");
        state = resDataBean.getState();
        initView();
    }

    private void initView() {
        switch (state) {
            case "draft":
                tvStateOutsource.setText("等待外协");
                tvStateClick.setText("送去外协加工");
                break;
            case "out_ing":
                tvStateOutsource.setText("进行中外协");
                tvStateClick.setText("收货");
                break;
            case "done":
                tvStateOutsource.setText("已完成");
                break;
        }
        nameProduct.setText(resDataBean.getProduct_id().get(1) + "");
        numProduct.setText(resDataBean.getQty_produced() + "");
        originProduct.setText(resDataBean.getProduction_id().get(1) + "");
        nameProductSupplier.setText(resDataBean.getOutsourcing_supplier_id().get(1) + "");
        peopleProductBuy.setText(resDataBean.getPo_user_id()+"");
        List employeeList = (List) resDataBean.getEmployee_id();
        gongxuProduct.setText(employeeList.get(1)+"");
    }

    @OnClick(R.id.tv_state_click)
    void setTvStateClick(View view) {
        if (state.equals("draft")) {
            AlertAialogUtils.getCommonDialog(OutSourceDetailActivity.this, "确定送去外协加工？")
                    .setPositiveButton("确定", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialogInterface, int i) {
                            changeState(resDataBean.getQty_produced());
                        }
                    }).show();
        } else if (state.equals("out_ing")) {
            final EditText editText = new EditText(OutSourceDetailActivity.this);
            editText.setText(resDataBean.getQty_produced() + "");
            //editText.setInputType(InputType.TYPE_CLASS_NUMBER);
            editText.setSelection(editText.getText().length());
            AlertDialog.Builder dialog = AlertAialogUtils.getCommonDialog(OutSourceDetailActivity.this, "请填写完成数量");
            dialog.setView(editText)
                    .setPositiveButton("确定", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            double integer = Double.valueOf(editText.getText().toString());
                            if (integer ==0){
                                ToastUtils.showCommonToast(OutSourceDetailActivity.this, "完成数量不能为0");
                                return;
                            }
                            if (integer>resDataBean.getQty_produced()){
                                ToastUtils.showCommonToast(OutSourceDetailActivity.this, "不能超过原来的生产数量");
                                return;
                            }
                            changeState(integer);
                        }
                    }).show();
        }
    }

    private void changeState(double qty_produce) {
        showDefultProgressDialog();
        HashMap<Object, Object> hashMap = new HashMap<>();
        hashMap.put("order_id", resDataBean.getId());
        if (state.equals("draft")) {
            hashMap.put("state", "draft_to_out_ing");
        } else if (state.equals("out_ing")) {
            hashMap.put("state", "out_ing_to_done");
        }
        hashMap.put("qty_produced", qty_produce);
        final Call<OutsourceBean> resDataBeanCall = inventoryApi.changeOutsouceOrder(hashMap);
        resDataBeanCall.enqueue(new Callback<OutsourceBean>() {
            @Override
            public void onResponse(Call<OutsourceBean> call, Response<OutsourceBean> response) {
                dismissDefultProgressDialog();
                if (response.body() == null) return;
                if (response.body().getError()!=null){
                    new TipDialog(OutSourceDetailActivity.this, R.style.MyDialogStyle, response.body().getError().getData().getMessage())
                            .show();
                    return;
                }
                if (response.body().getResult().getRes_data()!=null && response.body().getResult().getRes_code()==1){
                    if (state.equals("draft")) {
                        new TipDialog(OutSourceDetailActivity.this, R.style.MyDialogStyle, "本单状态改变成功！")
                                .setTrue(new View.OnClickListener() {
                                    @Override
                                    public void onClick(View view) {
                                        finish();
                                    }
                                })
                                .show();
                    } else if (state.equals("out_ing")) {
                        new TipDialog(OutSourceDetailActivity.this, R.style.MyDialogStyle, "收货成功，等待品检！")
                                .setTrue(new View.OnClickListener() {
                                    @Override
                                    public void onClick(View view) {
                                        finish();
                                    }
                                })
                                .show();
                    }
                }
            }

            @Override
            public void onFailure(Call<OutsourceBean> call, Throwable t) {
                dismissDefultProgressDialog();
            }
        });
    }
}
