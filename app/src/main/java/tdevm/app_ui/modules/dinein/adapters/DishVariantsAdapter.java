package tdevm.app_ui.modules.dinein.adapters;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.RadioButton;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;
import tdevm.app_ui.R;
import tdevm.app_ui.api.models.response.DishVariant;
import tdevm.app_ui.api.models.response.DishesOfCuisine;
import tdevm.app_ui.modules.dinein.callbacks.DishVariantSelected;

/**
 * Created by Tridev on 20-03-2018.
 */

public class DishVariantsAdapter extends RecyclerView.Adapter<DishVariantsAdapter.ItemViewHolder> {

    public static final String TAG = DishVariantsAdapter.class.getSimpleName();

    private Context context;
    private ArrayList<DishVariant> dishVariants;
    private DishesOfCuisine parentDish;
    private int lastCheckedPosition = -1;
    DishVariantSelected mDishVariantSelected;


    public void setOnDishVariantSelected(DishVariantSelected dishVariantSelected){
        this.mDishVariantSelected = dishVariantSelected;
    }

    public DishVariantsAdapter(Context context, ArrayList<DishVariant> dishVariants, DishesOfCuisine dishesOfCuisine) {
        this.context = context;
        this.parentDish = dishesOfCuisine;
        this.dishVariants = dishVariants;
    }

    @NonNull
    @Override
    public ItemViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.dish_variants_single_item,parent, false);
        return new ItemViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull ItemViewHolder holder, int position) {
        DishVariant model = dishVariants.get(position);
        initializeViews(model,holder,position);

    }

    private void initializeViews(final DishVariant model, final RecyclerView.ViewHolder holder, int position) {
        String output = model.getVariantType().substring(0, 1) + model.getVariantType().substring(1).toLowerCase();
        ((ItemViewHolder)holder).dishName.setText(String.format("%s %s", model.getDishVariantName(), output));
        ((ItemViewHolder)holder).dishPrice.setText(String.valueOf(context.getString(R.string.rupee_symbol,model.getVariantPrice().intValue())));
        if (Integer.parseInt(model.getVariantId()) == lastCheckedPosition){
            ((ItemViewHolder)holder).radioButton.setChecked(true);
        }else{
            ((ItemViewHolder)holder).radioButton.setChecked(false);
        }
        ((ItemViewHolder)holder).radioButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                lastCheckedPosition = Integer.parseInt(model.getVariantId());
                notifyItemRangeChanged(0, dishVariants.size());
                mDishVariantSelected.onDishVariantSelected(dishVariants.get(position),parentDish);
            }
        });

    }


    @Override
    public int getItemCount() {
        return dishVariants.size();
    }

    public static class ItemViewHolder extends RecyclerView.ViewHolder {

        @BindView(R.id.dish_name_dish_variant)
        TextView dishName;
        @BindView(R.id.dish_price_dish_variant)
        TextView dishPrice;
        @BindView(R.id.radio_button_dish_variant)
        RadioButton radioButton;

        public ItemViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);

        }
    }
}
