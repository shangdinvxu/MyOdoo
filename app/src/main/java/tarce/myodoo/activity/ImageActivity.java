package tarce.myodoo.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.chad.library.adapter.base.BaseQuickAdapter;

import java.util.ArrayList;
import java.util.List;

import butterknife.ButterKnife;
import butterknife.InjectView;
import cn.bingoogolapple.bgabanner.BGABanner;
import tarce.model.ImageBean;
import tarce.model.inventory.TakeDelListBean;
import tarce.myodoo.R;
import tarce.myodoo.adapter.ImageAdapter;
import tarce.support.ToastUtils;

/**
 * Created by zouwansheng on 2018/1/30.
 */

public class ImageActivity extends BaseActivity {
    @InjectView(R.id.recycler_image)
    RecyclerView recyclerImage;
    @InjectView(R.id.banner_guide_content)
    BGABanner bannerGuideContent;
    private LinearLayoutManager linearLayoutManager;
    private ImageAdapter imageAdapter;
    private List<ImageBean> imageBeanList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_image);
        ButterKnife.inject(this);

        linearLayoutManager = new LinearLayoutManager(ImageActivity.this);
        linearLayoutManager.setOrientation(LinearLayoutManager.HORIZONTAL);
        recyclerImage.setLayoutManager(linearLayoutManager);
        recyclerImage.addItemDecoration(new DividerItemDecoration(ImageActivity.this,
                DividerItemDecoration.HORIZONTAL));

        setTitle("产品图片");
        Intent intent = getIntent();
        imageBeanList = (List<ImageBean>) intent.getSerializableExtra("imageList");
        imageAdapter = new ImageAdapter(ImageActivity.this, R.layout.item_image, imageBeanList);
        recyclerImage.setAdapter(imageAdapter);
        imageAdapter.setOnItemClickListener(new BaseQuickAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(BaseQuickAdapter adapter, View view, int position) {
                bannerGuideContent.setCurrentItem(position);
            }
        });
//        if (imageBeanList!=null)
//        if (imageBeanList.size()==1){
//            bannerGuideContent.setAllowUserScrollable(false);
//        }else {
//            bannerGuideContent.setAllowUserScrollable(true);
//        }
        bannerGuideContent.setAdapter(new BGABanner.Adapter<ImageView, String>() {
            @Override
            public void fillBannerItem(BGABanner banner, ImageView itemView, String model, int position) {
                itemView.setScaleType(ImageView.ScaleType.FIT_XY);
                Glide.with(ImageActivity.this)
                        .load(model)
//                        .placeholder(R.drawable.warning)
                        .error(R.drawable.warning)
                        .dontAnimate()
                        .into(itemView);
            }
        });
        if (imageBeanList==null){
            ToastUtils.showCommonToast(ImageActivity.this, "没有相关图片");
            return;
        }else {
            List<String> imageList = new ArrayList<>();
            List<String> tipList = new ArrayList<>();
            for (int i = 0; i <imageBeanList.size(); i++) {
                imageList.add(imageBeanList.get(i).getImage_url());
                tipList.add("");
            }
            bannerGuideContent.setData(imageList, tipList);
        }
    }
}
