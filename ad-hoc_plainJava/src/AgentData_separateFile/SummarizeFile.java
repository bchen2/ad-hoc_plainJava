package AgentData_separateFile;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.apache.commons.math3.stat.descriptive.DescriptiveStatistics;

public class SummarizeFile {
	
		static  AOTOOption currentAOTOOption;
		static String dir="/Users/BinChen/Desktop/result1";
	static String dirAgent="/Users/BinChen/Desktop/result1/AgentInfo";
	static String dirTask="/Users/BinChen/Desktop/result1/TaskInfo";
	
	static FileWriter writer1 = null;
	static FileWriter writer2 = null;
	static FileWriter writer3 = null;
	static FileWriter writer4 = null;
	
	public static void main(String[] args) {
		/*
		 * output AgentSummary and TaskSummary, in agentSummary, each configuration just become 1 line in this file
		 */
		
		File direcotry = new File(dir+"/Summary");
		if (!direcotry.exists()) {
			if (direcotry.mkdir()) {
				System.out.println("Directory is created!");
			} else {
				System.out.println("Failed to create directory!");
			}
		}
		
		try {
			processAgentFile(direcotry);
//			processTaskFile(direcotry);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	
	}
	
	
	
	
	
	
	
	public static List<File> listf(String directoryName) {
	    File directory = new File(directoryName);

	    List<File> resultList = new ArrayList<File>();

	    // get all the files from a directory
	    File[] fList = directory.listFiles();
	    resultList.addAll(Arrays.asList(fList));
	    for (File file : fList) {
	        if (file.isFile()) {
//	            System.out.println(file.getAbsolutePath());
	        } else if (file.isDirectory()) {
	            resultList.addAll(listf(file.getAbsolutePath()));
	        }
	    }
	    //System.out.println(fList);
	    return resultList;
	} 
	
	
	static void processAgentFile( File direcotry) throws IOException{
		/** open an writer */
		File AgentSummaryfile = new File(direcotry+"/Summary1NoNormal/AgentSummary.txt");
		File AgentSummarySTDfile = new File(direcotry+"/Summary1NoNormal/AgentSummarySTD.txt");
		
		
		
		try {
			writer1 = new FileWriter(AgentSummaryfile);
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		try {
			writer2 = new FileWriter(AgentSummarySTDfile);
		} catch (IOException e) {
			e.printStackTrace();
		}
		


		writer1.write(AOTOOption.getAgentAvarageTitle()+"\n");
		writer2.write(AOTOOption.getAgentAvarageTitle()+"\n");
		
		
		List<File> flist=listf(dirAgent);
		ArrayList<File> fileTobeProcessed = new ArrayList<File> ();
		
		
//		System.out.println(flist);
		for (File f: flist){
			if (f.isFile() && f.getName().contains("AgentInfo") ){
//				System.out.println(f.getName());
				fileTobeProcessed.add(f);
			}
		}
		
//		File file1= fileTobeProcessed.get(0);
//		processEachAgentFile(file1);
		
		for(File f :fileTobeProcessed){
			processEachAgentFile(f);
		}
		
		writer1.close();
		writer2.close();
	}
	
	
	public static void processEachAgentFile(File f) throws IOException{
		
		
		
		
		String name=f.getName();
		System.out.println(name);
		
		String data[]=name.split("_");
		String name1=data[1];
		double AO=Double.parseDouble(name1.substring(2,name1.indexOf("TO")));
		double TO=Double.parseDouble(name1.substring(name1.indexOf("TO")+2,name1.indexOf("Op")));
		int Option=Integer.parseInt(name1.substring(name1.indexOf("Op")+2));
		
		currentAOTOOption=new AOTOOption(AO,TO,Option);
		
		
		
		
		
		String title = null;
		String line = null;
		
		ArrayList<Agent> agentList = new ArrayList<Agent>();
		
		try {
			BufferedReader reader = new BufferedReader(new FileReader(f));
			if ((line=reader.readLine())!=null){
				title=line;
			}
			
			while((line=reader.readLine())!=null) {
				String token[]=line.split(",");
				
				Agent a = new Agent(Integer.parseInt(token[0]));
				a.tickIn=Integer.parseInt(token[1]);
				a.tickOut=Integer.parseInt(token[2]);
				a.NumTaskInvolved=Integer.parseInt(token[3]);
				a.Reward=Double.parseDouble(token[4]);
				a.numBidsSubmitted=Integer.parseInt(token[5]);
				a.numBidsSubmittedAndWon=Integer.parseInt(token[6]);
				a.subtaskAssigned=Integer.parseInt(token[7]);
				a.selfGain=Double.parseDouble(token[8]);
				a.observationGain=Double.parseDouble(token[9]);
				a.learningGain=Double.parseDouble(token[10]);
				a.numTaskTypeBidsFor=Integer.parseInt(token[11]);
				a.numTaskTypeAssigned=Integer.parseInt(token[12]);
				a.subtaskOverTaskRatio=Double.parseDouble(token[13]);
				agentList.add(a);
				
				
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		
//		currentAOTOOption.calculateAgentAverageFromAgentList(agentList);
		currentAOTOOption.calculateAgentAverageFromAgentListNoNormalization(agentList);
		writer1.write(currentAOTOOption.getAgentAvarageInfo()+"\n");
		writer2.write(currentAOTOOption.getAgentAvarageWithSTD()+"\n");
		
		
		
		
		
		
	}
	
	
	public String averageAgent(ArrayList<Agent> agentList,double AO,double TO, int Option){
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
		
		double	averageTaskAssigned=taskAssigned.getMean();	
		double	averageTaskAssignedSTD=taskAssigned.getStandardDeviation();	
		double	 averageReward=reward.getMean();
		double	 averageRewardSTD=reward.getStandardDeviation();
double	averageBidsSubmitted=bidsSubmitted.getMean();
double	averageBidsSubmittedSTD=bidsSubmitted.getStandardDeviation(); 	
double	averageBidsSubmittedAndWon=bidsSubmittedAndWon.getMean(); 
double	averageBidsSubmittedAndWonSTD=bidsSubmittedAndWon.getStandardDeviation();
double	  averageBidsWonRatio=	bidsWonRatio.getMean();
double	  averageBidsWonRatioSTD=	bidsWonRatio.getStandardDeviation();
double	averageSubtasksAssigned=subtasksAssigned.getMean(); 
double	averageSubtasksAssignedSTD=subtasksAssigned.getStandardDeviation(); 
double	 averageSelfGain=selfGain.getMean(); 
double	 averageSelfGainSTD=selfGain.getStandardDeviation(); 
double	 averageObservationGain=observationGain.getMean();
double	 averageObservationGainSTD=observationGain.getStandardDeviation();
double	 averageLearningGain=LearningGain.getMean();
double	 averageLearningGainSTD=LearningGain.getStandardDeviation();

double	 averageTaskTypeBidsFor=TaskTypeBidsFor.getMean();
double	 averageTaskTypeBidsForSTD=TaskTypeBidsFor.getStandardDeviation();
double	 averageTaskTypeAssignedFromSet=taskTypeAssigned.getMean(); 
double	 averageTaskTypeAssignedFromSetSTD=taskTypeAssigned.getStandardDeviation(); 
double	  averageSubtaskOverTaskRatio=subtaskOverTaskRatio.getMean();
double	 averageSubtaskOverTaskRatioSTD=subtaskOverTaskRatio.getStandardDeviation();
		
		
		
String value=String.format("%.2f,%.2f,%d,%.4f,%.4f,%.4f,%.4f,%.4f,%.4f,%.4f,%.4f,%.4f,%.4f,%.4f,%.4f", AO,TO,Option,
		averageTaskAssigned,averageReward,averageBidsSubmitted,averageBidsSubmittedAndWon,
		averageBidsWonRatio,averageSubtasksAssigned,averageSelfGain,averageObservationGain,averageLearningGain,
		averageTaskTypeBidsFor,averageTaskTypeAssignedFromSet,averageSubtaskOverTaskRatio);
		
		return value;
		
	}
	
	
	
	static void processTaskFile( File direcotry) throws IOException{
		/** open an writer */
		File TaskSummaryfile = new File(direcotry+"/TaskSummary.txt");
		File TaskSummarySTDfile = new File(direcotry+"/TaskSummarySTD.txt");
		
		
		
		try {
			writer3 = new FileWriter(TaskSummaryfile);
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		try {
			writer4 = new FileWriter(TaskSummarySTDfile);
		} catch (IOException e) {
			e.printStackTrace();
		}
		


		writer3.write(AOTOOption.getTaskAverageTitle()+"\n");
		writer4.write(AOTOOption.getTaskAverageTitle()+"\n");
		
		
		List<File> flist=listf(dirTask);
		ArrayList<File> fileTobeProcessed = new ArrayList<File> ();
		
		
//		System.out.println(flist);
		for (File f: flist){
			if (f.isFile() && f.getName().contains("TaskInfo") ){
//				System.out.println(f.getName());
				fileTobeProcessed.add(f);
			}
		}
		
//		File file1= fileTobeProcessed.get(0);
//		processEachTaskFile(file1);
		
		for(File f :fileTobeProcessed){
			processEachTaskFile(f);
		}
		
		writer3.close();
		writer4.close();
	}	
	
	
	
	
	public static void processEachTaskFile(File f) throws IOException{
		String name=f.getName();
		System.out.println(name);
		
		String data[]=name.split("_");
		String name1=data[1];
		double AO=Double.parseDouble(name1.substring(2,name1.indexOf("TO")));
		double TO=Double.parseDouble(name1.substring(name1.indexOf("TO")+2,name1.indexOf("Op")));
		int Option=Integer.parseInt(name1.substring(name1.indexOf("Op")+2));
		
		currentAOTOOption=new AOTOOption(AO,TO,Option);
		
		
		
		
		
		String title = null;
		String line = null;
		
		ArrayList<task> taskList = new ArrayList<task>();
		
		try {
			BufferedReader reader = new BufferedReader(new FileReader(f));
			if ((line=reader.readLine())!=null){
				title=line;
			}
			
			while((line=reader.readLine())!=null) {
				String token[]=line.split(",");
				
				int taskId=Integer.parseInt(token[0]);
				int taskType=Integer.parseInt(token[1]);
				int numAssigned=Integer.parseInt(token[2]);
				int numRequired=Integer.parseInt(token[3]);
				double ratio=Double.parseDouble(token[4]);
				double reward=Double.parseDouble(token[5]);
				
				
				task t=new task(taskId,taskType);
				t.numAssigned=numAssigned;
				t.numRequired=numRequired;
				t.ratio=ratio;
				t.reward=reward;
				
				
			
				 taskList.add(t);
				
				
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		
		currentAOTOOption.CalculateTaskAverageFromTaskList(taskList);
		
		writer3.write(currentAOTOOption.getTaskAverageInfo()+"\n");
		writer4.write(currentAOTOOption.getAgentAvarageWithSTD()+"\n");
		
	}
	
	
	
	
	
	
	
	
	

}
