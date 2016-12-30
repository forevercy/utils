package com.cytmxk.utils.common;

import android.util.Log;
import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

/**
 * Created by chenyang on 2016/6/23.
 */
public class ReflectionUtils {

    private static final String TAG = ReflectionUtils.class.getCanonicalName();

    public static Class<?> getClassObj(String className) {
        try {
            return Class.forName(className);
        } catch (ClassNotFoundException e) {
            Log.i(TAG, "getClassObj ClassNotFoundException : " + e.getMessage());
        }
        return null;
    }

    public static Object getObj(String className, Class<?>[] parameterTypes, Object[] values) {
        Class<?> clz = getClassObj(className);
        try {
            Constructor<?> constructor = clz.getConstructor(parameterTypes);
            constructor.setAccessible(true);
            return constructor.newInstance(values);
        } catch (NoSuchMethodException e) {
            Log.i(TAG, "getObj NoSuchMethodException : " + e.getMessage());
        } catch (IllegalAccessException e) {
            Log.i(TAG, "getObj IllegalAccessException : " + e.getMessage());
        } catch (InstantiationException e) {
            Log.i(TAG, "getObj InstantiationException : " + e.getMessage());
        } catch (InvocationTargetException e) {
            Log.i(TAG, "getObj InvocationTargetException : " + e.getMessage());
        }
        return null;
    }

    public static Method getDeclaredMethod(String className, String methodName, Class<?>[] parameterTypes) {
        try {
            return getClassObj(className).getDeclaredMethod(methodName, parameterTypes);
        } catch (NoSuchMethodException e) {
            Log.i(TAG, "getDeclaredMethod NoSuchMethodException : " + e.getMessage());
        }
        return null;
    }

    public static Object invokeMethod(Object obj, Method method, Object[] values) {
        try {
            return method.invoke(obj, values);
        } catch (IllegalAccessException e) {
            Log.i(TAG, "invokeMethod IllegalAccessException : " + e.getMessage());
        } catch (InvocationTargetException e) {
            Log.i(TAG, "invokeMethod InvocationTargetException : " + e.getMessage());
        }
        return null;
    }

    public static Field getDeclaredField(String className, String fieldName) {
        try {
            return getClassObj(className).getDeclaredField(fieldName);
        } catch (NoSuchFieldException e) {
            Log.i(TAG, "getDeclaredField NoSuchFieldException : " + e.getMessage());
        }
        return null;
    }

    public static Object getFieldValue(Object obj, Field field) {
        try {
            return field.get(obj);
        } catch (IllegalAccessException e) {
            Log.i(TAG, "getFieldValue NoSuchFieldException : " + e.getMessage());
        }
        return null;
    }
}
