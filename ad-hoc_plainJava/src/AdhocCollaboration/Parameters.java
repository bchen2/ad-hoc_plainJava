package AdhocCollaboration;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Properties;

public class Parameters {

	public int initalCapNumber;
	public int totalTick;
	public int[] randomSeed;
	public int agentCount;
	public double[] taskOpenness;
	public double[] agentOpenness;

	/**
	 * agent task selection options
	 */
	public int[] option;

	/**
	 * the time tick required to finish an task
	 */
	public int tickToFinish;

	/**
	 * this determines the percentage of agents using each type of task
	 * selection strategies, option14-option15-option16-option17 30-20-30-20
	 * means 30% of agents use option14, 20% use option15,30% use option16 and
	 * 20%use option17 50-0-0-50 means 50% of agents use option14, 50% use
	 * option17
	 */
	public String optionTypeDistubution;

	/**
	 * Disable agentTypes function by entering an 0, this way agent will be
	 * randomly generated, we don't care about agent types. After kill, we also
	 * randomly entroduce an agent
	 */
	public int UsingAgentDistrubution;
	/**
	 * Disable task Types function by entering an 0, this way all tasks are
	 * randomly generated, they are no task types, we
	 * use"config_typesRandom.properties" to draw all the tasks from
	 */
	public int UsingTaskDistrubution;

	/**
	 * user enter a string of two numbers, not separated by comma. Example: 3040
	 * .this means 30% of Expert, 40% of Average, and 100-(30+40) % of Novice
	 */
	public String AgentDistrubution;

	/**
	 * user enter a string of two numbers, not separated by comma. Example: 3333
	 * .this means 33% of HardTask, 33% of AverageTask, and 100-(33+33) % of
	 * EasyTask
	 */
	public String TaskDistrubution;

	/**
	 * the output directory
	 */
	public String direcotry;
	
	/**
	 * the configfile to use
	 */
	public String configFile;
	
	public static Parameters instance;;

	public Parameters() {

		initalCapNumber = 20;
		totalTick = 100;
		randomSeed = new int[] { 318856981 };

		/* 30 seeds */
//		 randomSeed = new int[]{ 318856981, 318906411,
//		 318949337, 318994768, 319041788, 319097042, 319158544, 319210195,
//		 319253832, 319302136, 319344747, 319393883, 319433666, 319483658,
//		 319520815, 319571219, 319609484, 319659640, 319695607, 319747153,
//		 319785662, 319846666, 319881572, 319934879, 319967846, 320021327,
//		 320051734, 320104619, 320135073, 320187406 };

		/* 100 seeds */
		// randomSeed = new int[]{ 318856981,
		// 318906411, 318949337, 318994768, 319041788, 319097042, 319158544,
		// 319210195, 319253832, 319302136, 319344747, 319393883, 319433666,
		// 319483658, 319520815, 319571219, 319609484, 319659640, 319695607,
		// 319747153, 319785662, 319846666, 319881572, 319934879, 319967846,
		// 320021327, 320051734, 320104619, 320135073, 320187406, 328838372,
		// 328891867, 328936918, 328981715, 329016415, 329064667, 329095468,
		// 329151095, 329179609, 329238439, 329263522, 329325363, 329345568,
		// 329411877, 329428130, 329498808, 329511398, 329585117, 329594050,
		// 329672405, 329677545, 329759396, 329760651, 329838358, 329840922,
		// 329907441, 329913072, 329976699, 329984664, 330061609, 331385246,
		// 331434812, 331475602, 331518521, 331555327, 331598762, 331634234,
		// 331678663, 331715405, 331768256, 331797577, 331847013, 331874811,
		// 331925570, 331951024, 332002836, 332027635, 332079897, 332102941,
		// 332157211, 332178828, 332234702, 332255201, 332313853, 332330087,
		// 332391475, 332406055, 332469021, 332482343, 332546018, 332555774,
		// 332619488, 332627990, 332695890, 332701993, 332771186, 332774919,
		// 332845305, 332846830, 332918070 };

		agentCount = 100;
		taskOpenness = new double[] { 0, 0.01, 0.02, 0.05, 0.1, 0.2, 0.5 };
		agentOpenness = new double[] { 0, 0.01, 0.02, 0.05, 0.1, 0.2, 0.5 };
		option = new int[] { 14, 15, 16, 17 };
		tickToFinish = 1;
		optionTypeDistubution = "25-25-25-25";
		UsingAgentDistrubution = 0;
		UsingTaskDistrubution = 0;
		AgentDistrubution = "3040";
		TaskDistrubution = "3333";
//		direcotry = "./20cap1000tick";
		direcotry="/Users/BinChen/Desktop/testing111";
//		configFile="20choose5.properties";
		
				
		configFile="config_typesRandom.properties";

	}


