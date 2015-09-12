package org.mhacks.openmusic.utils.database;

import android.content.Context;
import android.widget.Toast;

import com.google.common.util.concurrent.MoreExecutors;
import com.microsoft.windowsazure.mobileservices.MobileServiceClient;
import com.microsoft.windowsazure.mobileservices.table.MobileServiceTable;

import org.androidannotations.annotations.AfterInject;
import org.androidannotations.annotations.Bean;
import org.androidannotations.annotations.EBean;
import org.androidannotations.annotations.EBean.Scope;
import org.androidannotations.annotations.RootContext;
import org.mhacks.openmusic.R;
import org.mhacks.openmusic.models.Song;
import org.mhacks.openmusic.utils.LogUtils;
import org.mhacks.openmusic.utils.MidiUtils;
import org.mhacks.openmusic.utils.database.tables.Songs;

import java.net.MalformedURLException;

@EBean(scope = Scope.Singleton)
public class Database {

	@RootContext
	Context mContext;

	@Bean
	MidiUtils mMidiUtils;

	private MobileServiceClient mClient;

	@AfterInject
	public void afterInject() {
		try {
			mClient = new MobileServiceClient(
					"https://openmusic.azure-mobile.net/",
					"YFCSXLBTyQTgnupPXiXJDFCljNponA22",
					mContext);
		}
		catch (MalformedURLException exception) {
			LogUtils.error(exception);
		}
	}

	public void addSong(Song song) {
		Songs songs = new Songs();
		songs.song = mMidiUtils.getSongString(song);
		songs.title = song.title;
		songs.tempo = song.tempo;
		mClient.getTable(Songs.class).insert(songs).addListener(
				new Runnable() {
					@Override
					public void run() {
						try {
							Toast.makeText(
									mContext,
									R.string.successfully_shared_song,
									Toast.LENGTH_SHORT).show();
						}
						catch (Exception exception) {
							LogUtils.error(exception);
							Toast.makeText(
									mContext,
									R.string.failed_sharing_song,
									Toast.LENGTH_SHORT).show();
						}
					}
				}, MoreExecutors.directExecutor());
	}

	public MobileServiceTable<Songs> getSongs() {
		return mClient.getTable(Songs.class);
	}
}
