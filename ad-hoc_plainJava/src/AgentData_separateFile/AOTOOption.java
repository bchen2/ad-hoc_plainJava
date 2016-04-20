package AgentData_separateFile;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;

import org.apache.commons.math3.stat.descriptive.DescriptiveStatistics;
import org.apache.commons.math3.stat.descriptive.SummaryStatistics;


public class AOTOOption {
	public double AO;
	public double TO;
	public int option;
	public int totalTick;
	public String optionDistrubution;
	public double numAgentStay;
	
	public ArrayList<File> fileList=new ArrayList<File>();
	
	public ArrayList<String> linesList = new ArrayList<String>();
	HashMap<Integer,task> taskMap = new HashMap<Integer,task>();
	HashMap<Integer,Agent> AgentMap = new HashMap<Integer,Agent>();
	
	/**
	 * records the agent for the agents who stayed in the ENTIRE SIMULATION
	 */
	ArrayList<Agent> AgentStayAllTickList = new ArrayList<Agent>();
	
	ArrayList<Agent> AgentOp14 = new ArrayList<Agent>();
	ArrayList<Agent> AgentOp15 = new ArrayList<Agent>();
	ArrayList<Agent> AgentOp16 = new ArrayList<Agent>();
	ArrayList<Agent> AgentOp17 = new ArrayList<Agent>();
	
	public String fileName;
	/*
	 * task average
	 */
	double averageAgentRequired=0;
	double averageAgentRequiredSTD=0;
	double averageAgentAssigned=0;
	double averageAgentAssignedSTD=0;
	double averageTaskReward=0;
	double averageTaskRewardSTD=0;
	double averageAssignedRatio=0;
	double averageAssignedRatioSTD=0;
	int taskTypes=0;
	
	/*
	 * agent average
	 */
	double averageBidsSubmitted=0;
	double averageBidsSubmittedSTD=0; 
//	double averageTaskBidFromSet=0;
	double averageBidsSubmittedAndWon=0; 
	double averageBidsSubmittedAndWonSTD=0; 	
//	double averageSelected=0;
	double averageTaskAssigned=0;	
	double averageTaskAssignedSTD=0;	
//	double averageTaskAssignmentFromSet=0; 
	double averageReward=0;	
	double averageRewardSTD=0;
//	double averageRewardFromTick=0;
	
	double averageTaskTypeBidsFor=0; 
	double averageTaskTypeBidsForSTD=0; 
	
	double averageTaskTypeAssignedFromSet=0; 
	double averageTaskTypeAssignedFromSetSTD=0; 
	
	double averageSubtasksAssigned=0; 
	double averageSubtasksAssignedSTD=0; 
	double averageSelfGain=0; 
	double averageSelfGainSTD=0; 
	double averageObservationGain=0;
	double averageObservationGainSTD=0;
	double averageLearningGain=0;
	double averageLearningGainSTD=0;
	
	double averageBidsWonRatio=0;
	double averageBidsWonRatioSTD=0;
	
	double averageSubtaskOverTaskRatio=0;
	double averageSubtaskOverTaskRatioSTD=0;
	
	
//	DescriptiveStatistics taskAssignedPerTick = new DescriptiveStatistics();
//	
//	DescriptiveStatistics subtasksAssignedPerTask = new DescriptiveStatistics();
//	DescriptiveStatistics subtasksAssignedPerTick = new DescriptiveStatistics();
//	
//	DescriptiveStatistics rewardPerTask = new DescriptiveStatistics();
//	DescriptiveStatistics rewardPerTick = new DescriptiveStatistics();
//	
//	
//	
//	DescriptiveStatistics bidsSubmitted = new DescriptiveStatistics();
//	DescriptiveStatistics bidsSubmittedAndWon = new DescriptiveStatistics();
//	
//	
//	DescriptiveStatistics selfGainPerTask = new DescriptiveStatistics();
//	DescriptiveStatistics selfGainPerTick = new DescriptiveStatistics();
//	DescriptiveStatistics observationGainPerTask = new DescriptiveStatistics();
//	DescriptiveStatistics observationGainPerTick = new DescriptiveStatistics();
//	DescriptiveStatistics LearningGainPerTask = new DescriptiveStatistics();
//	DescriptiveStatistics LearningGainPerTick = new DescriptiveStatistics();
//	
//	
//	ArrayList<DescriptiveStatistics> statsList = new ArrayList<DescriptiveStatistics>();
	
	SummaryStatistics taskAssignedPerTick = new SummaryStatistics();
	
	SummaryStatistics subtasksAssignedPerTask = new SummaryStatistics();
	SummaryStatistics subtasksAssignedPerTick = new SummaryStatistics();
	
	SummaryStatistics rewardPerTask = new SummaryStatistics();
	SummaryStatistics rewardPerTick = new SummaryStatistics();
	
	
	
