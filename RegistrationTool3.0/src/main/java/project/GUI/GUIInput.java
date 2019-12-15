package project.GUI;

import javax.swing.*;

import project.ButtonConfiguration.SetConfigureClassesButtonImplementation;
import project.ButtonConfiguration.SetPasswordButtonImplementation;
import project.ButtonConfiguration.SetTestLoginButtonImplementation;
import project.JudaicStudiesConfiguration.*;
import project.Registration.SeleniumTest1;
import java.awt.*;
import java.awt.event.*;
import java.io.IOException;

import project.config.PropertiesFile;
import project.search.ClassStatsSearch;
import project.writing.UserInput;

public class GUIInput {
	public static JFrame mainMenuFrame;
	protected static JPanel panel;
	private static JButton setPassword, testLogin, configure, statistics, showStats, register, judaicStudies, removePassword;
	//private static JLabel label;
	public static Box rightPanelBox;
	public static Box centerPanelBox;
	public static boolean availableStatistics = false;
	
	public static void main (String[] args) { 
		project.preCheck.initialization.main(null);
		
		instantiateMainMenuFrame();
		
		
		buildSetPasswordButton();
		buildTestLoginButton();
		buildJudaicStudiesButton();
		buildConfigureButton();
		buildStatisticsButton();
		buildShowStatsButton();
		buildRegisterButton();
		buildRemovePasswordButton();
		
		buildMainMenuLayout();
		
		buildMainMenuFrame();

	}
	public static void instantiateMainMenuFrame() {
		mainMenuFrame = new JFrame("menu");
	}
	public static void buildMainMenuFrame() {
		mainMenuFrame.setVisible(true);
		mainMenuFrame.setSize(200, 375);
		mainMenuFrame.setResizable(false);
		mainMenuFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);	
	}
	
	public static void buildSetPasswordButton() {
		setPassword = new JButton("  Set Password ");
		setPassword.addActionListener(new setPasswordButtonListener());
	}
	public static void buildTestLoginButton() {
		testLogin = new JButton("   Test Login  ");
		testLogin.addActionListener(new setTestLoginButtonListener());
	}
	public static void buildJudaicStudiesButton() {
		judaicStudies = new JButton("  J Configure  ");
		judaicStudies.addActionListener(new setConfigureJudaicStudiesClasses());
	}
	public static void buildConfigureButton() {
		configure = new JButton("   Configure   ");
		configure.addActionListener(new setConfigureClassesButtonListener());
	}
	public static void buildStatisticsButton() {
		statistics = new JButton("   Statistics  ");
		statistics.addActionListener(new setStatisticsSearchAndDisplay());
	}
	public static void buildShowStatsButton() {
		showStats = new JButton("Show Statistics");
		showStats.addActionListener(new showStatFile());
	}
	public static void buildRegisterButton() {
		register = new JButton("   Register    "); //the extra spacing is there to allow the buttons to line up	
		register.addActionListener(new initiateRegistrationButton());
	}
	public static void buildRemovePasswordButton() {
		removePassword = new JButton("Delete Password"); 	
		removePassword.addActionListener(new initiateDeletePassword());
	}
	public static void buildMainMenuLayout() {		
		panel = new JPanel(new BorderLayout());
		panel.setBackground(Color.black); //creates full black background, but not added to the actual frame yet
		
		rightPanelBox = Box.createVerticalBox();
		
		addUserInfoToBox();

		rightPanelBox.add(setPassword);
		rightPanelBox.add(testLogin);
		rightPanelBox.add(judaicStudies);
		rightPanelBox.add(configure);
		rightPanelBox.add(statistics);
		rightPanelBox.add(showStats);
		rightPanelBox.add(removePassword);
		rightPanelBox.add(register);
		panel.add(rightPanelBox, "West");
		
		if (availableStatistics)
		{
			panel.add(centerPanelBox);
		}
		mainMenuFrame.add(panel);
	}
	public static void addUserInfoToBox() {
		String[] userInfo = PropertiesFile.getUserInfoForGUI(); //[0]=username
																//[1]=userID
																//[2]=user Password
																//[3]=login verification
																//[4]=Term and year
																//[5]=Registration Status
																//[6]=Browser
																//[7]=Judiac Studies Type
		String partialPassword = "";
		if (userInfo[2].length() == 6) {
			partialPassword = userInfo[2].substring(0, 3) + "***";
		}
		
		JLabel userName = new JLabel("Name: " + userInfo[0]);
		userName.setForeground(Color.white);

		JLabel userID = new JLabel("ID: " + userInfo[1]);
		userID.setForeground(Color.white);
		
		JLabel userPassword = new JLabel("Password: " + partialPassword);
		userPassword.setForeground(Color.white);
		
		JLabel judaicSudtiesType = new JLabel("JSection: " + userInfo[7]);
		judaicSudtiesType.setForeground(Color.white);
		
		JLabel loginVerification = new JLabel("Login Validity: " + userInfo[3]);
		loginVerification.setForeground(Color.white);
		
		JLabel regTerm = new JLabel("Term: " + userInfo[4]);
		regTerm.setForeground(Color.white);
		
		JLabel regStatus = new JLabel("Status: " + userInfo[5]);
		regStatus.setForeground(Color.white);
		
		JLabel browser = new JLabel("Browser to be used: " + userInfo[6]);
		browser.setForeground(Color.white);
		//JLabel user = new JLabel();
		rightPanelBox.add(userName);
		rightPanelBox.add(userID);
		rightPanelBox.add(userPassword);
		rightPanelBox.add(judaicSudtiesType);
		rightPanelBox.add(loginVerification);
		rightPanelBox.add(regTerm);
		rightPanelBox.add(regStatus);
		rightPanelBox.add(browser);
	}
}

