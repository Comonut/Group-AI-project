


public class Note{
  private int note;
  private int sharp;
  private int octave;
  private double duration;


  public Note(int n, int s, int o , double d){
    note = n;
    sharp = s;
    octave = o;
    duration = d;
  }



  public boolean equals(Object other) {
      boolean result = false;
      if (other instanceof Note) {
          Note that = (Note) other;
          result = (this.getNote() == that.getNote() && this.getSharp() == that.getSharp()) && this.getOctave() == that.getOctave();
      }
      return result;
  }


  public String toString(){
	  return String.format("%d,%d,%d,%.1f;", note,sharp,octave,duration);
  }
  
  public int hashCode(){
	  int hash = 1;
	  
	  hash = hash*15 + note;
	  hash = hash*15 + sharp;
	  hash = hash * 15+ octave;
	  
	  
	  return hash;
  }
  
  
  public int getNote(){
	  return note;
  }
  
  public int getSharp(){
	  return sharp;
  }
  public int getOctave(){
	  return octave;
  }
  
  public double getDuration(){
	  return duration;
  }

}
