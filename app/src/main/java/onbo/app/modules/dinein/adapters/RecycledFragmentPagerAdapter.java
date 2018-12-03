package onbo.app.modules.dinein.adapters;

import android.content.Context;
import android.os.Parcelable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;

import java.util.ArrayList;

import onbo.app.api.models.response.v2.menu.Cuisine;
import onbo.app.modules.dinein.fragments.MenuItemsFragment;

/**
 * Created by Tridev on 30-07-2017.
 */

public class RecycledFragmentPagerAdapter extends FragmentStatePagerAdapter{

    private Context context;
    private ArrayList<Cuisine> cuisineList;
    private String RESTAURANT_ID;
    public RecycledFragmentPagerAdapter(FragmentManager fm, Context c, ArrayList<Cuisine> cuisines,String restaurantID) {
        super(fm);
        this.context = c;
        cuisineList = cuisines;
        this.RESTAURANT_ID = restaurantID;
    }

    @Override
    public Fragment getItem(int position) {
        return  MenuItemsFragment.newInstance("8");

    }

    @Override
    public int getCount() {
        return cuisineList.size();
    }

    @Override
    public CharSequence getPageTitle(int position) {
        return cuisineList.get(position).getCuisine_name();
    }

    @Override
    public Parcelable saveState() {
        return super.saveState();
    }

    @Override
    public void restoreState(Parcelable state, ClassLoader loader) {
        super.restoreState(state, loader);
    }
}
