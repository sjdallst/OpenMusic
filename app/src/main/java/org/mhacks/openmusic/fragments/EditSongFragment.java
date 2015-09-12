package org.mhacks.openmusic.fragments;

import android.support.v4.app.Fragment;

import org.androidannotations.annotations.AfterViews;
import org.androidannotations.annotations.EFragment;
import org.androidannotations.annotations.OptionsMenu;

import org.mhacks.openmusic.R;
import org.mhacks.openmusic.models.Song;

@EFragment(R.layout.edit_song_fragment)
@OptionsMenu(R.menu.edit_song_menu)
public class EditSongFragment extends Fragment {

	@AfterViews
	public void onViewChanged() {
		getActivity().setTitle("Song");
		Song song = new Song();
	}
}
