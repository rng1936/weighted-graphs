import java.util.ArrayList;
import java.util.PriorityQueue;

public class WeightedGraph{
	private ArrayList<Integer> vertices;
	private ArrayList<EdgeWithWeight> edges;
	private boolean hasPath = false;
	private double minWeight = Double.NaN;
	private EdgeWithWeight[] path;
	
	public WeightedGraph() {
		vertices = new ArrayList<Integer>();
		edges = new ArrayList<EdgeWithWeight>();
	}
	
	public boolean hasPath(int fromVertex, int toVertex) {
		shortestPath(fromVertex,toVertex);
		return hasPath;
	}

	public double getMinimumWeight(int fromVertex, int toVertex) {
		shortestPath(fromVertex,toVertex);
		return minWeight;
	}

	public EdgeWithWeight[] getPath(int fromVertex, int toVertex) {
		shortestPath(fromVertex,toVertex);
		if (hasPath) return path;
		return new EdgeWithWeight[0];
	}

	public boolean addVertex(int v) {
		if (!vertices.contains(v)) {
			vertices.add(v);
			return true;
		}
		return false;
	}

	public boolean addWeightedEdge(int from, int to, double weight) {
		EdgeWithWeight edge = new EdgeWithWeight(from,to,weight);
		for (int i = 0; i < edges.size(); i++) {
			if (edges.get(i).getFromVertex() == from && edges.get(i).getToVertex() == to) return false;
		}
		edges.add(edge);
		return true;
	}
	
	public String toString() {
		StringBuilder s = new StringBuilder();
		s.append("G = (V,E)\nV = {");
		for (int i = 0; i < vertices.size(); i++) {
			s.append(vertices.get(i));
			if (i == vertices.size() - 1) {
				s.append("}\n");
			} else {
				s.append(",");
			}
		}
		s.append("E = {");
		for (int i = 0; i < edges.size(); i++) {
			s.append("(" + edges.get(i).getFromVertex() + "," + edges.get(i).getToVertex() + "," + edges.get(i).getWeight() + ")");
			if (i == edges.size() - 1) {
				s.append("}");
			} else {
				s.append(",");
			}
		}
		return s.toString();
	}
	
	public void shortestPath(int fromVertex, int toVertex) {
		PriorityQueue<VertexWithWeight> minByWeight = new PriorityQueue<>(vertices.size(), new VertexWithWeightWeightComparator());
		VertexWithWeight[] vCost = new VertexWithWeight[vertices.size()];
		int[] parent = new int[vertices.size()];
		for (int i = 0; i < vertices.size(); i++) {
			if (vertices.get(i) == fromVertex) {
				vCost[i] = new VertexWithWeight(vertices.get(i), 0.0);
				parent[i] = fromVertex;
			} else {
				vCost[i] = new VertexWithWeight(vertices.get(i), Double.POSITIVE_INFINITY);
				parent[i] = -1;
			}
		}
		for (int i = 0; i < vertices.size(); i++) {
			minByWeight.add(vCost[i]);
		}
		while (minByWeight.size() > 0) {
			int v = minByWeight.poll().getVertex();
			if (parent[getIndex(v)] == -1 || v == toVertex) break;
			for (int i = 0; i < edges.size(); i++) {
				if (edges.get(i).getFromVertex() == v) {
					int u = edges.get(i).getToVertex();
					if (vCost[getIndex(v)].getWeight() + edges.get(i).getWeight() < vCost[getIndex(u)].getWeight()) {
						minByWeight.remove(vCost[getIndex(u)]);
						vCost[getIndex(u)].setWeight(vCost[getIndex(v)].getWeight() + edges.get(i).getWeight());
						minByWeight.add(vCost[getIndex(u)]);
						parent[getIndex(u)] = v;
					}
				}
			}
		}
		ArrayList<Integer> reversePath = new ArrayList<Integer>();
		ArrayList<Integer> forwardPath = new ArrayList<Integer>();
		int p = toVertex;
		reversePath.add(p);
		try {
			while (p != fromVertex) {
				p = parent[getIndex(p)];
				reversePath.add(p);
			}
		} catch (ArrayIndexOutOfBoundsException e) {
			return;
		}
		for (int i = reversePath.size()-1; i >= 0; i--) {
			forwardPath.add(reversePath.get(i));
		}
		EdgeWithWeight[] edgeList = new EdgeWithWeight[forwardPath.size()-1];
		for (int i = 0; i < edgeList.length; i++) {
			int from = forwardPath.get(i).intValue();
			int to = forwardPath.get(i+1).intValue();
			for (int j = 0; j < edges.size(); j++) {
				if (edges.get(j).getFromVertex() == from && edges.get(j).getToVertex() == to) {
					edgeList[i] = edges.get(j);
				}
			}
		}
		path = edgeList;
		if (path.length != 0) {
			hasPath = true;
			minWeight = 0;
			for (EdgeWithWeight e : path) {
				if (e != null) {
					minWeight += e.getWeight();
				}
			}
		}
	}
	
	public int getIndex(int v) {
		for (int i = 0; i < vertices.size(); i++) {
			if (vertices.get(i) == v) return i;
		}
		return -1;
	}
}