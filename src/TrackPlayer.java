

import java.io.*;
import javax.sound.sampled.*;

public class TrackPlayer {
    String noteInfo;
    int size = 9;
    char note[];
    String noteNumber;
    String noteType;
    String noteOctave;
    String noteDuration;
    double dur;
    private double speed;
    private String sequence;
    private int volume = 1;

    public void readTrack() throws InterruptedException, LineUnavailableException, IOException, UnsupportedAudioFileException {

        String[] seq = sequence.split(";");

        for (String seq1 : seq) {
            String[] seq2 = seq1.split(",");
            dur = Double.parseDouble(seq2[3]);
            String note = transformToNoteName(seq2[0], seq2[1], seq2[2]);
            playAudio(note, dur);
        }
    }

    public String transformToNoteName(String number, String type, String oct) {
        String noteInfo = "";

        switch (number) {
            case "1":
                noteInfo = noteInfo + "Do";
                break;
            case "2":
                noteInfo = noteInfo + "Re";
                break;
            case "3":
                noteInfo = noteInfo + "Mi";
                break;
            case "4":
                noteInfo = noteInfo + "Fa";
                break;
            case "5":
                noteInfo = noteInfo + "Sol";
                break;
            case "6":
                noteInfo = noteInfo + "La";
                break;
            case "7":
                noteInfo = noteInfo + "Si";
                break;
            default:
                break;
        }

        if (type.equals("1")) {
            noteInfo = noteInfo + ("_Sharp");
        }

        switch (oct) {
            case "1":
                noteInfo = "__" + noteInfo;
                break;
            case "2":
                noteInfo = "_" + noteInfo;
                break;
            case "4":
                noteInfo = noteInfo + "_";
                break;
            default:
                break;
        }

        return noteInfo;
    }

    public void playAudio(String info, double d) throws InterruptedException, LineUnavailableException, IOException, UnsupportedAudioFileException {
        String audio = "sound/";

        audio += info + ".wav";

        AudioInputStream audioInputStream;
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
        double division = d / speed;
        division = Math.round(division * 100.0) / 100.0;
        tStart = System.currentTimeMillis();
        do {

            tEnd = System.currentTimeMillis();
            tDelta = tEnd - tStart;
            duration = tDelta / 1000.0;

        } while (duration != division);
    }

    public void setVolume(int pasVol) {
        volume = pasVol;
    }

    public int getVolume() {
        return volume;
    }

    public void setSpeed(double pasSpeed) {
        speed = pasSpeed;
    }

    public double getSpeed() {
        return speed;
    }

    public void printSpeed() {
        System.out.println(speed);
    }

    public void setSequence(String pasSequence) {
        sequence = pasSequence;
    }

    public String getSequence() {
        return sequence;
    }
}
