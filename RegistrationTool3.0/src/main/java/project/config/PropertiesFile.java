package project.config;

import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;
import java.util.Properties;

import javax.crypto.Cipher;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;
import org.apache.commons.codec.binary.Base64;

import project.writing.UserInput;

public class PropertiesFile {
	public static String projectLocation = System.getProperty("user.dir");
	static Properties propHard = new Properties();
	static Properties propUser = new Properties();
	
	public static void main(String[] args) {
		setProps();
	}
	
	public static void setProps()
	{
		try
		{
			//InputStream hardInput = new FileInputStream(projectLocation + "\\src\\config\\hardConfig.properties");
			InputStream hardInput = PropertiesFile.class.getResourceAsStream("hardConfig.properties");
			propHard.load(hardInput);
			
			String[] sessionInfo = sessionInfo();
			String userConfigFileName = "[" + sessionInfo[0] + "][" + sessionInfo[1] + sessionInfo[2] + "].properties";
			String userConfigFileAbsolutepath = projectLocation + "\\RegToolData\\" + userConfigFileName;
			if (!(new File (userConfigFileAbsolutepath).exists())) {
				new File (userConfigFileAbsolutepath).createNewFile();
				UserInput.setProps();
				UserInput.setDefaultValues();
			}
				
			
			
			InputStream userInput = new FileInputStream(userConfigFileAbsolutepath);
			//InputStream userInput = PropertiesFile.class.getResourceAsStream("userConfig.properties");
			propUser.load(userInput);
		}
		
		catch(Exception e)
		{
			e.printStackTrace();
		}
	}
	
	public static String[] sessionInfo() {
		return new String[] {getUserName(),
							 getTermSession(),
							 getTermYear()};
	}
	
