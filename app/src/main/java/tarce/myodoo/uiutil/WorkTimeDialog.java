package tarce.myodoo.uiutil;

import android.app.Dialog;
import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.StyleRes;
import android.view.Display;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import java.util.List;

import butterknife.ButterKnife;
import butterknife.InjectView;
import butterknife.OnClick;
import tarce.model.GetSaleResponse;
import tarce.model.WorkTypeBean;
import tarce.myodoo.R;
import tarce.myodoo.utils.StringUtils;


/**
 * Created by zouwansheng on 2018/1/10.
 */

public class WorkTimeDialog extends Dialog {
    TextView tvName;
    TextView tvWorkName;
    EditText tvTime;
    Button btnCancel;
    Button btnTrue;
    Spinner spinner;
    private Display display;
    private Dialog dialog;
    private Context context;
    private LayoutInflater inflater;
    private OnSendCommonClickListener sendCommonClickListener;
    private static final String[] m={"A型","B型","O型","AB型","其他"};
    private ArrayAdapter<String> adapter;
    private String gongzhong;
    private View view;
    private List<WorkTypeBean.ResultBean.ResDataBean> list;
    private int type_id;
    private String from_partner = "暂无";
    private String to_partner = "暂无";

    public WorkTimeDialog(@NonNull Context context) {
        super(context, R.style.MyDialogStyle);

        this.context = context;
        inflater = LayoutInflater.from(context);
        WindowManager windowManager = (WindowManager) context.getSystemService(Context.WINDOW_SERVICE);
        display = windowManager.getDefaultDisplay();
        initView();
    }

    public WorkTimeDialog(@NonNull Context context, @StyleRes int themeResId, OnSendCommonClickListener sendCommonClickListener
                                                , List<WorkTypeBean.ResultBean.ResDataBean> list, String from_partner, String to_partner) {
        super(context, R.style.MyDialogStyle);

        this.from_partner = from_partner;
        this.to_partner = to_partner;
        this.list = list;
        this.sendCommonClickListener = sendCommonClickListener;
        this.context = context;
        inflater = LayoutInflater.from(context);
        WindowManager windowManager = (WindowManager) context.getSystemService(Context.WINDOW_SERVICE);
        display = windowManager.getDefaultDisplay();
        initView();
    }

    private void initView() {
        view = inflater.inflate(R.layout.dialog_worktime, null);
        setContentView(view);

        tvName = (TextView) view.findViewById(R.id.tv_name);
        tvWorkName = (TextView) view.findViewById(R.id.tv_work_name);
        tvTime = (EditText) view.findViewById(R.id.tv_time);
        btnCancel = (Button) view.findViewById(R.id.btn_cancel);
        btnTrue = (Button) view.findViewById(R.id.btn_true);
        spinner = (Spinner) view.findViewById(R.id.Spinner01);

        tvWorkName.setText(to_partner);
        tvName.setText(from_partner);

        Window window = getWindow();
        WindowManager.LayoutParams wl = window.getAttributes();
        wl.gravity = Gravity.CENTER;
        wl.width = (int) (display.getWidth() * 0.8);
        wl.height = WindowManager.LayoutParams.WRAP_CONTENT;
        window.setAttributes(wl);
        setCanceledOnTouchOutside(false);
        setCancelable(true);

        btnCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dismiss();
            }
        });

        MyAdapter adapter = new MyAdapter(list, context);

        //将adapter 添加到spinner中
        spinner.setAdapter(adapter);

        //添加事件Spinner事件监听
        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                gongzhong = list.get(i).getName();
                type_id = list.get(i).getId();
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });

        //设置默认值
        spinner.setVisibility(View.VISIBLE);
        btnTrue.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (StringUtils.isNullOrEmpty(tvTime.getText().toString())) {
                    Toast.makeText(context, "请填写工时？", Toast.LENGTH_SHORT).show();
                    return;
                }
                if (StringUtils.isNullOrEmpty(gongzhong)){
                    Toast.makeText(context, "没有工种信息，请选择？", Toast.LENGTH_SHORT).show();
                    return;
                }
                sendCommonClickListener.OnSendCommonClick(Double.parseDouble(tvTime.getText().toString()), type_id);
                tvTime.setText(null);
                dismiss();
            }
        });
    }

    public WorkTimeDialog setTrue(final View.OnClickListener listener){
        btnTrue.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (listener!=null)
                    listener.onClick(v);
            }
        });
        return this;
    }

    public interface OnSendCommonClickListener {
        void OnSendCommonClick(double num, int typeId);
    }

    class MyAdapter extends BaseAdapter{
        private List<WorkTypeBean.ResultBean.ResDataBean> mList;
        private Context mContext;

        public MyAdapter(List<WorkTypeBean.ResultBean.ResDataBean> mList, Context mContext) {
            this.mList = mList;
            this.mContext = mContext;
        }

        @Override
        public int getCount() {
            return mList.size();
        }

        @Override
        public Object getItem(int i) {
            return mList.get(i);
        }

        @Override
        public long getItemId(int i) {
            return i;
        }

        @Override
        public View getView(int i, View convertView, ViewGroup viewGroup) {
            LayoutInflater layoutInflater = LayoutInflater.from(mContext);
            convertView = layoutInflater.inflate(R.layout.item_country, null);
            if(convertView!=null) {
                TextView text_name=(TextView)convertView.findViewById(R.id.china_tv);
                text_name.setText(mList.get(i).getName());
            }
            return convertView;
        }
    }
}
