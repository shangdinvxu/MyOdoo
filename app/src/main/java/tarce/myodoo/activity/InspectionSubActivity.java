package tarce.myodoo.activity;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;

import com.aspsine.swipetoloadlayout.SwipeToLoadLayout;

import butterknife.ButterKnife;
import butterknife.InjectView;
import tarce.myodoo.R;
import tarce.myodoo.uiutil.RecyclerFooterView;
import tarce.myodoo.uiutil.RecyclerHeaderView;
import tarce.support.ToolBarActivity;

/**
 * Created by rose.zou on 2017/6/1.
 * 品检的子页面 列表
 */

public class InspectionSubActivity extends ToolBarActivity {
    @InjectView(R.id.swipe_refresh_header)
    RecyclerHeaderView swipeRefreshHeader;
    @InjectView(R.id.swipe_target)
    RecyclerView swipeTarget;
    @InjectView(R.id.swipe_load_more_footer)
    RecyclerFooterView swipeLoadMoreFooter;
    @InjectView(R.id.swipeToLoad)
    SwipeToLoadLayout swipeToLoad;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_inspection_sub);
        ButterKnife.inject(this);

        setRecyclerview(swipeTarget);
        View view = LayoutInflater.from(InspectionSubActivity.this).inflate(R.layout.inspection_header_sub, null);
        swipeTarget.addView(view, 0);
    }
}
