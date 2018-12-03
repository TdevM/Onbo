package onbo.app.modules.dinein.bottomsheets;

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
import onbo.app.AppApplication;
import onbo.app.R;
import onbo.app.api.models.response.v2.reviews.DishReviews;
import onbo.app.api.models.response.v2.menu.MenuItem;
import onbo.app.modules.dinein.DineInViewContract;
import onbo.app.modules.dinein.adapters.DishReviewsAdapter;

public class DishReviewsSheetFragment extends BottomSheetDialogFragment implements DineInViewContract.DishReviewsSheetView{

    public static final String TAG = DishReviewsSheetFragment.class.getSimpleName();
    private static final String ARG_ITEM_COUNT = "item_count";
    private static final String DISH = "dish";
    private DishReviewsSheetListener mDishReviewsSheetListener;

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

    public static DishReviewsSheetFragment newInstance(int itemCount, MenuItem menuItem) {
        final DishReviewsSheetFragment fragment = new DishReviewsSheetFragment();
        final Bundle args = new Bundle();
        args.putInt(ARG_ITEM_COUNT, itemCount);
        args.putParcelable(DISH,menuItem);
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
        MenuItem item = getArguments().getParcelable(DISH);
        if(item!=null){
            presenter.fetchMenuItemReview(Long.parseLong(item.getItemId()));
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
            mDishReviewsSheetListener = (DishReviewsSheetListener) parent;
        } else {
            mDishReviewsSheetListener = (DishReviewsSheetListener) context;
        }
    }

    @Override
    public void onDetach() {
        mDishReviewsSheetListener = null;
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
        MenuItem menuItem = getArguments().getParcelable(DISH);
        if(menuItem!=null){
            Glide.with(getContext()).load(menuItem.getItemImage()).into(dishImage);
            dishImage.setScaleType(ImageView.ScaleType.CENTER_CROP);
            dishName.setText(menuItem.getItemName());
            dishDescription.setText(menuItem.getDescription());
            avgRatingText.setText("4.5");
            if(menuItem.getIsVeg()){
             dishVegNonVeg.setImageDrawable(getContext().getResources().getDrawable(R.drawable.veg_symbol));
            } else if (!menuItem.getIsVeg()) {
                dishVegNonVeg.setImageDrawable(getContext().getResources().getDrawable(R.drawable.non_veg_symbol));
            }
        }
    }

    public interface DishReviewsSheetListener {
        void onItemClicked(int position);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        presenter.detachView();
        unbinder.unbind();
    }
}
