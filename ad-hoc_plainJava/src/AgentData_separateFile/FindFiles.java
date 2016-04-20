package AgentData_separateFile;

import java.io.File;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class FindFiles {

	
	
	
	
	
	
	
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
	
	
	
	
	public static List<File> listfContainName(String directoryName,String name) {
	    File directory = new File(directoryName);

	    List<File> resultList = new ArrayList<File>();

	    // get all the files from a directory
	    File[] fList = directory.listFiles();
	    resultList.addAll(Arrays.asList(fList));
	    for (File file : fList) {
	        if (file.isFile() && file.getName().contains(name)) {
//	            System.out.println(file.getAbsolutePath());
	        } else if (file.isDirectory()) {
	            resultList.addAll(listfContainName(file.getAbsolutePath(),name));
	        }
	    }
	    //System.out.println(fList);
	    return resultList;
	} 
}


