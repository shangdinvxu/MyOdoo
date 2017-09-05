package tarce.myodoo.activity.engineer;

import android.os.Bundle;
import android.widget.LinearLayout;

import butterknife.ButterKnife;
import butterknife.InjectView;
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
}
