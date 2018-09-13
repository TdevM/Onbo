package tdevm.app_ui.modules.dinein.callbacks;

import tdevm.app_ui.api.models.cart.MenuItem;

public interface CartItemClickListener {

    void onPlusButtonClicked(MenuItem menuItem, int num) ;
    void onMinusButtonClicked(MenuItem menuItem, int num);
    void onCustomizableItemClicked(MenuItem menuItem, int flag);

}
