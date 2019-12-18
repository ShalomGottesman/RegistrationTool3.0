package project.other; 
 
import java.io.*;
import java.security.*;
import java.security.spec.*;

import project.config.PropertiesFile;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
 
public class VerSig {
 
    public static boolean main(String[] args) {
    	String projectLocation = System.getProperty("user.dir");
    	String publicKey = projectLocation + File.separator + "RegToolData" + File.separator + "ppk";
    	String signature = projectLocation + File.separator + "RegToolData" + File.separator + "gis";
    	String propFile = projectLocation + File.separator + "RegToolData" + File.separator + "hardConfig.properties";
 
    	File dataFolder = new File(projectLocation + File.separator + "RegToolData");
    	dataFolder.mkdir();
    	
    	VerSig.class.getResourceAsStream("hardConfig.properties");
    	
    	//System.out.println("Copy files");                          project\config
    	InputStream in1 = PropertiesFile.class.getResourceAsStream("/project/config/ppk");
    	InputStream in2 = PropertiesFile.class.getResourceAsStream("/project/config/gis");
    	InputStream in3 = PropertiesFile.class.getResourceAsStream("/project/config/hardConfig.properties");
    	try {
	    	Files.copy(in1, new File(projectLocation + File.separator + "RegToolData" + File.separator + "ppk").toPath());
	    	Files.copy(in2, new File(projectLocation + File.separator + "RegToolData" + File.separator + "gis").toPath());
	    	Files.copy(in3, new File(projectLocation + File.separator + "RegToolData" + File.separator + "hardConfig.properties").toPath());
    	} catch (IOException e) {
    		System.out.println("error opening packaged files");
    		System.exit(0);
    		return false;
    	}
    	
    	
        /* Verify a DSA signature */
    	try{
 
            /* import encoded public key */
 
            FileInputStream keyfis = new FileInputStream(publicKey);
            byte[] encKey = new byte[keyfis.available()];  
            keyfis.read(encKey);
 
            keyfis.close();
 
            X509EncodedKeySpec pubKeySpec = new X509EncodedKeySpec(encKey);
 
            KeyFactory keyFactory = KeyFactory.getInstance("DSA", "SUN");
            PublicKey pubKey = keyFactory.generatePublic(pubKeySpec);
 
            /* input the signature bytes */
            FileInputStream sigfis = new FileInputStream(signature);
            byte[] sigToVerify = new byte[sigfis.available()]; 
            sigfis.read(sigToVerify );
 
            sigfis.close();
 
            /* create a Signature object and initialize it with the public key */
            Signature sig = Signature.getInstance("SHA1withDSA", "SUN");
            sig.initVerify(pubKey);
 
            /* Update and verify the data */
 
            FileInputStream datafis = new FileInputStream(propFile);
            BufferedInputStream bufin = new BufferedInputStream(datafis);
 
            byte[] buffer = new byte[1024];
            int len;
            while (bufin.available() != 0) {
                len = bufin.read(buffer);
                sig.update(buffer, 0, len);
                };
 
            bufin.close();
 
 
            boolean verifies = sig.verify(sigToVerify);
            
            File file1 = new File(projectLocation + "\\RegToolData\\ppk");
            File file2 = new File(projectLocation + "\\RegToolData\\gis");
            File file3 = new File(projectLocation + "\\RegToolData\\hardConfig.properties");
            
            //System.out.println("delteting files");
            file1.delete();
            file2.delete();
            file3.delete();
 
            System.out.println("signature verifies: " + verifies);
            if (verifies == false) {
            	System.exit(0);
            }
            
            if (verifies == true) {
            	return true;
            }
 
 
        } catch (Exception e) {
            System.err.println("Caught exception " + e.toString());
        };
	return false;
 
    }
 
}
