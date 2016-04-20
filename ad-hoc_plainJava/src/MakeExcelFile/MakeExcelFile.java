package MakeExcelFile;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.util.HashMap;
import java.util.Scanner;



/**
 * This class reads in the AgentCumulativeSummary?cap_withTaskAssigned.txt
 * and put the stats into the correct order for excel to make graphs
 * @author BinChen
 *
 */
public class MakeExcelFile {
	
	
	/**
	 * specify AO TO we want 
	 */
	static String cap="5";
	static double AO1=0; static double TO1=0;
//	static double AO1=0; static double TO1=0.5;
//	static double AO1=0.5; static double TO1=0;
//	static double AO1=0.5; static double TO1=0.5;
//	static double AO1=0.05; static double TO1=0.05;
	
	static String AgentfileName = "data5/AgentCumulativeSummary"+cap+"cap_withTaskAssgined.txt";
//	static String TaskfileName = "data4/AgentBidsCumulativeSummary"+cap+"cap.txt";
	static String outputFileName="data5/excel"+cap+"cap_"+"AO"+AO1+"TO"+TO1+".txt";
	
	
	


	
	static HashMap<String, AOTOOptionTick> map = new HashMap<String, AOTOOptionTick>();
	static double[] agentOpenness = { 0, 0.01, 0.02, 0.05, 0.1, 0.2, 0.5 };
	 static double [] taskOpenness = {0, 0.01, 0.02, 0.05, 0.1, 0.2, 0.5};
//	static double[] taskOpenness = { 0.5, 0.2, 0.1, 0.05, 0.02, 0.01, 0 };
	static int[] option = { 14, 15, 16, 17 };
	static int totalTick=1000;
	
	
	

