package co.lemonlabs.mortar.example.ui.misc;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import co.lemonlabs.mortar.example.ui.views.FirstFragment;
import co.lemonlabs.mortar.example.ui.views.SecondFragment;

/**
 * Created by george on 2014/11/4.
 */
public class SectionPagerAdapter extends FragmentPagerAdapter {

    public SectionPagerAdapter(FragmentManager fm) {
        super(fm);
    }

    @Override
    public Fragment getItem(int i) {
        Fragment fragment = null;

        switch (i) {
            case 0:
                fragment = new FirstFragment();
                break;
            case 1:
                fragment = new SecondFragment();
                break;
        }
        return fragment;
    }

    @Override
    public int getCount() {
        return 2;
    }
}
