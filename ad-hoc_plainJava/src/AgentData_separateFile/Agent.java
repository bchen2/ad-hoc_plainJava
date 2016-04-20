package AgentData_separateFile;

import java.util.HashMap;
import java.util.HashSet;

public class Agent implements Comparable {
	
	int id;
	int optionType;
	HashSet<task> taskSet = new HashSet<task>();
	HashSet<Integer> taskIdSet= new HashSet<Integer>();
	HashSet<Integer> collabratorSet=new HashSet<Integer>(); //records the ID of its collaborators
	
	int NumTaskInvolved=0; //calmutive find max
	double Reward=0;//calmutive find max
	int numBidsSubmitted=0;//calmutive find max
	int numBidsSubmittedAndWon=0;//calmutive find max
	int  tickIn;
	int tickOut;//find max
	
	int selected=0;//find sum   if true then add
	double rewardAtOneTick=0;//find sum
	double selfGain=0;//find sum
	double observationGain=0;//find sum
	int subtaskAssigned=0;
	double learningGain=0;
	int numTaskTypeBidsFor=0;
	int numTaskTypeAssigned=0;
	double subtaskOverTaskRatio=0;
	
	
	
	int TaskAssignmentAtOneTick=0;//find sum  using set
	int taskTobid=0;//find sum     using set
	int taskTypeSumOfBid=0;//find sum   using set
	int taskTypeSumOfAssigned;//find sum   using set
	
	int observedCapUsedCount=0;
	
	HashSet<Integer> taskAssignmentSet = new HashSet<Integer>();
	HashSet<Integer> taskBidSet = new HashSet<Integer>();
	HashSet<Integer> taskTypeSet = new HashSet<Integer>();//taskType of the bids, when agent bids, put the task type in here
	HashSet<Integer> taskTypeOfAssignedTasksSet = new HashSet<Integer>();//taskType of the tasks that this agent get assigned
	
	
	
	
	HashMap<Integer,TickStats> tickMap = new HashMap<Integer,TickStats>();
	
	public Agent(int agentId) {
		this.id=agentId;
	}
	
	
	
	public Agent(int agentId, int TotalTick) {
		this.id=agentId;
		for (int i=1; i<=TotalTick;i++){
			TickStats tickStats = new TickStats();
			tickStats.tick=i;
			tickMap.put(i, tickStats);
		}
	}
	
	public Agent(int agentId, int TotalTick, int optionType) {
		this.id=agentId;
		this.optionType=optionType;
		for (int i=1; i<=TotalTick;i++){
			TickStats tickStats = new TickStats();
			tickStats.tick=i;
			tickMap.put(i, tickStats);
		}
	}
	
	
	
	
	
	
	public void addTaskAssignmentSet(int num){
		this.taskAssignmentSet.add(num);
		this.TaskAssignmentAtOneTick=this.taskAssignmentSet.size();
	}
	
	public void addTaskBidSet(int num){
		this.taskBidSet.add(num);
		this.taskTobid=this.taskBidSet.size();
	}
	
	public void addTaskTypeSet(int num){
		this.taskTypeSet.add(num);
		this.taskTypeSumOfBid=this.taskTypeSet.size();
	}
	
	public void addTaskTypeOfAssignedTaskSet(int num){
		this.taskTypeOfAssignedTasksSet.add(num);
		this.taskTypeSumOfAssigned=this.taskTypeOfAssignedTasksSet.size();
	}
	
	public int getTaskAssignmentTotal(){
		return taskAssignmentSet.size();
	}
	
	public int getTaskBidTotal(){
		return taskBidSet.size();
	}
	
	/**
	 * the task types of the task that the agent bids on
	 * @return
	 */
	public int getTaskTypeTotal(){
		return taskTypeSet.size();
	}
	
	
	
	public int getTaskTypeTotalOfAgentsAssigned(){
		return this.taskTypeOfAssignedTasksSet.size();
	}
	
	public double getLearningGain(){
		return this.selfGain+this.observationGain;
	}
	
	public void addSelected(String selected){
		this.selected+=1;
	}
	
	public void addRewardAtOneTick(double reward){
		this.rewardAtOneTick+=reward;
	}
	
	public void addSelfGain(double gain){
		this.selfGain+=gain;
	}
	
	public void addObervationGain(double gain){
		this.observationGain+=gain;
	}
	
	public void addSubtasksAssigned(int num){
		this.subtaskAssigned+=num;
	}
	

	
	public void addMaxTick(int num){
		if (num>this.tickOut){
			this.tickOut=num;
		}
	}
	
	public void addMaxNumTaskInvolved(int num){
		if (num>this.NumTaskInvolved){
			this.NumTaskInvolved=num;
		}
	}
	
	public void addMaxReward(double num){
		if (num>this.Reward){
			this.Reward=num;
		}
	}
	
	public void addMaxNumBidsSumbitted(int num){
		if (num>this.numBidsSubmitted){
			this.numBidsSubmitted=num;
		}
	}
	
	public void addMaxNumBidsSubmittedAndWon(int num){
		if (num>this.numBidsSubmittedAndWon){
			this.numBidsSubmittedAndWon=num;
		}
	}
	
	
	

	public static String getTitle(){
//		String title="agentId,tickIn,tickOut,numTaskInvolved,numTaskFromTick,reward,rewardFromTick,numBidsSubmitted,numBidsFromTick,numBidsSubmittedAndWon,numSelected,numSubtasksAssgined,selfGain,ObservationGain,LearningGain,numTaskTypeBidsFor,numTaskTypeAssigned";
		String title="agentId,tickIn,tickOut,numTaskInvolved,reward,numBidsSubmitted,numBidsSubmittedAndWon,numSubtasksAssgined,selfGain,ObservationGain,LearningGain,numTaskTypeBidsFor,numTaskTypeAssigned,subtasksAssigned/taskAssigned,observedCapUsedCount";
		return title;
	}	
	
//TODO
	//Write toString method for each agent 
	public String toString(){
//		String value=String.format("%d,%d,%d,%d,%d,%f,%f,%d,%d,%d,%d,%d,%f,%f,%f,%d,%d\n",
//				this.id,this.tickIn,this.tickOut,this.NumTaskInvolved,this.TaskAssignmentAtOneTick,this.Reward,this.rewardAtOneTick,this.numBidsSubmitted,this.taskTobid,this.numBidsSubmittedAndWon,
//				this.selected,this.subtaskAssigned,this.selfGain,this.observationGain,this.getLearningGain(),this.taskTypeSumOfBid,this.taskTypeSumOfAssigned);
		double subtaskOverTaskRatio=0;
		if (this.subtaskAssigned!=0){
			subtaskOverTaskRatio=(double)this.subtaskAssigned/this.NumTaskInvolved;
		}
		String value=String.format("%d,%d,%d,%d,%f,%d,%d,%d,%f,%f,%f,%d,%d,%f,%d\n",
				this.id,this.tickIn,this.tickOut,this.NumTaskInvolved,this.Reward,this.numBidsSubmitted,this.numBidsSubmittedAndWon,
				this.subtaskAssigned,this.selfGain,this.observationGain,this.getLearningGain(),this.taskTypeSumOfBid,this.taskTypeSumOfAssigned,subtaskOverTaskRatio,this.observedCapUsedCount);
//		System.out.println(title);
//		System.out.println(value);
		return value;
		
	}
	
	
//TODO
	//Write outputAgentFile method to write each agent's summary to an file in AOTOOption class
	
	
	
	
	
	
	
	
	

	@Override
	public int compareTo(Object o) {
		Agent a = (Agent) o; 
        return this.id - a.id ;
		
	}
}