	public static void createFromFile(String fileName, Parameters instance) {
		// modify the instance
		
		Properties prop = new Properties();
	    try {

	    	  File jarPath=new File(Parameters.class.getProtectionDomain().getCodeSource().getLocation().getPath());
	          String propertiesPath=jarPath.getParent();
	          System.out.println(" propertiesPath-"+propertiesPath);
	          if (propertiesPath==null){
	        	  prop.load(new FileInputStream("./Parameters.properties"));
	          }else{
	        	  prop.load(new FileInputStream(propertiesPath+"/Parameters.properties"));
	          }
	         
	       System.out.println("Seeds From File " + prop.getProperty("randomSeed")); 
	       
	       
	       
	       instance.initalCapNumber = Integer.parseInt(prop.getProperty("initalCapNumber").trim());
	       instance.totalTick = Integer.parseInt(prop.getProperty("totalTick"));
	       instance.agentCount = Integer.parseInt(prop.getProperty("agentCount"));
	       instance.tickToFinish = Integer.parseInt(prop.getProperty("tickToFinish"));
	 
	       
	       instance.optionTypeDistubution = prop.getProperty("optionTypeDistubution");
	       instance.AgentDistrubution = prop.getProperty("AgentDistrubution");
	       instance.TaskDistrubution =prop.getProperty("TaskDistrubution");
	       instance.direcotry = prop.getProperty("direcotry");
	       instance.configFile=prop.getProperty("configFile");
	       
	       /**
	        * parse "randomSeed"
	        */
	     
	       instance.randomSeed=parseListInt(prop.getProperty("randomSeed"));
	       

	       /**
	        * parse taskOpenness
	        */
	       String taskOpenness=prop.getProperty("taskOpenness");
	       instance.taskOpenness=parseListDouble(taskOpenness);
	       
	       instance.agentOpenness =parseListDouble(prop.getProperty("agentOpenness"));
	      
	       
	       
	       
	       instance.option = parseListInt(prop.getProperty("option"));
	      
	       
	       instance.UsingAgentDistrubution = Integer.parseInt(prop.getProperty("UsingAgentDistrubution"));
	       instance.UsingTaskDistrubution = Integer.parseInt(prop.getProperty("UsingTaskDistrubution"));

	       
	       
	       
	       

	    } catch (IOException e1) {
	        e1.printStackTrace();
	        /**
	         * if did not find the file, then exit
	         */
	        System.exit(0);
	    }

	}


	
	public static double[] parseListDouble(String taskOpenness){
		 String[] taskOpennessToken=taskOpenness.split(",");
	       double[] taskOpennessList=new double[taskOpennessToken.length] ;
	       for (int i=0;i<taskOpennessToken.length;i++){
	    	   taskOpennessList[i]=Double.parseDouble(taskOpennessToken[i].trim());
	       }
	       return taskOpennessList;
	}
	
	
	
	public static int[] parseListInt(String seeds){
	       String[] SeedsToken=seeds.split(",");
	       int[] seedList=new int[SeedsToken.length] ;
	       for (int i=0;i<SeedsToken.length;i++){
	    	   seedList[i]=Integer.parseInt(SeedsToken[i].trim());
	       }
	       return seedList;
	}
	
	public static Parameters getInstance() {
		if (instance==null){
			System.out.println("New Prameter instance Created!");
			instance=new Parameters();
		}
		return instance;
	}


	public static void setInstance(Parameters instance) {
		Parameters.instance = instance;
	}


}
