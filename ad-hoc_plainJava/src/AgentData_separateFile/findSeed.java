package AgentData_separateFile;


import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;

public class findSeed {

	static String dir="/Volumes/My Passport for Mac/result4";
	
	public static void main(String[] args) {
		// TODO Auto-generated method stub

		
		
		ArrayList<File> allFiles = new ArrayList<File>();
		ArrayList<File> allPatientFiles = new ArrayList<File>();
		allFiles.addAll(FindFiles.listfContainName(dir, "AgentOutput"));
		for (File f: allFiles){
			if (f.isFile() && f.getName().contains("AgentOutput")){
				allPatientFiles.add(f);
			}
		}
		System.out.println(allPatientFiles);
		
		
//		for (File f: allPatientFiles){
////			String fileName= f.getAbsolutePath();
//			readinputFile(f);
//		}
		
		
		for (int i=0;i<100;i++){
//			System.out.println(allPatientFiles.get(i).getName());
			
			readInFile1(allPatientFiles.get(i).getAbsolutePath());
		}
	}
	
	
	
public static void readInFile1(String inputFile){	
		
		String title = null;
		String line = null;
		
		try {
			BufferedReader reader = new BufferedReader(new FileReader(inputFile));
			if ((line=reader.readLine())!=null){
				title=line;
			}
			
//			while((line=reader.readLine())!=null) {
//				
//				try {
//					processLine1(line);
//				} catch (Exception e) {
//					// TODO Auto-generated catch block
//					System.out.println(line);
//					System.out.println(inputFile);
//					e.printStackTrace();
//				}
//					
//			}
			if ((line=reader.readLine())!=null){
				processLine1(line);
			}
			
			reader.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		
		/**
		 * read in lines
		 */
//		System.out.println("reading done!");

	}


	public static void processLine1(String line){
		String data[]=line.split(",");
		String seed=data[11];
		System.out.print(seed+" ");
//		System.out.println(seed);
		
	};

}