	SummaryStatistics bidsSubmitted = new SummaryStatistics();
	SummaryStatistics bidsSubmittedAndWon = new SummaryStatistics();
	SummaryStatistics bidsWonOverBidsSubmittedRatio = new SummaryStatistics();
	
	
	SummaryStatistics selfGainPerTask = new SummaryStatistics();
	SummaryStatistics selfGainPerTick = new SummaryStatistics();
	SummaryStatistics observationGainPerTask = new SummaryStatistics();
	SummaryStatistics observationGainPerTick = new SummaryStatistics();
	SummaryStatistics LearningGainPerTask = new SummaryStatistics();
	SummaryStatistics LearningGainPerTick = new SummaryStatistics();
	
	
	
	
	
	
	ArrayList<SummaryStatistics> statsList = new ArrayList<SummaryStatistics>();
	
	
	/*********
	 * following are for calculate task summary
	 */
//	SummaryStatistics numAssigend = new SummaryStatistics();
//	SummaryStatistics numRequired = new SummaryStatistics();
//	SummaryStatistics reward= new SummaryStatistics();
//	SummaryStatistics taskType = new SummaryStatistics();
//	SummaryStatistics observedCapUsedCount = new SummaryStatistics();
	
	
	SummaryStatistics numUniqueTask = new SummaryStatistics();
	SummaryStatistics entropy = new SummaryStatistics();
	SummaryStatistics entropyPercentage = new SummaryStatistics();
	
	
	ArrayList<SummaryStatistics> taskStatsList = new ArrayList<SummaryStatistics>();
	ArrayList<task> taskList = new ArrayList<task>();
	
	
	/**
	 * for collabarators
	 * @param aO
	 * @param tO
	 * @param option
	 */
	SummaryStatistics collbaratorStat = new SummaryStatistics();
	
	
	
	
	HashMap<Integer,TickStats> tickMap = new HashMap<Integer,TickStats>();
	
	
	public AOTOOption(double aO, double tO, int option) {
		super();
		AO = aO;
		TO = tO;
		this.option = option;
		statsList.add(taskAssignedPerTick);statsList.add(subtasksAssignedPerTask);
		statsList.add(subtasksAssignedPerTick);statsList.add(rewardPerTask);statsList.add(rewardPerTick);
		statsList.add(bidsSubmitted);statsList.add(bidsSubmittedAndWon);
		statsList.add(selfGainPerTask);statsList.add(selfGainPerTick);
		statsList.add(observationGainPerTask);statsList.add(observationGainPerTick);
		statsList.add(LearningGainPerTask);statsList.add(LearningGainPerTick);
		statsList.add( bidsWonOverBidsSubmittedRatio);
		
//		taskStatsList.add(numAssigend);taskStatsList.add(numRequired);
//		taskStatsList.add(reward);taskStatsList.add(taskType);taskStatsList.add(observedCapUsedCount);
		}
	
	
	public AOTOOption(double aO, double tO, int option,boolean type) {
		super();
		AO = aO;
		TO = tO;
		this.option = option;
		statsList.add(taskAssignedPerTick);statsList.add(bidsSubmittedAndWon);
		statsList.add(rewardPerTask);statsList.add(rewardPerTick);
		statsList.add(LearningGainPerTask);statsList.add(LearningGainPerTick);
		statsList.add(selfGainPerTask);statsList.add(selfGainPerTick);
		statsList.add(observationGainPerTask);statsList.add(observationGainPerTick);
	
		
		}
	
	
	public AOTOOption(double aO, double tO, int option, int x, int y) {
		super();
		AO = aO;
		TO = tO;
		this.option = option;
//		statsList.add(taskAssignedPerTick);statsList.add(bidsSubmittedAndWon);
//		statsList.add(rewardPerTask);statsList.add(rewardPerTick);
//		statsList.add(LearningGainPerTask);statsList.add(LearningGainPerTick);
//		statsList.add(selfGainPerTask);statsList.add(selfGainPerTick);
//		statsList.add(observationGainPerTask);statsList.add(observationGainPerTick);
		this.taskStatsList.add(numUniqueTask);
		this.taskStatsList.add(entropy);
		this.taskStatsList.add(entropyPercentage);
		
		}
	
	
	
	public AOTOOption(double aO, double tO, int option,int TotalTick) {
		super();
		AO = aO;
		TO = tO;
		this.option = option;
		this.totalTick=TotalTick;
		this.numAgentStay=0;
		for (int i=1; i<=TotalTick;i++){
			TickStats tickStats = new TickStats();
			tickStats.tick=i;
			tickMap.put(i, tickStats);
		}
		
		}
	
	public AOTOOption(double aO, double tO, int option,int TotalTick,String Distrubution) {
		super();
		AO = aO;
		TO = tO;
		this.option = option;
		this.optionDistrubution=Distrubution;
		this.totalTick=TotalTick;
		this.numAgentStay=0;
		for (int i=1; i<=TotalTick;i++){
			TickStats tickStats = new TickStats();
			tickStats.tick=i;
			tickMap.put(i, tickStats);
		}
		
		}
	
	
	public AOTOOption(double aO, double tO, int option,String Distrubution) {
		super();
		AO = aO;
		TO = tO;
		this.option = option;
		this.optionDistrubution=Distrubution;
		statsList.add(taskAssignedPerTick);statsList.add(subtasksAssignedPerTask);
		statsList.add(subtasksAssignedPerTick);statsList.add(rewardPerTask);statsList.add(rewardPerTick);
		statsList.add(bidsSubmitted);statsList.add(bidsSubmittedAndWon);
		statsList.add(selfGainPerTask);statsList.add(selfGainPerTick);
		statsList.add(observationGainPerTask);statsList.add(observationGainPerTick);
		statsList.add(LearningGainPerTask);statsList.add(LearningGainPerTick);
		statsList.add( bidsWonOverBidsSubmittedRatio);
		
//		taskStatsList.add(numAssigend);taskStatsList.add(numRequired);
//		taskStatsList.add(reward);taskStatsList.add(taskType);taskStatsList.add(observedCapUsedCount);
		}
		
		
	
	public void calculateCollabrator(){
		
		//find each agents total collabrators
		for (Agent a : this.AgentMap.values()){
			for (Integer taskId:a.taskIdSet){
				a.collabratorSet.addAll(this.taskMap.get(taskId).agentsAssigned);
			}
		}
		
		for (Agent a : this.AgentMap.values()){
			
			double value=0;
//			if (a.taskIdSet.size()!=0){
//				value=(double)a.collabratorSet.size()/a.taskIdSet.size();
//				this.collbaratorStat.addValue(value);
//			}
			
			if (a.collabratorSet.size()!=0){
				value=a.collabratorSet.size()-1;
			}else{
				value=0;
			}
			
			this.collbaratorStat.addValue(value);
			
			
		}
		
		this.AgentMap.clear();this.taskMap.clear();
		
		
		
	}
	
	
	
	
	
	
	
	public void addTasksToTaskStats(){
		HashSet<Integer> taskTypeSet = new HashSet<Integer>();
		
		for (task t: this.taskMap.values()){
//			this.numAssigend.addValue(t.agentsAssigned.size());
//			this.numRequired.addValue(t.agentRequired);
//			this.reward.addValue(t.reward);
//			taskTypeSet.add(t.type);
//			this.observedCapUsedCount.addValue(t.observedCapUsedCount);
		}
//		this.taskType.addValue(taskTypeSet.size());
		this.taskMap.clear();
	}
	
//	public double calculateSME(DescriptiveStatistics des){
//		double y=Math.sqrt(des.getN());
//		double x=des.getStandardDeviation();
//		return x/y;
//		
//	}
	
	public double calculateSME1(SummaryStatistics summaryStatistics){
		double y=Math.sqrt(summaryStatistics.getN());
		double x=summaryStatistics.getStandardDeviation();
		return x/y;
		
	}
	
//	public void clearnStats(){
//		
//		for (DescriptiveStatistics stats : statsList){
//			stats.clear();
//			stats=new DescriptiveStatistics();
//		}
//		statsList.clear();
//	}
	
	public static String getCollabratorTitle(){
		return "AO,TO,Option,collabratorPerTask";
	}
	
