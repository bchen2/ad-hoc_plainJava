package AgentData_separateFile;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Scanner;

public class AgentDetail {

	static HashMap<String, AOTOOption> map = new HashMap<String, AOTOOption>();

	static AOTOOption currentAOTOOption;

	static String dir = "/Volumes/My Passport for Mac/result1";

	public static void main(String[] args) throws IOException {

		List<File> flist = listf(dir);
		ArrayList<File> fileTobeProcessed = new ArrayList<File>();
		// System.out.println(flist);
		for (File f : flist) {
			if (f.isFile() && f.getName().contains("AgentOutput")) {
				// System.out.println(f.getName());
				fileTobeProcessed.add(f);
			}
		}

		long startTime = System.nanoTime();

		for (File f : fileTobeProcessed) {
			processEachFile(f);
		}

		long endTime = System.nanoTime();
		long duration = (endTime - startTime); // divide by 1000000 to get
												// milliseconds.
		long nanoTime = duration / 1000000;
		long second = nanoTime / 1000;
		System.out.println("Duriation = " + duration / 1000000 + "ms or "
				+ second + " second");

		java.awt.Toolkit.getDefaultToolkit().beep();

		// File file1 =fileTobeProcessed.get(0);
		// System.out.println(file1.getName());
		// System.out.println(file1.getName().substring(12));

		/**
		 * output SummaryFile needs to be rewritten.......
		 */

		// for (File f : fileTobeProcessed){
		//
		//
		//
		//
		// processEachRun(f);
		//
		// }

	}

