package tarce.myodoo.activity.engineer;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.RecyclerView;
import android.text.InputType;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
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
import tarce.myodoo.R;
import tarce.myodoo.activity.BaseActivity;
import tarce.myodoo.activity.salesout.SalesDetailActivity;
import tarce.myodoo.adapter.SalesDetailAdapter;
import tarce.myodoo.uiutil.FullyLinearLayoutManager;
import tarce.myodoo.uiutil.TipDialog;
import tarce.myodoo.utils.StringUtils;
import tarce.support.AlertAialogUtils;
import tarce.support.MyLog;
import tarce.support.TimeUtils;
import tarce.support.ToastUtils;

/**
 * Created by zouwansheng on 2017/9/6.
 */

public class ProjectDetailActivity extends BaseActivity {

    @InjectView(R.id.tv_auto_num)
    TextView tvAutoNum;
    @InjectView(R.id.top_imageview)
    ImageView topImageview;
    @InjectView(R.id.tv_print)
    TextView tvPrint;
    @InjectView(R.id.partner)
    TextView partner;
    @InjectView(R.id.time)
    TextView time;
    @InjectView(R.id.states)
    TextView states;
    @InjectView(R.id.origin_documents)
    TextView originDocuments;
    @InjectView(R.id.sales_out)
    TextView salesOut;
    @InjectView(R.id.remarks)
    EditText remarks;
    @InjectView(R.id.framelayout)
    FrameLayout framelayout;
    @InjectView(R.id.camera_buttom_linearlayout)
    LinearLayout cameraButtomLinearlayout;
    @InjectView(R.id.recyclerview)
    RecyclerView recyclerview;
    @InjectView(R.id.buttom_button1)
    Button buttomButton1;
    @InjectView(R.id.buttom_button2)
    Button buttomButton2;
    @InjectView(R.id.buttom_button3)
    Button buttomButton3;
    @InjectView(R.id.buttom_button4)
    Button buttomButton4;
    @InjectView(R.id.linear_bottom)
    LinearLayout linearBottom;
    private InventoryApi inventoryApi;
    private GetSaleResponse.TResult.TRes_data bundle1;
    private String saleName;
    private SalesDetailAdapter salesDetailAdapter;
    private String model_state = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sales_detial);
        ButterKnife.inject(this);

        topImageview.setFocusableInTouchMode(true);
        topImageview.requestFocus();
        inventoryApi = RetrofitClient.getInstance(ProjectDetailActivity.this).create(InventoryApi.class);
        recyclerview.setLayoutManager(new FullyLinearLayoutManager(ProjectDetailActivity.this));
        recyclerview.addItemDecoration(new DividerItemDecoration(ProjectDetailActivity.this,
                DividerItemDecoration.VERTICAL));
        recyclerview.setNestedScrollingEnabled(false);
        initIntent();
    }

    private void initIntent() {
        Intent intent = getIntent();
        Bundle bundle = intent.getBundleExtra("intent");
        bundle1 = (GetSaleResponse.TResult.TRes_data) bundle.getSerializable("bundle");
        saleName = bundle1.getName();
        setTitle(saleName);
        refreshView(bundle1);
    }

    private void refreshView(GetSaleResponse.TResult.TRes_data bundle1) {
        partner.setText(bundle1.getParnter_id());
        time.setText(TimeUtils.utc2Local(bundle1.getMin_date()));
        states.setText(StringUtils.switchString(bundle1.getState()));
        originDocuments.setText(bundle1.getOrigin());
        if (bundle1.getDelivery_rule() != null) {
            salesOut.setText(StringUtils.switchString((String) bundle1.getDelivery_rule()));
        } else {
            salesOut.setText("");
        }
        remarks.setText(String.valueOf(bundle1.getSale_note()));
        List<GetSaleResponse.TResult.TRes_data.TPack_operation_product_ids> pack_operation_product_ids = bundle1.getPack_operation_product_ids();
        salesDetailAdapter = new SalesDetailAdapter(R.layout.salesout_detail_adapter_item, pack_operation_product_ids);
        recyclerview.setAdapter(salesDetailAdapter);
        buttomButton1.setText("开始备料");
        buttomButton1.setBackgroundResource(R.color.violet);
        buttomButton1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertAialogUtils.getCommonDialog(ProjectDetailActivity.this, "确定开始备货？")
                        .setPositiveButton("确定", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                //开始备货
                                refreshButtom("备料完成");
                                model_state = "change";
                                initListener();
                            }
                        }).show();
            }
        });
    }

    private void refreshButtom(String state) {
        switch (state){
            case "备料完成":
                buttomButton1.setVisibility(View.VISIBLE);
                buttomButton1.setText("备料完成");
                buttomButton1.setBackgroundResource(R.color.color_5693f8);
                buttomButton1.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        final List<GetSaleResponse.TResult.TRes_data.TPack_operation_product_ids> data = salesDetailAdapter.getData();
                        List<GetSaleResponse.TResult.TRes_data.TPack_operation_product_ids> subdata = new ArrayList<>();
                        for (int i = 0; i < data.size(); i++) {
                            if (data.get(i).getPack_id() == -1) {
                                subdata.add(data.get(i));
                            }
                        }
                        data.removeAll(subdata);
                        int sum = 0;
                        for (int i = 0; i < data.size(); i++) {
                            sum = sum + data.get(i).getQty_done();
                        }
                        if (sum == 0) {
                            ToastUtils.showCommonToast(ProjectDetailActivity.this, "请检查完成数量");
                            return;
                        }
                        AlertAialogUtils.getCommonDialog(ProjectDetailActivity.this, "是否确定备料完成？")
                                .setPositiveButton("确定", new DialogInterface.OnClickListener() {
                                    @Override
                                    public void onClick(DialogInterface dialog, int which) {
                                        HashMap<Object, Object> objectObjectHashMap = new HashMap<>();
                                        bundle1.setPack_operation_product_ids(data);
                                        boolean threeOrFive = false;
                                        for (int i = 0; i < data.size(); i++) {
                                            if (data.get(i).getQty_done() > 0) {
                                                threeOrFive = false;
                                            } else {
                                                threeOrFive = true;
                                                break;
                                            }
                                        }
                                        if (threeOrFive) {
                                            objectObjectHashMap.put("state", "process");
                                            objectObjectHashMap.put("picking_id", bundle1.getPicking_id());
                                            objectObjectHashMap.put("pack_operation_product_ids", data);
                                        } else {
                                            objectObjectHashMap.put("state", "process");
                                            objectObjectHashMap.put("picking_id", bundle1.getPicking_id());
                                            objectObjectHashMap.put("pack_operation_product_ids", data);
                                            objectObjectHashMap.put("qc_img", bundle1.getQc_img());
                                            objectObjectHashMap.put("qc_note", "yes");
                                        }
                                        Call<GetSaleResponse> getSaleResponseCall = inventoryApi.changeStockPicking(objectObjectHashMap);
                                        showDefultProgressDialog();
                                        getSaleResponseCall.enqueue(new Callback<GetSaleResponse>() {
                                            @Override
                                            public void onResponse(Call<GetSaleResponse> call, final Response<GetSaleResponse> response) {
                                                dismissDefultProgressDialog();
                                                if (response.body() == null || response.body().getResult() == null)
                                                    return;
                                                if (response.body().getError() != null) {
                                                    new TipDialog(ProjectDetailActivity.this, R.style.MyDialogStyle, response.body().getError().getMessage())
                                                            .show();
                                                    return;
                                                }
                                                if (response.body().getResult().getRes_code() == 1) {

                                                } else if (response.body().getResult().getRes_code() == -1) {
                                                    ToastUtils.showCommonToast(ProjectDetailActivity.this, response.body().getResult().getRes_data().getError() + "");
                                                }
                                            }

                                            @Override
                                            public void onFailure(Call<GetSaleResponse> call, Throwable t) {
                                                dismissDefultProgressDialog();
                                                MyLog.e("", t.toString());
                                            }
                                        });
                                    }
                                }).show();
                    }
                });
                break;
        }
    }

    private void initListener() {
        salesDetailAdapter.setOnItemClickListener(new BaseQuickAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(BaseQuickAdapter adapter, View view, int position) {
                if (position > adapter.getData().size() - 1) return;
                final GetSaleResponse.TResult.TRes_data.TPack_operation_product_ids obj =
                        (GetSaleResponse.TResult.TRes_data.TPack_operation_product_ids) adapter.getData().get(position);
                if (obj.getPack_id() == -1) return;
                final EditText editText = new EditText(ProjectDetailActivity.this);
                final Integer qty_available = StringUtils.doubleToInt(obj.getProduct_id().getQty_available());
                Integer product_qty = obj.getProduct_qty();
                Integer qty = qty_available >= product_qty ? product_qty : qty_available;
                editText.setText(qty + "");
                editText.setInputType(InputType.TYPE_CLASS_NUMBER);
                editText.setSelection(editText.getText().length());
                AlertDialog.Builder dialog = AlertAialogUtils.getCommonDialog(ProjectDetailActivity.this, "输入" + obj.getProduct_id().getName() + "备料数量");
                dialog.setView(editText)
                        .setPositiveButton("确定", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                int keyong;
                                if (bundle1.getState().equals("done") || bundle1.getState().equals("cancel")) {
                                    keyong = StringUtils.doubleToInt(obj.getProduct_id().getQty_available())-StringUtils.doubleToInt(obj.getReserved_qty());
                                } else {
                                    keyong = StringUtils.doubleToInt(obj.getProduct_id().getQty_available())-StringUtils.doubleToInt(obj.getReserved_qty())-obj.getQty_done();
                                }
                                if (Integer.parseInt(editText.getText().toString()) > keyong) {
                                    Toast.makeText(ProjectDetailActivity.this, "库存不足", Toast.LENGTH_SHORT).show();
                                    return;
                                }
                                obj.setQty_done(Integer.parseInt(editText.getText().toString()));
                                salesDetailAdapter.notifyDataSetChanged();
                            }
                        }).show();
            }
        });
    }
}
