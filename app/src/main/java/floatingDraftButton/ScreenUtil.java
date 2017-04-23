package floatingDraftButton;

import android.app.Activity;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Rect;
import android.util.DisplayMetrics;
import android.view.Display;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;

import java.lang.reflect.Method;

/**
 * Created by zhangbolun on 2017/4/15.
 */

public class ScreenUtil {
    public static int getDpi(Context context){ //获取屏幕原始尺寸高度，包括FloatinActionButton的高度
        int dpi = 0;
        WindowManager windowManager = (WindowManager) context.getSystemService(Context.WINDOW_SERVICE);
        Display display = windowManager.getDefaultDisplay();
        DisplayMetrics displayMetrics = new DisplayMetrics();
        @SuppressWarnings("rawtypes")
        Class c;
        try {
            c = Class.forName("android.view.Display");
            @SuppressWarnings("unchecked")
            Method method = c.getMethod("getRealMetrics",DisplayMetrics.class);
            method.invoke(display, displayMetrics);
            dpi=displayMetrics.heightPixels;
        }catch(Exception e){
            e.printStackTrace();
        }
        return dpi;
    }

    public static int getScreenWidth(Context context) { //获得屏幕的宽度
        WindowManager wm = (WindowManager) context
                .getSystemService(Context.WINDOW_SERVICE);
        DisplayMetrics outMetrics = new DisplayMetrics();
        wm.getDefaultDisplay().getMetrics(outMetrics);
        return outMetrics.widthPixels;
    }

    public static int getScreenHeight(Context context){ //获得屏幕的宽度
        WindowManager wm=(WindowManager)context
                .getSystemService(Context.WINDOW_SERVICE);
        DisplayMetrics outMetrics=new DisplayMetrics();
        wm.getDefaultDisplay().getMetrics(outMetrics);
        return outMetrics.heightPixels;
    }

    public static int getStatusHeight(Context context){ //获得状态栏高度
        int statusHeight=-1;
        try{
            Class<?> clazz=Class.forName("com.android.internal.R$dimen");
            Object object=clazz.newInstance();
            int height = Integer.parseInt(clazz.getField("status_bar_height")
                    .get(object).toString());
            statusHeight = context.getResources().getDimensionPixelSize(height);
        }catch (Exception e){
            e.printStackTrace();
        }
        return statusHeight;
    }

    public static  int getBottomStatusHeight(Context context){ //获取虚拟按键的高度
        return getDpi(context)  - getScreenHeight(context);
    }

    public static int getTitleHeight(Activity activity){ //获得标题栏的高度
        return  activity.getWindow().findViewById(Window.ID_ANDROID_CONTENT).getTop();
    }

    public static int getContentHeight(Context context){ //去除屏幕高度
        return getScreenHeight(context) - getStatusHeight(context);
    }

    public static Bitmap snapShotWithStatusBar(Activity activity){ //获得屏幕截图带状态栏
        View view = activity.getWindow().getDecorView();
        view.setDrawingCacheEnabled(true);
        view.buildDrawingCache();
        Bitmap bmp = view.getDrawingCache();
        int width = getScreenWidth(activity);
        int height = getScreenHeight(activity);
        Bitmap bp = null;
        bp = Bitmap.createBitmap(bmp, 0, 0, width, height);
        view.destroyDrawingCache();
        return bp;
    }

    public static Bitmap snapShotWithoutStatusBar(Activity activity){
        View view = activity.getWindow().getDecorView();
        view.setDrawingCacheEnabled(true);
        view.buildDrawingCache();
        Bitmap bmp = view.getDrawingCache();
        Rect frame=new Rect();
        activity.getWindow().getDecorView().getWindowVisibleDisplayFrame(frame);
        int statusBarHeight=frame.top;
        int width = getScreenWidth(activity);
        int height = getScreenHeight(activity);
        Bitmap bp = null;
        bp = Bitmap.createBitmap(bmp, 0, 0, width, height-statusBarHeight);
        view.destroyDrawingCache();
        return bp;
    }

}
