package project.JudaicStudiesConfiguration;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.event.*;
import javax.swing.*;
import javax.swing.text.*;
import project.config.PropertiesFile;
import project.writing.UserInput;

public class runIBCConfigWindow {

	private static JFrame IBCsetNumberOfClassesFrame;
	private static JPanel IBCpanel;
	private static Box IBCverticalBox;
	private static JTextField IBCamountOfClassesTextField; 
	private static JButton IBCbuttonToMoveOn;
	public static void main(String[] args) {
		runThis();
	}
	public static void runThis() {
		instantiateConfigureClassNumberFrame();
		configureNumberOfClassesFrame();
		buildFrame1();
	}
	
	public static void instantiateConfigureClassNumberFrame() {
		IBCsetNumberOfClassesFrame = new JFrame("Input Number of Classes");
	}
	
	public static void configureNumberOfClassesFrame() {
		IBCpanel = new JPanel(new FlowLayout(FlowLayout.LEFT, 0, 0));
		IBCpanel.setBackground(Color.black);
		IBCverticalBox = Box.createVerticalBox();
		PropertiesFile.setProps();
		String currentAmountOfIBCClasses= PropertiesFile.getNumOfNonYPClasses("IBC");
		if (currentAmountOfIBCClasses.isEmpty()) {
			currentAmountOfIBCClasses = "0";
		}
		JLabel label1 = new JLabel("current classes on file: " + currentAmountOfIBCClasses + System.lineSeparator());
		label1.setForeground(Color.white);
		IBCverticalBox.add(label1);
		
		JButton viewCurrentClasses = new JButton("View All IBC Classes"); 
		viewCurrentClasses.addActionListener(new displayAllIBCClassesOnFile());
		JButton changeIndividualClass = new JButton("Change Single IBC Class");
		changeIndividualClass.addActionListener(new changeIndividualIBCClassImplementation());
		JButton removeClass = new JButton("Delete IBC Class");
		removeClass.addActionListener(new removeIBCClassImplemetation());
		IBCverticalBox.add(viewCurrentClasses);
		IBCverticalBox.add(changeIndividualClass);
		IBCverticalBox.add(removeClass);
		
		JLabel label2 = new JLabel("New Amount of IBC Classes:");
		label2.setForeground(Color.white);
		IBCverticalBox.add(label2);
		JLabel label3 = new JLabel("The max value is 10");
		label3.setForeground(Color.white);
		IBCverticalBox.add(label3);
		
		NumberFormatter formatter = new NumberFormatter();
		IBCamountOfClassesTextField = new JFormattedTextField(formatter);
		IBCamountOfClassesTextField.setColumns(5);
		IBCverticalBox.add(IBCamountOfClassesTextField);
		
		IBCbuttonToMoveOn = new JButton("Submit");
		IBCbuttonToMoveOn.addActionListener(new openIBCClassConfigTable());
		
		IBCverticalBox.add(IBCbuttonToMoveOn);
		IBCpanel.add(IBCverticalBox);
		IBCsetNumberOfClassesFrame.add(IBCpanel);
	}
	
	public static void buildFrame1() {
		IBCsetNumberOfClassesFrame.setVisible(true);
		IBCsetNumberOfClassesFrame.setSize(200, 205);
		IBCsetNumberOfClassesFrame.setResizable(false);
		IBCsetNumberOfClassesFrame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
	}
	
	public static class openIBCClassConfigTable implements ActionListener {
		static JFrame IBCClassConfigurationFrame;
		public static int rows;
		
		static JLabel IBCClassLabel1, IBCClassLabel2, IBCClassLabel3, IBCClassLabel4, IBCClassLabel5, IBCClassLabel6, IBCClassLabel7, IBCClassLabel8, IBCClassLabel9, IBCClassLabel10;
		static JPanel IBCClassPanel1 = new JPanel (new FlowLayout(FlowLayout.LEADING, 0, 0)),
				IBCClassPanel2 = new JPanel (new FlowLayout(FlowLayout.LEADING, 0, 0)),
				IBCClassPanel3 = new JPanel (new FlowLayout(FlowLayout.LEADING, 0, 0)),
				IBCClassPanel4 = new JPanel (new FlowLayout(FlowLayout.LEADING, 0, 0)),
				IBCClassPanel5 = new JPanel (new FlowLayout(FlowLayout.LEADING, 0, 0)),
				IBCClassPanel6 = new JPanel (new FlowLayout(FlowLayout.LEADING, 0, 0)),
				IBCClassPanel7 = new JPanel (new FlowLayout(FlowLayout.LEADING, 0, 0)),
				IBCClassPanel8 = new JPanel (new FlowLayout(FlowLayout.LEADING, 0, 0)),
				IBCClassPanel9 = new JPanel (new FlowLayout(FlowLayout.LEADING, 0, 0)),
				IBCClassPanel10 = new JPanel (new FlowLayout(FlowLayout.LEADING, 0, 0));
		static JComboBox<String> IBCClass1Priority, IBCClass2Priority, IBCClass3Priority, IBCClass4Priority, IBCClass5Priority, IBCClass6Priority, IBCClass7Priority, IBCClass8Priority, IBCClass9Priority, IBCClass10Priority; 
		static JTextField IBCClass1CRN, IBCClass1IBCClassTitle, IBCClass1IBCClassSection, IBCClass1CourseNumber, IBCClass1SectionNumber;
		static JTextField IBCClass2CRN, IBCClass2IBCClassTitle, IBCClass2IBCClassSection, IBCClass2CourseNumber, IBCClass2SectionNumber;
		static JTextField IBCClass3CRN, IBCClass3IBCClassTitle, IBCClass3IBCClassSection, IBCClass3CourseNumber, IBCClass3SectionNumber;
		static JTextField IBCClass4CRN, IBCClass4IBCClassTitle, IBCClass4IBCClassSection, IBCClass4CourseNumber, IBCClass4SectionNumber;
		static JTextField IBCClass5CRN, IBCClass5IBCClassTitle, IBCClass5IBCClassSection, IBCClass5CourseNumber, IBCClass5SectionNumber;
		static JTextField IBCClass6CRN, IBCClass6IBCClassTitle, IBCClass6IBCClassSection, IBCClass6CourseNumber, IBCClass6SectionNumber;
		static JTextField IBCClass7CRN, IBCClass7IBCClassTitle, IBCClass7IBCClassSection, IBCClass7CourseNumber, IBCClass7SectionNumber;
		static JTextField IBCClass8CRN, IBCClass8IBCClassTitle, IBCClass8IBCClassSection, IBCClass8CourseNumber, IBCClass8SectionNumber;
		static JTextField IBCClass9CRN, IBCClass9IBCClassTitle, IBCClass9IBCClassSection, IBCClass9CourseNumber, IBCClass9SectionNumber;
		static JTextField IBCClass10CRN, IBCClass10IBCClassTitle, IBCClass10IBCClassSection, IBCClass10CourseNumber, IBCClass10SectionNumber;
		
