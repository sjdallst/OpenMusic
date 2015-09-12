package org.mhacks.openmusic.utils;

import android.content.Context;
import android.media.MediaPlayer;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.leff.midi.MidiFile;
import com.leff.midi.MidiTrack;
import com.leff.midi.event.meta.Tempo;
import com.leff.midi.event.meta.TimeSignature;

import org.androidannotations.annotations.EBean;
import org.androidannotations.annotations.EBean.Scope;
import org.androidannotations.annotations.RootContext;
import org.mhacks.openmusic.models.Note;
import org.mhacks.openmusic.models.Song;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

@EBean(scope = Scope.Singleton)
public class MidiUtils {

	@RootContext
	Context mContext;

	public void playMidi(Song song) {
		MidiTrack noteTrack = new MidiTrack();
		MidiTrack tempoTrack = new MidiTrack();
		Tempo tempo = new Tempo();
		TimeSignature timeSignature = new TimeSignature();
		ArrayList<MidiTrack> tracks = new ArrayList<MidiTrack>();
		timeSignature.setTimeSignature(
				3,
				4,
				TimeSignature.DEFAULT_METER,
				TimeSignature.DEFAULT_DIVISION);
		tempo.setBpm(song.getTempo());
		tempoTrack.insertEvent(timeSignature);
		tempoTrack.insertEvent(tempo);

		int currentTime = 0;
		for (Note note : song.getNotesList()) {
			noteTrack.insertNote(1, note.getMidiNumber(), 100, currentTime, note.getDuration());
			currentTime = currentTime + note.getDuration();
		}

		tracks.add(tempoTrack);
		tracks.add(noteTrack);
		MidiFile midi = new MidiFile(MidiFile.DEFAULT_RESOLUTION, tracks);
		File output = new File(mContext.getCacheDir(), "output.mid");
		try {
			midi.writeToFile(output);
		}
		catch (IOException exception) {
			LogUtils.error(exception);
		}

		try {
			MediaPlayer mediaPlayer = new MediaPlayer();
			mediaPlayer.setDataSource(output.getAbsolutePath());
			mediaPlayer.prepare();
			mediaPlayer.start();
		}
		catch (IOException exception) {
			LogUtils.error(exception);
		}

		output.delete();
	}

	public String getSongString(Song song) {
		try {
			final ObjectMapper objectMapper = new ObjectMapper();
			return objectMapper
					.writerWithDefaultPrettyPrinter()
					.writeValueAsString(song.getNotesList());
		}
		catch (IOException exception) {
			LogUtils.error(exception);
			return null;
		}
	}
}
