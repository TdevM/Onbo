package app.onbo.root.fragments;


import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AlertDialog;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.facebook.shimmer.ShimmerFrameLayout;

import javax.inject.Inject;

import app.onbo.OnboApplication;
import app.onbo.R;
import app.onbo.api.models.response.UserApp;
import app.onbo.modules.account.activities.AboutActivity;
import app.onbo.modules.account.activities.ChangePasswordActivity;
import app.onbo.modules.account.activities.EditAccountDetailsActivity;
import app.onbo.modules.account.activities.FavouritesActivity;
import app.onbo.modules.account.activities.HelpSupportActivity;
import app.onbo.modules.orders.RestaurantOrdersActivity;
import app.onbo.root.RootActivity;
import app.onbo.root.RootActivityViewContract;
import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;

/**
 * A simple {@link Fragment} subclass.
 */
public class AccountsFragment extends Fragment implements RootActivityViewContract.AccountsFragmentView,
        SwipeRefreshLayout.OnRefreshListener {


    public static final String TAG = AccountsFragment.class.getSimpleName();
    @BindView(R.id.iv_user_profile_image)
    ImageView userProfileImage;
    @BindView(R.id.tv_logged_user_email)
    TextView userEmail;
    @BindView(R.id.tv_logged_user_mobile)
    TextView userMobile;
    @BindView(R.id.tv_logged_user_name)
    TextView userName;


    @BindView(R.id.tv_app_version)
    TextView appVersion;

    RootActivity activity;

    @OnClick(R.id.btn_logout_user)
    void click() {

    }


    @BindView(R.id.frame_layout_backend_error)
    FrameLayout backendError;


    @BindView(R.id.frame_layout_connection_broken)
    FrameLayout noInternet;


    @BindView(R.id.shimmer_fragment_profile_section)
    ShimmerFrameLayout shimmerFrameLayout;

    @OnClick(R.id.cardView_text_favorites)
    void showFavourites() {
        Intent intent = new Intent(getContext(), FavouritesActivity.class);
        startActivity(intent);
    }

    @OnClick(R.id.cardView_text_about_us)
    void showAbout() {
        Intent intent = new Intent(getContext(), AboutActivity.class);
        startActivity(intent);
    }

    @OnClick(R.id.cardView_text_help)
    void showHelpNSupport() {
        Intent intent = new Intent(getContext(), HelpSupportActivity.class);
        startActivity(intent);
    }

    @OnClick(R.id.cardView_text_logout)
    void logout() {
        logoutUser();
    }


    @BindView(R.id.swipeToRefresh_account_fragment)
    SwipeRefreshLayout swipeRefreshLayout;


    @OnClick(R.id.cardView_text_orders)
    void showOrders() {
        Intent intent = new Intent(getContext(), RestaurantOrdersActivity.class);
        startActivity(intent);
    }

    @OnClick(R.id.cardView_text_change_password)
    void showChangePassword() {
        Intent intent = new Intent(getContext(), ChangePasswordActivity.class);
        startActivity(intent);
    }

    @OnClick(R.id.tv_edit_profile_details)
    void showEditAccountDetails() {
        presenter.fetchUserEdit();
    }

    @Inject
    AccountsFragmentPresenter presenter;
    Unbinder unbinder;

    public AccountsFragment() {
        // Required empty public constructor
    }

    public static AccountsFragment newInstance() {
        Log.d("AccountsFragment", "new Instance() Called");
        return new AccountsFragment();
    }

    @Override
    public void onResume() {
        super.onResume();
        presenter.attachView(this);
        presenter.fetchUser();
    }

    @Override
    public void onAttach(Context context) {
        Log.d("AccountsFragment", "onAttach() Called");
        super.onAttach(context);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        Log.d("AccountsFragment", "onCreateView() Called");
        activity = (RootActivity) getActivity();
        resolveDaggerDependencies();
        View view;
        view = inflater.inflate(R.layout.fragment_accounts, container, false);
        unbinder = ButterKnife.bind(this, view);
        swipeRefreshLayout.setOnRefreshListener(this);
        swipeRefreshLayout.setColorSchemeResources(R.color.primary_default_app);


        return view;
    }


    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {

        Log.d("AccountsFragment", "onCreate() Called");

        super.onCreate(savedInstanceState);
    }

    @Override
    public void onPause() {
        Log.d("AccountsFragment", "onPause() Called");

        super.onPause();
    }


    @Override
    public void onUserFetched(UserApp userApp) {
        swipeRefreshLayout.setRefreshing(false);
        hideBackendError();
        hideNoInternetError();
        userName.setText(userApp.getUserName());
        userEmail.setText(userApp.getUserEmail());
        userMobile.setText(userApp.getUserMobile());
        if (userApp.getUserGender().equals("M")) {
            userProfileImage.setImageResource(R.drawable.ic_profile_pic_male_2fd3e8);
        } else if (userApp.getUserGender().equals("F")) {
            userProfileImage.setImageResource(R.drawable.ic_profile_pic_female_3c17ab);
        }
//        SvgLoader.pluck()
//                .with(getActivity())
//                .setPlaceHolder(R.mipmap.ic_launcher, R.mipmap.ic_launcher)
//                .load(userApp.getUserImageUrl(), userProfileImage);

        Log.d(TAG, "user details fetched");
    }

    @Override
    public void showProgressUI() {
        shimmerFrameLayout.setVisibility(View.VISIBLE);
        shimmerFrameLayout.startShimmer();
    }

    @Override
    public void hideProgressUI() {
        shimmerFrameLayout.setVisibility(View.GONE);
        shimmerFrameLayout.stopShimmer();
    }

    @Override
    public void resolveDaggerDependencies() {
        ((OnboApplication) activity.getApplication()).getApiComponent().inject(this);

    }

    @Override
    public void onUserFetchFailure() {
        Toast.makeText(getContext(), "Failed to fetch user", Toast.LENGTH_SHORT).show();
    }

    private void logoutUser() {
        new AlertDialog.Builder(activity)
                .setTitle("Logout")
                .setMessage("Are you sure you want to Logout?")
                .setPositiveButton("LOGOUT", (dialog, which) -> {
                    activity.logOutUser();
                    Log.d(TAG, "User logged out");
                })
                .setNegativeButton("CANCEL", (dialog, which) -> Log.d(TAG, "Aborting logout..."))
                .show();
    }

    @Override
    public void allowEdit(UserApp userApp) {
        Intent intent = new Intent(getContext(), EditAccountDetailsActivity.class);
        intent.putExtra("user_details", userApp);
        startActivity(intent);
    }

    @Override
    public void onLoggedOut() {
        Toast.makeText(getContext(), "Logged Out!", Toast.LENGTH_SHORT).show();
    }

    @Override
    public void showNoInternetError() {
        swipeRefreshLayout.setRefreshing(false);
        noInternet.setVisibility(View.VISIBLE);
    }

    @Override
    public void hideNoInternetError() {
        noInternet.setVisibility(View.GONE);
    }

    @Override
    public void hideBackendError() {
        backendError.setVisibility(View.GONE);

    }

    @Override
    public void showBackendError() {
        swipeRefreshLayout.setRefreshing(false);
        backendError.setVisibility(View.VISIBLE);

    }

    @Override
    public void onRefresh() {
        presenter.fetchUser();
    }


    /**
     * Interface for handling events from activity
     */
    public interface OnFragmentInteractionListener {
        // TODO: Update argument type and name
        void onFragmentInteraction(Uri uri);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        unbinder.unbind();
        presenter.detachView();
    }
}
