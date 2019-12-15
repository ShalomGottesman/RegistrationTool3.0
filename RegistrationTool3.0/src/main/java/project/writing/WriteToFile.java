package project.writing;

import java.awt.Desktop;
import java.io.File;
import java.io.IOException;
import java.io.PrintStream;
import java.text.SimpleDateFormat;
import java.util.Date;

import project.config.PropertiesFile;
import project.search.ClassStatsSearch;

public class WriteToFile {
	public static void printClassStatsToFileV2(String[][] classInfo, String[][] classStats, int classNum, long startTime) throws IOException{ //this method just prints to file, and then display file
		PropertiesFile.setProps();
		String name = PropertiesFile.getUserName();
		String session = PropertiesFile.getTermSession();
		String year = PropertiesFile.getTermYear();
		File statsFile = new File(System.getProperty("user.dir") + "\\RegToolData\\ClassStatDisplay " + name +"_"+ session +"_"+ year + ".txt");
		statsFile.createNewFile();
		PrintStream toFile = new PrintStream(statsFile);
		String timeStamp = new SimpleDateFormat("yyyy.MM.dd.HH.mm.ss").format(new Date());
		toFile.println("            yyyy/mm/dd/hh/mm/ss");
		toFile.println("Data as of: " + timeStamp);
		printToFileJudaicInfo(toFile);
		toFile.println("***");
		toFile.print("Secular Studies:");
		String classStatsHeading = String.format("%n%-35s    %s    %s    %s    %s    %s    %s    %s    %s", " ", "Class Capacity", "Active Reg", "Remaining", "WL Capacity", "WL Active Reg", "WL Remaining", "Credits",  "Taught By");
		toFile.print(classStatsHeading);
		for (int currentClass=0; currentClass < classNum; currentClass++)
		{
			toFile.printf("%n%30s: ", classInfo[currentClass][1]);
			
			for (int currentStat=0; currentStat <6 ; currentStat++) //this only prints out the numbers of the class
			{
				toFile.print("     ");
				toFile.printf("%10s", classStats[currentClass][currentStat]);
			}
			toFile.printf("          %s       %s", classStats[currentClass][6], classStats[currentClass][7]);//prints out credits and teacher name
			toFile.printf("%n%30s", classInfo[currentClass][0] + " " + classInfo[currentClass][2] + " " + classInfo[currentClass][3]);
			
			if (classStats[currentClass][2].contentEquals("0")) {
				toFile.printf("%55s", "***CLASS FULL***");
			}
			if (classStats[currentClass][5].contentEquals("0")) {
				toFile.printf("%45s",  "***WAITLIST FULL***");
			}
			
			toFile.printf("%n%30s%n", "CRN: " + classInfo[currentClass][4]);
			toFile.printf("%30s%n%n", "Priority: " + classInfo[currentClass][5]);
		}
		double PriorityHighSecularClassCredits = 0;
		double PriorityMedSecularClassCredits = 0;
		double PriorityLowSecularClassCredits = 0;
		for (int x = 0; x < classNum; x++) {
			if(classInfo[x][5].equals("High")) {
				PriorityHighSecularClassCredits += Double.parseDouble(classStats[x][6]);
			}
			if(classInfo[x][5].equals("Medium")) {
				PriorityMedSecularClassCredits += Double.parseDouble(classStats[x][6]);
			}
			if(classInfo[x][5].equals("Low")) {
				PriorityLowSecularClassCredits += Double.parseDouble(classStats[x][6]);
			}
		}
		toFile.printf("%n%n%s%n", "***");
		toFile.printf("%s%n", "Total Credits from Secular Classes- High Priority: " + PriorityHighSecularClassCredits);
		toFile.printf("%s%n", "Total Credits from Secular Classes-  Med Priority: " + PriorityMedSecularClassCredits);
		toFile.printf("%s%n", "Total Credits from Secular Classes-  Low Priority: " + PriorityLowSecularClassCredits);
		
		
		for (int x = 0; x < (classNum - 1); x++) {
			for (int y = (x+1); y < classNum; y++) {
				if ((x != y) && (classInfo[x][3].equals(classInfo[y][3]))) {
					
					toFile.println("***Warning, classes " + (x+1) + " and " + (y+1) + " have conflicting timeslots!***");
				}
			}
		}
		long endTime = System.currentTimeMillis();
		toFile.println("***");
		toFile.println("This process took approximatly: " + ((endTime - startTime) / 1000) + " seconds");
		toFile.close();
		Desktop.getDesktop().open(statsFile);
	}
	
	
	
