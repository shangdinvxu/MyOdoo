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
 * Created by rose.zou on 2017/6/16.
 */


public class InsertFeedbackDial extends Dialog {


    @InjectView(R.id.edit_feedback_message)
    EditText editFeedbackMessage;
    @InjectView(R.id.cancel_insert_dialog)
    TextView cancelInsertDialog;
    @InjectView(R.id.true_inset_dialog)
    TextView trueInsetDialog;
    private LayoutInflater inflater;
    private Display display;
    private Context context;
    private View view;
    private OnSendCommonClickListener sendCommonClickListener;

    public InsertFeedbackDial(@NonNull Context context) {
        super(context, R.style.MyDialogStyle);

        this.context = context;
        inflater = LayoutInflater.from(context);
        WindowManager windowManager = (WindowManager) context.getSystemService(Context.WINDOW_SERVICE);
        display = windowManager.getDefaultDisplay();
        initView();
    }

    public InsertFeedbackDial(@NonNull Context context, @StyleRes int themeResId, OnSendCommonClickListener sendCommonClickListener) {
        super(context, R.style.MyDialogStyle);

        this.sendCommonClickListener = sendCommonClickListener;
        this.context = context;
        inflater = LayoutInflater.from(context);
        WindowManager windowManager = (WindowManager) context.getSystemService(Context.WINDOW_SERVICE);
        display = windowManager.getDefaultDisplay();
        initView();
    }

    private void initView() {
        view = inflater.inflate(R.layout.dialog_insert_feedback, null);
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

        /*eidtOutNum.setFocusable(true);
        eidtOutNum.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_VISIBLE);
            }
        });

        eidtOutNum.setSelection(eidtOutNum.getText().length());*/
    }

    public interface OnSendCommonClickListener {
        void OnSendCommonClick(int num);
    }


    @OnClick(R.id.cancel_insert_dialog)
    void cancelAction(View view) {
        dismiss();
    }

    /*@OnClick(R.id.true_inset_dialog)
    void trueAction(View view) {
        if (StringUtils.isNullOrEmpty(eidtOutNum.getText().toString())) {
            Toast.makeText(context, "请确认数量？", Toast.LENGTH_SHORT).show();
            return;
        }
        sendCommonClickListener.OnSendCommonClick(Integer.parseInt(eidtOutNum.getText().toString()));
        eidtOutNum.setText(null);
        dismiss();
    }*/
}
