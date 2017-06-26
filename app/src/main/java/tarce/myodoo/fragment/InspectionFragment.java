package tarce.myodoo.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
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
import tarce.model.GetGroupByListresponse;
import tarce.model.inventory.LoadInspectionBean;
import tarce.myodoo.MyApplication;
import tarce.myodoo.R;
import tarce.myodoo.activity.inspect.InspectionSubActivity;
import tarce.myodoo.activity.takedeliver.TakeDeliveListActivity;
import tarce.myodoo.adapter.SectionAdapter;
import tarce.myodoo.bean.MainItemBean;
import tarce.myodoo.bean.MenuBean;
import tarce.support.ToastUtils;

/**
 * 品检界面
 * Created by Daniel.Xu on 2017/4/20.
 */

public class InspectionFragment extends Fragment {
    public List<MainItemBean> list;
    public SectionAdapter sectionAdapter;
    @InjectView(R.id.recyclerview)
    RecyclerView recyclerview;
    private InventoryApi inventoryApi;
    private LoadInspectionBean.ResultBean.ResDataBean res_data;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        initData();
    }

    private void initData() {
        list = new ArrayList<>();
        list.add(new MainItemBean(true, ""));
        list.add(new MainItemBean(new MenuBean("等待生产品检", 0)));
        list.add(new MainItemBean(new MenuBean("品检中", 0)));
        list.add(new MainItemBean(true, ""));
        list.add(new MainItemBean(new MenuBean("入库品检", 0)));
    }

    @Override
    public void onResume() {
        if (res_data == null){
            initRedNum();
            initDeliever();
        }
        super.onResume();
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_3, null);
        ButterKnife.inject(this, view);
        inventoryApi = RetrofitClient.getInstance(getActivity()).create(InventoryApi.class);
        sectionAdapter = new SectionAdapter(R.layout.mian_list_item, R.layout.adapter_head, list);
        setRecyclerview(recyclerview);
        recyclerview.setAdapter(sectionAdapter);
        initRedNum();
        setOnClick();
        return view;
    }

    /**
     * 点击事件
     * */
    private void setOnClick() {
        sectionAdapter.setOnItemClickListener(new BaseQuickAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(BaseQuickAdapter adapter, View view, int position) {
                if (position==0 || position==3){
                    return;
                }
                String name = list.get(position).t.getName();
                switch (name){
                    case "等待生产品检":
                        Intent intent = new Intent(getActivity(), InspectionSubActivity.class);
                        intent.putExtra("state", "draft");
                        intent.putExtra("autoRefresh", 0);
                        startActivity(intent);
                        break;
                    case "品检中":
                        Intent intent1 = new Intent(getActivity(), InspectionSubActivity.class);
                        intent1.putExtra("state", "qc_ing");
                        startActivity(intent1);
                        break;
                    case "入库品检":
                        Intent intent2 = new Intent(getActivity(), TakeDeliveListActivity.class);
                        intent2.putExtra("from", "yes");
                        intent2.putExtra("type_code", "");
                        intent2.putExtra("state","qc_check");
                        intent2.putExtra("partner_id", 0);
                        intent2.putExtra("picking_type_id", 1);
                        startActivity(intent2);
                        break;
                }
            }
        });
    }

    /**
     *初始化数据
     * */
    private void initDeliever(){
        HashMap<Object, Object> hashMap = new HashMap<>();
        hashMap.put("partner_id", 0);
        hashMap.put("groupby", "picking_type_id");
        hashMap.put("model", "stock.picking");
        Call<GetGroupByListresponse> groupsByList = inventoryApi.getGroupsByList(hashMap);
        groupsByList.enqueue(new MyCallback<GetGroupByListresponse>() {
            @Override
            public void onResponse(Call<GetGroupByListresponse> call, Response<GetGroupByListresponse> response) {
                if (response.body() == null) return;
                if (response.body().getResult().getRes_code() == 1 && response.body().getResult().getRes_data()!= null) {
                    int size = response.body().getResult().getRes_data().size();
                    GetGroupByListresponse.ResultBean.ResDataBean resDataBean = null;
                    for (int i = 0; i < size; i++) {
                        if (response.body().getResult().getRes_data().get(i).getPicking_type_code().equals("incoming")){
                            resDataBean = response.body().getResult().getRes_data().get(i);
                            break;
                        }
                    }
                    for (int i = 0; i < resDataBean.getStates().size(); i++) {
                        if (resDataBean.getStates().get(i).getState().equals("qc_check")){
                            list.get(4).t.setNumber(resDataBean.getStates().get(i).getState_count());
                            sectionAdapter.notifyDataSetChanged();
                            break;
                        }
                    }
                }else {
                    ToastUtils.showCommonToast(getActivity(), "数据出现错误，请联系开发人员调试");
                }
            }

            @Override
            public void onFailure(Call<GetGroupByListresponse> call, Throwable t) {
                ToastUtils.showCommonToast(getActivity(), t.toString());
            }
        });
    }
    private void initRedNum(){
        String[] menus = {"linkloving_mrp_extend.mrp_production_wait_qc_inspection","linkloving_mrp_extend.mrp_production_qc_inspecting"};
        HashMap<Object, Object> hashMap = new HashMap<>();
        hashMap.put("xml_names", menus);
        hashMap.put("user_id", MyApplication.userID);
        Call<LoadInspectionBean> loadActionBeanCall = inventoryApi.load_actionInspec(hashMap);
        loadActionBeanCall.enqueue(new MyCallback<LoadInspectionBean>() {
            @Override
            public void onResponse(Call<LoadInspectionBean> call, Response<LoadInspectionBean> response) {
                    if (response.body() == null)return;
                    if (response.body().getResult().getRes_code() == 1){
                        res_data = response.body().getResult().getRes_data();
                        Integer needaction_counter0 = res_data.getLinkloving_mrp_extend_mrp_production_wait_qc_inspection().getNeedaction_counter();
                        list.get(1).t.setNumber(needaction_counter0);
                        Integer needaction_counter1 = res_data.getLinkloving_mrp_extend_mrp_production_qc_inspecting().getNeedaction_counter();
                        list.get(2).t.setNumber(needaction_counter1);
                        sectionAdapter.notifyDataSetChanged();
                    }
            }
            @Override
            public void onFailure(Call<LoadInspectionBean> call, Throwable t) {
                super.onFailure(call, t);
            }
        });
    }


    public void setRecyclerview(RecyclerView recyclerview){
        recyclerview.setLayoutManager(new LinearLayoutManager(getActivity()));
        recyclerview.addItemDecoration(new DividerItemDecoration(getActivity(),
                DividerItemDecoration.VERTICAL));
    }


    @Override
    public void onPause() {
        if (res_data != null){
            res_data = null;
        }
        super.onPause();
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        ButterKnife.reset(this);
    }
}
