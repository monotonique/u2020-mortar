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
import co.lemonlabs.mortar.example.ui.views.EntryView;
import flow.Layout;
import mortar.Blueprint;
import mortar.ViewPresenter;
import rx.functions.Action0;

/**
 * Created by george on 2014/11/3.
 */
@Layout(R.layout.entry)
@Transition({Transitions.NONE, Transitions.NONE, Transitions.NONE, Transitions.NONE})
public class EntryScreen implements Blueprint {

    @Override
    public String getMortarScopeName() {
        return getClass().getName();
    }

    @Override
    public Object getDaggerModule() {
        return new Module();
    }

    @dagger.Module(
        injects = EntryView.class,
        addsTo = CorePresenter.Module.class,
        library = true
    )
    public static class Module {

        public Module() {
        }

        ;
    }

    @Singleton
    public static class Presenter extends ViewPresenter<EntryView> {

        private final ActionBarPresenter mActionBarPresenter;

        @Inject
        Presenter(ActionBarPresenter actionBarPresenter) {
            mActionBarPresenter = actionBarPresenter;
        }

        @Override
        protected void onLoad(Bundle savedInstanceState) {
            super.onLoad(savedInstanceState);

            if (getView() == null) {
                return;
            }

            List<ActionBarPresenter.MenuAction> actions = new ArrayList<>();
            actions.add(new ActionBarPresenter.MenuAction("YOLO", new Action0() {
                @Override
                public void call() {
                    if (getView() != null) {
                        getView().showToast("You only live once!");
                    }
                }
            }));
            mActionBarPresenter.setConfig(new ActionBarPresenter.Config(
                true,
                false,
                "Nested Presenters",
                actions
            ));
        }
    }
}
