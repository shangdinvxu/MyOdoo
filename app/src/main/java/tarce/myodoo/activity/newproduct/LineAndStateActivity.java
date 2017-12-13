package tarce.myodoo.activity.newproduct;

import android.os.Bundle;
import android.support.v7.widget.RecyclerView;

import butterknife.ButterKnife;
import butterknife.InjectView;
import tarce.myodoo.R;
import tarce.myodoo.activity.BaseActivity;

/**
 * Created by zouwansheng on 2017/11/16.
 */

public class LineAndStateActivity extends BaseActivity {

    @InjectView(R.id.first_recycler)
    RecyclerView firstRecycler;
    @InjectView(R.id.second_recycler)
    RecyclerView secondRecycler;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_lineadnstate);
        ButterKnife.inject(this);
        setRecyclerview(firstRecycler);
        setRecyclerview(secondRecycler);
    }
}
