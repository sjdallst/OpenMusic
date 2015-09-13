package org.mhacks.openmusic.utils.database.tables;

import java.io.Serializable;

public class Songs implements Serializable {

	public String id;
	public String firstname;
	public String lastname;
	public String song;
	public int tempo;
	public String title;

	public Songs(
			String firstName,
			String lastName,
			String song,
			int tempo,
			String title) {
		this.firstname = firstName;
		this.lastname = lastName;
		this.song = song;
		this.tempo = tempo;
		this.title = title;
	}
}
