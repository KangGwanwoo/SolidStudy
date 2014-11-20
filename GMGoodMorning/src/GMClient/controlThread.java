package GMClient;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.PrintWriter;

public class controlThread extends Thread{

	BufferedReader controlIn;
	GMClientGui gm;
	private String va;

	public controlThread(GMClientGui g, BufferedReader in) {
		gm = g;
		controlIn = in;
	}

	@Override
	public void run() {
		// TODO Auto-generated method stub
		String valGiveRight;

		while (true) {
			try {
				valGiveRight = controlIn.readLine();
				gm.setRight(valGiveRight);
				gm.drawPad.setRight(valGiveRight);
	
				
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}

	}
}