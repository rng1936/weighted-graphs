public class EdgeWithWeight {
	private final Integer fromVertex, toVertex;
	private final Double weight;
	
	public EdgeWithWeight(int fromVertex, int toVertex, double weight) {
		this.fromVertex = fromVertex;
		this.toVertex = toVertex;
		this.weight = weight;
	}
	
	public int getFromVertex() {
		return fromVertex;
	}

	public int getToVertex() {
		return toVertex;
	}

	public double getWeight() {
		return weight;
	}
	
	public String toString() {
		return "(" + fromVertex + "," + toVertex + "," + weight +")";
	}

}
