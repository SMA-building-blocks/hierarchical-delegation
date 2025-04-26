package hierarchical_delegation.strategies;

import java.util.ArrayList;
import java.util.Arrays;

public class MedianStrategy implements Strategy {
    @Override
    public ArrayList<Double> executeOperation(ArrayList<Double> data) {
        data.sort(null);
        return new ArrayList<>(Arrays.asList(( data.size() % 2 != 0 ? 
            (double) data.get(Math.floorDiv(data.size(),2)) : 
            (double) ((data.get(data.size()/2) + data.get((data.size()/2) - 1))/2))));
    }
}
