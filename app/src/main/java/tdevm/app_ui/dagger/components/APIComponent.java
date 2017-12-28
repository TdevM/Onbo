package tdevm.app_ui.dagger.components;

import dagger.Component;
import tdevm.app_ui.dagger.modules.APIModule;
import tdevm.app_ui.dagger.scopes.PerActivity;
import tdevm.app_ui.modules.auth.fragments.AuthInitFragment;
import tdevm.app_ui.modules.auth.fragments.AuthLoginFragment;
import tdevm.app_ui.modules.auth.fragments.AuthRegisterFragment;
import tdevm.app_ui.modules.auth.fragments.VerifyPhoneOTPFragment;
import tdevm.app_ui.modules.dinein.fragments.DishMenuFragment;
import tdevm.app_ui.modules.dinein.fragments.SingleCuisineGridFragment;
import tdevm.app_ui.root.BottomNavigationHome;

/**
 * Created by Tridev on 04-10-2017.
 */

@PerActivity
@Component(modules = APIModule.class, dependencies = ApplicationComponent.class)
public interface APIComponent {
    void inject(AuthInitFragment authInitFragment);
    void inject(AuthLoginFragment authLoginFragment);
    void inject(VerifyPhoneOTPFragment verifyPhoneOTPFragment);
    void inject(AuthRegisterFragment authRegisterFragment);
    void inject(BottomNavigationHome bottomNavigationHome);
    void inject(SingleCuisineGridFragment singleCuisineGridFragment);
    void inject(DishMenuFragment dishMenuFragment);
}
