package org.mhacks.openmusic.models;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.List;

public class Song {

	@JsonProperty("id")
	private int mID;
	@JsonProperty("tempo")
	private int mTempo;
	@JsonProperty("title")
	private String mTitle;
	@JsonProperty("notes")
	private List<Note> mNotesList;

	public Song(int id, int bpm, String title, List<Note> notesList) {
		mID = id;
		mTempo = bpm;
		mTitle = title;
		mNotesList = notesList;
	}

	public int getID() {
		return mID;
	}

	public void setID(int ID) {
		mID = ID;
	}

	public int getTempo() {
		return mTempo;
	}

	public void setTempo(int tempo) {
		mTempo = tempo;
	}

	public String getTitle() {
		return mTitle;
	}

	public void setTitle(String title) {
		mTitle = title;
	}

	public List<Note> getNotesList() {
		return mNotesList;
	}

	public void setNotesList(List<Note> notesList) {
		mNotesList = notesList;
	}
}
