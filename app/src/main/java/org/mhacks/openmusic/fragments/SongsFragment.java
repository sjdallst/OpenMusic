package org.mhacks.openmusic.fragments;

import com.microsoft.windowsazure.mobileservices.MobileServiceException;
import com.microsoft.windowsazure.mobileservices.MobileServiceList;

import org.androidannotations.annotations.Background;
import org.androidannotations.annotations.Bean;
import org.androidannotations.annotations.EFragment;
import org.androidannotations.annotations.UiThread;
import org.mhacks.openmusic.R;
import org.mhacks.openmusic.ui.adapters.SongsAdapter;
import org.mhacks.openmusic.utils.LogUtils;
import org.mhacks.openmusic.utils.database.tables.Songs;

import java.util.concurrent.ExecutionException;

@EFragment(R.layout.recyclerview_fragment)
public class SongsFragment extends RecyclerViewFragment {

	@Bean
	SongsAdapter mSongsAdapter;

	@Override
	protected void init() {
		setEmptyText(R.string.no_songs);
		setRecyclerViewAdapter(mSongsAdapter);
		setTitle(R.string.songs_fragment_title);
	}

	@Background
	@Override
	protected void fetch() {
		try {
			MobileServiceList<Songs> result = mDatabase.getSongs().execute().get();
			updateAdapter(result);
			setLoadingSucceeded();
		}
		catch (ExecutionException | InterruptedException | MobileServiceException exception) {
			LogUtils.error(exception);
			setLoadingFailed();
		}
	}

	@UiThread
	public void updateAdapter(MobileServiceList<Songs> result) {
		mSongsAdapter.addAll(result);
	}

	@Override
	protected void onRecyclerViewItemClick(int position) {
		switchFragment(
				new EditSongFragment_()
						.builder()
						.mSongArg(mSongsAdapter.getItem(position))
						.build());
	}

	@Override
	protected boolean onRecyclerViewItemLongClick(int position) {
		return false;
	}
}
