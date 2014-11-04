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
package co.lemonlabs.mortar.example.core.android;

import android.os.Bundle;
import android.support.v4.app.FragmentActivity;

import hugo.weaving.DebugLog;
import mortar.Mortar;
import mortar.MortarScope;
import mortar.Presenter;
import timber.log.Timber;

public class ActivityPresenter extends Presenter<ActivityPresenter.View> {

    public interface View extends ContextHolder {
        void finishCurrentActivity();
    }

    ActivityPresenter() {}

    @Override protected MortarScope extractScope(View view) {
        return Mortar.getScope(view.getMortarContext());
    }

    @DebugLog @Override protected void onLoad(Bundle savedInstanceState) {
        super.onLoad(savedInstanceState);
    }

    @Override protected void onExitScope() {
        super.onExitScope();
    }

    public FragmentActivity getActivity() {
        View view = getView();
        Timber.d("getView() %s", view);
        if (view == null) {
            return null;
        }
        Timber.d("getView().getMortarContext(): %s", view.getMortarContext());
        return (FragmentActivity) view.getMortarContext();
    }

    public void finishCurrentActivity() {
        View view = getView();
        if (view == null) {
            return;
        }

        view.finishCurrentActivity();
    }

}
