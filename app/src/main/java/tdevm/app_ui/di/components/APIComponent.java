package tdevm.app_ui.di.components;

import dagger.Component;
import tdevm.app_ui.di.modules.APIModule;
import tdevm.app_ui.di.scopes.PerActivity;
import tdevm.app_ui.modules.auth.fragments.AuthInitFragment;
import tdevm.app_ui.modules.auth.fragments.AuthLoginFragment;
import tdevm.app_ui.modules.auth.fragments.AuthRegisterFragment;
import tdevm.app_ui.modules.auth.fragments.VerifyPhoneOTPFragment;
import tdevm.app_ui.modules.dinein.DineInActivity;
import tdevm.app_ui.modules.dinein.activities.InitializeDineOrderActivity;
import tdevm.app_ui.modules.dinein.bottomsheets.DishReviewsSheetFragment;
import tdevm.app_ui.modules.dinein.fragments.CartFragment;
import tdevm.app_ui.modules.dinein.fragments.DishMenuFragment;
import tdevm.app_ui.modules.dinein.fragments.MergedOrderFragment;
import tdevm.app_ui.modules.dinein.fragments.SingleCuisineGridFragment;
import tdevm.app_ui.modules.entry.RestaurantMenuEntryActivity;
import tdevm.app_ui.modules.nondinein.activities.NonDineActivity;
import tdevm.app_ui.modules.nondinein.activities.NonDineRestaurantDetailsActivity;
import tdevm.app_ui.modules.orders.fragments.MyOrderDetailFragment;
import tdevm.app_ui.modules.orders.fragments.MyOrdersFragment;
import tdevm.app_ui.modules.orders.RestaurantOrdersActivity;
import tdevm.app_ui.modules.payment.PaymentActivity;
import tdevm.app_ui.modules.payment.fragments.CheckoutFragment;
import tdevm.app_ui.modules.payment.fragments.PaymentFragment;
import tdevm.app_ui.root.BottomNavigationHome;
import tdevm.app_ui.root.fragments.AccountsFragment;
import tdevm.app_ui.root.fragments.RestaurantListFragment;

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
    void inject(InitializeDineOrderActivity initializeDineOrderActivity);
    void inject(NonDineRestaurantDetailsActivity activity);
    void inject(NonDineActivity activity);
    void inject(MergedOrderFragment fragment);
    void inject(AccountsFragment fragment);
    void inject(DishReviewsSheetFragment fragment);
    void inject(RestaurantMenuEntryActivity activity);
    void inject(CheckoutFragment fragment);
    void inject(PaymentFragment fragment);
    void inject(PaymentActivity activity);
    void inject(RestaurantListFragment fragment);
    void inject(RestaurantOrdersActivity activity);
    void inject(MyOrderDetailFragment fragment);
    void inject(MyOrdersFragment fragment);
}
