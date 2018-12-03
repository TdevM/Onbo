package onbo.app.modules.dinein.callbacks;

import onbo.app.api.models.cart.MenuItem;

public interface CartItemClickListener {

    void onPlusButtonClicked(MenuItem menuItem, int num) ;
    void onMinusButtonClicked(MenuItem menuItem, int num);
    void onCustomizableItemClicked(MenuItem menuItem, int flag);

}
