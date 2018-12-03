package onbo.app.modules.dinein.callbacks;


import java.util.List;

import onbo.app.api.models.response.v2.menu.MenuAddOn;
import onbo.app.api.models.response.v2.menu.MenuItem;
import onbo.app.api.models.response.v2.menu.MenuVOption;

/**
 * Created by Tridev on 20-11-2017.
 */

public interface MenuItemOptionsSelected {

    void onOptionsSelected(MenuItem menuItem, List<MenuVOption> variantOptions, List<MenuAddOn> addOns);
}