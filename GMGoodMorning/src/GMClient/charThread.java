package GMClient;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.PrintWriter;

public class charThread extends Thread{

	BufferedReader charBufferIn;
	GMClientGui gm;
	
	public charThread(GMClientGui g,BufferedReader in){
		gm = g;
		charBufferIn=in;
	}



	@Override
	public void run() {
		// TODO Auto-generated method stub
		String str;
		

		while(true){
		try {
			str = charBufferIn.readLine();
			gm.textArea1.append(str + "\n");
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}
	}
}
