package tarce.myodoo.activity.engineer;

import android.os.Bundle;
import android.support.annotation.IdRes;
import android.support.v7.widget.RecyclerView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.SearchView;

import butterknife.ButterKnife;
import butterknife.InjectView;
import tarce.myodoo.R;
import tarce.myodoo.activity.BaseActivity;

/**
 * Created by zouzou on 2017/8/28.
 */

public class ProjectActivity extends BaseActivity {

    @InjectView(R.id.wait_radio)
    RadioButton waitRadio;
    @InjectView(R.id.can_radio)
    RadioButton canRadio;
    @InjectView(R.id.done_radio)
    RadioButton doneRadio;
    @InjectView(R.id.radioproject)
    RadioGroup radioproject;
    @InjectView(R.id.search_name)
    SearchView searchName;
    @InjectView(R.id.search_num)
    SearchView searchNum;
    @InjectView(R.id.recycler_project)
    RecyclerView recyclerProject;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_project);
        ButterKnife.inject(this);
        setTitle("物流发料");

        radioproject.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup radioGroup, @IdRes int i) {
                int checkedRadioButtonId = radioGroup.getCheckedRadioButtonId();
                if (checkedRadioButtonId == R.id.wait_radio){

                }else if (checkedRadioButtonId == R.id.can_radio){

                }else if (checkedRadioButtonId == R.id.done_radio){

                }
            }
        });
    }
}
