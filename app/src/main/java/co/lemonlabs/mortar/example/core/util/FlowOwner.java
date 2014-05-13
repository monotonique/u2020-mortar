/*
 * Copyright 2013 Square Inc.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package co.lemonlabs.mortar.example.core.util;

import android.os.Bundle;
import android.view.View;

import java.util.List;

import co.lemonlabs.mortar.example.util.Lists;
import flow.Backstack;
import flow.Flow;
import flow.Parcer;
import mortar.Blueprint;
import mortar.ViewPresenter;

/**
 * Base class for all presenters that manage a {@link flow.Flow}.
 */
public abstract class FlowOwner<S extends Blueprint, V extends View & CanShowScreen<S> & CanShowDrawer<S>>
    extends ViewPresenter<V> implements Flow.Listener {

    private static final String FLOW_KEY = "FLOW_KEY";

    private final Parcer<Object> parcer;

    private Flow flow;

    protected FlowOwner(Parcer<Object> parcer) {
        this.parcer = parcer;
    }

    @Override
    public void onLoad(Bundle savedInstanceState) {
        super.onLoad(savedInstanceState);

        if (flow == null) {
            Backstack backstack;

            if (savedInstanceState != null) {
                backstack = Backstack.from(savedInstanceState.getParcelable(FLOW_KEY), parcer);
            } else {
                backstack = Backstack.fromUpChain(getFirstScreen());
            }

            flow = new Flow(backstack, this);
        }

        //noinspection unchecked
        showScreen((S) flow.getBackstack().current().getScreen(), null, null);
    }

    @Override
    public void onSave(Bundle outState) {
        super.onSave(outState);
        outState.putParcelable(FLOW_KEY, flow.getBackstack().getParcelable(parcer));
    }

    @Override
    public void go(Backstack backstack, Flow.Direction flowDirection) {
        //noinspection unchecked
        S newScreen = (S) backstack.current().getScreen();

        S oldScreen = null;
        if (flowDirection == Flow.Direction.FORWARD && backstack.size() > 1) {
            List<Backstack.Entry> entries = Lists.newArrayList(backstack.reverseIterator());
            Backstack.Entry oldEntry = entries.get(entries.size() - 2);
            //noinspection unchecked
            oldScreen = (S) oldEntry.getScreen();
        }

        showScreen(newScreen, oldScreen, flowDirection);
    }

    protected void showScreen(S newScreen, S oldScreen, Flow.Direction flowDirection) {
        V view = getView();
        if (view == null) return;

        view.showScreen(newScreen, oldScreen, flowDirection);
        view.showDrawer(getDrawerScreen());
    }

    public final Flow getFlow() {
        return flow;
    }

    /**
     * Returns the first screen shown by this presenter.
     */
    protected abstract S getFirstScreen();

    /**
     * Returns the screen of the navigation drawer
     */
    protected abstract S getDrawerScreen();
}