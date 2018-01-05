package tdevm.app_ui.utils;

import java.util.List;

import javax.inject.Inject;

import tdevm.app_ui.api.cart.CartItem;
import tdevm.app_ui.api.cart.CartItemDao;
import tdevm.app_ui.api.cart.CartSelection;
import tdevm.app_ui.api.cart.CartSelectionDao;
import tdevm.app_ui.api.models.response.DishesOfCuisine;

/**
 * Created by Tridev on 05-01-2018.
 */

public class CartHelper {

    private CartItemDao cartItemDao;
    private CartSelectionDao cartSelectionDao;

    @Inject
    public CartHelper(CartItemDao cartItemDao, CartSelectionDao cartSelectionDao) {
        this.cartItemDao = cartItemDao;
        this.cartSelectionDao = cartSelectionDao;
    }

    //Queries
    public List<CartItem> getCartItems() {
        return cartItemDao.getCartItems();
    }

    public CartItem getCartItemById(Long dishId) {
        return cartItemDao.getCartItemById(dishId);
    }

    public List<CartSelection> getCartSelections() {
        return cartSelectionDao.getCartSelections();
    }

    public CartSelection getCartSelectionById(Long dishId) {
        return cartSelectionDao.getCartSelectionById(dishId);
    }

    //Mutations
    public void addItemToCart(DishesOfCuisine dishesOfCuisine) {
        CartItem item  = cartItemDao.getCartItemById(dishesOfCuisine.getDish_id());
        if(item==null){
            CartItem cartItem = new CartItem(dishesOfCuisine.getDish_id(),dishesOfCuisine,1,dishesOfCuisine.getDish_price());
            cartItemDao.addItemToCart(cartItem);
        }else if(item.getQuantity()>=1){
            CartItem cartItem = new CartItem(dishesOfCuisine.getDish_id(),dishesOfCuisine,item.getQuantity()+1,(item.getQuantity()+1)*dishesOfCuisine.getDish_price());
            cartItemDao.updateCartItem(cartItem);
        }
    }

    public void updateCartItem(DishesOfCuisine dishesOfCuisine) {
        CartItem item  = cartItemDao.getCartItemById(dishesOfCuisine.getDish_id());
        if(item!=null){
            CartItem cartItem = new CartItem(dishesOfCuisine.getDish_id(),dishesOfCuisine,item.getQuantity()-1,(item.getQuantity()-1)*dishesOfCuisine.getDish_price());
            cartItemDao.updateCartItem(cartItem);
            if(item.getQuantity()==1){
                cartItemDao.deleteItemFromCart(item);
            }
        }
    }

    public void addItemToSelection(DishesOfCuisine dishesOfCuisine) {
        CartSelection i = cartSelectionDao.getCartSelectionById(dishesOfCuisine.getDish_id());
        if(i==null){
            CartSelection newSelection = new CartSelection(dishesOfCuisine.getDish_id(), dishesOfCuisine, 1);
            cartSelectionDao.addItemToSelection(newSelection);
        }else {
            cartSelectionDao.incrementCartSelectionById(dishesOfCuisine.getDish_id());
        }
    }

    public void updateSelectionItem(DishesOfCuisine dishesOfCuisine) {
        CartSelection i = cartSelectionDao.getCartSelectionById(dishesOfCuisine.getDish_id());
        if (i != null) {
            cartSelectionDao.decrementCartSelectionById(dishesOfCuisine.getDish_id());
        }
    }

    public Boolean cartSelectionExist(){
        return cartSelectionDao.getCartSelections()!=null;
    }

    public Boolean cartItemsExist(){
        return cartItemDao.getTotalItems()>0;
    }
}
