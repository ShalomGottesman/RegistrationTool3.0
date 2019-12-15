package project.JudaicStudiesConfiguration;

import java.awt.Color;
import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.Box;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;

import project.config.PropertiesFile;
import project.writing.UserInput;

public class runYPConfigWindow {
	private static JComboBox<String> creditsForShiur; 
	private static JTextField crnInputField;
	public static JFrame ypConfigFrame;
	
	public static void main(String[] args) {
		runThis();
	}
	public static void runThis() {
		//need to implement: pull current info and display
		String[] currentInfo = PropertiesFile.getYPShiurInfo();
		JLabel label1 = new JLabel("Current Amount of Credit: " + currentInfo[0]);
		JLabel label2 = new JLabel("Current Shiur CRN: " + currentInfo[1]);
		label1.setForeground(Color.white);
		label2.setForeground(Color.white);
		JPanel topPanel = new JPanel(new FlowLayout(FlowLayout.LEADING, 0, 0));
		JPanel topPanel2 = new JPanel(new FlowLayout(FlowLayout.LEADING, 0, 0));
		topPanel.setBackground(Color.black);
		topPanel2.setBackground(Color.black);
		topPanel.add(label1);
		topPanel2.add(label2);
		
		JLabel label3 = new JLabel("New Credits for Shiur :");
		label3.setForeground(Color.white);
		creditsForShiur = new JComboBox<String>(new String[] {"0", "1", "2", "3"});
		JPanel panel1 = new JPanel(new FlowLayout(FlowLayout.LEFT, 0, 0));
		panel1.setBackground(Color.black);
		panel1.add(label3);
		panel1.add(creditsForShiur);
		
		JLabel label4 = new JLabel("New Shiur CRN: ");
		label4.setForeground(Color.white);
		crnInputField = new JTextField();
		crnInputField.setColumns(8);
		JPanel panel2 = new JPanel(new FlowLayout(FlowLayout.LEFT, 0, 0));
		panel2.setBackground(Color.black);
		panel2.add(label4);
		panel2.add(crnInputField);
		
		JButton submit = new JButton("Submit");
		submit.addActionListener(new writeYPInfoToFile());
		JPanel panel3 = new JPanel();
		panel3.setBackground(Color.black);
		panel3.add(submit);
		
		Box verticalBox = Box.createVerticalBox();
		verticalBox.add(topPanel);
		verticalBox.add(topPanel2);
		verticalBox.add(panel1);
		verticalBox.add(panel2);
		verticalBox.add(panel3);
		
		ypConfigFrame = new JFrame("YP Config Frame");
		ypConfigFrame.add(verticalBox);
		ypConfigFrame.setVisible(true);
		ypConfigFrame.setSize(200, 200);
		ypConfigFrame.setResizable(false);
		ypConfigFrame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
	}
	public static String[] getInputInfo() {
		String CRN = crnInputField.getText();
		String credits = (String)creditsForShiur.getSelectedItem();
		return (new String[] {CRN, credits});
	}
}
class writeYPInfoToFile implements ActionListener {
	
	public void actionPerformed(ActionEvent e) {
		String[] info = runYPConfigWindow.getInputInfo();//[0]=CRN, [1]=credits
		UserInput.writeYPInfoToFile(info);
		runYPConfigWindow.ypConfigFrame.dispose();
	}
}
