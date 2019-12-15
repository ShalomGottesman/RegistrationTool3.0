package main;

import javax.swing.SwingUtilities;
import project.GUI.GUIInput;

public class initiate {
	public static void main(String[] args) {
		SwingUtilities.invokeLater(new Runnable() {
			public void run() {
				GUIInput.main(null);
			}
		});
	}
}
