package tarce.myodoo.activity.incentroy;

import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.animation.GlideAnimation;
import com.bumptech.glide.request.target.SimpleTarget;
import com.bumptech.glide.request.target.Target;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.uuzuche.lib_zxing.activity.CaptureActivity;
import com.uuzuche.lib_zxing.activity.CodeUtils;

import java.io.Serializable;
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
import tarce.model.FindProductByConditionResponse;
import tarce.model.inventory.CommonBean;
import tarce.model.inventory.DiyListBean;
import tarce.myodoo.R;
import tarce.myodoo.activity.BaseActivity;
import tarce.myodoo.adapter.AddCuAdapter;
import tarce.myodoo.utils.StringUtils;
import tarce.support.BitmapUtils;
import tarce.support.ToastUtils;

/**
 * Created by zouwansheng on 2017/8/29.
 */

public class AddCuActivity extends BaseActivity {
    private static final int REQUESTCODE_ADDCU = 1;
    public static final int SETRESULT = 999;
    @InjectView(R.id.scan_tv)
    TextView scanTv;
    @InjectView(R.id.edit_write_name)
    EditText editWriteName;
    @InjectView(R.id.btn_commit)
    Button btnCommit;
    @InjectView(R.id.clear_all)
    Button clearAll;
    @InjectView(R.id.recycler_scan)
    RecyclerView recyclerScan;
    private InventoryApi inventoryApi;
    private List<DiyListBean> listBeen = new ArrayList<>();
    private AddCuAdapter addCuAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_addcu);
        ButterKnife.inject(this);

        inventoryApi = RetrofitClient.getInstance(AddCuActivity.this).create(InventoryApi.class);
        setRecyclerview(recyclerScan);
    }

    @OnClick(R.id.scan_tv)
    void setScanTv(View view) {
        Intent intent = new Intent(AddCuActivity.this, SaveChangeActivity.class);
        intent.putExtra("scan", "yes");
        startActivityForResult(intent, REQUESTCODE_ADDCU);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (resultCode == SETRESULT) {
            if (requestCode == REQUESTCODE_ADDCU) {
                if (data == null) return;
                DiyListBean bean = (DiyListBean) data.getSerializableExtra("bean");
                listBeen.add(bean);
                if (listBeen.size() == 1) {
                    addCuAdapter = new AddCuAdapter(R.layout.adapter_detaildeleive, listBeen);
                    recyclerScan.setAdapter(addCuAdapter);
                } else {
                    addCuAdapter.notifyDataSetChanged();
                }
                initListener();
            }
        }
    }

    //点击事件
    private void initListener() {
        addCuAdapter.setOnItemClickListener(new BaseQuickAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(BaseQuickAdapter adapter, View view, int position) {
                Intent intent = new Intent(AddCuActivity.this, SaveChangeActivity.class);
                intent.putExtra("scan", "no");
                intent.putExtra("bean", listBeen.get(position));
                startActivity(intent);
            }
        });
    }

    //提交库存
    @OnClick(R.id.btn_commit)
    void commitKucun() {
        if (StringUtils.isNullOrEmpty(editWriteName.getText().toString())) {
            ToastUtils.showCommonToast(AddCuActivity.this, "请输入名字");
            return;
        }
        showDefultProgressDialog();
        HashMap<Object, Object> hashMap = new HashMap<>();
        hashMap.put("name", editWriteName.getText().toString());
        for (int i = 0; i < listBeen.size(); i++) {
            final int finalI = i;
            Glide.with(AddCuActivity.this)
                    .load(listBeen.get(i).getProduct().getImage_medium())
                    .asBitmap()
                    .into(new SimpleTarget<Bitmap>(Target.SIZE_ORIGINAL, Target.SIZE_ORIGINAL) {
                        @Override
                        public void onResourceReady(Bitmap bitmap, GlideAnimation glideAnimation) {
                            //得到bitmap
                            String s = BitmapUtils.bitmapToBase64(bitmap);
                          //  Log.e("zws", "?????"+s);
                            listBeen.get(finalI).getProduct().setImage_medium(BitmapUtils.bitmapToBase64(bitmap));
                        }
                    });
        }
        hashMap.put("line_ids", listBeen);
        Call<CommonBean> stockInventroy = inventoryApi.createStockInventroy(hashMap);
        stockInventroy.enqueue(new Callback<CommonBean>() {
            @Override
            public void onResponse(Call<CommonBean> call, Response<CommonBean> response) {
                dismissDefultProgressDialog();
                if (response.body() == null ||response.body().getResult() == null) return;
                if (response.body().getResult().getRes_code() == 1) {
                    ToastUtils.showCommonToast(AddCuActivity.this, "提交成功");
                    finish();
                } else if (response.body().getResult().getRes_code() == -1) {
                    ToastUtils.showCommonToast(AddCuActivity.this, response.body().getResult().getRes_data().getError());
                }
            }

            @Override
            public void onFailure(Call<CommonBean> call, Throwable t) {
                dismissDefultProgressDialog();
            }
        });
    }

    //清除全部
    @OnClick(R.id.clear_all)
    void setClearAll(View view){
        listBeen.clear();
        addCuAdapter.notifyDataSetChanged();
    }
}
