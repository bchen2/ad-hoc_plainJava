package AgentData_separateFile;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;

public class AgentBidsCumulativeStats {

	/**
	 * this method read all the AgentOupts and for each AOTOOption, calculates the average cumulative rewards, bidsWon,
	 * learning, for each tick and output them into a file (each AOTOOption has one file, each file contains stats for each 
	 * tick.
	 * @param args
	 */

	
	
	static	HashMap<String,AOTOOption> map = new HashMap<String,AOTOOption>();
	static AOTOOption currentAOTOOption;
	static String dir="/Users/BinChen/Desktop/testing111/";
//	static String dir="/Volumes/My Passport for Mac/result5";
//	static String dir="/Volumes/My Passport for Mac/result6";
//	static String dir="/Volumes/My Passport for Mac/result7";
//	static String dir="/Users/BinChen/Desktop/result5/";
	
//	static String dir="/Users/BinChen/Documents/resultX";
//	static String dir="/Users/BinChen/Documents/result7";

	
	
	
	static int totalTick=1000;
	static double AO[] = {0,0.01,0.02,0.05,0.1,0.2,0.5};
	static double TO[]={0,0.01,0.02,0.05,0.1,0.2,0.5};
	static int Option[]={14,15,16,17};
	
//	static double AO[] = {0};
//	static double TO[]={0};
//	static int Option[]={14};
	
