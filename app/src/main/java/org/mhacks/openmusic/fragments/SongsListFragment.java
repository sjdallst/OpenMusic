package org.mhacks.openmusic.fragments;

import com.afollestad.materialdialogs.MaterialDialog;
import com.microsoft.windowsazure.mobileservices.MobileServiceException;
import com.microsoft.windowsazure.mobileservices.MobileServiceList;

import org.androidannotations.annotations.Background;
import org.androidannotations.annotations.Bean;
import org.androidannotations.annotations.Click;
import org.androidannotations.annotations.EFragment;
import org.androidannotations.annotations.UiThread;
import org.mhacks.openmusic.R;
import org.mhacks.openmusic.ui.adapters.SongsAdapter;
import org.mhacks.openmusic.utils.LogUtils;
import org.mhacks.openmusic.utils.database.tables.Songs;

import java.util.concurrent.ExecutionException;

@EFragment(R.layout.recyclerview_fragment)
public class SongsListFragment extends RecyclerViewFragment {

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

	@Click(android.R.id.button1)
	public void onNewSongClickButton() {
		switchFragment(new NewSongFragment_());
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
		Songs songs = mSongsAdapter.getItem(position);
		String name = songs.firstname + " " + songs.lastname;
		new MaterialDialog.Builder(getContext())
				.title(songs.title)
				.content(getString(R.string.composed_by, name))
				.positiveText(android.R.string.ok)
				.negativeText(android.R.string.cancel)
				.show();
		return true;
	}
}
