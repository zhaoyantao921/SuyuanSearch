package com.shou.zhao.suyuansearch;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.KeyEvent;
import android.view.View;
import android.view.ViewConfiguration;
import android.widget.Toast;

import com.shou.zhao.suyuansearch.ui.ChangeColorIconWithTextView;
import com.shou.zhao.suyuansearch.ui.FrgInfo;
import com.shou.zhao.suyuansearch.ui.FrgOrder;
import com.shou.zhao.suyuansearch.ui.FrgSerch;
import com.shou.zhao.suyuansearch.ui.FrgTeam;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;

public class MainActivity extends FragmentActivity implements ViewPager.OnPageChangeListener,View.OnClickListener{

    private ViewPager mViewPager;

    // 选项卡一业务介绍
    private FrgInfo frgInfo;
    // 选项卡一企业合作
    private FrgTeam frgTeam;
    //预定
    private FrgOrder frgOrder;
    //查询
    private FrgSerch frgSerch;
    private List<ChangeColorIconWithTextView> mTabIndicator = new ArrayList<ChangeColorIconWithTextView>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        setOverflowShowingAlways();
        mViewPager = (ViewPager) findViewById(R.id.id_viewpager);
        mViewPager.setAdapter(new MyPagerAdapter(getSupportFragmentManager()));
        initTabIndicator();
       // mViewPager.setOnPageChangeListener(this);

    }


    private void initTabIndicator() {
        ChangeColorIconWithTextView info = (ChangeColorIconWithTextView) findViewById(R.id.id_indicator_info);
       ChangeColorIconWithTextView team = (ChangeColorIconWithTextView) findViewById(R.id.id_indicator_team);
        ChangeColorIconWithTextView order = (ChangeColorIconWithTextView) findViewById(R.id.id_indicator_order);
        ChangeColorIconWithTextView search = (ChangeColorIconWithTextView) findViewById(R.id.id_indicator_search);

        mTabIndicator.add(info);
        mTabIndicator.add(search);
        mTabIndicator.add(order);
        mTabIndicator.add(team);

        info.setOnClickListener(this);
        team.setOnClickListener(this);
        order.setOnClickListener(this);
        search.setOnClickListener(this);

        info.setIconAlpha(0.1f);
        team.setIconAlpha(0.1f);
        order.setIconAlpha(0.1f);
        search.setIconAlpha(0.1f);



    }

    @Override
    public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

        if (positionOffset > 0) {
            ChangeColorIconWithTextView left = mTabIndicator.get(position);
            ChangeColorIconWithTextView right = mTabIndicator.get(position + 1);

            left.setIconAlpha(1 - positionOffset);
            right.setIconAlpha(positionOffset);
        }
    }


    @Override
    public void onPageSelected(int position) {

    }

    @Override
    public void onPageScrollStateChanged(int state) {
    }

    @Override
    public void onClick(View v) {
        resetOtherTabs();

        switch (v.getId()) {
            case R.id.id_indicator_info:
                mTabIndicator.get(0).setIconAlpha(0.7f);
                mViewPager.setCurrentItem(0, false);
                break;
            case R.id.id_indicator_search:
                mTabIndicator.get(1).setIconAlpha(0.7f);
                mViewPager.setCurrentItem(1, false);
                break;
            case R.id.id_indicator_order:
                mTabIndicator.get(2).setIconAlpha(0.7f);
                mViewPager.setCurrentItem(2, false);
                break;

            case R.id.id_indicator_team:
                mTabIndicator.get(3).setIconAlpha(0.7f);
                mViewPager.setCurrentItem(3, false);
                break;

        }

    }
    /**
     * 重置其他的Tab
     */
    private void resetOtherTabs() {
        for (int i = 0; i < mTabIndicator.size(); i++) {
            mTabIndicator.get(i).setIconAlpha(0);
        }
    }


    private void setOverflowShowingAlways() {
        try {
            // true if a permanent menu key is present, false otherwise.
            ViewConfiguration config = ViewConfiguration.get(this);
            Field menuKeyField = ViewConfiguration.class.getDeclaredField("sHasPermanentMenuKey");
            menuKeyField.setAccessible(true);
            menuKeyField.setBoolean(config, false);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    public class MyPagerAdapter extends FragmentPagerAdapter {

        public MyPagerAdapter(FragmentManager fm) {
            super(fm);
        }

        private final String[] titles = { "业务介绍", "信息查询","预定", "合作企业"};

        @Override
        public CharSequence getPageTitle(int position) {
            return titles[position];
        }

        @Override
        public int getCount() {
            return titles.length;
        }

        @Override
        public Fragment getItem(int position) {
            switch (position) {
                case 0:
                    if (frgInfo == null) {
                        frgInfo = new FrgInfo();
                    }
                    return frgInfo;
                case 1:
                    if (frgSerch == null) {
                        frgSerch = new FrgSerch();
                    }
                    return frgSerch;
                case 2:
                    if (frgOrder == null) {
                        //要加载所有的销售种类
                        frgOrder = new FrgOrder();

                    }
                    return frgOrder;
                case 3:
                    if (frgTeam == null) {
                        frgTeam= new FrgTeam();
                    }
                    return frgTeam;

                default:
                    return null;
            }
        }
    }

    private long exitTime = 0;
    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if(keyCode == KeyEvent.KEYCODE_BACK && event.getAction() == KeyEvent.ACTION_DOWN){
            if((System.currentTimeMillis()-exitTime) > 2000){
                Toast.makeText(getApplicationContext(), "再按一次退出程序", Toast.LENGTH_SHORT).show();
                exitTime = System.currentTimeMillis();
            } else {
                finish();
                System.exit(0);
            }
            return true;
        }
        return super.onKeyDown(keyCode, event);
    }
}
