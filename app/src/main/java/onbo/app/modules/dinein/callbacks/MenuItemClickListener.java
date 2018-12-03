package onbo.app.modules.dinein.callbacks;


import onbo.app.api.models.response.v2.menu.MenuItem;

/**
 * Created by Tridev on 21-10-2017.
 */

public interface MenuItemClickListener {

    void onPlusButtonClicked(MenuItem menuItem, int num) ;
    void onMinusButtonClicked(MenuItem menuItem, int num);
    void onItemImageClicked(MenuItem menuItem);
    void onCustomizableItemClicked(MenuItem menuItem, int flag);
}