package tarce.myodoo.activity.takedeliver;

import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.os.Handler;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.RecyclerView;
import android.text.InputType;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.newland.me.ConnUtils;
import com.newland.me.DeviceManager;
import com.newland.mtype.ConnectionCloseEvent;
import com.newland.mtype.ModuleType;
import com.newland.mtype.event.DeviceEventListener;
import com.newland.mtype.module.common.printer.Printer;
import com.newland.mtype.module.common.rfcard.RFCardModule;
import com.newland.mtype.module.common.rfcard.RFResult;
import com.newland.mtype.util.ISOUtils;
import com.newland.mtypex.nseries.NSConnV100ConnParams;
import com.uuzuche.lib_zxing.activity.CodeUtils;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Observable;
import java.util.concurrent.TimeUnit;

import butterknife.ButterKnife;
import butterknife.InjectView;
import butterknife.OnClick;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava.RxJavaCallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;
import rx.Subscriber;
import tarce.api.MyCallback;
import tarce.api.OKHttpFactory;
import tarce.api.RetrofitClient;
import tarce.api.api.InventoryApi;
import tarce.model.LoginResponse;
import tarce.model.inventory.NfcOrderBean;
import tarce.model.inventory.TakeDeAreaBean;
import tarce.model.inventory.TakeDelListBean;
import tarce.myodoo.R;
import tarce.myodoo.activity.AreaMessageActivity;
import tarce.myodoo.activity.BaseActivity;
import tarce.myodoo.activity.OrderDetailActivity;
import tarce.myodoo.activity.WriteCheckMessaActivity;
import tarce.myodoo.adapter.takedeliver.DetailTakedAdapter;
import tarce.myodoo.device.Const;
import tarce.myodoo.uiutil.FullyLinearLayoutManager;
import tarce.myodoo.uiutil.NFCdialog;
import tarce.myodoo.utils.DateTool;
import tarce.myodoo.utils.StringUtils;
import tarce.myodoo.utils.UserManager;
import tarce.support.AlertAialogUtils;
import tarce.support.MyLog;
import tarce.support.TimeUtils;
import tarce.support.ToastUtils;

import static tarce.api.RetrofitClient.Url;

/**
 * Created by zouzou on 2017/6/23.
 * 收货详情
 */

