package tarce.myodoo.activity;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.RadioButton;

import butterknife.ButterKnife;
import butterknife.InjectView;
import butterknife.OnClick;
import tarce.myodoo.R;
import tarce.myodoo.fragment.Fragment1;
import tarce.myodoo.fragment.Fragment2;
import tarce.myodoo.fragment.Fragment3;
import tarce.myodoo.fragment.Fragment4;
import tarce.myodoo.view.NoScrollViewPager;
import tarce.support.ToolBarActivity;

public class MainActivity extends AppCompatActivity {
    @InjectView(R.id.viewpager)
    NoScrollViewPager mViewPager;
    @InjectView(R.id.radio_button1)
    RadioButton radioButton1;
    @InjectView(R.id.radio_button2)
    RadioButton radioButton2;
    @InjectView(R.id.radio_button3)
    RadioButton radioButton3;
    @InjectView(R.id.radio_button4)
    RadioButton radioButton4;

    private Fragment[] myfragment;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.inject(this);
        initFragment();

    }

    @OnClick(R.id.radio_button1)
    void clickRadio_Button1(View view){
        mViewPager.setCurrentItem(0,false);
    }
    @OnClick(R.id.radio_button2)
    void clickRadio_Button2(View view){
        mViewPager.setCurrentItem(1,true);
    }
    @OnClick(R.id.radio_button3)
    void clickRadio_Button3(View view){
        mViewPager.setCurrentItem(2,false);
    }
    @OnClick(R.id.radio_button4)
    void clickRadio_Button4(View view){
        mViewPager.setCurrentItem(3,true);
    }



    private void initFragment() {
        myfragment = new Fragment[4];
        myfragment[0] = new Fragment1();
        myfragment[1] = new Fragment2();
        myfragment[2] = new Fragment3();
        myfragment[3] = new Fragment4();
        MyViewPagerAdapter myViewPagerAdapter = new MyViewPagerAdapter(getSupportFragmentManager());
        mViewPager.setAdapter(myViewPagerAdapter);
        mViewPager.setCurrentItem(0);
    }

    class MyViewPagerAdapter extends FragmentPagerAdapter {
        private MyViewPagerAdapter(FragmentManager fm) {
            super(fm);
        }

        @Override
        public Fragment getItem(int position) {
            return myfragment[position];
        }

        @Override
        public int getCount() {
            return myfragment.length;
        }

    }
}
