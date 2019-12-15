package project.ButtonConfiguration;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.event.*;
import javax.swing.*;
import javax.swing.text.*;
import project.config.PropertiesFile;
import project.writing.UserInput;

public class SetConfigureClassesButtonImplementation {

	private static JFrame setNumberOfClassesFrame;
	private static JPanel panel;
	private static Box verticalBox;
	private static JTextField amountOfClassesTextField; 
	private static JButton buttonToMoveOn;
	public static void main(String[] args) {
		runThis();
	}
	public static void runThis() {
		instantiateConfigureClassNumberFrame();
		configureNumberOfClassesFrame();
		buildFrame1();
	}
	
	public static void instantiateConfigureClassNumberFrame() {
		setNumberOfClassesFrame = new JFrame("Input Number of Classes");
	}
	
	public static void configureNumberOfClassesFrame() {
		panel = new JPanel(new FlowLayout(FlowLayout.LEFT, 0, 0));
		panel.setBackground(Color.black);
		verticalBox = Box.createVerticalBox();
		PropertiesFile.setProps();
		String currentAmountOfClasses= PropertiesFile.getNumOfClasses();
		if (currentAmountOfClasses.isEmpty()) {
			currentAmountOfClasses = "0";
		}
		JLabel label1 = new JLabel("current classes on file: " + currentAmountOfClasses + System.lineSeparator());
		label1.setForeground(Color.white);
		verticalBox.add(label1);
		
		JButton viewCurrentClasses = new JButton("View All Classes"); 
		viewCurrentClasses.addActionListener(new displayAllClassesOnFile());
		JButton changeIndividualClass = new JButton("Change Single Class");
		changeIndividualClass.addActionListener(new changeIndividualClassImplementation());
		JButton removeClass = new JButton("Delete Class");
		removeClass.addActionListener(new removeClassImplemetation());
		verticalBox.add(viewCurrentClasses);
		verticalBox.add(changeIndividualClass);
		verticalBox.add(removeClass);
		
		JLabel label2 = new JLabel("New Amount of Classes:");
		label2.setForeground(Color.white);
		verticalBox.add(label2);
		JLabel label3 = new JLabel("The max value is 10");
		label3.setForeground(Color.white);
		verticalBox.add(label3);
		
		NumberFormatter formatter = new NumberFormatter();
		amountOfClassesTextField = new JFormattedTextField(formatter);
		amountOfClassesTextField.setColumns(5);
		verticalBox.add(amountOfClassesTextField);
		
		buttonToMoveOn = new JButton("Submit");
		buttonToMoveOn.addActionListener(new openClassConfigTable());
		
		verticalBox.add(buttonToMoveOn);
		panel.add(verticalBox);
		setNumberOfClassesFrame.add(panel);
	}
	
	public static void buildFrame1() {
		setNumberOfClassesFrame.setVisible(true);
		setNumberOfClassesFrame.setSize(200, 205);
		setNumberOfClassesFrame.setResizable(false);
		setNumberOfClassesFrame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
	}


	public static class openClassConfigTable implements ActionListener {
		static JFrame classConfigurationFrame;
		public static int rows;
		
