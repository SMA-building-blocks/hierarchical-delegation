package hierarchical_delegation.strategies;

import java.util.ArrayList;

public class MedianStrategy implements Strategy {
    @Override
    public Double executeOperation(ArrayList<Integer> data) {
        data.sort(null);
        return ( data.size() % 2 != 0 ? 
            (double) data.get(Math.floorDiv(data.size(),2)) : 
            (double) ((data.get(data.size()/2) + data.get((data.size()/2) - 1))/2));
    }
}
