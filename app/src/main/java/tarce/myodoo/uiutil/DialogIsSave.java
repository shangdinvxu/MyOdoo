package tarce.myodoo.uiutil;

import android.app.Dialog;
import android.content.Context;
import android.support.annotation.NonNull;
import android.view.Display;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.TextView;

import butterknife.ButterKnife;
import butterknife.InjectView;
import tarce.myodoo.R;

/**
 * Created by rose.zou on 2017/6/20.
 */

public class DialogIsSave extends Dialog {
    @InjectView(R.id.tv_save)
    TextView tvSave;
    @InjectView(R.id.tv_not_save)
    TextView tvNotSave;
    @InjectView(R.id.tv_cancel)
    TextView tvCancel;
    @InjectView(R.id.tv_title)
    TextView tvTitle;
    private LayoutInflater inflater;
    private Display display;
    private Context context;
    private View view;

    public DialogIsSave(@NonNull Context context) {
        super(context, R.style.MyDialogStyle);

        this.context = context;
        inflater = LayoutInflater.from(context);
        WindowManager windowManager = (WindowManager) context.getSystemService(Context.WINDOW_SERVICE);
        display = windowManager.getDefaultDisplay();
        initView();
    }

    private void initView() {
        view = inflater.inflate(R.layout.dialog_is_save, null);
        setContentView(view);
        ButterKnife.inject(this, view);

        Window window = getWindow();
        WindowManager.LayoutParams wl = window.getAttributes();
        wl.gravity = Gravity.CENTER;
        wl.width = (int) (display.getWidth() * 0.6);
        wl.height = WindowManager.LayoutParams.WRAP_CONTENT;
        wl.alpha = 1.0f;
        window.setAttributes(wl);
        setCanceledOnTouchOutside(true);
        setCancelable(true);
    }

    public DialogIsSave changeT(String title){
        tvTitle.setText(title);
        return this;
    }

    public DialogIsSave changeFirst(String first){
        tvSave.setText(first);
        return this;
    }

    public DialogIsSave changeSecond(String second){
        tvSave.setText(second);
        return this;
    }

    public DialogIsSave changeTitle() {
        tvTitle.setText("选择生产单类型");
        return this;
    }

    public DialogIsSave changeOne() {
        tvSave.setText("创建欠单");
        return this;
    }

    public DialogIsSave changeTwo() {
        tvNotSave.setText("没有欠单");
        return this;
    }

    public DialogIsSave setCancel() {
        tvCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dismiss();
            }
        });
        return this;
    }

    public DialogIsSave setSave(final View.OnClickListener listener) {
        tvSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                listener.onClick(v);
                dismiss();
            }
        });
        return this;
    }

    public DialogIsSave setNotSave(final View.OnClickListener listener) {
        tvNotSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                listener.onClick(v);
                dismiss();
            }
        });
        return this;
    }

}