		static JLabel classLabel1, classLabel2, classLabel3, classLabel4, classLabel5, classLabel6, classLabel7, classLabel8, classLabel9, classLabel10;
		static JPanel classPanel1 = new JPanel (new FlowLayout(FlowLayout.LEADING, 0, 0)),
				classPanel2 = new JPanel (new FlowLayout(FlowLayout.LEADING, 0, 0)),
				classPanel3 = new JPanel (new FlowLayout(FlowLayout.LEADING, 0, 0)),
				classPanel4 = new JPanel (new FlowLayout(FlowLayout.LEADING, 0, 0)),
				classPanel5 = new JPanel (new FlowLayout(FlowLayout.LEADING, 0, 0)),
				classPanel6 = new JPanel (new FlowLayout(FlowLayout.LEADING, 0, 0)),
				classPanel7 = new JPanel (new FlowLayout(FlowLayout.LEADING, 0, 0)),
				classPanel8 = new JPanel (new FlowLayout(FlowLayout.LEADING, 0, 0)),
				classPanel9 = new JPanel (new FlowLayout(FlowLayout.LEADING, 0, 0)),
				classPanel10 = new JPanel (new FlowLayout(FlowLayout.LEADING, 0, 0));
		static JComboBox<String> class1Priority, class2Priority, class3Priority, class4Priority, class5Priority, class6Priority, class7Priority, class8Priority, class9Priority, class10Priority; 
		static JTextField class1CRN, class1ClassTitle, class1ClassSection, class1CourseNumber, class1SectionNumber;
		static JTextField class2CRN, class2ClassTitle, class2ClassSection, class2CourseNumber, class2SectionNumber;
		static JTextField class3CRN, class3ClassTitle, class3ClassSection, class3CourseNumber, class3SectionNumber;
		static JTextField class4CRN, class4ClassTitle, class4ClassSection, class4CourseNumber, class4SectionNumber;
		static JTextField class5CRN, class5ClassTitle, class5ClassSection, class5CourseNumber, class5SectionNumber;
		static JTextField class6CRN, class6ClassTitle, class6ClassSection, class6CourseNumber, class6SectionNumber;
		static JTextField class7CRN, class7ClassTitle, class7ClassSection, class7CourseNumber, class7SectionNumber;
		static JTextField class8CRN, class8ClassTitle, class8ClassSection, class8CourseNumber, class8SectionNumber;
		static JTextField class9CRN, class9ClassTitle, class9ClassSection, class9CourseNumber, class9SectionNumber;
		static JTextField class10CRN, class10ClassTitle, class10ClassSection, class10CourseNumber, class10SectionNumber;
		public void actionPerformed(ActionEvent e) {
			//save the info, close this frame, open a new one
			UserInput.setProps();
			String inputNumber = amountOfClassesTextField.getText();
			//System.out.println("[" + inputNumber + "]");
			if ((inputNumber.isEmpty()) || (Integer.parseInt(inputNumber.trim()) > 10)) {
				inputNumber = "10";
				if (inputNumber.isEmpty()) {
					inputNumber = "0";
				}
				//System.out.println("Number changed to: " + inputNumber);
			}
			UserInput.setNumOfClasses(inputNumber);
			UserInput.storeProps();
			setNumberOfClassesFrame.setVisible(false);
			
			setupSecondFrame(inputNumber);			
		}
		
