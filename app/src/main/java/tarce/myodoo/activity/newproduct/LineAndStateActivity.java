package tarce.myodoo.activity.newproduct;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.widget.Toast;

import com.chad.library.adapter.base.BaseQuickAdapter;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import butterknife.ButterKnife;
import butterknife.InjectView;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import tarce.api.RetrofitClient;
import tarce.api.api.InventoryApi;
import tarce.model.LineBean;
import tarce.model.ProductLinesBean;
import tarce.model.StateCountBean;
import tarce.myodoo.IntentFactory;
import tarce.myodoo.R;
import tarce.myodoo.activity.BaseActivity;
import tarce.myodoo.activity.BomFramworkActivity;
import tarce.myodoo.activity.LineProductActivity;
import tarce.myodoo.activity.ProductLineListActivity;
import tarce.myodoo.activity.inspect.InspectMoDetailActivity;
import tarce.myodoo.activity.inspect.InspectionSubActivity;
import tarce.myodoo.activity.outsourcing.OutSourcingActivity;
import tarce.myodoo.activity.product.AgainProductActivity;
import tarce.myodoo.adapter.LinesProductAdapter;
import tarce.myodoo.adapter.SectionAdapter;
import tarce.myodoo.adapter.newproduct.LineAdapter;
import tarce.myodoo.bean.MainItemBean;
import tarce.myodoo.bean.MenuBean;
import tarce.myodoo.bean.ProcessBean;
import tarce.myodoo.uiutil.TipDialog;
import tarce.myodoo.utils.StringUtils;
import tarce.support.SharePreferenceUtils;
import tarce.support.ToastUtils;

import static android.R.attr.data;
import static android.R.attr.publicKey;
import static tarce.myodoo.R.id.produce_in;
import static tarce.myodoo.R.id.recyclerview;

/**
 * Created by zouwansheng on 2017/11/16.
 */

public class LineAndStateActivity extends BaseActivity {

