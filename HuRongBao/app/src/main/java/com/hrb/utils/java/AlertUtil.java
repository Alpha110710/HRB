package com.hrb.utils.java;

import android.content.Context;
import android.widget.Toast;

public class AlertUtil {

	public static void t(Context context, String msg) {
		Toast.makeText(context, msg, Toast.LENGTH_SHORT).show();
	}

	public static void t(Context context, int msgRes) {
		Toast.makeText(context, msgRes, Toast.LENGTH_SHORT).show();
	}

	public static void t(Context context, int msgRes, int duration) {
		Toast.makeText(context, msgRes, duration).show();
	}
	
	public static void t(Context context, String msg, int duration) {
		Toast.makeText(context, msg, duration).show();
	}
}
