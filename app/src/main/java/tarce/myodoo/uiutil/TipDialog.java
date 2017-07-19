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
import android.widget.Button;
import android.widget.TextView;

import tarce.myodoo.R;

/**
 * Created by zws on 2017/7/17.
 */

public class TipDialog extends Dialog {
    private Display display;
    private Dialog dialog;
    private Context context;
    private LayoutInflater inflater;
    private TextView mTv_content;
    private Button mBtn_true;
    private String content;

    public TipDialog(@NonNull Context context) {
        super(context);

        this.context = context;
        inflater = LayoutInflater.from(context);
        WindowManager windowManager = (WindowManager) context.getSystemService(Context.WINDOW_SERVICE);
        display = windowManager.getDefaultDisplay();
        initView();
    }
    public TipDialog(@NonNull Context context, @StyleRes int themeResId, String content) {
        super(context, R.style.MyDialogStyle);

        this.context = context;
        this.content = content;
        inflater = LayoutInflater.from(context);
        WindowManager windowManager = (WindowManager) context.getSystemService(Context.WINDOW_SERVICE);
        display = windowManager.getDefaultDisplay();
        initView();
    }

    private void initView() {
        View view = inflater.inflate(R.layout.dialog_tipdialog, null);
        setContentView(view);

        mTv_content = (TextView) view.findViewById(R.id.tv_content);
        mBtn_true = (Button) view.findViewById(R.id.btn_true);

        Window window = getWindow();
        WindowManager.LayoutParams wl = window.getAttributes();
        wl.gravity = Gravity.CENTER;
        wl.width = (int) (display.getWidth() * 0.8);
        wl.height = WindowManager.LayoutParams.WRAP_CONTENT;
        window.setAttributes(wl);
        setCanceledOnTouchOutside(false);
        setCancelable(true);

        mTv_content.setText(content);
        mBtn_true.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dismiss();
            }
        });
    }

    public TipDialog setTrue(final View.OnClickListener listener){
        mBtn_true.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (listener!=null)
                listener.onClick(v);
            }
        });
        return this;
    }
}
