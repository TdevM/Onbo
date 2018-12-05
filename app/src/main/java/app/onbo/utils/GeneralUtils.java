package app.onbo.utils;

import android.graphics.drawable.Drawable;
import android.os.Handler;
import android.os.Looper;
import android.support.graphics.drawable.Animatable2Compat;
import android.support.graphics.drawable.AnimatedVectorDrawableCompat;
import android.view.View;
import android.widget.ImageView;

import java.text.DecimalFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class GeneralUtils {


    public static String parseTime(String time) {
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm");
        Date date = null;
        try {
            date = dateFormat.parse(time);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        System.out.println(date);
        return date.toString();
    }


    public static String parseStringDouble(String value) {
        Double d = Double.parseDouble(value);
        double lowest = (d * 0.01);
        lowest = Double.parseDouble(new DecimalFormat("##.##").format(lowest));
        return String.valueOf(lowest);
    }


    public static void animate(View view) {
        ImageView v = (ImageView) view;
        Drawable d = v.getDrawable();
        AnimatedVectorDrawableCompat animatedVector = (AnimatedVectorDrawableCompat) d;
        final Handler mainHandler = new Handler(Looper.getMainLooper());
        animatedVector.registerAnimationCallback(new Animatable2Compat.AnimationCallback() {
            @Override
            public void onAnimationEnd(final Drawable drawable) {
                mainHandler.post(new Runnable() {
                    @Override
                    public void run() {
                        animatedVector.start();
                    }
                });
            }
        });
        animatedVector.start();
    }

//    public static void animateViewHolder(ViewHolder view) {
//        ImageView v = (ImageView) view;
//        Drawable d = v.getDrawable();
//        AnimatedVectorDrawableCompat animatedVector = (AnimatedVectorDrawableCompat) d;
//        final Handler mainHandler = new Handler(Looper.getMainLooper());
//        animatedVector.registerAnimationCallback(new Animatable2Compat.AnimationCallback() {
//            @Override
//            public void onAnimationEnd(final Drawable drawable) {
//                mainHandler.post(new Runnable() {
//                    @Override
//                    public void run() {
//                        animatedVector.start();
//                    }
//                });
//            }
//        });
//        animatedVector.start();
//    }
}
