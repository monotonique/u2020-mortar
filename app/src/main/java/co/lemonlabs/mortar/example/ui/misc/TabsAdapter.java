package co.lemonlabs.mortar.example.ui.misc;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v4.view.ViewPager.OnPageChangeListener;
import android.support.v7.app.ActionBar;
import android.support.v7.app.ActionBarActivity;

import java.util.ArrayList;

/**
 * This class handles the forum pages in ForumReaderActivity
 *
 * @author George Lin
 */
public class TabsAdapter extends FragmentPagerAdapter
        implements ActionBar.TabListener, OnPageChangeListener {

    private final Context mContext;
    private final ActionBar mActionBar;
    private final ViewPager mViewPager;
    private final ArrayList<TabInfo> mTabs = new ArrayList<TabInfo>();

    public TabsAdapter(ActionBarActivity activity, ViewPager pager) {
        super(activity.getSupportFragmentManager());
        mContext = activity;
        mActionBar = activity.getSupportActionBar();
        mViewPager = pager;
        mViewPager.setAdapter(this);
        mViewPager.setOnPageChangeListener(this);
    }

    @Override
    public void onTabSelected(ActionBar.Tab tab, android.support.v4.app.FragmentTransaction ft) {
        // get forum posts list (the first page)
        // and update the tab content here
        mViewPager.setCurrentItem(tab.getPosition());
    }

    @Override
    public void onTabUnselected(ActionBar.Tab tab, android.support.v4.app.FragmentTransaction ft) {
        // do nothing intended
    }

    @Override
    public void onTabReselected(ActionBar.Tab tab, android.support.v4.app.FragmentTransaction ft) {
        // do nothing intended
    }

    /**
     * This adds a tab to the action bar.
     *
     * @param tab  the tab to be added.
     * @param clss the class to be added.
     * @param args the attached arguments.
     */
    public void addTab(ActionBar.Tab tab, Class<?> clss, Bundle args) {
        TabInfo info = new TabInfo(clss, args);
        tab.setTag(info);
        tab.setTabListener(this);
        mTabs.add(info);
        mActionBar.addTab(tab);
    }

    /**
     * This calls notifyDataSetChanged()
     */
    public void updateTabs() {
        notifyDataSetChanged();
    }

    @Override
    public void onPageScrollStateChanged(int state) {
        // do nothing intended
    }

    @Override
    public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
        // do nothing intended
    }

    @Override
    public void onPageSelected(int position) {
        mActionBar.setSelectedNavigationItem(position);
    }

    @Override
    public Fragment getItem(int position) {
        TabInfo info = mTabs.get(position);
        return Fragment.instantiate(mContext, info.mClss.getName(), info.mArgs);
    }

    @Override
    public int getCount() {
        return mTabs.size();
    }

    static final class TabInfo {

        private final Class<?> mClss;
        private final Bundle mArgs;

        TabInfo(Class<?> clss, Bundle args) {
            mClss = clss;
            mArgs = args;
        }
    }

}
