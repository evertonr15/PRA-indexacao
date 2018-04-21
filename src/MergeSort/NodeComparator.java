package MergeSort;


import java.util.Comparator;

public class NodeComparator implements Comparator<Node> {

	@Override
	public int compare(Node arg0, Node arg1) {
		return arg0.getData().compareTo(arg1.getData());
	}
}
