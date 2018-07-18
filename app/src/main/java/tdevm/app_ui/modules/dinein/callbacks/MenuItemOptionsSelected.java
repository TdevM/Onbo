package tdevm.app_ui.modules.dinein.callbacks;


import java.util.List;

import tdevm.app_ui.api.models.response.DishVariant;
import tdevm.app_ui.api.models.response.DishesOfCuisine;
import tdevm.app_ui.api.models.response.v2.menu.MenuAddOn;
import tdevm.app_ui.api.models.response.v2.menu.MenuItem;
import tdevm.app_ui.api.models.response.v2.menu.MenuVOption;

/**
 * Created by Tridev on 20-11-2017.
 */

public interface MenuItemOptionsSelected {

    void onOptionsSelected(MenuItem menuItem, List<MenuVOption> variantOptions, List<MenuAddOn> addOns);
}