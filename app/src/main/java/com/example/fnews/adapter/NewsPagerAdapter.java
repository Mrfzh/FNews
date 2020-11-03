package com.example.fnews.adapter;

import java.util.List;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentStatePagerAdapter;

/**
 * @author fengzhaohao
 * @date 2020/11/2
 */
public class NewsPagerAdapter extends FragmentStatePagerAdapter {

    private List<Fragment> mFragmentList;   //碎片集合
    private List<String> mPageTitleList;    //tab的标题

    public NewsPagerAdapter(FragmentManager fm, List<Fragment> mFragmentList, List<String> mPageTitleList) {
        super(fm);
        this.mFragmentList = mFragmentList;
        this.mPageTitleList = mPageTitleList;
    }

    @Override public Fragment getItem(int position) {
        return mFragmentList.get(position);
    }

    @Override public int getCount() {
        return mFragmentList.size();
    }

    /**
     * 当TabLayout与ViewPager绑定的时候能够绑定Tab标签的标题
     * @param position
     * @return
     */
    @Override
    public CharSequence getPageTitle(int position) {
        return mPageTitleList.get(position);
    }
}
