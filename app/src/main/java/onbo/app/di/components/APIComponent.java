package onbo.app.di.components;

import dagger.Component;
import onbo.app.di.modules.APIModule;
import onbo.app.di.scopes.PerActivity;
import onbo.app.modules.account.activities.ChangePasswordActivity;
import onbo.app.modules.account.activities.EditAccountDetailsActivity;
import onbo.app.modules.auth.fragments.AuthInitFragment;
import onbo.app.modules.auth.fragments.AuthLoginFragment;
import onbo.app.modules.auth.fragments.AuthRegisterFragment;
import onbo.app.modules.auth.fragments.AuthRegisterUpdate;
import onbo.app.modules.auth.fragments.VerifyPhoneOTPFragment;
import onbo.app.modules.dinein.DineInActivity;
import onbo.app.modules.dinein.activities.InitializeDineOrderActivity;
import onbo.app.modules.dinein.bottomsheets.DishReviewsSheetFragment;
import onbo.app.modules.dinein.fragments.CartFragment;
import onbo.app.modules.dinein.fragments.InitializeOrderFragment;
import onbo.app.modules.dinein.fragments.MenuItemsFragment;
import onbo.app.modules.dinein.fragments.MergedOrderFragment;
import onbo.app.modules.entry.RestaurantMenuEntryActivity;
import onbo.app.modules.intro.IntroActivity;
import onbo.app.modules.intro.SplashActivity;
import onbo.app.modules.nondine.NonDineActivity;
import onbo.app.modules.nondine.activities.InitNonDineOrderActivity;
import onbo.app.modules.nondine.activities.NonDineRestaurantDetailsActivity;
import onbo.app.modules.nondine.fragments.DigitalPaymentOptionsFragment;
import onbo.app.modules.nondine.fragments.NonDineCheckoutFragment;
import onbo.app.modules.nondine.fragments.OrderPaymentTypeFragment;
import onbo.app.modules.nondine.fragments.NDOrderCashFragment;
import onbo.app.modules.orders.fragments.MyOrderDetailActivity;
import onbo.app.modules.orders.fragments.MyOrdersFragment;
import onbo.app.modules.orders.RestaurantOrdersActivity;
import onbo.app.modules.payment.PaymentActivity;
import onbo.app.modules.payment.fragments.CashPickupFragment;
import onbo.app.modules.payment.fragments.CheckoutFragment;
import onbo.app.modules.payment.fragments.PaymentFragment;
import onbo.app.root.RootActivity;
import onbo.app.root.activities.RestaurantDetailActivity;
import onbo.app.root.fragments.AccountsFragment;
import onbo.app.root.fragments.RestaurantListFragment;

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
    void inject(CashPickupFragment fragment);
}
