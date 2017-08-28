package tarce.myodoo.activity;

import android.app.Activity;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.Bundle;
import android.os.Handler;
import android.support.v7.app.AlertDialog;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.amitshekhar.DebugDB;
import com.newland.me.ConnUtils;
import com.newland.me.DeviceManager;
import com.newland.mtype.ConnectionCloseEvent;
import com.newland.mtype.ModuleType;
import com.newland.mtype.event.DeviceEventListener;
import com.newland.mtype.module.common.rfcard.RFCardModule;
import com.newland.mtype.module.common.rfcard.RFResult;
import com.newland.mtype.util.ISOUtils;
import com.newland.mtypex.nseries.NSConnV100ConnParams;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.concurrent.TimeUnit;

import butterknife.ButterKnife;
import butterknife.InjectView;
import butterknife.OnClick;
import cn.hugeterry.updatefun.UpdateFunGO;
import cn.hugeterry.updatefun.config.UpdateKey;
import greendao.DaoSession;
import retrofit2.Call;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava.RxJavaCallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;
import rx.Observable;
import rx.Subscriber;
import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Action1;
import rx.schedulers.Schedulers;
import tarce.api.MyCallback;
import tarce.api.OKHttpFactory;
import tarce.api.RetrofitClient;
import tarce.api.api.InventoryApi;
import tarce.api.api.LoginApi;
import tarce.model.LoginBean;
import tarce.model.LoginDatabase;
import tarce.model.LoginResponse;
import tarce.model.greendaoBean.LoginResponseBean;
import tarce.model.greendaoBean.UserLogin;
import tarce.model.inventory.NFcLoginBean;
import tarce.myodoo.IntentFactory;
import tarce.myodoo.MyApplication;
import tarce.myodoo.R;
import tarce.myodoo.device.Const;
import tarce.myodoo.greendaoUtils.UserLoginUtils;
import tarce.myodoo.utils.StringUtils;
import tarce.myodoo.utils.UserManager;
import tarce.support.MyLog;
import tarce.support.SharePreferenceUtils;
import tarce.support.ToastUtils;
import tarce.support.ViewUtils;

/**
 * Created by Daniel.Xu on 2017/4/20.
 */

