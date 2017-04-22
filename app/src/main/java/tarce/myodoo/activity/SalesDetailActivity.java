package tarce.myodoo.activity;

import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.ViewUtils;
import android.view.SurfaceHolder;
import android.view.SurfaceView;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.uuzuche.lib_zxing.activity.CaptureFragment;
import com.uuzuche.lib_zxing.activity.CodeUtils;
import com.uuzuche.lib_zxing.decoding.CaptureActivityHandler;

import java.util.List;

import butterknife.ButterKnife;
import butterknife.InjectView;
import butterknife.OnClick;
import tarce.model.GetSaleResponse;
import tarce.myodoo.R;
import tarce.myodoo.adapter.SalesDetailAdapter;
import tarce.myodoo.utils.StringUtils;
import tarce.support.AlertAialogUtils;
import tarce.support.ToolBarActivity;

/**
 * Created by Daniel.Xu on 2017/4/21.
 */

public class SalesDetailActivity extends ToolBarActivity {
    @InjectView(R.id.partner)
    TextView partner;
    @InjectView(R.id.time)
    TextView time;
    @InjectView(R.id.states)
    TextView states;
    @InjectView(R.id.origin_documents)
    TextView originDocuments;
    @InjectView(R.id.sales_out)
    TextView salesOut;
    @InjectView(R.id.recyclerview)
    RecyclerView recyclerview;
    @InjectView(R.id.framelayout)
    FrameLayout framelayout;
    @InjectView(R.id.camera_buttom_linearlayout)
    LinearLayout cameraButtomLinearlayout;
    @InjectView(R.id.top_imageview)
    ImageView topImageview;
    @InjectView(R.id.camera_Imageview)
    ImageView cameraImageview;
    private SalesDetailAdapter salesDetailAdapter;
    private boolean isShowCamera = true;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sales_detial);
        ButterKnife.inject(this);
        initIntent();
        initFragment();
    }

    private void initFragment() {
        FragmentManager supportFragmentManager = getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = supportFragmentManager.beginTransaction();
        final CaptureFragment captureFragment = new CaptureFragment();
        CodeUtils.setFragmentArgs(captureFragment, R.layout.my_camera);
        captureFragment.setAnalyzeCallback(new CodeUtils.AnalyzeCallback() {
            @Override
            public void onAnalyzeSuccess(Bitmap mBitmap, String result) {
                Toast.makeText(SalesDetailActivity.this, "扫描到的结果是" + result, Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onAnalyzeFailed() {
            }
        });
        fragmentTransaction.replace(R.id.framelayout, captureFragment);
        fragmentTransaction.commit();
    }


    @OnClick(R.id.top_imageview)
    void setCameraState(View view) {
        if (isShowCamera) {
            showCamera();
        } else {
            dismissCamera();
        }

    }

    private void dismissCamera() {
        cameraImageview.setVisibility(View.GONE);
        isShowCamera = true;
        tarce.support.ViewUtils.ViewRotate(topImageview,0);
    }

    private void showCamera() {
        isShowCamera = false;
        cameraImageview.setVisibility(View.VISIBLE);
        tarce.support.ViewUtils.ViewRotate(topImageview,180);
    }

    private void initIntent() {
        Intent intent = getIntent();
        Bundle bundle = intent.getBundleExtra("intent");
        GetSaleResponse.TResult.TRes_data bundle1 = (GetSaleResponse.TResult.TRes_data) bundle.getSerializable("bundle");
        partner.setText(bundle1.getParnter_id());
        time.setText(bundle1.getMin_date());
        states.setText(StringUtils.switchString(bundle1.getState()));
        originDocuments.setText(bundle1.getOrigin());
        salesOut.setText(bundle1.getDelivery_rule());
        List<GetSaleResponse.TResult.TRes_data.TPack_operation_product_ids> pack_operation_product_ids = bundle1.getPack_operation_product_ids();
        salesDetailAdapter = new SalesDetailAdapter(R.layout.salesout_detail_adapter_item, pack_operation_product_ids);
        setRecyclerview(recyclerview);
        recyclerview.setAdapter(salesDetailAdapter);
        initListener();
    }

    private void initListener() {
        salesDetailAdapter.setOnItemClickListener(new BaseQuickAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(BaseQuickAdapter adapter, View view, int position) {
                final GetSaleResponse.TResult.TRes_data.TPack_operation_product_ids obj =
                        (GetSaleResponse.TResult.TRes_data.TPack_operation_product_ids) adapter.getData().get(position);
                final EditText editText = new EditText(SalesDetailActivity.this);
                Integer qty_available = obj.getProduct_id().getQty_available();
                Integer product_qty = obj.getProduct_qty();
                Integer qty = qty_available >= product_qty ? qty_available : product_qty;
                editText.setText(qty + "");
                AlertDialog.Builder dialog = AlertAialogUtils.getCommonDialog(SalesDetailActivity.this, "输入" + obj.getProduct_id().getName() + "完成数量");
                dialog.setView(editText)
                        .setPositiveButton("确定", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                obj.setQty_done(Integer.parseInt(editText.getText().toString()));
                                salesDetailAdapter.notifyDataSetChanged();
                            }
                        }).show();
            }
        });
    }
}