		public static void setupSecondFrame(String numClasses) {
			classConfigurationFrame = new JFrame("Class Configuration");
			rows = Integer.parseInt(numClasses);
			Box verticalClassConfigBox = Box.createVerticalBox();
			JLabel numberOfClassesLabel = new JLabel("Number of Classes: " + rows);
			numberOfClassesLabel.setForeground(Color.white);
			JPanel topPanel = new JPanel(new FlowLayout(FlowLayout.LEFT, 0, 0));
			topPanel.setBackground(Color.black);
			topPanel.add(numberOfClassesLabel);
			verticalClassConfigBox.add(topPanel);
			
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
			verticalClassConfigBox.add(titlePanel);
			verticalClassConfigBox.add(examplePanel);
			
			classPanel1.setBackground(Color.black);
			classPanel2.setBackground(Color.black);
			classPanel3.setBackground(Color.black);
			classPanel4.setBackground(Color.black);
			classPanel5.setBackground(Color.black);
			classPanel6.setBackground(Color.black);
			classPanel7.setBackground(Color.black);
			classPanel8.setBackground(Color.black);
			classPanel9.setBackground(Color.black);
			classPanel10.setBackground(Color.black);
			
			int crnColumns = 7;
			int classTitleColumns = 16; 
			int classSectionColumns = 8;
			int courseNumberColumns = 8;
			int sectionNumberColumns = 8;
			
			switch (10 - rows + 1) {
				case 1: 
					classLabel10 = new JLabel("Class 10:");
					classLabel10.setForeground(Color.white);
					class10CRN = new JTextField();
					class10CRN.setColumns(crnColumns);
					class10ClassTitle = new JTextField();
					class10ClassTitle.setColumns(classTitleColumns);
					class10ClassSection = new JTextField();
					class10ClassSection.setColumns(classSectionColumns);
					class10CourseNumber = new JTextField();
					class10CourseNumber.setColumns(courseNumberColumns);
					class10SectionNumber = new JTextField();
					class10SectionNumber.setColumns(sectionNumberColumns);
					class10Priority = new JComboBox<String>(new String[] {"High", "Medium", "Low"});
					classPanel10.add(classLabel10);
					classPanel10.add(class10CRN);
					classPanel10.add(class10ClassTitle);
					classPanel10.add(class10ClassSection);
					classPanel10.add(class10CourseNumber);
					classPanel10.add(class10SectionNumber);
					classPanel10.add(class10Priority);
				case 2:
					classLabel9 = new JLabel("Class 9:");
					classLabel9.setForeground(Color.white);
					class9CRN = new JTextField();
					class9CRN.setColumns(crnColumns);
					class9ClassTitle = new JTextField();
					class9ClassTitle.setColumns(classTitleColumns);
					class9ClassSection = new JTextField();
					class9ClassSection.setColumns(classSectionColumns);
					class9CourseNumber = new JTextField();
					class9CourseNumber.setColumns(courseNumberColumns);
					class9SectionNumber = new JTextField();
					class9SectionNumber.setColumns(sectionNumberColumns);
					class9Priority = new JComboBox<String>(new String[] {"High", "Medium", "Low"});
					classPanel9.add(classLabel9);
					classPanel9.add(class9CRN);
					classPanel9.add(class9ClassTitle);
					classPanel9.add(class9ClassSection);
					classPanel9.add(class9CourseNumber);
					classPanel9.add(class9SectionNumber);
					classPanel9.add(class9Priority);
				case 3:
					classLabel8 = new JLabel("Class 8:");
					classLabel8.setForeground(Color.white);
					class8CRN = new JTextField();
					class8CRN.setColumns(crnColumns);
					class8ClassTitle = new JTextField();
					class8ClassTitle.setColumns(classTitleColumns);
					class8ClassSection = new JTextField();
					class8ClassSection.setColumns(classSectionColumns);
					class8CourseNumber = new JTextField();
					class8CourseNumber.setColumns(courseNumberColumns);
					class8SectionNumber = new JTextField();
					class8SectionNumber.setColumns(sectionNumberColumns);
					class8Priority = new JComboBox<String>(new String[] {"High", "Medium", "Low"});
					classPanel8.add(classLabel8);
					classPanel8.add(class8CRN);
					classPanel8.add(class8ClassTitle);
					classPanel8.add(class8ClassSection);
					classPanel8.add(class8CourseNumber);
					classPanel8.add(class8SectionNumber);
					classPanel8.add(class8Priority);
				case 4:
					classLabel7 = new JLabel("Class 7:");
					classLabel7.setForeground(Color.white);
					class7CRN = new JTextField();
					class7CRN.setColumns(crnColumns);
					class7ClassTitle = new JTextField();
					class7ClassTitle.setColumns(classTitleColumns);
					class7ClassSection = new JTextField();
					class7ClassSection.setColumns(classSectionColumns);
					class7CourseNumber = new JTextField();
					class7CourseNumber.setColumns(courseNumberColumns);
					class7SectionNumber = new JTextField();
					class7SectionNumber.setColumns(sectionNumberColumns);
					class7Priority = new JComboBox<String>(new String[] {"High", "Medium", "Low"});
					classPanel7.add(classLabel7);
					classPanel7.add(class7CRN);
					classPanel7.add(class7ClassTitle);
					classPanel7.add(class7ClassSection);
					classPanel7.add(class7CourseNumber);
					classPanel7.add(class7SectionNumber);
					classPanel7.add(class7Priority);
				case 5:
					classLabel6 = new JLabel("Class 6:");
					classLabel6.setForeground(Color.white);
					class6CRN = new JTextField();
					class6CRN.setColumns(crnColumns);
					class6ClassTitle = new JTextField();
					class6ClassTitle.setColumns(classTitleColumns);
					class6ClassSection = new JTextField();
					class6ClassSection.setColumns(classSectionColumns);
					class6CourseNumber = new JTextField();
					class6CourseNumber.setColumns(courseNumberColumns);
					class6SectionNumber = new JTextField();
					class6SectionNumber.setColumns(sectionNumberColumns);
					class6Priority = new JComboBox<String>(new String[] {"High", "Medium", "Low"});
					classPanel6.add(classLabel6);
					classPanel6.add(class6CRN);
					classPanel6.add(class6ClassTitle);
					classPanel6.add(class6ClassSection);
					classPanel6.add(class6CourseNumber);
					classPanel6.add(class6SectionNumber);
					classPanel6.add(class6Priority);
				case 6:
					classLabel5 = new JLabel("Class 5:");
					classLabel5.setForeground(Color.white);
					class5CRN = new JTextField();
					class5CRN.setColumns(crnColumns);
					class5ClassTitle = new JTextField();
					class5ClassTitle.setColumns(classTitleColumns);
					class5ClassSection = new JTextField();
					class5ClassSection.setColumns(classSectionColumns);
					class5CourseNumber = new JTextField();
					class5CourseNumber.setColumns(courseNumberColumns);
					class5SectionNumber = new JTextField();
					class5SectionNumber.setColumns(sectionNumberColumns);
					class5Priority = new JComboBox<String>(new String[] {"High", "Medium", "Low"});
					classPanel5.add(classLabel5);
					classPanel5.add(class5CRN);
					classPanel5.add(class5ClassTitle);
					classPanel5.add(class5ClassSection);
					classPanel5.add(class5CourseNumber);
					classPanel5.add(class5SectionNumber);
					classPanel5.add(class5Priority);
				case 7:
					classLabel4 = new JLabel("Class 4:");
					classLabel4.setForeground(Color.white);
					class4CRN = new JTextField();
					class4CRN.setColumns(crnColumns);
					class4ClassTitle = new JTextField();
					class4ClassTitle.setColumns(classTitleColumns);
					class4ClassSection = new JTextField();
					class4ClassSection.setColumns(classSectionColumns);
					class4CourseNumber = new JTextField();
					class4CourseNumber.setColumns(courseNumberColumns);
					class4SectionNumber = new JTextField();
					class4SectionNumber.setColumns(sectionNumberColumns);
					class4Priority = new JComboBox<String>(new String[] {"High", "Medium", "Low"});
					classPanel4.add(classLabel4);
					classPanel4.add(class4CRN);
					classPanel4.add(class4ClassTitle);
					classPanel4.add(class4ClassSection);
					classPanel4.add(class4CourseNumber);
					classPanel4.add(class4SectionNumber);
					classPanel4.add(class4Priority);
				case 8:
					classLabel3 = new JLabel("Class 3:");
					classLabel3.setForeground(Color.white);
					class3CRN = new JTextField();
					class3CRN.setColumns(crnColumns);
					class3ClassTitle = new JTextField();
					class3ClassTitle.setColumns(classTitleColumns);
					class3ClassSection = new JTextField();
					class3ClassSection.setColumns(classSectionColumns);
					class3CourseNumber = new JTextField();
					class3CourseNumber.setColumns(courseNumberColumns);
					class3SectionNumber = new JTextField();
					class3SectionNumber.setColumns(sectionNumberColumns);
					class3Priority = new JComboBox<String>(new String[] {"High", "Medium", "Low"});
					classPanel3.add(classLabel3);
					classPanel3.add(class3CRN);
					classPanel3.add(class3ClassTitle);
					classPanel3.add(class3ClassSection);
					classPanel3.add(class3CourseNumber);
					classPanel3.add(class3SectionNumber);
					classPanel3.add(class3Priority);
				case 9:
					classLabel2 = new JLabel("Class 2:");
					classLabel2.setForeground(Color.white);
					class2CRN = new JTextField();
					class2CRN.setColumns(crnColumns);
					class2ClassTitle = new JTextField();
					class2ClassTitle.setColumns(classTitleColumns);
					class2ClassSection = new JTextField();
					class2ClassSection.setColumns(classSectionColumns);
					class2CourseNumber = new JTextField();
					class2CourseNumber.setColumns(courseNumberColumns);
					class2SectionNumber = new JTextField();
					class2SectionNumber.setColumns(sectionNumberColumns);
					class2Priority = new JComboBox<String>(new String[] {"High", "Medium", "Low"});
					classPanel2.add(classLabel2);
					classPanel2.add(class2CRN);
					classPanel2.add(class2ClassTitle);
					classPanel2.add(class2ClassSection);
					classPanel2.add(class2CourseNumber);
					classPanel2.add(class2SectionNumber);
					classPanel2.add(class2Priority);
				case 10:
					classLabel1 = new JLabel("Class 1:");
					classLabel1.setForeground(Color.white);
					class1CRN = new JTextField();
					class1CRN.setColumns(crnColumns);
					class1ClassTitle = new JTextField();
					class1ClassTitle.setColumns(classTitleColumns);
					class1ClassSection = new JTextField();
					class1ClassSection.setColumns(classSectionColumns);
					class1CourseNumber = new JTextField();
					class1CourseNumber.setColumns(courseNumberColumns);
					class1SectionNumber = new JTextField();
					class1SectionNumber.setColumns(sectionNumberColumns);
					class1Priority = new JComboBox<String>(new String[] {"High", "Medium", "Low"});
					classPanel1.add(classLabel1);
					classPanel1.add(class1CRN);
					classPanel1.add(class1ClassTitle);
					classPanel1.add(class1ClassSection);
					classPanel1.add(class1CourseNumber);
					classPanel1.add(class1SectionNumber);
					classPanel1.add(class1Priority);
			}

			if (rows > 0) {
				verticalClassConfigBox.add(classPanel1);
			}
			if (rows > 1) {
				verticalClassConfigBox.add(classPanel2);
			}
			if (rows > 2) {
				verticalClassConfigBox.add(classPanel3);
			}
			if (rows > 3) {
				verticalClassConfigBox.add(classPanel4);
			}
			if (rows > 4) {
				verticalClassConfigBox.add(classPanel5);
			}
			if (rows > 5) {
				verticalClassConfigBox.add(classPanel6);
			}
			if (rows > 6) {
				verticalClassConfigBox.add(classPanel7);
			}
			if (rows > 7) {
				verticalClassConfigBox.add(classPanel8);
			}
			if (rows > 8) {
				verticalClassConfigBox.add(classPanel9);
			}
			if (rows > 9) {
				verticalClassConfigBox.add(classPanel10);
			}
										
					
			JButton submitClasses = new JButton("Submit");
			submitClasses.addActionListener(new collectConfigClassesData());
			JPanel buttonPanel = new JPanel();
			buttonPanel.setBackground(Color.black);
			buttonPanel.add(submitClasses);
			verticalClassConfigBox.add(buttonPanel);
			
			classConfigurationFrame.add(verticalClassConfigBox);
			
			classConfigurationFrame.setVisible(true);
			classConfigurationFrame.setSize(700, 100 + (29*rows) + 10);
			classConfigurationFrame.setResizable(false);
			classConfigurationFrame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		}
	
