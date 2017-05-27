package tarce.myodoo.uiutil;

import android.app.Dialog;
import android.content.Context;
import android.view.Display;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.TextView;

import tarce.myodoo.R;


/**
 * Created by zws on 2016/12/31.
 */
public class TakePhotoDialog extends Dialog{
    private TextView mTv_takephoto;
    private TextView mTv_selectphoto;
    private TextView mTv_cancel;

    private Context context;
    private Display display;
    private LayoutInflater inflater;
    private View view;

    public TakePhotoDialog(Context context) {
        super(context, R.style.MyDialogStyle);
        this.context = context;
        inflater = LayoutInflater.from(context);
        WindowManager windowManager = (WindowManager) context.getSystemService(Context.WINDOW_SERVICE);
        display = windowManager.getDefaultDisplay();
        initView();
    }

    private void initView() {
        view = inflater.inflate(R.layout.dialog_photo_select, null);

        mTv_takephoto = (TextView) view.findViewById(R.id.tv_select_photo);
        mTv_selectphoto = (TextView) view.findViewById(R.id.tv_select_album);
        mTv_cancel = (TextView) view.findViewById(R.id.tv_select_cancel);
        setContentView(view);

        Window window = getWindow(); //得到对话框
        window.setWindowAnimations(R.style.MyDialogStyle); //设置窗口弹出动画
        WindowManager.LayoutParams wl = window.getAttributes(); //根据x，y坐标设置窗口需要显示的位置
        wl.gravity = Gravity.CENTER;
        wl.width = (int) (display.getWidth()*0.6);
        wl.height = (int) (display.getHeight()*0.3);
        wl.alpha = 1.0f;
        window.setAttributes(wl); //设置触摸对话框以外的地方取消对话框
        setCanceledOnTouchOutside(true);
        setCancelable(true);
    }

    public TakePhotoDialog(Context context, int themeResId) {
        super(context, R.style.MyDialogStyle);
    }

    public TakePhotoDialog setTakephoto(final View.OnClickListener listener){
        mTv_takephoto.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                listener.onClick(v);
                dismiss();
            }
        });
        return this;
    }
    public TakePhotoDialog setSelectalbum(final View.OnClickListener listener){
        mTv_selectphoto.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                listener.onClick(v);
                dismiss();
            }
        });
        return this;
    }
    public TakePhotoDialog setCancel(){
        mTv_cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dismiss();
            }
        });
        return this;
    }
}
