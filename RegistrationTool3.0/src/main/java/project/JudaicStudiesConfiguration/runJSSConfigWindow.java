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

public class runJSSConfigWindow {

	private static JFrame JSSsetNumberOfClassesFrame;
	private static JPanel JSSpanel;
	private static Box JSSverticalBox;
	private static JTextField JSSamountOfClassesTextField; 
	private static JButton JSSbuttonToMoveOn;
	public static void main(String[] args) {
		runThis();
	}
	public static void runThis() {
		instantiateConfigureClassNumberFrame();
		configureNumberOfClassesFrame();
		buildFrame1();
	}
	
	public static void instantiateConfigureClassNumberFrame() {
		JSSsetNumberOfClassesFrame = new JFrame("Input Number of Classes");
	}
	
	public static void configureNumberOfClassesFrame() {
		JSSpanel = new JPanel(new FlowLayout(FlowLayout.LEFT, 0, 0));
		JSSpanel.setBackground(Color.black);
		JSSverticalBox = Box.createVerticalBox();
		PropertiesFile.setProps();
		String currentAmountOfJSSClasses= PropertiesFile.getNumOfNonYPClasses("JSS");
		if (currentAmountOfJSSClasses.isEmpty()) {
			currentAmountOfJSSClasses = "0";
		}
		JLabel label1 = new JLabel("current classes on file: " + currentAmountOfJSSClasses + System.lineSeparator());
		label1.setForeground(Color.white);
		JSSverticalBox.add(label1);
		
		JButton viewCurrentClasses = new JButton("View All JSS Classes"); 
		viewCurrentClasses.addActionListener(new displayAllJSSClassesOnFile());
		JButton changeIndividualClass = new JButton("Change Single JSS Class");
		changeIndividualClass.addActionListener(new changeIndividualJSSClassImplementation());
		JButton removeClass = new JButton("Delete JSS Class");
		removeClass.addActionListener(new removeJSSClassImplemetation());
		JSSverticalBox.add(viewCurrentClasses);
		JSSverticalBox.add(changeIndividualClass);
		JSSverticalBox.add(removeClass);
		
		JLabel label2 = new JLabel("New Amount of JSS Classes:");
		label2.setForeground(Color.white);
		JSSverticalBox.add(label2);
		JLabel label3 = new JLabel("The max value is 10");
		label3.setForeground(Color.white);
		JSSverticalBox.add(label3);
		
		NumberFormatter formatter = new NumberFormatter();
		JSSamountOfClassesTextField = new JFormattedTextField(formatter);
		JSSamountOfClassesTextField.setColumns(5);
		JSSverticalBox.add(JSSamountOfClassesTextField);
		
		JSSbuttonToMoveOn = new JButton("Submit");
		JSSbuttonToMoveOn.addActionListener(new openJSSClassConfigTable());
		
		JSSverticalBox.add(JSSbuttonToMoveOn);
		JSSpanel.add(JSSverticalBox);
		JSSsetNumberOfClassesFrame.add(JSSpanel);
	}
	
	public static void buildFrame1() {
		JSSsetNumberOfClassesFrame.setVisible(true);
		JSSsetNumberOfClassesFrame.setSize(200, 205);
		JSSsetNumberOfClassesFrame.setResizable(false);
		JSSsetNumberOfClassesFrame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
	}
	
	public static class openJSSClassConfigTable implements ActionListener {
		static JFrame JSSClassConfigurationFrame;
		public static int rows;
		
