package app.onbo.modules.dinein.callbacks;

import app.onbo.api.models.cart.MenuItem;

public interface CartItemClickListener {

    void onPlusButtonClicked(MenuItem menuItem, int num) ;
    void onMinusButtonClicked(MenuItem menuItem, int num);
    void onCustomizableItemClicked(MenuItem menuItem, int flag);

}
