import java.util.Comparator;

public class VertexWithWeightWeightComparator implements Comparator<VertexWithWeight> {
    @Override
    public int compare(VertexWithWeight o1, VertexWithWeight o2) {
        // TODO Auto-generated method stub
        if (o1.getWeight() < o2.getWeight()) {
            return -1;
        }
        if (o1.getWeight() > o2.getWeight()) {
            return 1;
        }
        if (o1.getWeight() == o2.getWeight() && o1.getVertex() < o2.getVertex()) {
            return -1;
        }
        if (o1.getWeight() == o2.getWeight() && o1.getVertex() > o2.getVertex()) {
            return 1;
        }
        return 0;
    }
}
