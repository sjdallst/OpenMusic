package org.mhacks.openmusic.utils;

import android.content.Context;
import android.content.res.Resources;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.GradientDrawable;
import android.os.Build;

import org.mhacks.openmusic.R;

public class IconUtils {

	public static Drawable getIcon(Context context, char letter) {
		int sdk = Build.VERSION.SDK_INT;
		int resId = IconUtils.getIconColor(letter);
		Resources resources = context.getResources();
		GradientDrawable circle;
		if (sdk >= Build.VERSION_CODES.LOLLIPOP) {
			circle = (GradientDrawable)
					resources.getDrawable(R.drawable.circle, context.getTheme());
		}
		else {
			circle = (GradientDrawable) resources.getDrawable(R.drawable.circle);
		}
		circle.setColor(resources.getColor(resId));
		return circle;
	}

	private static int getIconColor(char letter) {
		switch(letter) {
			case 'A':case 'I':case 'Q':case 'Y':case '6':
				return R.color.yellow;
			case 'B':case 'J':case 'R':case 'Z':case '7':
				return R.color.purple;
			case 'C':case 'K':case 'S':case '0':case '8':
				return R.color.pink;
			case 'D':case 'L':case 'T':case '1':case '9':
				return R.color.light_red;
			case 'E':case 'M':case 'U':case '2':
				return R.color.light_orange;
			case 'F':case 'N':case 'V':case '3':
				return R.color.light_blue;
			case 'G':case 'O':case 'W':case '4':
				return R.color.green;
			case 'H':case 'P':case 'X':case '5':
				return R.color.indigo;
			default:
				return -1;
		}
	}
}
