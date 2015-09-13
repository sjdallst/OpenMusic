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
import org.mhacks.openmusic.models.Measure;
import org.mhacks.openmusic.models.Note;
import org.mhacks.openmusic.models.Song;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

@EBean(scope = Scope.Singleton)
public class MidiUtils {

	@RootContext
	Context mContext;

	MediaPlayer mediaPlayer = new MediaPlayer();
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
		tempo.setBpm(song.tempo);
		tempoTrack.insertEvent(timeSignature);
		tempoTrack.insertEvent(tempo);

		int currentTime = 0;
		for (Measure measure : song.measures) {
			for (Note note : measure.notes) {
				noteTrack.insertNote(1, note.midiNumber, 100, currentTime, note.duration*120);
				currentTime = currentTime + (note.duration*120);
			}
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
			if(mediaPlayer.isPlaying()) {
				mediaPlayer.stop();
				mediaPlayer.reset();
			}
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
			List<Note> notes = new ArrayList<>();
			for (Measure measure : song.measures) {
				notes.addAll(measure.notes);
			}
			return new ObjectMapper()
					.writerWithDefaultPrettyPrinter()
					.writeValueAsString(notes);
		}
		catch (IOException exception) {
			LogUtils.error(exception);
			return null;
		}
	}

	public List<Note> getNotes(String jsonString) {
		try {
			ObjectMapper objectMapper = new ObjectMapper();
			return objectMapper
					.readValue(
							jsonString, objectMapper.getTypeFactory()
									.constructCollectionType(List.class, Note.class));
		}
		catch (IOException exception) {
			LogUtils.error(exception);
			return null;
		}
	}

	public List<Measure> getMeasuresFromNotes(List<Note> notes) {
//		for(int i = 0; i < notes.size(); ++i) {
//			notes.get(i).duration /= 120;
//		}
		ArrayList<Measure> measureList = new ArrayList<>();
		Measure measure = new Measure(0, new ArrayList<Note>(), 0);
		int xPosition = 0;
		for (int i = 0; i < notes.size(); ++i) {
			if ((measure.numBeats + (notes.get(i).duration)) <= 16) {
				measure.numBeats += notes.get(i).duration;
				measure.notes.add(notes.get(i));
				notes.get(i).xPosition = xPosition;
				++xPosition;
			}
			else {
				measureList.add(measure);
				measure = new Measure(0, new ArrayList<Note>(), 0);
				xPosition = 0;

				measure.numBeats += notes.get(i).duration;
				measure.notes.add(notes.get(i));
				notes.get(i).xPosition = xPosition;
				++xPosition;
			}
		}
		measureList.add(measure);
		return measureList;
	}
}