		public static class collectConfigClassesData implements ActionListener {
			public void actionPerformed(ActionEvent e) {
				int numOfClasses = rows;
				String[][] allClassInfo = new String[rows][6];
				
				
				
				switch (10 - numOfClasses + 1) {
					case 1:
						allClassInfo[9][0] = class10CRN.getText();
						allClassInfo[9][1] = class10ClassTitle.getText();
						allClassInfo[9][2] = class10ClassSection.getText();
						allClassInfo[9][3] = class10CourseNumber.getText();
						allClassInfo[9][4] = class10SectionNumber.getText();
						allClassInfo[9][5] = (String)class10Priority.getSelectedItem();
					case 2:
						allClassInfo[8][0] = class9CRN.getText();
						allClassInfo[8][1] = class9ClassTitle.getText();
						allClassInfo[8][2] = class9ClassSection.getText();
						allClassInfo[8][3] = class9CourseNumber.getText();
						allClassInfo[8][4] = class9SectionNumber.getText();
						allClassInfo[8][5] = (String)class9Priority.getSelectedItem();
					case 3:
						allClassInfo[7][0] = class8CRN.getText();
						allClassInfo[7][1] = class8ClassTitle.getText();
						allClassInfo[7][2] = class8ClassSection.getText();
						allClassInfo[7][3] = class8CourseNumber.getText();
						allClassInfo[7][4] = class8SectionNumber.getText();
						allClassInfo[7][5] = (String)class8Priority.getSelectedItem();
					case 4:
						allClassInfo[6][0] = class7CRN.getText();
						allClassInfo[6][1] = class7ClassTitle.getText();
						allClassInfo[6][2] = class7ClassSection.getText();
						allClassInfo[6][3] = class7CourseNumber.getText();
						allClassInfo[6][4] = class7SectionNumber.getText();
						allClassInfo[6][5] = (String)class7Priority.getSelectedItem();
					case 5:
						allClassInfo[5][0] = class6CRN.getText();
						allClassInfo[5][1] = class6ClassTitle.getText();
						allClassInfo[5][2] = class6ClassSection.getText();
						allClassInfo[5][3] = class6CourseNumber.getText();
						allClassInfo[5][4] = class6SectionNumber.getText();
						allClassInfo[5][5] = (String)class6Priority.getSelectedItem();
					case 6:
						allClassInfo[4][0] = class5CRN.getText();
						allClassInfo[4][1] = class5ClassTitle.getText();
						allClassInfo[4][2] = class5ClassSection.getText();
						allClassInfo[4][3] = class5CourseNumber.getText();
						allClassInfo[4][4] = class5SectionNumber.getText();
						allClassInfo[4][5] = (String)class5Priority.getSelectedItem();
					case 7:
						allClassInfo[3][0] = class4CRN.getText();
						allClassInfo[3][1] = class4ClassTitle.getText();
						allClassInfo[3][2] = class4ClassSection.getText();
						allClassInfo[3][3] = class4CourseNumber.getText();
						allClassInfo[3][4] = class4SectionNumber.getText();
						allClassInfo[3][5] = (String)class3Priority.getSelectedItem();
					case 8:
						allClassInfo[2][0] = class3CRN.getText();
						allClassInfo[2][1] = class3ClassTitle.getText();
						allClassInfo[2][2] = class3ClassSection.getText();
						allClassInfo[2][3] = class3CourseNumber.getText();
						allClassInfo[2][4] = class3SectionNumber.getText();
						allClassInfo[2][5] = (String)class3Priority.getSelectedItem();
					case 9:
						allClassInfo[1][0] = class2CRN.getText();
						allClassInfo[1][1] = class2ClassTitle.getText();
						allClassInfo[1][2] = class2ClassSection.getText();
						allClassInfo[1][3] = class2CourseNumber.getText();
						allClassInfo[1][4] = class2SectionNumber.getText();
						allClassInfo[1][5] = (String)class2Priority.getSelectedItem();
					case 10:
						allClassInfo[0][0] = class1CRN.getText();
						allClassInfo[0][1] = class1ClassTitle.getText();
						allClassInfo[0][2] = class1ClassSection.getText();
						allClassInfo[0][3] = class1CourseNumber.getText();
						allClassInfo[0][4] = class1SectionNumber.getText();
						allClassInfo[0][5] = (String)class1Priority.getSelectedItem();
				}
				UserInput.setProps();
				UserInput.writeAllNewClasses(allClassInfo);
				UserInput.storeProps();
				classConfigurationFrame.dispose();
				SetConfigureClassesButtonImplementation.main(null);
				
			}
			
		}
	}
	public static class displayAllClassesOnFile implements ActionListener {
		public void actionPerformed(ActionEvent e) {
			PropertiesFile.setProps();
			String[][] currentClassInfo = PropertiesFile.getClassInfo();//[x][0] = Subject      (MAT)
																		//[x][1] = Class        (Data Structures)  
																		//[x][2] = Course Number(1320)
																		//[x][3] = Section      (311)
																		//[x][4] = CRN          (42170)
																		//[x][5] = Priority     (High)
			JLabel[][] classInfoLabels = new JLabel[currentClassInfo.length][7];
			for (int x = 0; x < currentClassInfo.length; x++) {
				classInfoLabels[x][0]= new JLabel("Class " + (x+1));
				classInfoLabels[x][1]= new JLabel(currentClassInfo[x][0]);
				classInfoLabels[x][2]= new JLabel(currentClassInfo[x][1]);
				classInfoLabels[x][3]= new JLabel(currentClassInfo[x][2]);
				classInfoLabels[x][4]= new JLabel(currentClassInfo[x][3]);
				classInfoLabels[x][5]= new JLabel(currentClassInfo[x][4]);
				classInfoLabels[x][6]= new JLabel(currentClassInfo[x][5]);
				
				classInfoLabels[x][0].setForeground(Color.cyan);
				classInfoLabels[x][1].setForeground(Color.white);
				classInfoLabels[x][2].setForeground(Color.white);
				classInfoLabels[x][3].setForeground(Color.white);
				classInfoLabels[x][4].setForeground(Color.white);
				classInfoLabels[x][5].setForeground(Color.white);
				classInfoLabels[x][6].setForeground(Color.white);
				
				classInfoLabels[x][0].setPreferredSize(new Dimension(50,  15));
				classInfoLabels[x][1].setPreferredSize(new Dimension(35,  15));
				classInfoLabels[x][2].setPreferredSize(new Dimension(220, 15));
				classInfoLabels[x][3].setPreferredSize(new Dimension(38,  15));
				classInfoLabels[x][4].setPreferredSize(new Dimension(35,  15));
				classInfoLabels[x][5].setPreferredSize(new Dimension(35,  15));
				classInfoLabels[x][6].setPreferredSize(new Dimension(45,  15));
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
			
			
			JPanel[] panelArrayWithLabels = new JPanel[classInfoLabels.length];
			for (int x = 0; x < panelArrayWithLabels.length; x++) {
				panelArrayWithLabels[x] = new JPanel(new FlowLayout(FlowLayout.LEFT, 5, 0));
				panelArrayWithLabels[x].setBackground(Color.black);
				panelArrayWithLabels[x].add(classInfoLabels[x][0]);
				panelArrayWithLabels[x].add(classInfoLabels[x][1]);
				panelArrayWithLabels[x].add(classInfoLabels[x][2]);
				panelArrayWithLabels[x].add(classInfoLabels[x][3]);
				panelArrayWithLabels[x].add(classInfoLabels[x][4]);
				panelArrayWithLabels[x].add(classInfoLabels[x][5]);
				panelArrayWithLabels[x].add(classInfoLabels[x][6]);
			}
			Box verticalBox = Box.createVerticalBox();
			verticalBox.add(topLevelPanel);
			for(int x = 0; x < panelArrayWithLabels.length; x++) {
				verticalBox.add(panelArrayWithLabels[x]);
			}
			JFrame ClassDisplay = new JFrame("Display Classes On File");
			ClassDisplay.add(verticalBox);
			ClassDisplay.setVisible(true);
			ClassDisplay.setSize(500, panelArrayWithLabels.length * 40 + 40);
			ClassDisplay.setResizable(false);
			ClassDisplay.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
			
		}
	}
	
