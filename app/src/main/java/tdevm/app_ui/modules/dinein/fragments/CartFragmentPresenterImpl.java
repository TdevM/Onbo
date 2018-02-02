package tdevm.app_ui.modules.dinein.fragments;

import android.util.Log;

import java.util.List;

import javax.inject.Inject;

import io.reactivex.disposables.CompositeDisposable;
import tdevm.app_ui.api.cart.CartItem;
import tdevm.app_ui.api.cart.CartSelection;
import tdevm.app_ui.api.models.response.DishesOfCuisine;
import tdevm.app_ui.base.BasePresenter;
import tdevm.app_ui.modules.dinein.DineInPresenterContract;
import tdevm.app_ui.modules.dinein.DineInViewContract;
import tdevm.app_ui.utils.CartHelper;

/**
 * Created by Tridev on 06-01-2018.
 */

public class CartFragmentPresenterImpl extends BasePresenter implements DineInPresenterContract.CartFragmentPresenter{

    public static final String TAG = CartFragmentPresenterImpl.class.getSimpleName();
    private CartHelper cartHelper;
    private DineInViewContract.CartFragmentView cartFragmentView;
    private CompositeDisposable compositeDisposable;


    @Inject
    public CartFragmentPresenterImpl(CartHelper cartHelper) {
        this.cartHelper = cartHelper;
        compositeDisposable = new CompositeDisposable();
    }


    @Override
    public boolean cartItemsExists() {
        return cartHelper.cartItemsExist();
    }


    @Override
    public void addItemToCart(DishesOfCuisine dishesOfCuisine) {
        cartHelper.addItemToCart(dishesOfCuisine);
        cartHelper.addItemToSelection(dishesOfCuisine);
        cartFragmentView.updateAdapter();
        cartFragmentView.updateBottomSheet(cartHelper.getCartTotalItems(),cartHelper.getCartTotal());
        logSelections();
    }

    @Override
    public void updateCartItem(DishesOfCuisine dishesOfCuisine) {
        cartHelper.updateCartItem(dishesOfCuisine);
        cartHelper.updateSelectionItem(dishesOfCuisine);
        cartFragmentView.updateAdapter();
        cartFragmentView.updateBottomSheet(cartHelper.getCartTotalItems(),cartHelper.getCartTotal());
        if(cartHelper.getCartTotalItems()==0){
            cartFragmentView.showCartEmpty();
        }
        logSelections();
    }

    @Override
    public void addCustomizableItemToCart(DishesOfCuisine dishesOfCuisine,Long dishId, int operationFlag) {
      if(operationFlag==1){
          cartHelper.addItemToCart(dishesOfCuisine);
          cartHelper.incrementCartSelectionById(dishId);
          cartFragmentView.updateAdapter();
          cartFragmentView.updateBottomSheet(cartHelper.getCartTotalItems(),cartHelper.getCartTotal());
      }else if(operationFlag==0){
          cartHelper.updateCartItem(dishesOfCuisine);
          cartHelper.decrementCartSelectionById(dishId);
          cartFragmentView.updateAdapter();
          cartFragmentView.updateBottomSheet(cartHelper.getCartTotalItems(),cartHelper.getCartTotal());
      }
    }

    @Override
    public void attachView(DineInViewContract.CartFragmentView view) {
        cartFragmentView = view;
    }

    @Override
    public void detachView() {
        compositeDisposable.clear();
        compositeDisposable.dispose();
    }


    public void logSelections() {
        Log.d(TAG,"Log Started");
        List<CartSelection> selection = cartHelper.getCartSelections();
        List<CartItem> cartItems = cartHelper.getCartItems();

        if(selection!=null && cartItems!=null) {
            for (int i = 0; i < selection.size(); i++) {
                Log.d(TAG, "Dish selection made:" + selection.get(i).getDishesOfCuisine().getDish_name());
                Log.d(TAG, "Dish qty added:" + String.valueOf(selection.get(i).getQty()));
            }

            for (int i = 0; i < cartItems.size(); i++) {
                Log.d(TAG, "Cart Item:" + cartItems.get(i).getDishesOfCuisine().getDish_name());
                Log.d(TAG, "Item QTY:" + cartItems.get(i).getQuantity());
                Log.d(TAG, "Item Total:" + cartItems.get(i).getPrice());
            }
        }
    }
}
