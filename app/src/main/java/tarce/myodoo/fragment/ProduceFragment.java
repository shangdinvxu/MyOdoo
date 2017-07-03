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
import tarce.model.inventory.LoadProductBean;
import tarce.myodoo.IntentFactory;
import tarce.myodoo.MyApplication;
import tarce.myodoo.R;
import tarce.myodoo.activity.GetPickNumActivity;
import tarce.myodoo.activity.ProcessOfPersonActivity;
import tarce.myodoo.adapter.product.ProduceRednumAdapter;
import tarce.myodoo.bean.MenuBean;
import tarce.support.ToastUtils;

/**\
 * 生产界面
 * Created by Daniel.Xu on 2017/4/20.
 */

public class ProduceFragment extends Fragment {

    public List<MenuBean> list;
    public ProduceRednumAdapter sectionAdapter;
    @InjectView(R.id.recyclerview)
    RecyclerView recyclerview;
    private InventoryApi inventoryApi;
    private LoadProductBean.LoadResultBean.ThreeSubResult res_data;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        initData();
    }

    private void initData() {
        list = new ArrayList<>();
        list.add(new MenuBean("领料", 0));
        list.add(new MenuBean("等待生产", 0));
        list.add(new MenuBean("生产中", 0));
        list.add(new MenuBean("退料", 0));
        list.add(new MenuBean("等待返工", 0));
        list.add(new MenuBean("返工中", 0));
        list.add(new MenuBean("已完成", 0));
    }


    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_2, null);
        ButterKnife.inject(this, view);
        sectionAdapter = new ProduceRednumAdapter(R.layout.mian_list_item, list);
        setRecyclerview(recyclerview);
        recyclerview.setAdapter(sectionAdapter);
        setOnclick();
        initRedNum();
        return view;
    }

    @Override
    public void onResume(){
        if (res_data==null){
            initRedNum();
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
                if (response.body() == null)return;
                try {
                    if (response.body().getResult().getRes_data()!=null && response.body().getResult().getRes_code() == 1){
                        res_data = response.body().getResult().getRes_data();
                        Integer needaction_counter0 = res_data.getLinkloving_mrp_extend_menu_mrp_finish_prepare_material().getNeedaction_counter();
                        list.get(0).setNumber(needaction_counter0);
                        Integer needaction_counter1 = res_data.getLinkloving_mrp_extend_menu_mrp_already_picking().getNeedaction_counter();
                        list.get(1).setNumber(needaction_counter1);
                        Integer needaction_counter2 = res_data.getLinkloving_mrp_extend_menu_mrp_progress().getNeedaction_counter();
                        list.get(2).setNumber(needaction_counter2);
                        Integer needaction_counter3 = res_data.getLinkloving_mrp_extend_menu_mrp_waiting_inventory_material().getNeedaction_counter();
                        list.get(3).setNumber(needaction_counter3);
                        Integer needaction_counter4 = res_data.getLinkloving_mrp_extend_mrp_production_qc_inspection_fail().getNeedaction_counter();
                        list.get(4).setNumber(needaction_counter4);
                        Integer needaction_counter5 = res_data.getLinkloving_mrp_extend_menu_mrp_rework_ing().getNeedaction_counter();
                        list.get(5).setNumber(needaction_counter5);
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

    /**
     * item点击事件
     * */
    private void setOnclick() {
        sectionAdapter.setOnItemClickListener(new BaseQuickAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(BaseQuickAdapter adapter, View view, int position) {
                String name = list.get(position).getName();
                switch (name){
                    case "领料":
                        IntentFactory.start_ProducLl_Activity(getActivity(), "领料", "finish_prepare_material");
                        break;
                    case "等待生产":
                        Intent intent = new Intent(getActivity(), GetPickNumActivity.class);
                        startActivity(intent);
                        break;
                    case "生产中":
                        Intent intent1 = new Intent(getActivity(), ProcessOfPersonActivity.class);
                        startActivity(intent1);
                        break;
                    case "退料":
                        IntentFactory.start_ProducLl_Activity(getActivity(), "退料", "waiting_inventory_material");
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
