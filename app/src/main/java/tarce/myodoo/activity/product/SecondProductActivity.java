package tarce.myodoo.activity.product;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;

import butterknife.ButterKnife;
import butterknife.InjectView;
import butterknife.OnClick;
import tarce.myodoo.R;
import tarce.myodoo.activity.BaseActivity;

/**
 * Created by zouwansheng on 2017/10/25.
 */

public class SecondProductActivity extends BaseActivity {
    @InjectView(R.id.one_num)
    Button oneNum;
    @InjectView(R.id.sales_out)
    LinearLayout salesOut;
    @InjectView(R.id.two_num)
    Button twoNum;
    @InjectView(R.id.buy_in)
    LinearLayout buyIn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_secondproduct);
        ButterKnife.inject(this);
        setTitle("二次加工");

        initData();
    }

    private void initData() {

    }

    @OnClick(R.id.sales_out)
    void setSalesOut(View view){

    }

    @OnClick(R.id.buy_in)
    void setBuyIn(View view){

    }
}
