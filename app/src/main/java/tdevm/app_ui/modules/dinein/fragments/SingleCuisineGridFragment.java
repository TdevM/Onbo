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
import tdevm.app_ui.api.models.response.DishReviews;
import tdevm.app_ui.api.models.response.DishVariant;
import tdevm.app_ui.api.models.response.DishesOfCuisine;
import tdevm.app_ui.modules.dinein.DineInViewContract;
import tdevm.app_ui.modules.dinein.adapters.RecycledGridMenuAdapter;
import tdevm.app_ui.modules.dinein.bottomsheets.DishReviewsSheetFragment;
import tdevm.app_ui.modules.dinein.bottomsheets.DishVariantsSheet;
import tdevm.app_ui.modules.dinein.callbacks.DishItemClickListener;
import tdevm.app_ui.modules.dinein.callbacks.DishVariantSelected;
import tdevm.app_ui.utils.CartHelper;

/**
 * Created by Tridev on 30-07-2017.
 */

public class SingleCuisineGridFragment extends Fragment
        implements DineInViewContract.SingleCuisineGridView, DishItemClickListener, DishVariantSelected,
        DishReviewsSheetFragment.Listener{

    public static final String TAG = SingleCuisineGridFragment.class.getSimpleName();
    public static final String CUISINE_ID = "CUISINE_ID";
    public static final String RESTAURANT_UUID = "RESTAURANT_UUID";

    private RecyclerView.LayoutManager mLayoutManager;
    @BindView(R.id.recycler_view_dishes_grid_single)
    RecyclerView recyclerViewGridSingle;
    Unbinder unbinder;
    private Map<String,String> fetchDishesMap;
    DishVariantsSheet dishVariantsSheet;
    RecycledGridMenuAdapter recycledGridMenuAdapter;

    @Inject
    SingleCuisineGridPresenter singleCuisineGridPresenter;
    @Inject
    CartHelper cartHelper;

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
        recyclerViewGridSingle.setLayoutManager(mLayoutManager);
        recycledGridMenuAdapter = new RecycledGridMenuAdapter(getActivity(),singleCuisineGridPresenter,cartHelper);
        recyclerViewGridSingle.setAdapter(recycledGridMenuAdapter);
        singleCuisineGridPresenter.fetchDishesByCuisines(fetchDishesMap);
        recycledGridMenuAdapter.setDishItemClickListenerCallback(this);
        dishVariantsSheet = new DishVariantsSheet();
        dishVariantsSheet.setOnDishVariantSelected(this);
        logSelections();
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
      recycledGridMenuAdapter.onDishesFetched(arrayList);
    }

    public void showDishVariantsSheet(ArrayList<DishVariant> arrayList, DishesOfCuisine parentDish){
        Bundle bundle = new Bundle();
        bundle.putParcelableArrayList("DISHES", arrayList);
        bundle.putParcelable("PARENT_DISH", parentDish);
        dishVariantsSheet.setArguments(bundle);
        if(!dishVariantsSheet.isAdded()) {
            dishVariantsSheet.show(getChildFragmentManager(), getTag());
        }
        Log.d(TAG,arrayList.toString());
    }

    @Override
    public void updateAdapter() {
        recycledGridMenuAdapter.notifyDataSetChanged();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        singleCuisineGridPresenter.detachView();
    }

    @Override
    public void onPlusButtonClicked(DishesOfCuisine dishesOfCuisine, int num) {
        singleCuisineGridPresenter.addItemToCart(dishesOfCuisine);
        Log.d(TAG,dishesOfCuisine.getDish_name());
    }

    @Override
    public void onMinusButtonClicked(DishesOfCuisine dishesOfCuisine, int num) {
        singleCuisineGridPresenter.updateCartItem(dishesOfCuisine);
        Log.d(TAG,dishesOfCuisine.getDish_name());
    }


    @Override
    public void onCustomizableItemClicked(DishesOfCuisine dishesOfCuisine, int flag) {
        fetchDishesMap.put("dish_id",dishesOfCuisine.getDish_id().toString());
        if(flag==1){
            //singleCuisineGridPresenter.fetchVariantsOfADish(fetchDishesMap, dishesOfCuisine);
            showDishVariantsSheet(dishesOfCuisine.getDish_variants(),dishesOfCuisine);
        }else {
            //
            Toast.makeText(getContext(), "Remove this item from cart!", Toast.LENGTH_SHORT).show();
        }
        Log.d(TAG,dishesOfCuisine.getDish_name());

    }

    @Override
    public void onCustomizableItemClicked(DishesOfCuisine dishesOfCuisine, Long parentDishId, int flag) {

    }

    @Override
    public void onDishImageClicked(DishesOfCuisine dishesOfCuisine) {
        //singleCuisineGridPresenter.fetchDishReviews();
        DishReviewsSheetFragment.newInstance(30,dishesOfCuisine).show(getChildFragmentManager(), "dialog");
    }


    @Override
    public void onDishVariantSelected(DishesOfCuisine childDish, DishesOfCuisine parentDish) {
        dishVariantsSheet.dismiss();

       // singleCuisineGridPresenter.addDishVariantItemToCart(childDish,parentDish);
    }

    @Override
    public void onDishVariantSelected(DishVariant dish, DishesOfCuisine parentDish) {
        Log.d("Child dish selected",dish.getDishVariantName()+dish.getVariantType()+"  "+parentDish.getDish_name());
    }

    public void logSelections() {
        cartHelper.logCartItems();

    }

    @Override
    public void onItemClicked(int position) {

    }
}
