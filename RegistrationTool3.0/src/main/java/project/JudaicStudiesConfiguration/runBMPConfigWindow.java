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

public class runBMPConfigWindow {

	private static JFrame BMPsetNumberOfClassesFrame;
	private static JPanel BMPpanel;
	private static Box BMPverticalBox;
	private static JTextField BMPamountOfClassesTextField; 
	private static JButton BMPbuttonToMoveOn;
	public static void main(String[] args) {
		runThis();
	}
	public static void runThis() {
		instantiateConfigureClassNumberFrame();
		configureNumberOfClassesFrame();
		buildFrame1();
	}
	
	public static void instantiateConfigureClassNumberFrame() {
		BMPsetNumberOfClassesFrame = new JFrame("Input Number of Classes");
	}
	
	public static void configureNumberOfClassesFrame() {
		BMPpanel = new JPanel(new FlowLayout(FlowLayout.LEFT, 0, 0));
		BMPpanel.setBackground(Color.black);
		BMPverticalBox = Box.createVerticalBox();
		PropertiesFile.setProps();
		String currentAmountOfBMPClasses= PropertiesFile.getNumOfNonYPClasses("BMP");
		if (currentAmountOfBMPClasses.isEmpty()) {
			currentAmountOfBMPClasses = "0";
		}
		JLabel label1 = new JLabel("current classes on file: " + currentAmountOfBMPClasses + System.lineSeparator());
		label1.setForeground(Color.white);
		BMPverticalBox.add(label1);
		
		JButton viewCurrentClasses = new JButton("View All BMP Classes"); 
		viewCurrentClasses.addActionListener(new displayAllBMPClassesOnFile());
		JButton changeIndividualClass = new JButton("Change Single BMP Class");
		changeIndividualClass.addActionListener(new changeIndividualBMPClassImplementation());
		JButton removeClass = new JButton("Delete BMP Class");
		removeClass.addActionListener(new removeBMPClassImplemetation());
		BMPverticalBox.add(viewCurrentClasses);
		BMPverticalBox.add(changeIndividualClass);
		BMPverticalBox.add(removeClass);
		
		JLabel label2 = new JLabel("New Amount of BMP Classes:");
		label2.setForeground(Color.white);
		BMPverticalBox.add(label2);
		JLabel label3 = new JLabel("The max value is 10");
		label3.setForeground(Color.white);
		BMPverticalBox.add(label3);
		
		NumberFormatter formatter = new NumberFormatter();
		BMPamountOfClassesTextField = new JFormattedTextField(formatter);
		BMPamountOfClassesTextField.setColumns(5);
		BMPverticalBox.add(BMPamountOfClassesTextField);
		
		BMPbuttonToMoveOn = new JButton("Submit");
		BMPbuttonToMoveOn.addActionListener(new openBMPClassConfigTable());
		
		BMPverticalBox.add(BMPbuttonToMoveOn);
		BMPpanel.add(BMPverticalBox);
		BMPsetNumberOfClassesFrame.add(BMPpanel);
	}
	
	public static void buildFrame1() {
		BMPsetNumberOfClassesFrame.setVisible(true);
		BMPsetNumberOfClassesFrame.setSize(200, 205);
		BMPsetNumberOfClassesFrame.setResizable(false);
		BMPsetNumberOfClassesFrame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
	}
	
	public static class openBMPClassConfigTable implements ActionListener {
		static JFrame BMPClassConfigurationFrame;
		public static int rows;
		
