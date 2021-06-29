import java.util.ArrayList;
import java.util.List;



public class CodingProblem {

	public static void main(String[] args) {

		
		//Red Ball - 9, Green Ball = 8, Blue Ball = 8
		
		int redBall = 9;
		int greenBall = 8;
		int blueBall = 8;
		
		Node head = new Node(redBall, greenBall, blueBall);
		Node.addProbability(head, 1, 6);

		List<Double> list = new ArrayList<Double>();

		Node.sameColourProbability(head, list);

		Double probabilty = list.stream().reduce(0.0, Double::sum);

		System.out.println("Probability of finding the same colour ball");
		System.out.println(probabilty);

	}

}
