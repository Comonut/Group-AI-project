/**
 *
 */
import java.util.Hashtable;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.Random;


/**
 * @author Alex
 *
 */





public class NoteMarkovChain {

    Hashtable<Note,Hashtable<Note,Hashtable<Note,NoteInfo>>> root = new Hashtable<>();

    Note start;
    Note first;
    Note end;

    Random random = new Random();
    private static Note[] getNotes(String music){
      music = "0,0,0,0.0;" + music + "1,1,1,1.1";

      String[] notes = music.split(";");

      Note[] result = new Note[notes.length];

      for(int q = 0; q < notes.length ; q++){

        String[] elements = notes[q].split(",");

        result[q] = new Note(Integer.parseInt(elements[0]),Integer.parseInt(elements[1]),Integer.parseInt(elements[2]),Double.parseDouble(elements[3]));



      }

      return result;



    }

    public NoteMarkovChain(String sequence){

        Note[] tokens = getNotes(sequence);
        start = tokens[0];
        end = tokens[tokens.length-1];
        first = tokens[1];
        Hashtable<Note,Hashtable<Note,NoteInfo>> temp;
        Hashtable<Note,NoteInfo> temp2;
        for (int q = 1; q < tokens.length-1; q++){

            if(root.containsKey(tokens[q])){


                temp = root.get(tokens[q]);

                if(temp.containsKey(tokens[q-1])){
                    temp2 = temp.get(tokens[q-1]);


                    if(temp2.containsKey(tokens[q+1])){



                        temp2.get(tokens[q+1]).add(tokens[q+1].getDuration());


                    }

                    else{


                        temp2.put(tokens[q+1],new NoteInfo(tokens[q+1].getDuration()));

                    }




                }
                else{

                    Hashtable<Note,NoteInfo> empty2 = new Hashtable<>();
                    empty2.put(tokens[q+1],new NoteInfo(tokens[q+1].getDuration()));
                    temp.put(tokens[q-1],empty2);

                }
            }
            else{

                Hashtable<Note, Hashtable<Note,NoteInfo>> empty = new Hashtable<>();
                Hashtable<Note,NoteInfo> empty2 = new Hashtable<>();
                empty2.put(tokens[q+1],new NoteInfo(tokens[q+1].getDuration()));
                empty.put(tokens[q-1],empty2);
                root.put(tokens[q], empty );

            }
        }




    }

    public String toString(){
        return root.toString();
    }



    public Note getNext(Note current,Note previous){



        Hashtable<Note,NoteInfo> temp = root.get(current).get(previous);


        int sum = 0;

        Enumeration<NoteInfo> values = temp.elements();

        while(values.hasMoreElements()){
            sum += values.nextElement().getFrequency();
        }

        int choice = random.nextInt(sum+1);


        Enumeration<Note> keys = temp.keys();



        int q = 0;
        Note key = start;
        while(keys.hasMoreElements()){
            key = keys.nextElement();
            q += temp.get(key).getFrequency();
            if(choice < q){
                break;
            }
        }

        return new Note(key.getNote(), key.getSharp(), key.getOctave(),temp.get(key).getRandomDuration());
    }

    public static String getString(ArrayList<Note> notes){
      String result = "";
      for(int q = 0; q < notes.size(); q++){
        result += notes.get(q).toString();

      }

      return result;

    }
    public ArrayList<Note> generateSequence(){

        Note current = first;
        Note previous = start;
        Note temp;
        ArrayList<Note> sequence = new ArrayList<Note>();

        sequence.add(current);

        while(true){

            temp = current;

            current = getNext(current,previous);


            previous = temp;
            if(current.equals(end)) break;
            else sequence.add(current);

        }



        return sequence;

    }

    public String makeMusic(){
      int length = 0;
      String music = "";
      ArrayList<Note> seq;
      for(int q = 0; q< 5; q ++){
        seq = generateSequence();
        if(seq.size() > length){
          music = getString(seq);
          length = seq.size();
        }
      }
      return music;
    }


}
