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
public class MarkovChain <E>{
	
	Hashtable<E,Hashtable<E,Integer>> root = new Hashtable<E,Hashtable<E,Integer>>();
	
	E start;
	E end;
	
	Random random = new Random();
	
	public MarkovChain(E tokens[]){
		start = tokens[0];
		end = tokens[tokens.length-1];
		Hashtable<E,Integer> temp;
		for (int q = 1; q < tokens.length; q++){
			if(root.containsKey(tokens[q-1])){
				temp = root.get(tokens[q-1]);
				if(temp.contains(tokens[q])){
					temp.put(tokens[q], temp.get(tokens[q])+1);
				}
				else{
					temp.put(tokens[q], 1);
				}
			}
			else{
				Hashtable<E, Integer> empty = new Hashtable<E,Integer>(); 
				empty.put(tokens[q], 1);
				root.put(tokens[q-1], empty );
				
			}
		}
		
		
		
		
	}
	
	public void print(){
		System.out.println(root.toString());
	}
	
	
	
	public E getNext(E current){
		
		
		Hashtable<E,Integer> temp = root.get(current);
		
		
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
		
		E current = start;
		ArrayList<E> sequence = new ArrayList<E>();
		
		
		while(true){
			
			current = getNext(current);
			if(current.equals(end)) break;
			else sequence.add(current);
			
		}
		
		
		
		return sequence;
		
	}
	

}
