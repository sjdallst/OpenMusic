package org.mhacks.openmusic.models;

import android.widget.ImageView;

import com.fasterxml.jackson.annotation.JsonIgnore;

public class Note {

	public int duration;
	public int midiNumber;
	public int xPosition;
	@JsonIgnore
	public ImageView imageView;

	public Note() {
	}

	public Note(int midi, int duration, int xPosition) {
		this.midiNumber = midi;
		this.duration = duration;
		this.xPosition = xPosition;
	}
}
