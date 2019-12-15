package project.ButtonConfiguration;

import project.config.PropertiesFile;
import project.writing.UserInput;

import javax.swing.*;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.By;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.phantomjs.PhantomJSDriver;

import project.GUI.GUIInput;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
public class SetTestLoginButtonImplementation {

	private static String userID, userPassword, userName, welcomeMessege;
	private static String projectLocation = System.getProperty("user.dir");	
	private static WebDriver driver;
	private static WebElement id, password;
	private static boolean loginPageFound, loginSuccess, nameMatch;
	private static JFrame loginTestingMainFrame;
	private static JButton okButton;
	private static JPanel panel;
	private static Box verticalBox;
	
	private static boolean testHeadless = true;
	public static void main(String[] args) {
		runThis();
	}
	public static void runThis()  {
		pullLoginInformation();
		setDriver();
		runTest();
		setVerificationProperty();
		instantiateTestLoginFrame();
		buildOKButton();
		buildInfoField();
		buildFrame();
	}
	
	public static void pullLoginInformation() {
		PropertiesFile.setProps();
		userID = PropertiesFile.getUserID();
		userPassword = PropertiesFile.getUserPassword();
		userName = PropertiesFile.getUserName();
	}
	
	public static void instantiateTestLoginFrame() {
		loginTestingMainFrame = new JFrame("Login Test");
	}
	
	public static void setDriver() {
		String browserLocation = projectLocation + "\\RegToolData";
		if (testHeadless == false) {
			String browser = PropertiesFile.getBrowser();
			if (browser.contains("Firefox")) {
				System.setProperty("webdriver.gecko.driver", browserLocation + "\\geckodriver.exe");
				//System.setProperty("webdriver.gecko.driver", SetTestLoginButtonImplementation.class.getResource("\\geckodriver.exe");
				driver = new FirefoxDriver();
			}
			
			if (browser.contains("Chrome")) {
				System.setProperty("webdriver.chrome.driver", browserLocation + "\\chromedriver.exe");
				driver = new ChromeDriver();
			}
		}
		if (testHeadless == true) {
			System.setProperty("phantomjs.binary.path", browserLocation + "\\phantomjs.exe");
			driver = new PhantomJSDriver(); 
		}
	}
	
	public static void runTest() {
		/**
		 * 1. can we load the page
		 * 3. test login credentials for successful login
		 * 4. does name on site match records
		 */
		
		try { //go to web page and identify input fields
			driver.get("http://selfserveprod.yu.edu/pls/banprd/twbkwbis.P_WWWLogin");
			id = driver.findElement(By.xpath("//*[@id=\"UserID\"]"));
			password = driver.findElement(By.xpath("//*[@id=\"PIN\"]/input"));	
			loginPageFound = true;
		}
		catch (NoSuchElementException e) {
			System.out.println("could not load page -> find id field -> find password field");
			loginPageFound = false;
		}
		
		
		try { //Attempt login
			id.sendKeys(userID);
			password.sendKeys(userPassword);
			driver.findElement(By.xpath("/html/body/div[3]/form/p/input[1]")).click(); //press enter after login credentials 
			welcomeMessege = driver.findElement(By.xpath("/html/body/div[3]/table[1]/tbody/tr/td[2]")).getText();
			if (welcomeMessege.contains("Authorization Failure")) {
				loginSuccess = false;
			} else {
			loginSuccess = true;
			}
		}
		catch (NoSuchElementException e) {
			System.out.println("could not send id key -> send password key -> click enter -> extract welcome messege");
			loginSuccess = false;
		}
		
		if (welcomeMessege.contains(userName)) {
			nameMatch = true;			
		}
		else {
			nameMatch = false;
		}
	driver.close();	
	}
	
	public static void setVerificationProperty() {
		if (loginPageFound && loginSuccess && nameMatch) {
			UserInput.setProps();
			UserInput.verifyLoginCombination();
			UserInput.storeProps();
		}
	}
	public static void buildOKButton() {
		okButton = new JButton("OK");
		okButton.addActionListener(new okButtonListenerImplementation());
	}
	public static void buildInfoField() {
		panel = new JPanel(new FlowLayout(FlowLayout.LEFT, 0, 0));
		panel.setBackground(Color.black);
		verticalBox = Box.createVerticalBox();
		String label1;
		if (loginPageFound == true) {
			label1 = "load web page: Success";
		} else {
			label1 = "lead web page: Failed";
		}
		JLabel loadWebPage = new JLabel(label1);
		loadWebPage.setForeground(Color.white);
		
		String label2;
		if (loginSuccess == true) {
			label2 = "login: Success";
		} else {
			label2 = "login: Failed";
		}
		JLabel tryLogin = new JLabel(label2);
		tryLogin.setForeground(Color.white);
		
		String label3;
		if (nameMatch == true) {
			label3 = "name match: Yes";
		} else {
			label3 = "name match: No";
		}
		JLabel didNamesMatch = new JLabel(label3);
		didNamesMatch.setForeground(Color.white);
		
		verticalBox.add(loadWebPage);
		verticalBox.add(tryLogin);
		verticalBox.add(didNamesMatch);
		verticalBox.add(okButton);
		panel.add(verticalBox);
		loginTestingMainFrame.add(panel);
	}
	public static void buildFrame() {
		loginTestingMainFrame.setVisible(true);
		loginTestingMainFrame.setSize(150, 100);
		loginTestingMainFrame.setResizable(false);
		loginTestingMainFrame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
	}
	
	public static class okButtonListenerImplementation implements ActionListener {
		public void actionPerformed(ActionEvent e) {
			loginTestingMainFrame.dispose();
			GUIInput.mainMenuFrame.dispose();
			GUIInput.main(null);
			
		}
	}
}
