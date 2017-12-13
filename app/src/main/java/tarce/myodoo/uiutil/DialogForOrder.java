package tarce.myodoo.uiutil;

import android.app.Dialog;
import android.content.Context;
import android.support.annotation.NonNull;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.Display;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import butterknife.ButterKnife;
import butterknife.InjectView;
import butterknife.OnClick;
import tarce.model.inventory.OrderDetailBean;
import tarce.myodoo.R;
import tarce.myodoo.activity.LoginActivity;
import tarce.myodoo.utils.StringUtils;
import tarce.support.ToastUtils;

/**
 * Created by rose.zou on 2017/5/24.
 * 领料订单详情里面所需dialog
 */

public class DialogForOrder extends Dialog {
    @InjectView(R.id.tv_weight_num)
    EditText tvWeightNum;
    @InjectView(R.id.btn_transterm)
    Button btnTransterm;
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
    private double weightNum = 0;
    private TextWatcher textWatcher = new TextWatcher() {
        CharSequence temp;

        @Override
        public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {
            temp = charSequence;

        }

        @Override
        public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

        }

        @Override
        public void afterTextChanged(Editable editable) {
            if (StringUtils.isNullOrEmpty(temp.toString())) {
                tvWeightNum.setText("0");
                return;
            }
            double num = Double.parseDouble(temp.toString());
            tvWeightNum.setText(num * weightNum + "");
        }
    };

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
        double v1 = linesBean.getProduct_uom_qty() - linesBean.getQuantity_ready() - linesBean.getQuantity_done();
        if (v1 < 0) {
            tvPrepareNum.setText("0");
        } else if (v1 >= linesBean.getQty_available()) {
            tvPrepareNum.setText(StringUtils.doubleToString(linesBean.getQty_available()));
        } else if (v1 < linesBean.getQty_available()) {
            tvPrepareNum.setText(StringUtils.doubleToString(v1));
        }
        tvPrepareNum.setSelection(tvPrepareNum.getText().length());
    }

    public interface OnSendCommonClickListener {
        void OnSendCommonClick(double num);
    }

    public DialogForOrder setWeight(double weightNum) {
        this.weightNum = weightNum;
        double valueOf = Double.valueOf(tvPrepareNum.getText().toString());
        tvWeightNum.setText(weightNum * valueOf + "");
        tvPrepareNum.addTextChangedListener(textWatcher);
        return this;
    }

    @OnClick(R.id.tv_cancel_dialog)
    void cancelAction(View view) {
        dismiss();
    }

    @OnClick(R.id.tv_true_dialog)
    void trueAction(View view) {
        if (StringUtils.isNullOrEmpty(tvPrepareNum.getText().toString())) {
            Toast.makeText(context, "请确认数量？", Toast.LENGTH_SHORT).show();
            return;
        }
        sendCommonClickListener.OnSendCommonClick(Double.valueOf(tvPrepareNum.getText().toString()));
        tvPrepareNum.setText(null);
        dismiss();
    }

    @OnClick(R.id.btn_transterm)
    void setBtnTransterm(View view){
        double weightDouble = Double.parseDouble(tvWeightNum.getText().toString());
        Log.e("zws", "weightDouble = "+weightDouble);
        if (StringUtils.isNullOrEmpty(tvWeightNum.getText().toString())){
            tvPrepareNum.setText("0");
            ToastUtils.showCommonToast(context, "请输入总重量");
        }else if (weightDouble == 0){
            tvPrepareNum.setText("0");
        }else if (weightNum!=0){
            double v = (double) weightDouble / weightNum;
            Log.e("zws", "v = "+v);
            tvPrepareNum.setText(StringUtils.twoDouble(v));
            tvWeightNum.setText(""+weightDouble);
        }
    }
}
