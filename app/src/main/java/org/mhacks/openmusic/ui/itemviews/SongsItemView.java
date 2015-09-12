package org.mhacks.openmusic.ui.itemviews;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.widget.RelativeLayout;
import android.widget.TextView;

import org.androidannotations.annotations.EViewGroup;
import org.androidannotations.annotations.ViewById;
import org.mhacks.openmusic.R;
import org.mhacks.openmusic.utils.IconUtils;
import org.mhacks.openmusic.utils.database.tables.Songs;

@EViewGroup(R.layout.generic_item_view)
public class SongsItemView extends RelativeLayout implements ItemView.Binder<Songs> {

	@ViewById(android.R.id.icon)
	TextView mIcon;
	@ViewById(android.R.id.text1)
	TextView mText;

	public SongsItemView(Context context) {
		super(context);
	}

	@Override
	public void bind(Songs data) {
		Drawable circle = IconUtils.getIcon(getContext(), data.title.charAt(0));
		if (android.os.Build.VERSION.SDK_INT < Build.VERSION_CODES.JELLY_BEAN) {
			mIcon.setBackgroundDrawable(circle);
		}
		else {
			mIcon.setBackground(circle);
		}
		mIcon.setText(String.valueOf(data.title.charAt(0)));
		mText.setText(data.title);
	}
}