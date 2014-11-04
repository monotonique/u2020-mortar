package co.lemonlabs.mortar.example.core.android;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;

@Module(injects = {}, library = true)
public class AndroidModule {
    @Provides @Singleton
    ActionBarPresenter provideActionBarPresenter() {
        return new ActionBarPresenter();
    }

    @Provides @Singleton
    DrawerPresenter provideDrawerPresenter() {
        return new DrawerPresenter();
    }

    @Provides @Singleton ActivityPresenter provideActivityOwner() {
        return new ActivityPresenter();
    }

//    @Provides @Singleton ActivityPresenter provideActivityPresenter() {
//        return new ActivityPresenter();
//    }

}