		static JLabel BMPClassLabel1, BMPClassLabel2, BMPClassLabel3, BMPClassLabel4, BMPClassLabel5, BMPClassLabel6, BMPClassLabel7, BMPClassLabel8, BMPClassLabel9, BMPClassLabel10;
		static JPanel BMPClassPanel1 = new JPanel (new FlowLayout(FlowLayout.LEADING, 0, 0)),
				BMPClassPanel2 = new JPanel (new FlowLayout(FlowLayout.LEADING, 0, 0)),
				BMPClassPanel3 = new JPanel (new FlowLayout(FlowLayout.LEADING, 0, 0)),
				BMPClassPanel4 = new JPanel (new FlowLayout(FlowLayout.LEADING, 0, 0)),
				BMPClassPanel5 = new JPanel (new FlowLayout(FlowLayout.LEADING, 0, 0)),
				BMPClassPanel6 = new JPanel (new FlowLayout(FlowLayout.LEADING, 0, 0)),
				BMPClassPanel7 = new JPanel (new FlowLayout(FlowLayout.LEADING, 0, 0)),
				BMPClassPanel8 = new JPanel (new FlowLayout(FlowLayout.LEADING, 0, 0)),
				BMPClassPanel9 = new JPanel (new FlowLayout(FlowLayout.LEADING, 0, 0)),
				BMPClassPanel10 = new JPanel (new FlowLayout(FlowLayout.LEADING, 0, 0));
		static JComboBox<String> BMPClass1Priority, BMPClass2Priority, BMPClass3Priority, BMPClass4Priority, BMPClass5Priority, BMPClass6Priority, BMPClass7Priority, BMPClass8Priority, BMPClass9Priority, BMPClass10Priority; 
		static JTextField BMPClass1CRN, BMPClass1BMPClassTitle, BMPClass1BMPClassSection, BMPClass1CourseNumber, BMPClass1SectionNumber;
		static JTextField BMPClass2CRN, BMPClass2BMPClassTitle, BMPClass2BMPClassSection, BMPClass2CourseNumber, BMPClass2SectionNumber;
		static JTextField BMPClass3CRN, BMPClass3BMPClassTitle, BMPClass3BMPClassSection, BMPClass3CourseNumber, BMPClass3SectionNumber;
		static JTextField BMPClass4CRN, BMPClass4BMPClassTitle, BMPClass4BMPClassSection, BMPClass4CourseNumber, BMPClass4SectionNumber;
		static JTextField BMPClass5CRN, BMPClass5BMPClassTitle, BMPClass5BMPClassSection, BMPClass5CourseNumber, BMPClass5SectionNumber;
		static JTextField BMPClass6CRN, BMPClass6BMPClassTitle, BMPClass6BMPClassSection, BMPClass6CourseNumber, BMPClass6SectionNumber;
		static JTextField BMPClass7CRN, BMPClass7BMPClassTitle, BMPClass7BMPClassSection, BMPClass7CourseNumber, BMPClass7SectionNumber;
		static JTextField BMPClass8CRN, BMPClass8BMPClassTitle, BMPClass8BMPClassSection, BMPClass8CourseNumber, BMPClass8SectionNumber;
		static JTextField BMPClass9CRN, BMPClass9BMPClassTitle, BMPClass9BMPClassSection, BMPClass9CourseNumber, BMPClass9SectionNumber;
		static JTextField BMPClass10CRN, BMPClass10BMPClassTitle, BMPClass10BMPClassSection, BMPClass10CourseNumber, BMPClass10SectionNumber;
		public void actionPerformed(ActionEvent e) {
			//save the info, close this frame, open a new one
			UserInput.setProps();
			String inputNumber = BMPamountOfClassesTextField.getText();
			System.out.println("[" + inputNumber + "]");
			if ((inputNumber.isEmpty()) || (Integer.parseInt(inputNumber.trim()) > 10)) {
				inputNumber = "10";
				if (inputNumber.isEmpty()) {
					inputNumber = "0";
				}
				System.out.println("Number changed to: " + inputNumber);
			}
			UserInput.setNumOfBMPClasses(inputNumber);
			UserInput.storeProps();
			BMPsetNumberOfClassesFrame.setVisible(false);
			
			setupSecondFrame(inputNumber);			
		}
		