		public void actionPerformed(ActionEvent e) {
			//save the info, close this frame, open a new one
			UserInput.setProps();
			String inputNumber = IBCamountOfClassesTextField.getText();
			System.out.println("[" + inputNumber + "]");
			if ((inputNumber.isEmpty()) || (Integer.parseInt(inputNumber.trim()) > 10)) {
				inputNumber = "10";
				if (inputNumber.isEmpty()) {
					inputNumber = "0";
				}
				System.out.println("Number changed to: " + inputNumber);
			}
			UserInput.setNumOfIBCClasses(inputNumber);
			UserInput.storeProps();
			IBCsetNumberOfClassesFrame.setVisible(false);
			
			setupSecondFrame(inputNumber);			
		}
		
		public static void setupSecondFrame(String numClasses) {
			IBCClassConfigurationFrame = new JFrame("IBC Class Configuration");
			rows = Integer.parseInt(numClasses);
			Box IBCverticalClassConfigBox = Box.createVerticalBox();
			JLabel IBCnumberOfClassesLabel = new JLabel("Number of IBC Classes: " + rows);
			IBCnumberOfClassesLabel.setForeground(Color.white);
			JPanel IBCtopPanel = new JPanel(new FlowLayout(FlowLayout.LEFT, 0, 0));
			IBCtopPanel.setBackground(Color.black);
			IBCtopPanel.add(IBCnumberOfClassesLabel);
			IBCverticalClassConfigBox.add(IBCtopPanel);
			
			  JLabel titleLabel = new JLabel("Class #      CRN                           Class Title                          Class Section     Course Number   Section Number   Priority");
			JLabel exampleLabel = new JLabel("Class x      40123                    First Year Writing                        FYWR                     1020                         311                  High");
			titleLabel.setForeground(Color.CYAN);
			exampleLabel.setForeground(Color.white);
			
			Font exampleFont = new Font("Arial", Font.ITALIC, 12);
			exampleLabel.setFont(exampleFont);
			
			JPanel titlePanel = new JPanel(new FlowLayout(FlowLayout.LEFT, 0, 0));
			JPanel examplePanel = new JPanel(new FlowLayout(FlowLayout.LEFT, 0, 0));
			titlePanel.add(titleLabel);
			titlePanel.setBackground(Color.black);
			examplePanel.add(exampleLabel);
			examplePanel.setBackground(Color.black);
			IBCverticalClassConfigBox.add(titlePanel);
			IBCverticalClassConfigBox.add(examplePanel);
			
			IBCClassPanel1.setBackground(Color.black);
			IBCClassPanel2.setBackground(Color.black);
			IBCClassPanel3.setBackground(Color.black);
			IBCClassPanel4.setBackground(Color.black);
			IBCClassPanel5.setBackground(Color.black);
			IBCClassPanel6.setBackground(Color.black);
			IBCClassPanel7.setBackground(Color.black);
			IBCClassPanel8.setBackground(Color.black);
			IBCClassPanel9.setBackground(Color.black);
			IBCClassPanel10.setBackground(Color.black);
			
			int crnColumns = 7;
			int classTitleColumns = 16; 
			int classSectionColumns = 8;
			int courseNumberColumns = 8;
			int sectionNumberColumns = 8;
			
			switch (10 - rows + 1) {
				case 1: 
					IBCClassLabel10 = new JLabel("Class 10:");
					IBCClassLabel10.setForeground(Color.white);
					IBCClass10CRN = new JTextField();
					IBCClass10CRN.setColumns(crnColumns);
					IBCClass10IBCClassTitle = new JTextField();
					IBCClass10IBCClassTitle.setColumns(classTitleColumns);
					IBCClass10IBCClassSection = new JTextField();
					IBCClass10IBCClassSection.setColumns(classSectionColumns);
					IBCClass10CourseNumber = new JTextField();
					IBCClass10CourseNumber.setColumns(courseNumberColumns);
					IBCClass10SectionNumber = new JTextField();
					IBCClass10SectionNumber.setColumns(sectionNumberColumns);
					IBCClass10Priority = new JComboBox<String>(new String[] {"High", "Medium", "Low"});
					IBCClassPanel10.add(IBCClassLabel10);
					IBCClassPanel10.add(IBCClass10CRN);
					IBCClassPanel10.add(IBCClass10IBCClassTitle);
					IBCClassPanel10.add(IBCClass10IBCClassSection);
					IBCClassPanel10.add(IBCClass10CourseNumber);
					IBCClassPanel10.add(IBCClass10SectionNumber);
					IBCClassPanel10.add(IBCClass10Priority);
				case 2:
					IBCClassLabel9 = new JLabel("Class 9:");
					IBCClassLabel9.setForeground(Color.white);
					IBCClass9CRN = new JTextField();
					IBCClass9CRN.setColumns(crnColumns);
					IBCClass9IBCClassTitle = new JTextField();
					IBCClass9IBCClassTitle.setColumns(classTitleColumns);
					IBCClass9IBCClassSection = new JTextField();
					IBCClass9IBCClassSection.setColumns(classSectionColumns);
					IBCClass9CourseNumber = new JTextField();
					IBCClass9CourseNumber.setColumns(courseNumberColumns);
					IBCClass9SectionNumber = new JTextField();
					IBCClass9SectionNumber.setColumns(sectionNumberColumns);
					IBCClass9Priority = new JComboBox<String>(new String[] {"High", "Medium", "Low"});
					IBCClassPanel9.add(IBCClassLabel9);
					IBCClassPanel9.add(IBCClass9CRN);
					IBCClassPanel9.add(IBCClass9IBCClassTitle);
					IBCClassPanel9.add(IBCClass9IBCClassSection);
					IBCClassPanel9.add(IBCClass9CourseNumber);
					IBCClassPanel9.add(IBCClass9SectionNumber);
					IBCClassPanel9.add(IBCClass9Priority);
				case 3:
					IBCClassLabel8 = new JLabel("Class 8:");
					IBCClassLabel8.setForeground(Color.white);
					IBCClass8CRN = new JTextField();
					IBCClass8CRN.setColumns(crnColumns);
					IBCClass8IBCClassTitle = new JTextField();
					IBCClass8IBCClassTitle.setColumns(classTitleColumns);
					IBCClass8IBCClassSection = new JTextField();
					IBCClass8IBCClassSection.setColumns(classSectionColumns);
					IBCClass8CourseNumber = new JTextField();
					IBCClass8CourseNumber.setColumns(courseNumberColumns);
					IBCClass8SectionNumber = new JTextField();
					IBCClass8SectionNumber.setColumns(sectionNumberColumns);
					IBCClass8Priority = new JComboBox<String>(new String[] {"High", "Medium", "Low"});
					IBCClassPanel8.add(IBCClassLabel8);
					IBCClassPanel8.add(IBCClass8CRN);
					IBCClassPanel8.add(IBCClass8IBCClassTitle);
					IBCClassPanel8.add(IBCClass8IBCClassSection);
					IBCClassPanel8.add(IBCClass8CourseNumber);
					IBCClassPanel8.add(IBCClass8SectionNumber);
					IBCClassPanel8.add(IBCClass8Priority);
				case 4:
					IBCClassLabel7 = new JLabel("Class 7:");
					IBCClassLabel7.setForeground(Color.white);
					IBCClass7CRN = new JTextField();
					IBCClass7CRN.setColumns(crnColumns);
					IBCClass7IBCClassTitle = new JTextField();
					IBCClass7IBCClassTitle.setColumns(classTitleColumns);
					IBCClass7IBCClassSection = new JTextField();
					IBCClass7IBCClassSection.setColumns(classSectionColumns);
					IBCClass7CourseNumber = new JTextField();
					IBCClass7CourseNumber.setColumns(courseNumberColumns);
					IBCClass7SectionNumber = new JTextField();
					IBCClass7SectionNumber.setColumns(sectionNumberColumns);
					IBCClass7Priority = new JComboBox<String>(new String[] {"High", "Medium", "Low"});
					IBCClassPanel7.add(IBCClassLabel7);
					IBCClassPanel7.add(IBCClass7CRN);
					IBCClassPanel7.add(IBCClass7IBCClassTitle);
					IBCClassPanel7.add(IBCClass7IBCClassSection);
					IBCClassPanel7.add(IBCClass7CourseNumber);
					IBCClassPanel7.add(IBCClass7SectionNumber);
					IBCClassPanel7.add(IBCClass7Priority);
				case 5:
					IBCClassLabel6 = new JLabel("Class 6:");
					IBCClassLabel6.setForeground(Color.white);
					IBCClass6CRN = new JTextField();
					IBCClass6CRN.setColumns(crnColumns);
					IBCClass6IBCClassTitle = new JTextField();
					IBCClass6IBCClassTitle.setColumns(classTitleColumns);
					IBCClass6IBCClassSection = new JTextField();
					IBCClass6IBCClassSection.setColumns(classSectionColumns);
					IBCClass6CourseNumber = new JTextField();
					IBCClass6CourseNumber.setColumns(courseNumberColumns);
					IBCClass6SectionNumber = new JTextField();
					IBCClass6SectionNumber.setColumns(sectionNumberColumns);
					IBCClass6Priority = new JComboBox<String>(new String[] {"High", "Medium", "Low"});
					IBCClassPanel6.add(IBCClassLabel6);
					IBCClassPanel6.add(IBCClass6CRN);
					IBCClassPanel6.add(IBCClass6IBCClassTitle);
					IBCClassPanel6.add(IBCClass6IBCClassSection);
					IBCClassPanel6.add(IBCClass6CourseNumber);
					IBCClassPanel6.add(IBCClass6SectionNumber);
					IBCClassPanel6.add(IBCClass6Priority);
				case 6:
					IBCClassLabel5 = new JLabel("Class 5:");
					IBCClassLabel5.setForeground(Color.white);
					IBCClass5CRN = new JTextField();
					IBCClass5CRN.setColumns(crnColumns);
					IBCClass5IBCClassTitle = new JTextField();
					IBCClass5IBCClassTitle.setColumns(classTitleColumns);
					IBCClass5IBCClassSection = new JTextField();
					IBCClass5IBCClassSection.setColumns(classSectionColumns);
					IBCClass5CourseNumber = new JTextField();
					IBCClass5CourseNumber.setColumns(courseNumberColumns);
					IBCClass5SectionNumber = new JTextField();
					IBCClass5SectionNumber.setColumns(sectionNumberColumns);
					IBCClass5Priority = new JComboBox<String>(new String[] {"High", "Medium", "Low"});
					IBCClassPanel5.add(IBCClassLabel5);
					IBCClassPanel5.add(IBCClass5CRN);
					IBCClassPanel5.add(IBCClass5IBCClassTitle);
					IBCClassPanel5.add(IBCClass5IBCClassSection);
					IBCClassPanel5.add(IBCClass5CourseNumber);
					IBCClassPanel5.add(IBCClass5SectionNumber);
					IBCClassPanel5.add(IBCClass5Priority);
				case 7:
					IBCClassLabel4 = new JLabel("Class 4:");
					IBCClassLabel4.setForeground(Color.white);
					IBCClass4CRN = new JTextField();
					IBCClass4CRN.setColumns(crnColumns);
					IBCClass4IBCClassTitle = new JTextField();
					IBCClass4IBCClassTitle.setColumns(classTitleColumns);
					IBCClass4IBCClassSection = new JTextField();
					IBCClass4IBCClassSection.setColumns(classSectionColumns);
					IBCClass4CourseNumber = new JTextField();
					IBCClass4CourseNumber.setColumns(courseNumberColumns);
					IBCClass4SectionNumber = new JTextField();
					IBCClass4SectionNumber.setColumns(sectionNumberColumns);
					IBCClass4Priority = new JComboBox<String>(new String[] {"High", "Medium", "Low"});
					IBCClassPanel4.add(IBCClassLabel4);
					IBCClassPanel4.add(IBCClass4CRN);
					IBCClassPanel4.add(IBCClass4IBCClassTitle);
					IBCClassPanel4.add(IBCClass4IBCClassSection);
					IBCClassPanel4.add(IBCClass4CourseNumber);
					IBCClassPanel4.add(IBCClass4SectionNumber);
					IBCClassPanel4.add(IBCClass4Priority);
				case 8:
					IBCClassLabel3 = new JLabel("Class 3:");
					IBCClassLabel3.setForeground(Color.white);
					IBCClass3CRN = new JTextField();
					IBCClass3CRN.setColumns(crnColumns);
					IBCClass3IBCClassTitle = new JTextField();
					IBCClass3IBCClassTitle.setColumns(classTitleColumns);
					IBCClass3IBCClassSection = new JTextField();
					IBCClass3IBCClassSection.setColumns(classSectionColumns);
					IBCClass3CourseNumber = new JTextField();
					IBCClass3CourseNumber.setColumns(courseNumberColumns);
					IBCClass3SectionNumber = new JTextField();
					IBCClass3SectionNumber.setColumns(sectionNumberColumns);
					IBCClass3Priority = new JComboBox<String>(new String[] {"High", "Medium", "Low"});
					IBCClassPanel3.add(IBCClassLabel3);
					IBCClassPanel3.add(IBCClass3CRN);
					IBCClassPanel3.add(IBCClass3IBCClassTitle);
					IBCClassPanel3.add(IBCClass3IBCClassSection);
					IBCClassPanel3.add(IBCClass3CourseNumber);
					IBCClassPanel3.add(IBCClass3SectionNumber);
					IBCClassPanel3.add(IBCClass3Priority);
				case 9:
					IBCClassLabel2 = new JLabel("Class 2:");
					IBCClassLabel2.setForeground(Color.white);
					IBCClass2CRN = new JTextField();
					IBCClass2CRN.setColumns(crnColumns);
					IBCClass2IBCClassTitle = new JTextField();
					IBCClass2IBCClassTitle.setColumns(classTitleColumns);
					IBCClass2IBCClassSection = new JTextField();
					IBCClass2IBCClassSection.setColumns(classSectionColumns);
					IBCClass2CourseNumber = new JTextField();
					IBCClass2CourseNumber.setColumns(courseNumberColumns);
					IBCClass2SectionNumber = new JTextField();
					IBCClass2SectionNumber.setColumns(sectionNumberColumns);
					IBCClass2Priority = new JComboBox<String>(new String[] {"High", "Medium", "Low"});
					IBCClassPanel2.add(IBCClassLabel2);
					IBCClassPanel2.add(IBCClass2CRN);
					IBCClassPanel2.add(IBCClass2IBCClassTitle);
					IBCClassPanel2.add(IBCClass2IBCClassSection);
					IBCClassPanel2.add(IBCClass2CourseNumber);
					IBCClassPanel2.add(IBCClass2SectionNumber);
					IBCClassPanel2.add(IBCClass2Priority);
				case 10:
					IBCClassLabel1 = new JLabel("Class 1:");
					IBCClassLabel1.setForeground(Color.white);
					IBCClass1CRN = new JTextField();
					IBCClass1CRN.setColumns(crnColumns);
					IBCClass1IBCClassTitle = new JTextField();
					IBCClass1IBCClassTitle.setColumns(classTitleColumns);
					IBCClass1IBCClassSection = new JTextField();
					IBCClass1IBCClassSection.setColumns(classSectionColumns);
					IBCClass1CourseNumber = new JTextField();
					IBCClass1CourseNumber.setColumns(courseNumberColumns);
					IBCClass1SectionNumber = new JTextField();
					IBCClass1SectionNumber.setColumns(sectionNumberColumns);
					IBCClass1Priority = new JComboBox<String>(new String[] {"High", "Medium", "Low"});
					IBCClassPanel1.add(IBCClassLabel1);
					IBCClassPanel1.add(IBCClass1CRN);
					IBCClassPanel1.add(IBCClass1IBCClassTitle);
					IBCClassPanel1.add(IBCClass1IBCClassSection);
					IBCClassPanel1.add(IBCClass1CourseNumber);
					IBCClassPanel1.add(IBCClass1SectionNumber);
					IBCClassPanel1.add(IBCClass1Priority);
			}

			if (rows > 0) {
				IBCverticalClassConfigBox.add(IBCClassPanel1);
			}
			if (rows > 1) {
				IBCverticalClassConfigBox.add(IBCClassPanel2);
			}
			if (rows > 2) {
				IBCverticalClassConfigBox.add(IBCClassPanel3);
			}
			if (rows > 3) {
				IBCverticalClassConfigBox.add(IBCClassPanel4);
			}
			if (rows > 4) {
				IBCverticalClassConfigBox.add(IBCClassPanel5);
			}
			if (rows > 5) {
				IBCverticalClassConfigBox.add(IBCClassPanel6);
			}
			if (rows > 6) {
				IBCverticalClassConfigBox.add(IBCClassPanel7);
			}
			if (rows > 7) {
				IBCverticalClassConfigBox.add(IBCClassPanel8);
			}
			if (rows > 8) {
				IBCverticalClassConfigBox.add(IBCClassPanel9);
			}
			if (rows > 9) {
				IBCverticalClassConfigBox.add(IBCClassPanel10);
			}
			
			JButton IBCsubmitClasses = new JButton("Submit");
			IBCsubmitClasses.addActionListener(new collectIBCConfigClassesData());
			JPanel buttonPanel = new JPanel();
			buttonPanel.setBackground(Color.black);
			buttonPanel.add(IBCsubmitClasses);
			IBCverticalClassConfigBox.add(buttonPanel);
			
			IBCClassConfigurationFrame.add(IBCverticalClassConfigBox);
			
			IBCClassConfigurationFrame.setVisible(true);
			IBCClassConfigurationFrame.setSize(700, 100 + (29*rows) + 10);
			IBCClassConfigurationFrame.setResizable(false);
			IBCClassConfigurationFrame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		}
		