		static JLabel JSSClassLabel1, JSSClassLabel2, JSSClassLabel3, JSSClassLabel4, JSSClassLabel5, JSSClassLabel6, JSSClassLabel7, JSSClassLabel8, JSSClassLabel9, JSSClassLabel10;
		static JPanel JSSClassPanel1 = new JPanel (new FlowLayout(FlowLayout.LEADING, 0, 0)),
				JSSClassPanel2 = new JPanel (new FlowLayout(FlowLayout.LEADING, 0, 0)),
				JSSClassPanel3 = new JPanel (new FlowLayout(FlowLayout.LEADING, 0, 0)),
				JSSClassPanel4 = new JPanel (new FlowLayout(FlowLayout.LEADING, 0, 0)),
				JSSClassPanel5 = new JPanel (new FlowLayout(FlowLayout.LEADING, 0, 0)),
				JSSClassPanel6 = new JPanel (new FlowLayout(FlowLayout.LEADING, 0, 0)),
				JSSClassPanel7 = new JPanel (new FlowLayout(FlowLayout.LEADING, 0, 0)),
				JSSClassPanel8 = new JPanel (new FlowLayout(FlowLayout.LEADING, 0, 0)),
				JSSClassPanel9 = new JPanel (new FlowLayout(FlowLayout.LEADING, 0, 0)),
				JSSClassPanel10 = new JPanel (new FlowLayout(FlowLayout.LEADING, 0, 0));
		static JComboBox<String> JSSClass1Priority, JSSClass2Priority, JSSClass3Priority, JSSClass4Priority, JSSClass5Priority, JSSClass6Priority, JSSClass7Priority, JSSClass8Priority, JSSClass9Priority, JSSClass10Priority; 
		static JTextField JSSClass1CRN, JSSClass1JSSClassTitle, JSSClass1JSSClassSection, JSSClass1CourseNumber, JSSClass1SectionNumber;
		static JTextField JSSClass2CRN, JSSClass2JSSClassTitle, JSSClass2JSSClassSection, JSSClass2CourseNumber, JSSClass2SectionNumber;
		static JTextField JSSClass3CRN, JSSClass3JSSClassTitle, JSSClass3JSSClassSection, JSSClass3CourseNumber, JSSClass3SectionNumber;
		static JTextField JSSClass4CRN, JSSClass4JSSClassTitle, JSSClass4JSSClassSection, JSSClass4CourseNumber, JSSClass4SectionNumber;
		static JTextField JSSClass5CRN, JSSClass5JSSClassTitle, JSSClass5JSSClassSection, JSSClass5CourseNumber, JSSClass5SectionNumber;
		static JTextField JSSClass6CRN, JSSClass6JSSClassTitle, JSSClass6JSSClassSection, JSSClass6CourseNumber, JSSClass6SectionNumber;
		static JTextField JSSClass7CRN, JSSClass7JSSClassTitle, JSSClass7JSSClassSection, JSSClass7CourseNumber, JSSClass7SectionNumber;
		static JTextField JSSClass8CRN, JSSClass8JSSClassTitle, JSSClass8JSSClassSection, JSSClass8CourseNumber, JSSClass8SectionNumber;
		static JTextField JSSClass9CRN, JSSClass9JSSClassTitle, JSSClass9JSSClassSection, JSSClass9CourseNumber, JSSClass9SectionNumber;
		static JTextField JSSClass10CRN, JSSClass10JSSClassTitle, JSSClass10JSSClassSection, JSSClass10CourseNumber, JSSClass10SectionNumber;
		
		public void actionPerformed(ActionEvent e) {
			//save the info, close this frame, open a new one
			UserInput.setProps();
			String inputNumber = JSSamountOfClassesTextField.getText();
			System.out.println("[" + inputNumber + "]");
			if ((inputNumber.isEmpty()) || (Integer.parseInt(inputNumber.trim()) > 10)) {
				inputNumber = "10";
				if (inputNumber.isEmpty()) {
					inputNumber = "0";
				}
				System.out.println("Number changed to: " + inputNumber);
			}
			UserInput.setNumOfJSSClasses(inputNumber);
			UserInput.storeProps();
			JSSsetNumberOfClassesFrame.setVisible(false);
			
			setupSecondFrame(inputNumber);			
		}
		
