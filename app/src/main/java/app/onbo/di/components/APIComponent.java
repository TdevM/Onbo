package app.onbo.di.components;

import app.onbo.utils.AuthInterceptor;
import dagger.Component;
import app.onbo.di.modules.APIModule;
import app.onbo.di.scopes.PerActivity;
import app.onbo.modules.account.activities.ChangePasswordActivity;
import app.onbo.modules.account.activities.EditAccountDetailsActivity;
import app.onbo.modules.auth.fragments.AuthInitFragment;
import app.onbo.modules.auth.fragments.AuthLoginFragment;
import app.onbo.modules.auth.fragments.AuthRegisterFragment;
import app.onbo.modules.auth.fragments.AuthRegisterUpdate;
import app.onbo.modules.auth.fragments.VerifyPhoneOTPFragment;
import app.onbo.modules.dinein.DineInActivity;
import app.onbo.modules.dinein.activities.InitializeDineOrderActivity;
import app.onbo.modules.dinein.bottomsheets.DishReviewsSheetFragment;
import app.onbo.modules.dinein.fragments.CartFragment;
import app.onbo.modules.dinein.fragments.InitializeOrderFragment;
import app.onbo.modules.dinein.fragments.MenuItemsFragment;
import app.onbo.modules.dinein.fragments.MergedOrderFragment;
import app.onbo.modules.entry.RestaurantMenuEntryActivity;
import app.onbo.modules.intro.IntroActivity;
import app.onbo.modules.intro.SplashActivity;
import app.onbo.modules.nondine.NonDineActivity;
import app.onbo.modules.nondine.activities.InitNonDineOrderActivity;
import app.onbo.modules.nondine.activities.NonDineRestaurantDetailsActivity;
import app.onbo.modules.nondine.fragments.DigitalPaymentOptionsFragment;
import app.onbo.modules.nondine.fragments.NonDineCheckoutFragment;
import app.onbo.modules.nondine.fragments.OrderPaymentTypeFragment;
import app.onbo.modules.nondine.fragments.NDOrderCashFragment;
import app.onbo.modules.orders.fragments.MyOrderDetailActivity;
import app.onbo.modules.orders.fragments.MyOrdersFragment;
import app.onbo.modules.orders.RestaurantOrdersActivity;
import app.onbo.modules.payment.PaymentActivity;
import app.onbo.modules.payment.fragments.CashPickupFragment;
import app.onbo.modules.payment.fragments.CheckoutFragment;
import app.onbo.modules.payment.fragments.PaymentFragment;
import app.onbo.root.RootActivity;
import app.onbo.root.activities.RestaurantDetailActivity;
import app.onbo.root.fragments.AccountsFragment;
import app.onbo.root.fragments.RestaurantListFragment;

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
    void inject(AuthInterceptor authInterceptor);
}