		public static class collectIBCConfigClassesData implements ActionListener {
			
			public void actionPerformed(ActionEvent e) {
				int numOfClasses = rows;
				String[][] allIBCClassInfo = new String[rows][6];
				
				
				
				switch (10 - numOfClasses + 1) {
					case 1:
						allIBCClassInfo[9][0] = IBCClass10CRN.getText();
						allIBCClassInfo[9][1] = IBCClass10IBCClassTitle.getText();
						allIBCClassInfo[9][2] = IBCClass10IBCClassSection.getText();
						allIBCClassInfo[9][3] = IBCClass10CourseNumber.getText();
						allIBCClassInfo[9][4] = IBCClass10SectionNumber.getText();
						allIBCClassInfo[9][5] = (String)IBCClass10Priority.getSelectedItem();
					case 2:
						allIBCClassInfo[8][0] = IBCClass9CRN.getText();
						allIBCClassInfo[8][1] = IBCClass9IBCClassTitle.getText();
						allIBCClassInfo[8][2] = IBCClass9IBCClassSection.getText();
						allIBCClassInfo[8][3] = IBCClass9CourseNumber.getText();
						allIBCClassInfo[8][4] = IBCClass9SectionNumber.getText();
						allIBCClassInfo[8][5] = (String)IBCClass9Priority.getSelectedItem();
					case 3:
						allIBCClassInfo[7][0] = IBCClass8CRN.getText();
						allIBCClassInfo[7][1] = IBCClass8IBCClassTitle.getText();
						allIBCClassInfo[7][2] = IBCClass8IBCClassSection.getText();
						allIBCClassInfo[7][3] = IBCClass8CourseNumber.getText();
						allIBCClassInfo[7][4] = IBCClass8SectionNumber.getText();
						allIBCClassInfo[7][5] = (String)IBCClass8Priority.getSelectedItem();
					case 4:
						allIBCClassInfo[6][0] = IBCClass7CRN.getText();
						allIBCClassInfo[6][1] = IBCClass7IBCClassTitle.getText();
						allIBCClassInfo[6][2] = IBCClass7IBCClassSection.getText();
						allIBCClassInfo[6][3] = IBCClass7CourseNumber.getText();
						allIBCClassInfo[6][4] = IBCClass7SectionNumber.getText();
						allIBCClassInfo[6][5] = (String)IBCClass7Priority.getSelectedItem();
					case 5:
						allIBCClassInfo[5][0] = IBCClass6CRN.getText();
						allIBCClassInfo[5][1] = IBCClass6IBCClassTitle.getText();
						allIBCClassInfo[5][2] = IBCClass6IBCClassSection.getText();
						allIBCClassInfo[5][3] = IBCClass6CourseNumber.getText();
						allIBCClassInfo[5][4] = IBCClass6SectionNumber.getText();
						allIBCClassInfo[5][5] = (String)IBCClass6Priority.getSelectedItem();
					case 6:
						allIBCClassInfo[4][0] = IBCClass5CRN.getText();
						allIBCClassInfo[4][1] = IBCClass5IBCClassTitle.getText();
						allIBCClassInfo[4][2] = IBCClass5IBCClassSection.getText();
						allIBCClassInfo[4][3] = IBCClass5CourseNumber.getText();
						allIBCClassInfo[4][4] = IBCClass5SectionNumber.getText();
						allIBCClassInfo[4][5] = (String)IBCClass5Priority.getSelectedItem();
					case 7:
						allIBCClassInfo[3][0] = IBCClass4CRN.getText();
						allIBCClassInfo[3][1] = IBCClass4IBCClassTitle.getText();
						allIBCClassInfo[3][2] = IBCClass4IBCClassSection.getText();
						allIBCClassInfo[3][3] = IBCClass4CourseNumber.getText();
						allIBCClassInfo[3][4] = IBCClass4SectionNumber.getText();
						allIBCClassInfo[3][5] = (String)IBCClass3Priority.getSelectedItem();
					case 8:
						allIBCClassInfo[2][0] = IBCClass3CRN.getText();
						allIBCClassInfo[2][1] = IBCClass3IBCClassTitle.getText();
						allIBCClassInfo[2][2] = IBCClass3IBCClassSection.getText();
						allIBCClassInfo[2][3] = IBCClass3CourseNumber.getText();
						allIBCClassInfo[2][4] = IBCClass3SectionNumber.getText();
						allIBCClassInfo[2][5] = (String)IBCClass3Priority.getSelectedItem();
					case 9:
						allIBCClassInfo[1][0] = IBCClass2CRN.getText();
						allIBCClassInfo[1][1] = IBCClass2IBCClassTitle.getText();
						allIBCClassInfo[1][2] = IBCClass2IBCClassSection.getText();
						allIBCClassInfo[1][3] = IBCClass2CourseNumber.getText();
						allIBCClassInfo[1][4] = IBCClass2SectionNumber.getText();
						allIBCClassInfo[1][5] = (String)IBCClass2Priority.getSelectedItem();
					case 10:
						allIBCClassInfo[0][0] = IBCClass1CRN.getText();
						allIBCClassInfo[0][1] = IBCClass1IBCClassTitle.getText();
						allIBCClassInfo[0][2] = IBCClass1IBCClassSection.getText();
						allIBCClassInfo[0][3] = IBCClass1CourseNumber.getText();
						allIBCClassInfo[0][4] = IBCClass1SectionNumber.getText();
						allIBCClassInfo[0][5] = (String)IBCClass1Priority.getSelectedItem();
				}
				UserInput.setProps();
				UserInput.writeAllNewIBCClasses(allIBCClassInfo);
				UserInput.storeProps();
				IBCClassConfigurationFrame.dispose();
				runIBCConfigWindow.main(null);
				
			}
			
		}
	}
		public static class displayAllIBCClassesOnFile implements ActionListener {
			
