package tarce.myodoo.activity;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.amitshekhar.DebugDB;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import butterknife.ButterKnife;
import butterknife.InjectView;
import butterknife.OnClick;
import greendao.ContactsBeanDao;
import greendao.DaoSession;
import greendao.MenuListBeanDao;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import rx.Observable;
import rx.Subscriber;
import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Action1;
import rx.schedulers.Schedulers;
import tarce.api.MyCallback;
import tarce.api.RetrofitClient;
import tarce.api.api.InventoryApi;
import tarce.api.api.LoginApi;
import tarce.model.GetMenuListResponse;
import tarce.model.LoginDatabase;
import tarce.model.LoginResponse;
import tarce.model.SearchSupplierResponse;
import tarce.model.greendaoBean.ContactsBean;
import tarce.model.greendaoBean.LoginResponseBean;
import tarce.model.greendaoBean.MenuListBean;
import tarce.model.greendaoBean.UserLogin;
import tarce.model.LoginBean;
import tarce.myodoo.IntentFactory;
import tarce.myodoo.MyApplication;
import tarce.myodoo.R;
import tarce.myodoo.greendaoUtils.UserLoginUtils;
import tarce.myodoo.utils.StringUtils;
import tarce.myodoo.utils.UserManager;
import tarce.support.MyLog;
import tarce.support.SharePreferenceUtils;
import tarce.support.ToastUtils;

/**
 * Created by Daniel.Xu on 2017/4/20.
 */

