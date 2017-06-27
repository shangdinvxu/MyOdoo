package tarce.myodoo.activity.scancode;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.uuzuche.lib_zxing.activity.CaptureActivity;
import com.uuzuche.lib_zxing.activity.CodeUtils;

import java.io.Serializable;
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
import tarce.model.inventory.SalesOutListResponse;
import tarce.model.inventory.TakeDelListBean;
import tarce.myodoo.R;
import tarce.myodoo.activity.BaseActivity;
import tarce.myodoo.activity.MainActivity;
import tarce.myodoo.activity.salesout.SalesListActivity;
import tarce.myodoo.activity.salesout.SalesOutActivity;
import tarce.myodoo.activity.takedeliver.TakeDeliveListActivity;
import tarce.myodoo.activity.takedeliver.TakeDeliverActivity;
import tarce.support.MyLog;
import tarce.support.ToastUtils;

/**
 * Created by zouozu on 2017/6/27.
 */

public class ScanCodeActivity extends BaseActivity {
    private static final int SHOU_SCAN = 1;
    private static final int CHU_SCAN = 1;
    private static final int PRODUCT_SCAN = 1;
    @InjectView(R.id.tv_shou)
    TextView tvShou;
    @InjectView(R.id.chu)
    TextView chu;
    @InjectView(R.id.tv_product)
    TextView tvProduct;
    private InventoryApi inventoryApi;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_scancode);
        ButterKnife.inject(this);

        inventoryApi = RetrofitClient.getInstance(ScanCodeActivity.this).create(InventoryApi.class);
    }

    @OnClick(R.id.tv_shou)
    void shouScan(View view){
        Intent intent = new Intent(ScanCodeActivity.this, CaptureActivity.class);
        startActivityForResult(intent, SHOU_SCAN);
    }

    @OnClick(R.id.chu)
    void chuScan(View view){
        Intent intent = new Intent(ScanCodeActivity.this, CaptureActivity.class);
        startActivityForResult(intent, CHU_SCAN);
    }

    @OnClick(R.id.tv_product)
    void productScan(View view){
        Intent intent = new Intent(ScanCodeActivity.this, CaptureActivity.class);
        startActivityForResult(intent, PRODUCT_SCAN);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == SHOU_SCAN){
            if (data!=null){
                Bundle bundle = data.getExtras();
                if (bundle==null){
                    return;
                }
                if (bundle.getInt(CodeUtils.RESULT_TYPE) == CodeUtils.RESULT_SUCCESS){
                        searchOrder(bundle.getString(CodeUtils.RESULT_STRING));
                     //   searchOrder("PO2017042604163");
                }
            }
        }else if (requestCode == CHU_SCAN){

        }else if (requestCode == PRODUCT_SCAN){

        }
    }

    /**
     * 根据订单号搜索 searchByTakeNumber
     * */
    private void searchOrder(String query) {
        showDefultProgressDialog();
        HashMap<Object, Object> objectObjectHashMap = new HashMap<>();
        objectObjectHashMap.put("order_name", query);
        objectObjectHashMap.put("type", "incoming");
        Call<TakeDelListBean> getSaleListByNumberResponseCall = inventoryApi.searchByTakeNumber(objectObjectHashMap);
        showDefultProgressDialog();
        getSaleListByNumberResponseCall.enqueue(new Callback<TakeDelListBean>() {
            @Override
            public void onResponse(Call<TakeDelListBean> call, Response<TakeDelListBean> response) {
                dismissDefultProgressDialog();
                if (response.body() == null)return;
                if (response.body().getResult().getRes_code() == 1 && response.body().getResult().getRes_data()!=null){
                    List<TakeDelListBean.ResultBean.ResDataBean> res_data = response.body().getResult().getRes_data();
                    if (res_data != null && res_data.size() > 0) {
                        Intent intent = new Intent(ScanCodeActivity.this, TakeDeliveListActivity.class);
                        intent.putExtra("intent", (Serializable) res_data);
                        intent.putExtra("from","no");
                        intent.putExtra("notneed", "yes");
                        startActivity(intent);
                    }
                }
            }

            @Override
            public void onFailure(Call<TakeDelListBean> call, Throwable t) {
                dismissDefultProgressDialog();
                ToastUtils.showCommonToast(ScanCodeActivity.this, t.toString());
                MyLog.e("ScanCodeActivity", t.toString());
            }
        });
    }

    /**
     * 根据输入的订单号搜索订单
     * */
    private void initSearchSalesListFromNet(final String name) {
        showDefultProgressDialog();
        HashMap<Object, Object> objectObjectHashMap = new HashMap<>();
        objectObjectHashMap.put("order_name", name);
        objectObjectHashMap.put("type", "outgoing");
        Call<SalesOutListResponse> getSaleListByNumberResponseCall = inventoryApi.searchBySalesNumber(objectObjectHashMap);
        showDefultProgressDialog();
        getSaleListByNumberResponseCall.enqueue(new Callback<SalesOutListResponse>() {
            @Override
            public void onResponse(Call<SalesOutListResponse> call, Response<SalesOutListResponse> response) {
                dismissDefultProgressDialog();
                if (response.body() == null)return;
                if (response.body().getResult().getRes_code() == 1){
                    List<SalesOutListResponse.TResult.TRes_data> res_data = response.body().getResult().getRes_data();
                    if (res_data != null && res_data.size() > 0) {
                        Intent intent = new Intent(ScanCodeActivity.this, SalesListActivity.class);
                        intent.putExtra("intent", (Serializable) res_data);
                        intent.putExtra("deliver","yes");
                        startActivity(intent);
                    }
                }
            }

            @Override
            public void onFailure(Call<SalesOutListResponse> call, Throwable t) {
                dismissDefultProgressDialog();
                ToastUtils.showCommonToast(ScanCodeActivity.this, t.toString());
                MyLog.e("ScanCodeActivity", t.toString());
            }
        });
    }
}