	public static String getBrowser() {
		try {
			String browser = propHard.getProperty("browser");
			return browser;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}
	
	public static String getUserID() {
		try {
			String userID = propHard.getProperty("userID");
			return userID;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;	
	}
	
	public static String getUserPassword() {
		try {
			String userPassword = propUser.getProperty("password");
			return userPassword;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;	
	}
	
	public static String getUserName() {
		try {
			String userName = decrypt(propHard.getProperty("userName"),
					propHard.getProperty("userNameKey")) ;
			return userName;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;	
	}
	
	public static String getTermYear() {
		try {
			String termYear = decrypt(propHard.getProperty("termYear"),
									propHard.getProperty("termYearKey")) ;
			return termYear;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}
	
	public static String getTermSession() {
		try {
			String termSession = decrypt(propHard.getProperty("termSession"),
										 propHard.getProperty("termSessionKey")) ;
			return termSession;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}
	
	public static String[] getRegularCRNArray() {
		try {
			String numberOfCRNs = propUser.getProperty("classNum");
			int numCRN = Integer.parseInt(numberOfCRNs);
			String[] CRNs = new String[numCRN];
			switch (10 - numCRN + 1) {
				case 1: 
					CRNs[9] =  propUser.getProperty("CRN10");
				case 2:
					CRNs[8] =  propUser.getProperty("CRN9");
				case 3:
					CRNs[7] =  propUser.getProperty("CRN8");
				case 4:
					CRNs[6] =  propUser.getProperty("CRN7");
				case 5:
					CRNs[5] =  propUser.getProperty("CRN6");
				case 6:
					CRNs[4] =  propUser.getProperty("CRN5");
				case 7:
					CRNs[3] =  propUser.getProperty("CRN4");
				case 8:
					CRNs[2] =  propUser.getProperty("CRN3");
				case 9:
					CRNs[1] =  propUser.getProperty("CRN2");
				case 10:
					CRNs[0] =  propUser.getProperty("CRN1");
					break;
				default:
					CRNs = null;
					break;
			}
			//System.out.println(Arrays.toString(CRNs));
			return CRNs;
			//return termSession;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}
	
	public static String getRegistationTime() {
		try {
			String registrationStatus = propHard.getProperty("registrationStatus");
			String registrationTime = propHard.getProperty(registrationStatus);
			//System.out.println("this is the reg time from PropertiesFile " + registrationTime);
			return registrationTime;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
		
	}
	
	public static String[][] getClassInfo()
	{
		try
		{
			int classNum = Integer.parseInt((propUser.getProperty("classNum")));
			
			String[][] classInfo = new String[classNum][6];
			
			for (int currentClass = 0; currentClass < classNum; currentClass++)
			{
				classInfo[currentClass][0] = propUser.getProperty("Subj" + (currentClass+1));
				classInfo[currentClass][1] = propUser.getProperty("Class" + (currentClass+1));
				classInfo[currentClass][2] = propUser.getProperty("CourseNum" + (currentClass+1));
				classInfo[currentClass][3] = propUser.getProperty("Sec" + (currentClass+1));
				classInfo[currentClass][4] = propUser.getProperty("CRN" + (currentClass+1));
				classInfo[currentClass][5] = propUser.getProperty("Priority" + (currentClass+1));
			}
			
			return classInfo;
		}
		
		catch (Exception e)
		{
			e.printStackTrace();
			
			return null;
		}
	}
	
	public static String[] getYpShiurInfo() {
		String[] info = new String[2];
		info[0] = propUser.getProperty("YpShiurCrn");
		info[1] = propUser.getProperty("YpShiurCred");
		return info;
	}
	
	public static String[] getUserInfoForGUI() {
		setProps();
		String[] userInfo = new String[8];
		
		userInfo[0] = getUserName();
		userInfo[1] = propHard.getProperty("userID");
		userInfo[2] = propUser.getProperty("password");
		userInfo[3] = propUser.getProperty("loginVerification");
		userInfo[4] = getTermSession() + " " + getTermYear();
		userInfo[5] = propHard.getProperty("registrationStatus");
		userInfo[6] = propHard.getProperty("browser");
		userInfo[7] = propHard.getProperty("Judaic_Section");
		
		return userInfo;
	}
	
	public static String getJudaicSectionType() {
		return propHard.getProperty("Judaic_Section");
	}
	public static String getNumOfClasses() {
		return propUser.getProperty("classNum");
	}
	
	public static String[] getYPShiurInfo() {
		setProps();
		return (new String[] {propUser.getProperty("YpShiurCred"), 
							  propUser.getProperty("YpShiurCrn")});	
	}
	
	public static String getCredentialsVerificationStatus() {
		return propUser.getProperty("loginVerification");
	}

	public static String[] getSingleClass(String classNum) {
		setProps();
		String[] singleClassInfo = {propUser.getProperty("Subj" + classNum),
									propUser.getProperty("Class" + classNum),
									propUser.getProperty("CourseNum" + classNum),
									propUser.getProperty("Sec" + classNum),
									propUser.getProperty("CRN" + classNum),
									propUser.getProperty("Priority" + classNum)};
		return singleClassInfo;
	}
	
	@SuppressWarnings("unused")
	private static String decrypt(String encrypted, String key) {
		String sfzfvczx =    "aDefcNAboaokcFgJ";
		String casdvzxcv =   "VDKGAjKKoGYjruW4";
		String aasfawevczx = "eTCxIc9TIXQOeMus";
		String dzhzdvxzc =   "zFLVIU4RWrTigwmL";
		String fakeEncrypt = "9xCRQ5dg7wsEloztokMQBQ\\=\\=";
		String fake2 = "peK+UTnAWqTDkTAETJBFVg\\=\\=";
	        try {
	        	IvParameterSpec xi = new IvParameterSpec(sfzfvczx.getBytes("UTF-8"));
	        	IvParameterSpec il = new IvParameterSpec(aasfawevczx.getBytes("UTF-8"));
	        	IvParameterSpec iii = new IvParameterSpec(sfzfvczx.getBytes("UTF-8"));
	        	IvParameterSpec ix = new IvParameterSpec(aasfawevczx.getBytes("UTF-8"));
	        	IvParameterSpec iv = new IvParameterSpec(dzhzdvxzc.getBytes("UTF-8"));
	        	IvParameterSpec vii = new IvParameterSpec(sfzfvczx.getBytes("UTF-8"));
	            IvParameterSpec vi = new IvParameterSpec(aasfawevczx.getBytes("UTF-8"));
	            IvParameterSpec iiiv = new IvParameterSpec(aasfawevczx.getBytes("UTF-8"));
	            IvParameterSpec iiv = new IvParameterSpec(casdvzxcv.getBytes("UTF-8"));
	            SecretKeySpec skeySpec = new SecretKeySpec(key.getBytes("UTF-8"), "AES");

	            Cipher cipher = null;
	            Cipher cipfer = null;
	            Cipher cipner = null;
	            cipher = Cipher.getInstance("AES/CBC/PKCS5PADDING");
	            cipfer = Cipher.getInstance("AES/CBC/PKCS5PADDING");
	            cipner = Cipher.getInstance("AES/CBC/PKCS5PADDING");
	            cipher.init(Cipher.DECRYPT_MODE, skeySpec, iv);
	            cipfer.init(Cipher.DECRYPT_MODE, skeySpec, vii);
	            cipner.init(Cipher.DECRYPT_MODE, skeySpec, iiv);
	           // byte[] origihal = cipfer.doFinal(Base64.decodeBase64(fakeEncrypt));
	            byte[] original = cipher.doFinal(Base64.decodeBase64(encrypted));
	           // byte[] origimal = cipner.doFinal(Base64.decodeBase64(fake2));

	            return new String(original);
	        } catch (Exception ex) {
	            ex.printStackTrace();
	        }

	        return null;
	    }
	
/*
public static String getNumOfBMPClasses() {
	return propUser.getProperty("BMPClassNum");
}

public static String getNumOfIBCClasses() {
	return propUser.getProperty("IBCClassNum");
}

public static String getNumOfJSSClasses() {
	return propUser.getProperty("JSSClassNum");
}
*/

public static String getNumOfNonYPClasses(String prefix) {
	return propUser.getProperty(prefix + "ClassNum");
}
/*

public static String[] getSingleIBCClass(String x) {
	setProps();
	return new String[] {propUser.getProperty("IBCClass" + x + "ClassSection"),
						 propUser.getProperty("IBCClass" + x + "Title"),
						 propUser.getProperty("IBCClass" + x + "CourseNum"),
						 propUser.getProperty("IBCClass" + x + "SectNum"),
						 propUser.getProperty("IBCClass" + x + "CRN"),
						 propUser.getProperty("IBCClass" + x + "Priority")};
	}

public static String[] getSingleJSSClass(String x) {
	setProps();
	return new String[] {propUser.getProperty("JSSClass" + x + "ClassSection"),
						 propUser.getProperty("JSSClass" + x + "Title"),
						 propUser.getProperty("JSSClass" + x + "CourseNum"),
						 propUser.getProperty("JSSClass" + x + "SectNum"),
						 propUser.getProperty("JSSClass" + x + "CRN"),
						 propUser.getProperty("JSSClass" + x + "Priority")};
	}

public static String[] getSingleBMPClass(String x) {
	setProps();
	return new String[] {propUser.getProperty("BMPClass" + x + "ClassSection"),
						 propUser.getProperty("BMPClass" + x + "Title"),
						 propUser.getProperty("BMPClass" + x + "CourseNum"),
						 propUser.getProperty("BMPClass" + x + "SectNum"),
						 propUser.getProperty("BMPClass" + x + "CRN"),
						 propUser.getProperty("BMPClass" + x + "Priority")};
	}
*/
public static String[] getSingleNonYPClass(String x, String prefix) {
	setProps();
	return new String[] {propUser.getProperty(prefix + "Class" + x + "ClassSection"),
						 propUser.getProperty(prefix + "Class" + x + "Title"),
						 propUser.getProperty(prefix + "Class" + x + "CourseNum"),
						 propUser.getProperty(prefix + "Class" + x + "SectNum"),
						 propUser.getProperty(prefix + "Class" + x + "CRN"),
						 propUser.getProperty(prefix + "Class" + x + "Priority")};
	}
public static String[][] getNonYPClassInfo(String prefix) {
	PropertiesFile.setProps();
	int numOfClasses = Integer.parseInt(PropertiesFile.getNumOfNonYPClasses(prefix));
	String[][] ClassInfo = new String[numOfClasses][7];
	for( int x = 0; x < numOfClasses; x++) {
		ClassInfo[x][0] = propUser.getProperty(prefix + "Class" + (x+1) + "ClassSection");
		ClassInfo[x][1] = propUser.getProperty(prefix + "Class" + (x+1) + "Title");
		ClassInfo[x][2] = propUser.getProperty(prefix + "Class" + (x+1) + "CourseNum");
		ClassInfo[x][3] = propUser.getProperty(prefix + "Class" + (x+1) + "SectNum");
		ClassInfo[x][4] = propUser.getProperty(prefix + "Class" + (x+1) + "CRN");
		ClassInfo[x][5] = propUser.getProperty(prefix + "Class" + (x+1) + "Priority");
	}
	return ClassInfo;
}

/*

public static String[][] getIBCClassInfo() {
	PropertiesFile.setProps();
	int numOfClasses = Integer.parseInt(PropertiesFile.getNumOfIBCClasses());
	String[][] IBCClassInfo = new String[numOfClasses][7];
	for( int x = 0; x < numOfClasses; x++) {
		IBCClassInfo[x][0] = propUser.getProperty("IBCClass" + (x+1) + "ClassSection");
		IBCClassInfo[x][1] = propUser.getProperty("IBCClass" + (x+1) + "Title");
		IBCClassInfo[x][2] = propUser.getProperty("IBCClass" + (x+1) + "CourseNum");
		IBCClassInfo[x][3] = propUser.getProperty("IBCClass" + (x+1) + "SectNum");
		IBCClassInfo[x][4] = propUser.getProperty("IBCClass" + (x+1) + "CRN");
		IBCClassInfo[x][5] = propUser.getProperty("IBCClass" + (x+1) + "Priority");
	}
	return IBCClassInfo;
}

public static String[][] getBMPClassInfo() {
	PropertiesFile.setProps();
	int numOfClasses = Integer.parseInt(PropertiesFile.getNumOfBMPClasses());
	String[][] BMPClassInfo = new String[numOfClasses][7];
	for( int x = 0; x < numOfClasses; x++) {
		BMPClassInfo[x][0] = propUser.getProperty("BMPClass" + (x+1) + "ClassSection");
		BMPClassInfo[x][1] = propUser.getProperty("BMPClass" + (x+1) + "Title");
		BMPClassInfo[x][2] = propUser.getProperty("BMPClass" + (x+1) + "CourseNum");
		BMPClassInfo[x][3] = propUser.getProperty("BMPClass" + (x+1) + "SectNum");
		BMPClassInfo[x][4] = propUser.getProperty("BMPClass" + (x+1) + "CRN");
		BMPClassInfo[x][5] = propUser.getProperty("BMPClass" + (x+1) + "Priority");
	}
	return BMPClassInfo;
}

public static String[][] getJSSClassInfo() {
	PropertiesFile.setProps();
	int numOfClasses = Integer.parseInt(PropertiesFile.getNumOfJSSClasses());
	String[][] JSSClassInfo = new String[numOfClasses][7];
	for( int x = 0; x < numOfClasses; x++) {
		JSSClassInfo[x][0] = propUser.getProperty("JSSClass" + (x+1) + "ClassSection");
		JSSClassInfo[x][1] = propUser.getProperty("JSSClass" + (x+1) + "Title");
		JSSClassInfo[x][2] = propUser.getProperty("JSSClass" + (x+1) + "CourseNum");
		JSSClassInfo[x][3] = propUser.getProperty("JSSClass" + (x+1) + "SectNum");
		JSSClassInfo[x][4] = propUser.getProperty("JSSClass" + (x+1) + "CRN");
		JSSClassInfo[x][5] = propUser.getProperty("JSSClass" + (x+1) + "Priority");
	}
	return JSSClassInfo;
}
*/

}
