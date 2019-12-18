package project.search;

import java.awt.Color;
import java.awt.Desktop;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.io.PrintStream;
import java.util.ArrayList;
import java.util.Arrays;

import javax.swing.Box;
import javax.swing.JLabel;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.phantomjs.PhantomJSDriver;
import org.openqa.selenium.support.ui.Select;

import project.GUI.GUIInput;
import project.config.*;
import project.writing.WriteToFile;

public class ClassStatsSearch
{
	public static String[][] classInfo;
	public static int classNum;
	public static String[][] classStats;
	public static String browser;
	public static WebDriver driver;
	public static String projectLocation = System.getProperty("user.dir");	
	public static File statsFile;
	final private static boolean headlessBrowser = false;
	
	public static void main (String[] args) throws Exception
	{
		getIBCClassStats();
		/*
		PropertiesFile.setProps();
		classInfo = PropertiesFile.getClassInfo();
		classNum = classInfo.length;
		
		setBrowser();
		setBrowserConfig();
		login();
		classStats = search();
		displayClassStats();
	*/
	}
	
	
	public static void runClassSearchForGUI() {
		long startTime = System.currentTimeMillis();
		PropertiesFile.setProps();
		classInfo = PropertiesFile.getClassInfo();
		classNum = classInfo.length;
		
		
		setBrowser();
		setBrowserConfig();
		login();
		classStats = search();
		try {
			WriteToFile.printClassStatsToFileV2(classInfo, classStats, classNum, startTime);
			//printClassStatsToFileV3();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}
	public static void setBrowser() {
		browser = PropertiesFile.getBrowser();
	}
	
	@SuppressWarnings("all")
	public static void setBrowserConfig() {
		String browserLocation = projectLocation + "\\RegToolData";
		if (headlessBrowser == false) {
			//conditional to decide which browser to use
			if (browser.contains("Firefox")) {
				System.setProperty("webdriver.gecko.driver", browserLocation + "\\geckodriver.exe");
				driver = new FirefoxDriver();
			}
			
			if (browser.contains("Chrome")) {
				System.setProperty("webdriver.chrome.driver", browserLocation + "\\chromedriver.exe");
				driver = new ChromeDriver();
			}
		}
		else {
			System.setProperty("phantomjs.binary.path", browserLocation + "\\phantomjs.exe");
			driver = new PhantomJSDriver();
		}
	}
	
	public static void login() {
		driver.get("http://selfserveprod.yu.edu/pls/banprd/twbkwbis.P_WWWLogin");
		
		WebElement id = driver.findElement(By.xpath("//*[@id=\"UserID\"]"));
		
		id.sendKeys(PropertiesFile.getUserID());
		
		WebElement password = driver.findElement(By.xpath("//*[@id=\"PIN\"]/input"));
		
		password.sendKeys(PropertiesFile.getUserPassword());
		
		driver.findElement(By.xpath("/html/body/div[3]/form/p/input[1]")).click(); //press enter after login credentials 
	}
	
	public static String[][] search()
	{
		String[][] classStats = new String[classNum][7];
		
		driver.get("https://selfserveprod.yu.edu/pls/banprd/bwskfcls.p_sel_crse_search");
		
		Select termSelect = new Select(driver.findElement(By.name("p_term"))); //finds term box to select term
		
		String termAndSession = (PropertiesFile.getTermSession() + " " + PropertiesFile.getTermYear()); //Retrieve term from property file
		
		termSelect.selectByVisibleText(termAndSession); //use retrieved term to select term from drop down list
		
		driver.findElement(By.xpath("/html/body/div[3]/form/input[2]")).click();
		driver.findElement(By.xpath("/html/body/div[3]/form/input[18]")).click();
		
		for (int currentClass = 0; currentClass < classNum; currentClass++)
		{
			System.out.println("regular class search, initiating class: " + currentClass);
			//classInfo[x][0] = subject (MAT)
			String[] perClassStats = new String[8];
			Select subjectSelect = new Select (driver.findElement(By.xpath("//*[@id=\"subj_id\"]")));
			subjectSelect.selectByValue(classInfo[currentClass][0]);
			
			WebElement courseSection = driver.findElement(By.xpath("//*[@id=\"crse_id\"]"));
			courseSection.clear();
			courseSection.sendKeys(classInfo[currentClass][2]);
			
			driver.findElement(By.xpath("//*[@id=\"advCourseBtnDiv\"]/input[1]")).click();
			

			int row = 3;
			boolean found = true;
			
			while(true)
			{
				if (row  == 53)
				{
					found = false;
					break;
				}
				
				try
				{
					String CRN = driver.findElement(By.xpath("/html/body/div[3]/form/table/tbody/tr[" + row + "]/td[2]/a")).getText();

					if (CRN.equals(classInfo[currentClass][4]))
					{
						break;
					}
					else
					{
						row++;
						continue;
					}
				}
				
				catch(Exception e)
				{
					row++;
					continue;
				}
			}
			
			if (!found)
			{
				System.out.println("CRN for " + classInfo[currentClass][1] + "not found");
				continue;
			}
			
			String XPathBase = "/html/body/div[3]/form/table/tbody/tr[" + row + "]/td[";

			String capacityXPath = XPathBase + "11]";
			String activeXPath = XPathBase + "12]";			
			String remainderXPath = XPathBase + "13]";
			String waitListCapacityXPath = XPathBase + "14]";
			String waitListActiveXPath = XPathBase + "15]";
			String waitListRemainderXPath = XPathBase + "16]";
			String classCredits = XPathBase + "7]";
			String professorName = XPathBase + "17]";
			
			perClassStats[0] = driver.findElement(By.xpath(capacityXPath)).getText();
			perClassStats[1] = driver.findElement(By.xpath(activeXPath)).getText();
			perClassStats[2] = driver.findElement(By.xpath(remainderXPath)).getText();
			perClassStats[3] = driver.findElement(By.xpath(waitListCapacityXPath)).getText();
			perClassStats[4] = driver.findElement(By.xpath(waitListActiveXPath)).getText();
			perClassStats[5] = driver.findElement(By.xpath(waitListRemainderXPath)).getText();
			perClassStats[6] = driver.findElement(By.xpath(classCredits)).getText();
			perClassStats[7] = driver.findElement(By.xpath(professorName)).getText();

			classStats[currentClass] = perClassStats;
			
			if (currentClass < classNum-1)
			{
				driver.navigate().back();
			}
		}
		
		driver.close();
		for (int x = 0; x < classStats.length; x++) {
			System.out.println(Arrays.toString(classStats[x]));
		}
		return classStats;
	}
	
	public static void displayClassStats()
	{
		System.out.printf("%n%-35s    %s    %s    %s    %s    %s    %s    %s", " ", "Class Capacity", "Active Reg", "Remaining", "WL Capacity", "WL Active Reg", "WL Remaining", "Taught by");
		for (int currentClass=0; currentClass < classNum; currentClass++)
		{
			System.out.printf("%n%30s: ", classInfo[currentClass][1]);
			
			for (int currentStat=0; currentStat <7 ; currentStat++) //this only prints out the numbers of the class
			{
				System.out.print("     ");
				System.out.printf("%10s", classStats[currentClass][currentStat]);
			}
			System.out.printf("          %s", classStats[currentClass][6]);//prints out teacher name
			System.out.printf("%n%30s", classInfo[currentClass][0] + " " + classInfo[currentClass][2] + " " + classInfo[currentClass][3]);
			
			if (classStats[currentClass][2].contentEquals("0")) {
				System.out.printf("%55s", "***CLASS FULL***");
			}
			if (classStats[currentClass][5].contentEquals("0")) {
				System.out.printf("%45s",  "***WAITLIST FULL***");
			}
			
			System.out.printf("%n%30s%n", "CRN: " + classInfo[currentClass][4]);
		}
	}
	
	public static void printClassStatsToFile() throws IOException {
		//un-commented code attempting to write stats directly to GUI
		File statsFile = new File(projectLocation + "\\files\\ClassStatDisplay.txt");
		statsFile.createNewFile();
		PrintStream toFile = new PrintStream(statsFile);
		String classStatsHeading = String.format("%n%-35s    %s    %s    %s    %s    %s    %s    %s", " ", "Class Capacity", "Active Reg", "Remaining", "WL Capacity", "WL Active Reg", "WL Remaining", "Taught By");
		System.out.print(classStatsHeading);
				
		JLabel classStatsHeadingLabel = new JLabel(classStatsHeading);
		JLabel[] classStatsLabels = new JLabel[(classNum * 3)];
		
		toFile.printf(classStatsHeading);
		for (int currentClass=0; currentClass < classNum; currentClass++)
		{
			String className = String.format("%n%30s: ", classInfo[currentClass][1]);
			String currentClassStatsString = className;
			toFile.print(className);
			
			for (int currentStat=0; currentStat < 7; currentStat++)
			{
				String currentStatString = String.format("     %10s", classStats[currentClass][currentStat]);
				currentClassStatsString = currentClassStatsString + currentStatString;
				toFile.print(currentStatString);
			}
			
			classStatsLabels[((currentClass + 1) * 3) - 3] = new JLabel(currentClassStatsString);
			System.out.print(currentClassStatsString);
			
			String currentClassInfoString = String.format("%n%30s", classInfo[currentClass][0] + " " + classInfo[currentClass][2] + " " + classInfo[currentClass][3]);
			toFile.print(currentClassInfoString);
			
			
			if (classStats[currentClass][2].contentEquals("0")) {
				String classFullWarning = String.format("%55s", "***CLASS FULL***");
				currentClassInfoString = currentClassInfoString + classFullWarning;
				toFile.print(classFullWarning);
			}
			if (classStats[currentClass][5].contentEquals("0")) {
				String waitListFullWarning = String.format("%45s",  "***WAITLIST FULL***");
				currentClassInfoString = currentClassInfoString + waitListFullWarning;
				toFile.print(waitListFullWarning);
			}
			
			classStatsLabels[((currentClass + 1) * 3 - 2)] = new JLabel(currentClassInfoString); 
			System.out.print(currentClassInfoString);
			
			String currentClassCRNString = String.format("%n%30s%n", "CRN: " + classInfo[currentClass][4]);
			toFile.print(currentClassCRNString);
			
			classStatsLabels[((currentClass + 1) * 3) - 1] = new JLabel(currentClassCRNString);
			System.out.print(currentClassCRNString);
		}
		
		GUIInput.centerPanelBox = Box.createVerticalBox();
		
		classStatsHeadingLabel.setForeground(Color.CYAN);
		GUIInput.centerPanelBox.add(classStatsHeadingLabel);
		
		for (JLabel currentClassLabel: classStatsLabels)
		{
			currentClassLabel.setForeground(Color.white);
			GUIInput.centerPanelBox.add(currentClassLabel);
		}
		
		GUIInput.availableStatistics = true;
		
		GUIInput.mainMenuFrame.dispose();
		GUIInput.main(null);
	
		/*try {
			Desktop.getDesktop().open(statsFile);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}*/
		
		toFile.close();
	}
	
	public static void printClassStatsToFileV3() throws IOException {
		statsFile = new File(projectLocation + "\\files\\ClassStatDisplay.txt");
		BufferedReader reader = null;
		reader = new BufferedReader(new FileReader(statsFile));
		ArrayList<JLabel> allRows = new ArrayList<JLabel>();
		JLabel labelX = null;
		int classes = Integer.parseInt(PropertiesFile.getNumOfClasses());
		int totalRows = 1 + (classes *3);
		for (int x = 0; x < totalRows; x++) {
			labelX = new JLabel(reader.readLine());
			labelX.setForeground(Color.white);
			allRows.add(labelX);
		}		
		reader.close();
		Box allRowsLinedUp = Box.createVerticalBox();
		for (JLabel labeInArrayLst: allRows) {
			allRowsLinedUp.add(labeInArrayLst);
		}
		GUIInput.centerPanelBox = allRowsLinedUp;
		GUIInput.availableStatistics = true;
		
		GUIInput.mainMenuFrame.dispose();
		GUIInput.main(null);
	}
	
	public static void DisplayStatFile() throws IOException {
		statsFile = new File(projectLocation + "\\RegToolData\\ClassStatDisplay.txt");
		Desktop.getDesktop().open(statsFile);
	}
	
	public static String[] getYPShiurStats() {
		String[] shiurInfo = PropertiesFile.getYPShiurInfo();
		PropertiesFile.setProps();		
		setBrowser();
		setBrowserConfig();
		login();
		driver.get("https://selfserveprod.yu.edu/pls/banprd/bwskfcls.p_sel_crse_search");
		Select termSelect = new Select(driver.findElement(By.name("p_term"))); //finds term box to select term
		String termAndSession = (PropertiesFile.getTermSession() + " " + PropertiesFile.getTermYear()); //Retrieve term from property file
		termSelect.selectByVisibleText(termAndSession); //use retrieved term to select term from drop down list
		driver.findElement(By.xpath("/html/body/div[3]/form/input[2]")).click();
		driver.findElement(By.xpath("/html/body/div[3]/form/input[18]")).click();
		Select subjectSelect = new Select (driver.findElement(By.xpath("//*[@id=\"subj_id\"]")));//identify Subject (TAL, MAT etc) menu
		try {
			subjectSelect.deselectByValue("%");
		} catch (Exception e) {}
		subjectSelect.selectByValue("TAL");
		driver.findElement(By.xpath("//*[@id=\"advCourseBtnDiv\"]/input[1]")).click();
		int xMatch = 0;
		for(int x = 3; x < 200; x++) {
			System.out.println("rolling throug shiur rows, at row: " + x);
			//    /html/body/div[3]/form/table/tbody/tr[3]/td[10]
			if (driver.findElement(By.xpath("/html/body/div[3]/form/table/tbody/tr[" + x + "]/td[10]")).getText().equals("09:00 am-12:00 pm")) {
				System.out.println("this line matches the 9 to 12 text");
				try {
					String currentCRN = (driver.findElement(By.xpath("/html/body/div[3]/form/table/tbody/tr[" + x + "]/td[2]/a")).getText() + "");//the extra quotes prevents the result from being null
					if (currentCRN.equals(shiurInfo[1])) {
						System.out.println("x match at: " + x);
						xMatch = x;
						break;
					}
				} catch (org.openqa.selenium.NoSuchElementException e) {
					continue;
				}
			}
		}
		String shiurInfoXPathBase = "/html/body/div[3]/form/table/tbody/tr[" + xMatch + "]/td[";
		
		String shiurCourseNumxpath = shiurInfoXPathBase + "4]";
		String shiurTitle = shiurInfoXPathBase + "8]";
		String shiurCapacityxpath = shiurInfoXPathBase + "11]";
		String shiurActivexpath = shiurInfoXPathBase + "12]";
		String shiurRemaining = shiurInfoXPathBase + "13]";
		String shiurWaitlistxpath = shiurInfoXPathBase + "14]";
		String shiurWaitlistActive = shiurInfoXPathBase + "15]";
		String shiurWaitlistReainder = shiurInfoXPathBase + "16]";
		String shiurRebbexpath = shiurInfoXPathBase + "17]";
		String shiurSection = shiurInfoXPathBase + "3]";
		
		String[] pulledShiurInfo = new String[] {driver.findElement(By.xpath(shiurCourseNumxpath)).getText(),
												 driver.findElement(By.xpath(shiurTitle)).getText(),
												 driver.findElement(By.xpath(shiurCapacityxpath)).getText(),
												 driver.findElement(By.xpath(shiurActivexpath)).getText(),
												 driver.findElement(By.xpath(shiurRemaining)).getText(),
												 driver.findElement(By.xpath(shiurWaitlistxpath)).getText(),
												 driver.findElement(By.xpath(shiurWaitlistActive)).getText(),
												 driver.findElement(By.xpath(shiurWaitlistReainder)).getText(),
												 driver.findElement(By.xpath(shiurRebbexpath)).getText(),
												 driver.findElement(By.xpath(shiurSection)).getText()};
		driver.close();
		System.out.println(Arrays.toString(pulledShiurInfo));
		return pulledShiurInfo;
	}
	
	public static String[][] getIBCClassStats() {
		String[][] allIBCClassInfo = PropertiesFile.getNonYPClassInfo("IBC");
		String[][] allIBCPulledStats = new String[allIBCClassInfo.length][8];
		/*
		 * IBCClassInfo[x][0] = propUser.getProperty("IBCClass" + (x+1) + "ClassSection");
			IBCClassInfo[x][1] = propUser.getProperty("IBCClass" + (x+1) + "Title");
			IBCClassInfo[x][2] = propUser.getProperty("IBCClass" + (x+1) + "CourseNum");
			IBCClassInfo[x][3] = propUser.getProperty("IBCClass" + (x+1) + "SectNum");
			IBCClassInfo[x][4] = propUser.getProperty("IBCClass" + (x+1) + "CRN");
			IBCClassInfo[x][5] = propUser.getProperty("IBCClass" + (x+1) + "Priority");
			*/
		PropertiesFile.setProps();		
		setBrowser();
		setBrowserConfig();
		login();
		driver.get("https://selfserveprod.yu.edu/pls/banprd/bwskfcls.p_sel_crse_search");
		Select termSelect = new Select(driver.findElement(By.name("p_term"))); //finds term box to select term
		String termAndSession = (PropertiesFile.getTermSession() + " " + PropertiesFile.getTermYear()); //Retrieve term from property file
		termSelect.selectByVisibleText(termAndSession); //use retrieved term to select term from drop down list
		driver.findElement(By.xpath("/html/body/div[3]/form/input[2]")).click();
		driver.findElement(By.xpath("/html/body/div[3]/form/input[18]")).click();
		for (int x = 0; x < allIBCClassInfo.length; x++) {
			WebElement courseSection = driver.findElement(By.xpath("//*[@id=\"crse_id\"]"));
			courseSection.clear();
			courseSection.sendKeys(allIBCClassInfo[x][2]);
			driver.findElement(By.xpath("//*[@id=\"advCourseBtnDiv\"]/input[1]")).click();
			
			int row = 0;
			for (int n = 3; n < 200; n++) {
				try {
					String tempCRN = driver.findElement(By.xpath("/html/body/div[3]/form/table/tbody/tr[" + n + "]/td[2]/a")).getText();
					if (tempCRN.equals(allIBCClassInfo[x][4])) {
						row = n;
						break;
					}
				} catch (Exception e) {
					continue;
				}	
			}
			String xPathBase = "/html/body/div[3]/form/table/tbody/tr[" + row + "]/td[";
			allIBCPulledStats[x][0] = driver.findElement(By.xpath(xPathBase + "7]")).getText(); //credits
			allIBCPulledStats[x][1] = driver.findElement(By.xpath(xPathBase + "11]")).getText();//capacity
			allIBCPulledStats[x][2] = driver.findElement(By.xpath(xPathBase + "12]")).getText();//active
			allIBCPulledStats[x][3] = driver.findElement(By.xpath(xPathBase + "13]")).getText();//remainder
			allIBCPulledStats[x][4] = driver.findElement(By.xpath(xPathBase + "14]")).getText();//WL Capacity
			allIBCPulledStats[x][5] = driver.findElement(By.xpath(xPathBase + "15]")).getText();//WL Active
			allIBCPulledStats[x][6] = driver.findElement(By.xpath(xPathBase + "16]")).getText();//WL Remainder
			allIBCPulledStats[x][7] = driver.findElement(By.xpath(xPathBase + "17]")).getText();//Professor
			
			driver.navigate().back();
		}
		driver.close();
		return allIBCPulledStats;
	}
	
	public static String[][] getBMPClassStats() {
		String[][] allBMPClassInfo = PropertiesFile.getNonYPClassInfo("BMP");
		String[][] allBMPPulledStats = new String[allBMPClassInfo.length][8];
		/*
		 * BMPClassInfo[x][0] = propUser.getProperty("BMPClass" + (x+1) + "ClassSection");
			BMPClassInfo[x][1] = propUser.getProperty("BMPClass" + (x+1) + "Title");
			BMPClassInfo[x][2] = propUser.getProperty("BMPClass" + (x+1) + "CourseNum");
			BMPClassInfo[x][3] = propUser.getProperty("BMPClass" + (x+1) + "SectNum");
			BMPClassInfo[x][4] = propUser.getProperty("BMPClass" + (x+1) + "CRN");
			BMPClassInfo[x][5] = propUser.getProperty("BMPClass" + (x+1) + "Priority");
			*/
		PropertiesFile.setProps();		
		setBrowser();
		setBrowserConfig();
		login();
		driver.get("https://selfserveprod.yu.edu/pls/banprd/bwskfcls.p_sel_crse_search");
		Select termSelect = new Select(driver.findElement(By.name("p_term"))); //finds term box to select term
		String termAndSession = (PropertiesFile.getTermSession() + " " + PropertiesFile.getTermYear()); //Retrieve term from property file
		termSelect.selectByVisibleText(termAndSession); //use retrieved term to select term from drop down list
		driver.findElement(By.xpath("/html/body/div[3]/form/input[2]")).click();
		driver.findElement(By.xpath("/html/body/div[3]/form/input[18]")).click();
		for (int x = 0; x < allBMPClassInfo.length; x++) {
			WebElement courseSection = driver.findElement(By.xpath("//*[@id=\"crse_id\"]"));
			courseSection.clear();
			courseSection.sendKeys(allBMPClassInfo[x][2]);
			driver.findElement(By.xpath("//*[@id=\"advCourseBtnDiv\"]/input[1]")).click();
			
			int row = 0;
			for (int n = 3; n < 200; n++) {
				try {
					String tempCRN = driver.findElement(By.xpath("/html/body/div[3]/form/table/tbody/tr[" + n + "]/td[2]/a")).getText();
					if (tempCRN.equals(allBMPClassInfo[x][4])) {
						row = n;
						break;
					}
				} catch (Exception e) {
					continue;
				}	
			}
			String xPathBase = "/html/body/div[3]/form/table/tbody/tr[" + row + "]/td[";
			allBMPPulledStats[x][0] = driver.findElement(By.xpath(xPathBase + "7]")).getText(); //credits
			allBMPPulledStats[x][1] = driver.findElement(By.xpath(xPathBase + "11]")).getText();//capacity
			allBMPPulledStats[x][2] = driver.findElement(By.xpath(xPathBase + "12]")).getText();//active
			allBMPPulledStats[x][3] = driver.findElement(By.xpath(xPathBase + "13]")).getText();//remainder
			allBMPPulledStats[x][4] = driver.findElement(By.xpath(xPathBase + "14]")).getText();//WL Capacity
			allBMPPulledStats[x][5] = driver.findElement(By.xpath(xPathBase + "15]")).getText();//WL Active
			allBMPPulledStats[x][6] = driver.findElement(By.xpath(xPathBase + "16]")).getText();//WL Remainder
			allBMPPulledStats[x][7] = driver.findElement(By.xpath(xPathBase + "17]")).getText();//Professor
			
			driver.navigate().back();
		}
		driver.close();
		return allBMPPulledStats;
	}
	
	public static String[][] getJSSClassStats() {
		String[][] allJSSClassInfo = PropertiesFile.getNonYPClassInfo("JSS");
		String[][] allJSSPulledStats = new String[allJSSClassInfo.length][8];
		/*
		 * JSSClassInfo[x][0] = propUser.getProperty("JSSClass" + (x+1) + "ClassSection");
			JSSClassInfo[x][1] = propUser.getProperty("JSSClass" + (x+1) + "Title");
			JSSClassInfo[x][2] = propUser.getProperty("JSSClass" + (x+1) + "CourseNum");
			JSSClassInfo[x][3] = propUser.getProperty("JSSClass" + (x+1) + "SectNum");
			JSSClassInfo[x][4] = propUser.getProperty("JSSClass" + (x+1) + "CRN");
			JSSClassInfo[x][5] = propUser.getProperty("JSSClass" + (x+1) + "Priority");
			*/
		PropertiesFile.setProps();		
		setBrowser();
		setBrowserConfig();
		login();
		driver.get("https://selfserveprod.yu.edu/pls/banprd/bwskfcls.p_sel_crse_search");
		Select termSelect = new Select(driver.findElement(By.name("p_term"))); //finds term box to select term
		String termAndSession = (PropertiesFile.getTermSession() + " " + PropertiesFile.getTermYear()); //Retrieve term from property file
		termSelect.selectByVisibleText(termAndSession); //use retrieved term to select term from drop down list
		driver.findElement(By.xpath("/html/body/div[3]/form/input[2]")).click();
		driver.findElement(By.xpath("/html/body/div[3]/form/input[18]")).click();
		for (int x = 0; x < allJSSClassInfo.length; x++) {
			WebElement courseSection = driver.findElement(By.xpath("//*[@id=\"crse_id\"]"));
			courseSection.clear();
			courseSection.sendKeys(allJSSClassInfo[x][2]);
			driver.findElement(By.xpath("//*[@id=\"advCourseBtnDiv\"]/input[1]")).click();
			
			int row = 0;
			for (int n = 3; n < 200; n++) {
				try {
					String tempCRN = driver.findElement(By.xpath("/html/body/div[3]/form/table/tbody/tr[" + n + "]/td[2]/a")).getText();
					if (tempCRN.equals(allJSSClassInfo[x][4])) {
						row = n;
						break;
					}
				} catch (Exception e) {
					continue;
				}	
			}
			String xPathBase = "/html/body/div[3]/form/table/tbody/tr[" + row + "]/td[";
			allJSSPulledStats[x][0] = driver.findElement(By.xpath(xPathBase + "7]")).getText(); //credits
			allJSSPulledStats[x][1] = driver.findElement(By.xpath(xPathBase + "11]")).getText();//capacity
			allJSSPulledStats[x][2] = driver.findElement(By.xpath(xPathBase + "12]")).getText();//active
			allJSSPulledStats[x][3] = driver.findElement(By.xpath(xPathBase + "13]")).getText();//remainder
			allJSSPulledStats[x][4] = driver.findElement(By.xpath(xPathBase + "14]")).getText();//WL Capacity
			allJSSPulledStats[x][5] = driver.findElement(By.xpath(xPathBase + "15]")).getText();//WL Active
			allJSSPulledStats[x][6] = driver.findElement(By.xpath(xPathBase + "16]")).getText();//WL Remainder
			allJSSPulledStats[x][7] = driver.findElement(By.xpath(xPathBase + "17]")).getText();//Professor
			
			driver.navigate().back();
		}
		driver.close();
		return allJSSPulledStats;
	}
}
	
