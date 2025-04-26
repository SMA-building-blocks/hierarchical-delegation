package hierarchical_delegation.strategies;

import java.util.ArrayList;
import java.util.Arrays;

public class AverageStrategy implements Strategy {
    @Override
    public ArrayList<Double> executeOperation(ArrayList<Double> data) {
        return new ArrayList<>(Arrays.asList((double) data.stream().mapToDouble(element -> element).sum()/data.size()));
    }
}