	public static String getNormalizedTitle(){
		
		String title=String.format("AO,TO,Option,numTaskAssignedPerTick,numSubtaskAssignedPerTask,numSubtaskAssignedPerTick,rewardPerTick,"
				+ "rewardPerTick,numBidsSubmittedPerTick,numBidsSubmittedAndWonPerTick,selfGainPerTask,selfGainPerTick,"
				+ "observationGainPerTask,observationGainPerTick,learningGainPerTask,learningGainPerTick,bidsWonOverBidsSubmittedRatio,"
				+ "subtasksOverTaskPerTickRatio");
		return title;
				
				
	}
	

public static String getNormalizedTitleShort(){

		String title=String.format("AO,TO,Option,numTaskAssignedPerTick,bidsWonPerTick,rewardPerTask,rewardPerTick,"
				+ "learningGainPerTask,learningGainPerTick,selfGainPerTask,selfGainPerTick,"
				+ "observationGainPerTask,observationGainPerTick,");
		return title;
				
				
	}

public static String getNormalizedTitleTask(){

	String title=String.format("AO,TO,Option,numUniqueTask,entropy,entropyPercentage");
	return title;
			
			
}
	
	
//	public String getNormalizedValue(){
//		
//		
//		
//				StringBuilder sb = new StringBuilder();
//				
//		sb.append(this.AO+","+this.TO+","+this.option+",");
//		
//		for (int i=0;i<this.statsList.size();i++){
//			DescriptiveStatistics target=this.statsList.get(i);
//			sb.append(String.format("%f (%f),", target.getMean(),this.calculateSME(this.statsList.get(i))));
//		}
//		
//		
//		double bidsWonOverBidsSubmitted=this.bidsSubmittedAndWon.getMean()/this.bidsSubmitted.getMean();
//		double subtaskOverTask=this.subtasksAssignedPerTick.getMean()/this.taskAssignedPerTick.getMean();
//		sb.append(bidsWonOverBidsSubmitted+","+subtaskOverTask);
//		
//		return sb.toString();
//		
//	}
	
	public String getCollabratorValue(){
		StringBuilder sb = new StringBuilder();
		
		sb.append(this.AO+","+this.TO+","+this.option+",");
		double mean= this.collbaratorStat.getMean();
		double stardardError=this.calculateSME1(this.collbaratorStat);
		
		sb.append(mean+" ("+stardardError+")");
		
		return sb.toString();
	}
	
	public String getNormalizedValue1(){
		
		
		
		StringBuilder sb = new StringBuilder();
		
sb.append(this.AO+","+this.TO+","+this.option+",");

for (int i=0;i<this.statsList.size();i++){
	SummaryStatistics target=this.statsList.get(i);
	sb.append(String.format("%f (%f),", target.getMean(),this.calculateSME1(this.statsList.get(i))));
}


//double bidsWonOverBidsSubmitted=this.bidsSubmittedAndWon.getMean()/this.bidsSubmitted.getMean();
double subtaskOverTask=this.subtasksAssignedPerTick.getMean()/this.taskAssignedPerTick.getMean();
sb.append(subtaskOverTask);

return sb.toString();

}
	
	
	public String getNormalizedValueShort(){
		
		
		
		StringBuilder sb = new StringBuilder();
		
sb.append(this.AO+","+this.TO+","+this.option+",");

for (int i=0;i<this.statsList.size();i++){
	SummaryStatistics target=this.statsList.get(i);
	sb.append(String.format("%f (%f),", target.getMean(),this.calculateSME1(this.statsList.get(i))));
}



return sb.toString();

}
	
	
	public String getNormalizedValueTask(){
		
		
		
		StringBuilder sb = new StringBuilder();
		
sb.append(this.AO+","+this.TO+","+this.option+",");

for (int i=0;i<this.taskStatsList.size()-1;i++){
	SummaryStatistics target=this.taskStatsList.get(i);
	sb.append(String.format("%f (%f),", target.getMean(),this.calculateSME1(this.taskStatsList.get(i))));
}

SummaryStatistics target=this.taskStatsList.get(this.taskStatsList.size()-1);
sb.append(String.format("%f (%f)", target.getMean(),this.calculateSME1(this.taskStatsList.get(this.taskStatsList.size()-1))));



return sb.toString();

}
	
	
	
public static String getTaskTitle(){
		
		String title=String.format("AO,TO,Option,numAssigned,numRequird,reward,taskTypes,observedCapUsedCount");
		return title;
				
				
	}	
	
	
public String getTaskInfo(){
		StringBuilder sb = new StringBuilder();
		
sb.append(this.AO+","+this.TO+","+this.option+",");

for (int i=0;i<this.taskStatsList.size()-1;i++){
	SummaryStatistics target=this.taskStatsList.get(i);
	sb.append(String.format("%f (%f),", target.getMean(),this.calculateSME1(target)));
}

SummaryStatistics target=this.taskStatsList.get(this.taskStatsList.size()-1);
sb.append(String.format("%f (%f)", target.getMean(),this.calculateSME1(target)));

return sb.toString();
}
	
	
	public String getLine(){
		DescriptiveStatistics NumFinishedTasks = new DescriptiveStatistics();
		DescriptiveStatistics LearnedCap = new DescriptiveStatistics();
		DescriptiveStatistics TotalReward = new DescriptiveStatistics();
		DescriptiveStatistics Ratio = new DescriptiveStatistics();
		
		ArrayList<DescriptiveStatistics> stats = new ArrayList<DescriptiveStatistics>();
		stats.add(NumFinishedTasks);stats.add(LearnedCap);stats.add(TotalReward);stats.add(Ratio);
		
		String tick = null;
		for (String line :this.linesList){
			String data[]=line.split(",");
			double NumTasks=Double.parseDouble(data[15]);
			double learnedCap=Double.parseDouble(data[1]);
			double totalReward=Double.parseDouble(data[2]);
			double ratio=Double.parseDouble(data[18]);
			NumFinishedTasks.addValue(NumTasks);
			LearnedCap.addValue(learnedCap);
			TotalReward.addValue(totalReward);
			Ratio.addValue(ratio);
			tick=data[0];
		}
		
		StringBuilder sb = new StringBuilder();
		sb.append(this.TO+","+tick+","+this.AO+","+this.option+",");
		
		for (int i=0;i<stats.size()-1;i++){
			DescriptiveStatistics target=stats.get(i);
			sb.append(String.format("%-4.4f (%-4.4f),", target.getMean(),target.getStandardDeviation()));
		}
		
		sb.append(String.format("%-4.4f (%-4.4f)\n", stats.get(stats.size()-1).getMean(),stats.get(stats.size()-1).getStandardDeviation()));
		
		return sb.toString();
	}
	
	
	

