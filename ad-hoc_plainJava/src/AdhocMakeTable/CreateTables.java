package AdhocMakeTable;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.util.HashMap;
import java.util.Scanner;

import cern.colt.Arrays;
import AdhocCollaboration.Blackboard;
import AdhocCollaboration.OutputClass;
import AdhocCollaboration.Parameters;

public class CreateTables {
	/**
	 * type0: numTaskAssignedPerTick 
	 * type1: bidsWonPerTick 
	 * type2: rewardPerTask
	 * type3: rewardPerTick 
	 * type4: learningGainPerTask
	 *  type5:learningGainPerTick 
	 * type6: selfGainPerTask
	 *  type7: selfGainPerTick 
	 *  type8:observationGainPerTask 
	 *  type9: observationGainPerTick
	 */

	static String fileName = "data4/AgentSummary.txt";

	static String graphTitle = "FinishedTasks";
	static String graphNotes = "";

	
	static HashMap<String, AOTOOption> map = new HashMap<String, AOTOOption>();
	
	
//	static double[] agentOpenness = { 0, 0.01, 0.02, 0.05, 0.1, 0.2, 0.5 };
//	// double [] taskOpenness = {0, 0.01, 0.02, 0.05, 0.1, 0.2, 0.5};
//	static double[] taskOpenness = { 0.5, 0.2, 0.1, 0.05, 0.02, 0.01, 0 };
//	static int[] option = { 14, 15, 16, 17 };
	
	
	static double[] agentOpenness = { 0, 0.01, 0.02, 0.05, 0.1, 0.2, 0.5 };
	// double [] taskOpenness = {0, 0.01, 0.02, 0.05, 0.1, 0.2, 0.5};
	static double[] taskOpenness = { 0.5, 0.2, 0.1, 0.05, 0.02, 0.01, 0 };
	static int[] option = { 14, 15, 16, 17 };

	static FileWriter writer=null;
	
