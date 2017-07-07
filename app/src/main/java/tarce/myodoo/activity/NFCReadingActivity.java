package tarce.myodoo.activity;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.SearchView;
import android.widget.TextView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.newland.me.ConnUtils;
import com.newland.me.DeviceManager;
import com.newland.mtype.ConnectionCloseEvent;
import com.newland.mtype.ModuleType;
import com.newland.mtype.event.DeviceEventListener;
import com.newland.mtype.module.common.rfcard.RFCardModule;
import com.newland.mtype.module.common.rfcard.RFResult;
import com.newland.mtype.util.ISOUtils;
import com.newland.mtypex.nseries.NSConnV100ConnParams;

import org.w3c.dom.Text;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Observable;
import java.util.concurrent.RunnableFuture;
import java.util.concurrent.TimeUnit;

import butterknife.ButterKnife;
import butterknife.InjectView;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava.RxJavaCallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;
import rx.Observer;
import rx.Subscriber;
import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Action1;
import rx.schedulers.Schedulers;
import tarce.api.OKHttpFactory;
import tarce.api.RetrofitClient;
import tarce.api.api.InventoryApi;
import tarce.model.LoginResponse;
import tarce.model.RequestBindUserBean;
import tarce.model.greendaoBean.LoginResponseBean;
import tarce.model.inventory.NFCUserBean;
import tarce.myodoo.R;
import tarce.myodoo.activity.takedeliver.TakeDeliverDetailActivity;
import tarce.myodoo.adapter.NFCUseradapter;
import tarce.myodoo.device.Const;
import tarce.support.MyLog;
import tarce.support.ToastUtils;

public class NFCReadingActivity extends BaseActivity {

    private static final String K21_DRIVER_NAME = "com.newland.me.K21Driver";
    @InjectView(R.id.search_name)
    SearchView searchName;
    @InjectView(R.id.recyclerview)
    RecyclerView recyclerview;

