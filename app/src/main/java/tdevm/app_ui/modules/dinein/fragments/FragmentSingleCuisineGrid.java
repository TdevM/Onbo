package tdevm.app_ui.modules.dinein.fragments;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;
import tdevm.app_ui.AppApplication;
import tdevm.app_ui.R;
import tdevm.app_ui.api.APIService;
import tdevm.app_ui.api.models.MySharedPreferences;
import tdevm.app_ui.api.models.response.DishesOfCuisine;
import tdevm.app_ui.modules.dinein.DineInContract;
import tdevm.app_ui.modules.dinein.adapters.RecycledGridAdapter;
import tdevm.app_ui.modules.dinein.listeners.DishItemClickListener;
import tdevm.app_ui.utils.AuthUtils;

/**
 * Created by Tridev on 30-07-2017.
 */

public class FragmentSingleCuisineGrid extends Fragment implements DineInContract.SingleCuisineGridView{

    public static final String TAG = FragmentSingleCuisineGrid.class.getSimpleName();
    public static final String CUISINE_ID = "CUISINE_ID";
    public static final String RESTAURANT_UUID = "RESTAURANT_UUID";

    private RecyclerView.LayoutManager mLayoutManager;
    @BindView(R.id.recycler_view_dishes_grid_single)
    RecyclerView recyclerViewGridSingle;
    Unbinder unbinder;
    @Inject
    APIService apiService;
    private AuthUtils authUtils;
    @Inject
    MySharedPreferences mySharedPreferences;

    RecycledGridAdapter recycledGridAdapter;
    SingleCuisineGridPresenter singleCuisineGridPresenter;

    public static FragmentSingleCuisineGrid newInstance(String restaurantUUID, Long mCuisineId) {
        Bundle args = new Bundle();
        args.putLong(CUISINE_ID,mCuisineId);
        args.putString(RESTAURANT_UUID,restaurantUUID);
        FragmentSingleCuisineGrid fragment = new FragmentSingleCuisineGrid();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_single_cuisine_grid, container, false);
        resolveDaggerDependencies();
        unbinder =  ButterKnife.bind(this,view);
        mLayoutManager = new GridLayoutManager(getContext(), 2);
        Map<String,String> map = new HashMap<>();
        map.put("restaurant_uuid",String.valueOf(getArguments().getString(RESTAURANT_UUID)));
        map.put("cuisine_id",String.valueOf(getArguments().getLong(CUISINE_ID)));
        authUtils = new AuthUtils(mySharedPreferences);
        singleCuisineGridPresenter = new SingleCuisineGridPresenter(this,authUtils,apiService,map);
        singleCuisineGridPresenter.fetchDishesByCuisines();
        return view;
    }

    @Override
    public void showProgressUI() {

    }

    @Override
    public void hideProgressUI() {

    }

    public void resolveDaggerDependencies(){
        ((AppApplication) getActivity().getApplication()).getApiComponent().inject(this);
    }


    @Override
    public void onDishesOfCuisinesFetched(ArrayList<DishesOfCuisine> arrayList) {
        recyclerViewGridSingle.setLayoutManager(mLayoutManager);
        recycledGridAdapter = new RecycledGridAdapter(getActivity(),arrayList);
        recyclerViewGridSingle.setAdapter(recycledGridAdapter);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        singleCuisineGridPresenter.compositeDisposable.clear();
        singleCuisineGridPresenter.compositeDisposable.dispose();
    }
}
