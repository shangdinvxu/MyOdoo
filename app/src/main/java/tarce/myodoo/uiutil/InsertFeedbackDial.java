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
    private String remark;

    public InsertFeedbackDial(@NonNull Context context) {
        super(context, R.style.MyDialogStyle);

        this.context = context;
        inflater = LayoutInflater.from(context);
        WindowManager windowManager = (WindowManager) context.getSystemService(Context.WINDOW_SERVICE);
        display = windowManager.getDefaultDisplay();
        initView();
    }

    public InsertFeedbackDial(@NonNull Context context, @StyleRes int themeResId, OnSendCommonClickListener sendCommonClickListener
                                                , String remark) {
        super(context, R.style.MyDialogStyle);

        this.remark = remark;
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

        editFeedbackMessage.setFocusable(true);
        editFeedbackMessage.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_VISIBLE);
            }
        });

        editFeedbackMessage.setText(remark);
        editFeedbackMessage.setSelection(editFeedbackMessage.getText().length());
    }

    public interface OnSendCommonClickListener {
        void OnSendCommonClick(String num);
    }


    @OnClick(R.id.cancel_insert_dialog)
    void cancelAction(View view) {
        dismiss();
    }

    @OnClick(R.id.true_inset_dialog)
    void trueAction(View view) {
        if (StringUtils.isNullOrEmpty(editFeedbackMessage.getText().toString())) {
            Toast.makeText(context, "填写反馈？", Toast.LENGTH_SHORT).show();
            return;
        }
        sendCommonClickListener.OnSendCommonClick(editFeedbackMessage.getText().toString());
        editFeedbackMessage.setText(null);
        dismiss();
    }
}
