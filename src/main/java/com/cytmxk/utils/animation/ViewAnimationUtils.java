package com.cytmxk.utils.animation;

import android.view.View;
import android.view.animation.RotateAnimation;

/**
 * Created by chenyang on 16/4/13.
 */
public class ViewAnimationUtils {

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
    public static void startRotateAnimRightOut(View view, long offset) {

        /*
		 * 默认圆为 为view的左上角，
		 * 水平向右 为 0度
		 * 顺时针旋转度数增加
		 */
        RotateAnimation rotateAnimation = new RotateAnimation(0, 180, view.getWidth()/2, view.getHeight());
        rotateAnimation.setDuration(500);
        rotateAnimation.setFillAfter(true);
        rotateAnimation.setStartOffset(offset);
        view.startAnimation(rotateAnimation);
    }

    public static void startRotateAnimLeftIn(View view) {
        startRotateAnimLeftIn(view, 0);
    }

    public static void startRotateAnimLeftIn(View view, long offset) {

        RotateAnimation rotateAnimation = new RotateAnimation(180, 360, view.getWidth()/2, view.getHeight());
        rotateAnimation.setDuration(500);
        rotateAnimation.setFillAfter(true);
        rotateAnimation.setStartOffset(offset);
        view.startAnimation(rotateAnimation);
    }
}