	public static class changeIndividualClassImplementation implements ActionListener {
		static JComboBox<String> classToChange, classYPriority;
		static JTextField classYCRN, classYClassTitle, classYClassSection, classYCourseNumber, classYSectionNumber;
		static JFrame newClassFrame;
		public void actionPerformed(ActionEvent e) {
			PropertiesFile.setProps();
			int classNum = Integer.parseInt(PropertiesFile.getNumOfClasses());
			String[] choices = new String[classNum + 1];//add extra spot to be able to add a class onto what already exists
			for(int x = 0; x < (classNum + 1) ; x++ ) {
				choices[x] = "" + (x+1);
			}
			classToChange = new JComboBox<String>(choices);
			JLabel label1 = new JLabel("Change Class: ");
			label1.setForeground(Color.white);
			JPanel topPanel = new JPanel(new FlowLayout(FlowLayout.LEFT, 10, 0));
			topPanel.add(label1);
			topPanel.add(classToChange);
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
			classYCRN = new JTextField();
			classYCRN.setColumns(7);
			classYClassTitle = new JTextField();
			classYClassTitle.setColumns(16);
			classYClassSection = new JTextField();
			classYClassSection.setColumns(8);
			classYCourseNumber = new JTextField();
			classYCourseNumber.setColumns(8);
			classYSectionNumber = new JTextField();
			classYSectionNumber.setColumns(8);
			classYPriority = new JComboBox<String>(new String[] {"High", "Medium", "Low"});
			JPanel newClassInfoPanel = new JPanel(new FlowLayout(FlowLayout.LEADING, 0, 0));
			newClassInfoPanel.setBackground(Color.black);
			newClassInfoPanel.add(classYLabel);
			newClassInfoPanel.add(classYCRN);
			newClassInfoPanel.add(classYClassTitle);
			newClassInfoPanel.add(classYClassSection);
			newClassInfoPanel.add(classYCourseNumber);
			newClassInfoPanel.add(classYSectionNumber);
			newClassInfoPanel.add(classYPriority);
			
			JButton submit = new JButton("Submit");
			submit.addActionListener(new sendNewClassInfoToFile());
			JPanel bottomPanel = new JPanel();
			bottomPanel.setBackground(Color.black);
			bottomPanel.add(submit);
			
			Box verticalBox = Box.createVerticalBox();
			verticalBox.add(topPanel);
			verticalBox.add(titlePanel);
			verticalBox.add(examplePanel);
			verticalBox.add(newClassInfoPanel);
			verticalBox.add(bottomPanel);
			
			newClassFrame = new JFrame("New Class Info");
			newClassFrame.add(verticalBox);
			newClassFrame.setVisible(true);
			newClassFrame.setSize(675, 150);
			newClassFrame.setResizable(false);
			newClassFrame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		}
	}
	public static class sendNewClassInfoToFile implements ActionListener {
		public void actionPerformed(ActionEvent e) {
			String[] newClassInfo= {(String)changeIndividualClassImplementation.classToChange.getSelectedItem(),
						            changeIndividualClassImplementation.classYCRN.getText(),
						            changeIndividualClassImplementation.classYClassTitle.getText(),
						            changeIndividualClassImplementation.classYClassSection.getText(),
						            changeIndividualClassImplementation.classYCourseNumber.getText(),
						            changeIndividualClassImplementation.classYSectionNumber.getText(),
									(String)changeIndividualClassImplementation.classYPriority.getSelectedItem()};
		UserInput.editSingleClass(newClassInfo);
		changeIndividualClassImplementation.newClassFrame.dispose();
		setNumberOfClassesFrame.dispose();
		SetConfigureClassesButtonImplementation.main(null);
		}
	}
	