		public static void setupSecondFrame(String numClasses) {
			BMPClassConfigurationFrame = new JFrame("BMP Class Configuration");
			rows = Integer.parseInt(numClasses);
			Box BMPverticalClassConfigBox = Box.createVerticalBox();
			JLabel BMPnumberOfClassesLabel = new JLabel("Number of BMP Classes: " + rows);
			BMPnumberOfClassesLabel.setForeground(Color.white);
			JPanel BMPtopPanel = new JPanel(new FlowLayout(FlowLayout.LEFT, 0, 0));
			BMPtopPanel.setBackground(Color.black);
			BMPtopPanel.add(BMPnumberOfClassesLabel);
			BMPverticalClassConfigBox.add(BMPtopPanel);
			
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
			BMPverticalClassConfigBox.add(titlePanel);
			BMPverticalClassConfigBox.add(examplePanel);
			
			BMPClassPanel1.setBackground(Color.black);
			BMPClassPanel2.setBackground(Color.black);
			BMPClassPanel3.setBackground(Color.black);
			BMPClassPanel4.setBackground(Color.black);
			BMPClassPanel5.setBackground(Color.black);
			BMPClassPanel6.setBackground(Color.black);
			BMPClassPanel7.setBackground(Color.black);
			BMPClassPanel8.setBackground(Color.black);
			BMPClassPanel9.setBackground(Color.black);
			BMPClassPanel10.setBackground(Color.black);
			
			int crnColumns = 7;
			int classTitleColumns = 16; 
			int classSectionColumns = 8;
			int courseNumberColumns = 8;
			int sectionNumberColumns = 8;
			
			switch (10 - rows + 1) {
				case 1: 
					BMPClassLabel10 = new JLabel("Class 10:");
					BMPClassLabel10.setForeground(Color.white);
					BMPClass10CRN = new JTextField();
					BMPClass10CRN.setColumns(crnColumns);
					BMPClass10BMPClassTitle = new JTextField();
					BMPClass10BMPClassTitle.setColumns(classTitleColumns);
					BMPClass10BMPClassSection = new JTextField();
					BMPClass10BMPClassSection.setColumns(classSectionColumns);
					BMPClass10CourseNumber = new JTextField();
					BMPClass10CourseNumber.setColumns(courseNumberColumns);
					BMPClass10SectionNumber = new JTextField();
					BMPClass10SectionNumber.setColumns(sectionNumberColumns);
					BMPClass10Priority = new JComboBox<String>(new String[] {"High", "Medium", "Low"});
					BMPClassPanel10.add(BMPClassLabel10);
					BMPClassPanel10.add(BMPClass10CRN);
					BMPClassPanel10.add(BMPClass10BMPClassTitle);
					BMPClassPanel10.add(BMPClass10BMPClassSection);
					BMPClassPanel10.add(BMPClass10CourseNumber);
					BMPClassPanel10.add(BMPClass10SectionNumber);
					BMPClassPanel10.add(BMPClass10Priority);
				case 2:
					BMPClassLabel9 = new JLabel("Class 9:");
					BMPClassLabel9.setForeground(Color.white);
					BMPClass9CRN = new JTextField();
					BMPClass9CRN.setColumns(crnColumns);
					BMPClass9BMPClassTitle = new JTextField();
					BMPClass9BMPClassTitle.setColumns(classTitleColumns);
					BMPClass9BMPClassSection = new JTextField();
					BMPClass9BMPClassSection.setColumns(classSectionColumns);
					BMPClass9CourseNumber = new JTextField();
					BMPClass9CourseNumber.setColumns(courseNumberColumns);
					BMPClass9SectionNumber = new JTextField();
					BMPClass9SectionNumber.setColumns(sectionNumberColumns);
					BMPClass9Priority = new JComboBox<String>(new String[] {"High", "Medium", "Low"});
					BMPClassPanel9.add(BMPClassLabel9);
					BMPClassPanel9.add(BMPClass9CRN);
					BMPClassPanel9.add(BMPClass9BMPClassTitle);
					BMPClassPanel9.add(BMPClass9BMPClassSection);
					BMPClassPanel9.add(BMPClass9CourseNumber);
					BMPClassPanel9.add(BMPClass9SectionNumber);
					BMPClassPanel9.add(BMPClass9Priority);
				case 3:
					BMPClassLabel8 = new JLabel("Class 8:");
					BMPClassLabel8.setForeground(Color.white);
					BMPClass8CRN = new JTextField();
					BMPClass8CRN.setColumns(crnColumns);
					BMPClass8BMPClassTitle = new JTextField();
					BMPClass8BMPClassTitle.setColumns(classTitleColumns);
					BMPClass8BMPClassSection = new JTextField();
					BMPClass8BMPClassSection.setColumns(classSectionColumns);
					BMPClass8CourseNumber = new JTextField();
					BMPClass8CourseNumber.setColumns(courseNumberColumns);
					BMPClass8SectionNumber = new JTextField();
					BMPClass8SectionNumber.setColumns(sectionNumberColumns);
					BMPClass8Priority = new JComboBox<String>(new String[] {"High", "Medium", "Low"});
					BMPClassPanel8.add(BMPClassLabel8);
					BMPClassPanel8.add(BMPClass8CRN);
					BMPClassPanel8.add(BMPClass8BMPClassTitle);
					BMPClassPanel8.add(BMPClass8BMPClassSection);
					BMPClassPanel8.add(BMPClass8CourseNumber);
					BMPClassPanel8.add(BMPClass8SectionNumber);
					BMPClassPanel8.add(BMPClass8Priority);
				case 4:
					BMPClassLabel7 = new JLabel("Class 7:");
					BMPClassLabel7.setForeground(Color.white);
					BMPClass7CRN = new JTextField();
					BMPClass7CRN.setColumns(crnColumns);
					BMPClass7BMPClassTitle = new JTextField();
					BMPClass7BMPClassTitle.setColumns(classTitleColumns);
					BMPClass7BMPClassSection = new JTextField();
					BMPClass7BMPClassSection.setColumns(classSectionColumns);
					BMPClass7CourseNumber = new JTextField();
					BMPClass7CourseNumber.setColumns(courseNumberColumns);
					BMPClass7SectionNumber = new JTextField();
					BMPClass7SectionNumber.setColumns(sectionNumberColumns);
					BMPClass7Priority = new JComboBox<String>(new String[] {"High", "Medium", "Low"});
					BMPClassPanel7.add(BMPClassLabel7);
					BMPClassPanel7.add(BMPClass7CRN);
					BMPClassPanel7.add(BMPClass7BMPClassTitle);
					BMPClassPanel7.add(BMPClass7BMPClassSection);
					BMPClassPanel7.add(BMPClass7CourseNumber);
					BMPClassPanel7.add(BMPClass7SectionNumber);
					BMPClassPanel7.add(BMPClass7Priority);
				case 5:
					BMPClassLabel6 = new JLabel("Class 6:");
					BMPClassLabel6.setForeground(Color.white);
					BMPClass6CRN = new JTextField();
					BMPClass6CRN.setColumns(crnColumns);
					BMPClass6BMPClassTitle = new JTextField();
					BMPClass6BMPClassTitle.setColumns(classTitleColumns);
					BMPClass6BMPClassSection = new JTextField();
					BMPClass6BMPClassSection.setColumns(classSectionColumns);
					BMPClass6CourseNumber = new JTextField();
					BMPClass6CourseNumber.setColumns(courseNumberColumns);
					BMPClass6SectionNumber = new JTextField();
					BMPClass6SectionNumber.setColumns(sectionNumberColumns);
					BMPClass6Priority = new JComboBox<String>(new String[] {"High", "Medium", "Low"});
					BMPClassPanel6.add(BMPClassLabel6);
					BMPClassPanel6.add(BMPClass6CRN);
					BMPClassPanel6.add(BMPClass6BMPClassTitle);
					BMPClassPanel6.add(BMPClass6BMPClassSection);
					BMPClassPanel6.add(BMPClass6CourseNumber);
					BMPClassPanel6.add(BMPClass6SectionNumber);
					BMPClassPanel6.add(BMPClass6Priority);
				case 6:
					BMPClassLabel5 = new JLabel("Class 5:");
					BMPClassLabel5.setForeground(Color.white);
					BMPClass5CRN = new JTextField();
					BMPClass5CRN.setColumns(crnColumns);
					BMPClass5BMPClassTitle = new JTextField();
					BMPClass5BMPClassTitle.setColumns(classTitleColumns);
					BMPClass5BMPClassSection = new JTextField();
					BMPClass5BMPClassSection.setColumns(classSectionColumns);
					BMPClass5CourseNumber = new JTextField();
					BMPClass5CourseNumber.setColumns(courseNumberColumns);
					BMPClass5SectionNumber = new JTextField();
					BMPClass5SectionNumber.setColumns(sectionNumberColumns);
					BMPClass5Priority = new JComboBox<String>(new String[] {"High", "Medium", "Low"});
					BMPClassPanel5.add(BMPClassLabel5);
					BMPClassPanel5.add(BMPClass5CRN);
					BMPClassPanel5.add(BMPClass5BMPClassTitle);
					BMPClassPanel5.add(BMPClass5BMPClassSection);
					BMPClassPanel5.add(BMPClass5CourseNumber);
					BMPClassPanel5.add(BMPClass5SectionNumber);
					BMPClassPanel5.add(BMPClass5Priority);
				case 7:
					BMPClassLabel4 = new JLabel("Class 4:");
					BMPClassLabel4.setForeground(Color.white);
					BMPClass4CRN = new JTextField();
					BMPClass4CRN.setColumns(crnColumns);
					BMPClass4BMPClassTitle = new JTextField();
					BMPClass4BMPClassTitle.setColumns(classTitleColumns);
					BMPClass4BMPClassSection = new JTextField();
					BMPClass4BMPClassSection.setColumns(classSectionColumns);
					BMPClass4CourseNumber = new JTextField();
					BMPClass4CourseNumber.setColumns(courseNumberColumns);
					BMPClass4SectionNumber = new JTextField();
					BMPClass4SectionNumber.setColumns(sectionNumberColumns);
					BMPClass4Priority = new JComboBox<String>(new String[] {"High", "Medium", "Low"});
					BMPClassPanel4.add(BMPClassLabel4);
					BMPClassPanel4.add(BMPClass4CRN);
					BMPClassPanel4.add(BMPClass4BMPClassTitle);
					BMPClassPanel4.add(BMPClass4BMPClassSection);
					BMPClassPanel4.add(BMPClass4CourseNumber);
					BMPClassPanel4.add(BMPClass4SectionNumber);
					BMPClassPanel4.add(BMPClass4Priority);
				case 8:
					BMPClassLabel3 = new JLabel("Class 3:");
					BMPClassLabel3.setForeground(Color.white);
					BMPClass3CRN = new JTextField();
					BMPClass3CRN.setColumns(crnColumns);
					BMPClass3BMPClassTitle = new JTextField();
					BMPClass3BMPClassTitle.setColumns(classTitleColumns);
					BMPClass3BMPClassSection = new JTextField();
					BMPClass3BMPClassSection.setColumns(classSectionColumns);
					BMPClass3CourseNumber = new JTextField();
					BMPClass3CourseNumber.setColumns(courseNumberColumns);
					BMPClass3SectionNumber = new JTextField();
					BMPClass3SectionNumber.setColumns(sectionNumberColumns);
					BMPClass3Priority = new JComboBox<String>(new String[] {"High", "Medium", "Low"});
					BMPClassPanel3.add(BMPClassLabel3);
					BMPClassPanel3.add(BMPClass3CRN);
					BMPClassPanel3.add(BMPClass3BMPClassTitle);
					BMPClassPanel3.add(BMPClass3BMPClassSection);
					BMPClassPanel3.add(BMPClass3CourseNumber);
					BMPClassPanel3.add(BMPClass3SectionNumber);
					BMPClassPanel3.add(BMPClass3Priority);
				case 9:
					BMPClassLabel2 = new JLabel("Class 2:");
					BMPClassLabel2.setForeground(Color.white);
					BMPClass2CRN = new JTextField();
					BMPClass2CRN.setColumns(crnColumns);
					BMPClass2BMPClassTitle = new JTextField();
					BMPClass2BMPClassTitle.setColumns(classTitleColumns);
					BMPClass2BMPClassSection = new JTextField();
					BMPClass2BMPClassSection.setColumns(classSectionColumns);
					BMPClass2CourseNumber = new JTextField();
					BMPClass2CourseNumber.setColumns(courseNumberColumns);
					BMPClass2SectionNumber = new JTextField();
					BMPClass2SectionNumber.setColumns(sectionNumberColumns);
					BMPClass2Priority = new JComboBox<String>(new String[] {"High", "Medium", "Low"});
					BMPClassPanel2.add(BMPClassLabel2);
					BMPClassPanel2.add(BMPClass2CRN);
					BMPClassPanel2.add(BMPClass2BMPClassTitle);
					BMPClassPanel2.add(BMPClass2BMPClassSection);
					BMPClassPanel2.add(BMPClass2CourseNumber);
					BMPClassPanel2.add(BMPClass2SectionNumber);
					BMPClassPanel2.add(BMPClass2Priority);
				case 10:
					BMPClassLabel1 = new JLabel("Class 1:");
					BMPClassLabel1.setForeground(Color.white);
					BMPClass1CRN = new JTextField();
					BMPClass1CRN.setColumns(crnColumns);
					BMPClass1BMPClassTitle = new JTextField();
					BMPClass1BMPClassTitle.setColumns(classTitleColumns);
					BMPClass1BMPClassSection = new JTextField();
					BMPClass1BMPClassSection.setColumns(classSectionColumns);
					BMPClass1CourseNumber = new JTextField();
					BMPClass1CourseNumber.setColumns(courseNumberColumns);
					BMPClass1SectionNumber = new JTextField();
					BMPClass1SectionNumber.setColumns(sectionNumberColumns);
					BMPClass1Priority = new JComboBox<String>(new String[] {"High", "Medium", "Low"});
					BMPClassPanel1.add(BMPClassLabel1);
					BMPClassPanel1.add(BMPClass1CRN);
					BMPClassPanel1.add(BMPClass1BMPClassTitle);
					BMPClassPanel1.add(BMPClass1BMPClassSection);
					BMPClassPanel1.add(BMPClass1CourseNumber);
					BMPClassPanel1.add(BMPClass1SectionNumber);
					BMPClassPanel1.add(BMPClass1Priority);
			}

			if (rows > 0) {
				BMPverticalClassConfigBox.add(BMPClassPanel1);
			}
			if (rows > 1) {
				BMPverticalClassConfigBox.add(BMPClassPanel2);
			}
			if (rows > 2) {
				BMPverticalClassConfigBox.add(BMPClassPanel3);
			}
			if (rows > 3) {
				BMPverticalClassConfigBox.add(BMPClassPanel4);
			}
			if (rows > 4) {
				BMPverticalClassConfigBox.add(BMPClassPanel5);
			}
			if (rows > 5) {
				BMPverticalClassConfigBox.add(BMPClassPanel6);
			}
			if (rows > 6) {
				BMPverticalClassConfigBox.add(BMPClassPanel7);
			}
			if (rows > 7) {
				BMPverticalClassConfigBox.add(BMPClassPanel8);
			}
			if (rows > 8) {
				BMPverticalClassConfigBox.add(BMPClassPanel9);
			}
			if (rows > 9) {
				BMPverticalClassConfigBox.add(BMPClassPanel10);
			}
			
			JButton BMPsubmitClasses = new JButton("Submit");
			BMPsubmitClasses.addActionListener(new collectBMPConfigClassesData());
			JPanel buttonPanel = new JPanel();
			buttonPanel.setBackground(Color.black);
			buttonPanel.add(BMPsubmitClasses);
			BMPverticalClassConfigBox.add(buttonPanel);
			
			BMPClassConfigurationFrame.add(BMPverticalClassConfigBox);
			
			BMPClassConfigurationFrame.setVisible(true);
			BMPClassConfigurationFrame.setSize(700, 100 + (29*rows) + 10);
			BMPClassConfigurationFrame.setResizable(false);
			BMPClassConfigurationFrame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		}
		
