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

import java.util.List;

import mortar.MortarScope;
import mortar.Presenter;
import rx.functions.Action0;

/**
 * Allows shared configuration of the Android ActionBar.
 */
public class ActionBarPresenter extends Presenter<ActionBarPresenter.View> {
    public interface View {
        MortarScope getMortarScope();
        void setShowHomeEnabled(boolean enabled);
        void setUpButtonEnabled(boolean enabled);
        void setTitle(CharSequence title);
//        void setMenu(MenuAction action);
        void setMenu(List<MenuAction> actions);
    }

    public static class Config {
        public final boolean      showHomeEnabled;
        public final boolean      upButtonEnabled;
        public final CharSequence title;
//        public final MenuAction   action;
        public final List<MenuAction> actions;

        public Config(boolean showHomeEnabled, boolean upButtonEnabled, CharSequence title,
                      List<MenuAction> actions) {
            this.showHomeEnabled = showHomeEnabled;
            this.upButtonEnabled = upButtonEnabled;
            this.title = title;
            this.actions = actions;
        }

        public Config withAction(List<MenuAction> actions) {
            return new Config(showHomeEnabled, upButtonEnabled, title, actions);
        }
    }

    public static class MenuAction {
        public final CharSequence title;
        public final int icon;
        public final Action0      action;

        public MenuAction(CharSequence title, Action0 action) {
            this(title, -1, action);
        }

        public MenuAction(CharSequence title, int icon, Action0 action) {
            this.title = title;
            this.icon = icon;
            this.action = action;
        }
    }

    private Config config;

    ActionBarPresenter() {
    }

    @Override protected MortarScope extractScope(View view) {
        return view.getMortarScope();
    }

    @Override public void onLoad(Bundle savedInstanceState) {
        super.onLoad(savedInstanceState);
        if (config != null) update();
    }

    public void setConfig(Config config) {
        this.config = config;
        update();
    }

    public Config getConfig() {
        return config;
    }


    private void update() {
        View view = getView();
        if (view == null) return;

        view.setShowHomeEnabled(config.showHomeEnabled);
        view.setUpButtonEnabled(config.upButtonEnabled);
        view.setTitle(config.title);
        view.setMenu(config.actions);
    }
}
