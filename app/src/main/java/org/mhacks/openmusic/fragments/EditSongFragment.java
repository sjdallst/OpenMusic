package org.mhacks.openmusic.fragments;

import android.support.v4.app.Fragment;
import android.widget.TextView;

import org.androidannotations.annotations.AfterViews;
import org.androidannotations.annotations.Bean;
import org.androidannotations.annotations.EFragment;
import org.androidannotations.annotations.OptionsMenu;
import org.androidannotations.annotations.ViewById;
import org.mhacks.openmusic.R;
import org.mhacks.openmusic.utils.database.Database;
import org.mhacks.openmusic.utils.MidiUtils;

@EFragment(R.layout.edit_song_fragment)
@OptionsMenu(R.menu.edit_song_menu)
public class EditSongFragment extends Fragment {

	@Bean
	MidiUtils mMidiUtils;

	@ViewById(android.R.id.text1)
	TextView mTextView;

	@Bean
	Database mDatabase;

	@AfterViews
	public void onViewChanged() {
		getActivity().setTitle("Song");
//
//		List<Note> noteList = new ArrayList<>();
//		noteList.add(new Note(65, 480, 0));
//		noteList.add(new Note(66, 480, 0));
//		noteList.add(new Note(68, 480, 0));
//		noteList.add(new Note(70, 480, 0));
//		noteList.add(new Note(72, 480, 0));
//		noteList.add(new Note(74, 480, 0));
//		noteList.add(new Note(76, 480, 0));
//		noteList.add(new Note(78, 480, 0));
//		Song song = new Song(0, 120, "DoReMiFaSoLaTiDo", noteList);
//		mMidiUtils.playMidi(song);
//		mTextView.setText(mMidiUtils.getSongString(song));
//		mDatabase.addSong(song);
	}
}