			public void actionPerformed(ActionEvent e) {
				PropertiesFile.setProps();
				String[][] currentIBCClassInfo = PropertiesFile.getNonYPClassInfo("IBC");//[x][0] = Subject      (MAT)
																			//[x][1] = Class        (Data Structures)  
																			//[x][2] = Course Number(1320)
																			//[x][3] = Section      (311)
																			//[x][4] = CRN          (42170)
																			//[x][5] = Priority     (High)
				JLabel[][] IBCclassInfoLabels = new JLabel[currentIBCClassInfo.length][7];
				for (int x = 0; x < currentIBCClassInfo.length; x++) {
					IBCclassInfoLabels[x][0]= new JLabel("Class " + (x+1));
					IBCclassInfoLabels[x][1]= new JLabel(currentIBCClassInfo[x][0]);
					IBCclassInfoLabels[x][2]= new JLabel(currentIBCClassInfo[x][1]);
					IBCclassInfoLabels[x][3]= new JLabel(currentIBCClassInfo[x][2]);
					IBCclassInfoLabels[x][4]= new JLabel(currentIBCClassInfo[x][3]);
					IBCclassInfoLabels[x][5]= new JLabel(currentIBCClassInfo[x][4]);
					IBCclassInfoLabels[x][6]= new JLabel(currentIBCClassInfo[x][5]);
					
					IBCclassInfoLabels[x][0].setForeground(Color.cyan);
					IBCclassInfoLabels[x][1].setForeground(Color.white);
					IBCclassInfoLabels[x][2].setForeground(Color.white);
					IBCclassInfoLabels[x][3].setForeground(Color.white);
					IBCclassInfoLabels[x][4].setForeground(Color.white);
					IBCclassInfoLabels[x][5].setForeground(Color.white);
					IBCclassInfoLabels[x][6].setForeground(Color.white);
					
					IBCclassInfoLabels[x][0].setPreferredSize(new Dimension(50,  15));
					IBCclassInfoLabels[x][1].setPreferredSize(new Dimension(35,  15));
					IBCclassInfoLabels[x][2].setPreferredSize(new Dimension(220, 15));
					IBCclassInfoLabels[x][3].setPreferredSize(new Dimension(38,  15));
					IBCclassInfoLabels[x][4].setPreferredSize(new Dimension(35,  15));
					IBCclassInfoLabels[x][5].setPreferredSize(new Dimension(35,  15));
					IBCclassInfoLabels[x][6].setPreferredSize(new Dimension(45,  15));
				}
				
				JLabel label1 = new JLabel("Class X");
				JLabel label2 = new JLabel("MAT");
				JLabel label3 = new JLabel("Data Structures");
				JLabel label4 = new JLabel("1320");
				JLabel label5 = new JLabel("311");
				JLabel label6 = new JLabel("42170");
				JLabel label7 = new JLabel("High");
				label1.setForeground(Color.cyan);
				label2.setForeground(Color.cyan);
				label3.setForeground(Color.cyan);
				label4.setForeground(Color.cyan);
				label5.setForeground(Color.cyan);
				label6.setForeground(Color.cyan);
				label7.setForeground(Color.cyan);
				label1.setPreferredSize(new Dimension(50,  15));
				label2.setPreferredSize(new Dimension(35,  15));
				label3.setPreferredSize(new Dimension(220, 15));
				label4.setPreferredSize(new Dimension(38,  15));
				label5.setPreferredSize(new Dimension(35,  15));
				label6.setPreferredSize(new Dimension(35,  15));
				label7.setPreferredSize(new Dimension(45,  15));
				JPanel topLevelPanel = new JPanel(new FlowLayout(FlowLayout.LEFT, 5, 0));
				topLevelPanel.setBackground(Color.black);
				topLevelPanel.add(label1);
				topLevelPanel.add(label2);
				topLevelPanel.add(label3);
				topLevelPanel.add(label4);
				topLevelPanel.add(label5);
				topLevelPanel.add(label6);
				topLevelPanel.add(label7);
				
				
				JPanel[] panelArrayWithIBCLabels = new JPanel[IBCclassInfoLabels.length];
				for (int x = 0; x < panelArrayWithIBCLabels.length; x++) {
					panelArrayWithIBCLabels[x] = new JPanel(new FlowLayout(FlowLayout.LEFT, 5, 0));
					panelArrayWithIBCLabels[x].setBackground(Color.black);
					panelArrayWithIBCLabels[x].add(IBCclassInfoLabels[x][0]);
					panelArrayWithIBCLabels[x].add(IBCclassInfoLabels[x][1]);
					panelArrayWithIBCLabels[x].add(IBCclassInfoLabels[x][2]);
					panelArrayWithIBCLabels[x].add(IBCclassInfoLabels[x][3]);
					panelArrayWithIBCLabels[x].add(IBCclassInfoLabels[x][4]);
					panelArrayWithIBCLabels[x].add(IBCclassInfoLabels[x][5]);
					panelArrayWithIBCLabels[x].add(IBCclassInfoLabels[x][6]);
				}
				Box verticalBox = Box.createVerticalBox();
				verticalBox.add(topLevelPanel);
				for(int x = 0; x < panelArrayWithIBCLabels.length; x++) {
					verticalBox.add(panelArrayWithIBCLabels[x]);
				}
				JFrame ClassDisplay = new JFrame("Display Classes On File");
				ClassDisplay.add(verticalBox);
				ClassDisplay.setVisible(true);
				ClassDisplay.setSize(500, panelArrayWithIBCLabels.length * 40 + 40);
				ClassDisplay.setResizable(false);
				ClassDisplay.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
				
			}
		}
		
