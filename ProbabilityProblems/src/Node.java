import java.util.List;

public class Node {

	String name;
	double red;
	double green;
	double blue;

	double probability;

	Node redPtr;
	Node greenPtr;
	Node bluePtr;
	static List<Double> list;

	Node(double red, double green, double blue) {
		this.red = red;
		this.green = green;
		this.blue = blue;
		probability = 0;
		redPtr = null;
		greenPtr = null;
		bluePtr = null;
	}

	public static void addProbability(Node head, int counter, int maxCounter) {

		Node temp = new Node(head.red - 1, head.green, head.blue);
		temp.probability = head.red / (head.red + head.green + head.blue);

		temp.name = "R";

		if (head.probability > 0) {
			temp.probability = temp.probability * head.probability;
		}
		head.redPtr = temp;

		temp = new Node(head.red, head.green - 1, head.blue);
		temp.probability = head.green / (head.red + head.green + head.blue);
		temp.name = "G";
		if (head.probability > 0) {
			temp.probability = temp.probability * head.probability;
		}
		head.greenPtr = temp;

		temp = new Node(head.red, head.green, head.blue - 1);
		temp.probability = head.blue / (head.red + head.green + head.blue);
		temp.name = "B";
		if (head.probability > 0) {
			temp.probability = temp.probability * head.probability;
		}
		head.bluePtr = temp;

		if (counter < maxCounter) {
			counter++;
			Node.addProbability(head.redPtr, counter, maxCounter);
			Node.addProbability(head.greenPtr, counter, maxCounter);
			Node.addProbability(head.bluePtr, counter, maxCounter);
		}

	}

	public static void sameColourProbability(Node head, List<Double> list) {

		if (head == null)
			return;

		Node temp = head;
		if ((temp.name != null && temp.redPtr != null && temp.name.equalsIgnoreCase(temp.redPtr.name))
				&& (temp.redPtr.redPtr == null)) {
			list.add(temp.redPtr.probability);

		}
		if (temp.name != null && temp.greenPtr != null && temp.name.equalsIgnoreCase(temp.greenPtr.name)
				&& (temp.greenPtr.greenPtr == null)) {
			list.add(temp.greenPtr.probability);

		}
		if (temp.name != null && temp.bluePtr != null && temp.name.equalsIgnoreCase(temp.bluePtr.name)
				&& (temp.bluePtr.bluePtr == null)) {
			list.add(temp.bluePtr.probability);

		}

		sameColourProbability(head.redPtr, list);
		sameColourProbability(head.greenPtr, list);
		sameColourProbability(head.bluePtr, list);

	}
}