package tdevm.app_ui.api.models.response;

import tdevm.app_ui.api.models.response.v2.menu.CuisineMenuItems;
import tdevm.app_ui.api.models.response.v2.menu.MenuItem;

public class HeterogeneousObject {

    int itemType;
    String itemClass;
    CuisineMenuItems cuisine;
    MenuItem menuItem;
    int menuItemCount;


    public HeterogeneousObject(int itemType,String itemClass, CuisineMenuItems cuisine) {
        this.itemType = itemType;
        this.cuisine = cuisine;
        this.itemClass = itemClass;
    }

    public HeterogeneousObject(int itemType,String itemClass, MenuItem menuItem) {
        this.itemType = itemType;
        this.menuItem = menuItem;
        this.itemClass = itemClass;
    }

    public int getItemType() {
        return itemType;
    }

    public void setItemType(int itemType) {
        this.itemType = itemType;
    }

    public CuisineMenuItems getCuisine() {
        return cuisine;
    }

    public void setCuisine(CuisineMenuItems cuisine) {
        this.cuisine = cuisine;
    }

    public MenuItem getMenuItem() {
        return menuItem;
    }

    public int getMenuItemCount() {
        return menuItemCount;
    }

    public void setMenuItemCount(int menuItemCount) {
        this.menuItemCount = menuItemCount;
    }

    public void setMenuItem(MenuItem menuItem) {
        this.menuItem = menuItem;
    }


    public String getItemClass() {
        return itemClass;
    }

    public void setItemClass(String itemClass) {
        this.itemClass = itemClass;
    }

    @Override
    public String toString() {
        return "HeterogeneousObject{" +
                "itemType=" + itemType +
                ", itemClass='" + itemClass + '\'' +
                ", cuisine=" + cuisine +
                ", menuItem=" + menuItem +
                '}';
    }
}
