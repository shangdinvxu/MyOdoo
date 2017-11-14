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
import retrofit2.Callback;
import retrofit2.Response;
import tarce.api.MyCallback;
import tarce.api.RetrofitClient;
import tarce.api.api.InventoryApi;
import tarce.model.LoadActionBean;
import tarce.model.inventory.LoadProductBean;
import tarce.myodoo.IntentFactory;
import tarce.myodoo.MyApplication;
import tarce.myodoo.R;
import tarce.myodoo.activity.GetPickNumActivity;
import tarce.myodoo.activity.ProcessOfPersonActivity;
import tarce.myodoo.activity.device.DeviceSelectActivity;
import tarce.myodoo.activity.engineer.EngineerActivity;
import tarce.myodoo.activity.outsourcing.OutSourcingActivity;
import tarce.myodoo.activity.product.AgainProductActivity;
import tarce.myodoo.activity.product.SecondProductActivity;
import tarce.myodoo.adapter.SectionAdapter;
import tarce.myodoo.bean.MainItemBean;
import tarce.myodoo.bean.MenuBean;
import tarce.support.MyLog;
import tarce.support.ToastUtils;

/**\
 * 生产界面
 * Created by Daniel.Xu on 2017/4/20.
 */

public class ProduceFragment extends Fragment {

