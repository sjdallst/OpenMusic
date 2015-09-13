package org.mhacks.openmusic.ui.listeners;

import android.view.DragEvent;
import android.view.View;

import org.mhacks.openmusic.R;
import org.mhacks.openmusic.models.Measure;
import org.mhacks.openmusic.models.Note;

import java.util.Collections;

public class MainDragListener implements View.OnDragListener {

	static final int [] midis = {77, 76, 74, 72, 71, 69, 67, 65, 64};

	private Measure mMeasure;

	public MainDragListener(Measure measure) {
		mMeasure = measure;
	}

	@Override
	public boolean onDrag(View v, DragEvent event) {
		switch (event.getAction()) {
			case DragEvent.ACTION_DRAG_STARTED:
				// do nothing
				break;
			case DragEvent.ACTION_DRAG_ENTERED:
				v.setBackgroundResource(R.drawable.shape_droptarget);
				break;
			case DragEvent.ACTION_DRAG_EXITED:
				v.setBackgroundResource(0);
				break;
			case DragEvent.ACTION_DROP:
				// Dropped, reassign View to ViewGroup
				View view = (View) event.getLocalState();
				float yPosition = v.getBottom() - (view.getHeight());
				view.setY(yPosition);
				int col = ((int) v.getTag())/16;
				int row = ((int) v.getTag()) - (col * 16);

				// swap with end
				if(col >= mMeasure.notes.size()) {
					col = mMeasure.notes.size() - 1;
				}

				Note note = (Note) view.getTag();
				note.midiNumber = midis[row];
				int j = note.xPosition;
				int i = col;
				Collections.swap(mMeasure.notes, i, j);
				note.xPosition = i;
				mMeasure.notes.get(j).xPosition = j;

				// Set the images to the correct position
				float tmpX = note.imageView.getX();
				note.imageView.setX(mMeasure.notes.get(j).imageView.getX());
				mMeasure.notes.get(j).imageView.setX(tmpX);

				view.setVisibility(View.VISIBLE);
				break;
			case DragEvent.ACTION_DRAG_ENDED:
				v.setBackgroundResource(0);
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
}