	public String getAgentLine(){
		
		DescriptiveStatistics taskAssigned = new DescriptiveStatistics();
		DescriptiveStatistics reward = new DescriptiveStatistics();
		DescriptiveStatistics bidsSubmitted = new DescriptiveStatistics();
		DescriptiveStatistics bidsSubmittedAndWon = new DescriptiveStatistics();
		DescriptiveStatistics bidsWonRatio= new DescriptiveStatistics();
		
		DescriptiveStatistics subtasksAssigned = new DescriptiveStatistics();
		DescriptiveStatistics selfGain = new DescriptiveStatistics();
		DescriptiveStatistics observationGain = new DescriptiveStatistics();
		DescriptiveStatistics LearningGain = new DescriptiveStatistics();
		DescriptiveStatistics taskTypeAssignedFromSet= new DescriptiveStatistics();
		DescriptiveStatistics TaskTypeBidsFor= new DescriptiveStatistics();
		
		DescriptiveStatistics SubtaskOverTaskRatio= new DescriptiveStatistics();
		
		ArrayList<DescriptiveStatistics> stats = new ArrayList<DescriptiveStatistics>();
		stats.add(taskAssigned);
		stats.add(reward);
		stats.add(bidsSubmitted);
		stats.add(bidsSubmittedAndWon);
		stats.add(bidsWonRatio);
		stats.add(subtasksAssigned);
		stats.add(selfGain);
		stats.add(observationGain);
		stats.add(LearningGain);
		stats.add(taskTypeAssignedFromSet);
		stats.add(TaskTypeBidsFor);
		stats.add(SubtaskOverTaskRatio);
		
		for (String line :this.linesList){
			String data[]=line.split(",");
	 taskAssigned .addValue(Double.parseDouble(data[3]));
	 reward.addValue(Double.parseDouble(data[4]));
	 bidsSubmitted.addValue(Double.parseDouble(data[5]));
	 bidsSubmittedAndWon.addValue(Double.parseDouble(data[6]));
	 bidsWonRatio.addValue(Double.parseDouble(data[7]));
	
	 subtasksAssigned.addValue(Double.parseDouble(data[8]));
	 selfGain.addValue(Double.parseDouble(data[9]));
	 observationGain .addValue(Double.parseDouble(data[10])); 
	 LearningGain.addValue(Double.parseDouble(data[11]));
	 taskTypeAssignedFromSet.addValue(Double.parseDouble(data[12]));
	 TaskTypeBidsFor.addValue(Double.parseDouble(data[13]));
	 SubtaskOverTaskRatio.addValue(Double.parseDouble(data[14]));
	}
		
		StringBuilder sb = new StringBuilder();
		
		sb.append(String.format("%.2f, %.2f,%d,",this.AO,this.TO,this.option));
		
		for (int i=0;i<stats.size()-1;i++){
			DescriptiveStatistics target=stats.get(i);
			sb.append(String.format("%-4.4f (%-4.4f),", target.getMean(),target.getStandardDeviation()));
		}
		
		sb.append(String.format("%-4.4f (%-4.4f)\n", stats.get(stats.size()-1).getMean(),stats.get(stats.size()-1).getStandardDeviation()));
		
		return sb.toString();
	}
	
	
	
	public String getTaskLine(){
		DescriptiveStatistics numAssigned = new DescriptiveStatistics();
		DescriptiveStatistics numRequired = new DescriptiveStatistics();
		DescriptiveStatistics assignedRatio = new DescriptiveStatistics();
		DescriptiveStatistics reward = new DescriptiveStatistics();
		DescriptiveStatistics numTaskTypesFinished = new DescriptiveStatistics();
	
		ArrayList<DescriptiveStatistics> stats = new ArrayList<DescriptiveStatistics>();
		stats.add(numAssigned);stats.add(numRequired);stats.add(assignedRatio);stats.add(reward);
		stats.add(numTaskTypesFinished);
		
		for (String line :this.linesList){
			String data[]=line.split(",");
			
			numAssigned.addValue(Double.parseDouble(data[3]));
			numRequired.addValue(Double.parseDouble(data[4]));
			assignedRatio.addValue(Double.parseDouble(data[5]));
			reward.addValue(Double.parseDouble(data[6]));

			try {
				numTaskTypesFinished.addValue(Integer.parseInt(data[7]));
			} catch (Exception e) {
				e.printStackTrace();
				System.out.println(line+" "+fileName);
			}
		}
		
		StringBuilder sb = new StringBuilder();
		sb.append(String.format("%.2f, %.2f,%d,",this.AO,this.TO,this.option));
		
		for (int i=0;i<stats.size()-1;i++){
			DescriptiveStatistics target=stats.get(i);
			sb.append(String.format("%-4.4f (%-4.4f),", target.getMean(),target.getStandardDeviation()));
		}
		
		sb.append(String.format("%-4.4f (%-4.4f)\n", stats.get(stats.size()-1).getMean(),stats.get(stats.size()-1).getStandardDeviation()));
		
		return sb.toString();
	}
	
	
	
	
	public void CalculateTaskAverage(){
		
		DescriptiveStatistics numAssigned = new DescriptiveStatistics();
		DescriptiveStatistics numRequired = new DescriptiveStatistics();
		DescriptiveStatistics assignedRatio = new DescriptiveStatistics();
		DescriptiveStatistics reward = new DescriptiveStatistics();
		
		HashSet<Integer> taskTypeSet = new HashSet<Integer>();
		
		for (task t :this.taskMap.values()){
			taskTypeSet.add(t.type);
			numAssigned.addValue(t.getAgentsAssigned());
			numRequired.addValue(t.agentRequired);
			double assignedRatio1=(double)t.getAgentsAssigned()/t.agentRequired;
			assignedRatio.addValue(assignedRatio1);
			reward.addValue(t.reward);
			
		}
		this.averageAgentAssigned=numAssigned.getMean();
		this.averageAgentAssignedSTD=numAssigned.getStandardDeviation();
		this.averageAgentRequired=numRequired.getMean();
		this.averageAgentRequiredSTD=numRequired.getStandardDeviation();
		this.averageTaskReward=reward.getMean();
		this.averageTaskRewardSTD=reward.getStandardDeviation();
		averageAssignedRatio=assignedRatio.getMean();
		averageAssignedRatioSTD=assignedRatio.getStandardDeviation();
		taskTypes=taskTypeSet.size();
	}
	
	
	public static String getTaskAverageTitle(){
		String title="AO,TO,Option,numAssigned,numRequired,assignedRatio,reward,taskTypes";
		return title;
	}
	
	public String getTaskAverageInfo(){
//		int AO=(int)this.AO; int TO=(int)this.TO;
		String value=String.format("%.2f,%.2f,%d,%.4f,%.4f,%.4f,%.4f,%d", AO,TO,this.option,
				this.averageAgentAssigned,this.averageAgentRequired,averageAssignedRatio,this.averageTaskReward,this.taskTypes);
		return value;
	}
	
