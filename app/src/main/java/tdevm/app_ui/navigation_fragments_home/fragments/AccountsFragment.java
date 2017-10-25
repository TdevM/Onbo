package tdevm.app_ui.navigation_fragments_home.fragments;


import android.content.Context;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import tdevm.app_ui.R;

/**
 * A simple {@link Fragment} subclass.
 */
public class AccountsFragment extends Fragment {

    TextView tvName;
    TextView tvPhone;
    String token;

    public static final String AuthPreferences = "AUTH_PREFERENCE";
    public static final String loginStatePreference = "LOGIN_STATE";
    public static final String loginPhonePreference = "LOGIN_PHONE";
    public static final String loginTokenPreference = "LOGIN_TOKEN";

    SharedPreferences sharedpreferences;


    public AccountsFragment() {
        // Required empty public constructor
    }
    public static AccountsFragment newInstance() {
        Log.d("AccountsFragment", "new Instance() Called");
        return new AccountsFragment();
    }

    @Override
    public void onAttach(Context context) {
        Log.d("AccountsFragment", "onAttach() Called");
        Bundle bundle = this.getArguments();
        if(bundle!=null){
            token = bundle.getString(loginTokenPreference);
            //Log.d("AccountsFragment", "FromOnAttach(): "+token);
        }else {
            Log.d("AccountsFragment", "Bundle is null");
        }
        super.onAttach(context);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        Log.d("AccountsFragment", "onCreateView() Called");

        // Inflate the layout for this fragment
        View view;
        view = inflater.inflate(R.layout.fragment_accounts,container,false);
        tvName = (TextView)view.findViewById(R.id.tv_display_name);
        tvPhone = (TextView)view.findViewById(R.id.tv_display_phone);

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

    /**
     * Interface for handling events from activity
     */
    public interface OnFragmentInteractionListener {
        // TODO: Update argument type and name
        void onFragmentInteraction(Uri uri);
    }
}
