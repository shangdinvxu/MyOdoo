package tarce.myodoo.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.chad.library.adapter.base.BaseQuickAdapter;

import java.util.ArrayList;
import java.util.List;

import butterknife.ButterKnife;
import butterknife.InjectView;
import tarce.myodoo.IntentFactory;
import tarce.myodoo.R;
import tarce.myodoo.activity.WaitReworkActivity;
import tarce.myodoo.adapter.SectionAdapter;
import tarce.myodoo.bean.MainItemBean;
import tarce.myodoo.bean.MenuBean;

/**
 * Created by Daniel.Xu on 2017/4/20.
 */

public class ProduceFragment extends Fragment {

    public List<MainItemBean> list;
    public SectionAdapter sectionAdapter;
    @InjectView(R.id.recyclerview)
    RecyclerView recyclerview;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        initData();
    }

    private void initData() {
        list = new ArrayList<>();
        list.add(new MainItemBean(new MenuBean("领料", 0)));
        list.add(new MainItemBean(new MenuBean("等待生产", 0)));
        list.add(new MainItemBean(new MenuBean("生产中", 0)));
        list.add(new MainItemBean(new MenuBean("退料", 0)));
        list.add(new MainItemBean(new MenuBean("等待返工", 0)));
        list.add(new MainItemBean(new MenuBean("返工中", 0)));
        list.add(new MainItemBean(new MenuBean("已完成", 0)));
    }


    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_2, null);
        ButterKnife.inject(this, view);
        sectionAdapter = new SectionAdapter(R.layout.mian_list_item, R.layout.adapter_head, list);
        setRecyclerview(recyclerview);
        recyclerview.setAdapter(sectionAdapter);
        setOnclick();
        return view;
    }

    /**
     * item点击事件
     * */
    private void setOnclick() {
        sectionAdapter.setOnItemClickListener(new BaseQuickAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(BaseQuickAdapter adapter, View view, int position) {
                String name = list.get(position).t.getName();
                switch (name){
                    case "领料":
                        IntentFactory.start_ProducLl_Activity(getActivity(), "领料", "finish_prepare_material");
                        break;
                    case "等待生产":
                        IntentFactory.start_ProducLl_Activity(getActivity(), "等待生产", "already_picking");
                        break;
                    case "生产中":
                        IntentFactory.start_ProducLl_Activity(getActivity(), "生产中", "progress");
                        break;
                    case "退料":
                        IntentFactory.start_ProducLl_Activity(getActivity(), "退料", "waiting_inventory_material");
                        break;
                    case "等待返工"://和其他不一样
                        IntentFactory.start_WaitRework_Activity(getActivity(), "等待返工","qc_fail");
                        break;
                    case "返工中":
                        IntentFactory.start_ProducLl_Activity(getActivity(), "返工中", "rework_ing");
                        break;
                    case "已完成":
                        IntentFactory.start_ProducLl_Activity(getActivity(), "已完成", "done");
                        break;
                }
            }
        });
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
}