	public String getTaskAverageWithSTD(){
//		int AO=(int)this.AO; int TO=(int)this.TO;
		String averageAgentAssigned=String.format("%.4f(%.4f)",this.averageAgentAssigned,this.averageAgentAssignedSTD);
		String averageAgentRequired=String.format("%.4f(%.4f)",this.averageAgentRequired,this.averageAgentRequiredSTD);
		String averageAssignedRatio=String.format("%.4f(%.4f)",this.averageAssignedRatio,this.averageAssignedRatioSTD);
		String averageTaskReward=String.format("%.4f(%.4f)",this.averageTaskReward,this.averageTaskRewardSTD);
		
		
		String value=String.format("%.2f,%.2f,%d,%s,%s,%s,%s,%d", AO,TO,this.option,
				averageAgentAssigned,averageAgentRequired,averageAssignedRatio,averageTaskReward,this.taskTypes);
		return value;
	}
	
	
	
	
	
	public void calculateAgentAverage(boolean detail){
		DescriptiveStatistics bidsSubmitted = new DescriptiveStatistics();
//		DescriptiveStatistics taskBidFromSet = new DescriptiveStatistics();
		DescriptiveStatistics bidsSubmittedAndWon = new DescriptiveStatistics();
//		DescriptiveStatistics selected = new DescriptiveStatistics();
		DescriptiveStatistics taskAssigned = new DescriptiveStatistics();
//		DescriptiveStatistics taskAssignmentFromSet = new DescriptiveStatistics();
		DescriptiveStatistics reward = new DescriptiveStatistics();
//		DescriptiveStatistics rewardFromTick = new DescriptiveStatistics();
		DescriptiveStatistics subtasksAssigned = new DescriptiveStatistics();
		DescriptiveStatistics selfGain = new DescriptiveStatistics();
		DescriptiveStatistics observationGain = new DescriptiveStatistics();
		DescriptiveStatistics LearningGain = new DescriptiveStatistics();
//		DescriptiveStatistics taskTypeFromSet= new DescriptiveStatistics();
		DescriptiveStatistics taskTypeAssignedFromSet= new DescriptiveStatistics();
		
		DescriptiveStatistics TaskTypeBidsFor= new DescriptiveStatistics();
	
		DescriptiveStatistics bidsWonRatio= new DescriptiveStatistics();
		double bidsWonRatio1;
		
		DescriptiveStatistics subtaskOverTaskRatio= new DescriptiveStatistics();//numTaskInvolved/numSubtasks
		double  subtaskOverTaskRatio1;
		
		for (Agent a: this.AgentMap.values()){
			if (a.numBidsSubmitted==0){
				 bidsWonRatio1=0;
			}else{
				bidsWonRatio1=(double)a.numBidsSubmittedAndWon/a.numBidsSubmitted;
				 bidsWonRatio.addValue(bidsWonRatio1);
			}
			
			 
			
			if (a.subtaskAssigned==0){
				subtaskOverTaskRatio1=0;
			}else{
				subtaskOverTaskRatio1=(double)a.subtaskAssigned/a.NumTaskInvolved;
			}
			
			
		
			
			bidsSubmitted.addValue(a.numBidsSubmitted);
			bidsSubmittedAndWon.addValue(a.numBidsSubmittedAndWon);
			subtasksAssigned.addValue(a.subtaskAssigned);
			taskAssigned.addValue(a.NumTaskInvolved);
			reward.addValue(a.Reward);
			selfGain.addValue(a.selfGain);
			observationGain.addValue(a.observationGain);
			LearningGain.addValue(a.getLearningGain());
			
//			taskAssignmentFromSet.addValue(a.getTaskAssignmentTotal());
//			taskBidFromSet.addValue(a.getTaskBidTotal());
//			taskTypeFromSet.addValue(a.getTaskTypeTotal());
//			rewardFromTick.addValue(a.rewardAtOneTick);
//			selected.addValue(a.selected);
			
			/*
			 * notice we only count these agents who has an assignment, we are looking at the task varieties of the finished tasks
			 */
			if (a.getTaskTypeTotalOfAgentsAssigned()!=0){
				taskTypeAssignedFromSet.addValue(a.getTaskTypeTotalOfAgentsAssigned());
			}
			/*
			 * notice we only count these agents who has an assignment, we are looking at the task varieties of the finished tasks
			 */
			if (a.taskTypeSumOfBid!=0){
				TaskTypeBidsFor.addValue(a.taskTypeSumOfBid);
			}
			
			/*
			 * notice we only count these agents who has an assignment, we are looking at if agent works for multi subtasks or not
			 */
			if ( subtaskOverTaskRatio1!=0){
				subtaskOverTaskRatio.addValue(subtaskOverTaskRatio1);
			}
			
		//TODO
//			if (detail){
//				System.out.println(a.toString());
//			}
			
		}
		
		
		//TODO
		if (detail){
			System.err.println(taskAssigned.getMean());
//			System.err.println(taskAssigned.getStandardDeviation());
		}
		
		
		averageBidsSubmitted=bidsSubmitted.getMean();
		averageBidsSubmittedSTD=bidsSubmitted.getStandardDeviation(); 	
//		averageTaskBidFromSet=taskBidFromSet.getMean();
		averageBidsSubmittedAndWon=bidsSubmittedAndWon.getMean(); 
		averageBidsSubmittedAndWonSTD=bidsSubmittedAndWon.getStandardDeviation();
//		averageSelected=selected.getMean();
		averageTaskAssigned=taskAssigned.getMean();	
		averageTaskAssignedSTD=taskAssigned.getStandardDeviation();	
//		averageTaskAssignmentFromSet=taskAssignmentFromSet.getMean(); 
		 averageReward=reward.getMean();
		 averageRewardSTD=reward.getStandardDeviation();
//		 averageRewardFromTick=rewardFromTick.getMean();
		 averageTaskTypeAssignedFromSet=taskTypeAssignedFromSet.getMean(); 
		 averageTaskTypeAssignedFromSetSTD=taskTypeAssignedFromSet.getStandardDeviation(); 
		 
		 averageTaskTypeBidsFor=TaskTypeBidsFor.getMean();
		 averageTaskTypeBidsForSTD=TaskTypeBidsFor.getStandardDeviation();
		 
		 
		averageSubtasksAssigned=subtasksAssigned.getMean(); 
		averageSubtasksAssignedSTD=subtasksAssigned.getStandardDeviation(); 
		 averageSelfGain=selfGain.getMean(); 
		 averageSelfGainSTD=selfGain.getStandardDeviation(); 
		 averageObservationGain=observationGain.getMean();
		 averageObservationGainSTD=observationGain.getStandardDeviation();
		 averageLearningGain=LearningGain.getMean();
		 averageLearningGainSTD=LearningGain.getStandardDeviation();
		 
		  averageBidsWonRatio=	bidsWonRatio.getMean();
		  averageBidsWonRatioSTD=	bidsWonRatio.getStandardDeviation();
		
		  this.averageSubtaskOverTaskRatio=subtaskOverTaskRatio.getMean();
		  this.averageSubtaskOverTaskRatioSTD=subtaskOverTaskRatio.getStandardDeviation();
	}
	
public static String getAgentAvarageTitle(){
		String title="AO,TO,Option,numTaskInvolved,Reward,numBidsSubmitted,numBidsSubmittedAndWon,bidsWonRatio,numSubtasksAssigned,selfGain,ObservationGain,LearningGain,numTaskTypeBidsFor,numTaskTypeAssigned,subtasksAssigned/taskAssigned";
		return title;
		
	}

public String getAgentAvarageInfo(){
	
	String value=String.format("%.2f,%.2f,%d,%.4f,%.4f,%.4f,%.4f,%.4f,%.4f,%.4f,%.4f,%.4f,%.4f,%.4f,%.4f", AO,TO,this.option,
			this.averageTaskAssigned,this.averageReward,this.averageBidsSubmitted,this.averageBidsSubmittedAndWon,
			averageBidsWonRatio,this.averageSubtasksAssigned,this.averageSelfGain,this.averageObservationGain,this.averageLearningGain,
			this.averageTaskTypeBidsFor,this.averageTaskTypeAssignedFromSet,this.averageSubtaskOverTaskRatio);
	return value;
}


public String getAgentAvarageWithSTD(){
//	int AO=(int)this.AO; int TO=(int)this.TO;
	String averageTaskAssigned=String.format("%.4f(%.4f)",this.averageTaskAssigned,this.averageTaskAssignedSTD);
	String averageReward=String.format("%.4f(%.4f)",this.averageReward,this.averageRewardSTD);
	String averageBidsSubmitted=String.format("%.4f(%.4f)",this.averageBidsSubmitted,this.averageBidsSubmittedSTD);
	String averageBidsSubmittedAndWon=String.format("%.4f(%.4f)",this.averageBidsSubmittedAndWon,this.averageBidsSubmittedAndWonSTD);
	String averageBidsWonRatio=String.format("%.4f(%.4f)",this.averageBidsWonRatio,this.averageBidsWonRatioSTD);
	String averageSubtasksAssigned=String.format("%.4f(%.4f)",this.averageSubtasksAssigned,this.averageSubtasksAssignedSTD);
	String averageSelfGain=String.format("%.4f(%.4f)",this.averageSelfGain,this.averageSelfGainSTD);
	String averageObservationGain=String.format("%.4f(%.4f)",this.averageObservationGain,this.averageObservationGainSTD);
	String averageLearningGain=String.format("%.4f(%.4f)",this.averageLearningGain,this.averageLearningGainSTD);
	String averageTaskTypeBidsFor=String.format("%.4f(%.4f)",this.averageTaskTypeBidsFor,this.averageTaskTypeBidsForSTD);
	String averageTaskTypeAssignedFromSet=String.format("%.4f(%.4f)",this.averageTaskTypeAssignedFromSet,this.averageTaskTypeAssignedFromSetSTD);
	String averageTaskOverSubtaskRatio=String.format("%.4f(%.4f)",this.averageSubtaskOverTaskRatio,this.averageSubtaskOverTaskRatioSTD);
	
	
	String value=String.format("%.2f,%.2f,%d,%s,%s,%s,%s,%s,%s,%s,%s,%s,%s,%s,%s", AO,TO,this.option,
			averageTaskAssigned,averageReward,averageBidsSubmitted,averageBidsSubmittedAndWon,
			averageBidsWonRatio,averageSubtasksAssigned,averageSelfGain,averageObservationGain,averageLearningGain,
			averageTaskTypeBidsFor,averageTaskTypeAssignedFromSet, averageTaskOverSubtaskRatio);
	return value;
}	
	

//do it in the main
public void outPutAgentAvarage(){
		
	}
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	@SuppressWarnings("unchecked")
	public void outputTaskFile(String dir,String name) throws IOException{
//		HashSet<task> taskSet=(HashSet<task>) this.taskMap.values();
		ArrayList<task> taskList = new 	ArrayList<task>();
		taskList.addAll(this.taskMap.values());
		Collections.sort(taskList);
		
		
		File direcotry = new File(dir+"/TaskInfo");
		if (!direcotry.exists()) {
			if (direcotry.mkdir()) {
				System.out.println("Directory is created!");
			} else {
				System.out.println("Failed to create directory!");
			}
		}
		
		
		
		
		String fileName=direcotry+"/TaskInfo_"+name.substring(12);
		
		File outputfile = new File(fileName);
		FileWriter writer = null;
		try {
			writer = new FileWriter(outputfile);
		} catch (IOException e) {
			e.printStackTrace();
		}
		writer.write(task.getTitle()+"\n");
		
		for (task t: taskList){
			writer.write(t.toString());
		}
		
		writer.close();
		System.out.println("Task outPut ["+fileName+"] Done.");
	}
	
