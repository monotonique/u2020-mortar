package co.lemonlabs.mortar.example.core;

import android.app.Application;
import android.content.Context;

import javax.inject.Inject;
import javax.inject.Singleton;

import co.lemonlabs.mortar.example.U2020Module;
import co.lemonlabs.mortar.example.core.android.ActionBarPresenter;
import co.lemonlabs.mortar.example.core.util.FlowOwner;
import co.lemonlabs.mortar.example.ui.screens.EntryScreen;
import co.lemonlabs.mortar.example.ui.screens.PagerScreen;
import dagger.Provides;
import flow.Flow;
import flow.Parcer;
import mortar.Blueprint;

public class CorePresenter implements Blueprint {

    private final String scopeName;

    /**
     * Required for a race condition cause by Android when a new scope is created
     * before the old one is destroyed
     * <p/>
     * https://github.com/square/mortar/issues/87#issuecomment-43849264
     */
    public CorePresenter(String scopeName) {
        this.scopeName = scopeName;
    }

    @Override
    public String getMortarScopeName() {
        return scopeName;
    }

    @Override
    public Object getDaggerModule() {
        return new Module();
    }

    @dagger.Module(
        injects = {
            CoreView.class
        },
        addsTo = U2020Module.class,
        library = true
    )
    public final class Module {

        public Module() {

        }

        @Provides
        @MainScope
        Flow provideFlow(Presenter presenter) {
            return presenter.getFlow();
        }

        @Provides
        Context providesContext(Application app) {
            return app;
        }
    }

    // The core presenter extends FlowOwner which take care of the screen flows
    @Singleton
    public static class Presenter extends FlowOwner<Blueprint, CoreView> {

        private final ActionBarPresenter actionBarOwner;

        @Inject
        Presenter(Parcer<Object> flowParcer, ActionBarPresenter actionBarOwner) {
            super(flowParcer);
            this.actionBarOwner = actionBarOwner;
        }

        @Override
        protected Blueprint getFirstScreen() {
            return new PagerScreen();
        }

//        @Override protected Blueprint getDrawerScreen() {
//            return new DrawerScreen();
//        }

    }
}