	public static void main(String[] args) throws IOException {

		makingExcelFile();
		System.out.println("Done");
//		System.out.println(map.keySet());

	}
	
	
	public static void makingExcelFile() throws IOException{
		/**
		 * create the map
		 */
	
		for (double AO : agentOpenness) {
			for (double TO : taskOpenness) {
				for (int Op : option) {
					for (int i=1;i<=totalTick;i++){
						String str = "AO" + AO + "TO" + TO + "Op" + Op+"Tick"+i;
						AOTOOptionTick obj = new AOTOOptionTick();
						map.put(str, obj);
					}
					
				}
			}
		}

		/**
		 * reading data
		 */

		readFile(AgentfileName);
//		readTaskFile(TaskfileName);
		
		outputExcelFile(outputFileName);
	
	}
	
	
	public static void outputExcelFile(String fileName) throws IOException{
		 FileWriter writer=null;
		
		File outputFile=new File(outputFileName);
		try {
			 writer=new FileWriter(outputFile);
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		//build the string 
		StringBuilder sbTitle = new StringBuilder();
		sbTitle.append("tick,numUniqueTask14,numUniqueTask15,numUniqueTask16,numUniqueTask17,,"
				+"numUniqueTaskCum14,numUniqueTaskCum15,numUniqueTaskCum16,numUniqueTaskCum17,,,"
				+ "learning14,learning15,learning16,learning17,,"
				+ "learningCum14,learningCum15,learningCum16,learningCum17,,,"
				+ "rewards14,rewards15,rewards16,rewards17,,"
				+ "rewardsCum14,rewardsCum15,rewardsCum16,rewardsCum17,,,"
				+"bidWon14,bidWon15,bidWon16,bidWon17,,"
				+"bidWonCum14,bidWonCum15,bidWonCum16,bidWonCum17,,,"
				+"taskAssignedCum14,taskAssignedCum15,taskAssignedCum16,taskAssignedCum17,,"
				+"taskAssignedCum14,taskAssignedCum15,taskAssignedCum16,taskAssignedCum17 \n");
		
		writer.write(sbTitle.toString());
		
		

	
	
	
	
	
	
	int option[]={14,15,16,17};
	HashMap<Integer,AOTOOptionTick> optionMap = new HashMap<Integer,AOTOOptionTick>();
	
	double unquieTasksCum14=0;
	double unquieTasksCum15=0;
	double unquieTasksCum16=0;
	double unquieTasksCum17=0;

	double learningCum14=0;
	double learningCum15=0;
	double learningCum16=0;
	double learningCum17=0;
	
	double rewardCum14=0;
	double rewardCum15=0;
	double rewardCum16=0;
	double rewardCum17=0;
	
	double bidWonCum14=0;
	double bidWonCum15=0;
	double bidWonCum16=0;
	double bidWonCum17=0;
	
	double taskAssignedCum14=0;
	double taskAssignedCum15=0;
	double taskAssignedCum16=0;
	double taskAssignedCum17=0;	
	
	
	for (int i=1;i<=totalTick;i++){
		//pick AOTOOptionTick we need
		for (int Op : option) {
		 String str = "AO" + AO1 + "TO" + TO1 + "Op" + Op+"Tick"+i;
		 optionMap.put(Op, map.get(str));
		}
		
		
		
		 unquieTasksCum14+=optionMap.get(14).unquieTasks;
		 unquieTasksCum15+=optionMap.get(15).unquieTasks;
		 unquieTasksCum16+=optionMap.get(16).unquieTasks;
		 unquieTasksCum17+=optionMap.get(17).unquieTasks;
		
		 learningCum14+=optionMap.get(14).learning;
		 learningCum15+=optionMap.get(15).learning;
		 learningCum16+=optionMap.get(16).learning;
		 learningCum17+=optionMap.get(17).learning;
		
		 rewardCum14+=optionMap.get(14).reward;
		 rewardCum15+=optionMap.get(15).reward;
		 rewardCum16+=optionMap.get(16).reward;
		 rewardCum17+=optionMap.get(17).reward;
		
		 bidWonCum14+=optionMap.get(14).bidsWon;
		 bidWonCum15+=optionMap.get(15).bidsWon;
		 bidWonCum16+=optionMap.get(16).bidsWon;
		 bidWonCum17+=optionMap.get(17).bidsWon;
		
		 taskAssignedCum14+=optionMap.get(14).taskAssigned;
		 taskAssignedCum15+=optionMap.get(15).taskAssigned;
		 taskAssignedCum16+=optionMap.get(16).taskAssigned;
		 taskAssignedCum17+=optionMap.get(17).taskAssigned;
		
		
		
		//build the string 
		StringBuilder sb = new StringBuilder();
		sb.append(i+",");
		sb.append(optionMap.get(14).unquieTasks+",");
		sb.append(optionMap.get(15).unquieTasks+",");
		sb.append(optionMap.get(16).unquieTasks+",");
		sb.append(optionMap.get(17).unquieTasks+",,");
		
		sb.append(unquieTasksCum14+optionMap.get(14).unquieTasks+",");
		sb.append(unquieTasksCum15+optionMap.get(15).unquieTasks+",");
		sb.append(unquieTasksCum16+optionMap.get(16).unquieTasks+",");
		sb.append(unquieTasksCum17+optionMap.get(17).unquieTasks+",,,");
	
		sb.append(optionMap.get(14).learning+",");
		sb.append(optionMap.get(15).learning+",");
		sb.append(optionMap.get(16).learning+",");
		sb.append(optionMap.get(17).learning+",,");
		
		sb.append(learningCum14+optionMap.get(14).learning+",");
		sb.append(learningCum15+optionMap.get(15).learning+",");
		sb.append(learningCum16+optionMap.get(16).learning+",");
		sb.append(learningCum17+optionMap.get(17).learning+",,,");
		
		sb.append(optionMap.get(14).reward+",");
		sb.append(optionMap.get(15).reward+",");
		sb.append(optionMap.get(16).reward+",");
		sb.append(optionMap.get(17).reward+",,");
		
		sb.append(rewardCum14+optionMap.get(14).reward+",");
		sb.append(rewardCum15+optionMap.get(15).reward+",");
		sb.append(rewardCum16+optionMap.get(16).reward+",");
		sb.append(rewardCum17+optionMap.get(17).reward+",,,");
		
		sb.append(optionMap.get(14).bidsWon+",");
		sb.append(optionMap.get(15).bidsWon+",");
		sb.append(optionMap.get(16).bidsWon+",");
		sb.append(optionMap.get(17).bidsWon+",,");
		
		sb.append(bidWonCum14+optionMap.get(14).bidsWon+",");
		sb.append(bidWonCum15+optionMap.get(15).bidsWon+",");
		sb.append(bidWonCum16+optionMap.get(16).bidsWon+",");
		sb.append(bidWonCum17+optionMap.get(17).bidsWon+",,,");
	
		sb.append(optionMap.get(14).taskAssigned+",");
		sb.append(optionMap.get(15).taskAssigned+",");
		sb.append(optionMap.get(16).taskAssigned+",,");
		
		sb.append(taskAssignedCum14+optionMap.get(14).taskAssigned+",");
		sb.append(taskAssignedCum15+optionMap.get(15).taskAssigned+",");
		sb.append(taskAssignedCum16+optionMap.get(16).taskAssigned+",");
		sb.append(taskAssignedCum17+optionMap.get(17).taskAssigned+"\n");
	
		writer.write(sb.toString());
	}
		
			
			writer.close();
	}
	
	

	//
	public static void readFile(String fileName) {
		File inputFile = new File(fileName);
		Scanner s = null;
		try {
			s = new Scanner(inputFile);
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}

		// int inputCount = 0;

		// File attribute = new File("data/data2.txt");

		// FileWriter writer = null;
		//
		// try {
		// writer = new FileWriter(attribute);
		// } catch (IOException e) {
		// e.printStackTrace();
		// }

		String firstLine = s.nextLine();

		
		while (s.hasNext()) {
			String data[] = null;
			String line = s.nextLine().replace("\"", "");
			// System.out.println(line);
			data = line.split(",");
			double agent_openness = Double.parseDouble(data[0]);
			double task_openness = Double.parseDouble(data[1]);
			int option = Integer.parseInt(data[2]);
			int tick=Integer.parseInt(data[3]);

			String key="AO" + agent_openness + "TO" + task_openness + "Op" + option+"Tick"+tick;
			
			String numTaskAssignedPerTick = data[7];
			String bidsWonPerTick = data[5];

			String rewardPerTick = data[4];
			String learningGainPerTick = data[6];
			
			String numUniqueTask = data[8];
			
		
			AOTOOptionTick obj=map.get(key);
			obj.bidsWon=Double.parseDouble(bidsWonPerTick);
			obj.learning=Double.parseDouble(learningGainPerTick);
			obj.reward=Double.parseDouble(rewardPerTick);
			obj.taskAssigned=Double.parseDouble(numTaskAssignedPerTick);
			
			
			
			obj.unquieTasks=Double.parseDouble(numUniqueTask);
			
		}
	}
		
		public static void readTaskFile(String fileName) {
			File inputFile = new File(fileName);
			Scanner s = null;
			try {
				s = new Scanner(inputFile);
			} catch (FileNotFoundException e) {
				e.printStackTrace();
			}

			// int inputCount = 0;

			// File attribute = new File("data/data2.txt");

			// FileWriter writer = null;
			//
			// try {
			// writer = new FileWriter(attribute);
			// } catch (IOException e) {
			// e.printStackTrace();
			// }

			String firstLine = s.nextLine();

			
			while (s.hasNext()) {
				String data[] = null;
				String line = s.nextLine().replace("\"", "");
				// System.out.println(line);
				data = line.split(",");
				double agent_openness = Double.parseDouble(data[0]);
				double task_openness = Double.parseDouble(data[1]);
				int option = Integer.parseInt(data[2]);
				int tick=Integer.parseInt(data[3]);
				String key="AO" + agent_openness + "TO" + task_openness + "Op" + option+"Tick"+tick;
				
				String numUniqueTask = data[4];
				
			
				AOTOOptionTick obj=map.get(key);
				obj.unquieTasks=Double.parseDouble(numUniqueTask);
			}	
		
	}
	

		
	
}
