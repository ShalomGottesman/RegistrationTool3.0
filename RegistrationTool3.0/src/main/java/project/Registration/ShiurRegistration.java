package project.Registration;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.Select;
import project.config.PropertiesFile;

public class ShiurRegistration {
	public static void register(int regType, WebDriver driver) {
		//this method should be called when the webdriver is already on the add drop class web page
		switch (regType) {
			case 1: //YP Shiur Registration method
				ypRegistration(driver);
				break;
			case 2: //BMP Shiur Registration
				bmpRegistration(driver);
				break;
			case 3: //IBC Registration
				ibcRegistration(driver);
				break;
			case 4: //JSS REgistration
				jssRegistration(driver);
				break;
		}
		return;
	}
	
	
	public static void ypRegistration(WebDriver driver) {
		if ((driver.findElements(By.xpath("/html/body/div[3]/form/center/h2/b")).size()!=0) &&
			(driver.findElement(By.xpath("/html/body/div[3]/form/center/h2/b")).getText().equals(
						"* YOU HAVE SATISFIED THE MYP REGISTRATION REQUIREMENT *"))){
		//checks if there is a message that the registration requirement has been fulfilled
			return;
		}
		String[] shiurInfo = PropertiesFile.getYpShiurInfo(); // [0]=CRN, [1]=Credits
		if ((Integer.parseInt(shiurInfo[1]) > 3) || (Integer.parseInt(shiurInfo[1]) < 0)){
			shiurInfo[1] = "0";
		}
		driver.findElement(By.xpath("//*[@id=\"crn_id1\"]")).sendKeys(shiurInfo[0]); //identify regular CRN input field, and input CRN
		driver.findElement(By.xpath("/html/body/div[3]/form/input[19]")).click(); //identify submit changes button and submit
		driver.findElement(By.xpath("/html/body/div[3]/form/input[" + (shiurInfo[1]+1) + "]")); //choose amount of shiur credit
		driver.findElement(By.xpath("/html/body/div[3]/form/input[9]")).click(); //submit amount of shiur credit
		return;				
	}
	
		
	public static void bmpRegistration(WebDriver driver) {
	}
	public static void ibcRegistration(WebDriver driver) {
		if ((driver.findElements(By.xpath("/html/body/div[3]/form/center/h2/b")).size()!=0) &&
				(driver.findElement(By.xpath("/html/body/div[3]/form/center/h2/b")).getText().equals(
							"* YOU HAVE SATISFIED THE IBC REGISTRATION REQUIREMENT *"))){
			//checks if there is a message that the registration requirement has been fulfilled
				return;
		}
		String[][] allIBCClassData = PropertiesFile.getNonYPClassInfo("IBC");
		int numOfClasses = allIBCClassData.length;
		String[] priority = {"High", "Medium", "Low"};
		for (int x = 0; x < 3; x++) {
			int inputBox = 1;
			for (int y = 0; y < numOfClasses; y++) {
				if(allIBCClassData[y][5].equals(priority[x])) {
					String inputXPath = "//*[@id=\"crn_id" + inputBox + "\"]";
					WebElement boxToSendCRN = driver.findElement(By.xpath(inputXPath));
					boxToSendCRN.sendKeys(allIBCClassData[y][4]);
					inputBox++;
				}
			}
			if (inputBox > 1) {//only click sumbit and check for errors if more than one CRN was inputted into the system
				driver.findElement(By.xpath("/html/body/div[3]/form/input[19]")).click(); 
				// ---wait list drop down box: //*[@id="waitaction_id1"]
				for (int p = 0; p < inputBox; p++) {//this for loop will, if possible, change all errors to waitlist requests
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
		//at this point all classes have been added or wait listed, now we have to make sure all classes are specifically for IBC
		int counter = 0;
		while ((driver.getTitle().equals("Add or Drop Classes")) && (counter < 15)) {
			int x = 2;
			try {
				driver.findElement(By.xpath("/html/body/div[3]/form/table[1]/tbody/tr[" + x + "]/td[7]/a")).click();
			} catch (Exception e) {
				continue;
			}
			counter++;
		}
		//should now be on page to change class attributes "Change Class Options"
		for(int x = 1; x <= numOfClasses; x++) {//start at 1 because one is the first table in the xpath
			String menuXPath = "//*[@id=\"level" + x + "_id\"]";
			try {
				Select dropDownMenu = new Select(driver.findElement(By.xpath(menuXPath)));
				dropDownMenu.selectByVisibleText("Isaac Breuer College");
			} catch (Exception e) {
				continue;
			}
		}
		driver.findElement(By.xpath("/html/body/div[3]/form/input[21]")).click();
		driver.findElement(By.xpath("/html/body/div[3]/a[1]")).click();
	}
	public static void jssRegistration(WebDriver driver) {
	}	
}

/*
 * 1. determine if shiur registration is required, if not return
 * 2. determine registration type (YP, BMP, IBC, JSS)
 * 3. find location of inputs
 * 4. input all data required
 * 5. submit and return
*/	
