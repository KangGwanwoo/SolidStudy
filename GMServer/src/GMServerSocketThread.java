import java.io.*;
import java.net.*;

public class GMServerSocketThread extends Thread {
	Socket msgSocket;
	Socket dataSocket;
	Socket controlSocket;

	GMServer GMS;
	PrintWriter msgPW;
	BufferedReader msgBR;
	PrintWriter dataOut;
	BufferedReader dataIn;
	PrintWriter controlOut;
	BufferedReader controlIn;
	

	String name;
	String ThreadName = "Thread";
	String valGiveRight="20";//10ÀÌ¸é ±Ç¸® È¹µæ
	
	public GMServerSocketThread(GMServer GMS, Socket msgSocket,
			Socket dataSocket,Socket controlsocket,int numName) {
		this.GMS = GMS;
		this.msgSocket = msgSocket;
		this.dataSocket = dataSocket;
		this.controlSocket = controlsocket;
		setName(String.valueOf(numName));
		ThreadName = getName();
		
		System.out.println(msgSocket.getInetAddress() + "ip¿¡¼­ ½ºÄÉÄ¡ºÏ¿¡ ÆæÀ» µé¾ú½À´Ï´Ù.");
		System.out.println("ThreadName:" + ThreadName);
	}

	public void sendData(String coor) {
		dataOut.println(coor);
	}

	public void sendMessage(String str) {
		msgPW.println(str);

	}
	
	public void sendConbit(String val){
		controlOut.println(val);
	}

	public void run() {
		try {
			msgBR = new BufferedReader(new InputStreamReader(
					msgSocket.getInputStream()));
			msgPW = new PrintWriter(msgSocket.getOutputStream(), true);

			dataIn = new BufferedReader(new InputStreamReader(
					dataSocket.getInputStream()));
			dataOut = new PrintWriter(dataSocket.getOutputStream(), true);
			
			controlIn = new BufferedReader(new InputStreamReader(
					controlSocket.getInputStream()));
			controlOut = new PrintWriter(controlSocket.getOutputStream(), true);
	
			
			charThread Str = new charThread(GMS, this, msgBR);
			controlThread contr = new controlThread(GMS, this, controlIn);


			Str.start();
			contr.start();
			
			while (true){
				String sdata;
				sdata = dataIn.readLine();
				GMS.databroadCasting(sdata);			
				
			}

		} catch (Exception e) {
			System.out.println(ThreadName + "ÅðÀåÇÔ");
			GMS.removeClient(this);
		} finally {
			try {
				msgSocket.close();
				dataSocket.close();
				controlSocket.close();

			} catch (Exception ex) {
				ex.printStackTrace();

			}
		}
	}

}
