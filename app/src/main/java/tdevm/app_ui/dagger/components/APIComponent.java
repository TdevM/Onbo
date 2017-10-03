package tdevm.app_ui.dagger.components;

import javax.inject.Singleton;

import dagger.Component;
import tdevm.app_ui.dagger.modules.APIModule;
import tdevm.app_ui.dagger.scopes.PerActivity;
import tdevm.app_ui.navigation_fragments_home.BottomNavigationHome;

/**
 * Created by Tridev on 04-10-2017.
 */

@PerActivity
@Component(modules = APIModule.class, dependencies = NetworkComponent.class)
public interface APIComponent {
    void inject(BottomNavigationHome bottomNavigationHome);
}