	public static void plotTables(String dir,Parameters param) throws IOException {

		String fileName=dir+"/Summary/AgentSummary.txt";
		String outputFileName=dir+"/Summary/Tables.txt";
		File outputFile=new File(outputFileName);
		try {
			 writer=new FileWriter(outputFile);
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		
	    agentOpenness = param.agentOpenness;
		// double [] taskOpenness = {0, 0.01, 0.02, 0.05, 0.1, 0.2, 0.5};
		 taskOpenness = reverseList(param.taskOpenness);
//		 System.out.println(Arrays.toString(taskOpenness));
		option = param.option;
		
		

		/**
		 * create the map
		 */
	
		for (double AO : agentOpenness) {
			for (double TO : taskOpenness) {
				for (int Op : option) {
					String str = "AO" + AO + "TO" + TO + "Op" + Op;
					AOTOOption obj = new AOTOOption();
					map.put(str, obj);
				}
			}
		}

		/**
		 * reading data
		 */

		readFile(fileName);
		
		// System.out.println(map.keySet());

		/**
		 * create the table
		 */
		
		
		
		for (int i=0; i<=9;i++){
			makingTable(i);
		}
		writer.close();
		

	}
	
	
	public static double[] reverseList(double[] taskOpenness){
		 int n=taskOpenness.length;
		 double temp[] = new double[n];
		
		 for (int i=0;i<n;i++){
			 int index=n-i-1;
//			 System.out.println("i="+i + " j="+index);
			 temp[i]=taskOpenness[index];
		 }
		 
		return temp;
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

			String key="AO" + agent_openness + "TO" + task_openness + "Op" + option;
			
			String numTaskAssignedPerTick = data[3];
			String bidsWonPerTick = data[4];

			String rewardPerTask = data[5];
			String rewardPerTick = data[6];
			String learningGainPerTask = data[7];
			String learningGainPerTick = data[8];
			
			String selfGainPerTask = data[9];
			String selfGainPerTick = data[10];
			String observationGainPerTask = data[11];
			String observationGainPerTick = data[12];
		
			AOTOOption obj=map.get(key);
			obj.result.add(numTaskAssignedPerTick);
			obj.result.add(bidsWonPerTick);
			obj.result.add( rewardPerTask);
			obj.result.add(rewardPerTick);
			obj.result.add(learningGainPerTask);
			obj.result.add(learningGainPerTick);
			obj.result.add(selfGainPerTask);
			obj.result.add(selfGainPerTick);
			obj.result.add(observationGainPerTask);
			obj.result.add(observationGainPerTick);
			
		}
		
		
	}
		public static void makingTable(int type) throws IOException{
			/**
			 * making the table
			 */
	
			/**
			 * type0: numTaskAssignedPerTick 
			 * type1: bidsWonPerTick 
			 * type2: rewardPerTask
			 * type3: rewardPerTick 
			 * type4: learningGainPerTask
			 *  type5:learningGainPerTick 
			 * type6: selfGainPerTask
			 *  type7: selfGainPerTick 
			 *  type8:observationGainPerTask 
			 *  type9: observationGainPerTick
			 */
			
			
	       if (type==0){
	       graphTitle="numTaskAssignedPerTick";
	       //graphNotes="(Number of tasks per agent finished on average per AO-TO-Option combination )";
	       }else if (type==1){
	       graphTitle="bidsWonPerTick ";
	       //graphNotes="(The reward per agent gets on average per AO-TO-Option combination )";
	       }else if (type==2){
	       graphTitle="rewardPerTask";
	       //graphNotes="(The bids per agent submitted on average per AO-TO-Option combination )";
	       }else if (type==3){
	       graphTitle=" rewardPerTick";
	       //graphNotes="(The bids per agent submitted and won on average per AO-TO-Option combination )";
	       }else if (type==4){
	       graphTitle="learningGainPerTask";
	       //graphNotes="(bidsSubmittedAndWon/bidsSubmitted per agent on average per AO-TO-Option combination )";
	       }else if (type==5){
	       graphTitle="learningGainPerTick  ";
	       //graphNotes="(Number of subtasks assigned per agent on average per AO-TO-Option combination )";
	       }else if (type==6){
	       graphTitle="selfGainPerTask";
	       //graphNotes="(The learning from doing gain per agent on average per AO-TO-Option combination )";
	       }else if (type==7){
	       graphTitle="selfGainPerTick ";
	       //graphNotes="(The learning from observation gain per agent on average per AO-TO-Option combination )";
	       }else if (type==8){
	       graphTitle="observationGainPerTask ";
	       //graphNotes="(The total learning gain per agent on average per AO-TO-Option combination )";
	       }else if (type==9){
	       graphTitle="observationGainPerTick";
	       //graphNotes="(The number different types of tasks finised per agent on average per AO-TO-Option combination )";
	       }
	       
			
			
			
			
			
			
			
			
			
			
			
			
			
			
			
			

			String str1 = "";
			String str2 = "";
			String str3 = "";
			String str4 = "";
			String str5 = "";
			String str6 = "";
			String str7 = "";

			String str = "--------------------";
			String output=String.format("%8s    +%-20s+%-20s+%-20s+%-20s+%-20s+%-20s+%-20s+\n",
					"        ", str, str, str, str, str, str, str);
//			System.out.printf("%8s    +%-20s+%-20s+%-20s+%-20s+%-20s+%-20s+%-20s+\n",
//					"        ", str, str, str, str, str, str, str);
			 writer.write(output);
			double lastTO = 0.1;
			for (double TO : taskOpenness) {

				for (int Op : option) {
					int count = 0;

					for (double AO : agentOpenness) {
						count++;
						String key = "AO" + AO + "TO" + TO + "Op" + Op;
						String result="NA";
						try{
							 result=map.get(key).result.get(type);
						}catch (Exception e){
							e.printStackTrace();
							System.out.println("Problem with "+key);
							
						}
						
						
						if (count == 1) {
							str1 = result;
						} else if (count == 2) {
							str2 = result;
						} else if (count == 3) {
							str3 = result;
						} else if (count == 4) {
							str4 = result;
						} else if (count == 5) {
							str5 = result;
						} else if (count == 6) {
							str6 = result;
						} else if (count == 7) {
							str7 = result;
						}

						// String str="AO"+AO+"TO"+TO+"Op"+Op;
						// AOTOOption obj=new AOTOOption();
						// map.put(str, obj);
						// System.out.println(String.format("AO%fTO%fOp%d",
						// AO,TO,Op));

					}

					/*
					 * print one line
					 */

					// str2 = new String ("0.652820 (0.000275)");
					// String str2 = new String ("AO"+AO+"TO"+TO+"Op"+Op+"   ");
					String x = "";
					if (TO == lastTO) {
					} else {
						String TO1 = Double.toString(TO);
						x = "TO " + TO1;
						if (TO == 0.05) {
						} else {
							x = TO1;
						}
					}
					
					output=String.format("%8s    |%-20s|%-20s|%-20s|%-20s|%-20s|%-20s|%-20s|\n",
							x, str1, str2, str3, str4, str5, str6, str7);
//					System.out.printf(
//							"%8s    |%-20s|%-20s|%-20s|%-20s|%-20s|%-20s|%-20s|\n",
//							x, str1, str2, str3, str4, str5, str6, str7);
					
					
					writer.write(output);
					
					
					
					lastTO = TO;
				}

				/*
				 * print the separator
				 */
				String strx = "--------------------";
//				System.out.printf(
//						"%8s    +%-20s+%-20s+%-20s+%-20s+%-20s+%-20s+%-20s+\n",
//						"        ", strx, strx, strx, strx, strx, strx, strx);
				output=String.format("%8s    +%-20s+%-20s+%-20s+%-20s+%-20s+%-20s+%-20s+\n",
						"        ", strx, strx, strx, strx, strx, strx, strx);
				writer.write(output);

			}
			output=String.format("%25d %20.2f %20.2f %20.2f %20.2f %20.2f %20.2f \n",
					0, 0.01, 0.02, 0.05, 0.1, 0.2, 0.5);
//			System.out.printf("%25d %20.2f %20.2f %20.2f %20.2f %20.2f %20.2f \n",
//					0, 0.01, 0.02, 0.05, 0.1, 0.2, 0.5);
			writer.write(output);
			output=String.format("%80s\n", "AO");
//			System.out.printf("%80s\n", "AO");
			writer.write(output);
			output=String.format("%85s\n", graphTitle);
//			System.out.printf("%85s\n", graphTitle);
			writer.write(output);
		}
	
		
		