	@SuppressWarnings("unchecked")
	public void outputTaskFile(String dir) throws IOException{
//		HashSet<task> taskSet=(HashSet<task>) this.taskMap.values();
		ArrayList<task> taskList = new 	ArrayList<task>();
		taskList.addAll(this.taskMap.values());
		Collections.sort(taskList);
		
		
		File direcotry = new File(dir+"/TaskInfo");
		if (!direcotry.exists()) {
			if (direcotry.mkdir()) {
				System.out.println("Directory is created!");
			} else {
				System.out.println("Failed to create directory!");
			}
		}
		
//		String[] nameToken=name.split("_");
		
		
//		String fileName=direcotry+"TaskInfo_"+nameToken[1];
		String fileName=direcotry+"/AO"+this.AO+"TO"+this.TO+"Option"+this.option+"_TaskInfo.txt";
		File outputfile = new File(fileName);
		FileWriter writer = null;
		try {
			writer = new FileWriter(outputfile);
		} catch (IOException e) {
			e.printStackTrace();
		}
		writer.write(task.getTitle()+"\n");
		
		for (task t: taskList){
			writer.write(t.toString());
		}
		
		writer.close();
		System.out.println("Task outPut ["+fileName+"] Done.");
	}
	
	
	
	
	//TODO
		//Write outputAgentFile method to write each agent's summary to an file in AOTOOption class
	@SuppressWarnings("unchecked")
	public void outputAgentFile(String dir,String name) throws IOException{
		ArrayList<Agent> agentList = new 	ArrayList<Agent>();
		agentList.addAll(this.AgentMap.values());
		Collections.sort(agentList);
		
		
		File direcotry = new File(dir+"/AgentInfo");
		if (!direcotry.exists()) {
			if (direcotry.mkdir()) {
				System.out.println("Directory is created!");
			} else {
				System.out.println("Failed to create directory!");
			}
		}
		
//		String[] nameToken=name.split("_");
		
		
		String fileName=direcotry+"/AgentInfo_"+name.substring(12);
		
		
//		String fileName=direcotry+"/AO"+this.AO+"TO"+this.TO+"Option"+this.option+"_Agent.txt";
		
		File outputfile = new File(fileName);
		FileWriter writer = null;
		try {
			writer = new FileWriter(outputfile);
		} catch (IOException e) {
			e.printStackTrace();
		}
		writer.write(Agent.getTitle()+"\n");
		
		for (Agent a: agentList){
			writer.write(a.toString());
		}
		
		writer.close();
		System.out.println("Agent outPut ["+fileName+"] Done.");
	}
	
	
	@SuppressWarnings("unchecked")
	public void outputAgentFile(String dir) throws IOException{
		ArrayList<Agent> agentList = new 	ArrayList<Agent>();
		agentList.addAll(this.AgentMap.values());
		Collections.sort(agentList);
		
		
		File direcotry = new File(dir+"/AgentInfo");
		if (!direcotry.exists()) {
			if (direcotry.mkdir()) {
				System.out.println("Directory is created!");
			} else {
				System.out.println("Failed to create directory!");
			}
		}
		
//		String[] nameToken=name.split("_");
		
		
//		String fileName=direcotry+"AgentInfo_"+nameToken[1];
		
		
		String fileName=direcotry+"/AO"+this.AO+"TO"+this.TO+"Option"+this.option+"_Agent.txt";
		
		File outputfile = new File(fileName);
		FileWriter writer = null;
		try {
			writer = new FileWriter(outputfile);
		} catch (IOException e) {
			e.printStackTrace();
		}
		writer.write(Agent.getTitle()+"\n");
		
		for (Agent a: agentList){
			writer.write(a.toString());
		}
		
		writer.close();
		System.out.println("Agent outPut ["+fileName+"] Done.");
	}
	
	
	
	
	
