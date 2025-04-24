package hierarchical_delegation.strategies;

import java.util.ArrayList;

public class SortStrategy implements Strategy {
    @Override
    public ArrayList<Integer> executeOperation(ArrayList<Integer> data) {
        data.sort(null);
        return data;
    }
}
