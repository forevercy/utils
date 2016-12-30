package com.cytmxk.utils.fragment;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * Created by chenyang on 2016/12/2.
 */

@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
public @interface FragmentConfigure {

    /* fields */
    LaunchMode launchMode() default LaunchMode.Standard;

    FragmentType fragmentType() default FragmentType.Normal;

    
    /* inner type */
    enum LaunchMode {
        Standard(0), SingleTop(1), SingleTask(2);

        private int value;
        LaunchMode (int value) {
            this.value = value;
        }

        public int getValue() {
            return value;
        }

        public static LaunchMode valueOf(int value) {

            LaunchMode ret = Standard;

            for (LaunchMode type : LaunchMode.values()) {
                if (type.getValue() == value) {
                    ret = type;
                    break;
                }
            }

            return ret;
        }
    }

    enum FragmentType {
        Normal(0), Root(1);

        private int value;
        FragmentType (int value) {
            this.value = value;
        }

        public int getValue() {
            return value;
        }

        public static FragmentType valueOf(int value) {

            FragmentType ret = Normal;

            for (FragmentType type : FragmentType.values()) {
                if (type.getValue() == value) {
                    ret = type;
                    break;
                }
            }

            return ret;
        }
    }
}