		public static class collectBMPConfigClassesData implements ActionListener {
			
			public void actionPerformed(ActionEvent e) {
				int numOfClasses = rows;
				String[][] allBMPClassInfo = new String[rows][6];
				
				
				
				switch (10 - numOfClasses + 1) {
					case 1:
						allBMPClassInfo[9][0] = BMPClass10CRN.getText();
						allBMPClassInfo[9][1] = BMPClass10BMPClassTitle.getText();
						allBMPClassInfo[9][2] = BMPClass10BMPClassSection.getText();
						allBMPClassInfo[9][3] = BMPClass10CourseNumber.getText();
						allBMPClassInfo[9][4] = BMPClass10SectionNumber.getText();
						allBMPClassInfo[9][5] = (String)BMPClass10Priority.getSelectedItem();
					case 2:
						allBMPClassInfo[8][0] = BMPClass9CRN.getText();
						allBMPClassInfo[8][1] = BMPClass9BMPClassTitle.getText();
						allBMPClassInfo[8][2] = BMPClass9BMPClassSection.getText();
						allBMPClassInfo[8][3] = BMPClass9CourseNumber.getText();
						allBMPClassInfo[8][4] = BMPClass9SectionNumber.getText();
						allBMPClassInfo[8][5] = (String)BMPClass9Priority.getSelectedItem();
					case 3:
						allBMPClassInfo[7][0] = BMPClass8CRN.getText();
						allBMPClassInfo[7][1] = BMPClass8BMPClassTitle.getText();
						allBMPClassInfo[7][2] = BMPClass8BMPClassSection.getText();
						allBMPClassInfo[7][3] = BMPClass8CourseNumber.getText();
						allBMPClassInfo[7][4] = BMPClass8SectionNumber.getText();
						allBMPClassInfo[7][5] = (String)BMPClass8Priority.getSelectedItem();
					case 4:
						allBMPClassInfo[6][0] = BMPClass7CRN.getText();
						allBMPClassInfo[6][1] = BMPClass7BMPClassTitle.getText();
						allBMPClassInfo[6][2] = BMPClass7BMPClassSection.getText();
						allBMPClassInfo[6][3] = BMPClass7CourseNumber.getText();
						allBMPClassInfo[6][4] = BMPClass7SectionNumber.getText();
						allBMPClassInfo[6][5] = (String)BMPClass7Priority.getSelectedItem();
					case 5:
						allBMPClassInfo[5][0] = BMPClass6CRN.getText();
						allBMPClassInfo[5][1] = BMPClass6BMPClassTitle.getText();
						allBMPClassInfo[5][2] = BMPClass6BMPClassSection.getText();
						allBMPClassInfo[5][3] = BMPClass6CourseNumber.getText();
						allBMPClassInfo[5][4] = BMPClass6SectionNumber.getText();
						allBMPClassInfo[5][5] = (String)BMPClass6Priority.getSelectedItem();
					case 6:
						allBMPClassInfo[4][0] = BMPClass5CRN.getText();
						allBMPClassInfo[4][1] = BMPClass5BMPClassTitle.getText();
						allBMPClassInfo[4][2] = BMPClass5BMPClassSection.getText();
						allBMPClassInfo[4][3] = BMPClass5CourseNumber.getText();
						allBMPClassInfo[4][4] = BMPClass5SectionNumber.getText();
						allBMPClassInfo[4][5] = (String)BMPClass5Priority.getSelectedItem();
					case 7:
						allBMPClassInfo[3][0] = BMPClass4CRN.getText();
						allBMPClassInfo[3][1] = BMPClass4BMPClassTitle.getText();
						allBMPClassInfo[3][2] = BMPClass4BMPClassSection.getText();
						allBMPClassInfo[3][3] = BMPClass4CourseNumber.getText();
						allBMPClassInfo[3][4] = BMPClass4SectionNumber.getText();
						allBMPClassInfo[3][5] = (String)BMPClass3Priority.getSelectedItem();
					case 8:
						allBMPClassInfo[2][0] = BMPClass3CRN.getText();
						allBMPClassInfo[2][1] = BMPClass3BMPClassTitle.getText();
						allBMPClassInfo[2][2] = BMPClass3BMPClassSection.getText();
						allBMPClassInfo[2][3] = BMPClass3CourseNumber.getText();
						allBMPClassInfo[2][4] = BMPClass3SectionNumber.getText();
						allBMPClassInfo[2][5] = (String)BMPClass3Priority.getSelectedItem();
					case 9:
						allBMPClassInfo[1][0] = BMPClass2CRN.getText();
						allBMPClassInfo[1][1] = BMPClass2BMPClassTitle.getText();
						allBMPClassInfo[1][2] = BMPClass2BMPClassSection.getText();
						allBMPClassInfo[1][3] = BMPClass2CourseNumber.getText();
						allBMPClassInfo[1][4] = BMPClass2SectionNumber.getText();
						allBMPClassInfo[1][5] = (String)BMPClass2Priority.getSelectedItem();
					case 10:
						allBMPClassInfo[0][0] = BMPClass1CRN.getText();
						allBMPClassInfo[0][1] = BMPClass1BMPClassTitle.getText();
						allBMPClassInfo[0][2] = BMPClass1BMPClassSection.getText();
						allBMPClassInfo[0][3] = BMPClass1CourseNumber.getText();
						allBMPClassInfo[0][4] = BMPClass1SectionNumber.getText();
						allBMPClassInfo[0][5] = (String)BMPClass1Priority.getSelectedItem();
				}
				UserInput.setProps();
				UserInput.writeAllNewBMPClasses(allBMPClassInfo);
				UserInput.storeProps();
				BMPClassConfigurationFrame.dispose();
				runBMPConfigWindow.main(null);
				
			}
			
		}
	}
		public static class displayAllBMPClassesOnFile implements ActionListener {
			
