package tarce.myodoo.adapter.product;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;

import java.util.List;

import tarce.myodoo.R;
import tarce.myodoo.bean.MenuBean;

/**
 * Created by rose.zou on 2017/6/9.
 *
 */

public class ProduceRednumAdapter extends BaseQuickAdapter<MenuBean, BaseViewHolder>{

    public ProduceRednumAdapter(int layoutResId, List<MenuBean> data) {
        super(layoutResId, data);
    }

    @Override
    protected void convert(BaseViewHolder helper, MenuBean item) {
        helper.setText(R.id.text,item.getName());
        if (item.getNumber()>0){
            helper.setText(R.id.number,item.getNumber()+"");
            helper.setVisible(R.id.number,true);
        }else {
            helper.setVisible(R.id.number,false);
        }
    }

}
