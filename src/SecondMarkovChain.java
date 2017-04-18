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
public class SecondMarkovChain <E>{

    Hashtable<E,Hashtable<E,Hashtable<E,Integer>>> root = new Hashtable<>();

    E start;
    E first;
    E end;

    Random random = new Random();

    public SecondMarkovChain(E tokens[]){
        start = tokens[0];
        end = tokens[tokens.length-1];
        first = tokens[1];
        Hashtable<E,Hashtable<E,Integer>> temp;
        Hashtable<E,Integer> temp2;
        for (int q = 1; q < tokens.length-1; q++){

            if(root.containsKey(tokens[q])){


                temp = root.get(tokens[q]);

                if(temp.containsKey(tokens[q-1])){
                    temp2 = temp.get(tokens[q-1]);


                    if(temp2.containsKey(tokens[q+1])){



                        temp2.put(tokens[q+1],temp2.get(tokens[q+1]) + 1);


                    }

                    else{


                        temp2.put(tokens[q+1],1);

                    }




                }
                else{

                    Hashtable<E,Integer> empty2 = new Hashtable<>();
                    empty2.put(tokens[q+1],1);
                    temp.put(tokens[q-1],empty2);

                }
            }
            else{

                Hashtable<E, Hashtable<E,Integer>> empty = new Hashtable<>();
                Hashtable<E,Integer> empty2 = new Hashtable<>();
                empty2.put(tokens[q+1],1);
                empty.put(tokens[q-1],empty2);
                root.put(tokens[q], empty );

            }
        }




    }

    public void print(){
        System.out.println(root.toString());
    }



    public E getNext(E current,E previous){


        Hashtable<E,Integer> temp = root.get(current).get(previous);


        int sum = 0;

        Enumeration<Integer> values = temp.elements();

        while(values.hasMoreElements()){
            sum += values.nextElement();
        }

        int choice = random.nextInt(sum+1);


        Enumeration<E> keys = temp.keys();



        int q = 0;
        E key = start;
        while(keys.hasMoreElements()){
            key = keys.nextElement();
            q += temp.get(key);
            if(choice < q){
                break;
            }
        }

        return key;
    }


    public ArrayList<E> generateSequence(){

        E current = first;
        E previous = start;
        E temp;
        ArrayList<E> sequence = new ArrayList<E>();

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


}
