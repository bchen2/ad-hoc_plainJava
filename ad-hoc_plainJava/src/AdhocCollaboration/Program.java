package AdhocCollaboration;

import java.io.IOException;
import java.util.HashMap;

import AdhocMakeTable.CreateTables;
import AgentData_separateFile.NormalizePerTick_ShortAgentOutput;
import cern.colt.Arrays;
import cern.jet.random.Uniform;

public class Program {

	
	/**
	 * When running the jar file do 
	 * "java -jar 
	 * @param args
	 * @throws IOException 
	 */
	
	
	public static void main(String[] args) throws IOException {
		
		
		Parameters params = Parameters.getInstance();
		
		if (args.length>0){
			//args[0] contains the propertyfile name
			System.out.println("args[0] = "+args[0]);
			Parameters.createFromFile(args[0],Parameters.getInstance());
			System.out.println("Reading Properties file success.");
		}else{
			System.out.println("Using DEFAULT parameters!");
		}
		
		
		
		Program program = new Program();
		
		double[] agentOpenness=params.agentOpenness;
		double[] taskOpenness=params.taskOpenness;
		int[] option=params.option;
		int[] randomSeed=params.randomSeed;
		String directory= params.direcotry;
		
		int run=0; int count=0; int n=agentOpenness.length*taskOpenness.length*option.length;
		int runTotal=randomSeed.length;
		
		/*
		 * Simulation Run
		 */
		System.out.println("AO = "+Arrays.toString(agentOpenness));
		System.out.println("TO = "+Arrays.toString(taskOpenness));
		System.out.println("Run = "+randomSeed.length);
		System.out.println("TotalTick = "+params.totalTick);
		System.out.println("AgentCount = "+params.agentCount);
		System.out.println("OutputDir = "+directory);
		System.out.println("ConfigFile = "+params.configFile);
		
		long startTime = System.nanoTime();
		
		for (int seed: randomSeed){
			run++; count=0;
			for (double AO: agentOpenness){
				for (double TO: taskOpenness){
					for (int op: option){
						count++;
						
						program.runConfiguration(params,TO, AO, op, seed);
						System.out.println("AO="+AO+" TO="+TO+" Option="+op+ " Run="+run+"/"+runTotal+String.format(" (%.2f%%)", ((double)run/runTotal)*100)
						+" Progress="+count+"/"+n+String.format(" (%.2f%%)",((double)count/n)*100));
						
						PrintDuriation(startTime);
						
					}
				}
			}
		}
		

		
	/*
	 * Calculate statistics from the output files	
	 */
		NormalizePerTick_ShortAgentOutput  norm = new NormalizePerTick_ShortAgentOutput();
		try {
			norm.NormalizePerTick(directory);
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		
		
		PrintDuriation( startTime);
		
		
		/*
		 * put result into tables 
		 */
		
		CreateTables.plotTables(directory,params);
		
		PrintDuriation( startTime);
		
	}
	
	
	
	public static void PrintDuriation(long startTime){
		
		long endTime = System.nanoTime();
		long duration = (endTime - startTime);  //divide by 1000000 to get milliseconds.
		long nanoTime=duration/1000000;
		long second=nanoTime/1000;
		long miniute=second/60;
		long hour=miniute/60;
		System.out.println("TOTAL PROGRAM Duriation = "+duration/1000000+ "ms or "+second + " seconds or " +miniute+" mins or "+hour+" hours");	
		
		
	}
	
	
	
	public void runConfiguration(Parameters params,double TO,double AO,int opt,int randSeed){
		
		int initalCapNummber=params.initalCapNumber;/*Initial Number of non zero capabilities*/
		int agentCount 			= params.agentCount;
		double taskOpenness 	= TO;
		int totalTick           =params.totalTick;
		double agentOpenness 	= AO; 
		int option 			= opt;
		int randomSeed =randSeed; 
		
		/**
		 * this determines the percentage of agents using each type of task selection strategies, 
		 * option14-option15-option16-option17
		 * 30-20-30-20 means 30% of agents use option14, 20% use option15,30% use option16 and 20%use option17
		 * 50-0-0-50 means 50% of agents use option14, 50% use option17*/
		String optionTypeDistrubution=params.optionTypeDistubution;
		
		
		/**
		 * Disable agentTypes function by entering an 0, this way agent will be randomly generated, we don't care about agent types. After kill, we also randomly entroduce an agent 
		 */
		int UsingAgentDistrubution=params.UsingAgentDistrubution;
		/**
		 * Disable task Types function by entering an 0, this way all tasks are randomly generated, they are no task types, we use"config_typesRandom.properties" to draw all the tasks from
		 */
		int UsingTaskDistrubution=params.UsingTaskDistrubution;
		
		int agentType 			= 0; /* 1 - expert, 2 - average ,3-novice*/
		double agentQualityMax 	= 1;
		
		/*user enter a string of two numbers, not separated by comma. Example: 3040  .this means 30% of Expert, 40% of Average, and 100-(30+40) % of Novice*/
		String AgentDistrubution =   params.AgentDistrubution;
		
		/*user enter a string of two numbers, not separated by comma. Example: 3333  .this means 33% of HardTask, 33% of AverageTask, and 100-(33+33) % of EasyTask*/
		String TaskDistrubution =  params.TaskDistrubution;
		
		String HardPercentageStr, AveragePercentageStr;
		HardPercentageStr="HP"+TaskDistrubution.substring(0,2);
		AveragePercentageStr="AP"+TaskDistrubution.substring(2,4);
		
		/*
		 * creating a new blackboard
		 */
		Blackboard b 			= new Blackboard();	
		b.setOption(option);
		b.setOptionTypeDistrubution(optionTypeDistrubution);
		b.setAgentCount(agentCount);
		b.setNumUnassignedAgents(agentCount);
		b.setAgentOpenness(agentOpenness);
		b.setTaskOpenness(taskOpenness);
		b.setUsingAgentDistrubution(UsingAgentDistrubution);
		b.setUsingTaskDistrubution(UsingTaskDistrubution);
		b.setNumAgentsIntroduced(agentCount);
//		context.add(b);
		
		print("Task openness "+taskOpenness);
		// pass parameters to the main agent
		MainAgent mainAgent = null;
		try {
			mainAgent = new MainAgent(b,taskOpenness, totalTick,agentCount, agentType, agentOpenness,initalCapNummber,HardPercentageStr,AveragePercentageStr,randomSeed);
		} catch (IOException e) {
			e.printStackTrace();
		}
		mainAgent.setTaskDistrubution(TaskDistrubution);//pass taskDistribution to the mainAgent, later the mainAgent will choose the congfig file based on the distrubution to pick task orders
//		mainAgent.setTicksToFinish(ticksToFinish);//pass tickTOFinish to mainAgent, so it can allow the last auctioned off task to have extra tick to get finished.
//		mainAgent.setContext(context);
		mainAgent.setUsingAgentDistrubution(UsingAgentDistrubution);
		mainAgent.setUsingTaskDistrubution(UsingTaskDistrubution);
//		Random random = new Random( randomSeed);
		Uniform uniform= new Uniform(0.0,1.0, randomSeed); //the RNG for the environment 
		mainAgent.setUniform(uniform);
//		context.add(mainAgent);
		MainAgent.randomSeed= randomSeed;
		
		b.mainAgent=mainAgent;
		
		/*
		 * create agents
		 */
		if (UsingAgentDistrubution==1){//using agent Types and create agents according to the agent distribution
			/*
			 * start the agents, create agent types depending on agentTypeScenario
			 */
			int expertCount=0;int averageCount=0; int noviceCount=0;
			//String[] token=distrubution.split(",");
			
			String expertCountStr, averageCountStr;
			expertCountStr = AgentDistrubution.substring(0,2);
			averageCountStr = AgentDistrubution.substring(2,4);
			
			expertCount=(int) Math.round((Integer.parseInt(expertCountStr)*agentCount/100.0)) ;
			averageCount=(int) Math.round((Integer.parseInt(averageCountStr)*agentCount/100.0));
			noviceCount=agentCount-expertCount-averageCount;
			
			for(int i=0; i<agentCount; i++) {
				if(i < expertCount){
					agentType= 1; // Expert
				}
				else if (i <expertCount + averageCount){
	;				agentType = 2; // Average
				}
				else{
					agentType =3; // Novice
				}
		
				Agent newAgent=new Agent(i,b,agentType,agentQualityMax,initalCapNummber);
				newAgent.setOption(opt);
//				context.add(newAgent);
				/* adding this agent to bb's agentList*/
				b.addAgent(newAgent);
				/*adding this agent to bb's AgentMap*/
				b.AddToAgentMap(i, newAgent);	
			}

		}else{//do not use agent type, we randomly generate agents 
			
		
			
			
			if (option==99){
				int op14count=0;
				int op15count=0;
				int op16count=0;
				int op17count=0;
				HashMap<String,Integer> optionCountMap = new HashMap<String,Integer>();
				
//				optionTypeDistrubution=(String) params.getValue("optionTypeDistrubution");
				
				if (optionTypeDistrubution!=null && optionTypeDistrubution!=""){
					String token[]=optionTypeDistrubution.split("-");
			
					if (Double.parseDouble(token[0])!=0){
						 op14count=(int) ((Double.parseDouble(token[0])/100)*agentCount);
						
					}else{
						op14count=0;
					}
					 optionCountMap.put("14", op14count);
					 
					if (Double.parseDouble(token[1])!=0){
						op15count=(int) ((Double.parseDouble(token[1])/100)*agentCount);
					}else{
						op15count=0;
					}
					 optionCountMap.put("15", op15count);
					 
					if (Double.parseDouble(token[2])!=0){
						op16count=(int) ((Double.parseDouble(token[2])/100)*agentCount);
					}else{
						op16count=0;
					}
					 optionCountMap.put("16", op16count);
					 
					if (Double.parseDouble(token[3])!=0){
						op17count=(int) ((Double.parseDouble(token[3])/100)*agentCount);
					}else{
						op17count=0;
					}
					 optionCountMap.put("17", op17count);
					
				}
				
				print("op14Count:"+op14count);
				print("op15Count:"+op15count);
				print("op16Count:"+op16count);
				print("op17Count:"+op17count);
				print(optionTypeDistrubution);
				print(optionCountMap.toString());
				int OpCount;
				int id=0;
				for (String op : optionCountMap.keySet()){
					OpCount=optionCountMap.get(op);
					for(int i=0; i<OpCount; i++) {
						id++;
						Agent newAgent=new Agent(id,b,agentType,agentQualityMax,initalCapNummber);
						newAgent.setOptionType(Integer.parseInt(op));
						newAgent.setOption(opt);
//						context.add(newAgent);
						/* adding this agent to bb's agentList*/
						b.addAgent(newAgent);
						/*adding this agent to bb's AgentMap*/
						b.AddToAgentMap(i, newAgent);	
						
						newAgent.outputAgentCapToFile();
					}
				
				}
				
				
				
				
				
			}else{
				
				for(int i=0; i<agentCount; i++) {
					
					Agent newAgent=new Agent(i,b,agentType,agentQualityMax,initalCapNummber);
//					context.add(newAgent);
					newAgent.setOption(opt);
					/* adding this agent to bb's agentList*/
					b.addAgent(newAgent);
					/*adding this agent to bb's AgentMap*/
					b.AddToAgentMap(i, newAgent);	
					
					newAgent.outputAgentCapToFile();
				}
				
				
			}
			
			
			
		}
		
		
		
		
		
		try {
			mainAgent.step();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		

	
	}
	
	


/** debug method */
@SuppressWarnings("unused")
private void print(String s){
	if (PrintClass.DebugMode && PrintClass.printClass){
		System.out.println(this.getClass().getSimpleName()+"::"+s);
	}else if(PrintClass.DebugMode){
		System.out.println(s);
	}
}

}
