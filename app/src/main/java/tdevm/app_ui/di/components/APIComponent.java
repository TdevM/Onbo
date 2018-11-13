package tdevm.app_ui.di.components;

import dagger.Component;
import tdevm.app_ui.di.modules.APIModule;
import tdevm.app_ui.di.scopes.PerActivity;
import tdevm.app_ui.modules.account.activities.ChangePasswordActivity;
import tdevm.app_ui.modules.account.activities.EditAccountDetailsActivity;
import tdevm.app_ui.modules.auth.fragments.AuthInitFragment;
import tdevm.app_ui.modules.auth.fragments.AuthLoginFragment;
import tdevm.app_ui.modules.auth.fragments.AuthRegisterFragment;
import tdevm.app_ui.modules.auth.fragments.AuthRegisterUpdate;
import tdevm.app_ui.modules.auth.fragments.VerifyPhoneOTPFragment;
import tdevm.app_ui.modules.dinein.DineInActivity;
import tdevm.app_ui.modules.dinein.activities.InitializeDineOrderActivity;
import tdevm.app_ui.modules.dinein.bottomsheets.DishReviewsSheetFragment;
import tdevm.app_ui.modules.dinein.fragments.CartFragment;
import tdevm.app_ui.modules.dinein.fragments.InitializeOrderFragment;
import tdevm.app_ui.modules.dinein.fragments.MenuItemsFragment;
import tdevm.app_ui.modules.dinein.fragments.MergedOrderFragment;
import tdevm.app_ui.modules.entry.RestaurantMenuEntryActivity;
import tdevm.app_ui.modules.intro.IntroActivity;
import tdevm.app_ui.modules.intro.SplashActivity;
import tdevm.app_ui.modules.nondine.NonDineActivity;
import tdevm.app_ui.modules.nondine.activities.InitNonDineOrderActivity;
import tdevm.app_ui.modules.nondine.activities.NonDineRestaurantDetailsActivity;
import tdevm.app_ui.modules.nondine.fragments.DigitalPaymentOptionsFragment;
import tdevm.app_ui.modules.nondine.fragments.NonDineCheckoutFragment;
import tdevm.app_ui.modules.nondine.fragments.OrderPaymentTypeFragment;
import tdevm.app_ui.modules.nondine.fragments.NDOrderCashFragment;
import tdevm.app_ui.modules.orders.fragments.MyOrderDetailActivity;
import tdevm.app_ui.modules.orders.fragments.MyOrdersFragment;
import tdevm.app_ui.modules.orders.RestaurantOrdersActivity;
import tdevm.app_ui.modules.payment.PaymentActivity;
import tdevm.app_ui.modules.payment.fragments.CheckoutFragment;
import tdevm.app_ui.modules.payment.fragments.PaymentFragment;
import tdevm.app_ui.root.RootActivity;
import tdevm.app_ui.root.activities.RestaurantDetailActivity;
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
    void inject(RootActivity rootActivity);
    void inject(MenuItemsFragment menuItemsFragment);
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
    void inject(MyOrderDetailActivity activity);
    void inject(MyOrdersFragment fragment);
    void inject(NonDineCheckoutFragment fragment);
    void inject(InitNonDineOrderActivity initNonDineOrderActivity);
    void inject(OrderPaymentTypeFragment fragment);
    void inject(NDOrderCashFragment fragment);
    void inject(SplashActivity activity);
    void inject(IntroActivity activity);
    void inject(DigitalPaymentOptionsFragment fragment);
    void inject(EditAccountDetailsActivity activity);
    void inject(ChangePasswordActivity activity);
    void inject(RestaurantDetailActivity activity);
    void inject(InitializeOrderFragment orderFragment);
    void inject(AuthRegisterUpdate fragment);
}
