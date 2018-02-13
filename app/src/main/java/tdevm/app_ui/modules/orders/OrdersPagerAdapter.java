package tdevm.app_ui.modules.orders;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

/**
 * Created by Tridev on 13-02-2018.
 */

public class OrdersPagerAdapter extends FragmentPagerAdapter {

    public OrdersPagerAdapter(FragmentManager fm) {
        super(fm);
    }

    @Override
    public Fragment getItem(int position) {
        switch (position) {
            case 0: // Fragment # 0 - This will show FirstFragment
                return NonDineOrdersFragment.newInstance();
            case 1: // Fragment # 0 - This will show FirstFragment different title
                return DineInOrdersFragment.newInstance();
            case 2: // Fragment # 1 - This will show SecondFragment
            default:
                return null;
        }
    }

    @Override
    public int getCount() {
        return 2;
    }
}