public class LoginActivity extends Activity {
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
    private String TAG = LoginActivity.class.getSimpleName();
    private int databaseSwitch = 0;
    private LoginApi loginApi;
    private DaoSession daoSession;
    private MenuListBeanDao menuListBeanDao;
    private ProgressDialog progressDialog;
    private List<UserLogin> userLogins;
    private InventoryApi inventoryApi;
    private ContactsBeanDao contactsBeanDao;
    private ArrayList<String> userStrings;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        ButterKnife.inject(this);
        String addressLog = DebugDB.getAddressLog();
        MyLog.e(TAG,addressLog+"=============================================addressLog");
        daoSession = MyApplication.getInstances().getDaoSession();
        menuListBeanDao = daoSession.getMenuListBeanDao();
        contactsBeanDao =  daoSession.getContactsBeanDao();
        progressDialog = new ProgressDialog(LoginActivity.this);
        progressDialog.setMessage("loading");
        checkOutoLogin();
        initEmailAdapter();
        initListener();
    }

    private void initEmailAdapter() {
        email.setThreshold(1);
        userLogins = new UserLoginUtils().searchAll();
        userStrings = new ArrayList<>();
        if (userLogins !=null&& userLogins.size()>0){
            for (UserLogin s : userLogins){
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

    private void checkOutoLogin() {
        int user_id = SharePreferenceUtils.getInt("user_id", -1000, LoginActivity.this);
        if (StringUtils.isNullOrEmpty(RetrofitClient.Url)){
            httpUrl.setText("http://erp.robotime.com");
        }else {
            httpUrl.setText(RetrofitClient.Url);
        }
        if (user_id != -1000) {
            String url = SharePreferenceUtils.getString("url", "null", LoginActivity.this);
            LoginActivity.this.httpUrl.setText(url);
            RetrofitClient.Url = url ;
            loginApi = RetrofitClient.getInstance(LoginActivity.this).create(LoginApi.class);
            String database = SharePreferenceUtils.getString("database", "null", LoginActivity.this);
            LoginActivity.this.database.setText(database);
            String email = SharePreferenceUtils.getString("email", "error", LoginActivity.this);
            LoginActivity.this.email.setText(email);
            String password = SharePreferenceUtils.getString("password", "error", LoginActivity.this);
            LoginActivity.this.password.setText(password);
            toLogin(new View(LoginActivity.this));
        }
    }

    @OnClick(R.id.database)
    void setDatabase(View view) {
        if (StringUtils.isNullOrEmpty(httpUrl.getText().toString())){
            ToastUtils.showCommonToast(LoginActivity.this, "请输入url地址");
            return;
        }
        String URL = httpUrl.getText().toString();
        RetrofitClient.Url = URL ;
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
                                if (progressDialog!=null&&progressDialog.isShowing()){
                                    progressDialog.dismiss();
                                }

                            }

                            @Override
                            public void onError(Throwable e) {
                                MyLog.e(TAG, e.toString());
                                if (progressDialog!=null&&progressDialog.isShowing()){
                                    progressDialog.dismiss();
                                }

                            }

                            @Override
                            public void onNext(LoginDatabase loginDatabase) {
                                if (progressDialog!=null&&progressDialog.isShowing()){
                                    progressDialog.dismiss();
                                }
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
        progressDialog.show();
        String chooseDB = database.getText().toString();
        final String emailString = this.email.getText().toString();
        final String passwordString = password.getText().toString();
        final String url = httpUrl.getText().toString();
        Call<LoginResponse> stringCall = loginApi.toLogin(new LoginBean(emailString, passwordString, chooseDB));
        stringCall.enqueue(new MyCallback<LoginResponse>() {
            @Override
            public void onResponse(Call<LoginResponse> call, Response<LoginResponse> response) {
                if (response.body()==null)
                    return;
                if (response.body().getError()!=null){
                    ToastUtils.showCommonToast(LoginActivity.this, response.body().getError().getMessage());
                    return;
                }
                if (response.body().getResult().getRes_code() == 1){
                    UserManager.getSingleton().setUserInfoBean(response.body());//单例存储
                   // UserManager.getSingleton().reFreshUserInfo(response.body());//单例存储
                    final int user_id = response.body().getResult().getRes_data().getUser_id();
                    MyApplication.userID = user_id ;
                    SharePreferenceUtils.putInt("user_id", user_id, LoginActivity.this);
                    SharePreferenceUtils.putString("email", emailString, LoginActivity.this);
                    SharePreferenceUtils.putString("url",url,LoginActivity.this);
                    SharePreferenceUtils.putString("password", passwordString, LoginActivity.this);
                    SharePreferenceUtils.putInt("partner_id",response.body().getResult().getRes_data().getPartner_id(),LoginActivity.this);
                    SharePreferenceUtils.putString("user_ava", response.body().getResult().getRes_data().getUser_ava(), LoginActivity.this);
                    final String name = response.body().getResult().getRes_data().getName();
                    new UserLoginUtils().insertUser(new UserLogin(emailString,passwordString));
                    List<LoginResponse.ResultBean.ResDataBean.GroupsBean> groups = response.body().getResult().getRes_data().getGroups();
                    Observable.from(groups)
                            .subscribe(new Action1<LoginResponse.ResultBean.ResDataBean.GroupsBean>() {
                                @Override
                                public void call(LoginResponse.ResultBean.ResDataBean.GroupsBean groupsBean) {
                                    daoSession.getLoginResponseBeanDao()
                                            .insertOrReplace(new LoginResponseBean(user_id, name, groupsBean.getName(), groupsBean.getId()));
                                }
                            });
                    getMenuList();
                }else {
                    if (progressDialog.isShowing()){
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

    public void getMenuList() {
        loginApi.getMenuList().enqueue(new MyCallback<GetMenuListResponse>() {
            @Override
            public void onResponse(Call<GetMenuListResponse> call, Response<GetMenuListResponse> response) {
                List<GetMenuListResponse.ResDataBean> res_data = response.body().getRes_data();
                if (res_data.size() > 0) {
                    int user_id = res_data.get(0).getUser_id();
                    for (GetMenuListResponse.ResDataBean resDataBean : res_data) {
                        menuListBeanDao.insertOrReplace(new MenuListBean(resDataBean.getId(), resDataBean.getAction()
                                , resDataBean.getSequence(), resDataBean.getWeb_icon(), resDataBean.getName()
                                , user_id, resDataBean.isParent_id() ? -1 : 0));
                        if (resDataBean.getChildren().size() > 0) {
                            List<GetMenuListResponse.ChildBean> children = resDataBean.getChildren();
                            for (GetMenuListResponse.ChildBean children1 : children) {
                                String[] parent_id = children1.getParent_id();
                                String parentID = parent_id[0];
                                menuListBeanDao.insertOrReplace(
                                        new MenuListBean(children1.getId(), children1.getAction(),
                                                children1.getSequence(), children1.getWeb_icon()
                                                , children1.getName(), user_id, Integer.parseInt(parentID)));
                                if (children1.getChildren().size() > 0) {
                                    List<GetMenuListResponse.ChildBean> children3 = children1.getChildren();
                                    for (GetMenuListResponse.ChildBean childrenMinUnit : children3) {
                                        String[] parent_idMinUnit = childrenMinUnit.getParent_id();
                                        String parentMinUnitID = parent_idMinUnit[0];
                                        menuListBeanDao.insertOrReplace(
                                                new MenuListBean(childrenMinUnit.getId(), childrenMinUnit.getAction(),
                                                        childrenMinUnit.getSequence(), childrenMinUnit.getWeb_icon()
                                                        , childrenMinUnit.getName(), user_id, Integer.parseInt(parentMinUnitID)));
                                    }
                                }
                            }
                        }
                    }
                    long count = menuListBeanDao.count();
                    MyLog.e(TAG,"menuListBeanDao里面的数量是"+count);
                }
                toMainActivity();
//                getSalesList();

            }
        });
    }

    private void getSalesList() {
        inventoryApi = RetrofitClient.getInstance(LoginActivity.this).create(InventoryApi.class);
        HashMap<Object, Object> objectObjectHashMap = new HashMap<>();
        objectObjectHashMap.put("name", null);
        // type: ‘supplier’ or ‘customer’
        objectObjectHashMap.put("type", "supplier");
        Call<SearchSupplierResponse> stringCall = inventoryApi.searchSupplier(objectObjectHashMap);
        stringCall.enqueue(new Callback<SearchSupplierResponse>() {
            @Override
            public void onResponse(Call<SearchSupplierResponse> call, Response<SearchSupplierResponse> response) {
                if (response.body().getResult() != null) {
                    List<SearchSupplierResponse.ResultBean.ResDataBean> res_data = response.body().getResult().getRes_data();
                    if (res_data != null && res_data.size() > 0) {
                        for (SearchSupplierResponse.ResultBean.ResDataBean resDataBean : res_data) {
                            contactsBeanDao.insertOrReplace(new ContactsBean(resDataBean.getComment(), resDataBean.getPhone()
                                    , resDataBean.getPartner_id(), resDataBean.getName(), resDataBean.getX_qq()));
                        }
                    }
                }
                long count = contactsBeanDao.count();
                MyLog.e(TAG,"contactsBeanDao里面的数量是"+count);
                toMainActivity();
            }
            @Override
            public void onFailure(Call<SearchSupplierResponse> call, Throwable t) {
            }
        });

    }

    private void toMainActivity(){
        if (progressDialog.isShowing()) {
            progressDialog.dismiss();
        }
        finish();
        IntentFactory.start_MainActivity(LoginActivity.this);
    }
}
