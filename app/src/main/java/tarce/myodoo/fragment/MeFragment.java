package tarce.myodoo.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;

import butterknife.ButterKnife;
import butterknife.InjectView;
import tarce.api.RetrofitClient;
import tarce.api.api.LoginApi;
import tarce.myodoo.R;
import tarce.myodoo.activity.LoginActivity;
import tarce.support.SharePreferenceUtils;
import tarce.support.Toolkits;

/**
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

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_4, null);
        ButterKnife.inject(this, view);
        initData();
        return view;
    }

    private void initData() {
        String database = SharePreferenceUtils.getString("database", "null", getActivity());
        db.setText(database);
        String email = SharePreferenceUtils.getString("email", "error", getActivity());
        userName.setText(email);
        String url = SharePreferenceUtils.getString("url", "null",getActivity());
        address.setText(url);
        version.setText(Toolkits.getVersionCode(getActivity())+"");
        String user_ava = SharePreferenceUtils.getString("user_ava", "null", getActivity());
        Glide.with(getActivity()).load(user_ava).into(heard);
    }


    @Override
    public void onDestroyView() {
        super.onDestroyView();
        ButterKnife.reset(this);
    }
}
