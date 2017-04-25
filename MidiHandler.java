package VirtualPiano;

/* This classes read in notes from a midi keyboard and output them to a txt file
 * sources: 
 * http://stackoverflow.com/questions/3850688/reading-midi-files-in-java
 * http://stackoverflow.com/questions/1182877/reading-midi-files
 * http://stackoverflow.com/questions/6937760/java-getting-input-from-midi-keyboard
 * */
import javax.sound.midi.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;
import java.io.*;
import javax.sound.midi.MidiEvent;
import javax.sound.midi.MidiMessage;
import javax.sound.midi.MidiSystem;
import javax.sound.midi.Sequence;
import javax.sound.midi.ShortMessage;
import javax.sound.midi.Track;
import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import javax.sound.sampled.LineUnavailableException;
import javax.sound.sampled.UnsupportedAudioFileException;

// Connect to device
    public class MidiHandler
{
    public String note_Name;
    public String note_Code;
    public int note_Octave;

    public MidiHandler() throws FileNotFoundException
    {   System.out.println("Please Connect your midi device before starting the program");
        System.out.println("Note Ranges: 2 octaves above and below central 'Do'");
        MidiDevice device;
        MidiDevice.Info[] infos = MidiSystem.getMidiDeviceInfo();

        for (int i = 0; i < infos.length; i++) {
            try {
            device = MidiSystem.getMidiDevice(infos[i]);
            //does the device have any transmitters?
            //if it does, add it to the device list
            System.out.println(infos[i]);

            //get all transmitters
            List<Transmitter> transmitters = device.getTransmitters();
            //and for each transmitter

            for(int j = 0; j<transmitters.size();j++) {
                //create a new receiver
                transmitters.get(j).setReceiver(
                        //using my own MidiInputReceiver
                        new MidiInputReceiver(device.getDeviceInfo().toString())
                );
            }

            Transmitter trans = device.getTransmitter();
            trans.setReceiver(new MidiInputReceiver(device.getDeviceInfo().toString()));

            //open each device
            device.open();
            //if code gets this far without throwing an exception
            //print a success message
            System.out.println(device.getDeviceInfo()+" Was Opened");

            //Setting up the txt file to export to. Setting 'System.out' to 'trackOutput.txt'
            PrintStream out = new PrintStream(new FileOutputStream("trackOutput.txt"));
            System.setOut(out);

        } catch (MidiUnavailableException e) {}
    }


}

//Receive and output note info
public class MidiInputReceiver implements Receiver {
    public String name;
    public MidiInputReceiver(String name) {
        this.name = name;
    }

    long tStart = 0;
    
    public void send(MidiMessage msg, long timeStamp) {
        
        final int NOTE_ON = 0x90;
        final int NOTE_OFF = 0x80;
        final String[] NOTE_NAMES = {"Do", "Do_Sharp", "Re", "Re_Sharp", "Mi", "Fa", "Fa_Sharp", "Sol", "Sol_Sharp", "La", "La_Sharp", "Si"};
        final String[] NOTE_CODES = {"1;0", "1;1", "2;0", "2;1", "3;0", "4;0", "4;1", "5;0", "5;1", "6;0", "6;1", "7;0"};

        if (msg instanceof ShortMessage) {
            ShortMessage sm = (ShortMessage) msg;
            if (sm.getCommand() == NOTE_ON) {
            	tStart = System.currentTimeMillis();
            	
                int key = sm.getData1();
                int octave = (key / 12)-2;
                int note = key % 12;
                String noteName = NOTE_NAMES[note];
                note_Name = noteName;
                note_Octave = octave;
                          
                playNote();

            } else if (sm.getCommand() == NOTE_OFF) {
            	long tEnd = System.currentTimeMillis();
            	long tDelta = tEnd - tStart;
            	double duration = tDelta / 1000.0;
            	
                int key = sm.getData1();
                int octave = (key / 12)-2;
                int note = key % 12;
                note_Code = NOTE_CODES[note];
                
                duration = roundDuration(duration);
                
                System.out.println(note_Code + ";" + octave + ";" + duration);
                duration = 0;
   
            } else {
                System.out.println("Command:" + sm.getCommand());
            }
        } else {
            System.out.println("Other message: " + msg.getClass());
        }
        
        
    }
    public void close() {}
    }

public double roundDuration(double d) {
	if ((0 <= d) && (d <= 0.3))
		d = 0.5;
	else if ((0.3 < d) && (d < 1.1))
		d = 1.0;
	else if ((1.1 <= d) && (d <= 1.5))
		d = 1.5;
	else if ((1.5 < d) && (d < 2.1))
		d = 2.0;
	else d = 4;
	return d;
}

//Playing note audio files for the keys played
public void playNote() {
	String audio = null;
	
	if(note_Octave == 5)
		audio = note_Name + "__.wav"; 
	else if(note_Octave == 4)
		audio = note_Name + "_.wav"; 
	else if(note_Octave == 2)
		audio = "_" + note_Name + ".wav"; 
	else if(note_Octave == 1)
		audio = "__" + note_Name + ".wav"; 
	else if(note_Octave == 3)
		audio = note_Name + ".wav"; 
	   
	AudioInputStream audioInputStream;
	try {
		audioInputStream = AudioSystem.getAudioInputStream(new File(audio).getAbsoluteFile());
	Clip clip = AudioSystem.getClip();
	clip.open(audioInputStream);
	clip.start();
	} catch (UnsupportedAudioFileException | IOException e1) {
	} catch (LineUnavailableException e1) {
	}
}

}
