package project.preCheck;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.LinkOption;
import java.nio.file.Path;
import java.nio.file.Paths;

public class initialization {
	private static String infoFolderpath;
	public static void main(String[] args) {
		String projectLocation = System.getProperty("user.dir");
		infoFolderpath = projectLocation + "\\RegToolData";
		Path folderPath = Paths.get(infoFolderpath);
		Path chrDriverPath = Paths.get(infoFolderpath + "\\chromedriver.exe");
		Path gecDriverPath = Paths.get(infoFolderpath + "\\geckodriver.exe");
		Path phaDriverPath = Paths.get(infoFolderpath + "\\phantomjs.exe");
		
		boolean folderCheck = Files.exists(folderPath, LinkOption.NOFOLLOW_LINKS);
		boolean chromDriverCheck = Files.exists(chrDriverPath, LinkOption.NOFOLLOW_LINKS);
		boolean geckoDriverCheck = Files.exists(gecDriverPath, LinkOption.NOFOLLOW_LINKS);
		boolean phantomDriverCheck = Files.exists(phaDriverPath, LinkOption.NOFOLLOW_LINKS);
		
		if (folderCheck == false) {
			new File(infoFolderpath).mkdir();
		}
		try {
			if (chromDriverCheck == false) {
				extractDriver("chromedriver.exe");
			}
			if (geckoDriverCheck == false) {
				extractDriver("geckodriver.exe");
			}
			if (phantomDriverCheck == false) {
				extractDriver("phantomjs.exe");
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	private static void extractDriver(String driverName) throws IOException {
		InputStream in = initialization.class.getResourceAsStream("/" + driverName);
		Files.copy(in, new File(infoFolderpath + "\\" + driverName).toPath());

	}
}
