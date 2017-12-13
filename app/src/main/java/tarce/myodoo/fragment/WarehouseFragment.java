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
import android.widget.Button;

import com.chad.library.adapter.base.BaseQuickAdapter;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import butterknife.ButterKnife;
import butterknife.InjectView;
import butterknife.OnClick;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import tarce.api.RetrofitClient;
import tarce.api.api.InventoryApi;
import tarce.model.LoadActionBean;
import tarce.myodoo.IntentFactory;
import tarce.myodoo.MyApplication;
import tarce.myodoo.R;
import tarce.myodoo.activity.ConfirmPurchaseActivity;
import tarce.myodoo.activity.MainActivity;
import tarce.myodoo.activity.engineer.EngineerActivity;
import tarce.myodoo.activity.engineer.ProjectActivity;
import tarce.myodoo.activity.incentroy.InventroyActivity;
import tarce.myodoo.activity.inquiriesstock.StockListActivity;
import tarce.myodoo.activity.inquiriesstock.StockMoveListActivity;
import tarce.myodoo.activity.takedeliver.TakeDeliverActivity;
import tarce.myodoo.adapter.SectionAdapter;
import tarce.myodoo.bean.MainItemBean;
import tarce.myodoo.bean.MenuBean;
import tarce.myodoo.uiutil.TipDialog;
import tarce.myodoo.utils.UserManager;
import tarce.support.MyLog;
import tarce.support.ToastUtils;

/**
 * 仓库界面
 * Created by Daniel.Xu on 2017/4/20.
 */

public class WarehouseFragment extends Fragment {
    @InjectView(R.id.sales_out)
    Button salesOut;
    @InjectView(R.id.recyclerview)
    RecyclerView recyclerview;
    public  List<MainItemBean> list;
    public SectionAdapter sectionAdapter;
    private InventoryApi inventoryApi;
    private LoadActionBean.ResultBean.ResDataBean res_data;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        initData();
    }

    private void initData() {
        list = new ArrayList<>();
        list.add(new MainItemBean(true, ""));
        list.add(new MainItemBean(new MenuBean("收货", 0)));
        list.add(new MainItemBean(new MenuBean("采购确认", 0)));
        list.add(new MainItemBean(new MenuBean("采购退货", 0)));
        list.add(new MainItemBean(true, ""));
        list.add(new MainItemBean(new MenuBean("销售出货", 0)));
        list.add(new MainItemBean(new MenuBean("销售退货", 0)));
        list.add(new MainItemBean(true, ""));
        list.add(new MainItemBean(new MenuBean("零售出货", 0)));
        list.add(new MainItemBean(new MenuBean("零售退货", 0)));
        list.add(new MainItemBean(true, ""));
        list.add(new MainItemBean(new MenuBean("生产入库", 0)));
        list.add(new MainItemBean(true, ""));
        list.add(new MainItemBean(new MenuBean("盘点", 0)));
        list.add(new MainItemBean(new MenuBean("物料异常上报", 0)));
        list.add(new MainItemBean(new MenuBean("库存查询", 0)));
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_1, null);
        ButterKnife.inject(this, view);
        inventoryApi = RetrofitClient.getInstance(getActivity()).create(InventoryApi.class);
        setRecyclerview(recyclerview);
        sectionAdapter = new SectionAdapter(R.layout.mian_list_item, R.layout.adapter_head, list);
        recyclerview.setAdapter(sectionAdapter);
        initListener();
        return view;
    }

    @Override
    public void onResume() {
        super.onResume();
        if (res_data ==null){
        }
    }

    @Override
    public void onPause() {
        super.onPause();
        if (res_data!=null){
            res_data = null;
        }
    }

    private void initListener() {
        sectionAdapter.setOnItemClickListener(new BaseQuickAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(BaseQuickAdapter adapter, View view, int position) {
                if (position==0 || position==4 || position==7 || position==10 || position==12){
                    return;
                }
                String name = list.get(position).t.getName();
                switch (name){
                    case "收货":
                        Intent intent = new Intent(getActivity(), TakeDeliverActivity.class);
                        startActivity(intent);
                        break;
                    case "采购确认":
                        Intent intent1 = new Intent(getActivity(), ConfirmPurchaseActivity.class);
                        startActivity(intent1);
                        break;
                    case "销售出货":
                        IntentFactory.start_SalesOut_Activity(getActivity());
                        break;
                    case "零售出货":
                        IntentFactory.start_RetailSub_Activity(getActivity());
                        break;
                    case "零售退货":
                        IntentFactory.start_RetailSub_Activity(getActivity());
                        break;
                    case "生产入库":
                        IntentFactory.start_WaitRework_Activity(getActivity(),"生产入库", "qc_success");
                        break;
                    case "盘点":
                        if (!UserManager.getSingleton().getGrops().contains("group_stock_control_manager")) {
                            ToastUtils.showCommonToast(getActivity(), "抱歉，您无此权限查看盘点相关");
                            return;
                        }
                        Intent intent2 = new Intent(getActivity(), InventroyActivity.class);
                        startActivity(intent2);
                        break;
                    case "库存查询":
                        Intent intent3 = new Intent(getActivity(), StockListActivity.class);
                        startActivity(intent3);
                        break;
                }
            }
        });
    }


    public void setRecyclerview(RecyclerView recyclerview){
        recyclerview.setLayoutManager(new LinearLayoutManager(getActivity()));
        recyclerview.addItemDecoration(new DividerItemDecoration(getActivity(),
                DividerItemDecoration.VERTICAL));
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        ButterKnife.reset(this);
    }

    @OnClick(R.id.sales_out)
    void setSalesOut(View view) {
        IntentFactory.start_SalesOut_Activity(getActivity());
    }

}
