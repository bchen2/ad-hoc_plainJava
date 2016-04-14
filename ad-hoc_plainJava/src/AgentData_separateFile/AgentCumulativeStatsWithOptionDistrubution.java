package AgentData_separateFile;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;

public class AgentCumulativeStatsWithOptionDistrubution {

	/**
	 * this method read all the AgentOupts and for each AOTOOption, calculates the average cumulative rewards, bidsWon,
	 * learning for the agents stays in the ENTIRE SIMULATION only, for each tick and output them into a file (each AOTOOption has one file, each file contains stats for each 
	 * tick.
	 * @param args
	 */

	static	HashMap<String,AOTOOption> map = new HashMap<String,AOTOOption>();
	static AOTOOption currentAOTOOption;
//	static String dir="/Users/BinChen/Desktop/result5/";
//	static String dir="/Volumes/My Passport for Mac/result5";
//	static String dir="/Volumes/My Passport for Mac/result6";
//	static String dir="/Volumes/My Passport for Mac/result7";
//	static String dir="/Users/BinChen/Desktop/result5/";
//	static String dir="/Users/BinChen/Documents/resultX";
//	static String dir="/Users/BinChen/Documents/result9";
//	static String dir="/Users/BinChen/Desktop/result9";
//	static String dir="/Volumes/My Passport for Mac/result8";
//	static String dir="/Users/student/Desktop/X";
//	static String dir="/Volumes/BIN'S DRIVE/Drive/repastOutput2";
//	static String dir="/Volumes/My Passport for Mac/repastOutput3";
//	static String dir="/Users/BinChen/Documents/OneDrive/ad-hoc/Thesis/repastOutput3_25-25-25-25";
//	static String dir="/Users/BinChen/Desktop/50-0-0-50";
//	static String dir="/Users/BinChen/Desktop/25-25-25-25";
	static String dir="/Volumes/My Passport for Mac/result10";
	
	static int totalTick=103;
//	static int agentLife=150;/*find the agents who stayed in the simulation at least equals agentLife many ticks */
	
	static double AO[] = {0,0.01,0.02,0.05,0.1};
	static double TO[]={0,0.01,0.02,0.05,0.1};
	static int Option[]={99};
//	static String Distrubution[]={"25-25-25-25"};
//	static String Distrubution[]={"50-0-0-50"};
//	static String Distrubution[]={"50-0-50-0"};
//	static String Distrubution[]={"50-50-0-0"};
//	static String Distrubution[]={"0-50-0-50"};
	static String Distrubution[]={"0-0-50-50"};
	
//	static double AO[] = {0};
//	static double TO[]={0};
//	static int Option[]={14};
	
