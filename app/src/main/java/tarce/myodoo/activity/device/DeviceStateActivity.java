package tarce.myodoo.activity.device;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.RecyclerView;

import butterknife.ButterKnife;
import butterknife.InjectView;
import tarce.myodoo.R;
import tarce.myodoo.activity.BaseActivity;

/**
 * Created by zouwansheng on 2017/9/18.
 */

public class DeviceStateActivity extends BaseActivity {
    @InjectView(R.id.recycler_devicestate)
    RecyclerView recyclerDevicestate;
    private String name;
    private int id;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_devicestate);
        ButterKnife.inject(this);

        initIntent();
        //item_devicestate
    }

    private void initIntent() {
        Intent intent = getIntent();
        name = intent.getStringExtra("name");
        id = intent.getIntExtra("id", 1);
    }
}
