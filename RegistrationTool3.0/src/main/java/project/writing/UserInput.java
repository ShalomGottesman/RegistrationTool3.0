package project.writing;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.Properties;
import java.util.Random;
import java.util.Scanner;

import javax.crypto.Cipher;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;

import org.apache.commons.codec.binary.Base64;

import project.config.PropertiesFile;

public class UserInput
{
	public static String projectLocation = System.getProperty("user.dir");
	private static String userConfigFileAbsolutepath;
	static Properties propUserIn = new Properties();
	static Scanner input = new Scanner(System.in);
	
	public static void setProps()
	{
		String[] sessionInfo = PropertiesFile.sessionInfo();
		String userConfigFileName = "[" + sessionInfo[0] + "][" + sessionInfo[1] + sessionInfo[2] + "].properties";
		userConfigFileAbsolutepath = projectLocation + File.separator + "RegToolData" + File.separator + userConfigFileName;
		File userPropertiesFile = new File(userConfigFileAbsolutepath);
		if (!userPropertiesFile.exists()) {
			try {
				userPropertiesFile.createNewFile();
			} catch (IOException e) {
				System.out.println("unable to create the properties file on disk");
			}
		}
		InputStream userInputIn = null;
			try {
				userInputIn = new FileInputStream(userConfigFileAbsolutepath);
			} catch (FileNotFoundException e) {
				System.out.println("cannot find file");
			}
			//InputStream userInputIn = PropertiesFile.class.getResourceAsStream("userConfig.properties");
			//System.out.println(userInputIn);
			try {
				propUserIn.load(userInputIn);
			} catch (IOException e) {
				System.out.println("unable to load props from file");
			}

	}
	
	public static void storeProps()
	{
		try
		{
			OutputStream userInputOut = new FileOutputStream(userConfigFileAbsolutepath);
			propUserIn.store(userInputOut, "User Input");
		}
		
		catch(Exception e)
		{
			e.printStackTrace();
		}
	}
	
	public static void setDefaultValues() {
		propUserIn.setProperty("loginVerification", "");
		propUserIn.setProperty("classNum", "");
		propUserIn.setProperty("password", "");
		storeProps();
	}
	
	public static void setPassword()
	{
		System.out.print("\nPassword: ");
		
		String password = input.next();
		
		propUserIn.setProperty("password", password);
		propUserIn.setProperty("loginVerification", "not verified");

	}
	
	public static void setPassword(String password) {	
		if (password.equals("")) {
			propUserIn.setProperty("password", "");
			propUserIn.setProperty("password", password);
			propUserIn.setProperty("passwordKey", "");
			propUserIn.setProperty("loginVerification", "");
		} 
		if (!password.equals("")) {
			String[] encryptedInfo = encrpt(password);
			propUserIn.setProperty("password", "");
			propUserIn.setProperty("password", encryptedInfo[0]);
			propUserIn.setProperty("passwordKey", encryptedInfo[1]);
			propUserIn.setProperty("loginVerification", "not verified");			
		}

	}
	
