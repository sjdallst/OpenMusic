package org.mhacks.openmusic.fragments;

import android.support.v4.app.Fragment;
import android.widget.TextView;

import org.androidannotations.annotations.AfterViews;
import org.androidannotations.annotations.Bean;
import org.androidannotations.annotations.EFragment;
import org.androidannotations.annotations.FragmentArg;
import org.androidannotations.annotations.OptionsItem;
import org.androidannotations.annotations.OptionsMenu;
import org.androidannotations.annotations.ViewById;
import org.mhacks.openmusic.R;
import org.mhacks.openmusic.models.Note;
import org.mhacks.openmusic.models.Song;
import org.mhacks.openmusic.utils.MidiUtils;
import org.mhacks.openmusic.utils.database.Database;
import org.mhacks.openmusic.utils.database.tables.Songs;

import java.util.List;

@EFragment(R.layout.edit_song_fragment)
@OptionsMenu(R.menu.edit_song_menu)
public class EditSongFragment extends Fragment {

	@FragmentArg
	public Songs mSongArg;
	public Song mMediaSong;

	@Bean
	MidiUtils mMidiUtils;
	@Bean
	Database mDatabase;

	@ViewById(android.R.id.text1)
	TextView mTextView;

	@AfterViews
	public void onViewChanged() {
		getActivity().setTitle("Song");

		List<Note> noteList = mMidiUtils.getNotes(mSongArg.song);
		mMediaSong = new Song(mSongArg.id, mSongArg.tempo, mSongArg.title, noteList);
		mTextView.setText(mMidiUtils.getSongString(mMediaSong));
	}

	@OptionsItem(R.id.play_song_action)
	public void onClickPlaySong() {
		mMidiUtils.playMidi(mMediaSong);
	}
}