		public static void setupSecondFrame(String numClasses) {
			JSSClassConfigurationFrame = new JFrame("JSS Class Configuration");
			rows = Integer.parseInt(numClasses);
			Box JSSverticalClassConfigBox = Box.createVerticalBox();
			JLabel JSSnumberOfClassesLabel = new JLabel("Number of JSS Classes: " + rows);
			JSSnumberOfClassesLabel.setForeground(Color.white);
			JPanel JSStopPanel = new JPanel(new FlowLayout(FlowLayout.LEFT, 0, 0));
			JSStopPanel.setBackground(Color.black);
			JSStopPanel.add(JSSnumberOfClassesLabel);
			JSSverticalClassConfigBox.add(JSStopPanel);
			
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
			JSSverticalClassConfigBox.add(titlePanel);
			JSSverticalClassConfigBox.add(examplePanel);
			
			JSSClassPanel1.setBackground(Color.black);
			JSSClassPanel2.setBackground(Color.black);
			JSSClassPanel3.setBackground(Color.black);
			JSSClassPanel4.setBackground(Color.black);
			JSSClassPanel5.setBackground(Color.black);
			JSSClassPanel6.setBackground(Color.black);
			JSSClassPanel7.setBackground(Color.black);
			JSSClassPanel8.setBackground(Color.black);
			JSSClassPanel9.setBackground(Color.black);
			JSSClassPanel10.setBackground(Color.black);
			
			int crnColumns = 7;
			int classTitleColumns = 16; 
			int classSectionColumns = 8;
			int courseNumberColumns = 8;
			int sectionNumberColumns = 8;
			
			switch (10 - rows + 1) {
				case 1: 
					JSSClassLabel10 = new JLabel("Class 10:");
					JSSClassLabel10.setForeground(Color.white);
					JSSClass10CRN = new JTextField();
					JSSClass10CRN.setColumns(crnColumns);
					JSSClass10JSSClassTitle = new JTextField();
					JSSClass10JSSClassTitle.setColumns(classTitleColumns);
					JSSClass10JSSClassSection = new JTextField();
					JSSClass10JSSClassSection.setColumns(classSectionColumns);
					JSSClass10CourseNumber = new JTextField();
					JSSClass10CourseNumber.setColumns(courseNumberColumns);
					JSSClass10SectionNumber = new JTextField();
					JSSClass10SectionNumber.setColumns(sectionNumberColumns);
					JSSClass10Priority = new JComboBox<String>(new String[] {"High", "Medium", "Low"});
					JSSClassPanel10.add(JSSClassLabel10);
					JSSClassPanel10.add(JSSClass10CRN);
					JSSClassPanel10.add(JSSClass10JSSClassTitle);
					JSSClassPanel10.add(JSSClass10JSSClassSection);
					JSSClassPanel10.add(JSSClass10CourseNumber);
					JSSClassPanel10.add(JSSClass10SectionNumber);
					JSSClassPanel10.add(JSSClass10Priority);
				case 2:
					JSSClassLabel9 = new JLabel("Class 9:");
					JSSClassLabel9.setForeground(Color.white);
					JSSClass9CRN = new JTextField();
					JSSClass9CRN.setColumns(crnColumns);
					JSSClass9JSSClassTitle = new JTextField();
					JSSClass9JSSClassTitle.setColumns(classTitleColumns);
					JSSClass9JSSClassSection = new JTextField();
					JSSClass9JSSClassSection.setColumns(classSectionColumns);
					JSSClass9CourseNumber = new JTextField();
					JSSClass9CourseNumber.setColumns(courseNumberColumns);
					JSSClass9SectionNumber = new JTextField();
					JSSClass9SectionNumber.setColumns(sectionNumberColumns);
					JSSClass9Priority = new JComboBox<String>(new String[] {"High", "Medium", "Low"});
					JSSClassPanel9.add(JSSClassLabel9);
					JSSClassPanel9.add(JSSClass9CRN);
					JSSClassPanel9.add(JSSClass9JSSClassTitle);
					JSSClassPanel9.add(JSSClass9JSSClassSection);
					JSSClassPanel9.add(JSSClass9CourseNumber);
					JSSClassPanel9.add(JSSClass9SectionNumber);
					JSSClassPanel9.add(JSSClass9Priority);
				case 3:
					JSSClassLabel8 = new JLabel("Class 8:");
					JSSClassLabel8.setForeground(Color.white);
					JSSClass8CRN = new JTextField();
					JSSClass8CRN.setColumns(crnColumns);
					JSSClass8JSSClassTitle = new JTextField();
					JSSClass8JSSClassTitle.setColumns(classTitleColumns);
					JSSClass8JSSClassSection = new JTextField();
					JSSClass8JSSClassSection.setColumns(classSectionColumns);
					JSSClass8CourseNumber = new JTextField();
					JSSClass8CourseNumber.setColumns(courseNumberColumns);
					JSSClass8SectionNumber = new JTextField();
					JSSClass8SectionNumber.setColumns(sectionNumberColumns);
					JSSClass8Priority = new JComboBox<String>(new String[] {"High", "Medium", "Low"});
					JSSClassPanel8.add(JSSClassLabel8);
					JSSClassPanel8.add(JSSClass8CRN);
					JSSClassPanel8.add(JSSClass8JSSClassTitle);
					JSSClassPanel8.add(JSSClass8JSSClassSection);
					JSSClassPanel8.add(JSSClass8CourseNumber);
					JSSClassPanel8.add(JSSClass8SectionNumber);
					JSSClassPanel8.add(JSSClass8Priority);
				case 4:
					JSSClassLabel7 = new JLabel("Class 7:");
					JSSClassLabel7.setForeground(Color.white);
					JSSClass7CRN = new JTextField();
					JSSClass7CRN.setColumns(crnColumns);
					JSSClass7JSSClassTitle = new JTextField();
					JSSClass7JSSClassTitle.setColumns(classTitleColumns);
					JSSClass7JSSClassSection = new JTextField();
					JSSClass7JSSClassSection.setColumns(classSectionColumns);
					JSSClass7CourseNumber = new JTextField();
					JSSClass7CourseNumber.setColumns(courseNumberColumns);
					JSSClass7SectionNumber = new JTextField();
					JSSClass7SectionNumber.setColumns(sectionNumberColumns);
					JSSClass7Priority = new JComboBox<String>(new String[] {"High", "Medium", "Low"});
					JSSClassPanel7.add(JSSClassLabel7);
					JSSClassPanel7.add(JSSClass7CRN);
					JSSClassPanel7.add(JSSClass7JSSClassTitle);
					JSSClassPanel7.add(JSSClass7JSSClassSection);
					JSSClassPanel7.add(JSSClass7CourseNumber);
					JSSClassPanel7.add(JSSClass7SectionNumber);
					JSSClassPanel7.add(JSSClass7Priority);
				case 5:
					JSSClassLabel6 = new JLabel("Class 6:");
					JSSClassLabel6.setForeground(Color.white);
					JSSClass6CRN = new JTextField();
					JSSClass6CRN.setColumns(crnColumns);
					JSSClass6JSSClassTitle = new JTextField();
					JSSClass6JSSClassTitle.setColumns(classTitleColumns);
					JSSClass6JSSClassSection = new JTextField();
					JSSClass6JSSClassSection.setColumns(classSectionColumns);
					JSSClass6CourseNumber = new JTextField();
					JSSClass6CourseNumber.setColumns(courseNumberColumns);
					JSSClass6SectionNumber = new JTextField();
					JSSClass6SectionNumber.setColumns(sectionNumberColumns);
					JSSClass6Priority = new JComboBox<String>(new String[] {"High", "Medium", "Low"});
					JSSClassPanel6.add(JSSClassLabel6);
					JSSClassPanel6.add(JSSClass6CRN);
					JSSClassPanel6.add(JSSClass6JSSClassTitle);
					JSSClassPanel6.add(JSSClass6JSSClassSection);
					JSSClassPanel6.add(JSSClass6CourseNumber);
					JSSClassPanel6.add(JSSClass6SectionNumber);
					JSSClassPanel6.add(JSSClass6Priority);
				case 6:
					JSSClassLabel5 = new JLabel("Class 5:");
					JSSClassLabel5.setForeground(Color.white);
					JSSClass5CRN = new JTextField();
					JSSClass5CRN.setColumns(crnColumns);
					JSSClass5JSSClassTitle = new JTextField();
					JSSClass5JSSClassTitle.setColumns(classTitleColumns);
					JSSClass5JSSClassSection = new JTextField();
					JSSClass5JSSClassSection.setColumns(classSectionColumns);
					JSSClass5CourseNumber = new JTextField();
					JSSClass5CourseNumber.setColumns(courseNumberColumns);
					JSSClass5SectionNumber = new JTextField();
					JSSClass5SectionNumber.setColumns(sectionNumberColumns);
					JSSClass5Priority = new JComboBox<String>(new String[] {"High", "Medium", "Low"});
					JSSClassPanel5.add(JSSClassLabel5);
					JSSClassPanel5.add(JSSClass5CRN);
					JSSClassPanel5.add(JSSClass5JSSClassTitle);
					JSSClassPanel5.add(JSSClass5JSSClassSection);
					JSSClassPanel5.add(JSSClass5CourseNumber);
					JSSClassPanel5.add(JSSClass5SectionNumber);
					JSSClassPanel5.add(JSSClass5Priority);
				case 7:
					JSSClassLabel4 = new JLabel("Class 4:");
					JSSClassLabel4.setForeground(Color.white);
					JSSClass4CRN = new JTextField();
					JSSClass4CRN.setColumns(crnColumns);
					JSSClass4JSSClassTitle = new JTextField();
					JSSClass4JSSClassTitle.setColumns(classTitleColumns);
					JSSClass4JSSClassSection = new JTextField();
					JSSClass4JSSClassSection.setColumns(classSectionColumns);
					JSSClass4CourseNumber = new JTextField();
					JSSClass4CourseNumber.setColumns(courseNumberColumns);
					JSSClass4SectionNumber = new JTextField();
					JSSClass4SectionNumber.setColumns(sectionNumberColumns);
					JSSClass4Priority = new JComboBox<String>(new String[] {"High", "Medium", "Low"});
					JSSClassPanel4.add(JSSClassLabel4);
					JSSClassPanel4.add(JSSClass4CRN);
					JSSClassPanel4.add(JSSClass4JSSClassTitle);
					JSSClassPanel4.add(JSSClass4JSSClassSection);
					JSSClassPanel4.add(JSSClass4CourseNumber);
					JSSClassPanel4.add(JSSClass4SectionNumber);
					JSSClassPanel4.add(JSSClass4Priority);
				case 8:
					JSSClassLabel3 = new JLabel("Class 3:");
					JSSClassLabel3.setForeground(Color.white);
					JSSClass3CRN = new JTextField();
					JSSClass3CRN.setColumns(crnColumns);
					JSSClass3JSSClassTitle = new JTextField();
					JSSClass3JSSClassTitle.setColumns(classTitleColumns);
					JSSClass3JSSClassSection = new JTextField();
					JSSClass3JSSClassSection.setColumns(classSectionColumns);
					JSSClass3CourseNumber = new JTextField();
					JSSClass3CourseNumber.setColumns(courseNumberColumns);
					JSSClass3SectionNumber = new JTextField();
					JSSClass3SectionNumber.setColumns(sectionNumberColumns);
					JSSClass3Priority = new JComboBox<String>(new String[] {"High", "Medium", "Low"});
					JSSClassPanel3.add(JSSClassLabel3);
					JSSClassPanel3.add(JSSClass3CRN);
					JSSClassPanel3.add(JSSClass3JSSClassTitle);
					JSSClassPanel3.add(JSSClass3JSSClassSection);
					JSSClassPanel3.add(JSSClass3CourseNumber);
					JSSClassPanel3.add(JSSClass3SectionNumber);
					JSSClassPanel3.add(JSSClass3Priority);
				case 9:
					JSSClassLabel2 = new JLabel("Class 2:");
					JSSClassLabel2.setForeground(Color.white);
					JSSClass2CRN = new JTextField();
					JSSClass2CRN.setColumns(crnColumns);
					JSSClass2JSSClassTitle = new JTextField();
					JSSClass2JSSClassTitle.setColumns(classTitleColumns);
					JSSClass2JSSClassSection = new JTextField();
					JSSClass2JSSClassSection.setColumns(classSectionColumns);
					JSSClass2CourseNumber = new JTextField();
					JSSClass2CourseNumber.setColumns(courseNumberColumns);
					JSSClass2SectionNumber = new JTextField();
					JSSClass2SectionNumber.setColumns(sectionNumberColumns);
					JSSClass2Priority = new JComboBox<String>(new String[] {"High", "Medium", "Low"});
					JSSClassPanel2.add(JSSClassLabel2);
					JSSClassPanel2.add(JSSClass2CRN);
					JSSClassPanel2.add(JSSClass2JSSClassTitle);
					JSSClassPanel2.add(JSSClass2JSSClassSection);
					JSSClassPanel2.add(JSSClass2CourseNumber);
					JSSClassPanel2.add(JSSClass2SectionNumber);
					JSSClassPanel2.add(JSSClass2Priority);
				case 10:
					JSSClassLabel1 = new JLabel("Class 1:");
					JSSClassLabel1.setForeground(Color.white);
					JSSClass1CRN = new JTextField();
					JSSClass1CRN.setColumns(crnColumns);
					JSSClass1JSSClassTitle = new JTextField();
					JSSClass1JSSClassTitle.setColumns(classTitleColumns);
					JSSClass1JSSClassSection = new JTextField();
					JSSClass1JSSClassSection.setColumns(classSectionColumns);
					JSSClass1CourseNumber = new JTextField();
					JSSClass1CourseNumber.setColumns(courseNumberColumns);
					JSSClass1SectionNumber = new JTextField();
					JSSClass1SectionNumber.setColumns(sectionNumberColumns);
					JSSClass1Priority = new JComboBox<String>(new String[] {"High", "Medium", "Low"});
					JSSClassPanel1.add(JSSClassLabel1);
					JSSClassPanel1.add(JSSClass1CRN);
					JSSClassPanel1.add(JSSClass1JSSClassTitle);
					JSSClassPanel1.add(JSSClass1JSSClassSection);
					JSSClassPanel1.add(JSSClass1CourseNumber);
					JSSClassPanel1.add(JSSClass1SectionNumber);
					JSSClassPanel1.add(JSSClass1Priority);
			}

			if (rows > 0) {
				JSSverticalClassConfigBox.add(JSSClassPanel1);
			}
			if (rows > 1) {
				JSSverticalClassConfigBox.add(JSSClassPanel2);
			}
			if (rows > 2) {
				JSSverticalClassConfigBox.add(JSSClassPanel3);
			}
			if (rows > 3) {
				JSSverticalClassConfigBox.add(JSSClassPanel4);
			}
			if (rows > 4) {
				JSSverticalClassConfigBox.add(JSSClassPanel5);
			}
			if (rows > 5) {
				JSSverticalClassConfigBox.add(JSSClassPanel6);
			}
			if (rows > 6) {
				JSSverticalClassConfigBox.add(JSSClassPanel7);
			}
			if (rows > 7) {
				JSSverticalClassConfigBox.add(JSSClassPanel8);
			}
			if (rows > 8) {
				JSSverticalClassConfigBox.add(JSSClassPanel9);
			}
			if (rows > 9) {
				JSSverticalClassConfigBox.add(JSSClassPanel10);
			}
			
			JButton JSSsubmitClasses = new JButton("Submit");
			JSSsubmitClasses.addActionListener(new collectJSSConfigClassesData());
			JPanel buttonPanel = new JPanel();
			buttonPanel.setBackground(Color.black);
			buttonPanel.add(JSSsubmitClasses);
			JSSverticalClassConfigBox.add(buttonPanel);
			
			JSSClassConfigurationFrame.add(JSSverticalClassConfigBox);
			
			JSSClassConfigurationFrame.setVisible(true);
			JSSClassConfigurationFrame.setSize(700, 100 + (29*rows) + 10);
			JSSClassConfigurationFrame.setResizable(false);
			JSSClassConfigurationFrame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		}
		