    public List<MainItemBean> list;
   // public List<MenuBean> list;
    //public ProduceRednumAdapter sectionAdapter;
    public SectionAdapter sectionAdapter;
    @InjectView(R.id.recyclerview)
    RecyclerView recyclerview;
    private InventoryApi inventoryApi;
    private LoadProductBean.LoadResultBean.ThreeSubResult res_data;
    private LoadActionBean.ResultBean.ResDataBean res_dataTwo;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        initData();
    }

    private void initData() {
        list = new ArrayList<>();
        list.add(new MainItemBean(new MenuBean("生产备料", 0)));
        list.add(new MainItemBean(new MenuBean("仓库检验退料", 0)));
        list.add(new MainItemBean(new MenuBean("生产入库", 0)));
        list.add(new MainItemBean(true, ""));
      //  list.add(new MainItemBean(new MenuBean("领料", 0)));
        list.add(new MainItemBean(new MenuBean("等待生产", 0)));
        list.add(new MainItemBean(new MenuBean("生产中", 0)));
        list.add(new MainItemBean(new MenuBean("外协", 0)));
        list.add(new MainItemBean(new MenuBean("二次生产", 0)));
        list.add(new MainItemBean(new MenuBean("清点退料", 0)));
        list.add(new MainItemBean(new MenuBean("等待返工", 0)));
        list.add(new MainItemBean(new MenuBean("返工中", 0)));
        list.add(new MainItemBean(new MenuBean("已完成", 0)));
        list.add(new MainItemBean(true, ""));
        list.add(new MainItemBean(new MenuBean("工程领料", 0)));
        list.add(new MainItemBean(new MenuBean("设备管理", 0)));
        list.add(new MainItemBean(true,""));
        list.add(new MainItemBean(new MenuBean("二次加工", 0)));
        list.add(new MainItemBean(true,""));
        /*list = new ArrayList<>();
        list.add(new MenuBean("领料", 0));
        list.add(new MenuBean("等待生产", 0));
        list.add(new MenuBean("生产中", 0));
        list.add(new MenuBean("退料", 0));
        list.add(new MenuBean("等待返工", 0));
        list.add(new MenuBean("返工中", 0));
        list.add(new MenuBean("已完成", 0));*/
    }


    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_2, null);
        ButterKnife.inject(this, view);
        sectionAdapter = new SectionAdapter(R.layout.mian_list_item, R.layout.adapter_head, list);
        setRecyclerview(recyclerview);
        recyclerview.setAdapter(sectionAdapter);
        setOnclick();
        initRedNum();
        refreshLoadAction();
        return view;
    }

    @Override
    public void onResume(){
        if (res_data==null){
            initRedNum();
            refreshLoadAction();
        }
        super.onResume();
    }

    /**
     * 检测是否需要显示的相应item的红色圈圈加数字
     * */
    private void initRedNum() {
    //    AlertAialogUtils.showDefultProgressDialog(getActivity());
        inventoryApi = RetrofitClient.getInstance(getActivity()).create(InventoryApi.class);
        String[] menus = {"linkloving_mrp_extend.menu_mrp_finish_prepare_material","linkloving_mrp_extend.menu_mrp_already_picking",
                "linkloving_mrp_extend.menu_mrp_progress","linkloving_mrp_extend.menu_mrp_waiting_inventory_material",
                "linkloving_mrp_extend.mrp_production_qc_inspection_fail","linkloving_mrp_extend.menu_mrp_rework_ing"
                ,"linkloving_mrp_extend.menu_mrp_done"};
        HashMap<Object, Object> hashMap = new HashMap<>();
        hashMap.put("xml_names", menus);
        hashMap.put("user_id", MyApplication.userID);
        Call<LoadProductBean> loadActionBeanCall = inventoryApi.load_action(hashMap);
        loadActionBeanCall.enqueue(new MyCallback<LoadProductBean>() {
            @Override
            public void onResponse(Call<LoadProductBean> call, Response<LoadProductBean> response) {
               // AlertAialogUtils.dismissDefultProgressDialog();
                if (response.body() == null || response.body().getResult() == null)return;
                try {
                    if (response.body().getResult().getRes_data()!=null && response.body().getResult().getRes_code() == 1){
                        res_data = response.body().getResult().getRes_data();
//                        Integer needaction_counter0 = res_data.getLinkloving_mrp_extend_menu_mrp_finish_prepare_material().getNeedaction_counter();
//                        list.get(4).t.setNumber(needaction_counter0);
                        Integer needaction_counter1 = res_data.getLinkloving_mrp_extend_menu_mrp_already_picking().getNeedaction_counter();
                        list.get(4).t.setNumber(needaction_counter1);
                        Integer needaction_counter2 = res_data.getLinkloving_mrp_extend_menu_mrp_progress().getNeedaction_counter();
                        list.get(5).t.setNumber(needaction_counter2);
                        Integer needaction_counter3 = res_data.getLinkloving_mrp_extend_menu_mrp_waiting_inventory_material().getNeedaction_counter();
                        list.get(7).t.setNumber(needaction_counter3);
                        Integer needaction_counter4 = res_data.getLinkloving_mrp_extend_mrp_production_qc_inspection_fail().getNeedaction_counter();
                        list.get(8).t.setNumber(needaction_counter4);
                        Integer needaction_counter5 = res_data.getLinkloving_mrp_extend_menu_mrp_rework_ing().getNeedaction_counter();
                        list.get(9).t.setNumber(needaction_counter5);
                        //    Integer needaction_counter6 = response.body().getResult().getRes_data().getLinkloving_mrp_extend_menu_mrp_progress().getNeedaction_counter();
                        //   list.get(6).setNumber(0);
                        sectionAdapter.notifyDataSetChanged();
                    }
                }catch (Exception e){
                    ToastUtils.showCommonToast(getActivity(), e.toString());
                }
            }

            @Override
            public void onFailure(Call<LoadProductBean> call, Throwable t) {
              //  AlertAialogUtils.dismissDefultProgressDialog();
                super.onFailure(call, t);
            }
        });
    }
    private void refreshLoadAction() {
        HashMap<Object, Object> objectObjectHashMap = new HashMap<>();
        String[] names = {"linkloving_mrp_extend.menu_mrp_prepare_material_ing", "linkloving_mrp_extend.menu_mrp_waiting_material",
                "linkloving_mrp_extend.menu_mrp_waiting_warehouse_inspection",
                "linkloving_mrp_extend.mrp_production_action_qc_success"};
        objectObjectHashMap.put("xml_names", names);
        objectObjectHashMap.put("user_id", MyApplication.userID);
        Call<LoadActionBean> objectCall = inventoryApi.load_actionCall(objectObjectHashMap);
        objectCall.enqueue(new Callback<LoadActionBean>() {
            @Override
            public void onResponse(Call<LoadActionBean> call, Response<LoadActionBean> response) {
                if (response.body() == null || response.body().getResult() == null)return;
                try {
                    if (response.body().getResult().getRes_data() != null && response.body().getResult().getRes_code() == 1) {
                        res_dataTwo = response.body().getResult().getRes_data();
                        Integer needaction_counter = res_dataTwo.getLinkloving_mrp_extend_menu_mrp_prepare_material_ing().getNeedaction_counter();
                        Integer needaction_counter1 = res_dataTwo.getLinkloving_mrp_extend_menu_mrp_waiting_warehouse_inspection().getNeedaction_counter();
                        Integer needaction_counter2 = res_dataTwo.getLinkloving_mrp_extend_mrp_production_action_qc_success().getNeedaction_counter();
                        Integer needaction_counter3 = res_dataTwo.getLinkloving_mrp_extend_menu_mrp_waiting_material().getNeedaction_counter();
                        list.get(0).t.setNumber(needaction_counter + needaction_counter3);
                        list.get(1).t.setNumber(needaction_counter1);
                        list.get(2).t.setNumber(needaction_counter2);
//                    //               warehouseFragment.list.get(7).t.setNumber(needaction_counter2);
                        sectionAdapter.notifyDataSetChanged();
                    }
                }catch (Exception e){
                    ToastUtils.showCommonToast(getActivity(),e.toString());
                }
            }

            @Override
            public void onFailure(Call<LoadActionBean> call, Throwable t) {
                MyLog.e("WarehouseFragment", t.toString());
            }
        });
    }

    /**
     * item点击事件
     * */
    private void setOnclick() {
        sectionAdapter.setOnItemClickListener(new BaseQuickAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(BaseQuickAdapter adapter, View view, int position) {
                if (position == 3 || position == 12 || position == 15){
                    return;
                }
                String name = list.get(position).t.getName();
                switch (name){
                    case "生产备料":
                        IntentFactory.start_SeProce_Activity(getActivity());
                        break;
                    case "仓库检验退料":
                        IntentFactory.start_ProducLl_Activity(getActivity(), "仓库检验退料", "waiting_warehouse_inspection");
                        break;
                    case "生产入库":
                        IntentFactory.start_WaitRework_Activity(getActivity(),"生产入库", "qc_success");
                        break;
//                    case "领料":
//                        IntentFactory.start_ProducLl_Activity(getActivity(), "领料", "finish_prepare_material");
//                        break;
                    case "备料":
                        IntentFactory.start_SeProce_Activity(getActivity());
                        break;
                    case "等待生产":
                        Intent intent = new Intent(getActivity(), GetPickNumActivity.class);
                        startActivity(intent);
                        break;
                    case "生产中":
                        Intent intent1 = new Intent(getActivity(), ProcessOfPersonActivity.class);
                        startActivity(intent1);
                        break;
                    case "清点退料":
                        IntentFactory.start_ProducLl_Activity(getActivity(), "清点退料", "waiting_inventory_material");
                        break;
                    case "等待返工"://和其他不一样
                        IntentFactory.start_WaitRework_Activity(getActivity(), "等待返工","qc_fail");
                        break;
                    case "返工中":
                        IntentFactory.start_ProducLl_Activity(getActivity(), "返工中", "rework_ing");
                        break;
                    case "已完成":
                        IntentFactory.start_ProducLl_Activity(getActivity(), "已完成", "done");
                        break;
                    case "工程领料":
                        Intent intent4 = new Intent(getActivity(), EngineerActivity.class);
                        startActivity(intent4);
                        break;
                    case "设备管理":
                        Intent intent2 = new Intent(getActivity(), DeviceSelectActivity.class);
                        startActivity(intent2);
                        break;
                    case "二次生产":
                        Intent intent3 = new Intent(getActivity(), AgainProductActivity.class);
                        startActivity(intent3);
                        break;
                    case "二次加工":
                        Intent intent5 = new Intent(getActivity(), SecondProductActivity.class);
                        startActivity(intent5);
                        break;
                    case "外协":
                        Intent intent6 = new Intent(getActivity(), OutSourcingActivity.class);
                        startActivity(intent6);
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
    public void onPause() {
        res_data = null;
        super.onPause();
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        ButterKnife.reset(this);
    }
}
