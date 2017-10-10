package tdevm.app_ui.modules.auth.fragments;
import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;

import butterknife.BindView;
import butterknife.ButterKnife;
import tdevm.app_ui.R;
import tdevm.app_ui.modules.auth.AuthContract;

public class AuthInitFragment extends Fragment implements AuthContract.AuthView {

    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";
    private String mParam1;
    private String mParam2;


    @BindView(R.id.et_init_phone_number)
    private EditText phoneNumberInit;
    @BindView(R.id.btn_login_next)
    private Button btnLoginInit;



    private AuthInitInteractionListener mListener;


    public AuthInitFragment() {
        // Required empty public constructor
    }
    public static AuthInitFragment newInstance() {
        Log.d("AuthInitFragment", "new Instance() Called");
        return new AuthInitFragment();
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_auth_init, container, false);
        ButterKnife.bind(this,view);
        return view;
    }

    @Override
    public void showProgressUI() {

    }

    @Override
    public void hideProgressUI() {

    }

    @Override
    public void showPasswordInputFragment(Long phone) {

    }

    @Override
    public void showLoginError() {

    }

    @Override
    public void showRegisterFragment(Long phone) {

    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }


    // TODO: Rename method, update argument and hook method into UI event
    public void onButtonPressed(Uri uri) {
        if (mListener != null) {
            mListener.onAuthInitInteractionListener(uri);
        }
    }


    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof AuthInitInteractionListener) {
            mListener = (AuthInitInteractionListener) context;
        } else {
            throw new RuntimeException(context.toString()
                    + " must implement PickPowerFragmentInteractionListener");
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }

    /**
     * This interface must be implemented by activities that contain this
     * fragment to allow an interaction in this fragment to be communicated
     * to the activity and potentially other fragments contained in that
     * activity.
     * <p>
     * See the Android Training lesson <a href=
     * "http://developer.android.com/training/basics/fragments/communicating.html"
     * >Communicating with Other Fragments</a> for more information.
     */
    public interface AuthInitInteractionListener {
        // TODO: Update argument type and name
        void onAuthInitInteractionListener(Uri uri);
    }
}
