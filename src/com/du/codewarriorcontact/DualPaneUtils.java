package com.du.codewarriorcontact;

import android.app.Activity;
import android.view.View;

public class DualPaneUtils {
	public static boolean isDualPane(Activity activity, int viewIdToLookForInActivity) {
        View view = activity.findViewById(viewIdToLookForInActivity);
        return view != null && view.getVisibility() == View.VISIBLE;
    }
}