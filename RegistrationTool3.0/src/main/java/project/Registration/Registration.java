package project.Registration;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.TimeZone;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.support.ui.Select;
import project.config.*;

//the goal of this is to run the entire project in a headless browser to reduce runtime but have a separate 
//headed browser that will display progress and success/fail for each individual registration

//header for testing website: https://selfservetest.yu.edu/pls/bandaily/twbkwbis.P_WWWLogin

public class Registration {
	public static String browser;
	public static WebDriver driver;
	public static String projectLocation = System.getProperty("user.dir");	
	
	public static void main (String[] args)  {
		PropertiesFile.setProps();
		setBrowser();
		setBrowserConfig();
		login();
		addDropPageForGivenTerm();
		Date regTime = buildRegistrationTime();
		checkIfThisIsTooEarly(regTime);
		//judaicStudiesRegistration();
		//enterCRNsToWebPage();
	}
	//function to check if it is too early to register
	//	System.out.println("copies of string: " + driver.findElements(By.xpath("/html/body/div[3]/div/table/tbody/tr/td[2]/span")) );
	//Assuming the above function returns that we cannot register yet, we need a way of knowing how soon to reload the page
	//Therefore we are going to access the system clock and compare it to how much time there is from the current time setting
	//to when the registration opening is
	//	method will most likely be recursive in nature and use nested if statements to determine how long to wait before reloading.
	//use getRegistationTime() for particular user.
	
	
	
	public static void setBrowser() {
		browser = PropertiesFile.getBrowser();
	}
	
