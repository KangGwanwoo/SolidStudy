import java.io.BufferedReader;
import java.io.IOException;
import java.io.PrintWriter;

public class controlThread extends Thread {

	BufferedReader controlIn;
	GMServer GMserver;
	GMServerSocketThread GMS;

	public controlThread(GMServer g, GMServerSocketThread gms, BufferedReader in) {
		GMserver = g;
		GMS = gms;
		controlIn = in;
	}

	@Override
	public void run() {
		// TODO Auto-generated method stub
		String valGiveRight = "20";
		GMS.sendConbit(valGiveRight);
		

		while (true) {

			try {

				valGiveRight = controlIn.readLine();
				if (valGiveRight.equals("10")) {
					GMserver.controlBroadCasting("20", GMS.getName());
					GMS.sendConbit(valGiveRight);
				} else if (valGiveRight.equals("20")) {
					GMserver.allSameBroadCasting("20");
				}

			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}

		}
	}
}