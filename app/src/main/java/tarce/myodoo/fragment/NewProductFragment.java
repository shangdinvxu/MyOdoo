package tarce.myodoo.fragment;


import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;


import com.chad.library.adapter.base.BaseQuickAdapter;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import butterknife.ButterKnife;
import butterknife.InjectView;
import retrofit2.Call;
import retrofit2.Response;
import tarce.api.MyCallback;
import tarce.api.RetrofitClient;
import tarce.api.api.InventoryApi;
import tarce.model.GetProcessBean;
import tarce.myodoo.R;
import tarce.myodoo.adapter.product.NewProcessAdapter;
import tarce.myodoo.uiutil.DividerGridItemDecoration;
import tarce.myodoo.uiutil.TipDialog;
import tarce.support.ToastUtils;

import static tarce.support.AlertAialogUtils.dismissDefultProgressDialog;
import static tarce.support.AlertAialogUtils.showDefultProgressDialog;

/**
 * Created by zouwansheng on 2017/11/16.
 */

public class NewProductFragment extends Fragment {

    @InjectView(R.id.recyclerview)
    RecyclerView recyclerview;
    private InventoryApi inventoryApi;
    private List<GetProcessBean.TestRSubBean.ListSubBean> listSubBeen;
    private NewProcessAdapter processAdapter;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_2, null);
        ButterKnife.inject(this, view);
        recyclerview.setLayoutManager(new GridLayoutManager(getActivity(), 3));
        recyclerview.addItemDecoration(new DividerGridItemDecoration(getActivity()));
        inventoryApi = RetrofitClient.getInstance(getActivity()).create(InventoryApi.class);
        getProcessList();
        return view;
    }

    @Override
    public void onResume() {
        super.onResume();

    }

    //获取工序列表
    private void getProcessList() {
        showDefultProgressDialog(getActivity());
        inventoryApi = RetrofitClient.getInstance(getActivity()).create(InventoryApi.class);
        Call<GetProcessBean> processBeanCall = inventoryApi.getProcess(new HashMap());
        processBeanCall.enqueue(new MyCallback<GetProcessBean>() {
            @Override
            public void onResponse(Call<GetProcessBean> call, Response<GetProcessBean> response) {
                dismissDefultProgressDialog();
                if (response.body() == null)return;
                if (response.body().getError()!=null){
                    new TipDialog(getActivity(), R.style.MyDialogStyle, response.body().getError().getData().getMessage())
                            .show();
                    return;
                }
                if (response.body().getResult().getRes_data()!=null && response.body().getResult().getRes_code() == 1){
                    listSubBeen = response.body().getResult().getRes_data();
                    processAdapter = new NewProcessAdapter(R.layout.item_gridview, listSubBeen);
                    recyclerview.setAdapter(processAdapter);
                    initListener();
                }
            }
            @Override
            public void onFailure(Call<GetProcessBean> call, Throwable t) {
                dismissDefultProgressDialog();
                ToastUtils.showCommonToast(getActivity(), t.toString());
                Log.i("NewProductFragment",t.toString());
            }
        });
    }

    //点击事件
    private void initListener() {
        processAdapter.setOnItemClickListener(new BaseQuickAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(BaseQuickAdapter adapter, View view, int position) {

            }
        });
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        ButterKnife.reset(this);
    }
}
