package onbo.app.modules.account.fragments;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;
import onbo.app.R;
import onbo.app.modules.account.activities.HelpSupportActivity;


public class HNSActionList extends Fragment {

    public HNSActionList() {
        // Required empty public constructor
    }


    HelpSupportActivity activity;

    Unbinder unbinder;
    @BindView(R.id.toolbar_fragment_h_n_s_list)
    Toolbar toolbar;


    @OnClick(R.id.cardView_text_contact)
    void contact() {
        activity.showHNSContent("Contact", 3);
    }
    @OnClick(R.id.cardView_text_privacy_policy)
    void privacyPolicy() {
        activity.showHNSContent("Privacy Policy", 1);
    }
    @OnClick(R.id.cardView_text_refund_policy)
    void refundPolicy() {
        activity.showHNSContent("Refund Policy", 2);
    }
    @OnClick(R.id.cardView_text_terms_of_service)
    void termsOfService() {
        activity.showHNSContent("Terms of Service", 0);
    }
    public static HNSActionList newInstance() {
        HNSActionList fragment = new HNSActionList();
        Bundle args = new Bundle();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_hnsaction_list, container, false);
        unbinder = ButterKnife.bind(this, view);
        activity = (HelpSupportActivity) getActivity();
        AppCompatActivity activity = (AppCompatActivity) getActivity();
        if (activity != null) {
            activity.setSupportActionBar(toolbar);
            toolbar.setNavigationIcon(R.drawable.ic_arrow_back_black_24dp);
            toolbar.setNavigationOnClickListener(v -> activity.onBackPressed());
            toolbar.setTitle("Help and Support");
        }
        return view;
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);

    }

    @Override
    public void onDetach() {
        super.onDetach();

    }

}
