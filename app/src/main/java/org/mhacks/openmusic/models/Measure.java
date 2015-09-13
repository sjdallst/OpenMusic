package org.mhacks.openmusic.models;

import android.view.View;

import java.util.List;

public class Measure {

	public int numBeats;
	public List<Note> notes;
	public int nextOpenPosition;
	public View rootView = null;

	public Measure(int numBeats, List<Note> notes, int nextOpenPosition) {
		this.numBeats = numBeats;
		this.notes = notes;
		this.nextOpenPosition = nextOpenPosition;
	}
}
