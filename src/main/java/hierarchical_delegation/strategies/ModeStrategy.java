package hierarchical_delegation.strategies;

import java.util.ArrayList;
import java.util.Hashtable;

public class ModeStrategy implements Strategy {
    @Override
    public ArrayList<Integer> executeOperation(ArrayList<Integer> data) {
        data.sort(null);

        // WIP: to be fixed

        int prev = data.get(0), counter = 0;
        Hashtable<Integer, Integer> ht = new Hashtable<>();
        for ( int element : data ) {
            if ( element == prev ) counter++;
            else {
                ht.put(prev, counter);
                prev = element;
                counter = 0;
            }
        }

        ArrayList<Integer> result = new ArrayList<>();
        int maxCount = -1;
        while ( ht.keys().hasMoreElements() ) {
            int key = ht.keys().nextElement();
           
            if ( ht.get(key) > maxCount ) result.clear();
            else if ( ht.get(key) < maxCount ) continue;

            result.add(key);
            maxCount = ht.get(key);
        }
        return data;
    }
}