    private Retrofit retrofit;
    private InventoryApi inventoryApi;
    private List<NFCUserBean.ResultBean.ResDataBean> res_data;
    private LayoutInflater layoutInflater;
    private DeviceManager deviceManager;
    private RFCardModule rfCardModule;
    private NFCUseradapter nfcUseradapter;
    private List<NFCUserBean.ResultBean.ResDataBean> allList = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_nfc_reading);
        ButterKnife.inject(this);
        setRecyclerview(recyclerview);
        setTitle("员工NFC录入");
        searchName.clearFocus();
        layoutInflater = LayoutInflater.from(NFCReadingActivity.this);
        initData();
        initListener();
        initDevice();

    }

    private void initData() {
        retrofit = new Retrofit.Builder()
                .client(new OKHttpFactory(NFCReadingActivity.this).getOkHttpClient())
                .baseUrl(RetrofitClient.Url + "/linkloving_user_auth/")
                .addConverterFactory(GsonConverterFactory.create())
                .addCallAdapterFactory(RxJavaCallAdapterFactory.create())
                .build();
        inventoryApi = retrofit.create(InventoryApi.class);
        final HashMap<Object, Object> hashMap = new HashMap<>();
        hashMap.put("limit", 1000);
        hashMap.put("offset", 0);
        Call<NFCUserBean> allUser = inventoryApi.getAllUser(hashMap);
        showDefultProgressDialog();
        allUser.enqueue(new Callback<NFCUserBean>() {
            @Override
            public void onResponse(Call<NFCUserBean> call, Response<NFCUserBean> response) {
                if (response.body().getResult()==null || response.body().getResult()==null)return;
                if (response.body().getResult().getRes_data()!=null && response.body().getResult().getRes_data()!=null){
                    allList = new ArrayList<>();
                    res_data = response.body().getResult().getRes_data();
                    allList =res_data;
                    nfcUseradapter = new NFCUseradapter(R.layout.adapter_nfcread_detail, res_data);
                    recyclerview.setAdapter(nfcUseradapter);
                    initadapterListener();
                    dismissDefultProgressDialog();
                }
            }

            @Override
            public void onFailure(Call<NFCUserBean> call, Throwable t) {
                dismissDefultProgressDialog();
                MyLog.e(t.toString());
            }
        });

    }

    private void initadapterListener() {
        nfcUseradapter.setOnItemClickListener(new BaseQuickAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(final BaseQuickAdapter adapter, View view, final int position) {
                new Thread(new Runnable() {
                    public TextView tv_tip;
                    private AlertDialog alertDialog;
                    private Button conform_button;
                    private Button cancelButton;
                    private TextView errorText;
                    private TextView nfcNumber;

                    @Override
                    public void run() {
                        processingLock();
                        runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                AlertDialog.Builder builder = new AlertDialog.Builder(NFCReadingActivity.this);
                                View inflate = layoutInflater.inflate(R.layout.nfc_detail_dialog, null);
                                TextView textviewName = (TextView) inflate.findViewById(R.id.textview_name);
                                nfcNumber = (TextView) inflate.findViewById(R.id.NFC_number);
                                errorText = (TextView) inflate.findViewById(R.id.error_text);
                                cancelButton = (Button) inflate.findViewById(R.id.cancel_button);
                                conform_button = (Button) inflate.findViewById(R.id.conform_button);
                                tv_tip = (TextView) inflate.findViewById(R.id.tv_tip);
                                textviewName.setText(res_data.get(position).getName());
                                builder.setView(inflate);
                                alertDialog = builder.create();
                                alertDialog.show();
                            }
                        });
                        try {
                           final RFResult qPResult = rfCardModule.powerOn(null, 10, TimeUnit.SECONDS);
                            runOnUiThread(new Runnable() {
                                @Override
                                public void run() {
                                    if (qPResult.getCardSerialNo() == null) {
                                        errorText.setText("非接卡序列号null: " + "" + Const.MessageTag.DATA);
                                    } else {
                                        tv_tip.setVisibility(View.GONE);
                                        String NFC_Number = ISOUtils.hexString(qPResult.getCardSerialNo());
                                        nfcNumber.setText(NFC_Number);
                                        final HashMap<Object, Object> hashMapBand = new HashMap<>();
                                        hashMapBand.put("is_force", false);
                                        hashMapBand.put("employee_id", res_data.get(position).getEmployee_id());
                                        hashMapBand.put("card_num", NFC_Number);
                                        Call<RequestBindUserBean> stringCall = inventoryApi.requestBindUser(hashMapBand);
                                        stringCall.enqueue(new Callback<RequestBindUserBean>() {
                                            @Override
                                            public void onResponse(Call<RequestBindUserBean> call, Response<RequestBindUserBean> response) {
                                                if (response.body()==null || response.body().getResult()==null)return;
                                                int res_code = response.body().getResult().getRes_code();
                                                if (res_code == 1) {
                                                    alertDialog.dismiss();
                                                    ToastUtils.showCommonToast(NFCReadingActivity.this, "绑定成功");
                                                } else {
                                                    String error = response.body().getResult().getRes_data().getError();
                                                    errorText.setText(error);
                                                    cancelButton.setVisibility(View.VISIBLE);
                                                    conform_button.setVisibility(View.VISIBLE);
                                                    cancelButton.setOnClickListener(new View.OnClickListener() {
                                                        @Override
                                                        public void onClick(View view) {
                                                            alertDialog.dismiss();
                                                        }
                                                    });
                                                    conform_button.setOnClickListener(new View.OnClickListener() {
                                                        @Override
                                                        public void onClick(View view) {
                                                            hashMapBand.put("is_force", true);
                                                            Call<RequestBindUserBean> requestbind = inventoryApi.requestBindUser(hashMapBand);
                                                            requestbind.enqueue(new Callback<RequestBindUserBean>() {
                                                                @Override
                                                                public void onResponse(Call<RequestBindUserBean> call, Response<RequestBindUserBean> response) {
                                                                    int res_code = response.body().getResult().getRes_code();
                                                                    if (res_code == 1) {
                                                                        alertDialog.dismiss();
                                                                        ToastUtils.showCommonToast(NFCReadingActivity.this, "绑定成功");
                                                                        res_data.get(position).setCard_num(response.body().getResult().getRes_data().getCard_num());
                                                                        nfcUseradapter.notifyDataSetChanged();
                                                                    } else {
                                                                        ToastUtils.showCommonToast(NFCReadingActivity.this, "绑定失败");
                                                                    }
                                                                }

                                                                @Override
                                                                public void onFailure(Call<RequestBindUserBean> call, Throwable t) {
                                                                    ToastUtils.showCommonToast(NFCReadingActivity.this, "绑定失败");
                                                                }
                                                            });


                                                        }
                                                    });

                                                }
                                            }

                                            @Override
                                            public void onFailure(Call<RequestBindUserBean> call, Throwable t) {
                                                    MyLog.e("NFCReadingActivity", t.toString());
                                            }
                                        });

                                    }
                                    processingUnLock();
                                }
                            });

                        } catch (final Exception e) {
                            e.fillInStackTrace();
                            runOnUiThread(new Runnable() {
                                @Override
                                public void run() {
                                    errorText.setText(e.getMessage() + "  " + Const.MessageTag.ERROR);
                                }
                            });
                            processingUnLock();
                        }
                    }
                }).start();
            }
        });
    }


    private void initListener() {
        searchName.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String s) {
                return false;
            }

            @Override
            public boolean onQueryTextChange(String s) {
                ArrayList<NFCUserBean.ResultBean.ResDataBean> searchList = new ArrayList<>();
                for (NFCUserBean.ResultBean.ResDataBean bean : allList) {
                    if (bean.getName().contains(s)) {
                        searchList.add(bean);
                    }
                }
                res_data = searchList;
                nfcUseradapter.setNewData(res_data);
                nfcUseradapter.notifyDataSetChanged();
                return false;

            }
        });

    }

    /**
     * 连接设备打印机
     */
    private void initDevice() {
        deviceManager = ConnUtils.getDeviceManager();
        try {
            deviceManager.init(NFCReadingActivity.this, K21_DRIVER_NAME, new NSConnV100ConnParams(), new DeviceEventListener<ConnectionCloseEvent>() {
                @Override
                public void onEvent(ConnectionCloseEvent connectionCloseEvent, Handler handler) {
                    if (connectionCloseEvent.isSuccess()) {
                        ToastUtils.showCommonToast(NFCReadingActivity.this, "设备被客户主动断开！");
                    }
                    if (connectionCloseEvent.isFailed()) {
                        ToastUtils.showCommonToast(NFCReadingActivity.this, "设备链接异常断开！");
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
            ToastUtils.showCommonToast(NFCReadingActivity.this, "链接异常,请检查设备或重新连接.." + e);
        }

        rfCardModule = (RFCardModule) deviceManager.getDevice().getStandardModule(ModuleType.COMMON_RFCARDREADER);

    }

    public void processingLock() {
        runOnUiThread(new Runnable() {

            @Override
            public void run() {
                SharedPreferences setting = getSharedPreferences("setting", 0);
                SharedPreferences.Editor editor = setting.edit();
                editor.putBoolean("PBOC_LOCK", true);
                editor.commit();
            }
        });

    }

    public void processingUnLock() {
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                SharedPreferences setting = getSharedPreferences("setting", 0);
                SharedPreferences.Editor editor = setting.edit();
                editor.putBoolean("PBOC_LOCK", false);
                editor.commit();
            }
        });

    }
}
