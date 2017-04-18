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

		Character[] sequence = {'s','1','2','2','2','2','1','1','e'};


		SecondMarkovChain<Character> markov= new SecondMarkovChain<>(sequence);

		System.out.println("Chain tree: ");
		markov.print();

		System.out.println("Example sequences:");
        for(int q = 0; q < 10; q++){
            System.out.println(markov.generateSequence());
        }

	}

}
