package com.sxy.scan.utils;


import android.text.TextUtils;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

/**
 * + * 获取手机的系统信息，例如判断是否是MIUI系统
 * +
 */
public class RomUtils {

    private static final String MIUI_PROP = "ro.miui.ui.version.name";
    private static final String EMUI_PROP = "ro.build.hw_emui_api_level";

    /**
     * 判断你是否是MIUI Rom
     * <a href="http://dev.xiaomi.com/doc/?p=254">参考链接 </a>
     */
    public static boolean isMIUIRom() {
        String property = getSystemProperty(MIUI_PROP);
        return !TextUtils.isEmpty(property);
    }

    /**
     * 判断你是否是华为EMUI Rom
     */
    public static boolean isEMUIRom() {
        String property = getSystemProperty(EMUI_PROP);
        return !TextUtils.isEmpty(property);
    }


    private static String getSystemProperty(String propName) {
        String line;
        BufferedReader input = null;
        try {
            Process p = Runtime.getRuntime().exec("getprop " + propName);
            input = new BufferedReader(new InputStreamReader(p.getInputStream()), 1024);
            line = input.readLine();
            input.close();
        } catch (IOException ex) {
            return null;
        } finally {
            if (input != null) {
                try {
                    input.close();
                } catch (IOException e) {
                    // do nothing
                }
            }
        }
        return line;
    }

}