package tarce.myodoo.uiutil;

import android.app.Dialog;
import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.StyleRes;
import android.view.Display;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import butterknife.ButterKnife;
import butterknife.InjectView;
import butterknife.OnClick;
import tarce.model.inventory.OrderDetailBean;
import tarce.myodoo.R;
import tarce.myodoo.utils.StringUtils;

/**
 * Created by rose.zou on 2017/5/31.
 */

public class InsertNumDialog extends Dialog {

    @InjectView(R.id.name_product_dialog)
    TextView nameProductDialog;
    @InjectView(R.id.edit_out_num)
    EditText eidtOutNum;
    @InjectView(R.id.cancel_insert_dialog)
    TextView cancelInsertDialog;
    @InjectView(R.id.true_inset_dialog)
    TextView trueInsetDialog;
    @InjectView(R.id.tv_tip_dialog)
    TextView tvTipDialog;
    private LayoutInflater inflater;
    private Display display;
    private Context context;
    private View view;
    private OnSendCommonClickListener sendCommonClickListener;
    private String product_name;
    private OrderDetailBean.ResultBean.ResDataBean resDataBean;
    private int position;
    private double beiNum;
    private double return_qty = 0;

    public InsertNumDialog(@NonNull Context context) {
        super(context, R.style.MyDialogStyle);

        this.context = context;
        inflater = LayoutInflater.from(context);
        WindowManager windowManager = (WindowManager) context.getSystemService(Context.WINDOW_SERVICE);
        display = windowManager.getDefaultDisplay();
        initView();
    }

    public InsertNumDialog(@NonNull Context context, @StyleRes int themeResId, OnSendCommonClickListener sendCommonClickListener,
                           String product_name) {
        super(context, R.style.MyDialogStyle);

        this.product_name = product_name;
        this.sendCommonClickListener = sendCommonClickListener;
        this.context = context;
        inflater = LayoutInflater.from(context);
        WindowManager windowManager = (WindowManager) context.getSystemService(Context.WINDOW_SERVICE);
        display = windowManager.getDefaultDisplay();
        initView();
    }

    public InsertNumDialog(@NonNull Context context, @StyleRes int themeResId, OnSendCommonClickListener sendCommonClickListener,
                           String product_name, double return_qty) {
        super(context, R.style.MyDialogStyle);

        this.return_qty = return_qty;
        this.product_name = product_name;
        this.sendCommonClickListener = sendCommonClickListener;
        this.context = context;
        inflater = LayoutInflater.from(context);
        WindowManager windowManager = (WindowManager) context.getSystemService(Context.WINDOW_SERVICE);
        display = windowManager.getDefaultDisplay();
        initView();
    }

    public InsertNumDialog(@NonNull Context context, @StyleRes int themeResId, OnSendCommonClickListener sendCommonClickListener,
                           String product_name, int position, OrderDetailBean.ResultBean.ResDataBean resDataBean) {
        super(context, R.style.MyDialogStyle);

        this.product_name = product_name;
        this.position = position;
        this.resDataBean = resDataBean;
        this.sendCommonClickListener = sendCommonClickListener;
        this.context = context;
        inflater = LayoutInflater.from(context);
        WindowManager windowManager = (WindowManager) context.getSystemService(Context.WINDOW_SERVICE);
        display = windowManager.getDefaultDisplay();
        initView();
    }

    private void initView() {
        view = inflater.inflate(R.layout.dialog_insert_dialog, null);
        setContentView(view);
        ButterKnife.inject(this, view);

        Window window = getWindow();
        WindowManager.LayoutParams wl = window.getAttributes();
        wl.gravity = Gravity.CENTER;
        wl.width = (int) (display.getWidth() * 0.8);
        wl.height = WindowManager.LayoutParams.WRAP_CONTENT;
        window.setAttributes(wl);
        setCanceledOnTouchOutside(true);
        setCancelable(true);

        eidtOutNum.setFocusable(true);
        eidtOutNum.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_VISIBLE);
            }
        });

        nameProductDialog.setText("产品名称: " + product_name);
        /*if (resDataBean!=null){
            if (resDataBean.getState().equals("waiting_material")
                    || resDataBean.getState().equals("prepare_material_ing")
                    || resDataBean.getState().equals("finish_prepare_material")){
                beiNum = resDataBean.getStock_move_lines().get(position).getQuantity_ready() + resDataBean.getStock_move_lines().get(position).getQuantity_done();
            }else {
                beiNum = resDataBean.getStock_move_lines().get(position).getQuantity_done();
            }
            // TODO: 2017/6/8 生产num/需求num*item的需求num
            double v = resDataBean.getQty_produced() / resDataBean.getProduct_qty() * resDataBean.getStock_move_lines().get(position).getProduct_uom_qty();
            eidtOutNum.setText(StringUtils.doubleToString(beiNum-v));
        }*/
        if (return_qty!=0){
            eidtOutNum.setText(StringUtils.doubleToString(return_qty));
        }else {
            eidtOutNum.setText("0");
        }
        eidtOutNum.setSelection(eidtOutNum.getText().length());
    }

    public interface OnSendCommonClickListener {
        void OnSendCommonClick(int num);
    }

    public InsertNumDialog changeTitle(String title) {
        nameProductDialog.setText(title);
        return this;
    }

    public InsertNumDialog dismissTip() {
        tvTipDialog.setVisibility(View.GONE);
        return this;
    }


    @OnClick(R.id.cancel_insert_dialog)
    void cancelAction(View view) {
        dismiss();
    }

    @OnClick(R.id.true_inset_dialog)
    void trueAction(View view) {
        if (StringUtils.isNullOrEmpty(eidtOutNum.getText().toString())) {
            Toast.makeText(context, "请确认数量？", Toast.LENGTH_SHORT).show();
            return;
        }
        sendCommonClickListener.OnSendCommonClick(Integer.parseInt(eidtOutNum.getText().toString()));
        eidtOutNum.setText(null);
        dismiss();
    }
}
