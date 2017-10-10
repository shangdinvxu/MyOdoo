package tarce.myodoo.activity.moreproduce;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.RecyclerView;
import android.text.InputType;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import com.chad.library.adapter.base.BaseQuickAdapter;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
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
import tarce.myodoo.activity.BaseActivity;
import tarce.myodoo.adapter.DoneAdapter;
import tarce.myodoo.uiutil.TipDialog;
import tarce.myodoo.utils.StringUtils;
import tarce.support.AlertAialogUtils;
import tarce.support.ToastUtils;

/**
 * Created by zouwansheng on 2017/9/22.
 */

public class MoreProduceActicity extends BaseActivity {
    @InjectView(R.id.recycler_feedback)
    RecyclerView recyclerFeedback;
    @InjectView(R.id.tv_add_feedb)
    TextView tvAddFeedb;
    @InjectView(R.id.recycler_feedback_semi)
    RecyclerView recyclerFeedbackSemi;
    @InjectView(R.id.recycler_feedback_liu)
    RecyclerView recyclerFeedbackLiu;
    private List<OrderDetailBean.ResultBean.ResDataBean.DoneStockMovBean> doneStockMovBean;
    private List<OrderDetailBean.ResultBean.ResDataBean.DoneStockMovBean> list_one = new ArrayList<>();
    private List<OrderDetailBean.ResultBean.ResDataBean.DoneStockMovBean> list_two = new ArrayList<>();
    private List<OrderDetailBean.ResultBean.ResDataBean.DoneStockMovBean> list_three = new ArrayList<>();
    private DoneAdapter doneAdapter;
    private DoneAdapter doneAdapter_one;
    private DoneAdapter doneAdapter_two;
    private DoneAdapter doneAdapter_three;
    private int order_id;
    private InventoryApi inventoryApi;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_moreproduce);
        ButterKnife.inject(this);
        setTitle("产出");

        inventoryApi = RetrofitClient.getInstance(MoreProduceActicity.this).create(InventoryApi.class);
        Intent intent = getIntent();
        doneStockMovBean = (List<OrderDetailBean.ResultBean.ResDataBean.DoneStockMovBean>) intent.getSerializableExtra("bean");
        order_id = intent.getIntExtra("id", 1);
        setRecyclerview(recyclerFeedback);
        setRecyclerview(recyclerFeedbackLiu);
        setRecyclerview(recyclerFeedbackSemi);
        for (int i = 0; i < doneStockMovBean.size(); i++) {
            if (doneStockMovBean.get(i).getProduct_type().equals("material")) {
                list_one.add(doneStockMovBean.get(i));
            } else if (doneStockMovBean.get(i).getProduct_type().equals("real_semi_finished")) {
                list_two.add(doneStockMovBean.get(i));
            } else {
                list_three.add(doneStockMovBean.get(i));
            }
        }
       // doneAdapter = new DoneAdapter(R.layout.item_done_adapter, doneStockMovBean);
        doneAdapter_one = new DoneAdapter(R.layout.item_done_adapter, list_one, true);
        doneAdapter_two = new DoneAdapter(R.layout.item_done_adapter, list_two, true);
        doneAdapter_three = new DoneAdapter(R.layout.item_done_adapter, list_three, true);
        recyclerFeedback.setAdapter(doneAdapter_one);
        recyclerFeedbackSemi.setAdapter(doneAdapter_two);
        recyclerFeedbackLiu.setAdapter(doneAdapter_three);
        initItem(doneAdapter_one);
        initItem(doneAdapter_two);
        initItem(doneAdapter_three);
    }

    private void initItem(final DoneAdapter weAdapter) {
        weAdapter.setOnItemClickListener(new BaseQuickAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(BaseQuickAdapter adapter, View view, final int position) {
                final EditText editText = new EditText(MoreProduceActicity.this);
                editText.setSelection(editText.getText().length());
                editText.setInputType(InputType.TYPE_CLASS_NUMBER);
                AlertDialog.Builder dialog = AlertAialogUtils.getCommonDialog(MoreProduceActicity.this, "输入" + weAdapter.getData().get(position).getProduct_id() + "产出数量");
                dialog.setView(editText)
                        .setPositiveButton("确定", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                if (StringUtils.isNullOrEmpty(editText.getText().toString())) {
                                    ToastUtils.showCommonToast(MoreProduceActicity.this, "未输入数量");
                                    return;
                                }
                                weAdapter.getData().get(position).setNow_num(Integer.parseInt(editText.getText().toString()));
                                weAdapter.notifyDataSetChanged();
                            }
                        }).show();
            }
        });
    }

    //确认产出
    @OnClick(R.id.tv_add_feedb)
    void addProduce() {
        doneStockMovBean = new ArrayList<>();
        doneStockMovBean.addAll(doneAdapter_one.getData());
        doneStockMovBean.addAll(doneAdapter_two.getData());
        doneStockMovBean.addAll(doneAdapter_three.getData());
        boolean isTrue = false;
        for (int i = 0; i < doneStockMovBean.size(); i++) {
            if (doneStockMovBean.get(i).getNow_num() > 0) {
                isTrue = true;
                break;
            }
        }
        if (isTrue) {
            AlertAialogUtils.getCommonDialog(MoreProduceActicity.this, "是否确认产出？")
                    .setPositiveButton("确定", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialogInterface, int i) {
                            showDefultProgressDialog();
                            HashMap<Object, Object> hashMap = new HashMap<>();
                            hashMap.put("order_id", order_id);
                            List<Map<Object, Object>> num = new ArrayList<>();
                            for (int j = 0; j < doneStockMovBean.size(); j++) {
                                Map<Object, Object> map = new HashMap<>();
                                map.put("id", doneStockMovBean.get(j).getId());
                                map.put("now_num", doneStockMovBean.get(j).getNow_num());
                                num.add(map);
                            }
                            hashMap.put("produce_qty", num);
                            Call<OrderDetailBean> objectCall = inventoryApi.checkOut(hashMap);
                            objectCall.enqueue(new MyCallback<OrderDetailBean>() {
                                @Override
                                public void onResponse(Call<OrderDetailBean> call, Response<OrderDetailBean> response) {
                                    dismissDefultProgressDialog();
                                    if (response.body() == null) return;
                                    if (response.body().getError() != null) {
                                        new TipDialog(MoreProduceActicity.this, R.style.MyDialogStyle, response.body().getError().getData().getMessage())
                                                .show();
                                        return;
                                    }
                                    if (response.body().getResult().getRes_code() == 1 && response.body().getResult().getRes_data() != null) {
                                        ToastUtils.showCommonToast(MoreProduceActicity.this, "产出提交成功");
                                        finish();
                                    } else if (response.body().getResult().getRes_data() != null && response.body().getResult().getRes_code() == -1) {
                                        ToastUtils.showCommonToast(MoreProduceActicity.this, response.body().getResult().getRes_data().getError());
                                    }
                                }

                                @Override
                                public void onFailure(Call<OrderDetailBean> call, Throwable t) {
                                    //  ToastUtils.showCommonToast(ProductingActivity.this, t.toString());
                                    dismissDefultProgressDialog();
                                }
                            });
                        }
                    }).show();
        } else {
            ToastUtils.showCommonToast(MoreProduceActicity.this, "请输入产出数量");
        }
    }
}