		public static class collectJSSConfigClassesData implements ActionListener {
			
			public void actionPerformed(ActionEvent e) {
				int numOfClasses = rows;
				String[][] allJSSClassInfo = new String[rows][6];
				
				
				
				switch (10 - numOfClasses + 1) {
					case 1:
						allJSSClassInfo[9][0] = JSSClass10CRN.getText();
						allJSSClassInfo[9][1] = JSSClass10JSSClassTitle.getText();
						allJSSClassInfo[9][2] = JSSClass10JSSClassSection.getText();
						allJSSClassInfo[9][3] = JSSClass10CourseNumber.getText();
						allJSSClassInfo[9][4] = JSSClass10SectionNumber.getText();
						allJSSClassInfo[9][5] = (String)JSSClass10Priority.getSelectedItem();
					case 2:
						allJSSClassInfo[8][0] = JSSClass9CRN.getText();
						allJSSClassInfo[8][1] = JSSClass9JSSClassTitle.getText();
						allJSSClassInfo[8][2] = JSSClass9JSSClassSection.getText();
						allJSSClassInfo[8][3] = JSSClass9CourseNumber.getText();
						allJSSClassInfo[8][4] = JSSClass9SectionNumber.getText();
						allJSSClassInfo[8][5] = (String)JSSClass9Priority.getSelectedItem();
					case 3:
						allJSSClassInfo[7][0] = JSSClass8CRN.getText();
						allJSSClassInfo[7][1] = JSSClass8JSSClassTitle.getText();
						allJSSClassInfo[7][2] = JSSClass8JSSClassSection.getText();
						allJSSClassInfo[7][3] = JSSClass8CourseNumber.getText();
						allJSSClassInfo[7][4] = JSSClass8SectionNumber.getText();
						allJSSClassInfo[7][5] = (String)JSSClass8Priority.getSelectedItem();
					case 4:
						allJSSClassInfo[6][0] = JSSClass7CRN.getText();
						allJSSClassInfo[6][1] = JSSClass7JSSClassTitle.getText();
						allJSSClassInfo[6][2] = JSSClass7JSSClassSection.getText();
						allJSSClassInfo[6][3] = JSSClass7CourseNumber.getText();
						allJSSClassInfo[6][4] = JSSClass7SectionNumber.getText();
						allJSSClassInfo[6][5] = (String)JSSClass7Priority.getSelectedItem();
					case 5:
						allJSSClassInfo[5][0] = JSSClass6CRN.getText();
						allJSSClassInfo[5][1] = JSSClass6JSSClassTitle.getText();
						allJSSClassInfo[5][2] = JSSClass6JSSClassSection.getText();
						allJSSClassInfo[5][3] = JSSClass6CourseNumber.getText();
						allJSSClassInfo[5][4] = JSSClass6SectionNumber.getText();
						allJSSClassInfo[5][5] = (String)JSSClass6Priority.getSelectedItem();
					case 6:
						allJSSClassInfo[4][0] = JSSClass5CRN.getText();
						allJSSClassInfo[4][1] = JSSClass5JSSClassTitle.getText();
						allJSSClassInfo[4][2] = JSSClass5JSSClassSection.getText();
						allJSSClassInfo[4][3] = JSSClass5CourseNumber.getText();
						allJSSClassInfo[4][4] = JSSClass5SectionNumber.getText();
						allJSSClassInfo[4][5] = (String)JSSClass5Priority.getSelectedItem();
					case 7:
						allJSSClassInfo[3][0] = JSSClass4CRN.getText();
						allJSSClassInfo[3][1] = JSSClass4JSSClassTitle.getText();
						allJSSClassInfo[3][2] = JSSClass4JSSClassSection.getText();
						allJSSClassInfo[3][3] = JSSClass4CourseNumber.getText();
						allJSSClassInfo[3][4] = JSSClass4SectionNumber.getText();
						allJSSClassInfo[3][5] = (String)JSSClass3Priority.getSelectedItem();
					case 8:
						allJSSClassInfo[2][0] = JSSClass3CRN.getText();
						allJSSClassInfo[2][1] = JSSClass3JSSClassTitle.getText();
						allJSSClassInfo[2][2] = JSSClass3JSSClassSection.getText();
						allJSSClassInfo[2][3] = JSSClass3CourseNumber.getText();
						allJSSClassInfo[2][4] = JSSClass3SectionNumber.getText();
						allJSSClassInfo[2][5] = (String)JSSClass3Priority.getSelectedItem();
					case 9:
						allJSSClassInfo[1][0] = JSSClass2CRN.getText();
						allJSSClassInfo[1][1] = JSSClass2JSSClassTitle.getText();
						allJSSClassInfo[1][2] = JSSClass2JSSClassSection.getText();
						allJSSClassInfo[1][3] = JSSClass2CourseNumber.getText();
						allJSSClassInfo[1][4] = JSSClass2SectionNumber.getText();
						allJSSClassInfo[1][5] = (String)JSSClass2Priority.getSelectedItem();
					case 10:
						allJSSClassInfo[0][0] = JSSClass1CRN.getText();
						allJSSClassInfo[0][1] = JSSClass1JSSClassTitle.getText();
						allJSSClassInfo[0][2] = JSSClass1JSSClassSection.getText();
						allJSSClassInfo[0][3] = JSSClass1CourseNumber.getText();
						allJSSClassInfo[0][4] = JSSClass1SectionNumber.getText();
						allJSSClassInfo[0][5] = (String)JSSClass1Priority.getSelectedItem();
				}
				UserInput.setProps();
				UserInput.writeAllNewJSSClasses(allJSSClassInfo);
				UserInput.storeProps();
				JSSClassConfigurationFrame.dispose();
				runJSSConfigWindow.main(null);
				
			}
			
		}
	}
		public static class displayAllJSSClassesOnFile implements ActionListener {
			
