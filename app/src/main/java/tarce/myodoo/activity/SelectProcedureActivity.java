package tarce.myodoo.activity;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.google.gson.Gson;
import com.google.gson.JsonObject;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.lang.reflect.Proxy;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import butterknife.ButterKnife;
import butterknife.InjectView;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import rx.Observable;
import rx.Scheduler;
import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Action1;
import rx.functions.Func1;
import rx.schedulers.Schedulers;
import tarce.api.MyCallback;
import tarce.api.RetrofitClient;
import tarce.api.api.InventoryApi;
import tarce.model.FindProductByConditionResponse;
import tarce.model.GetProcessBean;
import tarce.model.inventory.GetNumProcess;
import tarce.model.inventory.ProcessDeatilBean;
import tarce.model.inventory.ProcessShowBean;
import tarce.myodoo.R;
import tarce.myodoo.adapter.processproduct.ProcessListAdapter;
import tarce.support.AlertAialogUtils;
import tarce.support.ToolBarActivity;

/**
 * Created by rose.zou on 2017/5/18.
 * 生产工序页面
 */

public class SelectProcedureActivity extends ToolBarActivity {

    private final static String TAG = SelectProcedureActivity.class.getSimpleName();

    @InjectView(R.id.recyc_select_proce)
    RecyclerView recycSelectProce;
    private InventoryApi inventoryApi;
    private List<GetProcessBean.TestRSubBean.ListSubBean> listSubBeen;
    private List<String> process_name;
    private ProcessListAdapter processListAdapter;//生产工序的适配器
    private List<Integer> process_num;//生产工序的id数组
    private List<Integer> delay_num;//存放取出的工序数组，做key使用
    private ArrayList<ProcessShowBean> showBeanList;
    private HashMap<Object, Object> map;//存放生产工序id（value）和数目name（key）

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_select_proce);
        ButterKnife.inject(this);
        setTitle("生产工序");
        setRecyclerview(recycSelectProce);
        getProcessNAme();
    }


    /**
     * 获取生产工序
     * */
    private void getProcessNAme() {
        showDefultProgressDialog();
        listSubBeen = new ArrayList<>();
        process_name = new ArrayList<>();
        process_num = new ArrayList<>();
        inventoryApi = RetrofitClient.getInstance(SelectProcedureActivity.this).create(InventoryApi.class);
        Call<GetProcessBean> processBeanCall = inventoryApi.getProcess(new HashMap());
        processBeanCall.enqueue(new MyCallback<GetProcessBean>() {
            @Override
            public void onResponse(Call<GetProcessBean> call, Response<GetProcessBean> response) {
                if (response.body() == null)return;
                if (response.body().getResult().getRes_code() == 1){
                    listSubBeen = response.body().getResult().getRes_data();
                    for (int i = 0; i < listSubBeen.size(); i++) {
                        process_name.add(listSubBeen.get(i).getName());
                        process_num.add(listSubBeen.get(i).getProcess_id());
                    }
                    HashMap<Object,Object> hashMap = new HashMap<Object, Object>();
                    hashMap.put("process_ids",process_num);
                    //根据返回的int数组获取延误的数目
                    Call<GetNumProcess> numProcessCall = inventoryApi.getNumProcess(hashMap);
                    numProcessCall.enqueue(new MyCallback<GetNumProcess>() {
                        @Override
                        public void onResponse(Call<GetNumProcess> call, Response<GetNumProcess> response) {
                            dismissDefultProgressDialog();
                            if (response.body() == null)return;
                            Log.i(TAG, response.body().getJsonrpc()+"  "+response.body().getId());
                            if (response.body().getResult().getRes_code() == 1){
                                delay_num = new ArrayList<>();
                                try {
                                    GetNumProcess.ResultBean.ResDataBean res_data = response.body().getResult().getRes_data();
                                    String s = new Gson().toJson(res_data);
                                    Log.i(TAG,"转化 = "+s);
                                    JSONObject jsonObject1 = new JSONObject(s);
                                    Iterator<String> keys = jsonObject1.keys();
                                    while (keys.hasNext()){
                                      //  keys.next();
                                        delay_num.add(Integer.valueOf(keys.next()));
                                    }
                                    map = new HashMap<>();
                                    Log.i(TAG,"size:  "+delay_num.size());
                                    showBeanList = new ArrayList<ProcessShowBean>();
                                    for (int i = 0; i < delay_num.size(); i++) {
                                        JSONArray jsonArray = jsonObject1.getJSONArray(String.valueOf(delay_num.get(i)));
                                        if (jsonArray.length()>0){
                                            JSONObject jsonObject = jsonArray.getJSONObject(0);
                                            map.put(delay_num.get(i),jsonObject.getInt("count"));
                                        }else {
                                            map.put(delay_num.get(i),0);
                                        }
                                    }
                                    for (int i = 0; i < delay_num.size(); i++) {
                                    ProcessShowBean showBean = new ProcessShowBean(process_name.get(i), (Integer) map.get(process_num.get(i)));
                                    showBeanList.add(showBean);
                                    }
                                } catch (JSONException e) {
                                    e.printStackTrace();
                                }
                                //显示recycler
                                processListAdapter = new ProcessListAdapter(R.layout.adapter_process_list, showBeanList);
                                recycSelectProce.setAdapter(processListAdapter);
                                initListener();
                            }
                        }

                        @Override
                        public void onFailure(Call<GetNumProcess> call, Throwable t) {
                            super.onFailure(call, t);
                            AlertAialogUtils.dismissDefultProgressDialog();
                        }
                    });

                }
            }

            @Override
            public void onFailure(Call<GetProcessBean> call, Throwable t) {
                AlertAialogUtils.dismissDefultProgressDialog();
                Log.i(TAG,t.toString());
            }
        });
    }
/**
 * item监听事件
 * */
    private void initListener() {
        processListAdapter.setOnItemClickListener(new BaseQuickAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(BaseQuickAdapter adapter, View view, int position) {
                //跳转至工序详情界面
                Intent intent = new Intent(SelectProcedureActivity.this, ShowProcessActivity.class);
                intent.putExtra("delay_num",process_num.get(position));
                intent.putExtra("process_name", showBeanList.get(position).getProcess_name());
                startActivity(intent);
            }
        });
    }
}
