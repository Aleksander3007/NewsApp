package com.ermakov.newsapp.ui;

import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;

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
        NewsViewPagerAdapter adapter = new NewsViewPagerAdapter(getSupportFragmentManager());
        adapter.addFragment(NewsCategoryFragment.newInstance(NewsSource.CATEGORY_GENERAL), "General");
        adapter.addFragment(NewsCategoryFragment.newInstance(NewsSource.CATEGORY_BUSINESS), "Business");
        adapter.addFragment(NewsCategoryFragment.newInstance(NewsSource.CATEGORY_SPORT), "Sport");
        adapter.addFragment(NewsCategoryFragment.newInstance(NewsSource.CATEGORY_SCIENCE_AND_NATURE), "Science");
        adapter.addFragment(NewsCategoryFragment.newInstance(NewsSource.CATEGORY_TECHNOLOGY), "Technology");
        adapter.addFragment(NewsCategoryFragment.newInstance(NewsSource.CATEGORY_ENTERTAINMENT), "Entertainment");
        adapter.addFragment(NewsCategoryFragment.newInstance(NewsSource.CATEGORY_MUSIC), "Music");
        mNewsViewPager.setAdapter(adapter);
    }

    class NewsViewPagerAdapter extends FragmentPagerAdapter {
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

        public void addFragment(Fragment fragment, String title) {
            mFragmentList.add(fragment);
            mFragmentTitleList.add(title);
        }
    }
}
