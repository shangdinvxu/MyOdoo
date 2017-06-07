package tarce.myodoo.activity;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

import com.chad.library.adapter.base.BaseQuickAdapter;

import java.util.HashMap;
import java.util.Map;

import butterknife.ButterKnife;
import butterknife.InjectView;
import butterknife.OnClick;
import retrofit2.Call;
import retrofit2.Response;
import tarce.api.MyCallback;
import tarce.api.RetrofitClient;
import tarce.api.api.InventoryApi;
import tarce.model.BuLlBean;
import tarce.model.inventory.OrderDetailBean;
import tarce.myodoo.R;
import tarce.myodoo.adapter.product.BuGetLiaoAdapter;
import tarce.myodoo.uiutil.InsertNumDialog;
import tarce.support.AlertAialogUtils;
import tarce.support.ToastUtils;
import tarce.support.ToolBarActivity;

/**
 * Created by rose.zou on 2017/5/31.
 * 补领料页面
 */

public class BuGetLiaoActivity extends ToolBarActivity {
    @InjectView(R.id.recycler_bu_getliao)
    RecyclerView recyclerBuGetliao;
    @InjectView(R.id.tv_bottom_bu)
    TextView tvBottomBu;

    private OrderDetailBean.ResultBean.ResDataBean resDataBean;
    private BuGetLiaoAdapter adapter;
    private int order_id;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_bu_getliao);
        ButterKnife.inject(this);

        setTitle("补领料");
        setRecyclerview(recyclerBuGetliao);
        Intent intent = getIntent();
        order_id = intent.getIntExtra("order_id", 1);
        resDataBean = (OrderDetailBean.ResultBean.ResDataBean) intent.getSerializableExtra("value");
        String state = intent.getStringExtra("state");
        adapter = new BuGetLiaoAdapter(R.layout.bu_ll_adapter, resDataBean.getStock_move_lines(), state);
        recyclerBuGetliao.setAdapter(adapter);

        adapter.setOnItemClickListener(new BaseQuickAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(final BaseQuickAdapter adapter, View view, final int position) {
                new InsertNumDialog(BuGetLiaoActivity.this, R.style.MyDialogStyle, new InsertNumDialog.OnSendCommonClickListener() {
                    @Override
                    public void OnSendCommonClick(int num) {
                        if (num > resDataBean.getStock_move_lines().get(position).getQty_available()) {
                            ToastUtils.showCommonToast(BuGetLiaoActivity.this, "库存不足");
                        } else {
                            resDataBean.getStock_move_lines().get(position).setOver_picking_qty(num);
                            adapter.notifyDataSetChanged();
                        }
                    }
                }, resDataBean.getStock_move_lines().get(position).getProduct_id())
                        .changeTitle("填写 " + resDataBean.getStock_move_lines().get(position).getProduct_id() + "的补领料数量")
                        .dismissTip().show();
            }
        });
    }

    @OnClick(R.id.tv_bottom_bu)
    void buLl(View view) {
        AlertAialogUtils.getCommonDialog(BuGetLiaoActivity.this, "")
                .setMessage("是否确定")
                .setPositiveButton("确定", new DialogInterface.OnClickListener(){
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        showDefultProgressDialog();
                        InventoryApi inventoryApi = RetrofitClient.getInstance(BuGetLiaoActivity.this).create(InventoryApi.class);
                        HashMap<Object, Object> hashMap = new HashMap<>();
                        hashMap.put("order_id", order_id);
                        Map[] maps = new Map[resDataBean.getStock_move_lines().size()];
                        for (int i = 0; i < resDataBean.getStock_move_lines().size(); i++) {
                            Map<Object, Object> objectMap = new HashMap<>();
                            objectMap.put("order_id", resDataBean.getStock_move_lines().get(i).getOrder_id());
                            objectMap.put("over_picking_qty", resDataBean.getStock_move_lines().get(i).getOver_picking_qty());
                            objectMap.put("stock_move_lines_id", resDataBean.getStock_move_lines().get(i).getId());
                            maps[i] = objectMap;
                        }
                        hashMap.put("stock_moves", maps);
                        Call<BuLlBean> objectCall = inventoryApi.buLl(hashMap);
                        objectCall.enqueue(new MyCallback<BuLlBean>() {
                            @Override
                            public void onResponse(Call<BuLlBean> call, Response<BuLlBean> response) {
                                dismissDefultProgressDialog();
                                if (response.body() == null)return;
                                if (response.body().getResult().getRes_code() == 1){
                                    finish();
                                }
                            }

                            @Override
                            public void onFailure(Call<BuLlBean> call, Throwable t) {
                                dismissDefultProgressDialog();
                            }
                        });
                    }
                }).show();
    }
}
