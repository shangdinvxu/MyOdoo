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
 * Created by rose.zou on 2017/5/24.
 * 领料订单详情里面所需dialog
 */

public class DialogForOrder extends Dialog {
    private OrderDetailBean.ResultBean.ResDataBean.StockMoveLinesBean linesBean;
    @InjectView(R.id.dialog_name_product)
    TextView dialogNameProduct;
    @InjectView(R.id.tv_kucun_num)
    TextView tvKucunNum;
    @InjectView(R.id.dialog_need_num)
    TextView dialogNeedNum;
    @InjectView(R.id.dialog_advice_num)
    TextView dialogAdviceNum;
    @InjectView(R.id.tv_prepare_num)
    EditText tvPrepareNum;
    @InjectView(R.id.tv_cancel_dialog)
    TextView tvCancelDialog;
    @InjectView(R.id.tv_true_dialog)
    TextView tvTrueDialog;
    private LayoutInflater inflater;
    private Display display;
    private Context context;
    private View view;
    private OnSendCommonClickListener sendCommonClickListener;

    public DialogForOrder(@NonNull Context context) {
        super(context, R.style.MyDialogStyle);

        this.context = context;
        inflater = LayoutInflater.from(context);
        WindowManager windowManager = (WindowManager) context.getSystemService(Context.WINDOW_SERVICE);
        display = windowManager.getDefaultDisplay();
        initView();
    }

    public DialogForOrder(@NonNull Context context, OnSendCommonClickListener sendCommonClickListener, OrderDetailBean.ResultBean.ResDataBean.StockMoveLinesBean linesBean) {
        super(context, R.style.MyDialogStyle);

        this.sendCommonClickListener = sendCommonClickListener;
        this.context = context;
        this.linesBean = linesBean;
        inflater = LayoutInflater.from(context);
        WindowManager windowManager = (WindowManager) context.getSystemService(Context.WINDOW_SERVICE);
        display = windowManager.getDefaultDisplay();
        initView();
    }

    private void initView() {
        view = inflater.inflate(R.layout.dialog_product_edit, null);
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

        tvPrepareNum.setFocusable(true);
        tvPrepareNum.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_VISIBLE);
            }
        });
        dialogNameProduct.setText(linesBean.getProduct_id());
        tvKucunNum.setText(StringUtils.doubleToString(linesBean.getQty_available()));
        dialogNeedNum.setText(StringUtils.doubleToString(linesBean.getProduct_uom_qty()));
        dialogAdviceNum.setText(StringUtils.doubleToString(linesBean.getSuggest_qty()));
        tvPrepareNum.setText(StringUtils.doubleToString(linesBean.getQuantity_ready()));
    }

    public interface OnSendCommonClickListener {
        void OnSendCommonClick(int num);
    }


    @OnClick(R.id.tv_cancel_dialog)
    void cancelAction(View view) {
        dismiss();
    }

    @OnClick(R.id.tv_true_dialog)
    void trueAction(View view) {
        if (StringUtils.isNullOrEmpty(tvPrepareNum.getText().toString())){
            Toast.makeText(context, "请确认数量？", Toast.LENGTH_SHORT).show();
            return;
        }
        sendCommonClickListener.OnSendCommonClick(Integer.parseInt(tvPrepareNum.getText().toString()));
        tvPrepareNum.setText(null);
        dismiss();
    }
}