	public static void setBrowserConfig() {
		//conditional to decide which browser to use
		if (browser.contains("Firefox")) {
			System.setProperty("webdriver.gecko.driver", projectLocation + "\\lib\\seleniumjars\\ExeFiles\\geckodriver.exe");
			driver = new FirefoxDriver();
		}
		
		if (browser.contains("Chrome")) {
			System.setProperty("webdriver.chrome.driver", projectLocation + "\\lib\\seleniumjars\\ExeFiles\\chromedriver.exe");
			driver = new ChromeDriver();
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
	
	public static void addDropPageForGivenTerm () {
		//driver.findElement(By.xpath("/html/body/div[3]/table[2]/tbody/tr[2]/td[2]/a")).click();
		driver.get("http://selfserveprod.yu.edu/pls/banprd/bwskfreg.P_AltPin"); //takes to Add Drop page
	    Select termSelect = new Select(driver.findElement(By.name("term_in"))); //finds term box to select term
		String termAndSession = (PropertiesFile.getTermSession() + " " + PropertiesFile.getTermYear()); //Retrieve term from property file
		termSelect.selectByVisibleText(termAndSession); //use retrieved term to select term from drop down list
		driver.findElement(By.xpath("/html/body/div[3]/form/input")).click(); //click enter for that term
		
	}
	
	public static Date buildRegistrationTime() {
		String registrationDate = PropertiesFile.getRegistationTime();
		SimpleDateFormat registrationTimeFormat = new SimpleDateFormat("MM/dd/yyyy/HH/mm/ss");
		registrationTimeFormat.setTimeZone(TimeZone.getTimeZone("EST"));
		try {
			Date dateRegistrationDate = registrationTimeFormat.parse(registrationDate);
			return dateRegistrationDate;
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;
	}
	public static void checkIfThisIsTooEarly(Date regTime)  {
		//have to reload the page...
		//before anything else, does the web page have the error message saying that we cannot log in?
		//if yes, then calculate how soon to reload, otherwise return immediately!
		
		
		if (driver.findElements(By.xpath("/html/body/div[3]/div/table/tbody/tr/td[2]/span")).size() ==0) {//if the text appears, the length wont be zero, if it does not appear, the non existent text is length zero, in such a case registration must be open. so return
			return;
		}
		
		long millisUntilRegistrationOpens = regTime.getTime() - System.currentTimeMillis();
		if (millisUntilRegistrationOpens > 600000 ) {
			try {
				Thread.sleep(600000);
			} catch (InterruptedException e) {
				Thread.currentThread().interrupt();
				e.printStackTrace();
			}
			driver.get("http://selfserveprod.yu.edu/pls/banprd/bwskfreg.P_AltPin");	
			checkIfThisIsTooEarly(regTime);
			return;
		}
		if ((millisUntilRegistrationOpens <= 600000 ) && (millisUntilRegistrationOpens > 1000)) {
			try {
				Thread.sleep(millisUntilRegistrationOpens / 2);
			} catch (InterruptedException e) {
				Thread.currentThread().interrupt();
				e.printStackTrace();
			}
			driver.get("http://selfserveprod.yu.edu/pls/banprd/bwskfreg.P_AltPin");
			checkIfThisIsTooEarly(regTime);
			return;
		}
		if ((millisUntilRegistrationOpens <= 1000 ) && (millisUntilRegistrationOpens >= 0)) {
			try {
				Thread.sleep(200);
			} catch (InterruptedException e) {
				Thread.currentThread().interrupt();
				e.printStackTrace();
			}
			driver.get("http://selfserveprod.yu.edu/pls/banprd/bwskfreg.P_AltPin");
			checkIfThisIsTooEarly(regTime);
			return;
		}
		if ((millisUntilRegistrationOpens < 0 ) && (millisUntilRegistrationOpens > -1000)) {
			try {
				Thread.sleep(500);
			} catch (InterruptedException e) {
				Thread.currentThread().interrupt();
				e.printStackTrace();
			}
			driver.get("http://selfserveprod.yu.edu/pls/banprd/bwskfreg.P_AltPin");
			checkIfThisIsTooEarly(regTime);
			return;
		}
		if ((millisUntilRegistrationOpens <= -1000 ) && (millisUntilRegistrationOpens > -10000)) {
			try {
				Thread.sleep(1000);
			} catch (InterruptedException e) {
				Thread.currentThread().interrupt();
				e.printStackTrace();
			}
			driver.get("http://selfserveprod.yu.edu/pls/banprd/bwskfreg.P_AltPin");
			checkIfThisIsTooEarly(regTime);
			return;
		}
		if ((millisUntilRegistrationOpens <= -10000 ) && (millisUntilRegistrationOpens > -100000)) {
			try {
				Thread.sleep(5000);
			} catch (InterruptedException e) {
				Thread.currentThread().interrupt();
				e.printStackTrace();
			}
			driver.get("http://selfserveprod.yu.edu/pls/banprd/bwskfreg.P_AltPin");
			checkIfThisIsTooEarly(regTime);
			return;
		}
		if ((millisUntilRegistrationOpens <= -100000 ) && (millisUntilRegistrationOpens > -300000)) {
			try {
				Thread.sleep(10000);
			} catch (InterruptedException e) {
				Thread.currentThread().interrupt();
				e.printStackTrace();
			}
			driver.get("http://selfserveprod.yu.edu/pls/banprd/bwskfreg.P_AltPin");
			checkIfThisIsTooEarly(regTime);
			return;
		}
		if ((millisUntilRegistrationOpens <= -300000 ) && (millisUntilRegistrationOpens > -600000)) {
			try {
				Thread.sleep(300000);
			} catch (InterruptedException e) {
				Thread.currentThread().interrupt();
				e.printStackTrace();
			}
			driver.get("http://selfserveprod.yu.edu/pls/banprd/bwskfreg.P_AltPin");
			checkIfThisIsTooEarly(regTime);
			return;
		}
		if ((millisUntilRegistrationOpens <= -600000 ) && (millisUntilRegistrationOpens > -3000000)) {
			try {
				Thread.sleep(450000);
			} catch (InterruptedException e) {
				Thread.currentThread().interrupt();
				e.printStackTrace();
			}
			driver.get("http://selfserveprod.yu.edu/pls/banprd/bwskfreg.P_AltPin");
			checkIfThisIsTooEarly(regTime);
			return;
		}
		driver.get("http://selfserveprod.yu.edu/pls/banprd/bwskfreg.P_AltPin");
		checkIfThisIsTooEarly(regTime);
		return;
	}

	public static void judaicStudiesRegistration() {
		String judiacStudiesType = PropertiesFile.getJudaicSectionType();
		if (judiacStudiesType.contentEquals("YP")) {
			ShiurRegistration.register(1, driver);
		}
		if (judiacStudiesType.contentEquals("BMP")) {
			ShiurRegistration.register(2, driver);
		}
		if (judiacStudiesType.contentEquals("IBC")) {
			ShiurRegistration.register(3, driver);
		}
		if (judiacStudiesType.contentEquals("JSS")) {
			ShiurRegistration.register(4, driver);
		}
	}
	
	public static void enterCRNsToWebPage () {
		String[][] allSecularClassInfo = PropertiesFile.getClassInfo();
		int numOfClasses = allSecularClassInfo.length;
		String[] priority = {"High", "Medium", "Low"};
		for (int x = 0; x < 3; x++) {
			int inputBox = 1;
			for (int y = 0; y < numOfClasses; y++) {
				if(allSecularClassInfo[y][5].equals(priority[x])) {
					String inputXPath = "//*[@id=\"crn_id" + inputBox + "\"]";
					WebElement boxToSendCRN = driver.findElement(By.xpath(inputXPath));
					boxToSendCRN.sendKeys(allSecularClassInfo[y][4]);
					inputBox++;
				}
			}
			if (inputBox > 1) {//only click sumbit and check for errors if more than one CRN was inputted into the system
				driver.findElement(By.xpath("/html/body/div[3]/form/input[19]")).click(); 
				// ---wait list drop down box: //*[@id="waitaction_id1"]
				for (int p = 0; p < inputBox; p++) {//this for loop will, if possible, change all errors to wait list requests
					try {
						Select errorSelect = new Select(driver.findElement(By.xpath("//*[@id=\"waitaction_id" + (p + 1) + "\"]")));
						errorSelect.selectByVisibleText("Wait List");
					} catch (Exception e) {
						continue;
					}
				}
				driver.findElement(By.xpath("/html/body/div[3]/form/input[19]")).click(); 
			}
		}	
		/*	
		String [] crnsToEnter = PropertiesFile.getRegularCRNArray();
		for (int n = 0; n < crnsToEnter.length; n++) {
			String crnID = ("//*[@id=\"crn_id" + (n+1) + "\"]");
			//System.out.println(crnID);
			WebElement crnInputBox = driver.findElement(By.xpath(crnID));
			crnInputBox.sendKeys(crnsToEnter[n]);			
			//driver.findElement(By.xpath("/html/body/div[3]/form/input[19]")).click(); //allow this to submit changes
		}
		*/
	}
}