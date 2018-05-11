package tdevm.app_ui.modules.dinein.fragments;


import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;
import tdevm.app_ui.AppApplication;
import tdevm.app_ui.R;
import tdevm.app_ui.api.models.response.v2.Cuisine;
import tdevm.app_ui.modules.auth.fragments.AuthInitFragment;
import tdevm.app_ui.modules.dinein.DineInViewContract;
import tdevm.app_ui.modules.dinein.adapters.RecycledFragmentPagerAdapter;
import tdevm.app_ui.utils.AuthUtils;

public class DishMenuFragment extends Fragment implements DineInViewContract.DishMenuView {
    public static final String TAG = AuthInitFragment.class.getSimpleName();

    Unbinder unbinder;
    @BindView(R.id.viewpager_dish_menu)
    ViewPager viewPagerDishMenu;
    @BindView(R.id.sliding_tabs_dish_menu)
    TabLayout tabLayoutDishMenu;

    @Inject
    DishMenuPresenter dishMenuPresenter;
    @Inject
    AuthUtils authUtils;


    public DishMenuFragment() {
        // Required empty public constructor
    }

    public static DishMenuFragment newInstance(){
        return new DishMenuFragment();
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
        }
    }

    @Override
    public void onResume() {
        dishMenuPresenter.attachView(this);
        super.onResume();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        resolveDaggerDependencies();
        View view =  inflater.inflate(R.layout.fragment_dish_menu, container, false);

        unbinder =  ButterKnife.bind(this,view);
        tabLayoutDishMenu.setupWithViewPager(viewPagerDishMenu);
        tabLayoutDishMenu.setOverScrollMode(1);
        Map<String,String> map = new HashMap<>();
        map.put("restaurant_uuid",authUtils.getScannedRestaurantUuid());
        dishMenuPresenter.FetchAllCuisines(map);
        return view;
    }


    public void resolveDaggerDependencies(){
        ((AppApplication) getActivity().getApplication()).getApiComponent().inject(this);
    }

    @Override
    public void showProgressUI() {

    }

    @Override
    public void hideProgressUI() {

    }

    @Override
    public void onCuisinesFetched(ArrayList<Cuisine> cuisines) {
      viewPagerDishMenu.setAdapter(new RecycledFragmentPagerAdapter(getChildFragmentManager(),getActivity(),cuisines,authUtils.getScannedRestaurantUuid()));
    }

    @Override
    public void onDestroy() {
        dishMenuPresenter.detachView();
        super.onDestroy();
    }
}
