package tarce.myodoo.adapter;

import android.content.Context;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;


import java.util.List;

import tarce.model.ImageBean;
import tarce.myodoo.R;

/**
 * Created by zouwansheng on 2018/1/30.
 */

public class ImageAdapter extends BaseQuickAdapter<ImageBean, BaseViewHolder> {
    private Context mContext;
    public ImageAdapter(Context mContext,int layoutResId, List<ImageBean> data) {
        super(layoutResId, data);
        this.mContext = mContext;
    }

    @Override
    protected void convert(BaseViewHolder helper, ImageBean item) {
        Glide.with(mContext).load(item.getImage_url()).into((ImageView) helper.getView(R.id.image_item));
    }
}
