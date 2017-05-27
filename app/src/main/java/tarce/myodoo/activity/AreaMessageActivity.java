package tarce.myodoo.activity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;

import butterknife.ButterKnife;
import butterknife.InjectView;
import tarce.myodoo.R;
import tarce.support.ToolBarActivity;

/**
 * Created by rose.zou on 2017/5/26.
 */

public class AreaMessageActivity extends ToolBarActivity {

    @InjectView(R.id.image_show_area)
    ImageView imageShowArea;
    @InjectView(R.id.tv_string_area_message)
    TextView tvStringAreaMessage;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_area_message);
        ButterKnife.inject(this);
        setTitle("物料位置信息");

        Intent intent = getIntent();
        String img_area = intent.getStringExtra("img_area");
        //String string_area = intent.getStringExtra("string_area");
        Glide.with(AreaMessageActivity.this).load(img_area).into(imageShowArea);
      //  tvStringAreaMessage.setText(string_area);
    }
}
