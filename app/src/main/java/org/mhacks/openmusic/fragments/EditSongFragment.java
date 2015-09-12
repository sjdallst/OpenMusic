package org.mhacks.openmusic.fragments;

import android.support.v4.app.Fragment;
import android.widget.SeekBar;
import android.widget.TextView;

import com.afollestad.materialdialogs.MaterialDialog;

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

	private SeekBar mTempoSeekBar;
	private TextView mTempoTextView;

	@AfterViews
	public void onViewChanged() {
		getActivity().setTitle(mSongArg.title);

		List<Note> noteList = mMidiUtils.getNotes(mSongArg.song);
		mMediaSong = new Song(mSongArg.id, mSongArg.tempo, mSongArg.title, noteList);
		mTextView.setText(mMidiUtils.getSongString(mMediaSong));
	}

	@OptionsItem(R.id.change_tempo_action)
	public void onClickChangeTempo() {
		MaterialDialog dialog = new MaterialDialog.Builder(getContext())
				.customView(R.layout.change_tempo_dialog, false)
				.positiveText(android.R.string.ok)
				.negativeText(android.R.string.cancel)
				.callback(
						new MaterialDialog.ButtonCallback() {
							@Override
							public void onPositive(MaterialDialog dialog) {
								mMediaSong.tempo = mTempoSeekBar.getProgress();
							}

							@Override
							public void onNegative(MaterialDialog dialog) {
								super.onNegative(dialog);
							}
						}).build();

		mTempoSeekBar = (SeekBar) dialog.getCustomView().findViewById(android.R.id.progress);
		mTempoTextView = (TextView) dialog.getCustomView().findViewById(android.R.id.text1);

		mTempoSeekBar.setProgress(mMediaSong.tempo);
		mTempoTextView.append(" ");
		mTempoTextView.append(Integer.toString(mTempoSeekBar.getProgress()));

		mTempoSeekBar.setOnSeekBarChangeListener(
				new SeekBar.OnSeekBarChangeListener() {
					@Override
					public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
						mTempoTextView.setText(R.string.tempo);
						mTempoTextView.append(" ");
						mTempoTextView.append(Integer.toString(mTempoSeekBar.getProgress()));
					}

					@Override
					public void onStartTrackingTouch(SeekBar seekBar) {
					}

					@Override
					public void onStopTrackingTouch(SeekBar seekBar) {
					}
				});

		dialog.show();
	}

	@OptionsItem(R.id.play_song_action)
	public void onClickPlaySong() {
		mMidiUtils.playMidi(mMediaSong);
	}

	@OptionsItem(R.id.save_song_action)
	public void onSaveSong() {
		mDatabase.updateSong(mMediaSong);
	}
}