		public static class changeIndividualIBCClassImplementation implements ActionListener {
			static JComboBox<String> IBCclassToChange, IBCclassYPriority;
			static JTextField IBCclassYCRN, IBCclassYClassTitle, IBCclassYClassSection, IBCclassYCourseNumber, IBCclassYSectionNumber;
			static JFrame IBCnewClassFrame;
			
			public void actionPerformed(ActionEvent e) {
				PropertiesFile.setProps();
				int classNum = Integer.parseInt(PropertiesFile.getNumOfNonYPClasses("IBC"));
				String[] choices = new String[classNum + 1];//add extra spot to be able to add a class onto what already exists
				for(int x = 0; x < (classNum + 1) ; x++ ) {
					choices[x] = "" + (x+1);
				}
				IBCclassToChange = new JComboBox<String>(choices);
				JLabel label1 = new JLabel("Change Class: ");
				label1.setForeground(Color.white);
				JPanel topPanel = new JPanel(new FlowLayout(FlowLayout.LEFT, 10, 0));
				topPanel.add(label1);
				topPanel.add(IBCclassToChange);
				topPanel.setBackground(Color.black);
				
				JLabel titleLabel = new JLabel("Class #      CRN                           Class Title                          Class Section     Course Number   Section Number   Priority ");
				JLabel exampleLabel = new JLabel("Class x      40123                    First Year Writing                        FYWR                     1020                         311               High");
				titleLabel.setForeground(Color.CYAN);
				exampleLabel.setForeground(Color.white);
				JPanel titlePanel = new JPanel(new FlowLayout(FlowLayout.LEADING, 0, 0));
				JPanel examplePanel = new JPanel(new FlowLayout(FlowLayout.LEADING, 0, 0));
				titlePanel.add(titleLabel);
				titlePanel.setBackground(Color.black);
				examplePanel.add(exampleLabel);
				examplePanel.setBackground(Color.black);
				
				JLabel classYLabel = new JLabel("Class Y");
				classYLabel.setForeground(Color.white);
				IBCclassYCRN = new JTextField();
				IBCclassYCRN.setColumns(7);
				IBCclassYClassTitle = new JTextField();
				IBCclassYClassTitle.setColumns(16);
				IBCclassYClassSection = new JTextField();
				IBCclassYClassSection.setColumns(8);
				IBCclassYCourseNumber = new JTextField();
				IBCclassYCourseNumber.setColumns(8);
				IBCclassYSectionNumber = new JTextField();
				IBCclassYSectionNumber.setColumns(8);
				IBCclassYPriority = new JComboBox<String>(new String[] {"High", "Medium", "Low"});
				JPanel newClassInfoPanel = new JPanel(new FlowLayout(FlowLayout.LEADING, 0, 0));
				newClassInfoPanel.setBackground(Color.black);
				newClassInfoPanel.add(classYLabel);
				newClassInfoPanel.add(IBCclassYCRN);
				newClassInfoPanel.add(IBCclassYClassTitle);
				newClassInfoPanel.add(IBCclassYClassSection);
				newClassInfoPanel.add(IBCclassYCourseNumber);
				newClassInfoPanel.add(IBCclassYSectionNumber);
				newClassInfoPanel.add(IBCclassYPriority);
				
				JButton submit = new JButton("Submit");
				submit.addActionListener(new sendNewIBCClassInfoToFile());
				JPanel bottomPanel = new JPanel();
				bottomPanel.setBackground(Color.black);
				bottomPanel.add(submit);
				
				Box verticalBox = Box.createVerticalBox();
				verticalBox.add(topPanel);
				verticalBox.add(titlePanel);
				verticalBox.add(examplePanel);
				verticalBox.add(newClassInfoPanel);
				verticalBox.add(bottomPanel);
				
				IBCnewClassFrame = new JFrame("New IBC Class Info");
				IBCnewClassFrame.add(verticalBox);
				IBCnewClassFrame.setVisible(true);
				IBCnewClassFrame.setSize(675, 150);
				IBCnewClassFrame.setResizable(false);
				IBCnewClassFrame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
			}
		}
		public static class sendNewIBCClassInfoToFile implements ActionListener {
			
