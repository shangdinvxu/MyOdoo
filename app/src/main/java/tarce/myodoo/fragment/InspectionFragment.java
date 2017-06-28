package tarce.myodoo.fragment;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.newland.me.ConnUtils;
import com.newland.me.DeviceManager;
import com.newland.mtype.ConnectionCloseEvent;
import com.newland.mtype.Module;
import com.newland.mtype.ModuleType;
import com.newland.mtype.event.DeviceEventListener;
import com.newland.mtype.module.common.printer.Printer;
import com.newland.mtype.module.common.rfcard.RFCardModule;
import com.newland.mtype.module.common.rfcard.RFCardType;
import com.newland.mtype.module.common.rfcard.RFResult;
import com.newland.mtype.util.ISOUtils;
import com.newland.mtypex.nseries.NSConnV100ConnParams;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.concurrent.TimeUnit;

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
import tarce.myodoo.activity.NFCInsetActivity;
import tarce.myodoo.activity.inspect.InspectionSubActivity;
import tarce.myodoo.activity.salesout.SalesDetailActivity;
import tarce.myodoo.activity.takedeliver.TakeDeliveListActivity;
import tarce.myodoo.adapter.SectionAdapter;
import tarce.myodoo.bean.MainItemBean;
import tarce.myodoo.bean.MenuBean;
import tarce.myodoo.device.Const;
import tarce.myodoo.device.N900Device;
import tarce.support.MyLog;
import tarce.support.ToastUtils;

/**
 * 品检界面
 * Created by Daniel.Xu on 2017/4/20.
 */

public class InspectionFragment extends Fragment {
    private static final String K21_DRIVER_NAME = "com.newland.me.K21Driver";
    public List<MainItemBean> list;
    public SectionAdapter sectionAdapter;
    @InjectView(R.id.recyclerview)
    RecyclerView recyclerview;
    private InventoryApi inventoryApi;
    private LoadInspectionBean.ResultBean.ResDataBean res_data;
    private DeviceManager deviceManager;
    private RFCardModule rfCardModule;
    private Dialog nccard_dialog;

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
        list.add(new MainItemBean(new MenuBean("录入NFC", 0)));
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
                    case "录入NFC":
                        Intent intent3 = new Intent(getActivity(), NFCInsetActivity.class);
                        startActivity(intent3);
                        /*getActivity().runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                final AlertDialog.Builder builder = new android.app.AlertDialog.Builder(getActivity());
                                builder.setTitle("非接卡类型:");
                                final boolean[] arrayWorkingKeySelected = new boolean[] { true, true, true };
                                builder.setMultiChoiceItems(new String[] { "A卡", "B卡", "M1卡" }, arrayWorkingKeySelected,
                                        new DialogInterface.OnMultiChoiceClickListener() {
                                            @Override
                                            public void onClick(DialogInterface dialog, int which, boolean isChecked) {
                                                arrayWorkingKeySelected[which] = isChecked;

                                            }});
                                builder.setPositiveButton("确定", new DialogInterface.OnClickListener() {
                                    @Override
                                    public void onClick(DialogInterface arg0, int arg1) {
                                        new Thread(new Runnable() {
                                            @Override
                                            public void run() {
                                                try {
                                                    initDevice();
                                                   // ToastUtils.showCommonToast(getActivity(), "请贴非接卡！");
                                                    Log.i("zouwansheng", "请贴非接卡！");
                                                    try {
                                                        List<RFCardType> cardTypeList=new ArrayList<RFCardType>();
                                                        if (arrayWorkingKeySelected[0]) {
                                                            cardTypeList.add(RFCardType.ACARD);
                                                        }
                                                        if (arrayWorkingKeySelected[1]) {
                                                            cardTypeList.add(RFCardType.BCARD);

                                                        }
                                                        if(arrayWorkingKeySelected[2]){
                                                            cardTypeList.add(RFCardType.M1CARD);
                                                        }
                                                        rfCardModule = (RFCardModule) deviceManager.getDevice().getStandardModule(ModuleType.COMMON_RFCARDREADER);
                                                        RFResult qPResult = rfCardModule.powerOn(cardTypeList.toArray(new RFCardType[cardTypeList.size()]),8, TimeUnit.SECONDS);
//										        RFResult qPResult = rfCardModule.powerOn(RFCardType.ACARD, 8);		//卡类型可以为null,就包括了所以非接卡类型

                                                        if (qPResult.getCardSerialNo() == null) {
                                                          //  ToastUtils.showCommonToast(getActivity(), "非接卡序列号null: " + "\r\n"+ Const.MessageTag.DATA);
                                                            Log.i("zouwansheng", "非接卡序列号null: " + "\r\n"+ Const.MessageTag.DATA);
                                                        } else {
                                                          //  ToastUtils.showCommonToast(getActivity(), "非接卡序列号:" + "\r\n"+ ISOUtils.hexString(qPResult.getCardSerialNo()));
                                                            Log.i("zouwansheng", "非接卡序列号:" + "\r\n"+ ISOUtils.hexString(qPResult.getCardSerialNo()));
                                                        }
                                                        // mainActivity.showMessage("寻卡上电完成" + "\r\n", MessageTag.NORMAL);
                                                    }catch(Exception e){e.fillInStackTrace();
                                                       // ToastUtils.showCommonToast(getActivity(), "非接卡寻卡上电异常:" + e.getMessage() + "\r\n"+Const.MessageTag.ERROR);
                                                        Log.i("zouwansheng",e.getMessage() + "\r\n"+Const.MessageTag.ERROR );
                                                    }
                                                } catch (Exception e) {
                                                  //  ToastUtils.showCommonToast(getActivity(), "非接卡寻卡上电异常:" + e.getMessage() + "\r\n"+Const.MessageTag.ERROR);
                                                    Log.i("zouwansheng",e.getMessage() + "\r\n"+Const.MessageTag.ERROR );
                                                }
                                            }
                                        }).start();
                                    }
                                });
                                builder.setNegativeButton("取消", new DialogInterface.OnClickListener() {

                                    @Override
                                    public void onClick(DialogInterface arg0, int arg1) {
                                    }
                                });
                                nccard_dialog = builder.create();
                                nccard_dialog.show();
                                nccard_dialog.setCancelable(false);
                                nccard_dialog.setCanceledOnTouchOutside(false);
                            }
                        });*/
                        break;
                }
            }
        });
    }
    /**
     * 连接设备打印机
     */
    private void initDevice() {
        deviceManager = ConnUtils.getDeviceManager();
        try {
            deviceManager.init(getActivity(), K21_DRIVER_NAME, new NSConnV100ConnParams(), new DeviceEventListener<ConnectionCloseEvent>() {
                @Override
                public void onEvent(ConnectionCloseEvent connectionCloseEvent, Handler handler) {
                    if (connectionCloseEvent.isSuccess()) {
                        ToastUtils.showCommonToast(getActivity(), "设备被客户主动断开！");
                    }
                    if (connectionCloseEvent.isFailed()) {
                        ToastUtils.showCommonToast(getActivity(), "设备链接异常断开！");
                    }
                }

                @Override
                public Handler getUIHandler() {
                    return null;
                }
            });
            deviceManager.connect();
            MyLog.e("ProductingActivity", "连接成功");
        } catch (Exception e) {
            e.printStackTrace();
            ToastUtils.showCommonToast(getActivity(), "链接异常,请检查设备或重新连接.." + e);
        }
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
                    if (response.body().getResult().getRes_data()!=null && response.body().getResult().getRes_code() == 1){
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
                ToastUtils.showCommonToast(getActivity(), t.toString());
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
