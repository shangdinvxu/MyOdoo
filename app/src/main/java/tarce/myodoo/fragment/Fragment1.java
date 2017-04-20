package tarce.myodoo.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import butterknife.ButterKnife;
import butterknife.InjectView;
import butterknife.OnClick;
import tarce.myodoo.IntentFactory;
import tarce.myodoo.R;

/**
 * Created by Daniel.Xu on 2017/4/20.
 */

public class Fragment1 extends Fragment {
    @InjectView(R.id.sales_out)
    Button salesOut;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_1, null);
        ButterKnife.inject(this, view);
        return view;
    }

    @OnClick(R.id.sales_out)
    void setSalesOut(View view){
        IntentFactory.start_SalesOut_Activity(getActivity());
    }


    @Override
    public void onDestroyView() {
        super.onDestroyView();
        ButterKnife.reset(this);
    }
}
