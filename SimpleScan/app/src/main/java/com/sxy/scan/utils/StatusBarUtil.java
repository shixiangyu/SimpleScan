package com.sxy.scan.utils;

import android.os.Build;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;

import java.lang.reflect.Field;
import java.lang.reflect.Method;

/**
 * StatusBar工具类，实现状态栏功能的兼容方案
 */
public class StatusBarUtil {

    /**
     * 设置status bar颜色
     */
    public static void setStatusBarColor(Window window, int color) {
        if (window != null && Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            window.setStatusBarColor(color);
        }
    }

    /**
     * 设置状态栏可以显示内容
     */
    public static void setStatusBarOverlay(Window window) {
        if (window != null && Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            int originStatus = window.getDecorView().getSystemUiVisibility();
            int targetStatus = originStatus
                    | View.SYSTEM_UI_FLAG_LAYOUT_STABLE
                    | View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN;
            window.getDecorView().setSystemUiVisibility(targetStatus);
        }
    }

    public static void hideStatusBar(Window window) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT
                && window != null) {
            int flags = View.SYSTEM_UI_FLAG_FULLSCREEN
                    | View.SYSTEM_UI_FLAG_LAYOUT_STABLE;
            window.getDecorView().setSystemUiVisibility(flags);

//            WindowManager.LayoutParams lp =  window.getAttributes();
//            lp.flags |= WindowManager.LayoutParams.FLAG_FULLSCREEN;
//            lp.flags &= (~WindowManager.LayoutParams.FLAG_TRANSLUCENT_NAVIGATION);
//
//            window.setAttributes(lp);
            window.addFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN);
//            window.addFlags(WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS);
        }
    }


    /**
     * 设置状态栏字体图标为深色，需要MIUIV6以上
     * @param window 需要设置的窗口
     * @param dark 是否把状态栏字体及图标颜色设置为深色
     * @return  boolean 成功执行返回true
     *
     */
    private static boolean MIUISetStatusBarLightMode(Window window, boolean dark) {
        boolean result = false;
        if (window != null) {
            Class clazz = window.getClass();
            try {
                int darkModeFlag;
                Class layoutParams = Class.forName("android.view.MiuiWindowManager$LayoutParams");
                Field field = layoutParams.getField("EXTRA_FLAG_STATUS_BAR_DARK_MODE");
                darkModeFlag = field.getInt(layoutParams);
                Method extraFlagField = clazz.getMethod("setExtraFlags", int.class, int.class);
                if(dark){
                    extraFlagField.invoke(window,darkModeFlag,darkModeFlag);//状态栏透明且黑色字体
                }else{
                    extraFlagField.invoke(window, 0, darkModeFlag);//清除黑色字体
                }
                result=true;
            } catch (Exception e){
                e.printStackTrace();
            }
        }
        return result;
    }
}
