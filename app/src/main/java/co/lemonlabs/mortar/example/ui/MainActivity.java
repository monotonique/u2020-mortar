package co.lemonlabs.mortar.example.ui;

import android.app.ActionBar;
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.ViewGroup;

import java.util.List;
import java.util.UUID;

import javax.inject.Inject;

import butterknife.ButterKnife;
import co.lemonlabs.mortar.example.R;
import co.lemonlabs.mortar.example.U2020App;
import co.lemonlabs.mortar.example.core.CorePresenter;
import co.lemonlabs.mortar.example.core.CoreView;
import co.lemonlabs.mortar.example.core.android.ActionBarPresenter;
import flow.Flow;
import mortar.Mortar;
import mortar.MortarActivityScope;
import mortar.MortarScope;

import static android.content.Intent.ACTION_MAIN;
import static android.content.Intent.CATEGORY_LAUNCHER;

public class MainActivity extends Activity implements ActionBarPresenter.View/*, DrawerPresenter.View*/ {

    @Inject
    ActionBarPresenter actionBarPresenter;
//    @Inject
//    DrawerPresenter drawerPresenter;
    @Inject
    AppContainer appContainer;

    private List<ActionBarPresenter.MenuAction> actionBarMenuActions;
    private MortarActivityScope activityScope;
    private CoreView coreView;
    private Flow flow;
//    private ActionBarDrawerToggle drawerToggle;

    private boolean configurationChangeIncoming;
    private String scopeName;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        if (isWrongInstance()) {
            finish();
            return;
        }

        MortarScope parentScope = Mortar.getScope(getApplication());
        activityScope = Mortar.requireActivityScope(parentScope, new CorePresenter(getScopeName()));
        activityScope.onCreate(savedInstanceState);

        Mortar.inject(this, this);

        actionBarPresenter.takeView(this);

        ViewGroup container = appContainer.get(this, U2020App.get(this)); // (Activity, Application)

        getLayoutInflater().inflate(R.layout.core, container);
        coreView = ButterKnife.findById(this, R.id.core_layout);

        flow = coreView.getFlow();

//        drawerToggle = coreView.getDrawerToggle();
//        drawerPresenter.takeView(this);
    }

    @Override
    public Object getSystemService(String name) {
        if (Mortar.isScopeSystemService(name)) {
            return activityScope;
        }
        return super.getSystemService(name);
    }

    @Override
    public Object onRetainNonConfigurationInstance() {
        configurationChangeIncoming = true;
        return activityScope.getName();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (actionBarPresenter != null) actionBarPresenter.dropView(this);
//        if (drawerPresenter != null) drawerPresenter.dropView(this);
        if (!configurationChangeIncoming) {
            if (!activityScope.isDestroyed()) {
                MortarScope parentScope = Mortar.getScope(getApplication());
                parentScope.destroyChild(activityScope);
            }
            activityScope = null;
        }
    }

    private String getScopeName() {
        if (scopeName == null) scopeName = (String) getLastNonConfigurationInstance();
        if (scopeName == null) {
            scopeName = getClass().getName() + "-" + UUID.randomUUID().toString();
        }
        return scopeName;
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        if (actionBarMenuActions != null) {
            for (final ActionBarPresenter.MenuAction actionBarMenuAction : actionBarMenuActions) {
                MenuItem menuItem = menu.add(actionBarMenuAction.title)
                        .setOnMenuItemClickListener(new MenuItem.OnMenuItemClickListener() {
                            @Override
                            public boolean onMenuItemClick(MenuItem item) {
                                actionBarMenuAction.action.call();
                                return true;
                            }
                        });
                if (actionBarMenuAction.icon >= 0) {
                    menuItem.setIcon(actionBarMenuAction.icon)
                            .setShowAsActionFlags(MenuItem.SHOW_AS_ACTION_IF_ROOM);

                } else {
                    menuItem.setShowAsActionFlags(MenuItem.SHOW_AS_ACTION_COLLAPSE_ACTION_VIEW);
                }

            }
        }
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
//        if (drawerToggle.onOptionsItemSelected(item)) {
//            return true;
//        }
        if (item.getItemId() == android.R.id.home) {
            return flow.goBack();
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        activityScope.onSaveInstanceState(outState);
    }

    @Override
    public void onBackPressed() {
        if (flow.goBack()) {
            return;
        }
        super.onBackPressed();
    }

    // ActionBarDrawerToggle:This class provides a handy way to tie together the functionality of
    // DrawerLayout and the framework ActionBar to implement the recommended design for navigation drawers.
    // This shall be put in onConfigurationChanged
//    @Override
//    public void onConfigurationChanged(Configuration newConfig) {
//        super.onConfigurationChanged(newConfig);
//        drawerToggle.onConfigurationChanged(newConfig);
//    }

    // Implements DrawerPresenter.View's methods
    @Override
    public MortarScope getMortarScope() {
        return activityScope;
    }

    // If true, home icon shows the drawer.
    // If false, home icon goes up.
//    @Override
//    public void setDrawerIndicatorEnabled(boolean enabled) {
//        drawerToggle.setDrawerIndicatorEnabled(enabled);
//    }

//    @Override
//    public void setDrawerLockMode(int lockMode) {
//        coreView.setDrawerLockMode(lockMode);
//    }

    // Implements ActionBarPresenter.View's methods
    @Override
    public void setShowHomeEnabled(boolean enabled) {
        ActionBar actionBar = getActionBar();
        actionBar.setDisplayShowHomeEnabled(enabled);
    }

    @Override
    public void setUpButtonEnabled(boolean enabled) {
        ActionBar actionBar = getActionBar();
        actionBar.setDisplayHomeAsUpEnabled(enabled);
        actionBar.setHomeButtonEnabled(enabled);
    }

    @Override
    public void setMenu(List<ActionBarPresenter.MenuAction> actions) {
        if (actions != null && !actions.equals(actionBarMenuActions)) {
            actionBarMenuActions = actions;
            invalidateOptionsMenu();
        }
    }

//    @Override
//    protected void onPostCreate(Bundle savedInstanceState) {
//        super.onPostCreate(savedInstanceState);
//        drawerToggle.syncState();
//    }

    private boolean isWrongInstance() {
        if (!isTaskRoot()) {
            Intent intent = getIntent();
            boolean isMainAction = intent.getAction() != null && intent.getAction().equals(ACTION_MAIN);
            return intent.hasCategory(CATEGORY_LAUNCHER) && isMainAction;
        }
        return false;
    }

}