class setPasswordButtonListener implements ActionListener{
	public void actionPerformed(ActionEvent e) {
		SetPasswordButtonImplementation.runThis();
	}
} 
class setTestLoginButtonListener implements ActionListener{
	public void actionPerformed(ActionEvent e) {
		SetTestLoginButtonImplementation.runThis();
	}
}
class setConfigureClassesButtonListener implements ActionListener {
	public void actionPerformed(ActionEvent e) {
		if (PropertiesFile.getCredentialsVerificationStatus().equals("verified")) {
			SetConfigureClassesButtonImplementation.runThis();
		}
	}
}
class setStatisticsSearchAndDisplay implements ActionListener {
	public static String projectLocation = System.getProperty("user.dir");
	public void actionPerformed(ActionEvent e){
		if (PropertiesFile.getCredentialsVerificationStatus().equals("verified")) {
			ClassStatsSearch.runClassSearchForGUI();
		}
	}
}
class showStatFile implements ActionListener {
	public static String projectLocation = System.getProperty("user.dir");
	public void actionPerformed(ActionEvent e) {
		if ((PropertiesFile.getCredentialsVerificationStatus().equals("verified")) && !(PropertiesFile.getNumOfClasses().isEmpty())) {
			try {
				ClassStatsSearch.DisplayStatFile();
			} catch (IOException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
		}
	}
}
class setConfigureJudaicStudiesClasses implements ActionListener {
	public static String projectLocation = System.getProperty("user.dir");
	public void actionPerformed(ActionEvent e) {
		if (PropertiesFile.getCredentialsVerificationStatus().equals("verified")){
			PropertiesFile.setProps();
			String judaicStudiesType = PropertiesFile.getJudaicSectionType();
			if(judaicStudiesType.equals("YP")) {//needs to be implemented
				runYPConfigWindow.runThis();
			}
			if(judaicStudiesType.equals("BMP")) {//needs to be implemented
				runBMPConfigWindow.runThis();
			}
			if(judaicStudiesType.equals("IBC")) {//needs to be implemented
				runIBCConfigWindow.runThis();
			}
			if(judaicStudiesType.equals("JSS")) {//needs to be implemented
				runJSSConfigWindow.runThis();
			}
		}
	}
}
class initiateRegistrationButton implements ActionListener {
	public void actionPerformed(ActionEvent e) {
		if (PropertiesFile.getCredentialsVerificationStatus().equals("verified")){
			SeleniumTest1.main(null);
		}
	}
}
class initiateDeletePassword implements ActionListener {
	public void actionPerformed(ActionEvent e) {
		if (PropertiesFile.getCredentialsVerificationStatus().equals("verified")){
			UserInput.setProps();
			UserInput.setPassword("");
			UserInput.storeProps();
			GUIInput.mainMenuFrame.dispose();
			GUIInput.main(null);
		}
	}
}





