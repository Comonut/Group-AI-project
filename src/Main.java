/**
 * 
 */

/**
 * @author Everyone
 *
 */
public class Main {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		// TODO Auto-generated method stub

		Integer[] w = {-1,1,2,1,2,-2};
		
		MarkovChain<Integer> chain = new MarkovChain<Integer>(w);
		System.out.println(chain.generateSequence());
	}

}
