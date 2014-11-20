import java.io.BufferedReader;
import java.io.IOException;
import java.io.PrintWriter;

public class charThread extends Thread {

	BufferedReader charBufferIn;
	GMServer GMserver;
	GMServerSocketThread GMS;
	private Object name;

	public charThread(GMServer g, GMServerSocketThread gms, BufferedReader in) {
		GMserver = g;
		GMS = gms;
		charBufferIn = in;
	}

	@Override
	public void run() {
		// TODO Auto-generated method stub
		String str;
		int i = 0;
		while (true) {
			try {
				if (i == 0) {
					str = "본인의 아이디를 입력하세요";

					GMS.sendMessage(str);

					name = charBufferIn.readLine();
					GMserver.broadCasting("[" + name + "]" + "님이 입장하셨습니다.");
					i = 1;
				}
				String strin = charBufferIn.readLine();
				GMserver.broadCasting("[" + name + "]" + strin);

			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}

	}
}
