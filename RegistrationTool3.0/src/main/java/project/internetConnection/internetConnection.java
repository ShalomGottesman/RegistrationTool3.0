package project.internetConnection;

import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;

public class internetConnection {

	public static void main(String[] args) {
		System.out.println("connection: " + isConnectionAvailable());
	}
	
	public static boolean isConnectionAvailable() {
		try {
			final URL url = new URL("https://selfserveprod.yu.edu/pls/banprd/twbkwbis.P_GenMenu?name=homepage");
			final URLConnection con = url.openConnection();
			con.connect();
			con.getInputStream().close();
			return true;
		} catch (MalformedURLException e) {
			return false;
		} catch (IOException e) {
			return false;
		}
	}
}
