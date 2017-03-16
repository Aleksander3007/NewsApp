package com.ermakov.newsapp.ui;

import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.widget.Toast;

import com.ermakov.newsapp.NewsSource;
import com.ermakov.newsapp.R;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class MainActivity extends AppCompatActivity {

    @BindView(R.id.toolbar) Toolbar mToolbar;
    @BindView(R.id.view_pager) ViewPager mNewsViewPager;
    @BindView(R.id.tab_layout) TabLayout mTabLayout;

    private NewsViewPagerAdapter mNewsViewPagerAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);

        setSupportActionBar(mToolbar);
        setupViewPager();
        mTabLayout.setupWithViewPager(mNewsViewPager);
    }

    private void setupViewPager() {
        mNewsViewPagerAdapter = new NewsViewPagerAdapter(getSupportFragmentManager());
        mNewsViewPagerAdapter.addFragment(NewsCategoryFragment.newInstance(NewsSource.CATEGORY_GENERAL), "General");
        mNewsViewPagerAdapter.addFragment(NewsCategoryFragment.newInstance(NewsSource.CATEGORY_BUSINESS), "Business");
        mNewsViewPagerAdapter.addFragment(NewsCategoryFragment.newInstance(NewsSource.CATEGORY_SPORT), "Sport");
        mNewsViewPagerAdapter.addFragment(NewsCategoryFragment.newInstance(NewsSource.CATEGORY_SCIENCE_AND_NATURE), "Science");
        mNewsViewPagerAdapter.addFragment(NewsCategoryFragment.newInstance(NewsSource.CATEGORY_TECHNOLOGY), "Technology");
        mNewsViewPagerAdapter.addFragment(NewsCategoryFragment.newInstance(NewsSource.CATEGORY_ENTERTAINMENT), "Entertainment");
        mNewsViewPagerAdapter.addFragment(NewsCategoryFragment.newInstance(NewsSource.CATEGORY_MUSIC), "Music");
        mNewsViewPager.setAdapter(mNewsViewPagerAdapter);
    }

    class NewsViewPagerAdapter extends FragmentStatePagerAdapter {
        private final List<Fragment> mFragmentList = new ArrayList<>();
        private final List<String> mFragmentTitleList = new ArrayList<>();

        public NewsViewPagerAdapter(FragmentManager fm) {
            super(fm);
        }

        @Override
        public Fragment getItem(int position) {
            return mFragmentList.get(position);
        }

        @Override
        public int getCount() {
            return mFragmentList.size();
        }

        @Override
        public CharSequence getPageTitle(int position) {
            return mFragmentTitleList.get(position);
        }

        @Override
        public int getItemPosition(Object object) {
            return super.getItemPosition(object);
        }

        public void addFragment(Fragment fragment, String title) {
            mFragmentList.add(fragment);
            mFragmentTitleList.add(title);
        }

        public void replaceFragment(int fragmentPosition, Fragment newFragment) {
            mFragmentList.set(fragmentPosition, newFragment);
            notifyDataSetChanged();
        }
    }
}
