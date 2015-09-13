package org.mhacks.openmusic.models;

import java.util.List;

public class Song {

	public String id;
	public int tempo;
	public String firstName;
	public String lastName;
	public String title;
	public List<Note> notesList;

	public Song(String id, int tempo, String title, List<Note> notesList) {
		this.id = id;
		this.tempo = tempo;
		this.title = title;
		this.notesList = notesList;
	}
}
