package AgentData_separateFile;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Date;
import java.util.HashSet;
import java.util.List;

public class example {

	public static void main(String[] args) throws IOException {
		// TODO Auto-generated method stub
//		task t =  new task(8,4);
//		t.reward=47;
//		t.agentRequired=12;t.agentsAssigned.add(2);
//		t.agentsAssigned.add(5);
//		t.agentsAssigned.add(10);
//		t.agentsAssigned.add(3);
//		t.agentsAssigned.add(8);
//		t.agentSubtaskNumMap.put(5, 3);
//		t.agentSubtaskNumMap.put(10, 2);
//		t.agentSubtaskNumMap.put(3, 1);
//		t.agentSubtaskNumMap.put(8, 4);
//		t.agentSubtaskNumMap.put(2, 2);
		
		
//		System.out.println(t.agentSubtaskNumMap);
//		String title="taskId,taskType,numAssigned,numRequired,ratio,reward,agentSet,assignment";
//		double ratio=(double)t.agentsAssigned.size()/t.agentRequired;
//		String value=String.format("%d,%d,%d,%d,%f,%f,%s,%s",t.id,t.type,t.agentsAssigned.size(),t.agentRequired,ratio,t.reward,t.getAgentAssignedString(),t.getAgentSubtaskNumMapString());
//		System.out.println(title);
//		System.out.println(value);
//		System.out.println(t.toString());
//		
//		task task2=new task(4,200);
//		
//		ArrayList<task> taskList = new 	ArrayList<task>();
////		HashSet<task> Set = new HashSet<task>();
//		taskList.add(t);
//		taskList.add(task2);
//		
//		
//		Set.add(t);Set.add(task2);
//		System.out.println(taskList);
//		System.out.println();taskList.get(1).toString();
//		
//		Collections.sort(taskList);
		
//		taskList.get(0).toString();
//		for (task t1: taskList){
//			System.out.print(t1.toString());
//		}
		
		
		
//		Agent a = new Agent (8);
//		Agent b = new Agent (2);
//		ArrayList<Agent>AgentList = new 	ArrayList<Agent>();
////		HashSet<task> Set = new HashSet<task>();
//		AgentList.add(a);
//		AgentList.add(b);
//		
//		for(Agent k: AgentList){
//			System.out.println(k.id);
//		}
//		
//		Collections.sort(AgentList);
//		
//		for(Agent k: AgentList){
//			System.out.println(k.id);
//		}
		
		
		
//		boolean b1=Boolean.parseBoolean("TRUE");
//		System.out.println(b1);
		
		
//		String s="32, TRUE";
//		String token[]=s.split(",");
//		System.out.println("token = "+ token[1]);
//		
//		System.out.println(token[1].trim().length());
//		if (token[1].trim().length()==4){
//			System.out.println("YEs");
//		}
		
		
		
		
//		File folder = new File("/Users/BinChen/Desktop/results/");
//		File[] listOfFiles = folder.listFiles();
//		
//		
//		ArrayList<File> dirNameList= new ArrayList<File>();
///**
// * find all directory that contains the file needs to be processed
// */
//		for (File file : listOfFiles) {
//			    
//		    if (file.isDirectory()){
//		    	System.out.println(file.getAbsolutePath());
//		    	dirNameList.add(file);
//		    }
//		    
//		}
//		
//		
///**
// * for each directory, grab all files and find the file that needs to be process		
// */
//		
//		ArrayList<File> fileTobeProcessed = new ArrayList<File> ();
//		
//		for (File dir: dirNameList){
//			File[] listOfFiles1 = dir.listFiles();
//			for (File file: listOfFiles1){
//				 if (file.isFile()) {
////				        System.out.println(file.getName());
//				        if (file.getName().contains("AgentData") && !file.getName().contains("batch_param_map")){
//				        	System.out.println(file.getName());
//				        	
//				        	
//				        	fileTobeProcessed.add(file);
//				        }
//			}
//		   
//	    }
//		}
//		
//		
//		/**
//		 * for each file find its directory 
//		 */
//		File file1=fileTobeProcessed.get(5);
//		System.out.println(file1.getAbsolutePath());
//		String path=file1.getAbsolutePath();
//    	int index=path.indexOf("Agent", 0);
//		System.out.println(index);
//		String dir=path.substring(0,index-1);
//		System.out.println(dir);
		
		
		
//		File file2=fileTobeProcessed.get(1);
//		System.out.println(file2.getAbsolutePath());
//		String path2=file1.getAbsolutePath();
//    	int index2=path2.indexOf("Agent", 0);
//		System.out.println(index2);
//		String dir2=path2.substring(0,index2-1);
//		System.out.println(dir2);
		
		
//		
//		System.out.println(getCurrentTimeStamp());
//		
//	String direcotry="/Users/BinChen/Desktop/try";
//    	
//    	/** open an writer */
//    	File AgentSummaryfile = new File(direcotry+"/AgentSummary.txt");
//    	
//    	
//    	
//    		FileWriter writer1 = new FileWriter(AgentSummaryfile);
//    
//    	
//    
//		writer1.write("s");
		 String dir="/Users/BinChen/Desktop/result1/";
		
		List<File> flist=listf(dir);
		ArrayList<File> fileTobeProcessed = new ArrayList<File> ();
//		System.out.println(flist);
		for (File f: flist){
			if (f.isFile() && f.getName().contains("AgentInfo") ){
				System.out.println(f.getName());
				fileTobeProcessed.add(f);
			}
		}	
		
		
		
		
		
		java.awt.Toolkit.getDefaultToolkit().beep();		
		
		
		
		
		
	}
	
	public static String getCurrentTimeStamp() {
	    SimpleDateFormat sdfDate = new SimpleDateFormat("yyyy-MM-dd_HH.mm.ss");//dd/MM/yyyy
	    Date now = new Date();
	    String strDate = sdfDate.format(now);
	    return strDate;
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
	
	
	

}
