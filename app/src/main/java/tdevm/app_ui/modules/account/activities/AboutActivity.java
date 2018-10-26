package tdevm.app_ui.modules.account.activities;

import android.graphics.drawable.AnimatedVectorDrawable;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.support.graphics.drawable.AnimatedVectorDrawableCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import tdevm.app_ui.R;

public class AboutActivity extends AppCompatActivity {

    @BindView(R.id.toolbar_about_activity)
    Toolbar toolbar;

    @OnClick(R.id.btn_start_anim)
    void startAnim(){
        animate(imageView);
    }

    @BindView(R.id.iv_show_anim)
    ImageView imageView;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_about);
        ButterKnife.bind(this);
        setSupportActionBar(toolbar);
        toolbar.setNavigationIcon(R.drawable.ic_arrow_back_black_24dp);
        toolbar.setNavigationOnClickListener(view -> onBackPressed());

    }


    public void animate(View view) {
        ImageView v = (ImageView) view;
        Drawable d = v.getDrawable();
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            if (d instanceof AnimatedVectorDrawable) {
                AnimatedVectorDrawable avd = (AnimatedVectorDrawable) d;
                avd.start();
            } else if (d instanceof AnimatedVectorDrawableCompat) {
                AnimatedVectorDrawableCompat avd = (AnimatedVectorDrawableCompat) d;
                avd.start();
            }
        }
    }
}
