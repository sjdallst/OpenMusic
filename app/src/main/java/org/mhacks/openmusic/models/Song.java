package org.mhacks.openmusic.models;

import java.util.List;

public class Song {

	public String id;
	public int tempo;
	public String firstName;
	public String lastName;
	public String title;
	public List<Measure> measures;

	public Song(
			String id,
			int tempo,
			String firstName,
			String lastName,
			String title,
			List<Measure> measures) {
		this.id = id;
		this.tempo = tempo;
		this.firstName = firstName;
		this.lastName = lastName;
		this.title = title;
		this.measures = measures;
	}
}