			public void actionPerformed(ActionEvent e) {
				PropertiesFile.setProps();
				String[][] currentJSSClassInfo = PropertiesFile.getNonYPClassInfo("JSS");//[x][0] = Subject      (MAT)
																			//[x][1] = Class        (Data Structures)  
																			//[x][2] = Course Number(1320)
																			//[x][3] = Section      (311)
																			//[x][4] = CRN          (42170)
																			//[x][5] = Priority     (High)
				JLabel[][] JSSclassInfoLabels = new JLabel[currentJSSClassInfo.length][7];
				for (int x = 0; x < currentJSSClassInfo.length; x++) {
					JSSclassInfoLabels[x][0]= new JLabel("Class " + (x+1));
					JSSclassInfoLabels[x][1]= new JLabel(currentJSSClassInfo[x][0]);
					JSSclassInfoLabels[x][2]= new JLabel(currentJSSClassInfo[x][1]);
					JSSclassInfoLabels[x][3]= new JLabel(currentJSSClassInfo[x][2]);
					JSSclassInfoLabels[x][4]= new JLabel(currentJSSClassInfo[x][3]);
					JSSclassInfoLabels[x][5]= new JLabel(currentJSSClassInfo[x][4]);
					JSSclassInfoLabels[x][6]= new JLabel(currentJSSClassInfo[x][5]);
					
					JSSclassInfoLabels[x][0].setForeground(Color.cyan);
					JSSclassInfoLabels[x][1].setForeground(Color.white);
					JSSclassInfoLabels[x][2].setForeground(Color.white);
					JSSclassInfoLabels[x][3].setForeground(Color.white);
					JSSclassInfoLabels[x][4].setForeground(Color.white);
					JSSclassInfoLabels[x][5].setForeground(Color.white);
					JSSclassInfoLabels[x][6].setForeground(Color.white);
					
					JSSclassInfoLabels[x][0].setPreferredSize(new Dimension(50,  15));
					JSSclassInfoLabels[x][1].setPreferredSize(new Dimension(35,  15));
					JSSclassInfoLabels[x][2].setPreferredSize(new Dimension(220, 15));
					JSSclassInfoLabels[x][3].setPreferredSize(new Dimension(38,  15));
					JSSclassInfoLabels[x][4].setPreferredSize(new Dimension(35,  15));
					JSSclassInfoLabels[x][5].setPreferredSize(new Dimension(35,  15));
					JSSclassInfoLabels[x][6].setPreferredSize(new Dimension(45,  15));
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
				
				
				JPanel[] panelArrayWithJSSLabels = new JPanel[JSSclassInfoLabels.length];
				for (int x = 0; x < panelArrayWithJSSLabels.length; x++) {
					panelArrayWithJSSLabels[x] = new JPanel(new FlowLayout(FlowLayout.LEFT, 5, 0));
					panelArrayWithJSSLabels[x].setBackground(Color.black);
					panelArrayWithJSSLabels[x].add(JSSclassInfoLabels[x][0]);
					panelArrayWithJSSLabels[x].add(JSSclassInfoLabels[x][1]);
					panelArrayWithJSSLabels[x].add(JSSclassInfoLabels[x][2]);
					panelArrayWithJSSLabels[x].add(JSSclassInfoLabels[x][3]);
					panelArrayWithJSSLabels[x].add(JSSclassInfoLabels[x][4]);
					panelArrayWithJSSLabels[x].add(JSSclassInfoLabels[x][5]);
					panelArrayWithJSSLabels[x].add(JSSclassInfoLabels[x][6]);
				}
				Box verticalBox = Box.createVerticalBox();
				verticalBox.add(topLevelPanel);
				for(int x = 0; x < panelArrayWithJSSLabels.length; x++) {
					verticalBox.add(panelArrayWithJSSLabels[x]);
				}
				JFrame ClassDisplay = new JFrame("Display Classes On File");
				ClassDisplay.add(verticalBox);
				ClassDisplay.setVisible(true);
				ClassDisplay.setSize(500, panelArrayWithJSSLabels.length * 40 + 40);
				ClassDisplay.setResizable(false);
				ClassDisplay.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
				
			}
		}
		
