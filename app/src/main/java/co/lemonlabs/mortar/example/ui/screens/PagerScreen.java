package co.lemonlabs.mortar.example.ui.screens;

import android.os.Bundle;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;
import javax.inject.Singleton;

import co.lemonlabs.mortar.example.R;
import co.lemonlabs.mortar.example.core.CorePresenter;
import co.lemonlabs.mortar.example.core.android.ActionBarPresenter;
import co.lemonlabs.mortar.example.core.anim.Transition;
import co.lemonlabs.mortar.example.core.anim.Transitions;
import co.lemonlabs.mortar.example.ui.views.PagerView;
import flow.Flow;
import flow.Layout;
import mortar.Blueprint;
import mortar.ViewPresenter;
import rx.functions.Action0;

/**
 * Created by george on 2014/11/3.
 */
@Layout(R.layout.pager)
@Transition({Transitions.NONE, Transitions.NONE, Transitions.NONE, Transitions.NONE})
public class PagerScreen implements Blueprint {

    @Override
    public String getMortarScopeName() {
        return getClass().getName();
    }

    @Override
    public Object getDaggerModule() {
        return new Module();
    }

    @dagger.Module(
        injects = PagerView.class,
        addsTo = CorePresenter.Module.class,
        library = true
    )
    public static class Module {

        public Module() { }

    }

    @Singleton
    public static class Presenter extends ViewPresenter<PagerView> {

        private final Flow mFlow;
        private final ActionBarPresenter mActionBarPresenter;

        @Inject
        Presenter(Flow flow, ActionBarPresenter actionBarPresenter) {
            mFlow = flow;
            mActionBarPresenter = actionBarPresenter;
        }

        @Override
        protected void onLoad(Bundle savedInstanceState) {
            super.onLoad(savedInstanceState);

            if (getView() == null) {
                return;
            }

            // Add MenuActions to Action Bar
            List<ActionBarPresenter.MenuAction> actions = new ArrayList<>();
            actions.add(new ActionBarPresenter.MenuAction("YOLO", new Action0() {
                @Override
                public void call() {
                    if (getView() != null) {
                    }
                }
            }));
            mActionBarPresenter.setConfig(new ActionBarPresenter.Config(
                true,
                false,
                "Pager",
                actions
            ));
        }
    }
}