			public void actionPerformed(ActionEvent e) {
				String[] newClassInfo= {(String)changeIndividualIBCClassImplementation.IBCclassToChange.getSelectedItem(),
						changeIndividualIBCClassImplementation.IBCclassYCRN.getText(),
						changeIndividualIBCClassImplementation.IBCclassYClassTitle.getText(),
						changeIndividualIBCClassImplementation.IBCclassYClassSection.getText(),
						changeIndividualIBCClassImplementation.IBCclassYCourseNumber.getText(),
						changeIndividualIBCClassImplementation.IBCclassYSectionNumber.getText(),
										(String)changeIndividualIBCClassImplementation.IBCclassYPriority.getSelectedItem()};
			UserInput.editSingleIBCClass(newClassInfo);
			changeIndividualIBCClassImplementation.IBCnewClassFrame.dispose();
			IBCsetNumberOfClassesFrame.dispose();
			runIBCConfigWindow.main(null);
			}
		}
		
		public static class removeIBCClassImplemetation implements ActionListener {
			static JComboBox<String> IBCclassToDelete;
			private static JFrame IBCclassToDeleteFrame;
			
			public void actionPerformed(ActionEvent e) {
				PropertiesFile.setProps();
				int classNum = Integer.parseInt(PropertiesFile.getNumOfNonYPClasses("IBC"));
				String[] choices = new String[classNum];
				for(int x = 0; x < (classNum) ; x++ ) {
					choices[x] = "" + (x+1);
				}
				IBCclassToDelete = new JComboBox<String>(choices);
				JLabel label1 = new JLabel("Delete Class: ");
				label1.setForeground(Color.white);
				JPanel topPanel = new JPanel(new FlowLayout(FlowLayout.LEFT, 10, 0));
				topPanel.add(label1);
				topPanel.add(IBCclassToDelete);
				topPanel.setBackground(Color.black);
				
				JButton submit = new JButton("Submit");
				submit.addActionListener(new ActionListener() {
					public void actionPerformed(ActionEvent e) {
						UserInput.deleteIBCClass((String)IBCclassToDelete.getSelectedItem());
						IBCclassToDeleteFrame.dispose();
						IBCsetNumberOfClassesFrame.dispose();
						runIBCConfigWindow.main(null);
					}
				});
				JPanel bottomPanel = new JPanel();
				bottomPanel.setBackground(Color.black);
				bottomPanel.add(submit);
				
				Box verticalBox = Box.createVerticalBox();
				verticalBox.add(topPanel);
				verticalBox.add(bottomPanel);
				
				IBCclassToDeleteFrame = new JFrame("Delete Class");
				IBCclassToDeleteFrame.add(verticalBox);
				IBCclassToDeleteFrame.setVisible(true);
				IBCclassToDeleteFrame.setSize(175, 100);
				IBCclassToDeleteFrame.setResizable(false);
				IBCclassToDeleteFrame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
			}
		}
	}