	public static class removeClassImplemetation implements ActionListener {
		static JComboBox<String> classToDelete;
		private static JFrame classToDeleteFrame;
		public void actionPerformed(ActionEvent e) {
			PropertiesFile.setProps();
			int classNum = Integer.parseInt(PropertiesFile.getNumOfClasses());
			String[] choices = new String[classNum];//add extra spot to be able to add a class onto what already exists
			for(int x = 0; x < (classNum) ; x++ ) {
				choices[x] = "" + (x+1);
			}
			classToDelete = new JComboBox<String>(choices);
			JLabel label1 = new JLabel("Delete Class: ");
			label1.setForeground(Color.white);
			JPanel topPanel = new JPanel(new FlowLayout(FlowLayout.LEFT, 10, 0));
			topPanel.add(label1);
			topPanel.add(classToDelete);
			topPanel.setBackground(Color.black);
			
			JButton submit = new JButton("Submit");
			submit.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent e) {
					UserInput.deleteClass((String)classToDelete.getSelectedItem());
					classToDeleteFrame.dispose();
					setNumberOfClassesFrame.dispose();
					SetConfigureClassesButtonImplementation.main(null);
				}
			});
			JPanel bottomPanel = new JPanel();
			bottomPanel.setBackground(Color.black);
			bottomPanel.add(submit);
			
			Box verticalBox = Box.createVerticalBox();
			verticalBox.add(topPanel);
			verticalBox.add(bottomPanel);
			
			classToDeleteFrame = new JFrame("Delete Class");
			classToDeleteFrame.add(verticalBox);
			classToDeleteFrame.setVisible(true);
			classToDeleteFrame.setSize(175, 100);
			classToDeleteFrame.setResizable(false);
			classToDeleteFrame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		}
	}
}