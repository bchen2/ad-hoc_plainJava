package AgentData_separateFile;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Scanner;

public class blackboardDetail {

	/**
	 * the file reads in agent data and find out what agent solving what task
	 * @param args
	 */
	public static void main(String[] args) {
		// TODO Auto-generated method stub

		
//		String fileName="/Users/BinChen/Desktop/results/run20/AgentData.2015.Oct.26.14_23_15.txt";//solved 103tasks AO=0 TO=0 Option14
		
		String fileName="/Users/BinChen/Desktop/results/run33/AgentData.2015.Oct.26.17_08_14.txt";//solved 412tasks AO=0 TO=0 Option14
		
		
		HashMap<Integer,task> taskMap = new HashMap<Integer,task>();
		HashMap<Integer,Agent> agentMap = new HashMap<Integer,Agent>();
		
		
		Scanner s = null;
		String title = null;
		String line = null;

		try {
			s = new Scanner(new File(fileName));
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}

		if (s.hasNext()) {
			title = s.next();
		}

		
		
		
		while (s.hasNext()) {
			line = s.next();
			String data[]=line.split(",");
			double  tick=Double.parseDouble(data[0]);
			double agentOpenness=Double.parseDouble(data[12]);
			double  taskOpenness=Double.parseDouble(data[13]);
			int option=Integer.parseInt(data[14]);
			int run=Integer.parseInt(data[9]);
//			int numAgentsIntroduced=Integer.parseInt(data[7]);
//			int numAgentsKilled=Integer.parseInt(data[8]);
//			double reward=Double.parseDouble(data[2]);
//			int numTask=Integer.parseInt(data[15]);
//			double ratio=reward/numTask;
			
			int taskAssignment= Integer.parseInt(data[3]);
			int taskType= Integer.parseInt(data[10]);
			int agentId= Integer.parseInt(data[1]);
			
			if (run==1 && agentOpenness==0 && taskOpenness==0 && option==14 && taskAssignment!=0 ){
				if (!taskMap.keySet().contains(taskAssignment)){
					task task1= new task(taskAssignment,taskType);
					task1.agentsAssigned.add(agentId);
					taskMap.put(taskAssignment, task1);
				}else{
					taskMap.get(taskAssignment).agentsAssigned.add(agentId);
				}
				
				if (!agentMap.containsKey(agentId)){
					Agent agent= new Agent(agentId);
					agentMap.put(agentId, agent);
					agent.taskSet.add(taskMap.get(taskAssignment));
					agent.taskIdSet.add(taskAssignment);
				}else{
					agentMap.get(agentId).taskSet.add(taskMap.get(taskAssignment));
					agentMap.get(agentId).taskIdSet.add(taskAssignment);
				}
				
				
			}
			
		
		}
		s.close();
		
		
		System.out.println("Task Solved = "+taskMap.size());
		HashSet<Integer> agentSet = new HashSet<Integer>();
		for (task t : taskMap.values()){
			agentSet.addAll(t.agentsAssigned);
		}
		
		System.out.println("Unquie Agent Number = "+agentSet.size());
		System.out.println("Agent Ids " +agentSet);
		System.out.println();
		
		int count=0;
		for (Integer i: agentSet){
			int numTask=agentMap.get(i).taskIdSet.size();
			count+=numTask;
			System.out.println("agent "+i+" involved "+ numTask +" tasks");
		}
		System.out.println();
		System.out.println("Among all agents that solved tasks, each agent involed in average of "+(double )count/agentSet.size());
		
	}

	
	
}
