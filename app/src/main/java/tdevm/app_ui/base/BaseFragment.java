package tdevm.app_ui.base;

import android.os.Bundle;
import android.provider.SyncStateContract;
import android.support.annotation.LayoutRes;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;

import butterknife.BindView;
import butterknife.ButterKnife;
import tdevm.app_ui.R;

/**
 * Created by Tridev on 12-10-2017.
 */

public abstract class BaseFragment extends Fragment {


    @LayoutRes
    protected abstract int getLayoutRes();
    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }



    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View v = inflater.inflate(getLayoutRes(), container, false);
        ButterKnife.bind(true,v);
        return v;
    }
}
