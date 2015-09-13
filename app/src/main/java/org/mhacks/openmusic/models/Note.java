package org.mhacks.openmusic.models;

public class Note {

	public int duration;
	public int midiNumber;
	public float xPosition;

	public Note() {
	}

	public Note(int midi, int duration, float xPosition) {
		this.midiNumber = midi;
		this.duration = duration;
		this.xPosition = xPosition;
	}
}
