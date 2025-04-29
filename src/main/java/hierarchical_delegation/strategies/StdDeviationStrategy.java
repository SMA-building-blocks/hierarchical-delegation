package hierarchical_delegation.strategies;

import java.util.ArrayList;
import java.util.Arrays;

public class StdDeviationStrategy implements Strategy {
    @Override
    public ArrayList<Double> executeOperation(ArrayList<Double> data) {
        double avg = data.stream().mapToDouble(element -> element).sum()/data.size();

        double variance = data.stream().mapToDouble(element -> ((element - avg)*(element - avg))).sum()/data.size();

        return new ArrayList<>(Arrays.asList(((double) Math.round(Math.sqrt(variance) * 100)/100)));
    }
}
