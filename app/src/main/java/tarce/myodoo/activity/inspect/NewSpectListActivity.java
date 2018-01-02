package tarce.myodoo.activity.inspect;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.view.Gravity;
import android.view.View;

import com.aspsine.swipetoloadlayout.OnLoadMoreListener;
import com.aspsine.swipetoloadlayout.OnRefreshListener;
import com.aspsine.swipetoloadlayout.SwipeToLoadLayout;
import com.chad.library.adapter.base.BaseQuickAdapter;

import java.io.Serializable;
import java.util.List;

import butterknife.ButterKnife;
import butterknife.InjectView;
import tarce.model.inventory.QcFeedbaskBean;
import tarce.myodoo.R;
import tarce.myodoo.activity.BaseActivity;
import tarce.myodoo.adapter.InspectionSubAdapter;
import tarce.myodoo.uiutil.RecyclerFooterView;
import tarce.myodoo.uiutil.RecyclerHeaderView;

/**
 * Created by zouwansheng on 2017/12/21.
 * 适用于生产入库， 等待返工
 */

public class NewSpectListActivity extends BaseActivity {
    public static final int Refresh = 1;
    @InjectView(R.id.swipe_refresh_header)
    RecyclerHeaderView swipeRefreshHeader;
    @InjectView(R.id.swipe_target)
    RecyclerView swipeTarget;
    @InjectView(R.id.swipe_load_more_footer)
    RecyclerFooterView swipeLoadMoreFooter;
    @InjectView(R.id.swipeToLoad)
    SwipeToLoadLayout swipeToLoad;
    private String state;
    private int process_id;
    private int line_id;
    private List<QcFeedbaskBean.ResultBean.ResDataBean> bean;
    private InspectionSubAdapter subAdapter;
    private boolean isChange = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_inspection_sub);
        ButterKnife.inject(this);

        setRecyclerview(swipeTarget);

        Intent intent = getIntent();
        state = intent.getStringExtra("state");
        process_id = intent.getIntExtra("process_id", -1000);
        line_id = intent.getIntExtra("line_id", -1000);
        bean = (List<QcFeedbaskBean.ResultBean.ResDataBean>) intent.getSerializableExtra("data");
        setRecyc();
        initData();
    }

    private void initData() {
        subAdapter = new InspectionSubAdapter(R.layout.adapter_inspec_sub, bean);
        swipeTarget.setAdapter(subAdapter);
        clickItem();
    }

    private void clickItem() {
        subAdapter.setOnItemClickListener(new BaseQuickAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(BaseQuickAdapter adapter, View view, int position) {
                Intent intent = new Intent(NewSpectListActivity.this, InspectMoDetailActivity.class);
                intent.putExtra("data", bean.get(position));
                intent.putExtra("state",state);
                intent.putExtra("process_id", process_id);
                intent.putExtra("line_id", line_id);
                startActivityForResult(intent, Refresh);
            }
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == Refresh){
            if (requestCode == Refresh){
                int feed_back = data.getIntExtra("feed_back", -1);
                boolean isHave = false;
                int index = -1;
                for (int i = 0; i < bean.size(); i++) {
                    if (bean.get(i).getFeedback_id() == feed_back){
                        isHave = true;
                        index = i;
                        break;
                    }
                }
                if (isHave){
                    bean.remove(index);
                    subAdapter.notifyDataSetChanged();
                }
            }
        }
    }

    @Override
    protected void onResume(){
        //刷新试图
        if (isChange){
            //
        }
        super.onResume();
    }

    private void setRecyc() {
        swipeRefreshHeader.setGravity(Gravity.CENTER);
        swipeLoadMoreFooter.setGravity(Gravity.CENTER);
        swipeToLoad.setRefreshHeaderView(swipeRefreshHeader);
        swipeToLoad.setLoadMoreFooterView(swipeLoadMoreFooter);

        swipeToLoad.setOnRefreshListener(new OnRefreshListener() {
            @Override
            public void onRefresh() {

                swipeToLoad.setRefreshing(false);
            }
        });
        swipeToLoad.setOnLoadMoreListener(new OnLoadMoreListener() {
            @Override
            public void onLoadMore() {
                swipeToLoad.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        swipeToLoad.setLoadingMore(false);
                    }
                }, 1000);
            }
        });
    }

    @Override
    protected void onPause() {
        isChange = true;
        super.onPause();
    }
}
