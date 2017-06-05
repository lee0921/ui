package com.lee.ui.utils;

import android.app.Activity;
import android.app.KeyguardManager;
import android.content.Context;
import android.provider.Settings;
import android.util.DisplayMetrics;
import android.util.TypedValue;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;

import java.lang.reflect.Field;

/**
 *  屏幕工具类
 */

public class ScreenUtils {
    public ScreenUtils() {
    }

    public static float dpToPx(Context context, float dp) {
        return context == null ? -1.0F : dp * context.getResources().getDisplayMetrics().density;
    }

    public static int dpToPxInt(Context context, float dp) {
        return (int)(dpToPx(context, dp) + 0.5F);
    }

    public static int sp2px(Context context, float spValue) {
        float fontScale = context.getResources().getDisplayMetrics().scaledDensity;
        return (int)(spValue * fontScale + 0.5F);
    }

    public static float pxToDp(Context context, float px) {
        return context != null && px != 0.0F?px / context.getResources().getDisplayMetrics().density:-1.0F;
    }

    public static int pxToDpInt(Context context, float px) {
        return (int)(pxToDp(context, px) + 0.5F);
    }

    public static int getStatusBarHeight(Context mContext) {
        Class c = null;
        Object obj = null;
        Field field = null;
        boolean x = false;
        int sbar = 0;

        try {
            c = Class.forName("com.android.internal.R$dimen");
            obj = c.newInstance();
            field = c.getField("status_bar_height");
            int x1 = Integer.parseInt(field.get(obj).toString());
            sbar = mContext.getResources().getDimensionPixelSize(x1);
        } catch (Exception var7) {
            var7.printStackTrace();
        }

        return sbar;
    }

    public static int getActionBarHeight(Activity activity) {
        TypedValue tv = new TypedValue();
        return activity.getTheme().resolveAttribute(16843499, tv, true) ?
                TypedValue.complexToDimensionPixelSize(tv.data, activity.getResources().getDisplayMetrics()) : 0;
    }

    public static int getScreenWidth(Context context) {
        if(context == null) {
            return 0;
        } else {
            WindowManager windowManager = (WindowManager)context.getSystemService("window");
            DisplayMetrics metric = new DisplayMetrics();
            windowManager.getDefaultDisplay().getMetrics(metric);
            return metric.widthPixels;
        }
    }

    public static int getScreenHeight(Context context) {
        if(context == null) {
            return 0;
        } else {
            WindowManager windowManager = (WindowManager)context.getSystemService("window");
            DisplayMetrics metric = new DisplayMetrics();
            windowManager.getDefaultDisplay().getMetrics(metric);
            return metric.heightPixels;
        }
    }

    public static boolean isLandscape(Context context) {
        return context.getApplicationContext().getResources().getConfiguration().orientation == 2;
    }

    public static boolean isPortrait(Context context) {
        return context.getApplicationContext().getResources().getConfiguration().orientation == 1;
    }

    public static void setLandscape(Activity activity) {
        activity.setRequestedOrientation(0);
    }

    public static void setPortrait(Activity activity) {
        activity.setRequestedOrientation(1);
    }

    public static boolean isFullScreen(Activity activity) {
        int flag = activity.getWindow().getAttributes().flags;
        return (flag & 1024) == 1024;
    }

    public static boolean hasNavBar(Context context) {
        int id = context.getResources().getIdentifier("config_showNavigationBar", "bool", "android");
        return id > 0 ? context.getResources().getBoolean(id) : false;
    }

    public static void hideSoftInput(Activity activity) {
        View view = activity.getCurrentFocus();
        if(view == null) {
            view = new View(activity);
        }

        InputMethodManager imm = (InputMethodManager)activity.getSystemService("input_method");
        imm.hideSoftInputFromWindow(view.getWindowToken(), 0);
    }

    public static void showSoftInput(Context context, EditText edit) {
        if(context != null && edit != null) {
            edit.setFocusable(true);
            edit.setFocusableInTouchMode(true);
            edit.requestFocus();
            InputMethodManager imm = (InputMethodManager)context.getSystemService("input_method");
            imm.showSoftInput(edit, 0);
        }
    }

    public static boolean isScreenLock(Context context) {
        KeyguardManager km = (KeyguardManager)context.getSystemService("keyguard");
        return km.inKeyguardRestrictedInputMode();
    }

    public static int getSystemScreenBrightness(Context context) {
        int values = 0;

        try {
            values = Settings.System.getInt(context.getContentResolver(), "screen_brightness");
        } catch (Settings.SettingNotFoundException var3) {
            var3.printStackTrace();
        }

        return values;
    }

    public static void setActivityScreenBrightness(Activity activity, int paramInt) {
        if(activity != null) {
            Window localWindow = activity.getWindow();
            WindowManager.LayoutParams localLayoutParams = localWindow.getAttributes();
            localLayoutParams.screenBrightness = calculationScreenBrightnessValue(paramInt);
            localWindow.setAttributes(localLayoutParams);
        }
    }

    public static float calculationScreenBrightnessValue(int paramInt) {
        if(paramInt < 0) {
            paramInt = 0;
        }

        if(paramInt > 255) {
            paramInt = 255;
        }

        return (float)paramInt / 255.0F;
    }

    public static void resetActivityScreenBrightness(Activity activity) {
        if(activity != null) {
            Window localWindow = activity.getWindow();
            WindowManager.LayoutParams localLayoutParams = localWindow.getAttributes();
            localLayoutParams.screenBrightness = -1.0F;
            localWindow.setAttributes(localLayoutParams);
        }
    }
}
