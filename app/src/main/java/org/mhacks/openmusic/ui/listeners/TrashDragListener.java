package org.mhacks.openmusic.ui.listeners;

import android.util.Log;
import android.view.DragEvent;
import android.view.View;

import org.mhacks.openmusic.models.Measure;
import org.mhacks.openmusic.models.Note;
import org.mhacks.openmusic.ui.adapters.RecyclerAdapter;

import java.util.List;

public class TrashDragListener implements View.OnDragListener {
	List<Measure> mMeasureList = null;
	RecyclerAdapter mAdapter = null;

	public TrashDragListener(RecyclerAdapter adapter) {
		mAdapter = adapter;
		mMeasureList = mAdapter.mMeasures;
	}

	@Override
	public boolean onDrag(View v, DragEvent event) {
		switch (event.getAction()) {
			case DragEvent.ACTION_DRAG_STARTED:
				// do nothing
				break;
			case DragEvent.ACTION_DRAG_ENTERED:
				break;
			case DragEvent.ACTION_DRAG_EXITED:
				break;
			case DragEvent.ACTION_DROP:
				View iv = (View) event.getLocalState();
				Note note = (Note)(iv.getTag());
				Log.e("SAD", "ERASED SOMETHING");
				for(int i = 0; i < mMeasureList.size(); ++i) {
					if(mMeasureList.get(i).notes.indexOf(note) != -1) {
						int noteIndex = mMeasureList.get(i).notes.indexOf(note);
						mMeasureList.get(i).notes.remove(noteIndex);
						mMeasureList.get(i).numBeats -= note.duration;

						if(mMeasureList.get(i).numBeats == 0) {
							mAdapter.deleteMeasure(i);
						} else {
							removeNote(mMeasureList.get(i).notes, noteIndex, v);
						}
					}
				}
				break;
			case DragEvent.ACTION_DRAG_ENDED:
				if(!event.getResult()) {
					final View image = (View) event.getLocalState();

					image.post(new Runnable() {
											 @Override
											 public void run() {
												 image.setVisibility(View.VISIBLE);
											 }
										 });
				}
			default:
				break;
		}
		return true;
	}

	public void removeNote(List<Note> notes, int index, View view) {
		for(int i = index; i < notes.size(); ++i) {
			notes.get(i).xPosition -= 1;
			notes.get(i).imageView.setX(RecyclerAdapter.getX(notes.get(i).xPosition, view));
		}
	}
}
