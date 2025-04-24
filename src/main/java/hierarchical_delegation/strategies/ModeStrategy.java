package hierarchical_delegation.strategies;

import java.util.ArrayList;

public class ModeStrategy implements Strategy {
    @Override
    public ArrayList<Integer> executeOperation(ArrayList<Integer> data) {
        data.sort(null);
        int prev = data.get(0);
        int maxCount = 1;
        int count = 1;
        ArrayList<Integer> mode = new ArrayList<>();

        for(int i = 1; i< data.size(); i++){
            if(data.get(i) == prev){
                count++;
            } else {
                if( count > maxCount){
                    maxCount = count;
                    mode.clear();
                    mode.add(prev);
                } else if (count == maxCount){
                    mode.add(prev);
                }
                count = 1;
                prev = data.get(i);
            }
        }
        
        if( count > maxCount){
            maxCount = count;
            mode.clear();
            mode.add(prev);
        } else if (count == maxCount){
            mode.add(prev);
        }

        return mode;
    }
}
