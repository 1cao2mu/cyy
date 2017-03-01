package com.hhstu.cyy.cyy.activity;

import android.os.Bundle;
import android.os.Parcelable;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import com.hhstu.cyy.cyy.R;
import com.hhstu.cyy.cyy.base.BaseAppCompatActivity;
import com.hhstu.cyy.cyy.fragment.Tab1Fragment;
import com.hhstu.cyy.cyy.fragment.Tab2Fragment;
import com.hhstu.cyy.cyy.fragment.Tab3Fragment;
import com.hhstu.cyy.cyy.utils.Utils;

import java.util.ArrayList;
import java.util.List;

public class TabLayoutActivity extends BaseAppCompatActivity {

    private TabLayout tlContent;
    private LinearLayout activityTabLayout;
    private ViewPager vpContent;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tab_layout);
        initView();
    }

    private void initView() {
        tlContent = (TabLayout) findViewById(R.id.tl_content);
        activityTabLayout = (LinearLayout) findViewById(R.id.activity_tab_layout);
        vpContent = (ViewPager) findViewById(R.id.vp_content);
        tlContent.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                Utils.showToast(getContext(), tab.getPosition() + "");
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {

            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {

            }
        });
        MyPagerAdapter myPagerAdapter = new MyPagerAdapter(getSupportFragmentManager());
        tlContent.setupWithViewPager(vpContent);
//        tlContent.addView(LayoutInflater.from(getActivity()).inflate(R.layout.item_tab_layout, null));
    }


    public class MyPagerAdapter extends FragmentStatePagerAdapter {
        List<Fragment> data = new ArrayList<>();
        private List<String> titleList = new ArrayList<>();

        public MyPagerAdapter(FragmentManager fm) {
            super(fm);
            data.add(new Tab1Fragment());
            data.add(new Tab2Fragment());
            data.add(new Tab3Fragment());
            titleList.add("全部");
            titleList.add("收款");
            titleList.add("提现");
        }

        @Override
        public void destroyItem(ViewGroup container, int position, Object object) {
            super.destroyItem(container, position, object);
        }

        @Override
        public CharSequence getPageTitle(int position) {
            return titleList.get(position);
        }

        public void refresh(List<Fragment> list, List<String> title) {
            this.data = list;
            this.notifyDataSetChanged();
        }

        public int getItemPosition(Object object) {
            return POSITION_NONE;
        }

        @Override
        public Fragment getItem(int position) {
            return this.data.get(position);
        }

        @Override
        public int getCount() {
            return this.data.size();
        }

        @Override
        public Parcelable saveState() {
            return null;
        }
    }

}
