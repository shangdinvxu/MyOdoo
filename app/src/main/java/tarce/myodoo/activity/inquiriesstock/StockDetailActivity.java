package tarce.myodoo.activity.inquiriesstock;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.text.InputType;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.newland.mtype.ModuleType;
import com.newland.mtype.module.common.printer.Printer;
import com.uuzuche.lib_zxing.activity.CodeUtils;

import java.util.HashMap;
import java.util.concurrent.TimeUnit;

import butterknife.ButterKnife;
import butterknife.InjectView;
import butterknife.OnClick;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import tarce.api.RetrofitClient;
import tarce.api.api.InventoryApi;
import tarce.model.inventory.ChangeWeightBean;
import tarce.model.inventory.StockListBean;
import tarce.myodoo.R;
import tarce.myodoo.activity.BaseActivity;
import tarce.myodoo.activity.outsourcing.OutSourceListActivity;
import tarce.myodoo.activity.salesout.SalesDetailActivity;
import tarce.myodoo.uiutil.TipDialog;
import tarce.myodoo.utils.DateTool;
import tarce.myodoo.utils.StringUtils;
import tarce.support.AlertAialogUtils;
import tarce.support.ToastUtils;
import tarce.support.ViewUtils;

/**
 * Created by zouzou on 2017/7/5.
 */

public class StockDetailActivity extends BaseActivity {
    @InjectView(R.id.image_stockdetail)
    ImageView imageStockdetail;
    @InjectView(R.id.tv_product_type)
    TextView tvProductType;
    @InjectView(R.id.tv_consult)
    TextView tvConsult;
    @InjectView(R.id.tv_product_name)
    TextView tvProductName;
    @InjectView(R.id.tv_num)
    TextView tvNum;
    @InjectView(R.id.tv_area)
    TextView tvArea;
    @InjectView(R.id.tv_shortform)
    TextView tvShortform;
    @InjectView(R.id.tv_type_in)
    TextView tvTypeIn;
    @InjectView(R.id.tv_guige)
    TextView tvGuige;
    @InjectView(R.id.tv_in_channel)
    TextView tvInChannel;
    @InjectView(R.id.tv_look_move)
    TextView tvLookMove;
    @InjectView(R.id.tv_print)
    TextView tvPrint;
    @InjectView(R.id.tv_weight)
    TextView tvWeight;
    @InjectView(R.id.btn_change_weight)
    Button btnChangeWeight;
    private StockListBean.ResultBean.ResDataBean resDataBean;
    private Printer printer;
    private InventoryApi inventoryApi;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_stockdetail);
        ButterKnife.inject(this);

        inventoryApi = RetrofitClient.getInstance(StockDetailActivity.this).create(InventoryApi.class);
        Intent intent = getIntent();
        resDataBean = (StockListBean.ResultBean.ResDataBean) intent.getSerializableExtra("bean");
        if (resDataBean != null) {
            initView();
        }
    }

    //初始化试图
    private void initView() {
        if (!StringUtils.isNullOrEmpty(resDataBean.getImage_medium())) {
            Glide.with(StockDetailActivity.this).load(resDataBean.getImage_medium()).into(imageStockdetail);
        }
        switch (resDataBean.getType()) {
            case "product":
                tvProductType.setText("可库存产品");
                break;
            case "consu":
                tvProductType.setText("可消耗");
                break;
            case "service":
                tvProductType.setText("服务");
                break;
        }
        tvConsult.setText(resDataBean.getDefault_code());
        tvProductName.setText(resDataBean.getProduct_name());
        tvNum.setText(resDataBean.getQty_available() + "/" + resDataBean.getVirtual_available());
        tvArea.setText(String.valueOf(resDataBean.getArea_id().getArea_name()));
        tvShortform.setText(String.valueOf(resDataBean.getInner_code()));
        tvTypeIn.setText(String.valueOf(resDataBean.getInner_spec()));
        tvGuige.setText(String.valueOf(resDataBean.getProduct_spec()));
        tvInChannel.setText(resDataBean.getCateg_id());
        tvWeight.setText(StringUtils.fourDouble(resDataBean.getWeight()));
    }

    @OnClick(R.id.tv_look_move)
    void moveLin(View view) {
        Intent intent = new Intent(StockDetailActivity.this, StockMoveListActivity.class);
        intent.putExtra("product_id", resDataBean.getProduct_id());
        intent.putExtra("name", "[" + resDataBean.getDefault_code() + "] " + resDataBean.getProduct_name());
        startActivity(intent);
    }

    @OnClick(R.id.tv_print)
    void setTvPrint(View view) {
        initDevice();
        printer = (Printer) deviceManager.getDevice().getStandardModule(ModuleType.COMMON_PRINTER);
        printer.init();
        printer.setLineSpace(1);
        printer.print("料号: " + resDataBean.getDefault_code() + "\n品名: " + resDataBean.getProduct_name()
                + "\n位置: " + resDataBean.getArea_id().getArea_name() + "\n", 30, TimeUnit.SECONDS);
        Bitmap bitmap = CodeUtils.createImage(resDataBean.getDefault_code(), 150, 150, null);
        printer.print(0, bitmap, 30, TimeUnit.SECONDS);
        printer.print("\n", 30, TimeUnit.SECONDS);
        Bitmap mBitmap = BitmapFactory.decodeResource(getResources(), R.drawable.print_img);
        printer.print(0, mBitmap, 30, TimeUnit.SECONDS);
        printer.print("\n" + "打印时间：" + DateTool.getDateTime(), 30, TimeUnit.SECONDS);
        printer.print("\n\n\n\n\n\n\n", 30, TimeUnit.SECONDS);
    }

    @OnClick(R.id.btn_change_weight)
    void setBtnChangeWeight(View view) {
        final EditText editText = new EditText(StockDetailActivity.this);
        editText.setText(resDataBean.getWeight() + "");
        editText.setSelection(editText.getText().length());
        AlertDialog.Builder dialog = AlertAialogUtils.getCommonDialog(StockDetailActivity.this, "输入" + resDataBean.getProduct_name() + "的单位重量");
        dialog.setView(editText)
                .setPositiveButton("确定", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        double weight = Double.parseDouble(editText.getText().toString());
                        tvWeight.setText(StringUtils.fourDouble(weight) + "");
                        showDefultProgressDialog();
                        HashMap<Object, Object> hashMap = new HashMap<>();
                        hashMap.put("product_id", resDataBean.getProduct_id());
                        hashMap.put("weight", weight);
                        Call<ChangeWeightBean> objectCall = inventoryApi.changeProductWeight(hashMap);
                        objectCall.enqueue(new Callback<ChangeWeightBean>() {
                            @Override
                            public void onResponse(Call<ChangeWeightBean> call, Response<ChangeWeightBean> response) {
                                dismissDefultProgressDialog();
                                if (response.body() == null) return;
                                if (response.body().getError()!=null){
                                    new TipDialog(StockDetailActivity.this, R.style.MyDialogStyle, response.body().getError().getData().getMessage())
                                            .show();
                                    return;
                                }
                                if (response.body().getResult().getRes_data()!=null && response.body().getResult().getRes_code()==1){
                                    new TipDialog(StockDetailActivity.this, R.style.MyDialogStyle, "成功修改重量!")
                                            .show();
                                }
                            }

                            @Override
                            public void onFailure(Call<ChangeWeightBean> call, Throwable t) {
                                dismissDefultProgressDialog();
                            }
                        });
                    }
                }).show();
    }
}