	public static void printToFileJudaicInfo(PrintStream toFile) {
		String judaicStudiesType = PropertiesFile.getJudaicSectionType();
		toFile.printf("%n%s", "Judaic Studies: Section- " + judaicStudiesType);
		if (judaicStudiesType.equals("YP")) {
			String[] shiurInfo = PropertiesFile.getYPShiurInfo();
			String[] shiurStats  = ClassStatsSearch.getYPShiurStats();
			//[0]=Course Number
			//[1]=Shiur Title
			//[2]=Shiur Capacity
			//[3]=Shiur Active
			//[4]=Shiur Remainder
			//[5]=Shiur Wait list Capacity
			//[6]=Shiur Wait list Active
			//[7]=Shiur Wait list Remaining
			//[8]=Shiur Rebbe
			//[9]=Shiur Section (TAL)
			
			String classStatsHeading = String.format("%n%-35s    %s    %s    %s    %s    %s    %s    %s", " ", "Class Capacity", "Active Reg", "Remaining", "WL Capacity", "WL Active Reg", "WL Remaining", "Taught By");
			toFile.print(classStatsHeading);
			toFile.printf("%n%30s", shiurStats[1]);
			
			toFile.printf("               %-3s            %-3s             %-3s           %-3s             %-3s            %-3s", shiurStats[2], shiurStats[3], shiurStats[4], shiurStats[5], shiurStats[6], shiurStats[6]);
			toFile.printf("         %s%n", shiurStats[8]);
			toFile.printf("%30s", shiurStats[9] + " " + shiurStats[0]);
			if (shiurStats[4].contentEquals("0")) {
				toFile.printf("%55s", "***CLASS FULL***");
				if (shiurStats[7].contentEquals("0")) {
					toFile.printf("%45s",  "***WAITLIST FULL***");
				}
			}
			toFile.printf("%n%30s%n", "CRN: " + shiurInfo[1]);
			toFile.printf("%30s%n", "Credits: " + shiurInfo[0]);
			toFile.println("");
		}
		
		if (judaicStudiesType.equals("BMP")) {
			String[][] allBMPClassInfo = PropertiesFile.getNonYPClassInfo("BMP");
			/*
			[x][0] = propUser.getProperty("BMPClass" + (x+1) + "ClassSection");
			[x][1] = propUser.getProperty("BMPClass" + (x+1) + "Title");
			[x][2] = propUser.getProperty("BMPClass" + (x+1) + "CourseNum");
			[x][3] = propUser.getProperty("BMPClass" + (x+1) + "SectNum");
			[x][4] = propUser.getProperty("BMPClass" + (x+1) + "CRN");
			[x][5] = propUser.getProperty("BMPClass" + (x+1) + "Priority");
			 */
			String[][] allBMPPulledStats = ClassStatsSearch.getBMPClassStats();
			//[x][0] =credits
			//[x][1] =capacity
			//[x][2] =active
			//[x][3] =remainder
			//[x][4] =WL Capacity
			//[x][5] =WL Active
			//[x][6] =WL Remainder
			//[x][7] =Professor
			String classStatsHeading = String.format("%n%-35s    %s    %s    %s    %s    %s    %s    %s    %s", " ", "Class Capacity", "Active Reg", "Remaining", "WL Capacity", "WL Active Reg", "WL Remaining", "Credits", "Taught By");
			toFile.print(classStatsHeading);
			for(int x = 0; x < allBMPPulledStats.length; x++) {
				toFile.printf("%n%30s: ", allBMPClassInfo[x][1]);
				for(int y = 1; y < 7; y++) {
					toFile.print("     ");
					toFile.printf("%10s", allBMPPulledStats[x][y]);
				}
				toFile.printf("          %s       %s", allBMPPulledStats[x][0], allBMPPulledStats[x][7]);//prints out credits and teacher name
				toFile.printf("%n%30s", allBMPClassInfo[x][0] + " " + allBMPClassInfo[x][2] + " " + allBMPClassInfo[x][3]);		
				if (allBMPPulledStats[x][3].contentEquals("0")) {
					toFile.printf("%55s", "***CLASS FULL***");
					if (allBMPPulledStats[x][6].contentEquals("0")) {
						toFile.printf("%45s",  "***WAITLIST FULL***");
					}
				}
				
				toFile.printf("%n%30s%n", "CRN: " + allBMPClassInfo[x][4]);
				toFile.printf("%30s%n%n", "Priority: " + allBMPClassInfo[x][5]);
			}
			double PriorityHighBMPClassCredits = 0;
			double PriorityMedBMPClassCredits = 0;
			double PriorityLowBMPClassCredits = 0;
			for (int x = 0; x < allBMPClassInfo.length; x++) {
				if(allBMPClassInfo[x][5].equals("High")) {
					PriorityHighBMPClassCredits += Double.parseDouble(allBMPPulledStats[x][0]);
				}
				if(allBMPClassInfo[x][5].equals("Medium")) {
					PriorityMedBMPClassCredits += Double.parseDouble(allBMPPulledStats[x][0]);
				}
				if(allBMPClassInfo[x][5].equals("Low")) {
					PriorityLowBMPClassCredits += Double.parseDouble(allBMPPulledStats[x][0]);
				}
			}
			toFile.printf("%n%n%s%n", "***");
			toFile.printf("%s%n", "Total Credits from Secular Classes- High Priority: " + PriorityHighBMPClassCredits);
			toFile.printf("%s%n", "Total Credits from Secular Classes-  Med Priority: " + PriorityMedBMPClassCredits);
			toFile.printf("%s%n", "Total Credits from Secular Classes-  Low Priority: " + PriorityLowBMPClassCredits);
			toFile.printf("%s%n%n", "***");
		}
		if (judaicStudiesType.equals("IBC")) {
			String[][] allIBCClassInfo = PropertiesFile.getNonYPClassInfo("IBC");
			/*
			[x][0] = propUser.getProperty("IBCClass" + (x+1) + "ClassSection");
			[x][1] = propUser.getProperty("IBCClass" + (x+1) + "Title");
			[x][2] = propUser.getProperty("IBCClass" + (x+1) + "CourseNum");
			[x][3] = propUser.getProperty("IBCClass" + (x+1) + "SectNum");
			[x][4] = propUser.getProperty("IBCClass" + (x+1) + "CRN");
			[x][5] = propUser.getProperty("IBCClass" + (x+1) + "Priority");
			 */
			String[][] allIBCPulledStats = ClassStatsSearch.getIBCClassStats();
			//[x][0] =credits
			//[x][1] =capacity
			//[x][2] =active
			//[x][3] =remainder
			//[x][4] =WL Capacity
			//[x][5] =WL Active
			//[x][6] =WL Remainder
			//[x][7] =Professor
			String classStatsHeading = String.format("%n%-35s    %s    %s    %s    %s    %s    %s    %s    %s", " ", "Class Capacity", "Active Reg", "Remaining", "WL Capacity", "WL Active Reg", "WL Remaining", "Credits", "Taught By");
			toFile.print(classStatsHeading);
			for(int x = 0; x < allIBCPulledStats.length; x++) {
				toFile.printf("%n%30s: ", allIBCClassInfo[x][1]);
				for(int y = 1; y < 7; y++) {
					toFile.print("     ");
					toFile.printf("%10s", allIBCPulledStats[x][y]);
				}
				toFile.printf("          %s       %s", allIBCPulledStats[x][0], allIBCPulledStats[x][7]);//prints out credits and teacher name
				toFile.printf("%n%30s", allIBCClassInfo[x][0] + " " + allIBCClassInfo[x][2] + " " + allIBCClassInfo[x][3]);		
				if (allIBCPulledStats[x][3].contentEquals("0")) {
					toFile.printf("%55s", "***CLASS FULL***");
					if (allIBCPulledStats[x][6].contentEquals("0")) {
						toFile.printf("%45s",  "***WAITLIST FULL***");
					}
				}
				
				toFile.printf("%n%30s%n", "CRN: " + allIBCClassInfo[x][4]);
				toFile.printf("%30s%n%n", "Priority: " + allIBCClassInfo[x][5]);
			}
			double PriorityHighIBCClassCredits = 0;
			double PriorityMedIBCClassCredits = 0;
			double PriorityLowIBCClassCredits = 0;
			for (int x = 0; x < allIBCClassInfo.length; x++) {
				if(allIBCClassInfo[x][5].equals("High")) {
					PriorityHighIBCClassCredits += Double.parseDouble(allIBCPulledStats[x][0]);
				}
				if(allIBCClassInfo[x][5].equals("Medium")) {
					PriorityMedIBCClassCredits += Double.parseDouble(allIBCPulledStats[x][0]);
				}
				if(allIBCClassInfo[x][5].equals("Low")) {
					PriorityLowIBCClassCredits += Double.parseDouble(allIBCPulledStats[x][0]);
				}
			}
			toFile.printf("%n%n%s%n", "***");
			toFile.printf("%s%n", "Total Credits from Secular Classes- High Priority: " + PriorityHighIBCClassCredits);
			toFile.printf("%s%n", "Total Credits from Secular Classes-  Med Priority: " + PriorityMedIBCClassCredits);
			toFile.printf("%s%n", "Total Credits from Secular Classes-  Low Priority: " + PriorityLowIBCClassCredits);
			toFile.printf("%s%n%n", "***");
		}
		if (judaicStudiesType.equals("JSS")) {
			String[][] allJSSClassInfo = PropertiesFile.getNonYPClassInfo("JSS");
			/*
			[x][0] = propUser.getProperty("JSSClass" + (x+1) + "ClassSection");
			[x][1] = propUser.getProperty("JSSClass" + (x+1) + "Title");
			[x][2] = propUser.getProperty("JSSClass" + (x+1) + "CourseNum");
			[x][3] = propUser.getProperty("JSSClass" + (x+1) + "SectNum");
			[x][4] = propUser.getProperty("JSSClass" + (x+1) + "CRN");
			[x][5] = propUser.getProperty("JSSClass" + (x+1) + "Priority");
			 */
			String[][] allJSSPulledStats = ClassStatsSearch.getJSSClassStats();
			//[x][0] =credits
			//[x][1] =capacity
			//[x][2] =active
			//[x][3] =remainder
			//[x][4] =WL Capacity
			//[x][5] =WL Active
			//[x][6] =WL Remainder
			//[x][7] =Professor
			String classStatsHeading = String.format("%n%-35s    %s    %s    %s    %s    %s    %s    %s    %s", " ", "Class Capacity", "Active Reg", "Remaining", "WL Capacity", "WL Active Reg", "WL Remaining", "Credits", "Taught By");
			toFile.print(classStatsHeading);
			for(int x = 0; x < allJSSPulledStats.length; x++) {
				toFile.printf("%n%30s: ", allJSSClassInfo[x][1]);
				for(int y = 1; y < 7; y++) {
					toFile.print("     ");
					toFile.printf("%10s", allJSSPulledStats[x][y]);
				}
				toFile.printf("          %s       %s", allJSSPulledStats[x][0], allJSSPulledStats[x][7]);//prints out credits and teacher name
				toFile.printf("%n%30s", allJSSClassInfo[x][0] + " " + allJSSClassInfo[x][2] + " " + allJSSClassInfo[x][3]);		
				if (allJSSPulledStats[x][3].contentEquals("0")) {
					toFile.printf("%55s", "***CLASS FULL***");
					if (allJSSPulledStats[x][6].contentEquals("0")) {
						toFile.printf("%45s",  "***WAITLIST FULL***");
					}
				}
				
				toFile.printf("%n%30s%n", "CRN: " + allJSSClassInfo[x][4]);
				toFile.printf("%30s%n%n", "Priority: " + allJSSClassInfo[x][5]);
			}
			double PriorityHighJSSClassCredits = 0;
			double PriorityMedJSSClassCredits = 0;
			double PriorityLowJSSClassCredits = 0;
			for (int x = 0; x < allJSSClassInfo.length; x++) {
				if(allJSSClassInfo[x][5].equals("High")) {
					PriorityHighJSSClassCredits += Double.parseDouble(allJSSPulledStats[x][0]);
				}
				if(allJSSClassInfo[x][5].equals("Medium")) {
					PriorityMedJSSClassCredits += Double.parseDouble(allJSSPulledStats[x][0]);
				}
				if(allJSSClassInfo[x][5].equals("Low")) {
					PriorityLowJSSClassCredits += Double.parseDouble(allJSSPulledStats[x][0]);
				}
			}
			toFile.printf("%n%n%s%n", "***");
			toFile.printf("%s%n", "Total Credits from Secular Classes- High Priority: " + PriorityHighJSSClassCredits);
			toFile.printf("%s%n", "Total Credits from Secular Classes-  Med Priority: " + PriorityMedJSSClassCredits);
			toFile.printf("%s%n", "Total Credits from Secular Classes-  Low Priority: " + PriorityLowJSSClassCredits);
			toFile.printf("%s%n%n", "***");
		}
	}
}
