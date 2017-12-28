package tdevm.app_ui.modules.dinein.fragments;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
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
import tdevm.app_ui.api.models.response.DishesOfCuisine;
import tdevm.app_ui.modules.dinein.DineInViewContract;
import tdevm.app_ui.modules.dinein.adapters.RecycledGridAdapter;
import tdevm.app_ui.modules.dinein.listeners.DishItemClickListener;

/**
 * Created by Tridev on 30-07-2017.
 */

public class SingleCuisineGridFragment extends Fragment implements DineInViewContract.SingleCuisineGridView{

    public static final String TAG = SingleCuisineGridFragment.class.getSimpleName();
    public static final String CUISINE_ID = "CUISINE_ID";
    public static final String RESTAURANT_UUID = "RESTAURANT_UUID";

    private RecyclerView.LayoutManager mLayoutManager;
    @BindView(R.id.recycler_view_dishes_grid_single)
    RecyclerView recyclerViewGridSingle;
    Unbinder unbinder;
    private Map<String,String> fetchDishesMap;

    RecycledGridAdapter recycledGridAdapter;

    @Inject
    SingleCuisineGridPresenter singleCuisineGridPresenter;

    public static SingleCuisineGridFragment newInstance(String restaurantUUID, Long mCuisineId) {
        Bundle args = new Bundle();
        args.putLong(CUISINE_ID,mCuisineId);
        args.putString(RESTAURANT_UUID,restaurantUUID);
        SingleCuisineGridFragment fragment = new SingleCuisineGridFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public void onResume() {
        singleCuisineGridPresenter.attachView(this);
        super.onResume();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        resolveDaggerDependencies();
        View view = inflater.inflate(R.layout.fragment_single_cuisine_grid, container, false);
        unbinder =  ButterKnife.bind(this,view);
        mLayoutManager = new GridLayoutManager(getContext(), 2);
        fetchDishesMap = new HashMap<>();
        fetchDishesMap.put("restaurant_uuid",String.valueOf(getArguments().getString(RESTAURANT_UUID)));
        fetchDishesMap.put("cuisine_id",String.valueOf(getArguments().getLong(CUISINE_ID)));
        singleCuisineGridPresenter.fetchDishesByCuisines(fetchDishesMap);
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
        recycledGridAdapter.setOnDishItemClickListener(new DishItemClickListener() {
            @Override
            public void getDishItemQuant(DishesOfCuisine dishesOfCuisine, int oldValue, int newValue) {
                if(dishesOfCuisine.getIs_customizable()){
                    fetchDishesMap.put("dish_id",String.valueOf(dishesOfCuisine.getDish_id()));
                    singleCuisineGridPresenter.fetchVariantsOfADish(fetchDishesMap);
                }else {
                    singleCuisineGridPresenter.addToCart(dishesOfCuisine);
                }
            }
        });
    }

    @Override
    public void onDishVariantsFetched(ArrayList<DishesOfCuisine> arrayList) {
        Log.d(TAG,arrayList.toString());
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
       singleCuisineGridPresenter.detachView();
    }
}
