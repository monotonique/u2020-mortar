package co.lemonlabs.mortar.example.ui.misc;

import android.content.Context;
import android.support.v4.view.PagerAdapter;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;
import java.util.List;

import flow.Layouts;
import mortar.Blueprint;
import mortar.Mortar;
import mortar.MortarScope;

public class ScreenPagerAdapter<S extends Blueprint> extends PagerAdapter
{
    private final Context mContext;
    private final List<S> screens;

    public ScreenPagerAdapter(Context context)
    {
        mContext = context;
        this.screens = new ArrayList<S>();
    }

    public void addScreen(S... newScreens)
    {
        for (S newScreen : newScreens)
        {
            screens.add(newScreen);
        }
        notifyDataSetChanged();
    }

    protected Context getContext()
    {
        return mContext;
    }

    @Override
    public Object instantiateItem(ViewGroup container, int position)
    {
        S screen = screens.get(position);
        MortarScope myScope = Mortar.getScope(mContext);
        MortarScope newChildScope = myScope.requireChild(screen);
        Context childContext = newChildScope.createContext(mContext);
        View newChild = Layouts.createView(childContext, screen);
        container.addView(newChild);
        return newChild;
    }

    @Override
    public void destroyItem(ViewGroup container, int position, Object object)
    {
        View view = ((View) object);
        MortarScope myScope = Mortar.getScope(mContext);
        MortarScope childScope = Mortar.getScope(view.getContext());
        container.removeView(view);
        myScope.destroyChild(childScope);
    }

    @Override
    public int getCount()
    {
        return screens.size();
    }

    public final S getItem(int position)
    {
        return screens.get(position);
    }

    @Override
    public CharSequence getPageTitle(int position) {
        return "page" + position;
    }

    @Override
    public boolean isViewFromObject(View view, Object object)
    {
        return view.equals(object);
    }
}