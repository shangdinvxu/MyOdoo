package tarce.myodoo.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import java.util.ArrayList;
import java.util.List;

import butterknife.ButterKnife;
import butterknife.InjectView;
import butterknife.OnClick;
import tarce.myodoo.IntentFactory;
import tarce.myodoo.R;
import tarce.myodoo.adapter.SectionAdapter;
import tarce.myodoo.bean.MainItemBean;
import tarce.myodoo.bean.MenuBean;
import tarce.support.ToolBarActivity;

/**
 * Created by Daniel.Xu on 2017/4/20.
 */

public class WarehouseFragment extends Fragment {
    @InjectView(R.id.sales_out)
    Button salesOut;
    @InjectView(R.id.recyclerview)
    RecyclerView recyclerview;
    public  List<MainItemBean> list;
    public SectionAdapter sectionAdapter;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        initData();
    }

    private void initData() {
        list = new ArrayList<>();
        list.add(new MainItemBean(true, ""));
        list.add(new MainItemBean(new MenuBean("收货", 0)));
        list.add(new MainItemBean(new MenuBean("入库", 0)));
        list.add(new MainItemBean(new MenuBean("采购退货", 0)));
        list.add(new MainItemBean(true, ""));
        list.add(new MainItemBean(new MenuBean("生产备料", 0)));
        list.add(new MainItemBean(new MenuBean("生产退料", 0)));
        list.add(new MainItemBean(new MenuBean("生产入库", 0)));
        list.add(new MainItemBean(new MenuBean("生产补料", 0)));
        list.add(new MainItemBean(true, ""));
        list.add(new MainItemBean(new MenuBean("销售出库", 0)));
        list.add(new MainItemBean(new MenuBean("销售退货", 0)));
        list.add(new MainItemBean(true, ""));
        list.add(new MainItemBean(new MenuBean("零售出库", 0)));
        list.add(new MainItemBean(new MenuBean("零售退货", 0)));
        list.add(new MainItemBean(true, ""));
        list.add(new MainItemBean(new MenuBean("盘点", 0)));
        list.add(new MainItemBean(new MenuBean("物料异常上报", 0)));
        list.add(new MainItemBean(new MenuBean("库存查询", 0)));
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_1, null);
        ButterKnife.inject(this, view);
        sectionAdapter = new SectionAdapter(R.layout.mian_list_item, R.layout.adapter_head, list);
        setRecyclerview(recyclerview);
        recyclerview.setAdapter(sectionAdapter);
        return view;
    }


    public void setRecyclerview(RecyclerView recyclerview){
        recyclerview.setLayoutManager(new LinearLayoutManager(getActivity()));
        recyclerview.addItemDecoration(new DividerItemDecoration(getActivity(),
                DividerItemDecoration.VERTICAL));
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        ButterKnife.reset(this);
    }

    @OnClick(R.id.sales_out)
    void setSalesOut(View view) {
        IntentFactory.start_SalesOut_Activity(getActivity());
    }

    @OnClick(R.id.sales_in)
    void setSaleIn(View view) {
        IntentFactory.start_SalesIn_Activity(getActivity());
    }


}
