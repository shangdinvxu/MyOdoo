package tarce.myodoo.activity.salesout;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.chad.library.adapter.base.BaseQuickAdapter;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import butterknife.ButterKnife;
import butterknife.InjectView;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import tarce.api.RetrofitClient;
import tarce.api.api.InventoryApi;
import tarce.model.GetSaleResponse;
import tarce.model.inventory.CommonBean;
import tarce.myodoo.R;
import tarce.myodoo.activity.BaseActivity;
import tarce.myodoo.adapter.PingkingListAdapter;
import tarce.myodoo.uiutil.TipDialog;
import tarce.myodoo.utils.UserManager;
import tarce.support.AlertAialogUtils;
import tarce.support.MyLog;
import tarce.support.ToastUtils;

/**
 * Created by zouwansheng on 2018/4/11.
 */

public class ReserveListActivity extends BaseActivity {
    @InjectView(R.id.recycler_reserve)
    RecyclerView recyclerReserve;
    private List<GetSaleResponse.TResult.TRes_data.TPack_operation_product_ids.ReservedPingking> pingkingList;
    private PingkingListAdapter pingkingListAdapter;
    private InventoryApi inventoryApi;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_reservelist);
        ButterKnife.inject(this);

        inventoryApi = RetrofitClient.getInstance(ReserveListActivity.this).create(InventoryApi.class);
        setRecyclerview(recyclerReserve);
        Intent intent = getIntent();
        pingkingList = (List<GetSaleResponse.TResult.TRes_data.TPack_operation_product_ids.ReservedPingking>) intent.getSerializableExtra("data");
        initView();
    }

    private void initView() {
        pingkingListAdapter = new PingkingListAdapter(R.layout.item_pingkinglist, pingkingList);
        recyclerReserve.setAdapter(pingkingListAdapter);
        pingkingListAdapter.setOnItemClickListener(new BaseQuickAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(BaseQuickAdapter adapter, View view, int position) {
                final GetSaleResponse.TResult.TRes_data.TPack_operation_product_ids.ReservedPingking data = pingkingList.get(position);
                final EditText editText = new EditText(ReserveListActivity.this);
                final double qty_done = data.getQty_done();
                editText.setText(qty_done + "");
                if (qty_done<0){
                    editText.setText("0");
                }
                editText.setSelection(editText.getText().length());
                AlertDialog.Builder dialog = AlertAialogUtils.getCommonDialog(ReserveListActivity.this, "修改" + data.getProduct_name() + "备货数量");
                dialog.setView(editText)
                        .setPositiveButton("确定", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                double parseDouble = Double.parseDouble(editText.getText().toString());
                                if (parseDouble>qty_done){
                                    ToastUtils.showCommonToast(ReserveListActivity.this, "不可超过备货数量");
                                    return;
                                }
                                showDefultProgressDialog();
                                HashMap<Object, Object> objectObjectHashMap = new HashMap<>();
                                objectObjectHashMap.put("pack_id", data.getId());
                                objectObjectHashMap.put("new_qty_done", parseDouble);
                                Call<CommonBean> getReassignCall = inventoryApi.getReassignPackDone(objectObjectHashMap);
                                getReassignCall.enqueue(new Callback<CommonBean>() {
                                    @Override
                                    public void onResponse(Call<CommonBean> call, Response<CommonBean> response) {
                                        dismissDefultProgressDialog();
                                        if (response.body()==null)return;
                                        if (response.body().getError() != null) {
                                            new TipDialog(ReserveListActivity.this, R.style.MyDialogStyle, response.body().getError().getData().getMessage())
                                                    .show();
                                            return;
                                        }
                                        if (response.body().getResult().getRes_code() == 1) {
                                            data.setQty_done(Double.parseDouble(editText.getText().toString()));
                                            pingkingListAdapter.notifyDataSetChanged();
                                        } else if (response.body().getResult().getRes_code() == -1) {
                                            ToastUtils.showCommonToast(ReserveListActivity.this, response.body().getResult().getRes_data().getError() + "");
                                        }
                                    }

                                    @Override
                                    public void onFailure(Call<CommonBean> call, Throwable t) {
                                        dismissDefultProgressDialog();
                                    }
                                });
                                // obj.setQty_done(Double.parseDouble(editText.getText().toString()));
                                // salesDetailAdapter.notifyDataSetChanged();
                            }
                        }).show();
            }
        });
    }
}
