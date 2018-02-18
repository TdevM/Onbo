package tdevm.app_ui.di.components;

import dagger.Component;
import tdevm.app_ui.di.modules.APIModule;
import tdevm.app_ui.di.scopes.PerActivity;
import tdevm.app_ui.modules.auth.fragments.AuthInitFragment;
import tdevm.app_ui.modules.auth.fragments.AuthLoginFragment;
import tdevm.app_ui.modules.auth.fragments.AuthRegisterFragment;
import tdevm.app_ui.modules.auth.fragments.VerifyPhoneOTPFragment;
import tdevm.app_ui.modules.dinein.DineInActivity;
import tdevm.app_ui.modules.dinein.activities.TempOrderActivity;
import tdevm.app_ui.modules.dinein.bottomsheets.DishReviewsSheetFragment;
import tdevm.app_ui.modules.dinein.fragments.CartFragment;
import tdevm.app_ui.modules.dinein.fragments.DishMenuFragment;
import tdevm.app_ui.modules.dinein.fragments.RunningOrderFragment;
import tdevm.app_ui.modules.dinein.fragments.SingleCuisineGridFragment;
import tdevm.app_ui.modules.nondinein.activities.NonDineActivity;
import tdevm.app_ui.modules.nondinein.activities.NonDineRestaurantDetailsActivity;
import tdevm.app_ui.root.BottomNavigationHome;
import tdevm.app_ui.root.fragments.AccountsFragment;

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
    void inject(CartFragment cartFragment);
    void inject(DineInActivity dineInActivity);
    void inject(TempOrderActivity tempOrderActivity);
    void inject(NonDineRestaurantDetailsActivity activity);
    void inject(NonDineActivity activity);
    void inject(RunningOrderFragment fragment);
    void inject(AccountsFragment fragment);
    void inject(DishReviewsSheetFragment fragment);
}
