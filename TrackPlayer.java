package VirtualPiano;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;

import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import javax.sound.sampled.LineUnavailableException;
import javax.sound.sampled.UnsupportedAudioFileException;

//This class reads in note info from a txt file and plays the notes
public class TrackPlayer {
	String noteInfo;
	int size = 9;
	char note [];
	String noteNumber;
	String noteType;
	String noteOctave;
	String noteDuration;
	double dur;
	
	public void readTrack() throws IOException, InterruptedException {
		try (BufferedReader br = new BufferedReader(new FileReader("trackOutput.txt"))) {
		    String line;
		    while ((line = br.readLine()) != null) {
		    	note = new char[6];
		    	int j = 0;
		    	
		    	noteInfo = line.toString();
		    	
		    	for (int i = 0;i < size; i++){
		    		char temp = noteInfo.charAt(i);
		    		
		    		if(temp != ';') {
		    	    note[j] = temp;	
		    	    j++;
		    		}	
		    	}
		    	
		    	noteNumber = "" + note[0];
		    	noteType = "" + note[1];
		    	noteOctave = "" + note[2];
		    	
		    	noteDuration = "" + note[3] + note[4] + note[5];
		    	
		    	dur = Double.parseDouble(noteDuration);
		    	
		    	String note = transformToNoteName(noteNumber, noteType, noteOctave);
		    	playAudio(note, dur);
		    }
		}
	}
	public String transformToNoteName(String number, String type, String oct) {
		String noteInfo = "";
		
		if(number.equals("1"))
			noteInfo = noteInfo + "Do";
		else if(number.equals("2"))
			noteInfo = noteInfo + "Re";
		else if(number.equals("3"))
			noteInfo = noteInfo + "Mi";
		else if(number.equals("4"))
			noteInfo = noteInfo + "Fa";
		else if(number.equals("5"))
			noteInfo = noteInfo + "Sol";
		else if(number.equals("6"))
			noteInfo = noteInfo + "La";
		else if(number.equals("7"))
			noteInfo = noteInfo + "Si";
		
		if(type.equals("1"))
			noteInfo = noteInfo + ("_Sharp");
		
		if(oct.equals("1"))
			noteInfo = "__" + noteInfo;
		else if(oct.equals("2"))
			noteInfo = "_" + noteInfo;
		else if(oct.equals("4"))
			noteInfo = noteInfo + "_";
		
		return noteInfo;	
	}

	public void playAudio(String info, double d) throws InterruptedException {
		String audio = null;
		boolean stop = false;
		
		audio = info + ".wav"; 
		
		AudioInputStream audioInputStream;
		try {
			audioInputStream = AudioSystem.getAudioInputStream(new File(audio).getAbsoluteFile());
		Clip clip = AudioSystem.getClip();
		clip.open(audioInputStream);
		clip.start();
		
			long tStart = 0;
			long tEnd = 0;
			double duration;
			long tDelta;
			
			tStart = System.currentTimeMillis();
			do{

			tEnd = System.currentTimeMillis();
        	tDelta = tEnd - tStart;
        	duration = tDelta / 1000.0;

			} while (duration != d);

		
		} catch (UnsupportedAudioFileException | IOException e1) {
			e1.printStackTrace();
		} catch (LineUnavailableException e1) {
			e1.printStackTrace();
		}
	}
	
	public static void main(String[] args) throws IOException, InterruptedException {
		TrackPlayer player = new TrackPlayer();
		player.readTrack();
	}
}
