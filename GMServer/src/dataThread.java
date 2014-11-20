import java.io.BufferedReader;
import java.io.IOException;
import java.io.PrintWriter;

public class dataThread implements Runnable {

	BufferedReader dataIn;
	 GMServerSocketThread gmt;
	GMServer gmserver;
	private int meOldX;
	private int meOldY;
	private int meCurrentX;
	private int meCurrentY;
	private int check = 0;
	private String va;

	public dataThread(GMServer g, GMServerSocketThread gms, BufferedReader in) {
		gmserver = g;
		dataIn = in;
		gmt=gms;
	}

	@Override
	public void run() {
		// TODO Auto-generated method stub
		while (true) {

			try {
				String sdata;
				sdata = dataIn.readLine();
				gmserver.databroadCasting(sdata);

			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}
}