	public static void main(String[] args) throws IOException {
		//1. we do this per AO-TO-Option base 
		/**
		 * 1. find all the files per configuration
		 * 2. define descriptives 
		 * 3. dump every line of every file into the descriptives
		 * 4. output the descriptives of each configuration into just 1 line 
		 */
		
		
		
		if (args[0]!=null){
			System.out.println(args[0]);
			dir=args[0];
		}
		
		

		/**
		 * initialize the AOTOOption map
		 */
		for (double agentOpenness: AO){
			for (double taskOpenness: TO){
				for (int option: Option){
					AOTOOption item = new AOTOOption(agentOpenness,taskOpenness,option,totalTick);
					String key="AO"+agentOpenness+"TO"+taskOpenness+"Option"+option;
					map.put(key, item);
				}
			}
		}
		
		
		ArrayList<File> allFiles = new ArrayList<File>();
		ArrayList<File> allAgentOutputFiles = new ArrayList<File>();
		allFiles.addAll(FindFiles.listf(dir));
		
		/**
		 * finding all the files that contains "AgentOutput"
		 */
		for (File f: allFiles){
//			AgentOutput
			if (f.isFile() && f.getName().contains("TaskOutput")){
				allAgentOutputFiles.add(f);
//				System.out.println(f.getName());
			}
		}
		
		
		/**
		 * find the files that belongs to each AOTOOption and add them to the filesList of each AOTOOption
		 */
		for (File f :allAgentOutputFiles){
			String name=f.getName();
//			System.out.println(name);
			
			String token[]=name.split("_");
			String name1=token[1];
			double agentOpenness=Double.parseDouble(name1.substring(2,name1.indexOf("TO")));
			double taskOpenness=Double.parseDouble(name1.substring(name1.indexOf("TO")+2,name1.indexOf("Op")));
			int option=Integer.parseInt(name1.substring(name1.indexOf("Op")+2));
			
			
			String key="AO"+agentOpenness+"TO"+taskOpenness+"Option"+option;
			System.out.println(key);
			map.get(key).fileList.add(f);
		}
		
		
		
		/**
		 * print numbers of files per AOTOOption
		 */
		for (AOTOOption ato: map.values()){
			String stats="AO"+ato.AO+"TO"+ato.TO+"Option"+ato.option+"  ="+ato.fileList.size();
		System.out.println(stats);
		}
		
		/**
		 * create new directory to store the output files
		 */
		File direcotry = new File(dir+"/Summary");
		if (!direcotry.exists()) {
			if (direcotry.mkdir()) {
				System.out.println("Directory is created!");
			} else {
				System.out.println("Failed to create directory!");
			}
		}
		
		/** open an writer */
		File AgentSummaryfile = new File(dir+"/Summary/AgentBidsCumulativeSummary.txt");
//		File TaskSummaryfile = new File(dir+"/Summary/TaskSummary.txt");
		FileWriter writer1 = null;
//		FileWriter writer2 = null;
		
		try {
			writer1 = new FileWriter(AgentSummaryfile);
//			writer2 = new FileWriter(TaskSummaryfile);
		} catch (IOException e) {
			e.printStackTrace();
		}
		
	
		writer1.write("AO,TO,Option,tick,numUniqueTask\n");
//		writer1.write(AOTOOption.getNormalizedTitle()+"\n");
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
			
			/**
			 * calculation for this AOTOOption
			 */
			ArrayList<TickStats> TickStatsList = new ArrayList<TickStats>(currentAOTOOption.tickMap.values());
			Collections.sort(TickStatsList);
			
			double cumulativeBidsWon=0;
//			double cumulativeLearningGain=0;
//			double cumulativeReward=0;
			for (TickStats tStats: TickStatsList){
				int numfile=currentAOTOOption.fileList.size();
				
//				tStats.cumulativeBidsWon=tStats.cumulativeBidsWon/100; //there are 100 agent in each tick
				tStats.cumulativeBidsWon=tStats.cumulativeBidsWon/numfile;
				
//				tStats.cumulativeLearningGain=tStats.cumulativeLearningGain/100;
//				tStats.cumulativeLearningGain=tStats.cumulativeLearningGain/numfile;
//				
//				tStats.cumulativeReward=tStats.cumulativeReward/100;
//				tStats.cumulativeReward=tStats.cumulativeReward/numfile;
				
				cumulativeBidsWon+=tStats.cumulativeBidsWon;
//				cumulativeLearningGain+=tStats.cumulativeLearningGain;
//				cumulativeReward+=tStats.cumulativeReward;
				
				writer1.write(currentAOTOOption.AO+","+currentAOTOOption.TO+","+currentAOTOOption.option+","+tStats.tick
						+","+cumulativeBidsWon+"\n");
//				System.out.println(tStats.tick);
			}
			
			
			
			
			
			
//			writer1.write(currentAOTOOption.getNormalizedValue1()+"\n");
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
//					processLine1(line);
//					processLineForShortAgentOutput(line);
					processLineForTaskOutput(line);
				} catch (Exception e) {
					// TODO Auto-generated catch block
					System.out.println(line);
					System.out.println(inputFile);
					e.printStackTrace();
				}
					
			}
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
		
		String selected=token[8].trim();
		
		if (taskTobid!=0 && selected.equalsIgnoreCase("true")){
//			System.out.println("task "+taskTobid+" true" +" tick "+tick+" ID:"+agentId);
			item.tickMap.get(tick).cumulativeBidsWon++;
		}else{
//			System.out.println("task "+taskTobid+" False" +" tick "+tick+" ID:"+agentId);
		}
		
		
		double reward=Double.parseDouble(token[17]);
		item.tickMap.get(tick).cumulativeReward+=reward;
		
		
		
		
		double selfGain=Double.parseDouble(token[20]);
		double observationGain=Double.parseDouble(token[21]);
		double learningGain=selfGain+observationGain;
		
		item.tickMap.get(tick).cumulativeLearningGain+=learningGain;
		
		
		
}
	
	
	
	
	
	
	public static void processLineForShortAgentOutput(String line){
		
		
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
		
		
		int bidsWon = Integer.parseInt(token[3]);
		item.tickMap.get(tick).cumulativeBidsWon+=bidsWon;
		

		double reward=Double.parseDouble(token[5]);
		item.tickMap.get(tick).cumulativeReward+=reward;
		
		
		
		
		double selfGain=Double.parseDouble(token[6]);
		double observationGain=Double.parseDouble(token[7]);
		double learningGain=selfGain+observationGain;
		
		item.tickMap.get(tick).cumulativeLearningGain+=learningGain;
		
		
		
}
	
public static void processLineForTaskOutput(String line){
		
		
		String[] token = line.split(",");
//		int agentId=Integer.parseInt(token[1]);
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
		
		
		int unqiueTasks = Integer.parseInt(token[1]);
		item.tickMap.get(tick).cumulativeBidsWon+=unqiueTasks;
		

//		double reward=Double.parseDouble(token[5]);
//		item.tickMap.get(tick).cumulativeReward+=reward;
//		
//		
//		
//		
//		double selfGain=Double.parseDouble(token[6]);
//		double observationGain=Double.parseDouble(token[7]);
//		double learningGain=selfGain+observationGain;
//		
//		item.tickMap.get(tick).cumulativeLearningGain+=learningGain;
		
		
		
}
}


