package AgentData_separateFile;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;

import org.apache.commons.math3.stat.descriptive.DescriptiveStatistics;



public class TaskDetail {

	static	HashMap<String,AOTOOption> map = new HashMap<String,AOTOOption>();
	static AOTOOption currentAOTOOption;
	static String dir="/Users/BinChen/Desktop/result1/";
//	static String dir="/Volumes/My Passport for Mac/result1";
	
	public static void main(String[] args) throws IOException {
		//1. we do this per AO-TO-Option base 
		/**
		 * 1. find all the files per configuration
		 * 2. for each file, create tasks and store them into the list in AOTOOption
		 * 3. after we have have all read all the files, and have all the tasks in the list,
		 * we use descriptives to calculate
		 * 4. output the descriptives of each configuration into just 1 line 
		 */
		
		
		double AO[] = {0,0.01,0.02,0.05,0.1};
		double TO[]={0,0.05,0.1,0.2,0.5};
		int Option[]={14,15,16};

		
		for (double agentOpenness: AO){
			for (double taskOpenness: TO){
				for (int option: Option){
					AOTOOption item = new AOTOOption(agentOpenness,taskOpenness,option);
					String key="AO"+agentOpenness+"TO"+taskOpenness+"Option"+option;
					map.put(key, item);
				}
			}
		}
		
		
		ArrayList<File> allFiles = new ArrayList<File>();
		ArrayList<File> allAgentOutputFiles = new ArrayList<File>();
		allFiles.addAll(FindFiles.listf(dir));
		
		
		for (File f: allFiles){
//			AgentOutput
			if (f.isFile() && f.getName().contains("AgentOutput")){
				allAgentOutputFiles.add(f);
//				System.out.println(f.getName());
			}
		}
		
		
		
		for (File f :allAgentOutputFiles){
			String name=f.getName();
//			System.out.println(name);
			
			String token[]=name.split("_");
			String name1=token[1];
			double agentOpenness=Double.parseDouble(name1.substring(2,name1.indexOf("TO")));
			double taskOpenness=Double.parseDouble(name1.substring(name1.indexOf("TO")+2,name1.indexOf("Op")));
			int option=Integer.parseInt(name1.substring(name1.indexOf("Op")+2));
			
			String key="AO"+agentOpenness+"TO"+taskOpenness+"Option"+option;
			map.get(key).fileList.add(f);
		}
		
		
		/**
		 * print numbers of files per AOTOOption
		 */
		for (AOTOOption ato: map.values()){
			String stats="AO"+ato.AO+"TO"+ato.TO+"Option"+ato.option+"  ="+ato.fileList.size();
		System.out.println(stats);
		}
		
		
		File direcotry = new File(dir+"/Summary");
		if (!direcotry.exists()) {
			if (direcotry.mkdir()) {
				System.out.println("Directory is created!");
			} else {
				System.out.println("Failed to create directory!");
			}
		}
		
		
		/** open an writer */
		File TaskSummaryfile = new File(dir+"/Summary/TaskSummary.txt");
		FileWriter writer1 = null;
		
		try {
			writer1 = new FileWriter(TaskSummaryfile);
		} catch (IOException e) {
			e.printStackTrace();
		}
		
	

		writer1.write(AOTOOption.getTaskTitle()+"\n");
		
		
		
		/**
		 * for each configuration, open all the files and do the calculation
		 */
		long startTime = System.nanoTime();
		
		int count=1;
		
		
//System.out.println(map.keySet());
		
		



//currentAOTOOption=map.get("AO0.1TO0.05Option14");	
//System.out.println(currentAOTOOption.fileList.size());
//for (File f: map.get("AO0.1TO0.05Option14").fileList){
//	String fileName=f.getAbsolutePath();
//	readInFile1(fileName);
//}




		for (AOTOOption ato: map.values()){
			
			currentAOTOOption=ato;
			
//			Runtime rt = Runtime.getRuntime();
//			System.out.println("before loop , Available Free Memory: " + rt.freeMemory());
			
			for (File f: currentAOTOOption.fileList){
				String fileName=f.getAbsolutePath();
				
				try {
					readInFile1(fileName);
				} catch (Exception e) {
					// TODO Auto-generated catch block
					System.out.println(fileName);
				
					e.printStackTrace();
				}
					
				
				
			}
			
			
			
			
//			System.out.println("Free Memory before call to gc(): " + 
//					rt.freeMemory());
//					System.runFinalization();
//					System.gc();
//					System.out.println(" Free Memory after call to gc(): " + 
//					rt.freeMemory()); 
			
			
			writer1.write(currentAOTOOption.getTaskInfo()+"\n");
			System.out.println("Configuration Done."+count);
//			ato.clearnStats();
			ato=null;
//			currentAOTOOption.clearnStats();
			currentAOTOOption=null;
			count++;
			
		}
		
		writer1.close();
		
		/**
		 * play a sound to notify job is done.
		 */
		java.awt.Toolkit.getDefaultToolkit().beep();		
	
		long endTime = System.nanoTime();
		long duration = (endTime - startTime);  //divide by 1000000 to get milliseconds.
		long nanoTime=duration/1000000;
		long second=nanoTime/1000;
		System.out.println("Duriation = "+duration/1000000+ "ms or "+second + " second");
		
		
		
		
		
		
		
		
		
		
	}
	
