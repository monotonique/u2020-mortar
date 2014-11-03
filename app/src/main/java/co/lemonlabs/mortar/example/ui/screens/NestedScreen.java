package co.lemonlabs.mortar.example.ui.screens;

import android.os.Bundle;
import android.support.v4.widget.DrawerLayout;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;
import javax.inject.Singleton;

import co.lemonlabs.mortar.example.R;
import co.lemonlabs.mortar.example.core.CorePresenter;
import co.lemonlabs.mortar.example.core.android.ActionBarPresenter;
import co.lemonlabs.mortar.example.core.android.DrawerPresenter;
import co.lemonlabs.mortar.example.core.anim.Transition;
import co.lemonlabs.mortar.example.core.anim.Transitions;
import co.lemonlabs.mortar.example.ui.views.NestedChildView;
import co.lemonlabs.mortar.example.ui.views.NestedView;
import flow.Layout;
import mortar.Blueprint;
import mortar.ViewPresenter;
import rx.functions.Action0;

@Layout(R.layout.nested)
@Transition({Transitions.NONE, Transitions.NONE, Transitions.NONE, Transitions.NONE})
public class NestedScreen implements Blueprint {

    @Override
    public String getMortarScopeName() {
        return getClass().getName();
    }

    @Override
    public Object getDaggerModule() {
        return new Module();
    }

    @dagger.Module(
        injects = {
            NestedView.class,
            NestedChildView.class
        },
        addsTo = CorePresenter.Module.class,
        library = true
    )

    public static class Module {

        public Module() {}
    }

    @Singleton
    public static class Presenter extends ViewPresenter<NestedView> {

        @Inject ChildPresenter childPresenter;

        private final ActionBarPresenter actionBar;
        private final DrawerPresenter drawer;

        @Inject Presenter(ActionBarPresenter actionBar, DrawerPresenter drawer) {
            this.actionBar = actionBar;
            this.drawer = drawer;
        }

        @Override
        public void onLoad(Bundle savedInstanceState) {
            super.onLoad(savedInstanceState);
            if (getView() == null) return;

            List<ActionBarPresenter.MenuAction> actions = new ArrayList<>();
            actions.add(new ActionBarPresenter.MenuAction("Animate", new Action0() {
                @Override public void call() {
                    if (getView() != null) {
                        toggleChildAnimation();
                    }
                }
            }));
            actionBar.setConfig(new ActionBarPresenter.Config(
                    true,
                    true,
                    "Nested Presenters",
                    actions
            ));
            drawer.setConfig(new DrawerPresenter.Config(true, DrawerLayout.LOCK_MODE_UNLOCKED));


//            actionBar.setConfig(new ActionBarPresenter.Config(
//                true,
//                true,
//                "Nested Presenters",
//                new ("Animate", new Action0() {
//                    @Override public void call() {
//                        if (getView() != null) {
//                            toggleChildAnimation();
//                        }
//                    }
//                })
//            ));
        }

        public void toggleChildAnimation() {
            childPresenter.toggleAnimation();
        }

        public ChildPresenter getChildPresenter() {
            return childPresenter;
        }
    }

    @Singleton
    public static class ChildPresenter extends ViewPresenter<NestedChildView> {

        @Inject ChildPresenter() {
        }

        @Override
        public void onLoad(Bundle savedInstanceState) {
            super.onLoad(savedInstanceState);
            if (getView() == null) return;
            toggleAnimation();
        }

        public void toggleAnimation() {
            getView().toggleAnimation();
        }
    }
}