	private static String[] encrpt (String toBeEncrypted) {
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
	
	public static void setNumOfClasses(String str) {
		propUserIn.setProperty("classNum", str);
	}
	
	public static void setClasses() 
	{
		System.out.print("\nNumber of classes: ");
		
		int classNum = input.nextInt();
		
		propUserIn.setProperty("classNum", Integer.toString(classNum));
		
		System.out.println("\nInput class info in the following format:");
		System.out.println("Subject: FYWR");
		System.out.println("Course Number: 1020H");
		System.out.println("Class Title: First_Year_Writing");
		System.out.println("Section: 351");
		System.out.println("CRN: 42119");
		
		for (int currentClass = 1; currentClass <= classNum; currentClass++)
		{
			System.out.println("\nCLASS #" + Integer.toString(currentClass));
			
			System.out.print("\nSubject: ");
			String subject = input.next();
			propUserIn.setProperty("Subj" + Integer.toString(currentClass), subject);
			
			System.out.print("Course Number: ");
			String courseNum = input.next();
			propUserIn.setProperty("CourseNum" + Integer.toString(currentClass), courseNum);
			
			System.out.print("Class Title: ");
			String classTitle = input.next();
			propUserIn.setProperty("Class" + Integer.toString(currentClass), classTitle);
			
			System.out.print("Section: ");
			String section = input.next();
			propUserIn.setProperty("Sec" + Integer.toString(currentClass), section);
			
			System.out.print("CRN: ");
			String crn = input.next();
			propUserIn.setProperty("CRN" + Integer.toString(currentClass), crn);
		}	
	}
	
	public static void verifyLoginCombination() {
		propUserIn.setProperty("loginVerification", "verified");
	}
	
	public static void writeAllNewClasses(String[][] classArray) {
		//[x][0]=CRN
		//[x][1]=Class Title
		//[x][2]=Class Section (ie. FYWR)
		//[x][3]=Course Number (ie. 1020)
		//[x][4]=Section Number (311)
		for (int x = 0; x < classArray.length; x++) {
			String cRN = "CRN" + (x+1);
			String className = "Class" + (x+1);
			String subject = "Subj" + (x+1);
			String courseNumber = "CourseNum" + (x+1);
			String sectionNumber = "Sec" + (x+1);
			String classPriority = "Priority" + (x+1);
			propUserIn.setProperty(cRN, classArray[x][0]);
			propUserIn.setProperty(className, classArray[x][1]);
			propUserIn.setProperty(subject, classArray[x][2]);
			propUserIn.setProperty(courseNumber, classArray[x][3]);
			propUserIn.setProperty(sectionNumber, classArray[x][4]);
			propUserIn.setProperty(classPriority, classArray[x][5]);
		}
	}
	
	public static void editSingleClass(String[] newCLassInfo) {
		//[0]=class number  (1)
		//[1]=class CRN     (42876)
		//[2]=class Title   (first year writing)
		//[3]=Class Section (FYWR)
		//[4]=course number (1400)
		//[5]=section Number(321)
		//[6]=Priority      (High)
		setProps();
		propUserIn.setProperty("CRN" + newCLassInfo[0] , newCLassInfo[1]);
		propUserIn.setProperty("Class" + newCLassInfo[0] , newCLassInfo[2]);
		propUserIn.setProperty("Subj" + newCLassInfo[0] , newCLassInfo[3]);
		propUserIn.setProperty("CourseNum" + newCLassInfo[0] , newCLassInfo[4]);
		propUserIn.setProperty("Sec" + newCLassInfo[0] , newCLassInfo[5]);
		propUserIn.setProperty("Priority" + newCLassInfo[0], newCLassInfo[6]);
		
		PropertiesFile.setProps();
		int currentNumOfCourses = Integer.parseInt(PropertiesFile.getNumOfClasses());
		if(Integer.parseInt(newCLassInfo[0]) > currentNumOfCourses) {
			setNumOfClasses(newCLassInfo[0]);
		}
		storeProps();		
	}
	
	public static void editSingleIBCClass(String[] newIBCCLassInfo) {
		//[0]=class number  (1)
		//[1]=class CRN     (42876)
		//[2]=class Title   (first year writing)
		//[3]=Class Section (FYWR)
		//[4]=course number (1400)
		//[5]=section Number(321)
		//[6]=Priority      (High)
		setProps();
		String x = newIBCCLassInfo[0];
		propUserIn.setProperty("IBCClass" + x + "CRN" , newIBCCLassInfo[1]);
		propUserIn.setProperty("IBCClass" + x + "Title" , newIBCCLassInfo[2]);
		propUserIn.setProperty("IBCClass" + x + "ClassSection" , newIBCCLassInfo[3]);
		propUserIn.setProperty("IBCClass" + x + "CourseNum" , newIBCCLassInfo[4]);
		propUserIn.setProperty("IBCClass" + x + "SectNum" , newIBCCLassInfo[5]);
		propUserIn.setProperty("IBCClass" + x + "Priority", newIBCCLassInfo[6]);
		
		PropertiesFile.setProps();
		int currentNumOfIBCCourses = Integer.parseInt(PropertiesFile.getNumOfNonYPClasses("IBC"));
		if(Integer.parseInt(newIBCCLassInfo[0]) > currentNumOfIBCCourses) {
			setNumOfIBCClasses(newIBCCLassInfo[0]);
		}
		storeProps();		
	}
	
	public static void editSingleJSSClass(String[] newJSSCLassInfo) {
		//[0]=class number  (1)
		//[1]=class CRN     (42876)
		//[2]=class Title   (first year writing)
		//[3]=Class Section (FYWR)
		//[4]=course number (1400)
		//[5]=section Number(321)
		//[6]=Priority      (High)
		setProps();
		String x = newJSSCLassInfo[0];
		propUserIn.setProperty("JSSClass" + x + "CRN" , newJSSCLassInfo[1]);
		propUserIn.setProperty("JSSClass" + x + "Title" , newJSSCLassInfo[2]);
		propUserIn.setProperty("JSSClass" + x + "ClassSection" , newJSSCLassInfo[3]);
		propUserIn.setProperty("JSSClass" + x + "CourseNum" , newJSSCLassInfo[4]);
		propUserIn.setProperty("JSSClass" + x + "SectNum" , newJSSCLassInfo[5]);
		propUserIn.setProperty("JSSClass" + x + "Priority", newJSSCLassInfo[6]);
		
		PropertiesFile.setProps();
		int currentNumOfJSSCourses = Integer.parseInt(PropertiesFile.getNumOfNonYPClasses("JSS"));
		if(Integer.parseInt(newJSSCLassInfo[0]) > currentNumOfJSSCourses) {
			setNumOfJSSClasses(newJSSCLassInfo[0]);
		}
		storeProps();		
	}
	
	public static void deleteIBCClass(String classToDelete) {
		setProps();
		PropertiesFile.setProps();
		int currentNumberOfIBCClasses = Integer.parseInt(PropertiesFile.getNumOfNonYPClasses("IBC"));
		if (currentNumberOfIBCClasses == Integer.parseInt(classToDelete)) {
			propUserIn.setProperty("IBCClass" + classToDelete + "CRN" , "");
			propUserIn.setProperty("IBCClass" + classToDelete + "Title" , "");
			propUserIn.setProperty("IBCClass" + classToDelete + "ClassSection" , "");
			propUserIn.setProperty("IBCClass" + classToDelete + "CourseNum" , "");
			propUserIn.setProperty("IBCClass" + classToDelete + "SectNum" , "");
			propUserIn.setProperty("IBCClass" + classToDelete + "Priority", "");
		}
		int classNumberToDelete = Integer.parseInt(classToDelete);
		if (currentNumberOfIBCClasses > classNumberToDelete) {
			for (int x = classNumberToDelete; x < currentNumberOfIBCClasses; x++) {
				String[] nextClassToOverwriteThisClass = PropertiesFile.getSingleNonYPClass("" + (x + 1), "IBC");
				propUserIn.setProperty("IBCClass" + x + "ClassSection" , nextClassToOverwriteThisClass[0]);
				propUserIn.setProperty("IBCClass" + x + "Title" , nextClassToOverwriteThisClass[1]);
				propUserIn.setProperty("IBCClass" + x + "CourseNum" , nextClassToOverwriteThisClass[2]);
				propUserIn.setProperty("IBCClass" + x + "SectNum" , nextClassToOverwriteThisClass[3]);
				propUserIn.setProperty("IBCClass" + x + "CRN", nextClassToOverwriteThisClass[4]);
				propUserIn.setProperty("IBCClass" + x + "Priority", nextClassToOverwriteThisClass[5]);
			}
			propUserIn.setProperty("IBCClass" + currentNumberOfIBCClasses + "ClassSection", "");
			propUserIn.setProperty("IBCClass" + currentNumberOfIBCClasses + "Title" , "");
			propUserIn.setProperty("IBCClass" + currentNumberOfIBCClasses + "CourseNum" , "");
			propUserIn.setProperty("IBCClass" + currentNumberOfIBCClasses + "SectNum" , "");
			propUserIn.setProperty("IBCClass" + currentNumberOfIBCClasses + "CRN" , "");
			propUserIn.setProperty("IBCClass" + currentNumberOfIBCClasses + "Priority", "");
			//move all later class up the list
			//delete the last class on the list
		}
		String newNumberOfClasses = "" + (currentNumberOfIBCClasses-1);
		propUserIn.setProperty("IBCClassNum", newNumberOfClasses);
			//subtract one from number of classes
		storeProps();
	}
	
	public static void deleteJSSClass(String classToDelete) {
		setProps();
		PropertiesFile.setProps();
		int currentNumberOfJSSClasses = Integer.parseInt(PropertiesFile.getNumOfNonYPClasses("JSS"));
		if (currentNumberOfJSSClasses == Integer.parseInt(classToDelete)) {
			propUserIn.setProperty("JSSClass" + classToDelete + "CRN" , "");
			propUserIn.setProperty("JSSClass" + classToDelete + "Title" , "");
			propUserIn.setProperty("JSSClass" + classToDelete + "ClassSection" , "");
			propUserIn.setProperty("JSSClass" + classToDelete + "CourseNum" , "");
			propUserIn.setProperty("JSSClass" + classToDelete + "SectNum" , "");
			propUserIn.setProperty("JSSClass" + classToDelete + "Priority", "");
		}
		int classNumberToDelete = Integer.parseInt(classToDelete);
		if (currentNumberOfJSSClasses > classNumberToDelete) {
			for (int x = classNumberToDelete; x < currentNumberOfJSSClasses; x++) {
				String[] nextClassToOverwriteThisClass = PropertiesFile.getSingleNonYPClass("" + (x + 1), "JSS");
				propUserIn.setProperty("JSSClass" + x + "ClassSection" , nextClassToOverwriteThisClass[0]);
				propUserIn.setProperty("JSSClass" + x + "Title" , nextClassToOverwriteThisClass[1]);
				propUserIn.setProperty("JSSClass" + x + "CourseNum" , nextClassToOverwriteThisClass[2]);
				propUserIn.setProperty("JSSClass" + x + "SectNum" , nextClassToOverwriteThisClass[3]);
				propUserIn.setProperty("JSSClass" + x + "CRN", nextClassToOverwriteThisClass[4]);
				propUserIn.setProperty("JSSClass" + x + "Priority", nextClassToOverwriteThisClass[5]);
			}
			propUserIn.setProperty("JSSClass" + currentNumberOfJSSClasses + "ClassSection", "");
			propUserIn.setProperty("JSSClass" + currentNumberOfJSSClasses + "Title" , "");
			propUserIn.setProperty("JSSClass" + currentNumberOfJSSClasses + "CourseNum" , "");
			propUserIn.setProperty("JSSClass" + currentNumberOfJSSClasses + "SectNum" , "");
			propUserIn.setProperty("JSSClass" + currentNumberOfJSSClasses + "CRN" , "");
			propUserIn.setProperty("JSSClass" + currentNumberOfJSSClasses + "Priority", "");
			//move all later class up the list
			//delete the last class on the list
		}
		String newNumberOfClasses = "" + (currentNumberOfJSSClasses-1);
		propUserIn.setProperty("JSSClassNum", newNumberOfClasses);
			//subtract one from number of classes
		storeProps();
	}
	public static void editSingleBMPClass(String[] newBMPCLassInfo) {
		//[0]=class number  (1)
		//[1]=class CRN     (42876)
		//[2]=class Title   (first year writing)
		//[3]=Class Section (FYWR)
		//[4]=course number (1400)
		//[5]=section Number(321)
		//[6]=Priority      (High)
		setProps();
		String x = newBMPCLassInfo[0];
		propUserIn.setProperty("BMPClass" + x + "CRN" , newBMPCLassInfo[1]);
		propUserIn.setProperty("BMPClass" + x + "Title" , newBMPCLassInfo[2]);
		propUserIn.setProperty("BMPClass" + x + "ClassSection" , newBMPCLassInfo[3]);
		propUserIn.setProperty("BMPClass" + x + "CourseNum" , newBMPCLassInfo[4]);
		propUserIn.setProperty("BMPClass" + x + "SectNum" , newBMPCLassInfo[5]);
		propUserIn.setProperty("BMPClass" + x + "Priority", newBMPCLassInfo[6]);
		
		PropertiesFile.setProps();
		int currentNumOfBMPCourses = Integer.parseInt(PropertiesFile.getNumOfNonYPClasses("BMP"));
		if(Integer.parseInt(newBMPCLassInfo[0]) > currentNumOfBMPCourses) {
			setNumOfBMPClasses(newBMPCLassInfo[0]);
		}
		storeProps();		
	}
	
	public static void deleteBMPClass(String classToDelete) {
		setProps();
		PropertiesFile.setProps();
		int currentNumberOfBMPClasses = Integer.parseInt(PropertiesFile.getNumOfNonYPClasses("BMP"));
		if (currentNumberOfBMPClasses == Integer.parseInt(classToDelete)) {
			propUserIn.setProperty("BMPClass" + classToDelete + "CRN" , "");
			propUserIn.setProperty("BMPClass" + classToDelete + "Title" , "");
			propUserIn.setProperty("BMPClass" + classToDelete + "ClassSection" , "");
			propUserIn.setProperty("BMPClass" + classToDelete + "CourseNum" , "");
			propUserIn.setProperty("BMPClass" + classToDelete + "SectNum" , "");
			propUserIn.setProperty("BMPClass" + classToDelete + "Priority", "");
		}
		int classNumberToDelete = Integer.parseInt(classToDelete);
		if (currentNumberOfBMPClasses > classNumberToDelete) {
			for (int x = classNumberToDelete; x < currentNumberOfBMPClasses; x++) {
				String[] nextClassToOverwriteThisClass = PropertiesFile.getSingleNonYPClass("" + (x + 1), "BMP");
				propUserIn.setProperty("BMPClass" + x + "ClassSection" , nextClassToOverwriteThisClass[0]);
				propUserIn.setProperty("BMPClass" + x + "Title" , nextClassToOverwriteThisClass[1]);
				propUserIn.setProperty("BMPClass" + x + "CourseNum" , nextClassToOverwriteThisClass[2]);
				propUserIn.setProperty("BMPClass" + x + "SectNum" , nextClassToOverwriteThisClass[3]);
				propUserIn.setProperty("BMPClass" + x + "CRN", nextClassToOverwriteThisClass[4]);
				propUserIn.setProperty("BMPClass" + x + "Priority", nextClassToOverwriteThisClass[5]);
			}
			propUserIn.setProperty("BMPClass" + currentNumberOfBMPClasses + "ClassSection", "");
			propUserIn.setProperty("BMPClass" + currentNumberOfBMPClasses + "Title" , "");
			propUserIn.setProperty("BMPClass" + currentNumberOfBMPClasses + "CourseNum" , "");
			propUserIn.setProperty("BMPClass" + currentNumberOfBMPClasses + "SectNum" , "");
			propUserIn.setProperty("BMPClass" + currentNumberOfBMPClasses + "CRN" , "");
			propUserIn.setProperty("BMPClass" + currentNumberOfBMPClasses + "Priority", "");
			//move all later class up the list
			//delete the last class on the list
		}
		String newNumberOfClasses = "" + (currentNumberOfBMPClasses-1);
		propUserIn.setProperty("BMPClassNum", newNumberOfClasses);
			//subtract one from number of classes
		storeProps();
	}
	
	public static void deleteClass(String classToDelete) {
		setProps();
		PropertiesFile.setProps();
		int currentNumberOfClasses = Integer.parseInt(PropertiesFile.getNumOfClasses());
		if (currentNumberOfClasses == Integer.parseInt(classToDelete)) {
			propUserIn.setProperty("CRN" + classToDelete , "");
			propUserIn.setProperty("Class" + classToDelete , "");
			propUserIn.setProperty("Subj" + classToDelete , "");
			propUserIn.setProperty("CourseNum" + classToDelete , "");
			propUserIn.setProperty("Sec" + classToDelete , "");
			propUserIn.setProperty("Priority" + classToDelete, "");
		}
		int classNumberToDelete = Integer.parseInt(classToDelete);
		if (currentNumberOfClasses > classNumberToDelete) {
			for (int x = classNumberToDelete; x < currentNumberOfClasses; x++) {
				String[] nextClassToOverwriteThisClass = PropertiesFile.getSingleClass("" + (x + 1));
				propUserIn.setProperty("Subj" + x , nextClassToOverwriteThisClass[0]);
				propUserIn.setProperty("Class" + x , nextClassToOverwriteThisClass[1]);
				propUserIn.setProperty("CourseNum" + x , nextClassToOverwriteThisClass[2]);
				propUserIn.setProperty("Sec" + x , nextClassToOverwriteThisClass[3]);
				propUserIn.setProperty("CRN" + x , nextClassToOverwriteThisClass[4]);
				propUserIn.setProperty("Priority" + x, nextClassToOverwriteThisClass[5]);
			}
			propUserIn.setProperty("CRN" + currentNumberOfClasses , "");
			propUserIn.setProperty("Class" + currentNumberOfClasses , "");
			propUserIn.setProperty("Subj" + currentNumberOfClasses , "");
			propUserIn.setProperty("CourseNum" + currentNumberOfClasses , "");
			propUserIn.setProperty("Sec" + currentNumberOfClasses , "");
			propUserIn.setProperty("Priority" + currentNumberOfClasses, "");
			//move all later class up the list
			//delete the last class on the list
		}
		String newNumberOfClasses = "" + (currentNumberOfClasses-1);
		propUserIn.setProperty("classNum", newNumberOfClasses);
			//subtract one from number of classes
		storeProps();
	}
	
	public static void writeYPInfoToFile(String[] YPInfo) {//[0]=CRN, [1]=credits
		setProps();
		propUserIn.setProperty("YpShiurCred", YPInfo[1]);
		propUserIn.setProperty("YpShiurCrn", YPInfo[0]);
		storeProps();
	}
	
	public static void writeAllNewIBCClasses(String[][] newIBCClassInfo) {
		setProps();
		for(int x = 0; x < newIBCClassInfo.length; x++) {
			propUserIn.setProperty("IBCClass" + (x+1) + "CRN",newIBCClassInfo[x][0]);
			propUserIn.setProperty("IBCClass" + (x+1) + "Title",newIBCClassInfo[x][1]);
			propUserIn.setProperty("IBCClass" + (x+1) + "ClassSection",newIBCClassInfo[x][2]);
			propUserIn.setProperty("IBCClass" + (x+1) + "CourseNum",newIBCClassInfo[x][3]);
			propUserIn.setProperty("IBCClass" + (x+1) + "SectNum",newIBCClassInfo[x][4]);
			propUserIn.setProperty("IBCClass" + (x+1) + "Priority",newIBCClassInfo[x][5]);
		}
		storeProps();
	}
	
	public static void setNumOfIBCClasses(String numOfClasses) {
		propUserIn.setProperty("IBCClassNum", numOfClasses);
	}
	
	public static void setNumOfJSSClasses(String numOfClasses) {
		propUserIn.setProperty("JSSClassNum", numOfClasses);
	}
	
	public static void writeAllNewBMPClasses(String[][] newBMPClassInfo) {
		setProps();
		for(int x = 0; x < newBMPClassInfo.length; x++) {
			propUserIn.setProperty("BMPClass" + (x+1) + "CRN",newBMPClassInfo[x][0]);
			propUserIn.setProperty("BMPClass" + (x+1) + "Title",newBMPClassInfo[x][1]);
			propUserIn.setProperty("BMPClass" + (x+1) + "ClassSection",newBMPClassInfo[x][2]);
			propUserIn.setProperty("BMPClass" + (x+1) + "CourseNum",newBMPClassInfo[x][3]);
			propUserIn.setProperty("BMPClass" + (x+1) + "SectNum",newBMPClassInfo[x][4]);
			propUserIn.setProperty("BMPClass" + (x+1) + "Priority",newBMPClassInfo[x][5]);
		}
		storeProps();
	}
	
	public static void writeAllNewJSSClasses(String[][] newJSSClassInfo) {
		setProps();
		for(int x = 0; x < newJSSClassInfo.length; x++) {
			propUserIn.setProperty("JSSClass" + (x+1) + "CRN",newJSSClassInfo[x][0]);
			propUserIn.setProperty("JSSClass" + (x+1) + "Title",newJSSClassInfo[x][1]);
			propUserIn.setProperty("JSSClass" + (x+1) + "ClassSection",newJSSClassInfo[x][2]);
			propUserIn.setProperty("JSSClass" + (x+1) + "CourseNum",newJSSClassInfo[x][3]);
			propUserIn.setProperty("JSSClass" + (x+1) + "SectNum",newJSSClassInfo[x][4]);
			propUserIn.setProperty("JSSClass" + (x+1) + "Priority",newJSSClassInfo[x][5]);
		}
		storeProps();
	}
	
	public static void setNumOfBMPClasses(String numOfClasses) {
		propUserIn.setProperty("BMPClassNum", numOfClasses);
	}
	
	
	public static void main(String[] args)
	{
		setProps();
				
		System.out.println("Please input the information necessary to launch registration program:\n");
		
		setPassword();
		
		setClasses();
				
		storeProps();
	}
}