	public static void readInFile1(String inputFile){	
		
		String title = null;
		String line = null;
		
		try {
			BufferedReader reader = new BufferedReader(new FileReader(inputFile));
			if ((line=reader.readLine())!=null){
				title=line;
			}
			
			while((line=reader.readLine())!=null) {
				
				try {
					processLine1(line);
				} catch (Exception e) {
					// TODO Auto-generated catch block
					System.out.println(line);
					System.out.println(inputFile);
					e.printStackTrace();
				}
					
			}
			
			currentAOTOOption.addTasksToTaskStats();
//			currentAOTOOption.taskMap.clear();
			
			
			reader.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		
		/**
		 * read in lines
		 */
//		System.out.println("reading done!");

	}
	
	
	
	
	public static void processLine1(String line){
		
		
		String[] token = line.split(",");
		int agentId=Integer.parseInt(token[1]);
		int tick=(int) Double.parseDouble(token[0]);
		
		AOTOOption item=currentAOTOOption;

	/**
	 * processing agent info
	 */
//	Agent targetAgent=null;
//	if (!item.AgentMap.keySet().contains(agentId)){
//		targetAgent= new Agent(agentId);
//		targetAgent.tickIn=tick;
//		item.AgentMap.put(agentId, targetAgent);
//	}else{
//		targetAgent=item.AgentMap.get(agentId);
//	}
		int taskTobid=Integer.parseInt(token[5]);
		
	
		int taskAssignment=Integer.parseInt(token[3]);
		if (taskAssignment!=0){
			item.taskAssignedPerTick.addValue(1);
		}else{
			item.taskAssignedPerTick.addValue(0);
		}
		
		
		
		
		
		int numSubtaskAssigned=Integer.parseInt(token[18]);
		if (numSubtaskAssigned!=0){
			item.subtasksAssignedPerTask.addValue(numSubtaskAssigned);
		}
		item.subtasksAssignedPerTick.addValue(numSubtaskAssigned);
		
		double reward=Double.parseDouble(token[17]);
		if (reward!=0){
			item.rewardPerTask.addValue(reward);
		}
		item.rewardPerTick.addValue(reward);
		
	
		if (taskTobid!=0){
			item.bidsSubmitted.addValue(1);
		}else{
			item.bidsSubmitted.addValue(0);
		}
	
		
		
			/*
			 * notice the selected does not parese to boolean correctly and string comparesion also dones not work
			 */
			String selected=token[8].trim();
//			System.err.println("token = " +selected+ "length="+ selected.length());
//			System.out.println("Pass in  ======"  + selected.toUpperCase());
			if (selected.length()==4){//true in this case
//				System.err.println("true");
				item.bidsSubmittedAndWon.addValue(1);
			}else{
//				System.err.println("false");
				item.bidsSubmittedAndWon.addValue(0);
			}
//			System.out.println();
			
		
	
		
		
		double selfGain=Double.parseDouble(token[20]);
		double observationGain=Double.parseDouble(token[21]);
		double learningGain=selfGain+observationGain;
		
		/**
		 * note, for some task, there is only one type of gain exists, we want to calculate
		 * perTask, hence when one type is present, we add it to the stats.
		 * also, the gain get calculated at the third tick after the task was assigned,
		 * so we can not just look at taskAssigned or not to add selfGain to stats.
		 * there is the case that when agent do a task and get no selfgain and no observationGain. but in 
		 * this case, rewards will never be 0.
		 */
		if (reward!=0){
			item.selfGainPerTask.addValue(selfGain);
		}
		item.selfGainPerTick.addValue(selfGain);
	
		if (reward!=0){
			item.observationGainPerTask.addValue(observationGain);
		}
		item.observationGainPerTick.addValue(observationGain);
	
		if(reward!=0){
			item.LearningGainPerTask.addValue(learningGain);
		}
		item.LearningGainPerTick.addValue(learningGain);
		
		
		
		
		
		/**
		 * processing Task info
		 */
		int taskType=Integer.parseInt(token[10]);
		int taskId=taskAssignment;
		if (taskId!=0){
			task targetTask=null;
			if (!item.taskMap.keySet().contains(taskId)){
				targetTask= new task(taskId,taskType);
				item.taskMap.put(taskId, targetTask);
			}else{
				targetTask=item.taskMap.get(taskId);
			}
			targetTask.agentRequired=Integer.parseInt(token[16]);
			targetTask.agentsAssigned.add(agentId);
			int numSubtasksAssigned=Integer.parseInt(token[18]);
			targetTask.agentSubtaskNumMap.put(agentId, numSubtasksAssigned);
			double taskReward=Double.parseDouble(token[19]);
			targetTask.reward=taskReward;
			int observedCapUsedCount=Integer.parseInt(token[22]);
			targetTask.observedCapUsedCount+=observedCapUsedCount;
}
		
}
}
