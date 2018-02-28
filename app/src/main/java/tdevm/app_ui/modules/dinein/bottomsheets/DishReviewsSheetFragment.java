package tdevm.app_ui.modules.dinein.bottomsheets;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.BottomSheetDialogFragment;
import android.support.v4.app.Fragment;
import android.support.v7.widget.AppCompatImageView;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;

import java.util.ArrayList;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;
import tdevm.app_ui.AppApplication;
import tdevm.app_ui.R;
import tdevm.app_ui.api.models.response.DishReviews;
import tdevm.app_ui.api.models.response.DishesOfCuisine;
import tdevm.app_ui.modules.dinein.DineInViewContract;
import tdevm.app_ui.modules.dinein.adapters.DishReviewsAdapter;

public class DishReviewsSheetFragment extends BottomSheetDialogFragment implements DineInViewContract.DishReviewsSheetView{

    public static final String TAG = DishReviewsSheetFragment.class.getSimpleName();
    private static final String ARG_ITEM_COUNT = "item_count";
    private static final String DISH = "dish";
    private Listener mListener;

    private Unbinder unbinder;
    @Inject
    DishReviewsSheetPresenter presenter;

    @BindView(R.id.recycler_view_dish_reviews)
    RecyclerView recyclerView;
    DishReviewsAdapter adapter;
    @BindView(R.id.iv_dismiss_review_card)
    ImageView dismissCard;
    @BindView(R.id.tv_reviews_dish_description)
    TextView dishDescription;
    @BindView(R.id.tv_reviews_dish_name)
    TextView dishName;
    @BindView(R.id.iv_reviews_dish_image)
    ImageView dishImage;
    @BindView(R.id.iv_reviews_veg_non_veg_indicator)
    AppCompatImageView dishVegNonVeg;
    @BindView(R.id.tv_reviews_dish_rating_average)
    TextView avgRatingText;

    public static DishReviewsSheetFragment newInstance(int itemCount, DishesOfCuisine dishesOfCuisine) {
        final DishReviewsSheetFragment fragment = new DishReviewsSheetFragment();
        final Bundle args = new Bundle();
        args.putInt(ARG_ITEM_COUNT, itemCount);
        args.putParcelable(DISH,dishesOfCuisine);
        fragment.setArguments(args);
        return fragment;
    }


    @Override
    public void onResume() {
        super.onResume();
        presenter.attachView(this);

    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        resolveDaggerDependencies();
        View view  = inflater.inflate(R.layout.fragment_dish_reviews_sheet, container, false);
        unbinder = ButterKnife.bind(this,view);
        adapter = new DishReviewsAdapter(getArguments().getInt(ARG_ITEM_COUNT));
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        recyclerView.setAdapter(adapter);
        DishesOfCuisine d = getArguments().getParcelable(DISH);
        if(d!=null){
            presenter.fetchDishReview(d.getDish_id());
            updateDishInfoCard();
        }
        dismissCard.setOnClickListener(v -> this.dismiss());
        return view;
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        final Fragment parent = getParentFragment();
        if (parent != null) {
            mListener = (Listener) parent;
        } else {
            mListener = (Listener) context;
        }
    }

    @Override
    public void onDetach() {
        mListener = null;
        super.onDetach();
    }

    @Override
    public void showProgressUI() {

    }

    @Override
    public void hideProgressUI() {

    }

    @Override
    public void resolveDaggerDependencies() {
        ((AppApplication) getActivity().getApplication()).getApiComponent().inject(this);

    }

    @Override
    public void onDishReviewsFetched(ArrayList<DishReviews> body) {
        adapter.onDishReviewsFetched(body);
    }

    private void updateDishInfoCard() {
        DishesOfCuisine d = getArguments().getParcelable(DISH);
        if(d!=null){
            Glide.with(getContext()).load(d.getDish_image_url()).into(dishImage);
            dishImage.setScaleType(ImageView.ScaleType.CENTER_CROP);
            dishName.setText(d.getDish_name());
            dishDescription.setText(d.getDish_details());
            avgRatingText.setText("4.5");
            if(d.getDish_vegetarian()){
             dishVegNonVeg.setImageDrawable(getContext().getResources().getDrawable(R.drawable.veg_symbol));
            } else if (!d.getDish_vegetarian()) {
                dishVegNonVeg.setImageDrawable(getContext().getResources().getDrawable(R.drawable.non_veg_symbol));
            }
        }
    }

    public interface Listener {
        void onItemClicked(int position);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        presenter.detachView();
        unbinder.unbind();
    }
}
