package tarce.myodoo.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.RecyclerView;

import java.io.Serializable;
import java.util.List;

import butterknife.ButterKnife;
import butterknife.InjectView;
import tarce.model.GetSaleListByNumberResponse;
import tarce.myodoo.R;
import tarce.myodoo.adapter.SalesListAdapter;
import tarce.support.ToolBarActivity;

/**
 * Created by Daniel.Xu on 2017/4/20.
 */

public class SalesListActivity extends ToolBarActivity {
    @InjectView(R.id.recyclerview)
    RecyclerView recyclerview;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sales_list);
        ButterKnife.inject(this);
        makeDefultRecycler();
        recyclerview.setLayoutManager(linearLayoutManager);
        recyclerview.addItemDecoration(dividerItemDecoration);
        initIntent();
    }

    private void initIntent() {
        Intent intent = getIntent();
        Bundle bundle = intent.getBundleExtra("intent");
        List<GetSaleListByNumberResponse.TResult.TRes_data> bundle1 = (List<GetSaleListByNumberResponse.TResult.TRes_data>) bundle.getSerializable("bundle");
        SalesListAdapter salesListAdapter = new SalesListAdapter(R.layout.activity_salelist_item, bundle1);
        recyclerview.setAdapter(salesListAdapter);
    }
}
