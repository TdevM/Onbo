package tdevm.app_ui.modules.dinein.bottomsheets;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.BottomSheetDialogFragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;
import tdevm.app_ui.R;
import tdevm.app_ui.api.models.response.DishVariant;
import tdevm.app_ui.api.models.response.DishesOfCuisine;
import tdevm.app_ui.modules.dinein.adapters.DishVariantsAdapter;
import tdevm.app_ui.modules.dinein.callbacks.DishVariantSelected;


/**
 * Created by Tridev on 14-11-2017.
 */

public class DishVariantsSheet extends BottomSheetDialogFragment implements DishVariantSelected{

    @BindView(R.id.btn_variant_add) Button addCart;
    ArrayList<DishVariant> dishVariants;
    DishVariantSelected mDishVariantSelected;
    Unbinder unbinder;
    DishVariantsAdapter dishVariantsAdapter;
    @BindView(R.id.rv_variant_sheet_types)
    RecyclerView recyclerView;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }


    public void setOnDishVariantSelected(DishVariantSelected dishVariantSelected){
        this.mDishVariantSelected = dishVariantSelected;
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view  = inflater.inflate(R.layout.bottom_sheet_dish_variants, container, false);
        unbinder  = ButterKnife.bind(this,view);
        dishVariants = getArguments().getParcelableArrayList("DISHES");
        final DishesOfCuisine parent_dish = getArguments().getParcelable("PARENT_DISH");
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        dishVariantsAdapter = new DishVariantsAdapter(getContext(),dishVariants,parent_dish);
        recyclerView.setAdapter(dishVariantsAdapter);
        dishVariantsAdapter.setOnDishVariantSelected(this);
        return view;
    }

    @Override
    public void onDestroy() {
        unbinder.unbind();
        super.onDestroy();
    }

    @Override
    public void onDishVariantSelected(DishesOfCuisine childDish, DishesOfCuisine parentDish) {
        mDishVariantSelected.onDishVariantSelected(childDish,parentDish);
    }

    @Override
    public void onDishVariantSelected(DishVariant dish, DishesOfCuisine parentDish) {

    }
}