	public void calculateAgentAverageFromAgentList(ArrayList<Agent> agentList){
		DescriptiveStatistics taskAssigned = new DescriptiveStatistics();
		DescriptiveStatistics reward = new DescriptiveStatistics();
		
		DescriptiveStatistics bidsSubmitted = new DescriptiveStatistics();
		DescriptiveStatistics bidsSubmittedAndWon = new DescriptiveStatistics();
		DescriptiveStatistics bidsWonRatio= new DescriptiveStatistics();
		
		DescriptiveStatistics subtasksAssigned = new DescriptiveStatistics();
		DescriptiveStatistics selfGain = new DescriptiveStatistics();
		DescriptiveStatistics observationGain = new DescriptiveStatistics();
		DescriptiveStatistics LearningGain = new DescriptiveStatistics();
		DescriptiveStatistics TaskTypeBidsFor= new DescriptiveStatistics();
		DescriptiveStatistics taskTypeAssigned= new DescriptiveStatistics();
		DescriptiveStatistics subtaskOverTaskRatio= new DescriptiveStatistics();//numTaskInvolved/numSubtasks
		
	
		
		double bidsWonRatio1;
		
		
		double  subtaskOverTaskRatio1;
		
		for (Agent a: agentList){
			double agentLife=a.tickOut-a.tickIn+1;
			
			taskAssigned.addValue(a.NumTaskInvolved/agentLife);
			reward.addValue(a.Reward/agentLife);
			bidsSubmitted.addValue(a.numBidsSubmitted/agentLife);
			bidsSubmittedAndWon.addValue(a.numBidsSubmittedAndWon/agentLife);
			
			if (a.numBidsSubmitted==0){
				 bidsWonRatio1=0;
			}else{
				double x1=a.numBidsSubmittedAndWon/agentLife;
				double x2=a.numBidsSubmitted/agentLife;
			
				bidsWonRatio1=x1/x2;
				 bidsWonRatio.addValue(bidsWonRatio1);
			}
			
			 
			subtasksAssigned.addValue(a.subtaskAssigned/agentLife);
			selfGain.addValue(a.selfGain/agentLife);
			observationGain.addValue(a.observationGain/agentLife);
			LearningGain.addValue(a.learningGain/agentLife);
		
		
		TaskTypeBidsFor.addValue(a.numTaskTypeBidsFor/agentLife);
				taskTypeAssigned.addValue(a.numTaskTypeAssigned/agentLife);
				
				if (a.NumTaskInvolved==0){
					subtaskOverTaskRatio1=0;
				}else{
					double y1=a.subtaskAssigned/agentLife;
					double y2=a.NumTaskInvolved/agentLife;
					subtaskOverTaskRatio1=y1/y2;
				}
				
				subtaskOverTaskRatio.addValue(subtaskOverTaskRatio1);
			
		}
		
			averageTaskAssigned=taskAssigned.getMean();	
			averageTaskAssignedSTD=taskAssigned.getStandardDeviation();	
			 averageReward=reward.getMean();
			 averageRewardSTD=reward.getStandardDeviation();
	averageBidsSubmitted=bidsSubmitted.getMean();
	averageBidsSubmittedSTD=bidsSubmitted.getStandardDeviation(); 	
	averageBidsSubmittedAndWon=bidsSubmittedAndWon.getMean(); 
	averageBidsSubmittedAndWonSTD=bidsSubmittedAndWon.getStandardDeviation();
	  averageBidsWonRatio=	bidsWonRatio.getMean();
	  averageBidsWonRatioSTD=	bidsWonRatio.getStandardDeviation();
	averageSubtasksAssigned=subtasksAssigned.getMean(); 
	averageSubtasksAssignedSTD=subtasksAssigned.getStandardDeviation(); 
	 averageSelfGain=selfGain.getMean(); 
	 averageSelfGainSTD=selfGain.getStandardDeviation(); 
	 averageObservationGain=observationGain.getMean();
	 averageObservationGainSTD=observationGain.getStandardDeviation();
	 averageLearningGain=LearningGain.getMean();
	 averageLearningGainSTD=LearningGain.getStandardDeviation();

	 averageTaskTypeBidsFor=TaskTypeBidsFor.getMean();
	 averageTaskTypeBidsForSTD=TaskTypeBidsFor.getStandardDeviation();
	 averageTaskTypeAssignedFromSet=taskTypeAssigned.getMean(); 
	 averageTaskTypeAssignedFromSetSTD=taskTypeAssigned.getStandardDeviation(); 
	  averageSubtaskOverTaskRatio=subtaskOverTaskRatio.getMean();
	 averageSubtaskOverTaskRatioSTD=subtaskOverTaskRatio.getStandardDeviation();
	}
	
	
	
