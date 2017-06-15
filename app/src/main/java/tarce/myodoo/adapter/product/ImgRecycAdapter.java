package tarce.myodoo.adapter.product;

import android.graphics.BitmapFactory;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;

import java.util.List;

import tarce.myodoo.R;

/**
 * Created by rose.zou on 2017/6/8.
 * 图片列表
 */

public class ImgRecycAdapter extends BaseQuickAdapter<String, BaseViewHolder>{
    public ImgRecycAdapter(int layoutResId, List<String> data) {
        super(layoutResId, data);
    }

    @Override
    protected void convert(BaseViewHolder helper, String item) {
        helper.setImageBitmap(R.id.recycler_img_photo, BitmapFactory.decodeFile(item));
    }
}
