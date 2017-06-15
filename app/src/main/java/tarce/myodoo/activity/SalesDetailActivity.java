package tarce.myodoo.activity;

import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.RecyclerView;
import android.text.InputType;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.uuzuche.lib_zxing.activity.CaptureFragment;
import com.uuzuche.lib_zxing.activity.CodeUtils;

import java.io.File;
import java.io.IOException;
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
import tarce.model.GetSaleResponse;
import tarce.myodoo.R;
import tarce.myodoo.adapter.SalesDetailAdapter;
import tarce.myodoo.utils.StringUtils;
import tarce.support.AlertAialogUtils;
import tarce.support.AvatarHelper;
import tarce.support.BitmapUtils;
import tarce.support.MyLog;
import tarce.support.ToastUtils;
import tarce.support.ToolBarActivity;
import tarce.support.ViewUtils;

/**
 * Created by Daniel.Xu on 2017/4/21.
 */

public class SalesDetailActivity extends ToolBarActivity {
    private static String TAG = SalesDetailActivity.class.getSimpleName() ;
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
    @InjectView(R.id.remarks)
    TextView remarks;
    @InjectView(R.id.buttom_button1)
    Button buttomButton1;
    @InjectView(R.id.buttom_button2)
    Button buttomButton2;
    @InjectView(R.id.buttom_button3)
    Button buttomButton3;
    @InjectView(R.id.buttom_button4)
    Button buttomButton4;
    private SalesDetailAdapter salesDetailAdapter;
    private boolean isShowCamera = true;
    private InventoryApi inventoryApi;
    private GetSaleResponse.TResult.TRes_data bundle1;
    // 修改头像的临时文件存放路径（头像修改成功后，会自动删除之）
    private String __tempImageFileLocation = null;
    /** 回调常量之：拍照 */
    private static final int TAKE_BIG_PICTURE = 991;
    /** 回调常量之：拍照后裁剪 */
    private static final int CROP_BIG_PICTURE = 993;
//	/** 回调常量之：从相册中选取 */
//	private static final int CHOOSE_BIG_PICTURE = 995;
    /** 回调常量之：从相册中选取2 */
    private static final int CHOOSE_BIG_PICTURE2 = 996;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sales_detial);
        ButterKnife.inject(this);
        inventoryApi = RetrofitClient.getInstance(SalesDetailActivity.this).create(InventoryApi.class);
        setRecyclerview(recyclerview);
        initIntent();
        initFragment();

    }

    private void initIntent() {
        Intent intent = getIntent();
        Bundle bundle = intent.getBundleExtra("intent");
        bundle1 = (GetSaleResponse.TResult.TRes_data) bundle.getSerializable("bundle");
        refreshView(bundle1) ;
//        initListener();
    }

    private void refreshView(GetSaleResponse.TResult.TRes_data bundle1){
        partner.setText(bundle1.getParnter_id());
        time.setText(bundle1.getMin_date());
        states.setText(StringUtils.switchString(bundle1.getState()));
        originDocuments.setText(bundle1.getOrigin());
        salesOut.setText(StringUtils.switchString(bundle1.getDelivery_rule()));
        List<GetSaleResponse.TResult.TRes_data.TPack_operation_product_ids> pack_operation_product_ids = bundle1.getPack_operation_product_ids();
        salesDetailAdapter = new SalesDetailAdapter(R.layout.salesout_detail_adapter_item, pack_operation_product_ids);
        recyclerview.setAdapter(salesDetailAdapter);
        refreshButtom(bundle1.getState());

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
        ViewUtils.ViewRotate(topImageview, 0);
    }

    private void showCamera() {
        isShowCamera = false;
        cameraImageview.setVisibility(View.VISIBLE);
        ViewUtils.ViewRotate(topImageview, 180);
    }



    private void initListener() {
        salesDetailAdapter.setOnItemClickListener(new BaseQuickAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(BaseQuickAdapter adapter, View view, int position) {
                final GetSaleResponse.TResult.TRes_data.TPack_operation_product_ids obj =
                        (GetSaleResponse.TResult.TRes_data.TPack_operation_product_ids) adapter.getData().get(position);
                final EditText editText = new EditText(SalesDetailActivity.this);
                final Integer qty_available = obj.getProduct_id().getQty_available();
                Integer product_qty = obj.getProduct_qty();
                Integer qty = qty_available >= product_qty ? product_qty : qty_available;
                editText.setText(qty + "");
                editText.setInputType(InputType.TYPE_CLASS_NUMBER);
                AlertDialog.Builder dialog = AlertAialogUtils.getCommonDialog(SalesDetailActivity.this, "输入" + obj.getProduct_id().getName() + "完成数量");
                dialog.setView(editText)
                        .setPositiveButton("确定", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                if (Integer.parseInt(editText.getText().toString())>qty_available){
                                    Toast.makeText(SalesDetailActivity.this,"库存不足",Toast.LENGTH_SHORT).show();
                                    return;
                                }
                                obj.setQty_done(Integer.parseInt(editText.getText().toString()));
                                salesDetailAdapter.notifyDataSetChanged();
                            }
                        }).show();
            }
        });
    }



    /**
     * 加载扫描的fragment
     */
    private void initFragment() {
        FragmentManager supportFragmentManager = getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = supportFragmentManager.beginTransaction();
        final CaptureFragment captureFragment = new CaptureFragment();
        CodeUtils.setFragmentArgs(captureFragment, R.layout.my_camera);
        captureFragment.setAnalyzeCallback(new CodeUtils.AnalyzeCallback() {
            @Override
            public void onAnalyzeSuccess(Bitmap mBitmap, String result) {
                HashMap<Object, Object> objectObjectHashMap = new HashMap<>();
                objectObjectHashMap.put("default_code", result);
                HashMap<Object, Object> objectObjectHashMap1 = new HashMap<>();
                objectObjectHashMap1.put("condition",objectObjectHashMap);
                Call<FindProductByConditionResponse> productByCondition = inventoryApi.findProductByCondition(objectObjectHashMap1);
                showDefultProgressDialog();
                productByCondition.enqueue(new Callback<FindProductByConditionResponse>() {
                    @Override
                    public void onResponse(Call<FindProductByConditionResponse> call, Response<FindProductByConditionResponse> response) {
                        dismissDefultProgressDialog();
                        if (response.body().getResult().getRes_code()==1){
                            String name = response.body().getResult().getRes_data().getProduct().getProduct_name();
                            List<GetSaleResponse.TResult.TRes_data.TPack_operation_product_ids> data = salesDetailAdapter.getData();
                            if (data==null)return;
                            boolean isInit = false ;
                            int index = -1 ;
                            for (int i = 0 ;i<data.size();i++){
                                if (data.get(i).getProduct_id().getName().equals(name)){
                                    isInit = true ;
                                    index = i ;
                                }
                            }
                            if (isInit){
                               final GetSaleResponse.TResult.TRes_data.TPack_operation_product_ids obj = data.get(index);
                                final EditText editText = new EditText(SalesDetailActivity.this);
                                final Integer qty_available = obj.getProduct_id().getQty_available();
                                Integer product_qty = obj.getProduct_qty();
                                Integer qty = qty_available >= product_qty ? product_qty : qty_available;
                                editText.setText(qty + "");
                                editText.setInputType(InputType.TYPE_CLASS_NUMBER);
                                AlertDialog.Builder dialog = AlertAialogUtils.getCommonDialog(SalesDetailActivity.this, "输入" + obj.getProduct_id().getName() + "完成数量");
                                dialog.setView(editText)
                                        .setPositiveButton("确定", new DialogInterface.OnClickListener() {
                                            @Override
                                            public void onClick(DialogInterface dialog, int which) {
                                                if (Integer.parseInt(editText.getText().toString())>qty_available){
                                                    Toast.makeText(SalesDetailActivity.this,"库存不足",Toast.LENGTH_SHORT).show();
                                                    return;
                                                }
                                                obj.setQty_done(Integer.parseInt(editText.getText().toString()));
                                                salesDetailAdapter.notifyDataSetChanged();
                                            }
                                        }).show();
                            }else {
                                ToastUtils.showCommonToast(SalesDetailActivity.this,
                                       "该产品不在单据中");
                            }


                        }else {
                            ToastUtils.showCommonToast(SalesDetailActivity.this,
                                    response.body().getResult().getRes_data().getError());
                        }
                    }

                    @Override
                    public void onFailure(Call<FindProductByConditionResponse> call, Throwable t) {
                        dismissDefultProgressDialog();

                    }
                });

            }

            @Override
            public void onAnalyzeFailed() {

            }
        });
        fragmentTransaction.replace(R.id.framelayout, captureFragment);
        fragmentTransaction.commit();
    }


    private void  refreshButtom(String s){
        switch (s) {
            case "assigned" :
                buttomButton1.setText("开始备货");
                buttomButton1.setVisibility(View.VISIBLE);
                buttomButton1.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        AlertAialogUtils.getCommonDialog(SalesDetailActivity.this, "确定开始备货？")
                                .setPositiveButton("确定", new DialogInterface.OnClickListener() {
                                    @Override
                                    public void onClick(DialogInterface dialog, int which) {
                                        //开始备货
                                        refreshButtom("备货完成");
                                        initListener();
                                        showCamera();
                                    }
                                }).show();
                    }
                });
                buttomButton2.setVisibility(View.GONE);
                buttomButton3.setVisibility(View.GONE);
                buttomButton4.setVisibility(View.GONE);
                break;
            case "confirmed" :
                buttomButton1.setVisibility(View.GONE);
                buttomButton2.setVisibility(View.GONE);
                buttomButton3.setVisibility(View.GONE);
                buttomButton4.setVisibility(View.GONE);
                break;
            case "done" :
                buttomButton1.setText("补拍物流信息");
                buttomButton1.setVisibility(View.VISIBLE);
                buttomButton1.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        AlertAialogUtils.getCommonDialog(SalesDetailActivity.this, "是否补拍物流信息？")
                                .setPositiveButton("确定", new DialogInterface.OnClickListener() {
                                    @Override
                                    public void onClick(DialogInterface dialog, int which) {


                                    }
                                }).show();
                    }
                });
                buttomButton2.setVisibility(View.GONE);
                buttomButton3.setVisibility(View.GONE);
                buttomButton4.setVisibility(View.GONE);
                break;
            case "partially_available":
                buttomButton1.setVisibility(View.GONE);
                buttomButton2.setVisibility(View.GONE);
                buttomButton3.setVisibility(View.GONE);
                buttomButton4.setVisibility(View.GONE);
                break;
            case "备货完成":
                buttomButton1.setVisibility(View.VISIBLE);
                buttomButton1.setText(s);
                buttomButton1.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        AlertAialogUtils.getCommonDialog(SalesDetailActivity.this, "是否确定备货完成？")
                                .setPositiveButton("确定", new DialogInterface.OnClickListener() {
                                    @Override
                                    public void onClick(DialogInterface dialog, int which) {
                                        List<GetSaleResponse.TResult.TRes_data.TPack_operation_product_ids> data = salesDetailAdapter.getData();
                                        HashMap<Object, Object> objectObjectHashMap = new HashMap<>();
                                        bundle1.setPack_operation_product_ids(data);
                                        objectObjectHashMap.put("state", "process");
                                        objectObjectHashMap.put("picking_id", bundle1.getPicking_id());
                                        objectObjectHashMap.put("pack_operation_product_ids", data);
                                        objectObjectHashMap.put("qc_img", bundle1.getQc_img());
                                        objectObjectHashMap.put("qc_note", "yes");
                                        Call<GetSaleResponse> getSaleResponseCall = inventoryApi.changeStockPicking(objectObjectHashMap);
                                        showDefultProgressDialog();
                                        getSaleResponseCall.enqueue(new Callback<GetSaleResponse>() {
                                            @Override
                                            public void onResponse(Call<GetSaleResponse> call, Response<GetSaleResponse> response) {
                                                dismissDefultProgressDialog();
                                                if (response.body().getResult().getRes_code() == 1) {
                                                    bundle1 = response.body().getResult().getRes_data();
                                                    refreshView(bundle1);
                                                    refreshButtom("补拍物流信息");
                                                }else {
                                                    ToastUtils.showCommonToast(SalesDetailActivity.this,response.body().getResult().getRes_data().getError()+"");
                                                }
                                            }

                                            @Override
                                            public void onFailure(Call<GetSaleResponse> call, Throwable t) {
                                                dismissDefultProgressDialog();
                                                MyLog.e("", t.toString());
                                            }
                                        });
                                    }
                                }).show();
                    }
                });
                buttomButton2.setVisibility(View.GONE);
                buttomButton3.setVisibility(View.GONE);
                buttomButton4.setVisibility(View.GONE);
                break;
            case "补拍物流信息":
                buttomButton1.setText("补拍物流信息");
                buttomButton1.setVisibility(View.VISIBLE);
                buttomButton1.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        AlertDialog.Builder dialog = AlertAialogUtils.getCommonDialog(SalesDetailActivity.this, "是否要补拍物流信息？");
                        dialog.setPositiveButton("确定", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                //进入拍照
                                Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);//action is capture
                                intent.putExtra(MediaStore.EXTRA_OUTPUT, getTempImageFileUri());
                                startActivityForResult(intent, TAKE_BIG_PICTURE);
                            }
                        }).show();

                    }
                });
                buttomButton2.setVisibility(View.GONE);
                buttomButton3.setVisibility(View.GONE);
                buttomButton4.setVisibility(View.GONE);
                break;
            }
        }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        final Uri imagePhotoUri = getTempImageFileUri();
        switch (requestCode) {
            case TAKE_BIG_PICTURE:// 拍照完成则新拍的文件将会存放于指定的位置（即uri、tempImaheLocation所表示的地方）
            {
                if (resultCode == RESULT_OK) {
                    //从相机拍摄保存的Uri中取出图片，调用系统剪裁工具
                    if (imagePhotoUri != null) {
                        String bitmapString  = null ;
                        try {
                            Bitmap bitmapFormUri = BitmapUtils.getBitmapFormUri(SalesDetailActivity.this, imagePhotoUri);
                             bitmapString = BitmapUtils.bitmapToBase64(bitmapFormUri);
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                        if (bitmapString!=null){
                            HashMap<Object, Object> objectObjectHashMap = new HashMap<>();
                            objectObjectHashMap.put("state","process");
                            objectObjectHashMap.put("picking_id",bundle1.getPicking_id());
                            objectObjectHashMap.put("pack_operation_product_ids",bundle1.getPack_operation_product_ids());
                            objectObjectHashMap.put("qc_note",bundle1.getQc_note());
                            objectObjectHashMap.put("qc_img",bitmapString);
                            Call<GetSaleResponse> getSaleResponseCall = inventoryApi.changeStockPicking(objectObjectHashMap);
                            showDefultProgressDialog();
                            getSaleResponseCall.enqueue(new Callback<GetSaleResponse>() {
                                @Override
                                public void onResponse(Call<GetSaleResponse> call, Response<GetSaleResponse> response) {
                                    dismissDefultProgressDialog();

                                }

                                @Override
                                public void onFailure(Call<GetSaleResponse> call, Throwable t) {
                                    dismissDefultProgressDialog();

                                }
                            });
                        }

                    }
                }
                break;
            }
        }
    }

    /**
     * 获得临时文件存放地址的Uri(此地址存在与否并不代表该文件一定存在哦).
     *
     * @return 正常获得uri则返回，否则返回null
     */
    private Uri getTempImageFileUri()
    {
        String tempImageFileLocation = getTempImageFileLocation();
        if(tempImageFileLocation != null)
        {
            return Uri.parse("file://" + tempImageFileLocation);
        }
        return null;
    }
    /**
     * 获得临时文件存放地址(此地址存在与否并不代表该文件一定存在哦).
     *
     * @return 正常获得则返回，否则返回null
     */
    private String getTempImageFileLocation()
    {
        try
        {
            if(__tempImageFileLocation == null)
            {
                String avatarTempDirStr = AvatarHelper.getUserAvatarSavedDir(SalesDetailActivity.this);
                File avatarTempDir = new File(avatarTempDirStr);
                if(avatarTempDir != null)
                {
                    // 目录不存在则新建之
                    if(!avatarTempDir.exists())
                        avatarTempDir.mkdirs();
                    // 临时文件名
                    __tempImageFileLocation = avatarTempDir.getAbsolutePath()+"/"+"local_avatar_temp.jpg";
                }
            }
        }
        catch (Exception e)
        {
            Log.e(TAG, "【ChangeAvatar】读取本地用户的头像临时存储路径时出错了，" + e.getMessage(), e);
        }

        Log.d(TAG, "【ChangeAvatar】正在获取本地用户的头像临时存储路径：" + __tempImageFileLocation);

        return __tempImageFileLocation;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // TODO Auto-generated method stub
        if(item.getItemId() == android.R.id.home)
        {
            finish();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }
}
