package tarce.myodoo.activity.outsourcing;

import android.content.Intent;
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
 * Created by zouwansheng on 2017/11/10.
 */

public class OutSourcingActivity extends BaseActivity {
    @InjectView(R.id.one_out_num)
    Button oneOutNum;
    @InjectView(R.id.linear_wait_out)
    LinearLayout linearWaitOut;
    @InjectView(R.id.two_out_num)
    Button twoOutNum;
    @InjectView(R.id.linear_out)
    LinearLayout linearOut;

    @Override
    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_out_sourcing);
        ButterKnife.inject(this);

    }

    @OnClick(R.id.linear_wait_out)
    void setLinearWaitOut(View view){
        Intent intent = new Intent(OutSourcingActivity.this, OutSourceListActivity.class);
        intent.putExtra("source", "draft");
        startActivity(intent);
    }

    @OnClick(R.id.linear_out)
    void setLinearOut(View view){
        Intent intent = new Intent(OutSourcingActivity.this, OutSourceListActivity.class);
        intent.putExtra("source", "out_ing");
        startActivity(intent);
    }
}
