package mhacks.openmusic.models;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by sjdallst on 9/12/2015.
 */
public class Song {
    public List<Note> notes;
    public int bpm;
    public String title;
    public int id;

    public Song() {
        notes = new ArrayList<>();
        bpm = 98;
        title = "";
        id = -1;
    }

    public Song(List<Note> notes, int bpm, String title, int id) {
        this.notes = new ArrayList<>();
        this.notes.addAll(notes);
        this.bpm = bpm;
        this.title = title;
        this.id = id;
    }
}