		public static class changeIndividualJSSClassImplementation implements ActionListener {
			static JComboBox<String> JSSclassToChange, JSSclassYPriority;
			static JTextField JSSclassYCRN, JSSclassYClassTitle, JSSclassYClassSection, JSSclassYCourseNumber, JSSclassYSectionNumber;
			static JFrame JSSnewClassFrame;
			
			public void actionPerformed(ActionEvent e) {
				PropertiesFile.setProps();
				int classNum = Integer.parseInt(PropertiesFile.getNumOfNonYPClasses("JSS"));
				String[] choices = new String[classNum + 1];//add extra spot to be able to add a class onto what already exists
				for(int x = 0; x < (classNum + 1) ; x++ ) {
					choices[x] = "" + (x+1);
				}
				JSSclassToChange = new JComboBox<String>(choices);
				JLabel label1 = new JLabel("Change Class: ");
				label1.setForeground(Color.white);
				JPanel topPanel = new JPanel(new FlowLayout(FlowLayout.LEFT, 10, 0));
				topPanel.add(label1);
				topPanel.add(JSSclassToChange);
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
				JSSclassYCRN = new JTextField();
				JSSclassYCRN.setColumns(7);
				JSSclassYClassTitle = new JTextField();
				JSSclassYClassTitle.setColumns(16);
				JSSclassYClassSection = new JTextField();
				JSSclassYClassSection.setColumns(8);
				JSSclassYCourseNumber = new JTextField();
				JSSclassYCourseNumber.setColumns(8);
				JSSclassYSectionNumber = new JTextField();
				JSSclassYSectionNumber.setColumns(8);
				JSSclassYPriority = new JComboBox<String>(new String[] {"High", "Medium", "Low"});
				JPanel newClassInfoPanel = new JPanel(new FlowLayout(FlowLayout.LEADING, 0, 0));
				newClassInfoPanel.setBackground(Color.black);
				newClassInfoPanel.add(classYLabel);
				newClassInfoPanel.add(JSSclassYCRN);
				newClassInfoPanel.add(JSSclassYClassTitle);
				newClassInfoPanel.add(JSSclassYClassSection);
				newClassInfoPanel.add(JSSclassYCourseNumber);
				newClassInfoPanel.add(JSSclassYSectionNumber);
				newClassInfoPanel.add(JSSclassYPriority);
				
				JButton submit = new JButton("Submit");
				submit.addActionListener(new sendNewJSSClassInfoToFile());
				JPanel bottomPanel = new JPanel();
				bottomPanel.setBackground(Color.black);
				bottomPanel.add(submit);
				
				Box verticalBox = Box.createVerticalBox();
				verticalBox.add(topPanel);
				verticalBox.add(titlePanel);
				verticalBox.add(examplePanel);
				verticalBox.add(newClassInfoPanel);
				verticalBox.add(bottomPanel);
				
				JSSnewClassFrame = new JFrame("New JSS Class Info");
				JSSnewClassFrame.add(verticalBox);
				JSSnewClassFrame.setVisible(true);
				JSSnewClassFrame.setSize(675, 150);
				JSSnewClassFrame.setResizable(false);
				JSSnewClassFrame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
			}
		}
		public static class sendNewJSSClassInfoToFile implements ActionListener {
			