public class TakeDeliverDetailActivity extends BaseActivity {
    private static final String K21_DRIVER_NAME = "com.newland.me.K21Driver";
    @InjectView(R.id.top_imageview)
    ImageView topImageview;
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
    @InjectView(R.id.tv_print)
    TextView tvPrint;
    @InjectView(R.id.tv_false_product)
    TextView tvFalseProduct;
    private TakeDelListBean.ResultBean.ResDataBean resDataBean;
    private InventoryApi inventoryApi;
    private DetailTakedAdapter takedAdapter;
    private String type_code;
    private String state;
    private DeviceManager deviceManager;
    private Printer printer;
    private LoginResponse userInfoBean;
    private String notneed;
    private String from;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_takedelie_detail);
        ButterKnife.inject(this);

        Intent intent = getIntent();
        type_code = intent.getStringExtra("type_code");
        state = intent.getStringExtra("state");
        from = intent.getStringExtra("from");
        notneed = intent.getStringExtra("notneed");
        resDataBean = (TakeDelListBean.ResultBean.ResDataBean) intent.getSerializableExtra("dataBean");
        if (resDataBean != null)
            setTitle(resDataBean.getName());
        topImageview.setFocusableInTouchMode(true);
        topImageview.requestFocus();
        inventoryApi = RetrofitClient.getInstance(TakeDeliverDetailActivity.this).create(InventoryApi.class);
        recyclerview.setLayoutManager(new FullyLinearLayoutManager(TakeDeliverDetailActivity.this));
        recyclerview.addItemDecoration(new DividerItemDecoration(TakeDeliverDetailActivity.this,
                DividerItemDecoration.VERTICAL));
        recyclerview.setNestedScrollingEnabled(false);
        showView(resDataBean);
        userInfoBean = UserManager.getSingleton().getUserInfoBean();
    }


    private void showView(TakeDelListBean.ResultBean.ResDataBean resDataBean) {
        partner.setText(resDataBean.getParnter_id());
        time.setText(TimeUtils.utc2Local(resDataBean.getMin_date()));
        states.setText(StringUtils.switchString(resDataBean.getState()));
        originDocuments.setText(resDataBean.getOrigin());
        if (resDataBean.getDelivery_rule() != null) {
            salesOut.setText(StringUtils.switchString((String) resDataBean.getDelivery_rule()));
        } else {
            salesOut.setText("");
        }
        remarks.setText(String.valueOf(resDataBean.getSale_note()));
        List<TakeDelListBean.ResultBean.ResDataBean.PackOperationProductIdsBean> pack_operation_product_ids = resDataBean.getPack_operation_product_ids();
        takedAdapter = new DetailTakedAdapter(R.layout.adapter_detaildeleive, pack_operation_product_ids, TakeDeliverDetailActivity.this, "notShow");
        recyclerview.setAdapter(takedAdapter);
        refreshButtom(resDataBean.getState());
    }

    private void refreshButtom(final String state) {
        switch (state) {
            case "assigned":
                buttomButton1.setText("提交入库");
                showLinThreeCang();//根据权限判断
                initListenerAdapter();
                buttomButton1.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        AlertAialogUtils.getCommonDialog(TakeDeliverDetailActivity.this, "是否确定提交,如果确定将打印单据请等待！")
                                .setPositiveButton("确定", new DialogInterface.OnClickListener() {
                                    @Override
                                    public void onClick(DialogInterface dialog, int which) {
                                        List<TakeDelListBean.ResultBean.ResDataBean.PackOperationProductIdsBean> data = takedAdapter.getData();
                                        ArrayList<Integer> doneNum = new ArrayList<>();
                                        for (int i = 0; i < data.size(); i++) {
                                            doneNum.add(StringUtils.doubleToInt(data.get(i).getQty_done()));
                                        }
                                        boolean isIntent = false;
                                        for (int i = 0; i < data.size(); i++) {
                                            if (data.get(i).getQty_done() > 0) {
                                                isIntent = true;
                                                break;
                                            } else {
                                                isIntent = false;
                                            }
                                        }
                                        if (isIntent) {
                                            printTra();//打印
                                            Intent intent = new Intent(TakeDeliverDetailActivity.this, TakeDeAreaActivity.class);
                                            intent.putExtra("bean", resDataBean);
                                            intent.putIntegerArrayListExtra("intArr", doneNum);
                                            intent.putExtra("type_code", type_code);
                                            intent.putExtra("state", state);
                                            intent.putExtra("from", from);
                                            intent.putExtra("notneed", notneed);
                                            startActivity(intent);
                                            finish();
                                        } else {
                                            ToastUtils.showCommonToast(TakeDeliverDetailActivity.this, "请检查完成数量");
                                        }
                                    }
                                }).show();
                    }
                });
                break;
            case "qc_check":
                takedAdapter.setShowNotgood("qc_check");
                tvFalseProduct.setVisibility(View.VISIBLE);
                buttomButton1.setText("查看入库信息");
                buttomButton2.setText("填写品检信息");
                buttomButton2.setVisibility(View.VISIBLE);
                showLinThreePin();
                initRejectAdapter();
                buttomButton1.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Intent intent = new Intent(TakeDeliverDetailActivity.this, AreaMessageActivity.class);
                        intent.putExtra("img_area", resDataBean.getQc_img());
                        intent.putExtra("string_area", (String) resDataBean.getPost_area_id().getArea_name());
                        startActivity(intent);
                    }
                });
                buttomButton2.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Intent intent = new Intent(TakeDeliverDetailActivity.this, WriteCheckMessaActivity.class);
                        intent.putExtra("confirm", "notConfirm");
                        intent.putExtra("bean", resDataBean);
                        intent.putExtra("type_code", type_code);
                        intent.putExtra("state", state);
                        intent.putExtra("notneed", notneed);
                        startActivity(intent);
                        finish();
                    }
                });
                break;
            case "validate":
                takedAdapter.setShowNotgood("validate");
                tvFalseProduct.setVisibility(View.VISIBLE);
                buttomButton1.setText("查看品检结果");
                showLinThreePin();
                buttomButton1.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Intent intent = new Intent(TakeDeliverDetailActivity.this, WriteCheckMessaActivity.class);
                        List<TakeDelListBean.ResultBean.ResDataBean.PackOperationProductIdsBean> data = takedAdapter.getData();
                        intent.putExtra("confirm", "confirm");
                        intent.putExtra("bean", resDataBean);
                        intent.putExtra("type_code", type_code);
                        intent.putExtra("state", state);
                        intent.putExtra("notneed", notneed);
                        ArrayList<Integer> doneNum = new ArrayList<>();
                        for (int i = 0; i < data.size(); i++) {
                            doneNum.add(StringUtils.doubleToInt(data.get(i).getQty_done()));
                        }
                        intent.putIntegerArrayListExtra("intArr", doneNum);
                        startActivity(intent);
                    }
                });
                break;
            case "waiting_in":
                buttomButton1.setText("入库");
                showLinThreeCang();//根据权限判断
                buttomButton1.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        AlertAialogUtils.getCommonDialog(TakeDeliverDetailActivity.this, "确定入库？")
                                .setPositiveButton("确定", new DialogInterface.OnClickListener() {
                                    @Override
                                    public void onClick(DialogInterface dialog, int which) {
                                        showDefultProgressDialog();
                                        HashMap<Object, Object> hashMap = new HashMap<>();
                                        hashMap.put("state", "transfer");
                                        hashMap.put("picking_id", resDataBean.getPicking_id());
                                        List<TakeDelListBean.ResultBean.ResDataBean.PackOperationProductIdsBean> ids = resDataBean.getPack_operation_product_ids();
                                        List<TakeDelListBean.ResultBean.ResDataBean.PackOperationProductIdsBean> sub_ids = new ArrayList<>();
                                        for (int i = 0; i < ids.size(); i++) {
                                            if (ids.get(i).getPack_id() == -1) {
                                                sub_ids.add(ids.get(i));
                                            }
                                        }
                                        ids.removeAll(sub_ids);
                                        int size = ids.size();
                                        Map[] maps = new Map[size];
                                        for (int i = 0; i < size; i++) {
                                            Map<Object, Object> map = new HashMap<>();
                                            map.put("pack_id", ids.get(i).getPack_id());
                                            map.put("qty_done", StringUtils.doubleToInt(ids.get(i).getQty_done()));
                                            maps[i] = map;
                                        }
                                        hashMap.put("pack_operation_product_ids", maps);
                                        Call<TakeDeAreaBean> objectCall = inventoryApi.ruKu(hashMap);
                                        objectCall.enqueue(new MyCallback<TakeDeAreaBean>() {
                                            @Override
                                            public void onResponse(Call<TakeDeAreaBean> call, Response<TakeDeAreaBean> response) {
                                                dismissDefultProgressDialog();
                                                if (response.body() == null || response.body().getResult() == null)
                                                    return;
                                                if (response.body().getResult().getRes_data() != null && response.body().getResult().getRes_code() == 1) {
                                                    ToastUtils.showCommonToast(TakeDeliverDetailActivity.this, "入库完成");
                                                    finish();
                                                } else {
                                                    ToastUtils.showCommonToast(TakeDeliverDetailActivity.this, "some error");
                                                }
                                            }
                                            @Override
                                            public void onFailure(Call<TakeDeAreaBean> call, Throwable t) {
                                                dismissDefultProgressDialog();
                                                ToastUtils.showCommonToast(TakeDeliverDetailActivity.this, t.toString());
                                            }
                                        });
                                    }
                                }).show();
                    }
                });
                break;
            case "done":
                buttomButton1.setVisibility(View.GONE);
                break;
        }
    }

    //adapter的另一个点击事件
    private void initRejectAdapter() {
        takedAdapter.setOnItemClickListener(new BaseQuickAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(BaseQuickAdapter adapter, View view, int position) {
                final TakeDelListBean.ResultBean.ResDataBean.PackOperationProductIdsBean bean
                        = takedAdapter.getData().get(position);
                if (bean.getPack_id() == -1) {
                    return;
                }
                final EditText editText = new EditText(TakeDeliverDetailActivity.this);
                // final int qty_available = StringUtils.doubleToInt(bean.getProduct_id().getQty_available());
                final int product_qty = StringUtils.doubleToInt(bean.getProduct_qty());
                //final int qty = qty_available >= product_qty ? qty_available:product_qty;
                editText.setText(bean.getRejects_qty()+"");
                editText.setInputType(InputType.TYPE_CLASS_NUMBER);
                editText.setSelection(editText.getText().length());
                AlertDialog.Builder dialog = AlertAialogUtils.getCommonDialog(TakeDeliverDetailActivity.this, "请输入 " + bean.getProduct_id().getName() + " 不良品数量");
                dialog.setView(editText)
                        .setPositiveButton("确定", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                int anInt = Integer.parseInt(editText.getText().toString());
                                if (anInt > bean.getQty_done()){
                                    Toast.makeText(TakeDeliverDetailActivity.this, "不能超过完成数量", Toast.LENGTH_SHORT).show();
                                    return;
                                }
                                bean.setRejects_qty(anInt);
                                takedAdapter.notifyDataSetChanged();
                            }
                        }).show();
            }
        });
    }

    private void initListenerAdapter() {
        takedAdapter.setOnItemClickListener(new BaseQuickAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(BaseQuickAdapter adapter, View view, int position) {
                final TakeDelListBean.ResultBean.ResDataBean.PackOperationProductIdsBean bean
                        = takedAdapter.getData().get(position);
                if (bean.getPack_id() == -1) {
                    return;
                }
                final EditText editText = new EditText(TakeDeliverDetailActivity.this);
                // final int qty_available = StringUtils.doubleToInt(bean.getProduct_id().getQty_available());
                final int product_qty = StringUtils.doubleToInt(bean.getProduct_qty());
                //final int qty = qty_available >= product_qty ? qty_available:product_qty;
                editText.setText(product_qty + "");
                editText.setInputType(InputType.TYPE_CLASS_NUMBER);
                editText.setSelection(editText.getText().length());
                AlertDialog.Builder dialog = AlertAialogUtils.getCommonDialog(TakeDeliverDetailActivity.this, "请输入 " + bean.getProduct_id().getName() + " 完成数量");
                dialog.setView(editText)
                        .setPositiveButton("确定", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                int anInt = Integer.parseInt(editText.getText().toString());
                                if (anInt > product_qty) {
                                    Toast.makeText(TakeDeliverDetailActivity.this, "库存不足", Toast.LENGTH_SHORT).show();
                                    return;
                                }
                                bean.setQty_done(anInt);
                                takedAdapter.notifyDataSetChanged();
                            }
                        }).show();
            }
        });
    }

    /**
     * 打印操作
     */
    @OnClick(R.id.tv_print)
    void printDan(View view) {
        AlertAialogUtils.getCommonDialog(TakeDeliverDetailActivity.this, "确定打印？(请尽量避免重复打印)")
                .setPositiveButton("确定", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        printTra();
                    }
                }).show();
    }

    /**
     * 是否显示底部(仓库)
     */
    public void showLinThreeCang() {
        if (!UserManager.getSingleton().getGrops().contains("group_charge_warehouse")) {
            linearBottom.setVisibility(View.GONE);
        }
    }

    /**
     * 是否显示底部（品检）
     */
    public void showLinThreePin() {
        if (!UserManager.getSingleton().getGrops().contains("group_charge_inspection")) {
            linearBottom.setVisibility(View.GONE);
        }
    }

    /**
     * 是否显示底部（采购）
     */
    public void showLinThreeGou() {
        if (!UserManager.getSingleton().getGrops().contains("group_purchase_manager") && !UserManager.getSingleton().getGrops().contains("group_purchase_user")) {
            linearBottom.setVisibility(View.GONE);
        }
    }

    /**
     * 打印操作
     */
    private void printTra() {
        initDevice();
        printer = (Printer) deviceManager.getDevice().getStandardModule(ModuleType.COMMON_PRINTER);
        printer.init();
        printer.setLineSpace(1);
        printer.print("仓库备注:\n\n\n" + "品检备注:\n\n\n" + "入库单号：" + resDataBean.getName() + "\n" + "源单据: " + resDataBean.getOrigin() + "\n" + "合作伙伴： " + resDataBean.getParnter_id(), 30, TimeUnit.SECONDS);
        if (userInfoBean != null) {
            printer.print("\n" + "入库人：" + userInfoBean.getResult().getRes_data().getName() + "\n" +
                    "--------------------" + "\n", 30, TimeUnit.SECONDS);
        }
        printer.print("产品名称        完成数量", 30, TimeUnit.SECONDS);
        for (int i = 0; i < resDataBean.getPack_operation_product_ids().size(); i++) {
            if (resDataBean.getPack_operation_product_ids().get(i).getPack_id() != -1) {
                String name = resDataBean.getPack_operation_product_ids().get(i).getProduct_id().getName();
                if (name.length()>10){
                    printer.print(name.substring(0, 8) + "     " +
                            resDataBean.getPack_operation_product_ids().get(i).getQty_done()
                            + "\n"+name.substring(8, name.length())+"\n", 30, TimeUnit.SECONDS);
                }else {
                    printer.print(name+ "     " +
                            resDataBean.getPack_operation_product_ids().get(i).getQty_done()
                            + "\n", 30, TimeUnit.SECONDS);
                }
            }
        }
        printer.print("\n", 30, TimeUnit.SECONDS);
        Bitmap mBitmap = CodeUtils.createImage(resDataBean.getName(), 150, 150, null);
        printer.print(0, mBitmap, 30, TimeUnit.SECONDS);
        printer.print("\n" + "打印时间：" + DateTool.getDateTime(), 30, TimeUnit.SECONDS);
        printer.print("\n\n\n\n\n\n\n", 30, TimeUnit.SECONDS);
    }

    /**
     * 连接设备打印机
     */
    private void initDevice() {
        deviceManager = ConnUtils.getDeviceManager();
        try {
            deviceManager.init(TakeDeliverDetailActivity.this, K21_DRIVER_NAME, new NSConnV100ConnParams(), new DeviceEventListener<ConnectionCloseEvent>() {
                @Override
                public void onEvent(ConnectionCloseEvent connectionCloseEvent, Handler handler) {
                    if (connectionCloseEvent.isSuccess()) {
                        ToastUtils.showCommonToast(TakeDeliverDetailActivity.this, "设备被客户主动断开！");
                    }
                    if (connectionCloseEvent.isFailed()) {
                        ToastUtils.showCommonToast(TakeDeliverDetailActivity.this, "设备链接异常断开！");
                    }
                }

                @Override
                public Handler getUIHandler() {
                    return null;
                }
            });
            deviceManager.connect();
            MyLog.e("ProductingActivity", "连接成功");
        } catch (Exception e) {
            e.printStackTrace();
            ToastUtils.showCommonToast(TakeDeliverDetailActivity.this, "链接异常,请检查设备或重新连接.." + e);
        }
    }
}