	public void calculateAgentAverageFromAgentListNoNormalization(ArrayList<Agent> agentList){
		DescriptiveStatistics taskAssigned = new DescriptiveStatistics();
		DescriptiveStatistics reward = new DescriptiveStatistics();
		
		DescriptiveStatistics bidsSubmitted = new DescriptiveStatistics();
		DescriptiveStatistics bidsSubmittedAndWon = new DescriptiveStatistics();
		DescriptiveStatistics bidsWonRatio= new DescriptiveStatistics();
		
		DescriptiveStatistics subtasksAssigned = new DescriptiveStatistics();
		DescriptiveStatistics selfGain = new DescriptiveStatistics();
		DescriptiveStatistics observationGain = new DescriptiveStatistics();
		DescriptiveStatistics LearningGain = new DescriptiveStatistics();
		DescriptiveStatistics TaskTypeBidsFor= new DescriptiveStatistics();
		DescriptiveStatistics taskTypeAssigned= new DescriptiveStatistics();
		DescriptiveStatistics subtaskOverTaskRatio= new DescriptiveStatistics();//numTaskInvolved/numSubtasks
		
	
		DescriptiveStatistics selfGainPerTask= new DescriptiveStatistics();
		DescriptiveStatistics observationGainPerTask= new DescriptiveStatistics();
		DescriptiveStatistics LearningGainPerTask= new DescriptiveStatistics();
		
		double bidsWonRatio1;
		
		
		double  subtaskOverTaskRatio1;
		
		for (Agent a: agentList){
//			double agentLife=a.tickOut-a.tickIn+1;
			
			taskAssigned.addValue(a.NumTaskInvolved);
			reward.addValue(a.Reward);
			bidsSubmitted.addValue(a.numBidsSubmitted);
			bidsSubmittedAndWon.addValue(a.numBidsSubmittedAndWon);
			
			if (a.numBidsSubmitted==0){
				 bidsWonRatio1=0;
			}else{
				double x1=a.numBidsSubmittedAndWon;
				double x2=a.numBidsSubmitted;
			
				bidsWonRatio1=x1/x2;
				 bidsWonRatio.addValue(bidsWonRatio1);
			}
			
			 
			subtasksAssigned.addValue(a.subtaskAssigned);
			selfGain.addValue(a.selfGain);
			observationGain.addValue(a.observationGain);
			LearningGain.addValue(a.learningGain);
		
		
		TaskTypeBidsFor.addValue(a.numTaskTypeBidsFor);
				taskTypeAssigned.addValue(a.numTaskTypeAssigned);
				
				if (a.NumTaskInvolved==0){
					subtaskOverTaskRatio1=0;
				}else{
					double y1=a.subtaskAssigned;
					double y2=a.NumTaskInvolved;
					subtaskOverTaskRatio1=y1/y2;
				}
				
				subtaskOverTaskRatio.addValue(subtaskOverTaskRatio1);
				
				
				if (a.selfGain!=0){
					selfGainPerTask.addValue(a.selfGain/a.NumTaskInvolved);
				}
				
				if (a.observationGain!=0){
					observationGainPerTask.addValue(a.observationGain/a.NumTaskInvolved);
				}
				
				if (a.learningGain!=0){
					LearningGainPerTask.addValue(a.learningGain/a.NumTaskInvolved);
				}
			
		}
		
			averageTaskAssigned=taskAssigned.getMean();	
			averageTaskAssignedSTD=taskAssigned.getStandardDeviation();	
			 averageReward=reward.getMean();
			 averageRewardSTD=reward.getStandardDeviation();
	averageBidsSubmitted=bidsSubmitted.getMean();
	averageBidsSubmittedSTD=bidsSubmitted.getStandardDeviation(); 	
	averageBidsSubmittedAndWon=bidsSubmittedAndWon.getMean(); 
	averageBidsSubmittedAndWonSTD=bidsSubmittedAndWon.getStandardDeviation();
	  averageBidsWonRatio=	bidsWonRatio.getMean();
	  averageBidsWonRatioSTD=	bidsWonRatio.getStandardDeviation();
	averageSubtasksAssigned=subtasksAssigned.getMean(); 
	averageSubtasksAssignedSTD=subtasksAssigned.getStandardDeviation(); 
	 averageSelfGain=selfGain.getMean(); 
	 averageSelfGainSTD=selfGain.getStandardDeviation(); 
	 averageObservationGain=observationGain.getMean();
	 averageObservationGainSTD=observationGain.getStandardDeviation();
	 averageLearningGain=LearningGain.getMean();
	 averageLearningGainSTD=LearningGain.getStandardDeviation();

	 averageTaskTypeBidsFor=TaskTypeBidsFor.getMean();
	 averageTaskTypeBidsForSTD=TaskTypeBidsFor.getStandardDeviation();
	 averageTaskTypeAssignedFromSet=taskTypeAssigned.getMean(); 
	 averageTaskTypeAssignedFromSetSTD=taskTypeAssigned.getStandardDeviation(); 
	  averageSubtaskOverTaskRatio=subtaskOverTaskRatio.getMean();
	 averageSubtaskOverTaskRatioSTD=subtaskOverTaskRatio.getStandardDeviation();
	}
	
public void CalculateTaskAverageFromTaskList(ArrayList<task> taskList){
		
		DescriptiveStatistics numAssigned = new DescriptiveStatistics();
		DescriptiveStatistics numRequired = new DescriptiveStatistics();
		DescriptiveStatistics assignedRatio = new DescriptiveStatistics();
		DescriptiveStatistics reward = new DescriptiveStatistics();
		
		HashSet<Integer> taskTypeSet = new HashSet<Integer>();
		
		for (task t: taskList){
			taskTypeSet.add(t.type);
			numAssigned.addValue(t.numAssigned);
			numRequired.addValue(t.numRequired);
//			double assignedRatio1=(double)t.getAgentsAssigned()/t.agentRequired;
			assignedRatio.addValue(t.ratio);
			reward.addValue(t.reward);
		}
		
		
		this.averageAgentAssigned=numAssigned.getMean();
		this.averageAgentAssignedSTD=numAssigned.getStandardDeviation();
		this.averageAgentRequired=numRequired.getMean();
		this.averageAgentRequiredSTD=numRequired.getStandardDeviation();
		this.averageTaskReward=reward.getMean();
		this.averageTaskRewardSTD=reward.getStandardDeviation();
		averageAssignedRatio=assignedRatio.getMean();
		averageAssignedRatioSTD=assignedRatio.getStandardDeviation();
		taskTypes=taskTypeSet.size();
	}
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
}

