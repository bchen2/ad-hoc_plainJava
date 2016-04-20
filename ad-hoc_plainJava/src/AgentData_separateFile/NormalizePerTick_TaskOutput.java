package AgentData_separateFile;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;

import org.apache.commons.math3.stat.descriptive.DescriptiveStatistics;


/**
 * this method takes the task output of the simulation and calculate the averages and standard err
 * output format :   4.343245 (0.0024)  , first number is the average, the number in parenthesis is the standard err.
 * Note, we can use another class to calculate the 95% confidence interval and put it in the parenthesis
 * @author BinChen
 *
 */
public class NormalizePerTick_TaskOutput {

	static	HashMap<String,AOTOOption> map = new HashMap<String,AOTOOption>();
	static AOTOOption currentAOTOOption;
//	static String dir="/Users/BinChen/Desktop/result5/";
//	static String dir="/Volumes/My Passport for Mac/result5";
//	static String dir="/Volumes/My Passport for Mac/result6";
//	static String dir="/Volumes/My Passport for Mac/result7";
//	static String dir="/Users/BinChen/Desktop/test1_5cap";
//	static String dir="/Users/BinChen/Documents/OneDrive/13-2015 Spring/adhoc_jar";
	static String dir="/Users/BinChen/Desktop/testing111";
	
//	static String dir="/Volumes/My Passport for Mac/newTest2";
	
	public static void main(String[] args) throws IOException {
		
		
		if (args[0]!=null){
			dir=args[0];
		}
		
		
		
		//1. we do this per AO-TO-Option base 
		/**
		 * 1. find all the files per configuration
		 * 2. define descriptives 
		 * 3. dump every line of every file into the descriptives
		 * 4. output the descriptives of each configuration into just 1 line 
		 */
		
		
		double AO[] = {0,0.01,0.02,0.05,0.1,0.2,0.5};
//		double TO[]={0,0.05,0.1,0.2,0.5};
		double TO[]={0,0.01,0.02,0.05,0.1,0.2,0.5};
		int Option[]={14,15,16,17};

		
		for (double agentOpenness: AO){
			for (double taskOpenness: TO){
				for (int option: Option){
					AOTOOption item = new AOTOOption(agentOpenness,taskOpenness,option,1,1);
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
			if (f.isFile() && f.getName().contains("TaskOutput")){
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
		File AgentSummaryfile = new File(dir+"/Summary/TaskSummary.txt");
//		File TaskSummaryfile = new File(dir+"/Summary/TaskSummary.txt");
		FileWriter writer1 = null;
//		FileWriter writer2 = null;
		
		try {
			writer1 = new FileWriter(AgentSummaryfile);
//			writer2 = new FileWriter(TaskSummaryfile);
		} catch (IOException e) {
			e.printStackTrace();
		}
		
	

		writer1.write(AOTOOption.getNormalizedTitleTask()+"\n");
//		writer2.write(AOTOOption.getTaskTitle()+"\n");
		
		
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
			
			
			writer1.write(currentAOTOOption.getNormalizedValueTask()+"\n");
//			writer2.write(currentAOTOOption.getTaskInfo()+"\n");
			System.out.println("Configuration Done."+count);
//			ato.clearnStats();
			ato=null;
//			currentAOTOOption.clearnStats();
			currentAOTOOption=null;
			count++;
			
		}
		
		writer1.close();
//		writer2.close();
		
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
					processLine_Task(line);
				} catch (Exception e) {
					// TODO Auto-generated catch block
					System.out.println(line);
					System.out.println(inputFile);
					e.printStackTrace();
				}
					
			}
//			currentAOTOOption.addTasksToTaskStats();
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
			
			if (taskTobid!=0){
				if (selected.length()==4){//true in this case
					item.bidsWonOverBidsSubmittedRatio.addValue(1);
				}else{
					item.bidsWonOverBidsSubmittedRatio.addValue(0);
				}
			}
		
	
		
		
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
			
			
			int observedCapUsedCount=Integer.parseInt(token[23]);
			targetTask.observedCapUsedCount+=observedCapUsedCount;
}
}
	
	
	
	public static void processLine_Short(String line){
		
		
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
		
		
	
		
		
		
	
		int taskAssigned=Integer.parseInt(token[2]);
		if (taskAssigned==1){
			item.taskAssignedPerTick.addValue(1);
		}else{
			item.taskAssignedPerTick.addValue(0);
		}
		
		int bidsWon=Integer.parseInt(token[3]);
		if (bidsWon==1){
			item.bidsSubmittedAndWon.addValue(1);
		}else{
			item.bidsSubmittedAndWon.addValue(0);
		}
		
		
		
	
		
		double reward=Double.parseDouble(token[5]);
		if (reward!=0){
			item.rewardPerTask.addValue(reward);
		}
		item.rewardPerTick.addValue(reward);
		
	
		
	
		
		
		
			
	
		
	
		
		
		double selfGain=Double.parseDouble(token[6]);
		double observationGain=Double.parseDouble(token[7]);
		double learningGain=selfGain+observationGain;
		
		/**
		 * note, for some task, there is only one type of gain exists, we want to calculate
		 * perTask, hence when one type is present, we add it to the stats.
		 * also, the gain get calculated at the third tick after the task was assigned,
		 * so we can not just look at taskAssigned or not to add selfGain to stats.
		 * there is the case that when agent do a task and get no selfgain and no observationGain. but in 
		 * this case, rewards will never be 0.
		 */
		if (selfGain!=0){
			item.selfGainPerTask.addValue(selfGain);
		}
		item.selfGainPerTick.addValue(selfGain);
	
		if (observationGain!=0){
			item.observationGainPerTask.addValue(observationGain);
		}
		item.observationGainPerTick.addValue(observationGain);
	
		if(reward!=0){
			item.LearningGainPerTask.addValue(learningGain);
		}
		item.LearningGainPerTick.addValue(learningGain);

		
		

}
	
	
public static void processLine_Task(String line){
		
		
		String[] token = line.split(",");
		double entropy=Double.parseDouble(token[2]);
		double entropyPercentage=Double.parseDouble(token[3]);
		int numTasks=Integer.parseInt(token[1]);
		
		
		AOTOOption item=currentAOTOOption;
		
		item.numUniqueTask.addValue(numTasks);
		item.entropy.addValue(entropy);
		item.entropyPercentage.addValue(entropyPercentage);


}
	
	
}
	

