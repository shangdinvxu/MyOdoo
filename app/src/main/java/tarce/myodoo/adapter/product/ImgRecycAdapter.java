package tarce.myodoo.adapter.product;

import android.content.Context;
import android.graphics.BitmapFactory;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.model.GlideUrl;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;

import java.util.List;

import tarce.myodoo.R;
import tarce.support.ToastUtils;

/**
 * Created by rose.zou on 2017/6/8.
 * 图片列表
 */

public class ImgRecycAdapter extends BaseQuickAdapter<String, BaseViewHolder>{
    private Context context;
    public ImgRecycAdapter(Context context, int layoutResId, List<String> data) {
        super(layoutResId, data);
        this.context = context;
    }

    @Override
    protected void convert(BaseViewHolder helper, String item) {
        if (item.contains("http")){
            Glide.with(context).load(item).into((ImageView) helper.getView(R.id.recycler_img_photo));
        }else {
            helper.setImageBitmap(R.id.recycler_img_photo, BitmapFactory.decodeFile(item));
        }
    }
}
