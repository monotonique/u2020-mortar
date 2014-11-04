package co.lemonlabs.mortar.example.ui.views;

import android.content.Context;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.support.v7.app.ActionBar;
import android.support.v7.app.ActionBarActivity;
import android.util.AttributeSet;
import android.widget.Button;
import android.widget.FrameLayout;

import javax.inject.Inject;

import butterknife.ButterKnife;
import butterknife.InjectView;
import butterknife.OnPageChange;
import co.lemonlabs.mortar.example.R;
import co.lemonlabs.mortar.example.core.android.ActivityPresenter;
import co.lemonlabs.mortar.example.ui.misc.ScreenPagerAdapter;
import co.lemonlabs.mortar.example.ui.misc.SectionPagerAdapter;
import co.lemonlabs.mortar.example.ui.misc.TabsAdapter;
import co.lemonlabs.mortar.example.ui.screens.EntryScreen;
import co.lemonlabs.mortar.example.ui.screens.LobbyScreen;
import co.lemonlabs.mortar.example.ui.screens.NestedScreen;
import co.lemonlabs.mortar.example.ui.screens.PagerScreen;
import mortar.Blueprint;
import mortar.Mortar;
import timber.log.Timber;

/**
 * Created by george on 2014/11/3.
 */
public class PagerView extends FrameLayout {


    @Inject PagerScreen.Presenter mPresenter;

    @Inject ActivityPresenter mActivity;

    @InjectView(R.id.view_pager) ViewPager mViewPager;

    ScreenPagerAdapter<Blueprint> mScreenPagerAdapter;

    Blueprint[] blueprints = new Blueprint[] { new EntryScreen(), new LobbyScreen(), new NestedScreen() };
//    SectionPagerAdapter mSectionPagerAdapter;
//    private TabsAdapter mTabsAdapter;
//    private ActionBar mActionBar;

    public PagerView(Context context, AttributeSet attrs) {
        super(context, attrs);
        Mortar.inject(context, this);
    }

    @Override
    protected void onFinishInflate() {
        super.onFinishInflate();
        ButterKnife.inject(this);
        mPresenter.takeView(this);

//        mActionBar = ((ActionBarActivity) mActivity.getActivity()).getSupportActionBar();
//        if (mActionBar != null) {
//            mActionBar.setNavigationMode(ActionBar.NAVIGATION_MODE_TABS);
//        }
//
//        mTabsAdapter = new TabsAdapter((ActionBarActivity) mActivity.getActivity(), mViewPager);
//        mSectionPagerAdapter = new SectionPagerAdapter(mActivity.getActivity().getSupportFragmentManager());
        mScreenPagerAdapter = new ScreenPagerAdapter<>(mActivity.getActivity());
        mScreenPagerAdapter.addScreen(blueprints);
//        mViewPager.setAdapter(mSectionPagerAdapter);
        mViewPager.setAdapter(mScreenPagerAdapter);

//        mTabsAdapter.addTab(mActionBar.newTab().setText("A"), Fragment.class, null);
//        mTabsAdapter.addTab(mActionBar.newTab().setText("B"), Fragment.class, null);
//        mTabsAdapter.addTab(mActionBar.newTab().setText("C"), Fragment.class, null);
//        mTabsAdapter.updateTabs();
    }

    @OnPageChange(R.id.view_pager)
    public void onPageChange(int i) {
        // refresh the menu
//        mPresenter.refreshMenu();
        Timber.d("page " + i + ", name " + mScreenPagerAdapter.getItem(i).getMortarScopeName());
    }

    @Override
    protected void onDetachedFromWindow() {
        super.onDetachedFromWindow();
        mPresenter.dropView(this);
    }

}
