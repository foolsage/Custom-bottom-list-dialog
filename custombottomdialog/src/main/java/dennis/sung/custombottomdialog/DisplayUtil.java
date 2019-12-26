package dennis.sung.custombottomdialog;

import android.content.Context;

/**
 * Created by Dennis on 2019-12-23
 */
public class DisplayUtil {
    /**
     * 將px值轉換為dip或dp值,保證尺寸大小不變
     */
    public static int px2dip(Context context, float pxValue) {
        final float scale = context.getResources().getDisplayMetrics().density;
        return (int) (pxValue / scale + 0.5f);
    }

    /**
     * 將dip或dp值轉換為px值,保證尺寸大小不變
     */
    public static int dip2px(Context context, float dipValue) {
        final float scale = context.getResources().getDisplayMetrics().density;
        return (int) (dipValue * scale + 0.5f);
    }

    /**
     * 將px值轉換為sp值,保證文字大小不變
     */
    public static int px2sp(Context context, float pxValue) {
        final float fontScale = context.getResources().getDisplayMetrics().scaledDensity;
        return (int) (pxValue / fontScale + 0.5f);
    }

    /**
     * 將sp值轉換為px值,保證文字大小不變
     */
    public static int sp2px(Context context, float spValue) {
        final float fontScale = context.getResources().getDisplayMetrics().scaledDensity;
        return (int) (spValue * fontScale + 0.5f);
    }
}
