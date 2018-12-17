package app.onbo.modules.account.fragments;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;
import app.onbo.R;


public class HNSWebViewFragment extends Fragment {


    public static final String TOS = "https://onbo.app/terms.html";
    public static final String REFUND_POLICY = "https://onbo.app/refund.html";
    public static final String PRIVACY_POLICY = "https://onbo.app/privacy.html";
    public static final String CONTACT = "https://onbo.app/contact.html";

    Unbinder unbinder;


    public HNSWebViewFragment() {
        // Required empty public constructor
    }


    @BindView(R.id.toolbar_fragment_web_view_content)
    Toolbar toolbar;

    @BindView(R.id.web_view_help_n_support)
    WebView webView;

    public static HNSWebViewFragment newInstance(String toolbarTitle, int titleValue) {
        HNSWebViewFragment fragment = new HNSWebViewFragment();
        Bundle args = new Bundle();
        args.putString("TOOLBAR_TITLE", toolbarTitle);
        args.putInt("TITLE_VALUE", titleValue);
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
        View view = inflater.inflate(R.layout.fragment_hnsweb_view, container, false);
        unbinder = ButterKnife.bind(this, view);
        AppCompatActivity activity = (AppCompatActivity) getActivity();
        if (activity != null) {
            activity.setSupportActionBar(toolbar);
            toolbar.setNavigationIcon(R.drawable.ic_arrow_back_black_24dp);
            toolbar.setNavigationOnClickListener(v -> activity.onBackPressed());
        }
        webView.setWebViewClient(new WebViewClient());
        WebSettings webSettings = webView.getSettings();
        webSettings.setJavaScriptEnabled(true);

        if (getArguments() != null) {
            String title = getArguments().getString("TOOLBAR_TITLE");
            int titleValue = getArguments().getInt("TITLE_VALUE");
            if (title != null) {
                toolbar.setTitle(title);
            }
            switch (titleValue) {
                case 0:
                    webView.loadUrl(TOS);
                    break;
                case 1:
                    webView.loadUrl(PRIVACY_POLICY);
                    break;
                case 2:
                    webView.loadUrl(REFUND_POLICY);
                    break;
                case 3:
                    webView.loadUrl(CONTACT);
                    break;

                default:

            }
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
