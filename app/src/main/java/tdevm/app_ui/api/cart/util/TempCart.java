package tdevm.app_ui.api.cart.util;

import tdevm.app_ui.api.cart.model.Cart;

/**
 * Created by Tridev on 16-11-2017.
 */

public class TempCart {
    private static Cart cart = new Cart();
    public static Cart getTempCart() {
        if (cart == null) {
            cart = new Cart();
        }

        return cart;
    }

}
