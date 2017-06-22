package tarce.myodoo.activity;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import com.chad.library.adapter.base.BaseQuickAdapter;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import butterknife.ButterKnife;
import butterknife.InjectView;
import butterknife.OnClick;
import retrofit2.Call;
import retrofit2.Response;
import tarce.api.MyCallback;
import tarce.api.RetrofitClient;
import tarce.api.api.InventoryApi;
import tarce.model.inventory.CommonBean;
import tarce.model.inventory.GetFeedbackBean;
import tarce.myodoo.R;
import tarce.myodoo.adapter.product.FeedbackAdapter;
import tarce.myodoo.uiutil.InsertFeedbackDial;
import tarce.support.AlertAialogUtils;
import tarce.support.ToastUtils;

/**
 * Created by zouzou on 2017/6/22.
 * 各种反馈页面
 */

public class FeedbackActivity extends BaseActivity {
    @InjectView(R.id.recycler_feedback)
    RecyclerView recyclerFeedback;
    @InjectView(R.id.tv_add_feedb)
    TextView tvAddFeedb;
    private InventoryApi inventoryApi;
    private FeedbackAdapter adapter;
    private List<GetFeedbackBean.ResultBean.ResDataBean> dataBeanList = new ArrayList<>();
    private int order_id;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_feedback);
        ButterKnife.inject(this);

        inventoryApi = RetrofitClient.getInstance(FeedbackActivity.this).create(InventoryApi.class);
        setTitle("选择缺料原因");
        setRecyclerview(recyclerFeedback);
        Intent intent = getIntent();
        order_id = intent.getIntExtra("order_id", 1000);
        initData();
    }

    /**
     * 获取原因列表
     * */
    private void initData() {
        showDefultProgressDialog();
        Call<GetFeedbackBean> remark = inventoryApi.getRemark(new HashMap());
        remark.enqueue(new MyCallback<GetFeedbackBean>() {
            @Override
            public void onResponse(Call<GetFeedbackBean> call, Response<GetFeedbackBean> response) {
                dismissDefultProgressDialog();
                if (response.body() == null)return;
                if (response.body().getResult().getRes_code() == 1){
                    dataBeanList = response.body().getResult().getRes_data();
                    adapter = new FeedbackAdapter(R.layout.adapter_feedback, dataBeanList);
                    recyclerFeedback.setAdapter(adapter);
                    initClick();
                }
            }
            @Override
            public void onFailure(Call<GetFeedbackBean> call, Throwable t) {
                dismissDefultProgressDialog();
                ToastUtils.showCommonToast(FeedbackActivity.this, t.toString());
            }
        });
    }

    //item点击事件
    private void initClick() {
        adapter.setOnItemClickListener(new BaseQuickAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(BaseQuickAdapter adapter, View view, final int position) {
                final List<GetFeedbackBean.ResultBean.ResDataBean> data = adapter.getData();
                AlertAialogUtils.getCommonDialog(FeedbackActivity.this, "是否提交："+data.get(position).getContent())
                        .setPositiveButton("确定", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                showDefultProgressDialog();
                                HashMap<Object,Object> hashMap = new HashMap<>();
                                hashMap.put("order_id",order_id);
                                hashMap.put("remark_id",data.get(position).getId());
                                Call<CommonBean> objectCall = inventoryApi.selectRemark(hashMap);
                                objectCall.enqueue(new MyCallback<CommonBean>() {
                                    @Override
                                    public void onResponse(Call<CommonBean> call, Response<CommonBean> response) {
                                        dismissDefultProgressDialog();
                                        if (response.body() == null)return;
                                        if (response.body().getResult().getRes_code() == 1){
                                            finish();
                                        }
                                    }

                                    @Override
                                    public void onFailure(Call<CommonBean> call, Throwable t) {
                                        dismissDefultProgressDialog();
                                        ToastUtils.showCommonToast(FeedbackActivity.this, t.toString());
                                    }
                                });
                            }
                        }).show();
            }
        });
    }

    @OnClick(R.id.tv_add_feedb)
    void addFeedback(View view){
        new InsertFeedbackDial(FeedbackActivity.this, R.style.MyDialogStyle, new InsertFeedbackDial.OnSendCommonClickListener() {
            @Override
            public void OnSendCommonClick(String num) {
                HashMap<Object, Object> hashMap = new HashMap<>();
                hashMap.put("content",num);
                Call<GetFeedbackBean> objectCall = inventoryApi.addNewRemark(hashMap);
                objectCall.enqueue(new MyCallback<GetFeedbackBean>() {
                    @Override
                    public void onResponse(Call<GetFeedbackBean> call, Response<GetFeedbackBean> response) {
                        dismissDefultProgressDialog();
                        if (response.body() == null)return;
                        if (response.body().getResult().getRes_code() == 1){
                            adapter.getData().add(response.body().getResult().getRes_data().get(response.body().getResult().getRes_data().size()-1));
                            adapter.notifyDataSetChanged();
                            initClick();
                        }
                    }

                    @Override
                    public void onFailure(Call<GetFeedbackBean> call, Throwable t) {
                        dismissDefultProgressDialog();
                    }
                });
            }
        }, "").changeHint("缺料原因").show();
    }
}
