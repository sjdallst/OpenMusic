package org.mhacks.openmusic.ui.adapters;

import android.support.v7.widget.RecyclerView;
import android.util.DisplayMetrics;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;

import org.mhacks.openmusic.R;
import org.mhacks.openmusic.models.Measure;
import org.mhacks.openmusic.models.Note;
import org.mhacks.openmusic.ui.listeners.MainDragListener;
import org.mhacks.openmusic.ui.listeners.MainTouchListener;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class RecyclerAdapter extends RecyclerView.Adapter<RecyclerAdapter.ViewHolder> {
	public List<Measure> mMeasures;

	public static class ViewHolder extends RecyclerView.ViewHolder {
		public View mView;
		public ViewHolder(View v) {
			super(v);
			mView = v;
		}
	}

	public void addMeasure(Measure measure) {
		mMeasures.add(measure);
		notifyItemInserted(mMeasures.size() - 1);
	}

	public void deleteMeasure() {
		mMeasures.remove(mMeasures.size() - 1);
		notifyItemRemoved(mMeasures.size());
	}

	public void deleteMeasure(int index) {
		mMeasures.remove(index);
		notifyItemRemoved(index);
	}

	// Provide a suitable constructor (depends on the kind of dataset)
	public RecyclerAdapter(List<Measure> measures) {
		mMeasures = measures;
	}

	// Create new views (invoked by the layout manager)
	@Override
	public RecyclerAdapter.ViewHolder onCreateViewHolder(ViewGroup parent,
																											 int viewType) {
		// create a new view
		View v = LayoutInflater.from(parent.getContext())
				.inflate(R.layout.music_staff_item_view, parent, false);


		// set the view's size, margins, paddings and layout parameters
		ViewHolder vh = new ViewHolder(v);
		return vh;
	}

	// Replace the contents of a view (invoked by the layout manager)
	@Override
	public void onBindViewHolder(ViewHolder holder, int position) {
		// - get element from your dataset at this position
		// - replace the contents of the view with that element
		View v = holder.mView;

		mMeasures.get(position).rootView = v;

		final RelativeLayout rl = (RelativeLayout) v;
		rl.removeAllViews();
		for(int i = 0; i < 16; ++i) {
			int height = 0;
			for(int j = 0; j < 9; ++j) {
				View invisibleBlock= new View(v.getContext());
				int blockHeight = 0;
				if((j % 2) == 0) {
					blockHeight = 8;
				} else {
					blockHeight = 56;
				}
				RelativeLayout.LayoutParams params = new RelativeLayout.LayoutParams(dpToPx(500 / 16, rl), dpToPx(blockHeight, rl));
				params.leftMargin = i * dpToPx(500 / 16, rl);
				params.topMargin = dpToPx(height, rl);
				height += blockHeight;
				invisibleBlock.setOnDragListener(new MainDragListener(mMeasures.get(position)));

				// Set height and position
				invisibleBlock.setTag(16*i + j);
				rl.addView(invisibleBlock, params);
			}
		}

		displayNotes(rl, mMeasures.get(position).notes);
	}

	public void displayNotes(RelativeLayout rl, List<Note> notes) {
		for(int i = 0; i < notes.size(); ++i) {
			Note note = notes.get(i);
			ImageView iv = new ImageView(rl.getContext());
			iv.setTag(note);
			switch (note.duration) {
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
			note.imageView = iv; // set the imageview

			RelativeLayout.LayoutParams params = new RelativeLayout.LayoutParams(RecyclerAdapter.dpToPx(30, iv), RecyclerAdapter.dpToPx(60, iv));
			params.leftMargin = RecyclerAdapter.getX(i, rl);
			params.topMargin = dpToPx(252, rl) - yFromMidi(note.midiNumber, rl) - RecyclerAdapter.dpToPx(60, iv);
			iv.setOnTouchListener(new MainTouchListener());
			rl.addView(iv, params);
		}
	}



	// Return the size of your dataset (invoked by the layout manager)
	@Override
	public int getItemCount() {
		return mMeasures.size();
	}

	public static int dpToPx(int dp, View v) {
		DisplayMetrics displayMetrics = v.getContext().getResources().getDisplayMetrics();
		int px = Math.round(dp * (displayMetrics.xdpi / DisplayMetrics.DENSITY_DEFAULT));
		return px;
	}

	public static int getX(int xPosition, View v) {
		return xPosition * dpToPx(500 / 16, v);
	}

	public static int yFromMidi(int midi, View v) {
		Map<Integer, Integer> rMap = new HashMap<>();
		rMap.put(new Integer(64) , new Integer(0));
		rMap.put(new Integer(65), new Integer(8));
		rMap.put(new Integer(67), new Integer(61));
		rMap.put(new Integer(69), new Integer(69));
		rMap.put(new Integer(71), new Integer(69 + 53));
		rMap.put(new Integer(72), new Integer(69 + 53 + 8));
		rMap.put(new Integer(73), new Integer(69 + 53 + 8));
		rMap.put(new Integer(74), new Integer(69 + 53 + 8 + 53));
		rMap.put(new Integer(75), new Integer(69 + 53 + 8 + 53));
		rMap.put(new Integer(76), new Integer(69 + 53 + 8 + 53 + 8));
		rMap.put(new Integer(77), new Integer(69 + 53 + 69 + 53));

		return dpToPx(rMap.get(midi).intValue(), v);
	}
}

