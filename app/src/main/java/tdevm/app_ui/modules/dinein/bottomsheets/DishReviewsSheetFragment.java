package tdevm.app_ui.modules.dinein.bottomsheets;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.BottomSheetDialogFragment;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;
import tdevm.app_ui.AppApplication;
import tdevm.app_ui.R;
import tdevm.app_ui.api.models.response.DishReviews;
import tdevm.app_ui.modules.dinein.DineInViewContract;
import tdevm.app_ui.modules.dinein.adapters.DishReviewsAdapter;

public class DishReviewsSheetFragment extends BottomSheetDialogFragment implements DineInViewContract.DishReviewsSheetView{

    public static final String TAG = DishReviewsSheetFragment.class.getSimpleName();
    private static final String ARG_ITEM_COUNT = "item_count";
    private static final String DISH_ID = "dish_id";
    private Listener mListener;

    private Unbinder unbinder;
    @Inject
    DishReviewsSheetPresenter presenter;

    @BindView(R.id.recycler_view_dish_reviews)
    RecyclerView recyclerView;
    DishReviewsAdapter adapter;

    public static DishReviewsSheetFragment newInstance(int itemCount, Long dishId) {
        final DishReviewsSheetFragment fragment = new DishReviewsSheetFragment();
        final Bundle args = new Bundle();
        args.putInt(ARG_ITEM_COUNT, itemCount);
        args.putLong(DISH_ID,dishId);
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
        long dishId = getArguments().getLong(DISH_ID);
        adapter = new DishReviewsAdapter(getArguments().getInt(ARG_ITEM_COUNT));
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        recyclerView.setAdapter(adapter);
        presenter.fetchDishReview(dishId);
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