		// Row_AgentwithSTD row =new Row_AgentwithSTD();
		//
		//
		// // row.setTask_openness(task_openness);
		// // row.setAgent_openness(agent_openness);
		// // row.setOption(option);
		// // row.setTaskAssigned(taskAssigned);
		// // row.setReward(reward);row.setBidsSubmitted(bidsSubmittedAndWon);
		// // row.setBidsSubmittedAndWon(bidsSubmittedAndWon);
		// // row.setBidsWonRatio(bidsWonRatio);
		// // row.setSubtasksAssigned(subtasksAssigned);
		// // row.setSelfGain(selfGain);row.setObservationGain(observationGain);
		// //
		// row.setLearningGain(LearningGain);row.setTaskTypeAssignedFromSet(taskTypeAssignedFromSet);
		// // row.setTaskTypeBidsFor(TaskTypeBidsFor);
		//
		//
		//
		//
		// AOTO_AgentwithSTD target = new AOTO_AgentwithSTD();
		//
		// if (agent_openness==0.0){
		// if (task_openness==0.0){
		// target=AO0TO0;
		// }else if (task_openness==0.01){
		// target=AO0TO025;
		// }else if (task_openness==0.02){
		// target=AO0TO05;
		// }else if (task_openness==0.05){
		// target=AO0TO075;
		// }else if (task_openness==0.1){
		// target=AO0TO1;
		// }
		// }else if (agent_openness==0.01){
		// if (task_openness==0.0){
		// target=AO025TO0;
		// }else if (task_openness==0.01){
		// target=AO025TO025;
		// }else if (task_openness==0.02){
		// target=AO025TO05;
		// }else if (task_openness==0.05){
		// target=AO025TO075;
		// }else if (task_openness==0.1){
		// target=AO025TO1;
		// }
		// }else if (agent_openness==0.02){
		// if (task_openness==0.0){
		// target=AO05TO0;
		// }else if (task_openness==0.01){
		// target=AO05TO025;
		// }else if (task_openness==0.02){
		// target=AO05TO05;
		// }else if (task_openness==0.05){
		// target=AO05TO075;
		// }else if (task_openness==0.1){
		// target=AO05TO1;
		// }
		// }else if (agent_openness==0.05){
		// if (task_openness==0.0){
		// target=AO075TO0;
		// }else if (task_openness==0.01){
		// target=AO075TO025;
		// }else if (task_openness==0.02){
		// target=AO075TO05;
		// }else if (task_openness==0.05){
		// target=AO075TO075;
		// }else if (task_openness==0.1){
		// target=AO075TO1;
		// }
		// }else if (agent_openness==0.1){
		// if (task_openness==0.0){
		// target=AO1TO0;
		// }else if (task_openness==0.01){
		// target=AO1TO025;
		// }else if (task_openness==0.02){
		// target=AO1TO05;
		// }else if (task_openness==0.05){
		// target=AO1TO075;
		// }else if (task_openness==0.1){
		// target=AO1TO1;
		// }
		// }
		//
		//
		// String value=null;
		// // System.out.println("type = "+type);
		// if (type==1){
		// value=numTaskAssignedPerTick ;
		// }else if (type==2){
		// value=numTaskAssignedPerTaskString;
		// }else if (type==3){
		// value=numSubTaskAssignedPerTick;
		// }else if (type==4){
		// value=rewardPerTask;
		// }else if (type==5){
		// value=rewardPerTick;
		// }else if (type==6){
		// value=numBidsSubmittedPerTick;
		// }else if (type==7){
		// value=numBidsSubmittedAndWonPerTick;
		// }else if (type==8){
		// value=selfGainPerTask;
		// }else if (type==9){
		// value=selfGainPerTick;
		// }else if (type==10){
		// value=observationGainPerTask;
		// }else if (type==11){
		// value= observationGainPerTick;
		// }else if (type==12){
		// value=learningGainPerTask;
		// }else if (type==13){
		// value=learningGainPerTick;
		// }else if (type==14){
		// value=bidsWonOverBidsSubmittedRatio;
		// }else if (type==15){
		// value=subtaskOverTaskPerTickRatio;
		// }
		//
		//
		//
		// if (option==14){
		// target.option1=row;
		// target.value1=value;
		// }else if(option==15){
		// target.option2=row;
		// target.value2=value;
		// }else if (option==16){
		// target.option3=row;
		// target.value3=value;
		// }else if (option==17){
		// target.value4=value;
		// }
		//
		//
		//
		//
		// }
		// s.close();
		//
		//
		// }
		//
		//
		//
		// public static void MakeGraph(String name){
		//
		// if (type==1){
		// graphTitle="taskAssigned";
		// graphNotes="(Number of tasks per agent finished on average per AO-TO-Option combination )";
		// }else if (type==2){
		// graphTitle="reward";
		// graphNotes="(The reward per agent gets on average per AO-TO-Option combination )";
		// }else if (type==3){
		// graphTitle="bidsSubmitted";
		// graphNotes="(The bids per agent submitted on average per AO-TO-Option combination )";
		// }else if (type==4){
		// graphTitle="bidsSubmittedAndWon";
		// graphNotes="(The bids per agent submitted and won on average per AO-TO-Option combination )";
		// }else if (type==5){
		// graphTitle="bidsWonRatio";
		// graphNotes="(bidsSubmittedAndWon/bidsSubmitted per agent on average per AO-TO-Option combination )";
		// }else if (type==6){
		// graphTitle="subtasksAssigned";
		// graphNotes="(Number of subtasks assigned per agent on average per AO-TO-Option combination )";
		// }else if (type==7){
		// graphTitle="selfGain";
		// graphNotes="(The learning from doing gain per agent on average per AO-TO-Option combination )";
		// }else if (type==8){
		// graphTitle="observationGain";
		// graphNotes="(The learning from observation gain per agent on average per AO-TO-Option combination )";
		// }else if (type==9){
		// graphTitle="LearningGain";
		// graphNotes="(The total learning gain per agent on average per AO-TO-Option combination )";
		// }else if (type==10){
		// graphTitle="taskTypeAssigned";
		// graphNotes="(The number different types of tasks finised per agent on average per AO-TO-Option combination )";
		// }else if (type==11){
		// graphTitle="TaskTypeBidsFor";
		// graphNotes="(The number different types of tasks per agent bids for per AO-TO-Option combination )";
		// }else if (type==12){
		// graphTitle="subTaskOverTaskRatio";
		// graphNotes="(subtasksAssigned/taskAssigned per agent for per AO-TO-Option combination.\n The larger this number greater than 1, means more agents finised multiple subtasks within a task. )";
		// }
		//
		//
		//
		//
		//
		//
		//
		// System.out.printf("            +%-20s+%-20s+%-20s+%-20s+%-20s+\n",
		// "--------------------","--------------------","--------------------","--------------------","--------------------");
		// System.out.printf("            |%-10.2f|%-10.2f|%-10.2f|%-10.2f|%-10.2f%-20s\n",
		// AO0TO1.value1,AO025TO1.value1,AO05TO1.value1,AO075TO1.value1,AO1TO1.value1,"|");
		// System.out.printf("%8d    |%-10.2f|%-10.2f|%-10.2f|%-10.2f|%-10.2f%-20s\n",1,
		// AO0TO1.value2,AO025TO1.value2,AO05TO1.value2,AO075TO1.value2,AO1TO1.value2,"|");
		// System.out.printf("            |%-10.2f|%-10.2f|%-10.2f|%-10.2f|%-10.2f%-20s\n",
		// AO0TO1.value3,AO025TO1.value3,AO05TO1.value3,AO075TO1.value3,AO1TO1.value3,"|");
		// System.out.printf("            +%-20s+%-20s+%-20s+%-20s+%-20s+\n",
		// "--------------------","--------------------","--------------------","--------------------","--------------------");
		// System.out.printf("            |%-10.2f|%-10.2f|%-10.2f|%-10.2f|%-10.2f%-20s\n",
		// AO0TO075.value1,AO025TO075.value1,AO05TO075.value1,AO075TO075.value1,AO1TO075.value1,"|");
		// System.out.printf("%8.2f    |%-10.2f|%-10.2f|%-10.2f|%-10.2f|%-10.2f%-20s\n",0.75,
		// AO0TO075.value2,AO025TO075.value2,AO05TO075.value2,AO075TO075.value2,AO1TO075.value2,"|");
		// System.out.printf("            |%-10.2f|%-10.2f|%-10.2f|%-10.2f|%-10.2f%-20s\n",
		// AO0TO075.value3,AO025TO075.value3,AO05TO075.value3,AO075TO075.value3,AO1TO075.value3,"|");
		// System.out.printf("TO          +%-20s+%-20s+%-20s+%-20s+%-20s+\n",
		// "--------------------","--------------------","--------------------","--------------------","--------------------");
		// System.out.printf("            |%-10.2f|%-10.2f|%-10.2f|%-10.2f|%-10.2f%-20s\n",AO0TO05.value1,AO025TO05.value1,AO05TO05.value1,AO075TO05.value1,AO1TO05.value1,"|");
		// System.out.printf("%8.2f    |%-10.2f|%-10.2f|%-10.2f|%-10.2f|%-10.2f%-20s\n",0.5,AO0TO05.value2,AO025TO05.value2,AO05TO05.value2,AO075TO05.value2,AO1TO05.value2,"|");
		// System.out.printf("            |%-10.2f|%-10.2f|%-10.2f|%-10.2f|%-10.2f%-20s\n",AO0TO05.value3,AO025TO05.value3,AO05TO05.value3,AO075TO05.value3,AO1TO05.value3,"|");
		// System.out.printf("            +%-20s+%-20s+%-20s+%-20s+%-20s+\n",
		// "--------------------","--------------------","--------------------","--------------------","--------------------");
		// System.out.printf("            |%-10.2f|%-10.2f|%-10.2f|%-10.2f|%-10.2f%-20s\n",
		// AO0TO025.value1,AO025TO025.value1,AO05TO025.value1,AO075TO025.value1,AO1TO025.value1,"|");
		// System.out.printf("%8.2f    |%-10.2f|%-10.2f|%-10.2f|%-10.2f|%-10.2f%-20s\n",0.25,AO0TO025.value2,AO025TO025.value2,AO05TO025.value2,AO075TO025.value2,AO1TO025.value2,"|");
		// System.out.printf("            |%-10.2f|%-10.2f|%-10.2f|%-10.2f|%-10.2f%-20s\n",
		// AO0TO025.value3,AO025TO025.value3,AO05TO025.value3,AO075TO025.value3,AO1TO025.value3,"|");
		// // System.out.printf("+%-20s+%-20s+%-20s+%-20s+%-20s+\n",
		// "--------------------","--------------------","--------------------","--------------------","--------------------");
		//
		// System.out.printf("            +%-20s+%-20s+%-20s+%-20s+%-20s+\n",
		// "--------------------","--------------------","--------------------","--------------------","--------------------");
		// System.out.printf("            |%-10.2f|%-10.2f|%-10.2f|%-10.2f|%-10.2f%-20s\n",AO0TO0.value1,AO025TO0.value1,AO05TO0.value1,AO075TO0.value1,AO1TO0.value1,"|");
		// System.out.printf("%8d    |%-10.2f|%-10.2f|%-10.2f|%-10.2f|%-10.2f%-20s\n",0,
		// AO0TO0.value2,AO025TO0.value2,AO05TO0.value2,AO075TO0.value2,AO1TO0.value2,"|");
		// System.out.printf("            |%-10.2f|%-10.2f|%-10.2f|%-10.2f|%-10.2f%-20s\n",
		// AO0TO0.value3,AO025TO0.value3,AO05TO0.value3,AO075TO0.value3,AO1TO0.value3,"|");
		//
		// System.out.printf("            +%-20s+%-20s+%-20s+%-20s+%-20s+\n",
		// "--------------------","--------------------","--------------------","--------------------","--------------------");
		// System.out.printf("%18d %11.2f %10.2f %10.2f %9d \n",
		// 0,0.25,0.5,0.75,1);
		// System.out.printf("%40s\n", "AO");
		// System.out.printf("%45s\n",graphTitle);
		//
		// }
		//
		// public static void MakeFinalGraph(String name){
		//
		// if (type==1){
		// graphTitle=" numTaskAssignedPerTick  ";
		// //
		// graphNotes="(Number of tasks per agent finished on average per AO-TO-Option combination )";
		// graphNotes="";
		// }else if (type==2){
		// graphTitle="Subtask/Task per agent ";
		// //
		// graphNotes="(The reward per agent gets on average per AO-TO-Option combination )";
		// graphNotes="";
		// }else if (type==3){
		// graphTitle="numSubTaskAssignedPerTick";
		// //
		// graphNotes="(The bids per agent submitted on average per AO-TO-Option combination )";
		// graphNotes="";
		// }else if (type==4){
		// graphTitle="rewardPerTask";
		// //
		// graphNotes="(The bids per agent submitted and won on average per AO-TO-Option combination )";
		// graphNotes="";
		// }else if (type==5){
		// graphTitle="rewardPerTick";
		// //
		// graphNotes="(bidsSubmittedAndWon/bidsSubmitted per agent on average per AO-TO-Option combination )";
		// graphNotes="";
		// }else if (type==6){
		// graphTitle="numBidsSubmittedPerTick";
		// //
		// graphNotes="(Number of subtasks assigned per agent on average per AO-TO-Option combination )";
		// graphNotes="Notice when agent is executing a subtask, it won't bid. Hence here the value is less than 1.";
		// }else if (type==7){
		// graphTitle="numBidsSubmittedAndWonPerTick";
		// //
		// graphNotes="(The learning from doing gain per agent on average per AO-TO-Option combination )";
		// graphNotes="Notice we did not exclude the case when agents did not bid. When agent did not bid due to busy excuting task"
		// + "we count it as submitted and not won.";
		// }else if (type==8){
		// graphTitle="selfGainPerTask";
		// //
		// graphNotes="(The learning from observation gain per agent on average per AO-TO-Option combination )";
		// graphNotes="";
		// }else if (type==9){
		// graphTitle="selfGainPerTick";
		// //
		// graphNotes="(The total learning gain per agent on average per AO-TO-Option combination )";
		// graphNotes="";
		// }else if (type==10){
		// graphTitle="observationGainPerTask";
		// //
		// graphNotes="(The number different types of tasks finised per agent on average per AO-TO-Option combination )";
		// graphNotes="";
		// }else if (type==11){
		// graphTitle="observationGainPerTick";
		// //
		// graphNotes="(The number different types of tasks per agent bids for per AO-TO-Option combination )";
		// graphNotes="";
		// }else if (type==12){
		// graphTitle="learningGainPerTask";
		// //
		// graphNotes="(subtasksAssigned/taskAssigned per agent for per AO-TO-Option combination.\n The larger this number greater than 1, means more agents finised multiple subtasks within a task. )";
		// graphNotes="";
		// }else if (type==13){
		// graphTitle="learningGainPerTick";
		// //
		// graphNotes="(subtasksAssigned/taskAssigned per agent for per AO-TO-Option combination.\n The larger this number greater than 1, means more agents finised multiple subtasks within a task. )";
		// graphNotes="";
		// }else if (type==14){
		// graphTitle="bidsWonOverBidsSubmittedRatio";
		// //
		// graphNotes="(subtasksAssigned/taskAssigned per agent for per AO-TO-Option combination.\n The larger this number greater than 1, means more agents finised multiple subtasks within a task. )";
		// graphNotes="";
		// }else if (type==15){
		// graphTitle="subtaskOverTaskPerTickRatio";
		// //
		// graphNotes="(subtasksAssigned/taskAssigned per agent for per AO-TO-Option combination.\n The larger this number greater than 1, means more agents finised multiple subtasks within a task. )";
		// graphNotes="";
		// }
		//
		//
		//
		//
		//
		//
		//
		// System.out.printf("            +%-20s+%-20s+%-20s+%-20s+%-20s+\n",
		// "--------------------","--------------------","--------------------","--------------------","--------------------");
		//
		// System.out.printf("            |%-20s|%-20s|%-20s|%-20s|%-20s%-20s\n",
		// AO0TO1.value1,AO025TO1.value1,AO05TO1.value1,AO075TO1.value1,AO1TO1.value1,"|");
		// System.out.printf("%8.2f    |%-20s|%-20s|%-20s|%-20s|%-20s%-20s\n",0.10,
		// AO0TO1.value2,AO025TO1.value2,AO05TO1.value2,AO075TO1.value2,AO1TO1.value2,"|");
		// System.out.printf("            |%-20s|%-20s|%-20s|%-20s|%-20s%-20s\n",
		// AO0TO1.value3,AO025TO1.value3,AO05TO1.value3,AO075TO1.value3,AO1TO1.value3,"|");
		// System.out.printf("            |%-20s|%-20s|%-20s|%-20s|%-20s%-20s\n",
		// AO0TO1.value4,AO025TO1.value4,AO05TO1.value4,AO075TO1.value4,AO1TO1.value4,"|");
		//
		// System.out.printf("            +%-20s+%-20s+%-20s+%-20s+%-20s+\n",
		// "--------------------","--------------------","--------------------","--------------------","--------------------");
		// System.out.printf("            |%-20s|%-20s|%-20s|%-20s|%-20s%-20s\n",
		// AO0TO075.value1,AO025TO075.value1,AO05TO075.value1,AO075TO075.value1,AO1TO075.value1,"|");
		// System.out.printf("%8.2f    |%-20s|%-20s|%-20s|%-20s|%-20s%-20s\n",0.05,
		// AO0TO075.value2,AO025TO075.value2,AO05TO075.value2,AO075TO075.value2,AO1TO075.value2,"|");
		// System.out.printf("            |%-20s|%-20s|%-20s|%-20s|%-20s%-20s\n",
		// AO0TO075.value3,AO025TO075.value3,AO05TO075.value3,AO075TO075.value3,AO1TO075.value3,"|");
		// System.out.printf("            |%-20s|%-20s|%-20s|%-20s|%-20s%-20s\n",
		// AO0TO075.value4,AO025TO075.value4,AO05TO075.value4,AO075TO075.value4,AO1TO075.value4,"|");
		//
		//
		// System.out.printf("TO          +%-20s+%-20s+%-20s+%-20s+%-20s+\n",
		// "--------------------","--------------------","--------------------","--------------------","--------------------");
		// System.out.printf("            |%-20s|%-20s|%-20s|%-20s|%-20s%-20s\n",AO0TO05.value1,AO025TO05.value1,AO05TO05.value1,AO075TO05.value1,AO1TO05.value1,"|");
		// System.out.printf("%8.2f    |%-20s|%-20s|%-20s|%-20s|%-20s%-20s\n",0.02,AO0TO05.value2,AO025TO05.value2,AO05TO05.value2,AO075TO05.value2,AO1TO05.value2,"|");
		// System.out.printf("            |%-20s|%-20s|%-20s|%-20s|%-20s%-20s\n",AO0TO05.value3,AO025TO05.value3,AO05TO05.value3,AO075TO05.value3,AO1TO05.value3,"|");
		// System.out.printf("            |%-20s|%-20s|%-20s|%-20s|%-20s%-20s\n",AO0TO05.value4,AO025TO05.value4,AO05TO05.value4,AO075TO05.value4,AO1TO05.value4,"|");
		//
		//
		// System.out.printf("            +%-20s+%-20s+%-20s+%-20s+%-20s+\n",
		// "--------------------","--------------------","--------------------","--------------------","--------------------");
		// System.out.printf("            |%-20s|%-20s|%-20s|%-20s|%-20s%-20s\n",
		// AO0TO025.value1,AO025TO025.value1,AO05TO025.value1,AO075TO025.value1,AO1TO025.value1,"|");
		// System.out.printf("%8.2f    |%-20s|%-20s|%-20s|%-20s|%-20s%-20s\n",0.01,AO0TO025.value2,AO025TO025.value2,AO05TO025.value2,AO075TO025.value2,AO1TO025.value2,"|");
		// System.out.printf("            |%-20s|%-20s|%-20s|%-20s|%-20s%-20s\n",
		// AO0TO025.value3,AO025TO025.value3,AO05TO025.value3,AO075TO025.value3,AO1TO025.value3,"|");
		// System.out.printf("            |%-20s|%-20s|%-20s|%-20s|%-20s%-20s\n",
		// AO0TO025.value4,AO025TO025.value4,AO05TO025.value4,AO075TO025.value4,AO1TO025.value4,"|");
		//
		// // System.out.printf("+%-20s+%-20s+%-20s+%-20s+%-20s+\n",
		// "--------------------","--------------------","--------------------","--------------------","--------------------");
		//
		// System.out.printf("            +%-20s+%-20s+%-20s+%-20s+%-20s+\n",
		// "--------------------","--------------------","--------------------","--------------------","--------------------");
		// System.out.printf("            |%-20s|%-20s|%-20s|%-20s|%-20s%-20s\n",AO0TO0.value1,AO025TO0.value1,AO05TO0.value1,AO075TO0.value1,AO1TO0.value1,"|");
		// System.out.printf("%8d    |%-20s|%-20s|%-20s|%-20s|%-20s%-20s\n",0,
		// AO0TO0.value2,AO025TO0.value2,AO05TO0.value2,AO075TO0.value2,AO1TO0.value2,"|");
		// System.out.printf("            |%-20s|%-20s|%-20s|%-20s|%-20s%-20s\n",
		// AO0TO0.value3,AO025TO0.value3,AO05TO0.value3,AO075TO0.value3,AO1TO0.value3,"|");
		// System.out.printf("            |%-20s|%-20s|%-20s|%-20s|%-20s%-20s\n",
		// AO0TO0.value4,AO025TO0.value4,AO05TO0.value4,AO075TO0.value4,AO1TO0.value4,"|");
		//
		// System.out.printf("            +%-20s+%-20s+%-20s+%-20s+%-20s+\n",
		// "--------------------","--------------------","--------------------","--------------------","--------------------");
		// System.out.printf("%24d %21.2f %19.2f %19.2f %20.2f \n",
		// 0,0.01,0.02,0.05,0.1);
		// System.out.printf("%60s\n", "AO");
		// System.out.printf("%65s\n",graphTitle);
		// System.out.printf("%65s\n",graphNotes);
		//
		// }

	
}
