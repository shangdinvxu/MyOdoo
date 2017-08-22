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
import android.widget.ImageView;
import android.widget.TextView;

import butterknife.ButterKnife;
import butterknife.InjectView;
import tarce.myodoo.R;
import tarce.myodoo.utils.StringUtils;

/**
 * Created by zws on 2017/8/16.
 */

public class NFCdialog extends Dialog {
    @InjectView(R.id.image_nfc)
    ImageView imageNfc;
    @InjectView(R.id.tip_nfc)
    TextView tipNfc;
    @InjectView(R.id.btn_cancel_nfc)
    Button btnCancelNfc;
    private LayoutInflater inflater;
    private Display display;
    private Context context;
    private View view;

    public NFCdialog(@NonNull Context context) {
        super(context, R.style.MyDialogStyle);

        this.context = context;
        inflater = LayoutInflater.from(context);
        WindowManager windowManager = (WindowManager) context.getSystemService(Context.WINDOW_SERVICE);
        display = windowManager.getDefaultDisplay();
        initView();
    }

    public NFCdialog(@NonNull Context context, @StyleRes int themeResId) {
        super(context, R.style.MyDialogStyle);

        this.context = context;
        inflater = LayoutInflater.from(context);
        WindowManager windowManager = (WindowManager) context.getSystemService(Context.WINDOW_SERVICE);
        display = windowManager.getDefaultDisplay();
        initView();
    }

    private void initView() {
        view = inflater.inflate(R.layout.activity_nfcdialog, null);
        setContentView(view);
        ButterKnife.inject(this, view);

        Window window = getWindow();
        WindowManager.LayoutParams wl = window.getAttributes();
        wl.gravity = Gravity.CENTER;
        wl.width = (int) (display.getWidth());
        wl.height = WindowManager.LayoutParams.MATCH_PARENT;
        wl.alpha = 1.0f;
        window.setAttributes(wl);
        setCanceledOnTouchOutside(true);
        setCancelable(true);
    }

    public NFCdialog setTip(String nfcTip){
        tipNfc.setText(nfcTip);
        return this;
    }

    public NFCdialog setHeaderImage(int id){
        imageNfc.setImageResource(id);
        return this;
    }

    public NFCdialog setCancel(final View.OnClickListener listener){
        btnCancelNfc.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                listener.onClick(v);
            }
        });
        return this;
    }

    public NFCdialog setCancelVisi(){
        btnCancelNfc.setVisibility(View.GONE);
        return this;
    }
}
