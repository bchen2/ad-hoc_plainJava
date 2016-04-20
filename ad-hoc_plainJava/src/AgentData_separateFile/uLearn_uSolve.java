package AgentData_separateFile;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;

import org.apache.commons.math3.stat.descriptive.SummaryStatistics;

public class uLearn_uSolve {

	static String dir="/Users/BinChen/Desktop/result5/";
	static SummaryStatistics EU_Solve = new SummaryStatistics();
	static SummaryStatistics EU_Learn = new SummaryStatistics();
	
	public static void main(String[] args) {
		ArrayList<File> allFiles = new ArrayList<File>();
		ArrayList<File> allAgentOutputFiles = new ArrayList<File>();
		allFiles.addAll(FindFiles.listf(dir));
		
		
		for (File f: allFiles){
//			AgentOutput
			if (f.isFile() && f.getName().contains("agentUsolveUlearn")){
				allAgentOutputFiles.add(f);
//				System.out.println(f.getName());
			}
		}
		
		
		for (File f :allAgentOutputFiles){
			
			
			
			String name=f.getName();
			System.out.println(name);
		
			String line=null;
			String title=null;
			
			try {
				BufferedReader reader = new BufferedReader(new FileReader(f));
				if ((line=reader.readLine())!=null){
					title=line;
				}
				
				while((line=reader.readLine())!=null) {
					String token[]=line.split(",");
					
					EU_Solve.addValue(Double.parseDouble(token[4]));
					EU_Learn.addValue(Double.parseDouble(token[5]));
					
					
				}
			} catch (IOException e) {
				e.printStackTrace();
			}
			
			
		}
		
		System.out.println("EU_Solve Max="+EU_Solve.getMax());
		System.out.println("EU_Solve Min="+EU_Solve.getMin());
		System.out.println("EU_Solve Mean="+EU_Solve.getMean());
		System.out.println("EU_Solve STD="+EU_Solve.getStandardDeviation());
		System.out.println();
		System.out.println("EU_Learn Max="+EU_Learn.getMax());
		System.out.println("EU_Learn Min="+EU_Learn.getMin());
		System.out.println("EU_Learn Mean="+EU_Learn.getMean());
		System.out.println("EU_Learn STD="+EU_Learn.getStandardDeviation());
		
		
		
		
		}
	}
	
	
	


