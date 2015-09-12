package org.mhacks.openmusic.utils.database.tables;

import java.io.Serializable;

public class Songs implements Serializable {
	public String id;
	public String song;
	public int tempo;
	public String title;

	public Songs(String id, String song, int tempo, String title) {
		this.id = id;
		this.song = song;
		this.tempo = tempo;
		this.title = title;
	}
}
