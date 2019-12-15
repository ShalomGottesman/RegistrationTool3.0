package preCompile;

import java.awt.Color;
import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.Properties;
import java.util.Random;
import javax.crypto.Cipher;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;
import javax.swing.Box;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;

import org.apache.commons.codec.binary.Base64;


public class preCompile_other {
	public static JFrame preCompileMainFrame;
	public static JTextField userNameText, eightHundredNumber, termText, yearText;
	public static JComboBox<String> jSectionSelect, statusSelect, browserSelect, vipStatus;
	public static void main(String[] args) {
		//this is meant to take the users name, the term, and the year
		//all three of these will be encrypted with a private and public key
		//combination. the three public keys will be stored, along with the 
		//encrypted values in the HardProps.properties file, while the private key
		//will be saved to a different location.
		//this ensures that different values cannot be placed inside the 
		//properties file and thus transfer accessibility to a different user
		//
		JLabel userNameLabel = new JLabel("Name of User: ");
		userNameLabel.setForeground(Color.white);
		JLabel numberLabel = new JLabel("800 Number: ");
		numberLabel.setForeground(Color.white);
		JLabel termLabel = new JLabel ("Term (ie. Spring): ");
		termLabel.setForeground(Color.white);
		JLabel yearLabel = new JLabel ("Year (ie. 2019): ");
		yearLabel.setForeground(Color.white);
		
		userNameText = new JTextField();
		userNameText.setColumns(18);
		eightHundredNumber = new JTextField();
		eightHundredNumber.setColumns(18);
		termText = new JTextField();
		termText.setColumns(15);
		yearText = new JTextField();
		yearText.setColumns(15);
		
		JLabel jSection = new JLabel("J Section: ");
		jSection.setForeground(Color.white);
		jSectionSelect = new JComboBox<String>(new String[] {"YP", "BMP", "IBC", "JSS"});
		
		JLabel status = new JLabel("Status: ");
		status.setForeground(Color.white);
		statusSelect = new JComboBox<String>(new String[] {"Upper_Seniors",
																			 "Lower_Seniors",
																			 "Upper_Juniors",
																			 "Lower_Juniors",
																			 "Upper_Sophomores",
																			 "Lower_Sophomores",
																			 "Upper_Freshmen",
																			 "Lower_Freshmen"});
		JLabel browser = new JLabel("Browser: ");
		browser.setForeground(Color.white);
		browserSelect = new JComboBox<String>(new String[] {"Chrome", "Firefox"});
		
		JLabel vipStat = new JLabel("VIP? ");
		vipStat.setForeground(Color.white);
		vipStatus = new JComboBox<String>(new String[] {"False", "True"});
		
		JButton submit = new JButton("Submit");
		submit.addActionListener(new secureInfoAndWriteToFile());
		
		JPanel userNamePanel = new JPanel(new FlowLayout(FlowLayout.LEFT, 0, 0));
		userNamePanel.setBackground(Color.black);
		JPanel numberPanel = new JPanel(new FlowLayout(FlowLayout.LEFT, 0, 0));
		numberPanel.setBackground(Color.black);
		JPanel termPanel = new JPanel(new FlowLayout(FlowLayout.LEFT, 0, 0));
		termPanel.setBackground(Color.black);
		JPanel yearPanel = new JPanel(new FlowLayout(FlowLayout.LEFT, 0, 0));
		yearPanel.setBackground(Color.black);
		JPanel jSectionPanel = new JPanel(new FlowLayout(FlowLayout.LEFT, 0, 0));
		jSectionPanel.setBackground(Color.black);
		JPanel statusPanel = new JPanel(new FlowLayout(FlowLayout.LEFT, 0, 0));
		statusPanel.setBackground(Color.black);
		JPanel browserPanel = new JPanel(new FlowLayout(FlowLayout.LEFT, 0, 0));
		browserPanel.setBackground(Color.black);
		JPanel vipPanel = new JPanel(new FlowLayout(FlowLayout.LEFT, 0, 0));
		vipPanel.setBackground(Color.black);
		JPanel buttonPanel = new JPanel();
		buttonPanel.setBackground(Color.black);
		
		userNamePanel.add(userNameLabel);
		userNamePanel.add(userNameText);
		numberPanel.add(numberLabel);
		numberPanel.add(eightHundredNumber);
		termPanel.add(termLabel);
		termPanel.add(termText);
		yearPanel.add(yearLabel);
		yearPanel.add(yearText);
		jSectionPanel.add(jSection);
		jSectionPanel.add(jSectionSelect);
		statusPanel.add(status);
		statusPanel.add(statusSelect);
		browserPanel.add(browser);
		browserPanel.add(browserSelect);
		vipPanel.add(vipStat);
		vipPanel.add(vipStatus);
		buttonPanel.add(submit);
		
		Box verticalBox = Box.createVerticalBox();
		verticalBox.add(userNamePanel);
		verticalBox.add(numberPanel);
		verticalBox.add(termPanel);
		verticalBox.add(yearPanel);
		verticalBox.add(jSectionPanel);
		verticalBox.add(statusPanel);
		verticalBox.add(browserPanel);
		verticalBox.add(vipPanel);
		verticalBox.add(buttonPanel);
		
		preCompileMainFrame = new JFrame("Pre-Compile");
		preCompileMainFrame.add(verticalBox);
		preCompileMainFrame.setVisible(true);
		preCompileMainFrame.setSize(300, 300);
		preCompileMainFrame.setResizable(false);
		preCompileMainFrame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);		
	}
		public static class secureInfoAndWriteToFile implements ActionListener {
			private String projectLocation = System.getProperty("user.dir");
			private String fileLocation = projectLocation + "\\..\\SeleniumTestingCollaboration\\src\\project\\config\\hardConfig.properties";
			private Properties hardProperties = new Properties();
			
			@Override
			public void actionPerformed(ActionEvent e) {
				String userNameText = preCompile_other.userNameText.getText();
				String eightHundredNumber = preCompile_other.eightHundredNumber.getText();
				String termText = preCompile_other.termText.getText();
				String yearText = preCompile_other.yearText.getText();
				String jSection = (String)preCompile_other.jSectionSelect.getSelectedItem();
				String status = (String)preCompile_other.statusSelect.getSelectedItem();
				String browser = (String)preCompile_other.browserSelect.getSelectedItem();
				String vipStatus = (String)preCompile_other.vipStatus.getSelectedItem();
				
				String[][] allDataOnEncryptedText = encryptFollowing(userNameText, termText, yearText);
				String[] regularData = new String[] {eightHundredNumber, jSection, status, browser, vipStatus};
				writeItAllToFile(allDataOnEncryptedText, regularData);
				preCompileMainFrame.dispose();
				GenSig.main(null);
			}	
			private String[][] encryptFollowing(String usrName, String term, String year) {
				String[][] allInfo = new String[3][];
				allInfo[0] = encrpt(usrName);
				allInfo[1] = encrpt(term);
				allInfo[2] = encrpt(year);
				return allInfo;
			}
			private String[] encrpt (String toBeEncrypted) {
				String base = "1234567890!@$^&*qwertyuiopasdfghjklzxcvbnmQWERTYUIOPASDFGHJKLZXCVBNM";
				String keyString = "";
				Random rand = new Random();
				for (int x = 0; x < 16; x ++) {
					keyString = keyString + base.charAt(rand.nextInt(base.length()));
				}
				String initVector = "zFLVIU4RWrTigwmL";
				try {
					IvParameterSpec iv = new IvParameterSpec(initVector.getBytes("UTF-8"));
					SecretKeySpec skeySpec = new SecretKeySpec(keyString.getBytes("UTF-8"), "AES");
					
					Cipher cipher = Cipher.getInstance("AES/CBC/PKCS5PADDING");
		            cipher.init(Cipher.ENCRYPT_MODE, skeySpec, iv);
		            byte[] encrypted = cipher.doFinal(toBeEncrypted.getBytes());
		            return new String[] {Base64.encodeBase64String(encrypted), keyString};
		        } catch (Exception ex) {
		            ex.printStackTrace();
		        }
		        return null;
			}
			private void writeItAllToFile(String[][] allDataOnEncryptedText, String[] regularData) {
				setProperties();
				writeindividualPropertyToFile("userName" , allDataOnEncryptedText[0][0] );
				writeindividualPropertyToFile("termYear" , allDataOnEncryptedText[2][0]);
				writeindividualPropertyToFile("termSession" , allDataOnEncryptedText[1][0]);
				writeindividualPropertyToFile("userNameKey" , allDataOnEncryptedText[0][1]);
				writeindividualPropertyToFile("termYearKey" , allDataOnEncryptedText[2][1]);
				writeindividualPropertyToFile("termSessionKey" , allDataOnEncryptedText[1][1]);
				writeindividualPropertyToFile("userID" , regularData[0]);
				writeindividualPropertyToFile("Judaic_Section" , regularData[1]);
				writeindividualPropertyToFile("registrationStatus" , regularData[2]);
				writeindividualPropertyToFile("browser" , regularData[3]);
				writeindividualPropertyToFile("info", regularData[4]);
				writeindividualPropertyToFile("buildNo", buildNo());
				storeProperties();
				}
			private String buildNo() {
				String build = "1";
				String base = "1234567890";
				Random rand = new Random();
				for (int x = 0; x < 15; x ++) {
					build = build + base.charAt(rand.nextInt(base.length()));
				}
				return build;
			}
			private void setProperties () {
				try {
					InputStream hardConfigFile = new FileInputStream(fileLocation);
					hardProperties.load(hardConfigFile);
				} catch (Exception e) {
					e.printStackTrace();
				}	
			}
			private void storeProperties () {
				try {
					OutputStream hardConfigOut = new FileOutputStream(fileLocation);
					hardProperties.store(hardConfigOut, "config");
					
					InputStream hardConfigFile = new FileInputStream(fileLocation);
					Properties hardProperties = new Properties();
					hardProperties.load(hardConfigFile);
				} catch (Exception e) {
					e.printStackTrace();
				}	
			}
			private void writeindividualPropertyToFile (String key, String value) {
				hardProperties.setProperty(key, value);
			}

		}
}