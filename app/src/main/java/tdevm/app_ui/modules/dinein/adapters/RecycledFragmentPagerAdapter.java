package tdevm.app_ui.modules.dinein.adapters;

import android.content.Context;
import android.os.Parcelable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;

import java.util.ArrayList;

import tdevm.app_ui.api.models.response.Cuisine;
import tdevm.app_ui.modules.dinein.fragments.SingleCuisineGridFragment;

/**
 * Created by Tridev on 30-07-2017.
 */

public class RecycledFragmentPagerAdapter extends FragmentStatePagerAdapter{

    private Context context;
    private ArrayList<Cuisine> cuisineList;
    private String RESTAURANT_UUID;
    public RecycledFragmentPagerAdapter(FragmentManager fm, Context c, ArrayList<Cuisine> cuisines,String restaurantUUID) {
        super(fm);
        this.context = c;
        cuisineList = cuisines;
        this.RESTAURANT_UUID = restaurantUUID;
    }

    @Override
    public Fragment getItem(int position) {
        return  SingleCuisineGridFragment.newInstance(RESTAURANT_UUID,cuisineList.get(position).getCuisine_id());
    }

    @Override
    public int getCount() {
        return cuisineList.size();
    }

    @Override
    public CharSequence getPageTitle(int position) {
        return cuisineList.get(position).getCuisine_type();
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