			public void actionPerformed(ActionEvent e) {
				String[] newClassInfo= {(String)changeIndividualJSSClassImplementation.JSSclassToChange.getSelectedItem(),
						changeIndividualJSSClassImplementation.JSSclassYCRN.getText(),
						changeIndividualJSSClassImplementation.JSSclassYClassTitle.getText(),
						changeIndividualJSSClassImplementation.JSSclassYClassSection.getText(),
						changeIndividualJSSClassImplementation.JSSclassYCourseNumber.getText(),
						changeIndividualJSSClassImplementation.JSSclassYSectionNumber.getText(),
										(String)changeIndividualJSSClassImplementation.JSSclassYPriority.getSelectedItem()};
			UserInput.editSingleJSSClass(newClassInfo);
			changeIndividualJSSClassImplementation.JSSnewClassFrame.dispose();
			JSSsetNumberOfClassesFrame.dispose();
			runJSSConfigWindow.main(null);
			}
		}
		
		public static class removeJSSClassImplemetation implements ActionListener {
			static JComboBox<String> JSSclassToDelete;
			private static JFrame JSSclassToDeleteFrame;
			
			public void actionPerformed(ActionEvent e) {
				PropertiesFile.setProps();
				int classNum = Integer.parseInt(PropertiesFile.getNumOfNonYPClasses("JSS"));
				String[] choices = new String[classNum];
				for(int x = 0; x < (classNum) ; x++ ) {
					choices[x] = "" + (x+1);
				}
				JSSclassToDelete = new JComboBox<String>(choices);
				JLabel label1 = new JLabel("Delete Class: ");
				label1.setForeground(Color.white);
				JPanel topPanel = new JPanel(new FlowLayout(FlowLayout.LEFT, 10, 0));
				topPanel.add(label1);
				topPanel.add(JSSclassToDelete);
				topPanel.setBackground(Color.black);
				
				JButton submit = new JButton("Submit");
				submit.addActionListener(new ActionListener() {
					public void actionPerformed(ActionEvent e) {
						UserInput.deleteJSSClass((String)JSSclassToDelete.getSelectedItem());
						JSSclassToDeleteFrame.dispose();
						JSSsetNumberOfClassesFrame.dispose();
						runJSSConfigWindow.main(null);
					}
				});
				JPanel bottomPanel = new JPanel();
				bottomPanel.setBackground(Color.black);
				bottomPanel.add(submit);
				
				Box verticalBox = Box.createVerticalBox();
				verticalBox.add(topPanel);
				verticalBox.add(bottomPanel);
				
				JSSclassToDeleteFrame = new JFrame("Delete Class");
				JSSclassToDeleteFrame.add(verticalBox);
				JSSclassToDeleteFrame.setVisible(true);
				JSSclassToDeleteFrame.setSize(175, 100);
				JSSclassToDeleteFrame.setResizable(false);
				JSSclassToDeleteFrame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
			}
		}
	}
