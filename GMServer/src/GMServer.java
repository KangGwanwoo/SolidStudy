import java.io.*;
import java.net.*;
import java.util.*;

public class GMServer {
	ServerSocket ServerSocket1;
	Socket charSocket;
	Socket dataSocket;
	Socket controlSocket;
	Vector ClientThreadStorge;

	public GMServer(){
		ClientThreadStorge=new Vector(5,5);
		System.out.println("������ ���� �Ǿ����ϴ�");
	}
	
	public void giveAndTake(){
		try{
			ServerSocket1=new ServerSocket(5017);
			ServerSocket1.getReuseAddress();
			int i=-1;
			while(true){
				charSocket=ServerSocket1.accept();
				dataSocket=ServerSocket1.accept();
				controlSocket=ServerSocket1.accept();
				
				i++; // thread �̸��� ��ȣ �ο� 
				GMServerSocketThread svrth=new GMServerSocketThread(this, charSocket, dataSocket,controlSocket,i);
				addClient(svrth);
				svrth.start();
			}
			
		}
		catch(Exception ee){ee.printStackTrace();}
	}

	public void addClient(Thread tr){
		ClientThreadStorge.addElement(tr);
		
		System.out.println("�����̳� 1�� ����, ��"+ClientThreadStorge.size()+"��");
		
	}
	
	public void removeClient(Thread tr){
		ClientThreadStorge.removeElement(tr);
		System.out.println("�����̳� 1�� ����, ��"+ClientThreadStorge.size()+"��");
	}
	
	public void broadCasting(String sbc){
		for(int i=0;i<ClientThreadStorge.size();i++){
			GMServerSocketThread svtr=(GMServerSocketThread)ClientThreadStorge.elementAt(i);
			svtr.sendMessage(sbc);
		}
	}
	public void databroadCasting(String da){

		for(int i=0;i<ClientThreadStorge.size();i++){

			GMServerSocketThread svtr=(GMServerSocketThread)ClientThreadStorge.elementAt(i);
			svtr.sendData(da);
		}
	}

	public void controlBroadCasting(String val, String threadName) {
		// TODO Auto-generated method stub
		for(int i=0;i<ClientThreadStorge.size();i++){
			if(threadName.equals(String.valueOf(i))){
				continue;
			}else{
			GMServerSocketThread svtr=(GMServerSocketThread)ClientThreadStorge.elementAt(i);
			svtr.sendConbit(val);
			}
		}
		
	}

	public void allSameBroadCasting(String string) {
		// TODO Auto-generated method stub
		for(int i=0;i<ClientThreadStorge.size();i++){

			GMServerSocketThread svtr=(GMServerSocketThread)ClientThreadStorge.elementAt(i);
			svtr.sendConbit(string);
		
		}
		
		
	}
	
}
