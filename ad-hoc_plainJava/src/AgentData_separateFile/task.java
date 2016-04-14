package AgentData_separateFile;

import java.util.HashMap;
import java.util.HashSet;

public class task implements Comparable {
	int id;
	int type;
	double reward;
	HashSet<Integer> agentsAssigned=new HashSet<Integer>();
	int agentRequired;
	HashMap<Integer,Integer> agentSubtaskNumMap = new HashMap<Integer,Integer>(); //agentId ->numSubtasks it did
	
	int numAssigned;
	int numRequired;
	double ratio;
	int observedCapUsedCount=0;
	
	public task(int id, int type) {
		super();
		this.id = id;
		this.type = type;
	}
	
	
	public int getAgentsAssigned(){
		return this.agentsAssigned.size();
	}
	
	public String getAgentAssignedString(){
		StringBuilder sb= new StringBuilder();
		sb.append("[ ");
		
		for (Integer i : this.agentsAssigned){
			sb.append(i+" ");
		}
		sb.append("]");
		return sb.toString();
	}
	
	public String getAgentSubtaskNumMapString(){
		StringBuilder sb= new StringBuilder();
		sb.append("{ ");
		for (Integer i : this.agentSubtaskNumMap.keySet()){
			sb.append(i+"="+this.agentSubtaskNumMap.get(i)+" ");
		}
		sb.append("}");
		return sb.toString();
	}
	
	public static String getTitle(){
		String title="taskId,taskType,numAssigned,numRequired,ratio,reward,agentSet,assignment";
		return title;
	}
	
	public String toString(){
		double ratio=(double)this.agentsAssigned.size()/this.agentRequired;
		String value=String.format("%d,%d,%d,%d,%.4f,%.4f,%s,%s\n",this.id,this.type,this.agentsAssigned.size(),this.agentRequired,ratio,this.reward,this.getAgentAssignedString(),this.getAgentSubtaskNumMapString());
//		System.out.println(title);
//		System.out.println(value);
		return value;
		
	}


	@Override
	public int compareTo(Object o) {
		task t = (task) o; 
        return this.id - t.id ;

		
	}
	
}



