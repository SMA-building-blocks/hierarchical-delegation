package hierarchical_delegation.strategies;

import java.util.ArrayList;

public class SortStrategy implements Strategy {
    @Override
    public ArrayList<Double> executeOperation(ArrayList<Double> data) {
        data.sort(null);
        return data;
    }
}