	public static void processEachFile(File f) {
		String name = f.getName();
		// System.out.println(name);

		String token[] = name.split("_");
		String name1 = token[1];
		double AO = Double.parseDouble(name1.substring(2, name1.indexOf("TO")));
		double TO = Double.parseDouble(name1.substring(name1.indexOf("TO") + 2,
				name1.indexOf("Op")));
		int Option = Integer.parseInt(name1.substring(name1.indexOf("Op") + 2));

		/**
		 * initalize an AOTOOption
		 */
		currentAOTOOption = new AOTOOption(AO, TO, Option);
		String fileName = f.getAbsolutePath();
		readInFile1(fileName);

		/**
		 * output agentInfo and taskInfo
		 */
		try {
			currentAOTOOption.outputTaskFile(dir, f.getName());
			currentAOTOOption.outputAgentFile(dir, f.getName());
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

	public static void processEachRun(File f) {
		/*
		 * initialize AOTOOption
		 */
		double AO[] = { 0, 0.01, 0.02, 0.05, 0.1 };
		double TO[] = { 0, 0.05, 0.1, 0.2, 0.5 };
		int Option[] = { 14, 15, 16 };

		for (double agentOpenness : AO) {
			for (double taskOpenness : TO) {
				for (int option : Option) {
					AOTOOption item = new AOTOOption(agentOpenness,
							taskOpenness, option);
					String key = "AO" + agentOpenness + "TO" + taskOpenness
							+ "Option" + option;
					map.put(key, item);
				}
			}
		}

		/**
		 * read the agent file, for each AOTOOPtion, create an object to hold
		 * the data
		 */
		// String
		// fileName="/Users/BinChen/Desktop/results/run1/AgentData.2015.Oct.28.21_34_27.txt";
		String fileName = f.getAbsolutePath();

		long startTime = System.nanoTime();
		readInFile(fileName);
		long endTime = System.nanoTime();
		long duration = (endTime - startTime); // divide by 1000000 to get
												// milliseconds.
		long nanoTime = duration / 1000000;
		long second = nanoTime / 1000;
		System.out.println("Duriation = " + duration / 1000000 + "ms or "
				+ second + " second");

		// System.out.println(map.keySet());

		// AOTOOption a=map.get("AO0.0TO0.0Option14");
		// a.taskMap.values().size();

		// a.calculateAgentAverage();
		// System.out.println(AOTOOption.getAgentAvarageTitle());
		// System.out.println(a.getAgentAvarageInfo());
		// System.out.println(a.getAgentAvarageWithSTD());
		//
		//
		// a.CalculateTaskAverage();
		// System.out.println(AOTOOption.getTaskAverageTitle());
		// System.out.println(a.getTaskAverageInfo());
		// System.out.println(a.getTaskAverageWithSTD());

		/**
		 * find dir
		 */
		String path = f.getAbsolutePath();
		int index = path.indexOf("Agent", 0);
		String dir = path.substring(0, index - 1);
		System.out.println(dir);

		// for each AOTOOption
		/**
		 * write out each individual agents info and each task info
		 */
		for (AOTOOption ato : map.values()) {
			try {
				ato.outputTaskFile(dir);
				ato.outputAgentFile(dir);
			} catch (IOException e) {
				e.printStackTrace();
			}
		}

		/*
		 * output TaskAverage, agentAverage file for this run
		 */
		try {
			outputSummaryFile(dir);
		} catch (IOException e) {
			e.printStackTrace();
		}

		map.clear();
	}

	public static void readInFile1(String inputFile) {

		String title = null;
		String line = null;

		try {
			BufferedReader reader = new BufferedReader(
					new FileReader(inputFile));
			if ((line = reader.readLine()) != null) {
				title = line;
			}

			while ((line = reader.readLine()) != null) {
				processLine1(line);
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
		/**
		 * read in lines
		 */
		System.out.println("reading done!");

	}

	public static void readInFile(String inputFile) {

		String title = null;
		String line = null;

		try {
			BufferedReader reader = new BufferedReader(
					new FileReader(inputFile));
			if ((line = reader.readLine()) != null) {
				title = line;
			}

			while ((line = reader.readLine()) != null) {
				processLine(line);
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
		/**
		 * read in lines
		 */
		System.out.println("reading done!");

	}

	public static void processLine1(String line) {

		String[] token = line.split(",");
		int agentId = Integer.parseInt(token[1]);
		int tick = (int) Double.parseDouble(token[0]);

		AOTOOption item = currentAOTOOption;

		/**
		 * processing agent info
		 */
		Agent targetAgent = null;
		if (!item.AgentMap.keySet().contains(agentId)) {
			targetAgent = new Agent(agentId);
			targetAgent.tickIn = tick;
			item.AgentMap.put(agentId, targetAgent);
		} else {
			targetAgent = item.AgentMap.get(agentId);
		}

		targetAgent.addMaxTick(tick);

		int numTaskInvolved = Integer.parseInt(token[2]);
		targetAgent.addMaxNumTaskInvolved(numTaskInvolved);
		int numBidsSumbitted = Integer.parseInt(token[6]);
		targetAgent.addMaxNumBidsSumbitted(numBidsSumbitted);
		int numBidsSubmittedAndWon = Integer.parseInt(token[7]);
		targetAgent.addMaxNumBidsSubmittedAndWon(numBidsSubmittedAndWon);
		double reward = Double.parseDouble(token[4]);
		targetAgent.addMaxReward(reward);
		targetAgent.observedCapUsedCount = Integer.parseInt(token[22]);

		/*
		 * notice the selected does not parese to boolean correctly and string
		 * comparesion also dones not work
		 */
		String selected = token[8].trim();
		// System.out.println("token = " +selected+ "length="+
		// selected.length());
		// System.out.println("Pass in  ======" + selected.toUpperCase());
		if (selected.length() == 6) {
			// System.out.println("updateSelected!");
			targetAgent.addSelected(selected);
		}

		double rewardAtOneTick = Double.parseDouble(token[17]);
		targetAgent.addRewardAtOneTick(rewardAtOneTick);
		double selfGain = Double.parseDouble(token[20]);
		targetAgent.addSelfGain(selfGain);
		double observationGain = Double.parseDouble(token[21]);
		targetAgent.addObervationGain(observationGain);
		int subtasksAssigned = Integer.parseInt(token[18]);
		targetAgent.addSubtasksAssigned(subtasksAssigned);

		int taskBid = Integer.parseInt(token[5]);
		if (taskBid != 0) {
			targetAgent.addTaskBidSet(taskBid);
		}
		int taskType = Integer.parseInt(token[10]);
		if (taskType != 0) {
			targetAgent.addTaskTypeSet(taskType);
		}

		int taskAssignment = Integer.parseInt(token[3]);
		if (taskAssignment != 0) {
			targetAgent.addTaskAssignmentSet(taskAssignment);
			targetAgent.addTaskTypeOfAssignedTaskSet(taskType);
		}

		// done with agent info

		/**
		 * processing Task info
		 */
		int taskId = taskAssignment;
		if (taskId != 0) {
			task targetTask = null;
			if (!item.taskMap.keySet().contains(taskId)) {
				targetTask = new task(taskId, taskType);
				item.taskMap.put(taskId, targetTask);
			} else {
				targetTask = item.taskMap.get(taskId);
			}
			targetTask.agentRequired = Integer.parseInt(token[16]);
			targetTask.agentsAssigned.add(agentId);
			int numSubtasksAssigned = Integer.parseInt(token[18]);
			targetTask.agentSubtaskNumMap.put(agentId, numSubtasksAssigned);
			double taskReward = Double.parseDouble(token[19]);
			targetTask.reward = taskReward;
		}

	}

	public static void processLine(String line) {

		String[] token = line.split(",");

		double agentOpenness = Double.parseDouble(token[12]);
		double taskOpenness = Double.parseDouble(token[13]);
		int option = Integer.parseInt(token[14]);

		int agentId = Integer.parseInt(token[1]);
		int tick = (int) Double.parseDouble(token[0]);

		String key = "AO" + agentOpenness + "TO" + taskOpenness + "Option"
				+ option;

		// System.out.println(key);
		AOTOOption item = map.get(key);

		/**
		 * processing agent info
		 */
		Agent targetAgent = null;
		if (!item.AgentMap.keySet().contains(agentId)) {
			targetAgent = new Agent(agentId);
			targetAgent.tickIn = tick;
			item.AgentMap.put(agentId, targetAgent);
		} else {
			targetAgent = item.AgentMap.get(agentId);
		}

		targetAgent.addMaxTick(tick);

		int numTaskInvolved = Integer.parseInt(token[2]);
		targetAgent.addMaxNumTaskInvolved(numTaskInvolved);
		int numBidsSumbitted = Integer.parseInt(token[6]);
		targetAgent.addMaxNumBidsSumbitted(numBidsSumbitted);
		int numBidsSubmittedAndWon = Integer.parseInt(token[7]);
		targetAgent.addMaxNumBidsSubmittedAndWon(numBidsSubmittedAndWon);
		double reward = Double.parseDouble(token[4]);
		targetAgent.addMaxReward(reward);

		/*
		 * notice the selected does not parese to boolean correctly and string
		 * comparesion also dones not work
		 */
		String selected = token[8].trim();
		// System.out.println("token = " +selected+ "length="+
		// selected.length());
		// System.out.println("Pass in  ======" + selected.toUpperCase());
		if (selected.length() == 6) {
			// System.out.println("updateSelected!");
			targetAgent.addSelected(selected);
		}

		double rewardAtOneTick = Double.parseDouble(token[17]);
		targetAgent.addRewardAtOneTick(rewardAtOneTick);
		double selfGain = Double.parseDouble(token[20]);
		targetAgent.addSelfGain(selfGain);
		double observationGain = Double.parseDouble(token[21]);
		targetAgent.addObervationGain(observationGain);
		int subtasksAssigned = Integer.parseInt(token[18]);
		targetAgent.addSubtasksAssigned(subtasksAssigned);

		int taskBid = Integer.parseInt(token[5]);
		if (taskBid != 0) {
			targetAgent.addTaskBidSet(taskBid);
		}
		int taskType = Integer.parseInt(token[10]);
		if (taskType != 0) {
			targetAgent.addTaskTypeSet(taskType);
		}

		int taskAssignment = Integer.parseInt(token[3]);
		if (taskAssignment != 0) {
			targetAgent.addTaskAssignmentSet(taskAssignment);
			targetAgent.addTaskTypeOfAssignedTaskSet(taskType);
		}

		// done with agent info

		/**
		 * processing Task info
		 */
		int taskId = taskAssignment;
		if (taskId != 0) {
			task targetTask = null;
			if (!item.taskMap.keySet().contains(taskId)) {
				targetTask = new task(taskId, taskType);
				item.taskMap.put(taskId, targetTask);
			} else {
				targetTask = item.taskMap.get(taskId);
			}
			targetTask.agentRequired = Integer.parseInt(token[16]);
			targetTask.agentsAssigned.add(agentId);
			int numSubtasksAssigned = Integer.parseInt(token[18]);
			targetTask.agentSubtaskNumMap.put(agentId, numSubtasksAssigned);
			double taskReward = Double.parseDouble(token[19]);
			targetTask.reward = taskReward;
		}

	}

	public static void outputSummaryFile(String dir) throws IOException {
		/*
		 * output TaskAverage, agentAverage file for this run
		 */

		File direcotry = new File(dir + "/Summary");
		if (!direcotry.exists()) {
			if (direcotry.mkdir()) {
				System.out.println("Directory is created!");
			} else {
				System.out.println("Failed to create directory!");
			}
		}

		/** open an writer */
		File AgentSummaryfile = new File(direcotry + "/AgentSummary.txt");
		File AgentSummarySTDfile = new File(direcotry + "/AgentSummarySTD.txt");
		File TaskSummaryfile = new File(direcotry + "/TaskSummary.txt");
		File TaskSummarySTDfile = new File(direcotry + "/TaskSummarySTD.txt");
		FileWriter writer1 = null;
		FileWriter writer2 = null;
		FileWriter writer3 = null;
		FileWriter writer4 = null;

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

		writer1.write(AOTOOption.getAgentAvarageTitle() + "\n");
		writer2.write(AOTOOption.getAgentAvarageTitle() + "\n");
		writer3.write(AOTOOption.getTaskAverageTitle() + "\n");
		writer4.write(AOTOOption.getTaskAverageTitle() + "\n");

		for (AOTOOption item : map.values()) {
			// TODO print details
			boolean detail = false;

			// if (item.AO==0 && item.TO==0 && item.option==14){
			// detail=true;
			// }

			item.calculateAgentAverage(detail);
			writer1.write(item.getAgentAvarageInfo() + "\n");
			writer2.write(item.getAgentAvarageWithSTD() + "\n");

			item.CalculateTaskAverage();
			writer3.write(item.getTaskAverageInfo() + "\n");
			writer4.write(item.getTaskAverageWithSTD() + "\n");
		}

		writer1.close();
		writer2.close();
		writer3.close();
		writer4.close();

		java.awt.Toolkit.getDefaultToolkit().beep();
		System.out.println("Output Summary Done.");

	}

	public static void outputSummaryFile1(String dir) throws IOException {
		/*
		 * output AgentSummary and TaskSummary, in agentSummary, each
		 * configuration just become 1 line in this file
		 */

		File direcotry = new File(dir + "/Summary");
		if (!direcotry.exists()) {
			if (direcotry.mkdir()) {
				System.out.println("Directory is created!");
			} else {
				System.out.println("Failed to create directory!");
			}
		}

		/** open an writer */
		File AgentSummaryfile = new File(direcotry + "/AgentSummary.txt");
		File AgentSummarySTDfile = new File(direcotry + "/AgentSummarySTD.txt");
		File TaskSummaryfile = new File(direcotry + "/TaskSummary.txt");
		File TaskSummarySTDfile = new File(direcotry + "/TaskSummarySTD.txt");
		FileWriter writer1 = null;
		FileWriter writer2 = null;
		FileWriter writer3 = null;
		FileWriter writer4 = null;

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

		writer1.write(AOTOOption.getAgentAvarageTitle() + "\n");
		writer2.write(AOTOOption.getAgentAvarageTitle() + "\n");
		writer3.write(AOTOOption.getTaskAverageTitle() + "\n");
		writer4.write(AOTOOption.getTaskAverageTitle() + "\n");

		List<File> flist = listf(dir);
		ArrayList<File> fileTobeProcessed = new ArrayList<File>();
		// System.out.println(flist);
		for (File f : flist) {
			if (f.isFile() && f.getName().contains("AgentOutput")) {
				// System.out.println(f.getName());
				fileTobeProcessed.add(f);
			}
		}

		for (AOTOOption item : map.values()) {
			// TODO print details
			boolean detail = false;

			// if (item.AO==0 && item.TO==0 && item.option==14){
			// detail=true;
			// }

			item.calculateAgentAverage(detail);
			writer1.write(item.getAgentAvarageInfo() + "\n");
			writer2.write(item.getAgentAvarageWithSTD() + "\n");

			item.CalculateTaskAverage();
			writer3.write(item.getTaskAverageInfo() + "\n");
			writer4.write(item.getTaskAverageWithSTD() + "\n");
		}

		writer1.close();
		writer2.close();
		writer3.close();
		writer4.close();

		System.out.println("Output Summary Done.");

	}

	public static List<File> listf(String directoryName) {
		File directory = new File(directoryName);

		List<File> resultList = new ArrayList<File>();

		// get all the files from a directory
		File[] fList = directory.listFiles();
		resultList.addAll(Arrays.asList(fList));
		for (File file : fList) {
			if (file.isFile()) {
				// System.out.println(file.getAbsolutePath());
			} else if (file.isDirectory()) {
				resultList.addAll(listf(file.getAbsolutePath()));
			}
		}
		// System.out.println(fList);
		return resultList;
	}

}
