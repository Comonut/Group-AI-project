package VirtualPiano;

import java.io.*;
import javax.sound.midi.*; // package for all midi classes

/* Gabriela Rivas
 * 
 * This program takes in a string of
 * note Codes eg: "1,0,3,1.0;2,0,3,1.0;3,0,3,0.5;4,0,3,0.5;5,0,3,2.0"
 * and exports the track into a midi file
 */
public class MidiExporter {
	private String sequence;
	private int numberOfNotes;
	private ShortMessage mm;
	private MidiEvent me;
	private Track t;

	public MidiExporter(String track) {
		sequence = track;

		numberOfNotes = countNotes();

		try {
			// **** Create a new MIDI sequence with 24 ticks per beat ****
			Sequence s = new Sequence(javax.sound.midi.Sequence.PPQ, 24);

			// **** Obtain a MIDI track from the sequence ****
			t = s.createTrack();

			// **** General MIDI sysex -- turn on General MIDI sound set ****
			byte[] b = { (byte) 0xF0, 0x7E, 0x7F, 0x09, 0x01, (byte) 0xF7 };
			SysexMessage sm = new SysexMessage();
			sm.setMessage(b, 6);
			me = new MidiEvent(sm, (long) 0);
			t.add(me);

			// **** set tempo (meta event) ****
			MetaMessage mt = new MetaMessage();
			byte[] bt = { 0x02, (byte) 0x00, 0x00 };
			mt.setMessage(0x51, bt, 3);
			me = new MidiEvent(mt, (long) 0);
			t.add(me);

			// **** set track name (meta event) ****
			mt = new MetaMessage();
			String TrackName = new String("midifile track");
			mt.setMessage(0x03, TrackName.getBytes(), TrackName.length());
			me = new MidiEvent(mt, (long) 0);
			t.add(me);

			// **** set omni on ****
			mm = new ShortMessage();
			mm.setMessage(0xB0, 0x7D, 0x00);
			me = new MidiEvent(mm, (long) 0);
			t.add(me);

			// **** set poly on ****
			mm = new ShortMessage();
			mm.setMessage(0xB0, 0x7F, 0x00);
			me = new MidiEvent(mm, (long) 0);
			t.add(me);

			// **** set instrument to Piano ****
			mm = new ShortMessage();
			mm.setMessage(0xC0, 0x00, 0x00);
			me = new MidiEvent(mm, (long) 0);
			t.add(me);

			writeNotes();

			// **** set end of track (meta event) 19 ticks later ****
			mt = new MetaMessage();
			byte[] bet = {}; // empty array
			mt.setMessage(0x2F, bet, 0);
			me = new MidiEvent(mt, (long) 140);
			t.add(me);

			// **** write the MIDI sequence to a MIDI file ****
			String fileName = fileExists();
			// System.out.println(fileName); for testing
			File f = new File(fileName);
			MidiSystem.write(s, 1, f);
		} // try
		catch (Exception e) {
			System.out.println("Exception caught " + e.toString());
		} // catch
		System.out.println("midifile exported");
	}

	public int countNotes() {
		int noteNumber = 0;

		for (int i = 0; i < sequence.length(); i++) {
			if (sequence.charAt(i) == '.') {
				noteNumber++;
			}
		}
		return noteNumber;
	}

	public void writeNotes() throws InvalidMidiDataException {
		int time = 1;
		String temp;
		int count = 0;
		for (int i = 0; i < numberOfNotes; i++) {
			temp = "" + sequence.charAt(count);
			int note_Code = Integer.parseInt(temp);
			temp = "" + sequence.charAt(count + 2);
			int note_Type = Integer.parseInt(temp);
			temp = "" + sequence.charAt(count + 4);
			int note_Octave = Integer.parseInt(temp);
			temp = "" + sequence.charAt(count + 6) + sequence.charAt(count + 7) + sequence.charAt(count + 8);
			double note_Duration = Double.parseDouble(temp);

			count = count + 10;

			if (note_Code == 1)
				note_Code = 60;
			else if (note_Code == 2)
				note_Code = 62;
			else if (note_Code == 3)
				note_Code = 64;
			else if (note_Code == 4)
				note_Code = 65;
			else if (note_Code == 5)
				note_Code = 67;
			else if (note_Code == 6)
				note_Code = 69;
			else if (note_Code == 7)
				note_Code = 71;

			else if (note_Type == 1)
				note_Code++;

			if (note_Octave == 5)
				note_Code = note_Code + 24;
			else if (note_Code == 4)
				note_Code = note_Code + 12;
			else if (note_Code == 2)
				note_Code = note_Code - 12;
			else if (note_Code == 1)
				note_Code = note_Code - 24;

			int dur = 0;

			if (note_Duration == 1.0) {
				dur = 30;
			} else if (note_Duration == 0.5) {
				dur = 15;
			} else if (note_Duration == 1.5) {
				dur = 45;
			} else if (note_Duration == 2.0) {
				dur = 60;
			} else if (note_Duration == 4.0) {
				dur = 120;
			}

			// **** note on - middle C ****
			mm = new ShortMessage();
			mm.setMessage(0x90, note_Code, 0x60); // 60 = central 'Do'
			me = new MidiEvent(mm, (long) time);
			t.add(me);

			time = time + dur;

			// **** note off - middle C - 120 ticks later ****
			mm = new ShortMessage();
			mm.setMessage(0x80, note_Code, 0x40);
			me = new MidiEvent(mm, (long) time); // 30 ticks per beat
			t.add(me);

		}
	}

	public String fileExists() {
		String fmt = "";
		File f = null;
		int i;
		for (i = 1; i < 100; i++) {
			fmt = "export" + i + ".mid";
			f = new File(String.format(fmt));
			if (!f.exists()) {
				break;
			}
		}

		return fmt;
	}

}
