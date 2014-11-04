package co.lemonlabs.mortar.example.ui.screens;

import android.os.Bundle;
import android.view.ViewTreeObserver;

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
import flow.Flow;
import flow.Layout;
import mortar.Blueprint;
import mortar.ViewPresenter;
import rx.functions.Action0;
import timber.log.Timber;

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
        injects = { EntryView.class},
        addsTo = CorePresenter.Module.class,
        library = true
    )
    public static class Module {

        public Module() { }

    }

    @Singleton
    public static class Presenter extends ViewPresenter<EntryView> {

        private final Flow mFlow;
        private final ActionBarPresenter mActionBarPresenter;

        private ViewTreeObserver vto;

        @Inject
        Presenter(Flow flow, ActionBarPresenter actionBarPresenter) {
            mFlow = flow;
            mActionBarPresenter = actionBarPresenter;
        }


        @Override
        protected void onLoad(Bundle savedInstanceState) {
            Timber.d("onLoad");
            super.onLoad(savedInstanceState);

            // Add MenuActions to Action Bar
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
                "Entry",
                actions
            ));
        }

        public void goToLobby() {
            mFlow.goTo(new LobbyScreen());
        }
    }
}
