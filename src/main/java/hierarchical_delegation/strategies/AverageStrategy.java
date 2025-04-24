package hierarchical_delegation.strategies;

import java.util.ArrayList;

public class AverageStrategy implements Strategy {
    @Override
    public Double executeOperation(ArrayList<Integer> data) {
        return (double) data.stream().mapToDouble(element -> element).sum()/data.size();
    }
}
