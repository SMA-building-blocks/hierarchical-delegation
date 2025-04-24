package hierarchical_delegation.strategies;

import java.util.ArrayList;

public class StdDeviationStrategy implements Strategy {
    @Override
    public Double executeOperation(ArrayList<Integer> data) {
        double avg = (double) data.stream().mapToDouble(element -> element).sum()/data.size();

        double variance = data.stream().mapToDouble(element -> ((element - avg)*(element - avg))).sum()/data.size();

        return Math.sqrt(variance);
    }
}
