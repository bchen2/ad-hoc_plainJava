package AgentData_separateFile;

import java.util.ArrayList;
import java.util.Arrays;

public class TickStats implements Comparable{
	int tick;
	double cumulativeReward=0;
	double cumulativeBidsWon=0;
	double cumulativeLearningGain=0;
	double cumulativeTaskAssigned=0;
	double cumulativeUniqueTasksBidsPerTick=0;
	
	int numAgentStay=0;
	
	ArrayList<Double> cumulativeRewardList = new ArrayList<Double>(Arrays.asList(0.0,0.0,0.0,0.0));
	ArrayList<Double> cumulativeBidsWonList = new ArrayList<Double>(Arrays.asList(0.0,0.0,0.0,0.0));
	ArrayList<Double> cumulativeLearningGainList = new ArrayList<Double>(Arrays.asList(0.0,0.0,0.0,0.0));
	
	
	@Override
	public int compareTo(Object o) {
		TickStats a = (TickStats) o; 
        return this.tick - a.tick ;
		
	}
}

