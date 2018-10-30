package tdevm.app_ui.modules.dinein.fragments;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.bumptech.glide.Glide;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;
import tdevm.app_ui.R;

/**
 * A simple {@link Fragment} subclass.
 */
public class OrderSuccessFragment extends Fragment {


    Unbinder unbinder;

//    @BindView(R.id.iv_load_gif)
//    ImageView imageView;


    public OrderSuccessFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_order_success, container, false);
        unbinder = ButterKnife.bind(this,view);
//        Glide.with(getContext())
//                .load("https://mir-s3-cdn-cf.behance.net/project_modules/disp/a7a7b120588211.56042b16edecd.gif")
//                .into(imageView);
        return view;
    }


    @Override
    public void onDestroy() {
        super.onDestroy();
        unbinder.unbind();
    }
}
