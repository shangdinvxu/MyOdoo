package tarce.myodoo.fragment;


import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
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
import tarce.myodoo.activity.device.DeviceSelectActivity;
import tarce.myodoo.activity.engineer.EngineerActivity;
import tarce.myodoo.activity.newproduct.LineAndStateActivity;
import tarce.myodoo.activity.outsourcing.OutSourcingActivity;
import tarce.myodoo.activity.product.AgainProductActivity;
import tarce.myodoo.activity.product.SecondProductActivity;
import tarce.myodoo.adapter.NewSectionAdapter;
import tarce.myodoo.bean.MainProcessBean;
import tarce.myodoo.bean.ProcessBean;
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
    public List<MainProcessBean> list;
    public NewSectionAdapter sectionAdapter;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_2, null);
        ButterKnife.inject(this, view);
        recyclerview.setLayoutManager(new LinearLayoutManager(getActivity()));
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
                    list = new ArrayList<>();
                    for (int i = 0; i < listSubBeen.size(); i++) {
                        list.add(new MainProcessBean(new ProcessBean(listSubBeen.get(i).getName(), listSubBeen.get(i).getProcess_id(), 0)));
                    }
                    list.add(new MainProcessBean(true, ""));
                    list.add(new MainProcessBean(new ProcessBean("工程领料", 1000, 0)));
                    list.add(new MainProcessBean(new ProcessBean("设备管理", 1000, 0)));
                    list.add(new MainProcessBean(new ProcessBean("二次加工", 1000, 0)));
                    list.add(new MainProcessBean(new ProcessBean("外协", 1000, 0)));
                    sectionAdapter = new NewSectionAdapter(R.layout.mian_list_item, R.layout.adapter_head, list);
                    recyclerview.setAdapter(sectionAdapter);
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
        sectionAdapter.setOnItemClickListener(new BaseQuickAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(BaseQuickAdapter adapter, View view, int position) {
                if (position == listSubBeen.size()){
                    return;
                }
                if (sectionAdapter==null)return;
                List<MainProcessBean> data = sectionAdapter.getData();
                String name = data.get(position).t.getName();
                if (name.equals("二次加工")){
                    Intent intent = new Intent(getActivity(), SecondProductActivity.class);
                    startActivity(intent);
                }else if (name.equals("工程领料")){
                    Intent intent4 = new Intent(getActivity(), EngineerActivity.class);
                    startActivity(intent4);
                }else if (name.equals("设备管理")){
                    Intent intent2 = new Intent(getActivity(), DeviceSelectActivity.class);
                    startActivity(intent2);
                }else if (name.equals("外协")){
                    Intent intent3 = new Intent(getActivity(), OutSourcingActivity.class);
                    startActivity(intent3);
                }
                else {
                    Intent intent = new Intent(getActivity(), LineAndStateActivity.class);
                    intent.putExtra("process_id", data.get(position).t.getProcess());
                    startActivity(intent);
                }
            }
        });
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        ButterKnife.reset(this);
    }
}
