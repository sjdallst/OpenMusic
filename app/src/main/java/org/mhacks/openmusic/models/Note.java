package org.mhacks.openmusic.models;

import com.fasterxml.jackson.annotation.JsonProperty;

public class Note {

	@JsonProperty("duration")
	private int mDuration;
	@JsonProperty("midi_number")
	private int mMidiNumber;
	@JsonProperty("x_position")
	private float mxPosition;

	public Note(int midi, int duration, float xPosition) {
		mMidiNumber = midi;
		mDuration = duration;
		mxPosition = xPosition;
	}

	public int getDuration() {
		return mDuration;
	}

	public void setDuration(int duration) {
		mDuration = duration;
	}

	public int getMidiNumber() {
		return mMidiNumber;
	}

	public void setMidiNumber(int midiNumber) {
		mMidiNumber = midiNumber;
	}

	public float getxPosition() {
		return mxPosition;
	}

	public void setxPosition(float mxPosition) {
		this.mxPosition = mxPosition;
	}
}
