package org.mhacks.openmusic.fragments;

import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.SeekBar;
import android.widget.TextView;

import com.afollestad.materialdialogs.MaterialDialog;

import org.androidannotations.annotations.AfterViews;
import org.androidannotations.annotations.Bean;
import org.androidannotations.annotations.Click;
import org.androidannotations.annotations.EFragment;
import org.androidannotations.annotations.OptionsItem;
import org.androidannotations.annotations.OptionsMenu;
import org.androidannotations.annotations.ViewById;
import org.mhacks.openmusic.R;
import org.mhacks.openmusic.models.Measure;
import org.mhacks.openmusic.models.Note;
import org.mhacks.openmusic.models.Song;
import org.mhacks.openmusic.ui.adapters.RecyclerAdapter;
import org.mhacks.openmusic.ui.listeners.MainTouchListener;
import org.mhacks.openmusic.ui.listeners.TrashDragListener;
import org.mhacks.openmusic.utils.MidiUtils;
import org.mhacks.openmusic.utils.database.Database;

import java.util.ArrayList;
import java.util.List;

@EFragment(R.layout.base_song_fragment)
@OptionsMenu(R.menu.base_song_fragment_menu)
public abstract class BaseSongFragment extends Fragment {

	protected Song mMediaSong;

	@Bean
	MidiUtils mMidiUtils;
	@Bean
	Database mDatabase;

	@ViewById(R.id.trash_can)
	ImageView mTrashCan;

	private SeekBar mTempoSeekBar;
	private TextView mTempoTextView;

	List<Measure> mMeasureList = null;
	RecyclerAdapter mRecyclerAdapter;
	@ViewById(R.id.my_recycler_view)
	RecyclerView mRecyclerView;

	@AfterViews
	public void superOnViewChanged() {
		getActivity().setTitle(R.string.base_song_fragment_title);
		mRecyclerView.setLayoutManager(
				new LinearLayoutManager(
						getActivity(),
						LinearLayoutManager.HORIZONTAL,
						false));
		mMeasureList = new ArrayList<>();
		mRecyclerAdapter = new RecyclerAdapter(mMeasureList);
		mTrashCan.setOnDragListener(new TrashDragListener(mRecyclerAdapter));
//		List<Note> song = new ArrayList<>();
//		for (int i = 0; i < 40; ++i ) {
//			song.add(new Note(64, 4, 0));
//		}
//		initialize(song);
	}

	public void initialize(List<Note> notes) {
		mMeasureList = mMidiUtils.getMeasuresFromNotes(notes);
		mRecyclerAdapter.mMeasures.addAll(mMeasureList);
		mRecyclerView.setAdapter(mRecyclerAdapter);
		mMediaSong.measures = mMeasureList;
//		ArrayList<Measure> measureList = new ArrayList<>();
//
//		Measure measure = new Measure(0, new ArrayList<Note>(), 0);
//		int xPosition = 0;
//		for (int i = 0; i < notes.size(); ++i) {
//			if ((measure.numBeats + notes.get(i).duration) <= 16) {
//				measure.numBeats += notes.get(i).duration;
//				measure.notes.add(notes.get(i));
//				notes.get(i).xPosition = xPosition;
//				++xPosition;
//			}
//			else {
//				mRecyclerAdapter.addMeasure(measure);
//
//				measure = new Measure(0, new ArrayList<Note>(), 0);
//				xPosition = 0;
//
//				measure.numBeats += notes.get(i).duration;
//				measure.notes.add(notes.get(i));
//				notes.get(i).xPosition = xPosition;
//				++xPosition;
//			}
//		}
//		mRecyclerAdapter.addMeasure(measure);
//		mMeasureList = measureList;
	}

	@OptionsItem(R.id.change_tempo_action)
	public void onClickChangeTempo() {
		if (mMediaSong == null) {
			return;
		}

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
		if (mMediaSong == null) {
			return;
		}
		mMidiUtils.playMidi(mMediaSong);
	}

	@Click(R.id.sixteenthButton)
	void addSixteenth() {
		if(noteFits(1)) {
			addNoteToArray(1);
			displayNote(1);
		}
	}

	@Click(R.id.eigthButton)
	void addEight() {
		if(noteFits(2)) {
			addNoteToArray(2);
			displayNote(2);
		}
	}

	@Click(R.id.fourthButton)
	void addFourth() {
		if(noteFits(4)) {
			addNoteToArray(4);
			displayNote(4);
		}
	}

	@Click(R.id.halfButton)
	void addHalf() {
		if(noteFits(8)) {
			addNoteToArray(8);
			displayNote(8);
		}
	}

	@Click(R.id.wholeButton)
	void addWhole() {
		if(noteFits(16)) {
			addNoteToArray(16);
			displayNote(16);
		}
	}

	@Click(R.id.measureButton)
	void addMeasure() {
		mRecyclerAdapter.addMeasure(new Measure(0, new ArrayList<Note>(), 0));
		mRecyclerView.scrollToPosition(mMeasureList.size() - 1);
	}

	public boolean noteFits(int length) {
		if(mMeasureList.isEmpty()) {
			return false;
		}
		return mMeasureList.get(mMeasureList.size() - 1).numBeats + length <= 16;
	}

	public void addNoteToArray(int length) {
		mMeasureList.get(mMeasureList.size() - 1).numBeats += length;
		List<Note> notes = mMeasureList.get(mMeasureList.size() - 1).notes;
		notes.add(new Note(64, length, notes.size()));
	}

	public void displayNote(int length) {
		RelativeLayout rl = (RelativeLayout) (mMeasureList.get(mMeasureList.size() - 1).rootView);
		ImageView iv = new ImageView(getActivity());
		List<Note> notes = mMeasureList.get(mMeasureList.size() - 1).notes;
		iv.setTag(notes.get(notes.size() - 1));
		switch(length) {
			case 1:
				iv.setImageResource(R.drawable.ic_sixteenth_note);
				break;
			case 2:
				iv.setImageResource(R.drawable.ic_eight_note);
				break;
			case 4:
				iv.setImageResource(R.drawable.ic_quarter_note);
				break;
			case 8:
				iv.setImageResource(R.drawable.ic_half_note);
				break;
			case 16:
				iv.setImageResource(R.drawable.ic_whole_note);
				break;
		}
		notes.get(notes.size() - 1).imageView = iv; // set the imageview

		RelativeLayout.LayoutParams params = new RelativeLayout.LayoutParams(60, 120);
		params.leftMargin = RecyclerAdapter.getX(notes.size() - 1, rl);
		params.topMargin = rl.getBottom() - 120;
		iv.setOnTouchListener(new MainTouchListener());
		rl.addView(iv, params);

	}
}
