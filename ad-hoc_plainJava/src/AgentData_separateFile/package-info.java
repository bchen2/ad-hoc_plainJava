/**

 *  
 *  1. run NormalizePerTick to find the normalized value, it produce AgentSummary and TaskSummary, both file only 
 *  have 75 lines. 1 configuration takes 1 line.
 *  
 *  2. run graph_AgentNormalized1.java or graph_AgentNormalized_WithOption17 (if want to graph option17) in ad-hoc Graph project to make tables
 *  
 *  if want collabrator info then run FindCollabrators.java (this can be combined in NormalizePerTick)
 * then run graph_AgentCollabrator.java to get the table
 * 
 *  
 *  
 *  
 *  Other useage:
 *  ---- run AgentCumulativeStats.java to get agents coumulative stats at every tick 
 *  ---- run AgentCumulativeStatsForAgentsStayAllTicks.java to get only the agents stayed for certain ticks in the simulation and find their stats at every tick
 *  ---- Agent CumulativeStatsWithOptionDistrubution.java ,  this is used for we mix agents options in one simulation, then it finds the stats of each agents optionTypes and find their stats at every tick
 *  ---- NormalizPerTick_Distrubution.java,    this is used for this is used for we mix agents options in one simulation, and it finds agents stats according to their optionTypes, then we can 
 *  		use  graph_AgentNormalized_WithOption17.java to put the stats in table
 *  
 */
/**
 * @author BinChen
 *Date:11/18/2015 
 *this is used for AAMAS2016 paper
 */
package AgentData_separateFile;