package tdevm.app_ui.root.fragments;


import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;
import tdevm.app_ui.AppApplication;
import tdevm.app_ui.R;
import tdevm.app_ui.api.models.response.UserApp;
import tdevm.app_ui.modules.account.activities.ChangePasswordActivity;
import tdevm.app_ui.modules.account.activities.EditAccountDetailsActivity;
import tdevm.app_ui.modules.orders.RestaurantOrdersActivity;
import tdevm.app_ui.root.NavigationHomeViewContract;

/**
 * A simple {@link Fragment} subclass.
 */
public class AccountsFragment extends Fragment implements NavigationHomeViewContract.AccountsFragmentView{


    public static final String TAG = AccountsFragment.class.getSimpleName();
    @BindView(R.id.iv_user_profile_image)
    ImageView userProfileImage;
    @BindView(R.id.tv_logged_user_email)
    TextView userEmail;
    @BindView(R.id.tv_logged_user_mobile)
    TextView userMobile;
    @BindView(R.id.tv_logged_user_name)
    TextView userName;
    @OnClick(R.id.btn_logout_user)
    void onClick(){
        presenter.logOutUser();
    }

    @OnClick(R.id.cardView_text_orders)
    void showOrders(){
        Intent intent = new Intent(getContext(), RestaurantOrdersActivity.class);
        startActivity(intent);
    }

    @OnClick(R.id.cardView_text_change_password)
    void showChangePassword(){
        Intent intent = new Intent(getContext(), ChangePasswordActivity.class);
        startActivity(intent);
    }

    @OnClick(R.id.tv_edit_profile_details)
    void showEditAccountDetails(){
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
        resolveDaggerDependencies();
        View view;
        presenter.fetchUser();
        view = inflater.inflate(R.layout.fragment_accounts,container,false);
        unbinder = ButterKnife.bind(this,view);
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
      userName.setText(userApp.getUserName());
      userEmail.setText(userApp.getUserEmail());
      userMobile.setText(userApp.getUserMobile());
        Log.d(TAG,"user details fetched");
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
    public void onUserFetchFailure() {
        Toast.makeText(getContext(), "Failed to fetch user", Toast.LENGTH_SHORT).show();
    }

    @Override
    public void allowEdit(UserApp userApp) {
        Intent intent = new Intent(getContext(), EditAccountDetailsActivity.class);
        intent.putExtra("user_details",userApp);
        startActivity(intent);
    }

    @Override
    public void onLoggedOut() {
        Toast.makeText(getContext(), "Logged Out!", Toast.LENGTH_SHORT).show();
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
