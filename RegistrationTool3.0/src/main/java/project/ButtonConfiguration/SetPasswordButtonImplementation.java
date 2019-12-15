package project.ButtonConfiguration;

import javax.swing.*;
import javax.swing.text.MaskFormatter;

import project.GUI.GUIInput;

import java.awt.*;
import java.awt.event.*;
import java.text.ParseException;
import project.config.PropertiesFile;
import project.writing.UserInput;

public class SetPasswordButtonImplementation {
	private static JFrame setPasswordFrame;
	private static JPanel mainPanel, topThirdPanel, middleThirdPanel, bottomThirdPanel;
	private static JButton submit;
	private static Box verticalBox;
	private static JTextField newPasswordTextField;
	private static JTextArea textAreaWithOldPassword, textAreaForNewPassword;
	
	public static void main(String[] args) {
		runThis();
	}
	public static void runThis()  {
		instantiatePasswordFrame();
		
		
		buildSubmitButton();
		
		buildLayout();
		
		buildFrame();
	}
	private static void instantiatePasswordFrame() {
		setPasswordFrame = new JFrame("Set Password");
	}
	private static void buildFrame() {
		setPasswordFrame.setVisible(true);
		setPasswordFrame.setSize(300, 150);
		setPasswordFrame.setResizable(false);
		setPasswordFrame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
	}
	
	private static void buildSubmitButton() {
		submit = new JButton("Submit Changes");
		submit.addActionListener(new writeNewPasswordToFile());
	}

	private static void buildLayout()  {
		//split frame into thirds, top for currently on file, middle for changes, bottom for submit
		String passwordOnFile = PropertiesFile.getUserPassword();
		
		mainPanel = new JPanel(new FlowLayout(FlowLayout.LEFT, 0, 0));
		mainPanel.setBackground(Color.black);
		
		verticalBox = Box.createVerticalBox();
		
		topThirdPanel = new JPanel(new FlowLayout(FlowLayout.LEFT, 0, 0));
		topThirdPanel.setBackground(Color.black);
		
		textAreaWithOldPassword = new JTextArea("Password on File: " + passwordOnFile + System.lineSeparator(),
																										1, 10);
		textAreaWithOldPassword.setForeground(Color.white);
		textAreaWithOldPassword.setOpaque(false);
		textAreaWithOldPassword.setEditable(false);
		topThirdPanel.add(textAreaWithOldPassword);
		
		
		
		middleThirdPanel = new JPanel(new FlowLayout(FlowLayout.LEFT, 10, 0));
		middleThirdPanel.setBackground(Color.black);
		
		textAreaForNewPassword = new JTextArea("New Password Here " + System.lineSeparator() +
												"Must be exactly 6 digits long" + System.lineSeparator() +
												//"only numbers 0-9" + System.lineSeparator() +
												//"or leave blank and click" + System.lineSeparator() +
												"submit to go back",2 , 10);
		textAreaForNewPassword.setForeground(Color.white);
		textAreaForNewPassword.setOpaque(false);
		textAreaForNewPassword.setEditable(false);
		middleThirdPanel.add(textAreaForNewPassword);
		
		try {
		MaskFormatter formatter = new MaskFormatter("######"); //need to make a try catch to retry if characters are wrong
		newPasswordTextField = new JFormattedTextField(formatter);
		newPasswordTextField.setColumns(10);
		newPasswordTextField.addKeyListener(new writeNewPasswordToFile());
		middleThirdPanel.add(newPasswordTextField);
		}
		catch (ParseException e) {
			System.out.println("must be 6 digits only"); //need to implement better
		}
		
		bottomThirdPanel = new JPanel();
		bottomThirdPanel.setBackground(Color.black);
		bottomThirdPanel.add(submit);
		
		verticalBox.add(topThirdPanel);
		verticalBox.add(middleThirdPanel);
		verticalBox.add(bottomThirdPanel);
		mainPanel.add(verticalBox);
		setPasswordFrame.add(mainPanel);
		setPasswordFrame.getRootPane().setDefaultButton(submit);//this sets the frame so that if enter is pressed, it will be interpreted as clicking on the button
		
	}
	
	public static class writeNewPasswordToFile implements ActionListener, KeyListener {//as of current, enter must be pressed twice, need to figure out how o fix that
		public void actionPerformed(ActionEvent e) {
			String newPassword = newPasswordTextField.getText();
			UserInput.setProps();
			UserInput.setPassword(newPassword);
			UserInput.storeProps();
			setPasswordFrame.dispose();
			GUIInput.mainMenuFrame.dispose();
			GUIInput.main(null);
		}

		public void keyTyped(KeyEvent e) {
			// TODO Auto-generated method stub
			
		}

		public void keyPressed(KeyEvent e) {
			// TODO Auto-generated method stub
			
		}

		public void keyReleased(KeyEvent e) {
			// TODO Auto-generated method stub
			
		}
	}
}
	
	