package mhacks.openmusic.models;

/**
 * Created by sjdallst on 9/12/2015.
 */
public class Note {
    public int midi;
    public int duration;
    public float xPosition;

    public Note(int midi, int duration, float xPosition) {
        this.midi = midi;
        this.duration = duration;
        this.xPosition = xPosition;
    }
}
