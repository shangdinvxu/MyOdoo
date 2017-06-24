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
import tarce.model.inventory.OrderDetailBean;
import tarce.myodoo.R;
import tarce.myodoo.adapter.product.WriteFeedbackNumAdapter;
import tarce.myodoo.uiutil.InsertNumDialog;
import tarce.support.AlertAialogUtils;
import tarce.support.ToastUtils;
import tarce.support.ToolBarActivity;

/**
 * Created by rose.zou on 2017/6/5.
 * 填写退料页面
 */

public class WriteFeedMateriActivity extends ToolBarActivity {
    @InjectView(R.id.recycler_feed_material)
    RecyclerView recyclerFeedMaterial;
    @InjectView(R.id.tv_commit_feednum)
    TextView tvCommitFeednum;
    private OrderDetailBean.ResultBean.ResDataBean resDataBean;
    private WriteFeedbackNumAdapter adapter;
    private InsertNumDialog insertNumDialog;
    private InventoryApi inventoryApi;
    private int order_id;
    private String from;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_write_feedmater);
        ButterKnife.inject(this);

        setRecyclerview(recyclerFeedMaterial);
        Intent intent = getIntent();
        resDataBean = (OrderDetailBean.ResultBean.ResDataBean) intent.getSerializableExtra("recycler_data");
        order_id = intent.getIntExtra("order_id", 1);
        from = intent.getStringExtra("from");
        if (from.equals("look")){
            tvCommitFeednum.setText("确认退料数量");
        }
        inventoryApi = RetrofitClient.getInstance(WriteFeedMateriActivity.this).create(InventoryApi.class);
        initRecyc();
    }

    /**
     * 设置recycler
     * */
    private void initRecyc(){

        adapter = new WriteFeedbackNumAdapter(R.layout.adapter_write_feednum, resDataBean.getStock_move_lines());
        recyclerFeedMaterial.setAdapter(adapter);
        adapter.setOnItemClickListener(new BaseQuickAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(final BaseQuickAdapter adapter, View view, final int position) {
                insertNumDialog = new InsertNumDialog(WriteFeedMateriActivity.this, R.style.MyDialogStyle,
                        new InsertNumDialog.OnSendCommonClickListener() {
                            public double beiNum;//备料数量

                            @Override
                            public void OnSendCommonClick(int num) {
                                if (resDataBean.getState().equals("waiting_material")
                                        || resDataBean.getState().equals("prepare_material_ing")
                                        || resDataBean.getState().equals("finish_prepare_material")){
                                    beiNum = resDataBean.getStock_move_lines().get(position).getQuantity_ready() + resDataBean.getStock_move_lines().get(position).getQuantity_done();
                                }else {
                                    beiNum = resDataBean.getStock_move_lines().get(position).getQuantity_done();
                                }
                                // TODO: 2017/6/8 生产num/需求num*item的需求num
                                double v = resDataBean.getQty_produced() / resDataBean.getProduct_qty() * resDataBean.getStock_move_lines().get(position).getProduct_uom_qty();
                                if (num<=(beiNum-v)){
                                    resDataBean.getStock_move_lines().get(position).setReturn_qty(num);
                                    adapter.notifyDataSetChanged();
                                }else {
                                    ToastUtils.showCommonToast(WriteFeedMateriActivity.this, "退料过多");
                                }
                            }
                        },resDataBean.getStock_move_lines().get(position).getProduct_id(), position, resDataBean)
                .changeTitle("输入 "+resDataBean.getStock_move_lines().get(position).getProduct_id()+" 的退料数量");
                insertNumDialog.show();
            }
        });
    }

    @OnClick(R.id.tv_commit_feednum)
    void commitNum(View view){
        if (from.equals("check")){
            showDefultProgressDialog();
            HashMap<Object, Object> hashMap = new HashMap();
            hashMap.put("order_id",order_id);
            hashMap.put("is_check", 0);
            Map[] maps = new Map[resDataBean.getStock_move_lines().size()];
            for (int i = 0; i < resDataBean.getStock_move_lines().size(); i++) {
                Map<Object,Object> smallMap = new HashMap<>();
                smallMap.put("order_id",resDataBean.getStock_move_lines().get(i).getOrder_id());
                smallMap.put("product_tmpl_id",resDataBean.getStock_move_lines().get(i).getProduct_tmpl_id());
                smallMap.put("return_qty",resDataBean.getStock_move_lines().get(i).getReturn_qty());
                maps[i] = smallMap;
            }
            hashMap.put("stock_moves",maps);
            Call<OrderDetailBean> objectCall = inventoryApi.semiCommitReturn(hashMap);
            objectCall.enqueue(new MyCallback<OrderDetailBean>() {
                @Override
                public void onResponse(Call<OrderDetailBean> call, Response<OrderDetailBean> response) {
                    dismissDefultProgressDialog();
                    if (response.body() == null)return;
                    if (response.body().getError()!=null){
                        ToastUtils.showCommonToast(WriteFeedMateriActivity.this, response.body().getError().getMessage());
                        return;
                    }
                    if (response.body().getResult().getRes_data() != null && response.body().getResult().getRes_code() == 1){
                        ToastUtils.showCommonToast(WriteFeedMateriActivity.this, "提交退料成功");
                        Intent intent = new Intent(WriteFeedMateriActivity.this, ProductLlActivity.class);
                        intent.putExtra("name_activity","生产退料");
                        intent.putExtra("state_product","done");
                        startActivity(intent);
                        finish();
                    }else {
                        ToastUtils.showCommonToast(WriteFeedMateriActivity.this, "数据错误");
                    }
                }

                @Override
                public void onFailure(Call<OrderDetailBean> call, Throwable t) {
                    dismissDefultProgressDialog();
                    ToastUtils.showCommonToast(WriteFeedMateriActivity.this, t.toString());
                }
            });
        }else {
            AlertAialogUtils.getCommonDialog(WriteFeedMateriActivity.this,"是否确定提交")
                    .setPositiveButton("确定", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            showDefultProgressDialog();
                            HashMap<Object, Object> hashMap = new HashMap();
                            hashMap.put("order_id",order_id);
                            if (from.equals("look")){
                                hashMap.put("is_check", 1);
                            }else {
                                hashMap.put("is_check", 0);
                            }
                            Map[] maps = new Map[resDataBean.getStock_move_lines().size()];
                            for (int i = 0; i < resDataBean.getStock_move_lines().size(); i++) {
                                Map<Object,Object> smallMap = new HashMap<>();
                                smallMap.put("order_id",resDataBean.getStock_move_lines().get(i).getOrder_id());
                                smallMap.put("product_tmpl_id",resDataBean.getStock_move_lines().get(i).getProduct_tmpl_id());
                                smallMap.put("return_qty",resDataBean.getStock_move_lines().get(i).getReturn_qty());
                                maps[i] = smallMap;
                            }
                            hashMap.put("stock_moves",maps);
                            Call<OrderDetailBean> objectCall = inventoryApi.commitFeedNum(hashMap);
                            objectCall.enqueue(new MyCallback<OrderDetailBean>() {
                                @Override
                                public void onResponse(Call<OrderDetailBean> call, Response<OrderDetailBean> response) {
                                    dismissDefultProgressDialog();
                                    if (response.body() == null)return;
                                    if (response.body().getError()!=null){
                                        ToastUtils.showCommonToast(WriteFeedMateriActivity.this, response.body().getError().getMessage());
                                        return;
                                    }
                                    if (response.body().getResult().getRes_code() == 1 && response.body().getResult().getRes_data()!= null){
                                        Intent intent = new Intent(WriteFeedMateriActivity.this, ProductLlActivity.class);
                                        if (from.equals("look")){
                                            ToastUtils.showCommonToast(WriteFeedMateriActivity.this, "退料完成");
                                            intent.putExtra("name_activity","生产退料");
                                            intent.putExtra("state_product","waiting_warehouse_inspection");
                                        }else {
                                            ToastUtils.showCommonToast(WriteFeedMateriActivity.this, "提交退料完成");
                                            intent.putExtra("name_activity","退料");
                                            intent.putExtra("state_product","waiting_inventory_material");
                                        }
                                        startActivity(intent);
                                        finish();
                                    }else {
                                        ToastUtils.showCommonToast(WriteFeedMateriActivity.this, "数据错误");
                                    }
                                }

                                @Override
                                public void onFailure(Call<OrderDetailBean> call, Throwable t) {
                                    dismissDefultProgressDialog();
                                    ToastUtils.showCommonToast(WriteFeedMateriActivity.this, t.toString());
                                }
                            });
                        }
                    }).show();
        }
    }
}
