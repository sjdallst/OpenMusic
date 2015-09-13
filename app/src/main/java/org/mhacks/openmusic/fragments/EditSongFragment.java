package org.mhacks.openmusic.fragments;

import org.androidannotations.annotations.AfterViews;
import org.androidannotations.annotations.EFragment;
import org.androidannotations.annotations.FragmentArg;
import org.androidannotations.annotations.OptionsItem;
import org.mhacks.openmusic.R;
import org.mhacks.openmusic.models.Note;
import org.mhacks.openmusic.models.Song;
import org.mhacks.openmusic.utils.database.tables.Songs;

import java.util.List;

@EFragment(R.layout.base_song_fragment)
public class EditSongFragment extends BaseSongFragment {

	@FragmentArg
	public Songs mSongArg;

	@AfterViews
	public void onViewChanged() {
		getActivity().setTitle(mSongArg.title);
		List<Note> notes = mMidiUtils.getNotes(mSongArg.song);
		mMediaSong = new Song(
				mSongArg.id,
				mSongArg.tempo,
				mSongArg.firstname,
				mSongArg.lastname,
				mSongArg.title,
				null);
		initialize(notes);
	}

	@OptionsItem(R.id.save_song_action)
	public void onSaveSong() {
		mDatabase.updateSong(mMediaSong);
	}
}
