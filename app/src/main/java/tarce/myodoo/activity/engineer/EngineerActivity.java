package tarce.myodoo.activity.engineer;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.LinearLayout;

import java.io.Serializable;

import butterknife.ButterKnife;
import butterknife.InjectView;
import butterknife.OnClick;
import rx.Observable;
import rx.Observer;
import rx.Subscriber;
import tarce.myodoo.R;
import tarce.myodoo.activity.BaseActivity;

/**
 * Created by zouwansheng on 2017/9/5.
 */

public class EngineerActivity extends BaseActivity {

    @InjectView(R.id.line_product)
    LinearLayout lineProduct;
    @InjectView(R.id.line_engineer)
    LinearLayout lineEngineer;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_engineer);
        ButterKnife.inject(this);

    }

    //产线领用
    @OnClick(R.id.line_product)
    void setLineProduct(View view){
        Intent intent = new Intent(EngineerActivity.this, ProjectActivity.class);
        intent.putExtra("type", "pick_type");
        startActivity(intent);
    }

    //工程领用
    @OnClick(R.id.line_engineer)
    void setLineEngineer(View view){
        Intent intent = new Intent(EngineerActivity.this, ProjectActivity.class);
        intent.putExtra("type", "proofing");
        startActivity(intent);
    }
}
