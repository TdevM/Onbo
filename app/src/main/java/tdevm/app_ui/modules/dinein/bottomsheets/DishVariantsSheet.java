package tdevm.app_ui.modules.dinein.bottomsheets;

import android.annotation.SuppressLint;
import android.app.Dialog;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.BottomSheetDialogFragment;
import android.view.View;
import android.widget.Button;
import android.widget.RadioButton;
import android.widget.RadioGroup;

import java.util.ArrayList;
import java.util.Iterator;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;
import tdevm.app_ui.R;
import tdevm.app_ui.api.models.response.DishesOfCuisine;
import tdevm.app_ui.modules.dinein.callbacks.DishVariantSelected;


/**
 * Created by Tridev on 14-11-2017.
 */

public class DishVariantsSheet extends BottomSheetDialogFragment {

    @BindView(R.id.btn_variant_add) Button addCart;
    ArrayList<DishesOfCuisine> dishesOfCuisines;
    DishVariantSelected mDishVariantSelected;
    Unbinder unbinder;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    public void setOnDishVariantSelected(DishVariantSelected dishVariantSelected){
        this.mDishVariantSelected = dishVariantSelected;
    }


    @SuppressLint("RestrictedApi")
    @Override
    public void setupDialog(Dialog dialog, int style) {
        super.setupDialog(dialog, style);
        final View contentView = View.inflate(getContext(), R.layout.bottom_sheet_dish_variants, null);
        unbinder  = ButterKnife.bind(this,contentView);
        dishesOfCuisines = getArguments().getParcelableArrayList("DISHES");
        final DishesOfCuisine d = getArguments().getParcelable("PARENT_DISH");
        final RadioGroup radioGroup = contentView.findViewById(R.id.radio_group_variants);
        for (int i = 0; i < dishesOfCuisines.size(); i++) {
            RadioButton btn = new RadioButton(getContext());
            btn.setId(dishesOfCuisines.get(i).getDish_id().intValue());
            btn.setText(dishesOfCuisines.get(i).getDish_name() + "  " + getContext().getString(R.string.rupee_symbol,dishesOfCuisines.get(i).getDish_price().intValue()));
            radioGroup.addView(btn);
        }

        addCart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                int selectedId = radioGroup.getCheckedRadioButtonId();
                Iterator<DishesOfCuisine> arrayListIterator = dishesOfCuisines.listIterator();
                while (arrayListIterator.hasNext()){
                    DishesOfCuisine dishesOfCuisine = arrayListIterator.next();
                    if(selectedId == dishesOfCuisine.getDish_id().intValue()){
                        mDishVariantSelected.onDishVariantSelected(dishesOfCuisine,d);
                    }
                }
            }
        });
        dialog.setContentView(contentView);
    }

    @Override
    public void onDestroy() {
        unbinder.unbind();
        super.onDestroy();
    }
}