	public static void main(String[] args) throws IOException {
		//1. we do this per AO-TO-Option_Distrubution base 
		/**
		 * 1. find all the files per configuration
		 * 2. define descriptives 
		 * 3. dump every line of every file into the descriptives
		 * 4. output the descriptives of each configuration into just 1 line 
		 */
		
		//AgentOutput_AO0.05TO0.25Op99_50-0-0-50_2016-01-25_00.07.08

		/**
		 * initialize the AOTOOption map
		 */
		for (double agentOpenness: AO){
			for (double taskOpenness: TO){
				for (int option: Option){
					for (String dis: Distrubution){
						AOTOOption item = new AOTOOption(agentOpenness,taskOpenness,option,totalTick,dis);
						String key="AO"+agentOpenness+"TO"+taskOpenness+"Option"+option+"_"+dis;
						map.put(key, item);
					}
					
				}
			}
		}
		
		System.out.println(map);
		
		ArrayList<File> allFiles = new ArrayList<File>();
		ArrayList<File> allAgentOutputFiles = new ArrayList<File>();
		allFiles.addAll(FindFiles.listf(dir));
		
		/**
		 * finding all the files that contains "AgentOutput"
		 */
		for (File f: allFiles){
//			AgentOutput
			if (f.isFile() && f.getName().contains("AgentOutput") && f.getName().contains("Op99")){
				allAgentOutputFiles.add(f);
//				System.out.println(f.getName());
			}
		}
		
		
		/**
		 * find the files that belongs to each AOTOOption and add them to the filesList of each AOTOOption_Distrubution
		 */
		for (File f :allAgentOutputFiles){
			String name=f.getName();
//			System.out.println(name);
			
			String token[]=name.split("_");
			String name1=token[1];
			double agentOpenness=Double.parseDouble(name1.substring(2,name1.indexOf("TO")));
			double taskOpenness=Double.parseDouble(name1.substring(name1.indexOf("TO")+2,name1.indexOf("Op")));
			int option=Integer.parseInt(name1.substring(name1.indexOf("Op")+2));
			String distrubution=token[2];
			
			
			String key="AO"+agentOpenness+"TO"+taskOpenness+"Option"+option+"_"+distrubution;
//			System.out.println("getting key : "+key);
			if (map.get(key)!=null){
//				System.out.println("~~~~~");
				map.get(key).fileList.add(f);
			}
			
		}
		
		
		
		/**
		 * print numbers of files per AOTOOption
		 */
		for (AOTOOption ato: map.values()){
			String stats="AO"+ato.AO+"TO"+ato.TO+"Option"+ato.option+"_"+ato.optionDistrubution+"  ="+ato.fileList.size();
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
		String dis=Distrubution[0];
		File AgentSummaryfile = new File(dir+"/Summary/AgentCumulativeSummaryDistrubution"+dis+".txt");
//		File TaskSummaryfile = new File(dir+"/Summary/TaskSummary.txt");
		FileWriter writer1 = null;
//		FileWriter writer2 = null;
		
		try {
			writer1 = new FileWriter(AgentSummaryfile);
//			writer2 = new FileWriter(TaskSummaryfile);
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		
	
		writer1.write("AO,TO,Option,"+dis+",tick,Rewards14,TaskAssigned14,Learning14,Rewards15,TaskAssigned15,Learning15"
				+ ",Rewards16,TaskAssigned16,Learning16,Rewards17,TaskAssigned,Learning17\n");
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
			
			ArrayList<Double> cumulativeBidsWonList = new ArrayList<Double>(Arrays.asList(0.0,0.0,0.0,0.0));
			ArrayList<Double> cumulativeLearningGainList = new ArrayList<Double>(Arrays.asList(0.0,0.0,0.0,0.0));
			ArrayList<Double> cumulativeRewardList = new ArrayList<Double>(Arrays.asList(0.0,0.0,0.0,0.0));
			
			
			for (TickStats tStats: TickStatsList){
				int numfile=currentAOTOOption.fileList.size();
				
				for (int i=0;i<4;i++){
					tStats.cumulativeRewardList.set(i,tStats.cumulativeRewardList.get(i)/numfile);
					tStats.cumulativeLearningGainList.set(i, tStats.cumulativeLearningGainList.get(i)/numfile);
					tStats.cumulativeBidsWonList.set(i, tStats.cumulativeBidsWonList.get(i)/numfile);
					
					cumulativeBidsWonList.set(i, cumulativeBidsWonList.get(i)+tStats.cumulativeBidsWonList.get(i));
					cumulativeLearningGainList.set(i, cumulativeLearningGainList.get(i)+ tStats.cumulativeLearningGainList.get(i));
					cumulativeRewardList.set(i, cumulativeRewardList.get(i)+tStats.cumulativeRewardList.get(i));
					
				}
			
				
			
				
				writer1.write(currentAOTOOption.AO+","+currentAOTOOption.TO+","+currentAOTOOption.option+","+currentAOTOOption.optionDistrubution+","+tStats.tick
						+","+cumulativeRewardList.get(0)+","+cumulativeBidsWonList.get(0)+","+cumulativeLearningGainList.get(0)
						+","+cumulativeRewardList.get(1)+","+cumulativeBidsWonList.get(1)+","+cumulativeLearningGainList.get(1)
						+","+cumulativeRewardList.get(2)+","+cumulativeBidsWonList.get(2)+","+cumulativeLearningGainList.get(2)
						+","+cumulativeRewardList.get(3)+","+cumulativeBidsWonList.get(3)+","+cumulativeLearningGainList.get(3)+"\n");
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
					processLine1(line);
				} catch (Exception e) {
					// TODO Auto-generated catch block
					System.out.println(line);
					System.out.println(inputFile);
					e.printStackTrace();
				}
					
			}
			//after reading one file
			
			//find which agent stayedAllTicks
			AOTOOption item=currentAOTOOption;
			
			
			
			
			
			for (Agent a: item.AgentMap.values()){
//				System.out.println("Agent="+a.id+" tickIn="+a.tickIn+" tickOut="+a.tickOut);
//				if (a.tickOut-a.tickIn+1>=agentLife){
//					item.AgentStayAllTickList.add(a);
//				}
				
//				if (a.tickIn==1 && a.tickOut==item.totalTick){
//					item.AgentStayAllTickList.add(a);
//				}
//				item.AgentStayAllTickList.add(a);
				
					if (a.optionType==14){
						item.AgentOp14.add(a);
					}else if (a.optionType==15){
						item.AgentOp15.add(a);
					}else if (a.optionType==16){
						item.AgentOp16.add(a);
					}else if (a.optionType==17){
						item.AgentOp17.add(a);
					}
				
			}
			
			
//			System.out.println("@@@@@@@@@Agent op14 ="+item.AgentOp14.size());
//			System.out.println("@@@@@@@@@Agent op15 ="+item.AgentOp15.size());
//			System.out.println("@@@@@@@@@Agent op16 ="+item.AgentOp16.size());
//			System.out.println("@@@@@@@@@Agent op17 ="+item.AgentOp17.size());
			
			
			/**
			 * for the agents stayted all ticks, get their tickStatics and add it to the tickStatics of the file
			 */
			
			for (int i=1;i<=item.totalTick;i++){
				ArrayList<Double> cumulativeRewardTickList = new ArrayList<Double>(Arrays.asList(0.0,0.0,0.0,0.0));
				ArrayList<Double> cumulativeBidsWonTickList = new ArrayList<Double>(Arrays.asList(0.0,0.0,0.0,0.0));
				ArrayList<Double> cumulativeLearningTickGainList = new ArrayList<Double>(Arrays.asList(0.0,0.0,0.0,0.0));
//				double cumulativeBidsWonTick=0;
//				double cumulativeLearningGainTick=0;
//				double cumulativeRewardTick=0;
				
				
				
				//add agents stats at this tick all up
				for (Agent a : item.AgentMap.values()){
					int index=0;
					if (a.optionType==14){
						index=0;
					}else if (a.optionType==15){
						index=1;
					}else if (a.optionType==16){
						index=2;
					}else if (a.optionType==17){
						index=3;
					}
					double value0=cumulativeRewardTickList.get(index)+a.tickMap.get(i).cumulativeReward;
					cumulativeRewardTickList.set(index, value0);
					double value1=cumulativeBidsWonTickList.get(index)+a.tickMap.get(i).cumulativeBidsWon;
					cumulativeBidsWonTickList.set(index, value1);
					double value2=cumulativeLearningTickGainList.get(index)+a.tickMap.get(i).cumulativeLearningGain;
					cumulativeLearningTickGainList.set(index, value2);
				}
				
				//get the average agent stats at this tick
				int n=0;
				for (int j=0;j<4;j++){
					if (j==0){
						n=item.AgentOp14.size();
					}else if (j==1){
						n=item.AgentOp15.size();
					}else if (j==2){
						n=item.AgentOp16.size();
					}else if (j==3){
						n=item.AgentOp17.size();
					}
					double value0=cumulativeRewardTickList.get(j)/n;
					cumulativeRewardTickList.set(j, value0);
					double value1=cumulativeBidsWonTickList.get(j)/n;
					cumulativeBidsWonTickList.set(j, value1);
					double value2=cumulativeLearningTickGainList.get(j)/n;
					cumulativeLearningTickGainList.set(j, value2);
					
					
					
				}
					
				
				//records the agerage agent stats to TickStats of this tick in AOTOOption
				TickStats tickS=item.tickMap.get(i);
			
				for (int k=0;k<4;k++){
					double value0=tickS.cumulativeRewardList.get(k)+cumulativeRewardTickList.get(k);
					tickS.cumulativeRewardList.set(k, value0);
					double value1=tickS.cumulativeBidsWonList.get(k)+cumulativeBidsWonTickList.get(k);
					tickS.cumulativeBidsWonList.set(k, value1);
					double value2=tickS.cumulativeLearningGainList.get(k)+cumulativeLearningTickGainList.get(k);
					tickS.cumulativeLearningGainList.set(k, value2);
				}
				
				
			}
			
			item.AgentMap.clear();
			item.AgentStayAllTickList.clear();
			item.AgentOp14.clear();
			item.AgentOp15.clear();
			item.AgentOp16.clear();
			item.AgentOp17.clear();
			
			
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
//		System.out.println("length="+token.length);
		if (token.length==25){
			
			int agentId=Integer.parseInt(token[1]);
			int tick=(int) Double.parseDouble(token[0]);
			
			AOTOOption item=currentAOTOOption;

			/**
			 * processing agent info
			 */
			Agent targetAgent = null;
			int optionType=0;
			try {
			    optionType=Integer.parseInt(token[24]);
			} catch (Exception e) {
				System.out.println("ignore one line, optionType not avavilable" );
				e.printStackTrace();
			}
			
			if (!item.AgentMap.keySet().contains(agentId) && optionType!=0) {
				targetAgent = new Agent(agentId,item.totalTick,optionType);
				targetAgent.tickIn = tick;
				item.AgentMap.put(agentId, targetAgent);
			} else {
				targetAgent = item.AgentMap.get(agentId);
			}

			targetAgent.addMaxTick(tick);

		
			int taskAssigned=0;
			
			
			try {
				taskAssigned=Integer.parseInt(token[3]);
			} catch (Exception e) {
				
				System.out.println("ignore one line, taskTobid not avavilable" );
				e.printStackTrace();
			}
			
//			String selected=token[8].trim();
			
//			if (taskTobid!=0 && selected.equalsIgnoreCase("true")){
////				System.out.println("task "+taskTobid+" true" +" tick "+tick+" ID:"+agentId);
////				item.tickMap.get(tick).cumulativeBidsWon++;
//				targetAgent.tickMap.get(tick).cumulativeBidsWon++;
//				
//			}else{
////				System.out.println("task "+taskTobid+" False" +" tick "+tick+" ID:"+agentId);
//			}
			
			if (taskAssigned!=0 ){
//				System.out.println("task "+taskTobid+" true" +" tick "+tick+" ID:"+agentId);
//				item.tickMap.get(tick).cumulativeBidsWon++;
				targetAgent.tickMap.get(tick).cumulativeBidsWon++;
				
			}else{
//				System.out.println("task "+taskTobid+" False" +" tick "+tick+" ID:"+agentId);
			}
			
			double reward=0;
			try {
				reward=Double.parseDouble(token[17]);
			} catch (Exception e) {
				System.out.println("ignore one line, reward not avavilable" );
				e.printStackTrace();
			}
			
//			item.tickMap.get(tick).cumulativeReward+=reward;
			targetAgent.tickMap.get(tick).cumulativeReward+=reward;
			
			
			
			double selfGain=0;
			double observationGain=0;
			
			try {
				selfGain=Double.parseDouble(token[20]);
				observationGain=Double.parseDouble(token[21]);
			} catch (Exception e) {
				System.out.println("ignore one line, selfGain not avavilable" );
				e.printStackTrace();
			}
			
			double learningGain=selfGain+observationGain;
			
//			item.tickMap.get(tick).cumulativeLearningGain+=learningGain;
			targetAgent.tickMap.get(tick).cumulativeLearningGain+=learningGain;
		}else{
//			System.out.println("ignore one line");
		}
		
		
		
		
}
}