			public void actionPerformed(ActionEvent e) {
				PropertiesFile.setProps();
				String[][] currentBMPClassInfo = PropertiesFile.getNonYPClassInfo("BMP");//[x][0] = Subject      (MAT)
																			//[x][1] = Class        (Data Structures)  
																			//[x][2] = Course Number(1320)
																			//[x][3] = Section      (311)
																			//[x][4] = CRN          (42170)
																			//[x][5] = Priority     (High)
				JLabel[][] BMPclassInfoLabels = new JLabel[currentBMPClassInfo.length][7];
				for (int x = 0; x < currentBMPClassInfo.length; x++) {
					BMPclassInfoLabels[x][0]= new JLabel("Class " + (x+1));
					BMPclassInfoLabels[x][1]= new JLabel(currentBMPClassInfo[x][0]);
					BMPclassInfoLabels[x][2]= new JLabel(currentBMPClassInfo[x][1]);
					BMPclassInfoLabels[x][3]= new JLabel(currentBMPClassInfo[x][2]);
					BMPclassInfoLabels[x][4]= new JLabel(currentBMPClassInfo[x][3]);
					BMPclassInfoLabels[x][5]= new JLabel(currentBMPClassInfo[x][4]);
					BMPclassInfoLabels[x][6]= new JLabel(currentBMPClassInfo[x][5]);
					
					BMPclassInfoLabels[x][0].setForeground(Color.cyan);
					BMPclassInfoLabels[x][1].setForeground(Color.white);
					BMPclassInfoLabels[x][2].setForeground(Color.white);
					BMPclassInfoLabels[x][3].setForeground(Color.white);
					BMPclassInfoLabels[x][4].setForeground(Color.white);
					BMPclassInfoLabels[x][5].setForeground(Color.white);
					BMPclassInfoLabels[x][6].setForeground(Color.white);
					
					BMPclassInfoLabels[x][0].setPreferredSize(new Dimension(50,  15));
					BMPclassInfoLabels[x][1].setPreferredSize(new Dimension(35,  15));
					BMPclassInfoLabels[x][2].setPreferredSize(new Dimension(220, 15));
					BMPclassInfoLabels[x][3].setPreferredSize(new Dimension(38,  15));
					BMPclassInfoLabels[x][4].setPreferredSize(new Dimension(35,  15));
					BMPclassInfoLabels[x][5].setPreferredSize(new Dimension(35,  15));
					BMPclassInfoLabels[x][6].setPreferredSize(new Dimension(45,  15));
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
				
				
				JPanel[] panelArrayWithBMPLabels = new JPanel[BMPclassInfoLabels.length];
				for (int x = 0; x < panelArrayWithBMPLabels.length; x++) {
					panelArrayWithBMPLabels[x] = new JPanel(new FlowLayout(FlowLayout.LEFT, 5, 0));
					panelArrayWithBMPLabels[x].setBackground(Color.black);
					panelArrayWithBMPLabels[x].add(BMPclassInfoLabels[x][0]);
					panelArrayWithBMPLabels[x].add(BMPclassInfoLabels[x][1]);
					panelArrayWithBMPLabels[x].add(BMPclassInfoLabels[x][2]);
					panelArrayWithBMPLabels[x].add(BMPclassInfoLabels[x][3]);
					panelArrayWithBMPLabels[x].add(BMPclassInfoLabels[x][4]);
					panelArrayWithBMPLabels[x].add(BMPclassInfoLabels[x][5]);
					panelArrayWithBMPLabels[x].add(BMPclassInfoLabels[x][6]);
				}
				Box verticalBox = Box.createVerticalBox();
				verticalBox.add(topLevelPanel);
				for(int x = 0; x < panelArrayWithBMPLabels.length; x++) {
					verticalBox.add(panelArrayWithBMPLabels[x]);
				}
				JFrame ClassDisplay = new JFrame("Display Classes On File");
				ClassDisplay.add(verticalBox);
				ClassDisplay.setVisible(true);
				ClassDisplay.setSize(500, panelArrayWithBMPLabels.length * 40 + 40);
				ClassDisplay.setResizable(false);
				ClassDisplay.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
				
			}
		}
		
