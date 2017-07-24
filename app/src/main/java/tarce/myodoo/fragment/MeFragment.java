package tarce.myodoo.fragment;

import android.app.ProgressDialog;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.os.Handler;
import android.os.Message;
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

import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;

import butterknife.ButterKnife;
import butterknife.InjectView;
import butterknife.OnClick;
import retrofit2.Call;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava.RxJavaCallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;
import tarce.api.MyCallback;
import tarce.api.OKHttpFactory;
import tarce.api.RetrofitClient;
import tarce.api.api.InventoryApi;
import tarce.myodoo.R;
import tarce.myodoo.activity.LoginActivity;
import tarce.myodoo.activity.NFCReadingActivity;
import tarce.myodoo.utils.UserManager;
import tarce.support.SharePreferenceUtils;
import tarce.support.Toolkits;

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

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_4, null);
        ButterKnife.inject(this, view);
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
