package GMClient;

import javax.swing.JFrame;

public class GMClientMain {
	public static void main(String[] args) {

		GMClientGui main = new GMClientGui("127.0.0.1", 5017);
		main.giveAndTake();

	}

}
