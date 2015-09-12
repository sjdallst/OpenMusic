package org.mhacks.openmusic.ui.adapters;

import android.content.Context;
import android.view.ViewGroup;

import org.androidannotations.annotations.EBean;
import org.androidannotations.annotations.RootContext;
import org.mhacks.openmusic.ui.itemviews.SongsItemView;
import org.mhacks.openmusic.ui.itemviews.SongsItemView_;
import org.mhacks.openmusic.utils.database.tables.Songs;

@EBean
public class SongsAdapter extends BaseAdapter<Songs, SongsItemView> {

	@RootContext
	Context mContext;

	@Override
	protected SongsItemView onCreateItemView(ViewGroup parent, int viewType) {
		return SongsItemView_.build(mContext);
	}
}
