package VirtualPiano;


import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.text.DecimalFormat;
import java.util.InputMismatchException;

import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import javax.sound.sampled.FloatControl;
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
	private double speed;
	private String sequence;
	private int volume = 1;

	
	public void readTrack() throws InterruptedException {

		    String [] seq = sequence.split(";");

		    for (int x = 0; x < seq.length; x++) {
		    	
		    	String [] seq2 = seq[x].split(",");

		    	dur = Double.parseDouble(seq2[3]);
		    	
		    	String note = transformToNoteName(seq2[0], seq2[1], seq2[2]);
		    	playAudio(note, dur);
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
		
		audio = info + ".wav"; 
		
		AudioInputStream audioInputStream;
		try {
			audioInputStream = AudioSystem.getAudioInputStream(new File(audio).getAbsoluteFile());
			Clip clip = AudioSystem.getClip();
			clip.open(audioInputStream);
			FloatControl gainControl = (FloatControl) clip.getControl(FloatControl.Type.MASTER_GAIN);
			gainControl.setValue(volume);
			clip.start();
		
			long tStart = 0;
			long tEnd = 0;
			double duration;
			long tDelta;
			
			double division = d/speed;
			division = Math.round(division * 100.0) / 100.0;
			
			tStart = System.currentTimeMillis();
			do{

			tEnd = System.currentTimeMillis();
        	tDelta = tEnd - tStart;
        	duration = tDelta / 1000.0;

			} while (duration != division);

		
		} catch (UnsupportedAudioFileException | IOException e1) {
			e1.printStackTrace();
		} catch (LineUnavailableException e1) {
			e1.printStackTrace();
		}
	}
	
	public void setVolume(int pasVol){
		volume = pasVol;
	}
	
	public int getVolume(){
		return volume;
	}
	public void setSpeed(double pasSpeed){
		speed = pasSpeed;
	}
	
	public double getSpeed(){
		return speed;
	}
	
	public void printSpeed () {
		System.out.println(speed);
	}
	
	public void setSequence(String pasSequence){
		sequence = pasSequence;
	}
	
	public String getSequence(){
		return sequence;
	}
	
}	
