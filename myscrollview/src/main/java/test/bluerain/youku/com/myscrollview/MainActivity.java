package test.bluerain.youku.com.myscrollview;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;

import java.util.ArrayList;
import java.util.List;

import test.bluerain.youku.com.myscrollview.fragment.MyScrollFragment;
import test.bluerain.youku.com.myscrollview.fragment.SystemScrollFragment;

public class MainActivity extends AppCompatActivity {

    private List<Fragment> mFragments;
    private ViewPager mViewPager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initVaryables();
        initViews();

    }

    private void initVaryables() {
        mFragments = new ArrayList<>();
        mFragments.add(new MyScrollFragment());
        mFragments.add(new SystemScrollFragment());
    }

    private void initViews() {
        mViewPager = (ViewPager) findViewById(R.id.vp_main);
        mViewPager.setAdapter(new FragmentPagerAdapter(getSupportFragmentManager()) {
            @Override
            public Fragment getItem(int position) {
                return mFragments.get(position);
            }

            @Override
            public int getCount() {
                return mFragments.size();
            }
        });
    }
}
