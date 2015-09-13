package org.mhacks.openmusic.fragments;

import android.widget.EditText;

import com.afollestad.materialdialogs.MaterialDialog;

import org.androidannotations.annotations.AfterViews;
import org.androidannotations.annotations.EFragment;
import org.androidannotations.annotations.OptionsItem;
import org.mhacks.openmusic.R;
import org.mhacks.openmusic.models.Note;
import org.mhacks.openmusic.models.Song;

import java.util.ArrayList;
import java.util.List;

@EFragment(R.layout.base_song_fragment)
public class NewSongFragment extends BaseSongFragment {

	private EditText mSongTitleEditText;
	private EditText mFirstNameEditText;
	private EditText mLastNameEditText;

	@AfterViews
	public void onViewChanged() {
		getActivity().setTitle(R.string.new_song_fragment_title);
		List<Note> noteList = new ArrayList<>();
//		noteList.add(new Note(64, 480, 0));
//		noteList.add(new Note(65, 480, 0));
//		noteList.add(new Note(67, 480, 0));
//		noteList.add(new Note(69, 480, 0));
//		noteList.add(new Note(71, 480, 0));
//		noteList.add(new Note(72, 480, 0));
//		noteList.add(new Note(74, 480, 0));
//		noteList.add(new Note(76, 480, 0));
//		mMediaSong = new Song("0", 120, null, null, "DoReMiFaSoLaTiDo", mMidiUtils
//				.getMeasuresFromNotes(noteList));
		mMediaSong = new Song(
				"song",
				120,
				"Bob",
				"Smith",
				"New",
				null);
		initialize(noteList);
	}

	@OptionsItem(R.id.save_song_action)
	public void onClickSaveSong() {
		MaterialDialog dialog = new MaterialDialog.Builder(getContext())
				.customView(R.layout.save_song_dialog, false)
				.positiveText(android.R.string.ok)
				.negativeText(android.R.string.cancel)
				.callback(
						new MaterialDialog.ButtonCallback() {
							@Override
							public void onPositive(MaterialDialog dialog) {
								mMediaSong.title = mSongTitleEditText.getText().toString();
								mMediaSong.firstName = mFirstNameEditText.getText().toString();
								mMediaSong.lastName = mLastNameEditText.getText().toString();
								mDatabase.addSong(mMediaSong);
							}

							@Override
							public void onNegative(MaterialDialog dialog) {
								super.onNegative(dialog);
							}
						}).build();
		mSongTitleEditText = (EditText) dialog.getCustomView().findViewById(android.R.id.input);
		mFirstNameEditText = (EditText) dialog.getCustomView().findViewById(android.R.id.text1);
		mLastNameEditText = (EditText) dialog.getCustomView().findViewById(android.R.id.text2);
		dialog.show();
	}
}
