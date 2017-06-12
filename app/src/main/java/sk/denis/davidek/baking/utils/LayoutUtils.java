package sk.denis.davidek.baking.utils;

import android.content.Context;
import android.util.DisplayMetrics;

/**
 * Created by denis on 12.06.2017.
 */

public class LayoutUtils {

    public static int calculateNoOfColumns(Context context) {
        DisplayMetrics displayMetrics = context.getResources().getDisplayMetrics();
        float dpWidth = displayMetrics.widthPixels / displayMetrics.density;
        int noOfColumns = (int) (dpWidth / 180);
        return noOfColumns;
    }
}