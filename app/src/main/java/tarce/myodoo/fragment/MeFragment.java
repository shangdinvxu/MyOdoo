package tarce.myodoo.fragment;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;

import java.util.HashMap;
import java.util.List;
import java.util.Objects;

import butterknife.ButterKnife;
import butterknife.InjectView;
import butterknife.OnClick;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava.RxJavaCallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;
import tarce.api.OKHttpFactory;
import tarce.api.RetrofitClient;
import tarce.api.api.InventoryApi;
import tarce.model.CompanyTwoBean;
import tarce.model.ComponyQueryBean;
import tarce.model.LoginResponse;
import tarce.myodoo.R;
import tarce.myodoo.activity.BomFramworkActivity;
import tarce.myodoo.activity.LoginActivity;
import tarce.myodoo.activity.NFCReadingActivity;
import tarce.myodoo.uiutil.CompanyDialog;
import tarce.myodoo.uiutil.TipDialog;
import tarce.myodoo.utils.UserManager;
import tarce.support.SharePreferenceUtils;
import tarce.support.ToastUtils;
import tarce.support.Toolkits;

import static tarce.api.RetrofitClient.Url;

/**
 * 我界面
 * Created by Daniel.Xu on 2017/4/20.
 */

public class MeFragment extends Fragment {

    @InjectView(R.id.heard)
    ImageView heard;
    @InjectView(R.id.user_name)
    TextView userName;
    @InjectView(R.id.db)
    TextView db;
    @InjectView(R.id.address)
    TextView address;
    @InjectView(R.id.version)
    TextView version;
    @InjectView(R.id.button_exit)
    Button buttonExit;
    @InjectView(R.id.insert_nfc)
    TextView insertNfc;
    @InjectView(R.id.distance_name)
    TextView distanceName;
    private Retrofit retrofit;
    private LoginResponse userInfoBean;
    private ProgressDialog progressDialog;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_4, null);
        ButterKnife.inject(this, view);
        retrofit = new Retrofit.Builder()
                //设置OKHttpClient
                .client(new OKHttpFactory(getActivity()).getOkHttpClient())

                .baseUrl(Url+"/linkloving_oa_api/")
                //gson转化器
                .addConverterFactory(GsonConverterFactory.create())
                .addCallAdapterFactory(RxJavaCallAdapterFactory.create())
                .build();
        Url = RetrofitClient.Url;
        progressDialog = new ProgressDialog(getActivity());
        progressDialog.setMessage("加载中。。。");
        initData();
        return view;
    }

    @Override
    public void onResume() {
        initData();
        super.onResume();
    }

    private void initData() {
        String database = SharePreferenceUtils.getString("database", "null", getActivity());
        db.setText(database);
        String email = SharePreferenceUtils.getString("email", "error", getActivity());
        userName.setText(email);
        String url = SharePreferenceUtils.getString("url", "null", getActivity());
        address.setText(url);
        version.setText(Toolkits.getVersionName(getActivity()));
        String user_ava = SharePreferenceUtils.getString("user_ava", "null", getActivity());
        Log.i("user_ava", user_ava);
        Glide.with(getActivity()).load(user_ava).into(heard);
        try {
            if (UserManager.getSingleton().getGrops().contains("group_mrp_manager")) {
                insertNfc.setVisibility(View.VISIBLE);
            }
        } catch (Exception e) {
            Log.e("MeFragment", "权限问题");
        }
        userInfoBean = UserManager.getSingleton().getUserInfoBean();
        if (userInfoBean != null) {
            distanceName.setText(userInfoBean.getResult().getRes_data().getCompany());
        }
    }

    //点击切换公司
    @OnClick(R.id.distance_name)
    void changeDistance(View view){
        final InventoryApi inventoryApi = retrofit.create(InventoryApi.class);
        progressDialog.show();
        HashMap<Object, Object> hashMap = new HashMap<>();
        final int user_id = SharePreferenceUtils.getInt("user_id", 1, getActivity());
        hashMap.put("user_id", user_id);
        Call<ComponyQueryBean> componyQueryBeanCall = inventoryApi.changeCompany(hashMap);
        componyQueryBeanCall.enqueue(new Callback<ComponyQueryBean>() {
            @Override
            public void onResponse(Call<ComponyQueryBean> call, Response<ComponyQueryBean> response) {
                progressDialog.dismiss();
                if (response.body() == null)return;
                if (response.body().getError()!=null){
                    new TipDialog(getActivity(), R.style.MyDialogStyle, response.body().getError().getMessage())
                            .show();
                    return;
                }
                List<ComponyQueryBean.ResultBean.ResDataBean> result = response.body().getResult().getRes_data();
                CompanyDialog dialog = new CompanyDialog(getActivity(), result);
                dialog.show();
                dialog.sendNameCompany(new CompanyDialog.GetCompany() {
                    @Override
                    public void getCompanyName(final String name, int companyId) {
                        distanceName.setText(name);
                        HashMap<Object, Object> hashMap = new HashMap<>();
                        hashMap.put("id", companyId);
                        hashMap.put("user_id", user_id);
                        Call<CompanyTwoBean> objectCall = inventoryApi.changeCompanyTwo(hashMap);
                        objectCall.enqueue(new Callback<CompanyTwoBean>() {
                            @Override
                            public void onResponse(Call<CompanyTwoBean> call, Response<CompanyTwoBean> response) {
                                if (response.body() == null)return;
                                if (response.body().getResult().getRes_code() == 1){
                                    if (response.body().getResult().getRes_data().getSuccess() == 1){
                                        ToastUtils.showCommonToast(getActivity(), "切换成功");
                                        userInfoBean.getResult().getRes_data().setCompany(name);
                                    }
                                }
                            }

                            @Override
                            public void onFailure(Call<CompanyTwoBean> call, Throwable t) {
                                Log.e("zws", t.toString());
                            }
                        });
                    }
                });
            }

            @Override
            public void onFailure(Call<ComponyQueryBean> call, Throwable t) {
                progressDialog.dismiss();
                Log.e("zws", t.toString());
            }
        });
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        ButterKnife.reset(this);
    }


    /**
     * 退出登录  需要删除一些本地数据，缓存的数据
     */
    @OnClick(R.id.button_exit)
    void clickExit(View view) {
        SharePreferenceUtils.putInt("user_id", -1000, getActivity());
        SharePreferenceUtils.putString("email", "", getActivity());
        SharePreferenceUtils.putString("user_ava", "", getActivity());
        UserManager.getSingleton().getGrops().clear();
        UserManager.getSingleton().setUserInfoBean(null);
        Intent intent = new Intent(getActivity(), LoginActivity.class);
        startActivity(intent);
        //  getActivity().finish();
    }

    @OnClick(R.id.insert_nfc)
    void insertNFC(View view) {
        Intent intent3 = new Intent(getActivity(), NFCReadingActivity.class);
        startActivity(intent3);
    }

}