    @InjectView(R.id.first_recycler)
    RecyclerView firstRecycler;
    @InjectView(R.id.second_recycler)
    RecyclerView secondRecycler;
    private int process_id;
    private InventoryApi inventoryApi;
    private LineAdapter lineAdapter;
    public List<MainItemBean> list;
    private SectionAdapter sectionAdapter;
    private int line_id = -1000;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_lineadnstate);
        ButterKnife.inject(this);

        inventoryApi = RetrofitClient.getInstance(LineAndStateActivity.this).create(InventoryApi.class);
        Intent intent = getIntent();
        process_id = intent.getIntExtra("process_id", -1000);
        setRecyclerview(firstRecycler);
        setRecyclerview(secondRecycler);
        initData();
    }

    private void initTwoData() {
        list = new ArrayList<>();
        list.add(new MainItemBean(new MenuBean("生产备料", 0)));
        list.add(new MainItemBean(new MenuBean("仓库检验退料", 0)));
        list.add(new MainItemBean(new MenuBean("取消MO待检验退料", 0)));
        list.add(new MainItemBean(new MenuBean("生产入库", 0)));
        list.add(new MainItemBean(new MenuBean("等待生产", 0)));
        list.add(new MainItemBean(new MenuBean("生产中", 0)));
        list.add(new MainItemBean(new MenuBean("二次生产", 0)));
        list.add(new MainItemBean(new MenuBean("清点退料", 0)));
        list.add(new MainItemBean(new MenuBean("取消MO待清点退料", 0)));
        list.add(new MainItemBean(new MenuBean("等待返工", 0)));
        list.add(new MainItemBean(new MenuBean("返工中", 0)));
        list.add(new MainItemBean(new MenuBean("已完成", 0)));
        sectionAdapter = new SectionAdapter(R.layout.mian_list_item, R.layout.adapter_head, list);
        secondRecycler.setAdapter(sectionAdapter);
        initSectionListener();
    }

    private void initSectionListener() {
        sectionAdapter.setOnItemClickListener(new BaseQuickAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(BaseQuickAdapter adapter, View view, int position) {
                List<MainItemBean> data = sectionAdapter.getData();
                switch (data.get(position).t.getName()){
                    case "生产备料":
                        IntentFactory.new_Start_So_Activity(LineAndStateActivity.this, "生产备料", "waiting_material", process_id, line_id);
                        break;
                    case "仓库检验退料":
                        IntentFactory.new_Start_So_Activity(LineAndStateActivity.this, "仓库检验退料", "waiting_warehouse_inspection", process_id, line_id);
                        break;
                    case "取消MO待检验退料":
                        IntentFactory.new_Start_So_Activity(LineAndStateActivity.this, "取消MO待检验退料", "force_cancel_waiting_warehouse_inspection",process_id,line_id);
                        break;
                    case "生产入库":
//                        IntentFactory.new_Start_So_Activity(LineAndStateActivity.this,"生产入库", "qc_success", process_id, line_id);
                        Intent intent = new Intent(LineAndStateActivity.this, ForInspecSoActivity.class);
                        intent.putExtra("name_activity","生产入库");
                        intent.putExtra("state","qc_success");
                        intent.putExtra("process_id", process_id);
                        intent.putExtra("line_id", line_id);
                        startActivity(intent);
                        break;
                    case "等待生产":
                        Intent intent1 = new Intent(LineAndStateActivity.this, SoOriginActivity.class);
                        intent1.putExtra("state", "already_picking");
                        intent1.putExtra("process_id", process_id);
                        intent1.putExtra("line_id", line_id);
                        intent1.putExtra("name_activity","等待生产");
                        startActivity(intent1);
                        break;
                    case "生产中":
                        Intent intent2 = new Intent(LineAndStateActivity.this, SoOriginActivity.class);
                        intent2.putExtra("line_id", line_id);
                        intent2.putExtra("process_id", process_id);
                        intent2.putExtra("state", "progress");
                        intent2.putExtra("name_activity","生产中");
                        startActivity(intent2);
                        break;
                    case "清点退料":
                        IntentFactory.new_Start_So_Activity(LineAndStateActivity.this, "清点退料", "waiting_inventory_material",process_id, line_id);
                        break;
                    case "取消MO待清点退料":
                        IntentFactory.new_Start_So_Activity(LineAndStateActivity.this, "取消MO待清点退料", "force_cancel_waiting_return", process_id, line_id);
                        break;
                    case "等待返工":
//                        IntentFactory.new_Start_So_Activity(LineAndStateActivity.this, "等待返工","qc_fail", process_id, line_id);
                        Intent intent5 = new Intent(LineAndStateActivity.this, ForInspecSoActivity.class);
                        intent5.putExtra("name_activity","等待返工");
                        intent5.putExtra("state","qc_fail");
                        intent5.putExtra("process_id", process_id);
                        intent5.putExtra("line_id", line_id);
                        startActivity(intent5);
                        break;
                    case "返工中":
                        IntentFactory.new_Start_So_Activity(LineAndStateActivity.this, "返工中", "rework_ing", process_id, line_id);
                        break;
                    case "已完成":
                        IntentFactory.new_Start_So_Activity(LineAndStateActivity.this, "已完成", "done", process_id, line_id);
                        break;
                    case "二次生产":
                        Intent intent4 = new Intent(LineAndStateActivity.this, SoOriginActivity.class);
                        intent4.putExtra("line_id", line_id);
                        intent4.putExtra("process_id", process_id);
                        intent4.putExtra("state", "is_secondary_produce");
                        intent4.putExtra("name_activity","二次生产");
                        startActivity(intent4);
                        break;
                }
            }
        });
    }

    private void initData() {
        showDefultProgressDialog();
        HashMap<Object, Object> hashMap = new HashMap<>();
        if (process_id != -1000) {
            hashMap.put("process_id", process_id);
        }
        Call<LineBean> productLines = inventoryApi.getNewProductLines(hashMap);
        productLines.enqueue(new Callback<LineBean>() {
            @Override
            public void onResponse(Call<LineBean> call, Response<LineBean> response) {
                dismissDefultProgressDialog();
                if (response.body() == null) return;
                if (response.body().getError() != null) {
                    new TipDialog(LineAndStateActivity.this, R.style.MyDialogStyle, response.body().getError().getData().getMessage())
                            .show();
                    return;
                }
                if (response.body().getResult().getRes_data() == null && response.body().getResult().getRes_code() == 1){
                    //initTwoData();
                    ToastUtils.showCommonToast(LineAndStateActivity.this, "暂无数据");
                    return;
                }
                if (response.body().getResult().getRes_data() != null && response.body().getResult().getRes_code() == 1) {
                    List<LineBean.ResultBean.ResDataBean> res_data = response.body().getResult().getRes_data();
                    lineAdapter = new LineAdapter(R.layout.item_lineproduct, res_data);
                    firstRecycler.setAdapter(lineAdapter);
                    initListener();
                }
            }

            @Override
            public void onFailure(Call<LineBean> call, Throwable t) {
                dismissDefultProgressDialog();
                Log.e("zws", t.toString());
            }
        });
    }

    private void initListener() {
        lineAdapter.setOnItemClickListener(new BaseQuickAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(BaseQuickAdapter adapter, View view, int position) {
                List<LineBean.ResultBean.ResDataBean> data = lineAdapter.getData();
                data.get(position).setPosition(position);
                lineAdapter.notifyDataSetChanged();
                line_id =  data.get(position).getLine_id();
                initTwoData();
                initCount();
                initQc();
            }
        });
    }
    private void initQc(){
        HashMap<Object, Object> hashMap = new HashMap<>();

        if (process_id != -1000) {
            hashMap.put("process_id", process_id);
        }
        if (line_id != -1000){
            hashMap.put("production_line_id", line_id);
        }
        int partner_id = SharePreferenceUtils.getInt("partner_id", 1000, LineAndStateActivity.this);
        hashMap.put("partner_id", partner_id);
        String[] states = {"qc_success", "qc_fail"};
        hashMap.put("state", states);
        hashMap.put("is_group_by", true);
        Call<StateCountBean> countMrpPro = inventoryApi.getCountQc(hashMap);
        countMrpPro.enqueue(new Callback<StateCountBean>() {
            @Override
            public void onResponse(Call<StateCountBean> call, Response<StateCountBean> response) {
                if (response.body() == null)return;
                if (response.body().getError()!=null){
                    new TipDialog(LineAndStateActivity.this, R.style.MyDialogStyle, response.body().getError().getData().getMessage())
                            .show();
                    return;
                }
                if (response.body().getResult().getRes_code()==1){
                    List<StateCountBean.ResultBean.ResDataBean> res_data = response.body().getResult().getRes_data();
                    if (res_data!=null && res_data.size()>0){
                        for (int i = 0; i < res_data.size(); i++) {
                            int state_count = res_data.get(i).getState_count();
                            String state = res_data.get(i).getState();
                            switch (state){
                                case "qc_success":
                                    list.get(3).t.setNumber(state_count);
                                    break;
                                case "qc_fail":
                                    list.get(9).t.setNumber(state_count);
                                    break;
                            }
                        }
                        sectionAdapter.notifyDataSetChanged();
                    }
                }
            }

            @Override
            public void onFailure(Call<StateCountBean> call, Throwable t) {
                Log.e("zws", t.toString());
            }
        });
    }
    /**
     * 获取生产状态的数量
     * */
    private void initCount() {
        showDefultProgressDialog();
        HashMap<Object, Object> hashMap = new HashMap<>();

        if (process_id != -1000) {
            hashMap.put("process_id", process_id);
        }
        if (line_id != -1000){
            hashMap.put("production_line_id", line_id);
        }else {
            hashMap.put("production_line_id", false);
        }
        int partner_id = SharePreferenceUtils.getInt("partner_id", 1000, LineAndStateActivity.this);
        hashMap.put("partner_id", partner_id);
        String[] states = {"waiting_material", "already_picking","progress",
                "waiting_inventory_material",
        "waiting_warehouse_inspection", "already_picking","is_secondary_produce", "force_cancel_waiting_return",
                "force_cancel_waiting_warehouse_inspection", "rework_ing", "qc_success", "qc_fail"};
        hashMap.put("state", states);
        Call<StateCountBean> countMrpPro = inventoryApi.getCountMrpPro(hashMap);
        countMrpPro.enqueue(new Callback<StateCountBean>() {
            @Override
            public void onResponse(Call<StateCountBean> call, Response<StateCountBean> response) {
                dismissDefultProgressDialog();
                if (response.body() == null)return;
                if (response.body().getError()!=null){
                    new TipDialog(LineAndStateActivity.this, R.style.MyDialogStyle, response.body().getError().getData().getMessage())
                            .show();
                    return;
                }
                if (response.body().getResult().getRes_code()==1){
                    List<StateCountBean.ResultBean.ResDataBean> res_data = response.body().getResult().getRes_data();
                    if (res_data!=null && res_data.size()>0){
                        int sum = 0;
                        for (int i = 0; i < res_data.size(); i++) {
                            int state_count = res_data.get(i).getState_count();
                            String state = res_data.get(i).getState();
                            if (state.equals("waiting_material") || state.equals("prepare_material_ing")){
                                sum = state_count+sum;
                            }
                            switch (state){
                                case "waiting_inventory_material":
                                    list.get(7).t.setNumber(state_count);
                                    break;
                                case "waiting_warehouse_inspection":
                                    list.get(1).t.setNumber(state_count);
                                    break;
                                case "force_cancel_waiting_warehouse_inspection":
                                    list.get(2).t.setNumber(state_count);
                                    break;
                                case "qc_success":
                                    list.get(3).t.setNumber(state_count);
                                    break;
                                case "qc_fail":
                                    list.get(9).t.setNumber(state_count);
                                    break;
                                case "already_picking":
                                    list.get(4).t.setNumber(state_count);
                                    break;
                                case "progress":
                                    list.get(5).t.setNumber(state_count);
                                    break;
                                case "force_cancel_waiting_return":
                                    list.get(8).t.setNumber(state_count);
                                    break;
                                case "is_secondary_produce":
                                    list.get(6).t.setNumber(state_count);
                                    break;
                            }
                        }
                        list.get(0).t.setNumber(sum);
                        sectionAdapter.notifyDataSetChanged();
                    }
                }
            }

            @Override
            public void onFailure(Call<StateCountBean> call, Throwable t) {
                dismissDefultProgressDialog();
                Log.e("zws", t.toString());
            }
        });
    }


}