public class LoginActivity extends Activity {
    private static final String K21_DRIVER_NAME = "com.newland.me.K21Driver";
    @InjectView(R.id.database)
    Button database;
    @InjectView(R.id.email)
    AutoCompleteTextView email;
    @InjectView(R.id.password)
    EditText password;
    @InjectView(R.id.to_login)
    Button toLogin;
    @InjectView(R.id.httpUrl)
    EditText httpUrl;
    @InjectView(R.id.tv_nfc_login)
    TextView tvNfcLogin;
    @InjectView(R.id.img_delete)
    ImageView imgDelete;
    private String TAG = LoginActivity.class.getSimpleName();
    private int databaseSwitch = 0;
    private LoginApi loginApi;
    private DaoSession daoSession;
    private ProgressDialog progressDialog;
    private List<UserLogin> userLogins;
    private InventoryApi inventoryApi;
    private ArrayList<String> userStrings;
    private DeviceManager deviceManager;
    private RFCardModule rfCardModule;
    private Dialog alertDialog;
    private Retrofit retrofit;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        ButterKnife.inject(this);
        String addressLog = DebugDB.getAddressLog();
        MyLog.e(TAG, addressLog + "=============================================addressLog");
        daoSession = MyApplication.getInstances().getDaoSession();
        /*menuListBeanDao = daoSession.getMenuListBeanDao();
        contactsBeanDao = daoSession.getContactsBeanDao();*/
        progressDialog = new ProgressDialog(LoginActivity.this);
        progressDialog.setMessage("loading");
        checkOutoLogin();
        initEmailAdapter();
        initListener();
        httpUrl.setSelection(httpUrl.getText().length());
      //  initDevice();
    }

    @OnClick(R.id.img_delete)
    void deleteHost(View view){
        httpUrl.setText("");
    }

    @Override
    public boolean dispatchTouchEvent(MotionEvent ev) {
        ViewUtils.collapseSoftInputMethod(LoginActivity.this, httpUrl);
        ViewUtils.collapseSoftInputMethod(LoginActivity.this, email);
        ViewUtils.collapseSoftInputMethod(LoginActivity.this, password);
        return super.dispatchTouchEvent(ev);
    }

    @Override
    protected void onResume() {
        UpdateFunGO.onResume(this);
        super.onResume();
    }

    @Override
    protected void onStop() {
        UpdateFunGO.onStop(this);
        super.onStop();
    }

    //取出用户名检索
    private void initEmailAdapter() {
        email.setThreshold(1);
        userLogins = new UserLoginUtils().searchAll();
        userStrings = new ArrayList<>();
        if (userLogins != null && userLogins.size() > 0) {
            for (UserLogin s : userLogins) {
                userStrings.add(s.getUserName());
            }
            ArrayAdapter<String> stringArrayAdapter = new ArrayAdapter<>(LoginActivity.this, R.layout.auto_text_listview, userStrings);
            email.setAdapter(stringArrayAdapter);
        }
    }


    private void initListener() {

        /**
         * 先获取position 再获取位置
         */
        email.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                String item = (String) adapterView.getAdapter().getItem(i);
                int index = userStrings.indexOf(item);
                String password = userLogins.get(index).getPassword();
                LoginActivity.this.password.setText(password);
            }
        });
    }

    /**
     * 检查是否可以自动登录
     */
    private void checkOutoLogin() {
        int user_id = SharePreferenceUtils.getInt("user_id", -1000, LoginActivity.this);
        if (StringUtils.isNullOrEmpty(RetrofitClient.Url)) {
            httpUrl.setText("http://erp.robotime.com");
        } else {
            httpUrl.setText(RetrofitClient.Url);
        }
        if (user_id != -1000) {
            String url = SharePreferenceUtils.getString("url", "null", LoginActivity.this);
            LoginActivity.this.httpUrl.setText(url);
            RetrofitClient.Url = url;
            loginApi = RetrofitClient.getInstance(LoginActivity.this).create(LoginApi.class);
            String database = SharePreferenceUtils.getString("database", "null", LoginActivity.this);
            LoginActivity.this.database.setText(database);
            String email = SharePreferenceUtils.getString("email", "error", LoginActivity.this);
            LoginActivity.this.email.setText(email);
            String password = SharePreferenceUtils.getString("password", "error", LoginActivity.this);
            LoginActivity.this.password.setText(password);
            toLogin(new View(LoginActivity.this));
        } else {
            UpdateKey.API_TOKEN = "d8980dd0017f3e0a7b038aec2c52d737";
            UpdateKey.APP_ID = "5940d8ca959d6965c30002dc";
            //下载方式:
            //   UpdateKey.DialogOrNotification=UpdateKey.WITH_DIALOG;//通过Dialog来进行下载
            //UpdateKey.DialogOrNotification=UpdateKey.WITH_NOTIFITION;通过通知栏来进行下载(默认)
            UpdateFunGO.init(this);
        }
    }

    @OnClick(R.id.database)
    void setDatabase(View view) {
        if (StringUtils.isNullOrEmpty(httpUrl.getText().toString())) {
            ToastUtils.showCommonToast(LoginActivity.this, "请输入url地址");
            return;
        }
        String URL;
        if (!httpUrl.getText().toString().contains("http://")) {
            URL = "http://" + httpUrl.getText().toString();
            httpUrl.setText(URL);
        } else {
            URL = httpUrl.getText().toString();
        }
        RetrofitClient.Url = URL;
        loginApi = RetrofitClient.getInstance(LoginActivity.this).create(LoginApi.class);
        final AlertDialog.Builder builder = new AlertDialog.Builder(LoginActivity.this);
        builder.setTitle("选择数据库");
        builder.setIcon(android.R.drawable.ic_menu_more);
        builder.setCancelable(true);
        progressDialog = new ProgressDialog(LoginActivity.this);
        progressDialog.setMessage("Loading...");
        progressDialog.show();
        Observable<LoginDatabase> database = loginApi.getDatabase();
        database.subscribeOn(Schedulers.newThread())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(
                        new Subscriber<LoginDatabase>() {
                            @Override
                            public void onCompleted() {
                                if (progressDialog != null && progressDialog.isShowing()) {
                                    progressDialog.dismiss();
                                }
                            }

                            @Override
                            public void onError(Throwable e) {
                                MyLog.e(TAG, e.toString());
                                if (progressDialog != null && progressDialog.isShowing()) {
                                    progressDialog.dismiss();
                                }

                            }

                            @Override
                            public void onNext(LoginDatabase loginDatabase) {
                                if (progressDialog != null && progressDialog.isShowing()) {
                                    progressDialog.dismiss();
                                }
                                if (loginDatabase == null) return;
                                final List<String> res_data = loginDatabase.getRes_data();
                                final String[] databaseArr = res_data.toArray(new String[res_data.size()]);
                                builder.setSingleChoiceItems(databaseArr, databaseArr.length, new DialogInterface.OnClickListener() {
                                    @Override
                                    public void onClick(DialogInterface dialogInterface, int i) {
                                        databaseSwitch = i;
                                    }
                                });
                                builder.setPositiveButton("确认", new DialogInterface.OnClickListener() {
                                    @Override
                                    public void onClick(DialogInterface dialog, int which) {
                                        LoginActivity.this.database.setText(res_data.get(databaseSwitch));
                                        SharePreferenceUtils.putString("database", res_data.get(databaseSwitch), LoginActivity.this);
                                    }
                                });
                                AlertDialog dialog = builder.create();
                                dialog.setCanceledOnTouchOutside(true);
                                dialog.show();
                            }
                        }
                );
    }

    @OnClick(R.id.to_login)
    void toLogin(View view) {
        String chooseDB = database.getText().toString();
        if (chooseDB.equals("选择数据库")){
            ToastUtils.showCommonToast(LoginActivity.this, "请选择数据库");
            return;
        }
        final String emailString = this.email.getText().toString();
        if (StringUtils.isNullOrEmpty(emailString)){
            ToastUtils.showCommonToast(LoginActivity.this, "请输入邮箱");
            return;
        }
        final String passwordString = password.getText().toString();
        if (StringUtils.isNullOrEmpty(passwordString)){
            ToastUtils.showCommonToast(LoginActivity.this, "请输入密码");
            return;
        }
        final String url = httpUrl.getText().toString();
        if (StringUtils.isNullOrEmpty(url)){
            ToastUtils.showCommonToast(LoginActivity.this, "请输入url地址");
            return;
        }
        progressDialog.show();
        Call<LoginResponse> stringCall = loginApi.toLogin(new LoginBean(emailString, passwordString, chooseDB));
        stringCall.enqueue(new MyCallback<LoginResponse>() {
            @Override
            public void onResponse(Call<LoginResponse> call, Response<LoginResponse> response) {
                if (response.body() == null || response.body().getResult() == null)
                    return;
                if (response.body().getError() != null) {
                    ToastUtils.showCommonToast(LoginActivity.this, response.body().getError().getMessage());
                    return;
                }
                if (response.body().getResult().getRes_code() == 1) {
                    UserManager.getSingleton().setUserInfoBean(response.body());//单例存储
                    final int user_id = response.body().getResult().getRes_data().getUser_id();
                    MyApplication.userID = user_id;
                    SharePreferenceUtils.putInt("user_id", user_id, LoginActivity.this);
                    SharePreferenceUtils.putString("email", emailString, LoginActivity.this);
                    SharePreferenceUtils.putString("url", url, LoginActivity.this);
                    SharePreferenceUtils.putString("password", passwordString, LoginActivity.this);
                    SharePreferenceUtils.putInt("partner_id", response.body().getResult().getRes_data().getPartner_id(), LoginActivity.this);
                    SharePreferenceUtils.putString("user_ava", response.body().getResult().getRes_data().getUser_ava(), LoginActivity.this);
                    final String name = response.body().getResult().getRes_data().getName();
                    new UserLoginUtils().insertUser(new UserLogin(emailString, passwordString));
                    List<LoginResponse.ResultBean.ResDataBean.GroupsBean> groups = response.body().getResult().getRes_data().getGroups();
                    Observable.from(groups)
                            .subscribe(new Action1<LoginResponse.ResultBean.ResDataBean.GroupsBean>() {
                                @Override
                                public void call(LoginResponse.ResultBean.ResDataBean.GroupsBean groupsBean) {
                                    daoSession.getLoginResponseBeanDao()
                                            .insertOrReplace(new LoginResponseBean(user_id, name, groupsBean.getName(), groupsBean.getId()));
                                }
                            });
                    toMainActivity();
                } else {
                    if (progressDialog.isShowing()) {
                        progressDialog.dismiss();
                    }
                    Toast.makeText(LoginActivity.this, response.body().getResult().getRes_data().getError(), Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<LoginResponse> call, Throwable t) {
                super.onFailure(call, t);
                Toast.makeText(LoginActivity.this, "登录失败", Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void toMainActivity() {
        if (progressDialog.isShowing()) {
            progressDialog.dismiss();
        }
        finish();
        IntentFactory.start_MainActivity(LoginActivity.this);
    }

    @OnClick(R.id.tv_nfc_login)
    void nfcLogin(View view) {
        new Thread(new Runnable() {
            public TextView textTrue;
            public TextView textCancel;
            public ImageView imageHeader;
            public TextView textNfcNum;
            public TextView textviewName;

            @Override
            public void run() {
                processingLock();
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        AlertDialog.Builder builder = new AlertDialog.Builder(LoginActivity.this);
                        View inflate = LayoutInflater.from(LoginActivity.this).inflate(R.layout.dialog_nfc_addwork, null);
                        textviewName = (TextView) inflate.findViewById(R.id.nfc_work_name);
                        textNfcNum = (TextView) inflate.findViewById(R.id.tv_nfc_num);
                        textviewName.setText("请将卡靠近NFC感应区");
                        textviewName.setTextColor(Color.RED);
                        imageHeader = (ImageView) inflate.findViewById(R.id.image_nfc_addwork);
                        textCancel = (TextView) inflate.findViewById(R.id.tv_cancel_addnfc);
                        textTrue = (TextView) inflate.findViewById(R.id.tv_true_addnfc);
                        builder.setView(inflate);
                        alertDialog = builder.create();
                        alertDialog.show();
                        textCancel.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                alertDialog.dismiss();
                            }
                        });
                        textTrue.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                alertDialog.dismiss();
                            }
                        });
                    }
                });
                try {
                    final RFResult qPResult = rfCardModule.powerOn(null, 10, TimeUnit.SECONDS);
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            if (qPResult.getCardSerialNo() == null) {
                                ToastUtils.showCommonToast(LoginActivity.this, "非接卡序列号null: " + "" + Const.MessageTag.DATA);
                            } else {
                                String NFC_Number = ISOUtils.hexString(qPResult.getCardSerialNo());
                                textNfcNum.setText(NFC_Number);
                                progressDialog.show();
                                String host = httpUrl.getText().toString();
                                if (StringUtils.isNullOrEmpty(host)){
                                    return;
                                }else if (!host.contains("http://")){
                                    host = "http://"+host;
                                }
                                retrofit = new Retrofit.Builder()
                                        .client(new OKHttpFactory(LoginActivity.this).getOkHttpClient())
                                        .baseUrl(host + "/linkloving_user_auth/")
                                        .addConverterFactory(GsonConverterFactory.create())
                                        .addCallAdapterFactory(RxJavaCallAdapterFactory.create())
                                        .build();
                                HashMap<Object, Object> hashMap = new HashMap<>();
                                hashMap.put("card_num", ISOUtils.hexString(qPResult.getCardSerialNo()));
                                Call<NFcLoginBean> byCardnum = retrofit.create(InventoryApi.class).getByCardnum(hashMap);
                                byCardnum.enqueue(new MyCallback<NFcLoginBean>() {
                                    @Override
                                    public void onResponse(Call<NFcLoginBean> call, final Response<NFcLoginBean> response) {
                                        progressDialog.dismiss();
                                        if (response.body() == null) return;
                                        if (response.body().getResult() == null) return;
                                        if (response.body().getResult().getRes_data() != null && response.body().getResult().getRes_code() == 1) {
                                            textviewName.setText(response.body().getResult().getRes_data().getName());
                                            textviewName.setTextColor(Color.BLACK);
                                            textTrue.setOnClickListener(new View.OnClickListener() {
                                                @Override
                                                public void onClick(View v) {
                                                    rfCardModule.powerOff(2);
                                                    alertDialog.dismiss();
                                                    email.setText(response.body().getResult().getRes_data().getWork_email());

                                                }
                                            });
                                        }else if (response.body().getResult().getRes_code() == -1 && response.body().getResult().getRes_data()!=null){
                                            rfCardModule.powerOff(2);
                                            alertDialog.dismiss();
                                            ToastUtils.showCommonToast(LoginActivity.this, response.body().getResult().getRes_data().getError());
                                        }
                                    }

                                    @Override
                                    public void onFailure(Call<NFcLoginBean> call, Throwable t) {
                                        progressDialog.dismiss();
                                        MyLog.e("Login", t.toString());
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
                            ToastUtils.showCommonToast(LoginActivity.this, "非接卡序列号null: " + "" + Const.MessageTag.DATA);
                        }
                    });
                    processingUnLock();
                }
            }
        }).start();
    }

    /**
     * 连接设备打印机
     */
    private void initDevice() {
        deviceManager = ConnUtils.getDeviceManager();
        try {
            deviceManager.init(LoginActivity.this, K21_DRIVER_NAME, new NSConnV100ConnParams(), new DeviceEventListener<ConnectionCloseEvent>() {
                @Override
                public void onEvent(ConnectionCloseEvent connectionCloseEvent, Handler handler) {
                    if (connectionCloseEvent.isSuccess()) {
                        ToastUtils.showCommonToast(LoginActivity.this, "设备被客户主动断开！");
                    }
                    if (connectionCloseEvent.isFailed()) {
                        ToastUtils.showCommonToast(LoginActivity.this, "设备链接异常断开！");
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
            ToastUtils.showCommonToast(LoginActivity.this, "链接异常,请检查设备或重新连接.." + e);
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
