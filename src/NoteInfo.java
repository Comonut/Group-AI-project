import java.util.ArrayList;
import java.util.Random;


public class NoteInfo{

  private int frequency = 0;


  private ArrayList<Double> durations = new ArrayList<Double>();



  public NoteInfo(Double duration){
    frequency++;

    durations.add(duration);


  }

  public void add(Double duration){
    frequency++;
    durations.add(duration);

  }


  public int getFrequency(){
    return frequency;
  }

  public Double getRandomDuration(){
    
    Random rand = new Random();

    return durations.get(rand.nextInt(durations.size()));


  }
  
  public String toString(){
	  
	  return String.format(" %d - ",frequency) + durations.toString();
  }





}
