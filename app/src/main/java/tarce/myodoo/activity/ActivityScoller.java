package tarce.myodoo.activity;

import android.os.Bundle;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;

import java.util.ArrayList;
import java.util.List;

import butterknife.ButterKnife;
import butterknife.InjectView;
import rx.Observable;
import rx.functions.Action1;
import tarce.myodoo.R;
import tarce.support.ToolBarActivity;

/**
 * Created by Daniel.Xu on 2017/5/31.
 */

public class ActivityScoller extends ToolBarActivity {
    @InjectView(R.id.recyclerview)
    MyFatherRecyclerView recyclerview;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_scoller);
        ButterKnife.inject(this);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(ActivityScoller.this);
        linearLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        recyclerview.setLayoutManager(linearLayoutManager);
        recyclerview.addItemDecoration(new DividerItemDecoration(ActivityScoller.this,
                DividerItemDecoration.VERTICAL));
        final List<ArrayList<String>> date = new ArrayList<>();
        final ArrayList<String> objects = new ArrayList<>();
        Observable.range(2,100).subscribe(new Action1<Integer>() {
            @Override
            public void call(Integer integer) {
            objects.add(integer+"");
            }
        });
        Observable.range(2,80).subscribe(new Action1<Integer>() {
            @Override
            public void call(Integer integer) {
                date.add(objects);
            }
        });
        MyScallerAdapter myScallerAdapter = new MyScallerAdapter(R.layout.base_scoller, date);
        recyclerview.setAdapter(myScallerAdapter);
    }

    private class MyScallerAdapter extends BaseQuickAdapter<ArrayList<String>,BaseViewHolder>{


        public MyScallerAdapter(int layoutResId, List<ArrayList<String>> data) {
            super(layoutResId, data);
        }

        @Override
        protected void convert(BaseViewHolder helper, ArrayList<String> item) {
            RecyclerView view = helper.getView(R.id.recyclerview_second);
            LinearLayoutManager linearLayoutManager = new LinearLayoutManager(ActivityScoller.this);
            linearLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);
            view.setLayoutManager(linearLayoutManager);
            view.addItemDecoration(new DividerItemDecoration(ActivityScoller.this,
                    DividerItemDecoration.VERTICAL));
            TextviewAdapter textviewAdapter = new TextviewAdapter(R.layout.textview, item);
            view.setAdapter(textviewAdapter);
        }
    }


    private class  TextviewAdapter extends BaseQuickAdapter<String,BaseViewHolder>{
        public TextviewAdapter(int layoutResId, List<String> data) {
            super(layoutResId, data);
        }

        @Override
        protected void convert(BaseViewHolder helper, String item) {
            helper.setText(R.id.textview,item);
        }
    }

}
