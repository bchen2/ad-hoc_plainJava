package AdhocCollaboration;

public class OutputClass {
	// static boolean agentOutput=false;
	// static boolean blackboardOutput=false;

	static boolean agentOutput = false;

	static boolean agentOutputShort = true;// this will out put tick,id,
											// taskAssigned(0 or 1), bidswon(0
											// or 1), rewards, selfgain, obs
											// gain

	static boolean agentOutputWithCap = false;// if this is true, then it will
												// output agentinfo and pending
												// the agent QualityList at the
												// end, if false then do not
												// pend the QualityList

	static boolean blackboardOutput = false;

	static boolean agentCap = false;
	static boolean taskDetail = false;
	// static boolean taskDetail=true;

	static boolean agentUsolveUlearn = false;

	// static boolean biddingDetail=true;
	static boolean biddingDetail = false;

	// static String
	// direcotry="/Volumes/My Passport for Mac/result10";//distrubution
	// static String direcotry="/Users/BinChen/Desktop/test4";
	// static String direcotry="/Volumes/My Passport for Mac/result6";//contains
	// option17
	// static String
	// direcotry="/Volumes/My Passport for Mac/25-25-25-25";//contains option17
	// 1/13/2016
	// static String
	// direcotry="/Volumes/My Passport for Mac/newTest2";//contains option17, 10
	// times obs
	// static String direcotry="/Users/BinChen/Desktop/test7";
//	static String direcotry = "/Users/BinChen/Desktop/5capjava3New";
//	static String direcotry = "./10capjava1000tick";
	static String direcotry = Parameters.getInstance().direcotry;
}