		public static class changeIndividualBMPClassImplementation implements ActionListener {
			static JComboBox<String> BMPclassToChange, BMPclassYPriority;
			static JTextField BMPclassYCRN, BMPclassYClassTitle, BMPclassYClassSection, BMPclassYCourseNumber, BMPclassYSectionNumber;
			static JFrame BMPnewClassFrame;
			
			public void actionPerformed(ActionEvent e) {
				PropertiesFile.setProps();
				int classNum = Integer.parseInt(PropertiesFile.getNumOfNonYPClasses("BMP"));
				String[] choices = new String[classNum + 1];//add extra spot to be able to add a class onto what already exists
				for(int x = 0; x < (classNum + 1) ; x++ ) {
					choices[x] = "" + (x+1);
				}
				BMPclassToChange = new JComboBox<String>(choices);
				JLabel label1 = new JLabel("Change Class: ");
				label1.setForeground(Color.white);
				JPanel topPanel = new JPanel(new FlowLayout(FlowLayout.LEFT, 10, 0));
				topPanel.add(label1);
				topPanel.add(BMPclassToChange);
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
				BMPclassYCRN = new JTextField();
				BMPclassYCRN.setColumns(7);
				BMPclassYClassTitle = new JTextField();
				BMPclassYClassTitle.setColumns(16);
				BMPclassYClassSection = new JTextField();
				BMPclassYClassSection.setColumns(8);
				BMPclassYCourseNumber = new JTextField();
				BMPclassYCourseNumber.setColumns(8);
				BMPclassYSectionNumber = new JTextField();
				BMPclassYSectionNumber.setColumns(8);
				BMPclassYPriority = new JComboBox<String>(new String[] {"High", "Medium", "Low"});
				JPanel newClassInfoPanel = new JPanel(new FlowLayout(FlowLayout.LEADING, 0, 0));
				newClassInfoPanel.setBackground(Color.black);
				newClassInfoPanel.add(classYLabel);
				newClassInfoPanel.add(BMPclassYCRN);
				newClassInfoPanel.add(BMPclassYClassTitle);
				newClassInfoPanel.add(BMPclassYClassSection);
				newClassInfoPanel.add(BMPclassYCourseNumber);
				newClassInfoPanel.add(BMPclassYSectionNumber);
				newClassInfoPanel.add(BMPclassYPriority);
				
				JButton submit = new JButton("Submit");
				submit.addActionListener(new sendNewBMPClassInfoToFile());
				JPanel bottomPanel = new JPanel();
				bottomPanel.setBackground(Color.black);
				bottomPanel.add(submit);
				
				Box verticalBox = Box.createVerticalBox();
				verticalBox.add(topPanel);
				verticalBox.add(titlePanel);
				verticalBox.add(examplePanel);
				verticalBox.add(newClassInfoPanel);
				verticalBox.add(bottomPanel);
				
				BMPnewClassFrame = new JFrame("New BMP Class Info");
				BMPnewClassFrame.add(verticalBox);
				BMPnewClassFrame.setVisible(true);
				BMPnewClassFrame.setSize(675, 150);
				BMPnewClassFrame.setResizable(false);
				BMPnewClassFrame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
			}
		}
		public static class sendNewBMPClassInfoToFile implements ActionListener {
			
