import java.util.HashMap;
import java.util.LinkedHashSet;
import java.util.LinkedList;
import java.util.Map;
import java.util.Set;
import java.util.TreeSet;

public class Graph {
	private Map<Integer, LinkedHashSet<Integer>> map = new HashMap<Integer, LinkedHashSet<Integer>>();
	private LinkedList<String> paths;

	public void addEdge(Integer node1, Integer node2) {
		LinkedHashSet<Integer> adjacent = map.get(node1);
		if(adjacent==null) {
			adjacent = new LinkedHashSet<Integer>();
			map.put(node1, adjacent);
		}
		adjacent.add(node2);
	}

	public void addTwoWayEdge(Integer node1, Integer node2) {
		addEdge(node1, node2);
		addEdge(node2, node1);
	}

	public boolean isConnected(Integer node1, Integer node2) {
		Set<Integer> adjacent = map.get(node1);
		if(adjacent==null) {
			return false;
		}
		return adjacent.contains(node2);
	}

	public LinkedList<Integer> nextNodes(Integer node) {
		LinkedHashSet<Integer> adjacent = map.get(node);
		if(adjacent==null) {
			return new LinkedList<Integer>();
		}
		return new LinkedList<Integer>(adjacent);
	}
	
	public LinkedList<Integer> prevNodes(Integer node) {
		LinkedList<Integer> adjacent = new LinkedList<Integer>();
		for (Integer node2 : getAllNodes()) {
			if (nextNodes(node2).contains(node))
				adjacent.add(node2);
		}
		return adjacent;
	}
	
	public boolean isEnd(Integer node) {
		return nextNodes(node).isEmpty();
	}
	
	public boolean isStart(Integer node) {
		return prevNodes(node).isEmpty();
	}
	
	public TreeSet<Integer> getAllNodes() {
		TreeSet<Integer> nodes = new TreeSet<Integer>();
		for (Integer k : map.keySet()) {
			nodes.add(k);
			nodes.addAll(nextNodes(k));
		}
		return nodes;
	}
	
	public TreeSet<Integer> getStartNodes() {
		TreeSet<Integer> nodes = getAllNodes();
		TreeSet<Integer> list = new TreeSet<Integer>();
		for (Integer node : nodes) {
			if (isStart(node)) {
				list.add(node);
			}
		}
		return list;
	}
	
	public TreeSet<Integer> getEndNodes() {
		TreeSet<Integer> nodes = getAllNodes();
		TreeSet<Integer> list = new TreeSet<Integer>();
		for (Integer node : nodes) {
			if (isEnd(node)) {
				list.add(node);
			}
		}
		return list;
	}
	
	public LinkedList<String> getPath(Integer start, Integer end) {
		LinkedList<Integer> visited = new LinkedList<Integer>();
		paths = new LinkedList<String>();
		visited.add(start);
		depthFirst(visited, end);
		return paths;
	}
	
	public LinkedList<String> getPathStartEnd() {
		LinkedList<String> list = new LinkedList<String>();
		for (Integer start : getStartNodes()) {
			for (Integer end : getEndNodes()) {
				list.addAll(getPath(start,end));
			}
		}
		return list;
	}
	
	private void depthFirst(LinkedList<Integer> visited, Integer end) {
		LinkedList<Integer> nodes = nextNodes(visited.getLast());
		String text = "";
		
		for (Integer node : nodes) {
			if (visited.contains(node)) {
				continue;
			}
			if (node.equals(end)) {
				visited.add(node);
				for (Integer i : visited) {
					text += i+" -> ";
				}
				text = text.substring(0, text.length() - 4);				
				paths.add(text);
				visited.removeLast();
				break;
			}
		}
		for (Integer node : nodes) {
			if (visited.contains(node) || node.equals(0)) {
				continue;
			}
			visited.addLast(node);
			depthFirst(visited, end);
			visited.removeLast();
		}
	}
	
	@Override
	public String toString() {
		return(map+"");
	}
}