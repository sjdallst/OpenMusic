package org.mhacks.openmusic.utils;

import android.util.Log;

public class LogUtils {

	private static final String TAG = "Open Music";

	public static void log(String info) {
		String method = new Exception().getStackTrace()[1].getClassName();
		method += "." + new Exception().getStackTrace()[1].getMethodName();

		Log.d(TAG, method + "():- " + info);
	}

	public static void error(Exception exception) {
		String method = new Exception().getStackTrace()[1].getClassName();
		method += "." + new Exception().getStackTrace()[1].getMethodName();

		Log.e(TAG, method + "():- " + exception.toString());
	}
}