			public void actionPerformed(ActionEvent e) {
				String[] newClassInfo= {(String)changeIndividualBMPClassImplementation.BMPclassToChange.getSelectedItem(),
						changeIndividualBMPClassImplementation.BMPclassYCRN.getText(),
						changeIndividualBMPClassImplementation.BMPclassYClassTitle.getText(),
						changeIndividualBMPClassImplementation.BMPclassYClassSection.getText(),
						changeIndividualBMPClassImplementation.BMPclassYCourseNumber.getText(),
						changeIndividualBMPClassImplementation.BMPclassYSectionNumber.getText(),
										(String)changeIndividualBMPClassImplementation.BMPclassYPriority.getSelectedItem()};
			UserInput.editSingleBMPClass(newClassInfo);
			changeIndividualBMPClassImplementation.BMPnewClassFrame.dispose();
			BMPsetNumberOfClassesFrame.dispose();
			runBMPConfigWindow.main(null);
			}
		}
		
		public static class removeBMPClassImplemetation implements ActionListener {
			static JComboBox<String> BMPclassToDelete;
			private static JFrame BMPclassToDeleteFrame;
			
			public void actionPerformed(ActionEvent e) {
				PropertiesFile.setProps();
				int classNum = Integer.parseInt(PropertiesFile.getNumOfNonYPClasses("BMP"));
				String[] choices = new String[classNum];
				for(int x = 0; x < (classNum) ; x++ ) {
					choices[x] = "" + (x+1);
				}
				BMPclassToDelete = new JComboBox<String>(choices);
				JLabel label1 = new JLabel("Delete Class: ");
				label1.setForeground(Color.white);
				JPanel topPanel = new JPanel(new FlowLayout(FlowLayout.LEFT, 10, 0));
				topPanel.add(label1);
				topPanel.add(BMPclassToDelete);
				topPanel.setBackground(Color.black);
				
				JButton submit = new JButton("Submit");
				submit.addActionListener(new ActionListener() {
					public void actionPerformed(ActionEvent e) {
						UserInput.deleteBMPClass((String)BMPclassToDelete.getSelectedItem());
						BMPclassToDeleteFrame.dispose();
						BMPsetNumberOfClassesFrame.dispose();
						runBMPConfigWindow.main(null);
					}
				});
				JPanel bottomPanel = new JPanel();
				bottomPanel.setBackground(Color.black);
				bottomPanel.add(submit);
				
				Box verticalBox = Box.createVerticalBox();
				verticalBox.add(topPanel);
				verticalBox.add(bottomPanel);
				
				BMPclassToDeleteFrame = new JFrame("Delete Class");
				BMPclassToDeleteFrame.add(verticalBox);
				BMPclassToDeleteFrame.setVisible(true);
				BMPclassToDeleteFrame.setSize(175, 100);
				BMPclassToDeleteFrame.setResizable(false);
				BMPclassToDeleteFrame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
			}
		}
	}
