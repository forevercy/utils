package com.cytmxk.utils.animation;

import android.animation.ObjectAnimator;
import android.annotation.TargetApi;
import android.os.Build;
import android.view.View;
import android.view.animation.RotateAnimation;

/**
 * Created by chenyang on 16/4/13.
 */
public class PropertyAnimationUtils {
    /**
     * 让指定的view 执行 旋转离开的动画
     * @param view
     */
    public static void startRotateAnimRightOut(View view) {
        startRotateAnimRightOut(view, 0);
    }

    /**
     * 让指定view 延时 执行旋转离开的动画，
     * @param view
     * @param offset	延时的时间
     */
    @TargetApi(Build.VERSION_CODES.HONEYCOMB)
    public static void startRotateAnimRightOut(View view, long offset) {

        /*
		 * 默认圆为 为view的左上角，
		 * 水平向右 为 0度
		 * 顺时针旋转度数增加
		 */
        view.setPivotX(view.getWidth()/2);
        view.setPivotY(view.getHeight());
        ObjectAnimator objectAnimator = ObjectAnimator.ofFloat(view, "rotation", 0, 180).setDuration(500);
        objectAnimator.setStartDelay(offset);
        objectAnimator.start();
    }

    public static void startRotateAnimLeftIn(View view) {
        startRotateAnimLeftIn(view, 0);
    }

    @TargetApi(Build.VERSION_CODES.HONEYCOMB)
    public static void startRotateAnimLeftIn(View view, long offset) {

        view.setPivotX(view.getWidth()/2);
        view.setPivotY(view.getHeight());
        ObjectAnimator objectAnimator = ObjectAnimator.ofFloat(view, "rotation", 180, 360).setDuration(500);
        objectAnimator.setStartDelay(offset);
        objectAnimator.start();
    }
}
