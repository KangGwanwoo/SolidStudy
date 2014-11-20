package GMClient;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionAdapter;
import java.awt.event.MouseMotionListener;

import javax.swing.*;
import javax.swing.SpringLayout.Constraints;

import java.io.*;
import java.net.*;
import java.util.concurrent.BrokenBarrierException;

public class GMClientGui extends JFrame implements ActionListener, Runnable {
	private static final long serialVersionUID = 12345541234L;
	JPanel mainp;

	JPanel loginPanel = new JPanel();
	JPanel mainPanel = new JPanel();
	JPanel charArea = new JPanel();
	JPanel charFiled = new JPanel();
	JPanel charInput = new JPanel();
	JPanel drawPadPen = new JPanel();
	JPanel inputP;
	JPanel dumpLeft = new JPanel();
	JPanel dumpRight = new JPanel();
	private CardLayout cardLayout;
	JPanel panel = new JPanel();
	TextArea textArea1 = new TextArea(20, 50);
	TextField textField1 = new TextField(50);
	TextField idTF = new TextField(20);
	PadDraw drawPad;
	Socket charSocket;
	Socket dataSocket;
	Socket controlSocket;

	PrintWriter charPwOut;
	BufferedReader charBufferIn;

	PrintWriter dataOut;
	BufferedReader dataIn;

	PrintWriter controlOut;
	BufferedReader controlIn;

	String str;
	Graphics2D gra;
	int check = 0;
	int throwOldX, throwOldY, throwCurrentX, throwCurrentY;
	int meOldX, meOldY, meCurrentX, meCurrentY;

	String valGiveRight = "20";// 10이면 권리 획득
	GridBagLayout grid;
	GridBagConstraints gridc;
	GridBagLayout grid2;
	GridBagConstraints gridc2;
	
	public GMClientGui(String ip, int port1) {
		
		cardLayout = new CardLayout();
		grid = new GridBagLayout();
		gridc = new GridBagConstraints();
		
		mainp=(JPanel)this.getContentPane();
		mainp.setLayout(cardLayout);
		
		drawPad = new PadDraw();
		
		System.out.println(this.getClass().getName() + "1.start-->");
		
		loginFrom();

		try {
			charSocket = new Socket(ip, port1);
			dataSocket = new Socket(ip, port1);
			controlSocket = new Socket(ip, port1);
		} catch (Exception e) {
			System.out.println("서버 연결에 실패하였습니다.");
		}
		
		System.out.println(this.getClass().getName() + "2.Socket-->");

	}
	
	

	public void giveAndTake() {
		try {
			
			System.out.println(this.getClass().getName() + "3.inputoutput-->");
			charPwOut = new PrintWriter(charSocket.getOutputStream(), true);
			charBufferIn = new BufferedReader(new InputStreamReader(
					charSocket.getInputStream()));

			dataOut = new PrintWriter(dataSocket.getOutputStream(), true);
			dataIn = new BufferedReader(new InputStreamReader(
					dataSocket.getInputStream()));

			controlIn = new BufferedReader(new InputStreamReader(
					controlSocket.getInputStream()));
			controlOut = new PrintWriter(controlSocket.getOutputStream(), true);
			
			Thread ctr = new Thread(this);

			ctr.start();

		} catch (Exception e) {
			e.getMessage();
		}
	}

	@Override
	public void run() {
		// TODO Auto-generated method stub
		try {

			System.out.println(this.getClass().getName() + "4.run-->");
			String va;
			charThread Str = new charThread(this, charBufferIn);
			controlThread valtr=new controlThread(this, controlIn);
			Str.start();
			valtr.start();
			while (true) {

				if (check == 0) {
					va = dataIn.readLine();
					meOldX = Integer.parseInt(va);
					va = dataIn.readLine();
					meOldY = Integer.parseInt(va);

					this.drawPad.OtherUserMousePressed(meOldX, meOldY);
					check = 1;
				} else {
					va = dataIn.readLine();
					meCurrentX = Integer.parseInt(va);
					va = dataIn.readLine();
					meCurrentY = Integer.parseInt(va);
					if (meCurrentX == 0) {
						check = 0;
					} else {
						this.drawPad.paintOtherUser(meCurrentX, meCurrentY);
					}


				}
			}
		} catch (Exception ex) {
			ex.printStackTrace();
		} finally {
			try {
				charSocket.close();
				dataSocket.close();
				controlSocket.close();
			} catch (Exception ea) {
				ea.getMessage();
			}
		}
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		// TODO Auto-generated method stub
		if (e.getSource() == idTF) {
			String id = idTF.getText();
			if (id == null || id.trim().equals("")) {
				System.out.println("아이디를 다시 입력하여주세요");// 메시지 뜨게끔 하기
				return;
			}
			charPwOut.println(id.trim());
			charPwOut.flush();

			inits();

		} else if (e.getSource() == textField1) {
			String strs = this.textField1.getText();
			charPwOut.println(strs);
			charPwOut.flush();
			this.textField1.setText("");
			this.textField1.requestFocus();
		}
	}

	
	private void loginFrom() {
		this.setSize(new Dimension(500,400));
		this.setTitle("로그인 정보");

		// 총 사이즈 설정
		Dimension monitorSize=Toolkit.getDefaultToolkit().getScreenSize();
		Dimension framesize= this.getSize();
		if(framesize.height>monitorSize.height){
			framesize.height=monitorSize.height;
		}
		if(framesize.width>monitorSize.width){
			framesize.width=monitorSize.width;
		}

		int locationX=(monitorSize.width-framesize.width)/2;
		int locationY=(monitorSize.height-framesize.height)/2;
		this.setLocation(locationX,locationY);
		this.setVisible(true);
		 enableEvents(AWTEvent.WINDOW_EVENT_MASK);
		 
		 
		loginPanel.setLayout(grid);
		gridc.gridx = 0;
		gridc.gridy = 2;
		gridc.gridwidth =1;
		gridc.gridheight = 1;
		gridc.fill=GridBagConstraints.BOTH;
		
		loginPanel.add(new Label("아이디를 입력하세요"),gridc);
		

		idTF = new TextField(20);
		idTF.addActionListener(this);
		inputP = new JPanel();
		inputP.add(idTF);
		
		gridc.gridx = 0;
		gridc.gridy = 3;
		gridc.gridwidth =3;
		gridc.gridheight = 1;
		gridc.fill=GridBagConstraints.BOTH;
		
		loginPanel.add(inputP, gridc);

		
		mainp.add("login", loginPanel);

		cardLayout.show(mainp, "login");
		
		mainp.setVisible(true);

	}

	private void inits() {

		grid2 = new GridBagLayout();
		gridc2 = new GridBagConstraints();

		// 레이아웃 설정
		this.setSize(1000, 800);
		
		mainPanel.setLayout(grid2);
		mainPanel.setSize(800,700);


		drawPad.addMouseListener(new MouseAdapter() {
			public void mousePressed(MouseEvent e) {
				if(valGiveRight.equals("10")){
				throwOldX = e.getX();
				throwOldY = e.getY();
				dataOut.println(throwOldX);
				dataOut.println(throwOldY);
				}

			}
		});

		drawPad.addMouseMotionListener(new MouseMotionAdapter() {
			public void mouseDragged(MouseEvent ez) {
				if(valGiveRight.equals("10")){
				throwCurrentX = ez.getX();
				throwCurrentY = ez.getY();
				dataOut.println(throwCurrentX);
				dataOut.println(throwCurrentY);
				}
			}
		});

		drawPad.addMouseListener(new MouseAdapter() {
			public void mouseReleased(MouseEvent e) {
				if(valGiveRight.equals("10")){
				meCurrentX = 0;
				meCurrentY = 0;

				dataOut.println("0");
				dataOut.println("0");
				}
			}
		});
		Icon iconB = new ImageIcon("blue.gif");
		// the blue image icon
		Icon iconM = new ImageIcon("magenta.gif");
		// magenta image icon
		Icon iconR = new ImageIcon("red.gif");
		// red image icon
		Icon iconBl = new ImageIcon("black.gif");
		// black image icon
		Icon iconG = new ImageIcon("green.gif");
		// finally the green image icon
		// These will be the images for our colors.

		// creates a JPanel
		panel.setPreferredSize(new Dimension(40, 68));
		panel.setMinimumSize(new Dimension(40, 68));
		panel.setMaximumSize(new Dimension(40, 68));
		// This sets the size of the panel

		JButton clearButton = new JButton("Clear");
		// creates the clear button and sets the text as "Clear"
		clearButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				drawPad.clear();
			}
		});
		// this is the clear button, which clears the screen. This pretty
		// much attaches an action listener to the button and when the
		// button is pressed it calls the clear() method

		JButton redButton = new JButton("Red");
		// creates the red button and sets the icon we created for red
		redButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				drawPad.red();
			}
		});
		// when pressed it will call the red() method. So on and so on =]

		JButton blackButton = new JButton("Black");
		// same thing except this is the black button
		blackButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				drawPad.black();
			}
		});

		JButton magentaButton = new JButton("plnk");
		// magenta button
		magentaButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				drawPad.magenta();
			}
		});

		JButton blueButton = new JButton("Blue");
		// blue button
		blueButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				drawPad.blue();
			}
		});

		JButton greenButton = new JButton("green");
		// green button
		greenButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				drawPad.green();
			}
		});
		JButton drawButton = new JButton("draw");
		// green button
		drawButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {

				valGiveRight= "10";
				controlOut.println(valGiveRight);
				drawPad.setRight(valGiveRight);

			}
		});

		// sets the sizes of the buttons

		panel.add(greenButton);
		panel.add(blueButton);
		panel.add(magentaButton);
		panel.add(blackButton);
		panel.add(redButton);
		panel.add(clearButton);
		panel.add(drawButton);
		// adds the buttons to the panel
		
		gridc.gridx = 0;
		gridc.gridy = 0;
		gridc.gridwidth =10;
		gridc.gridheight = 8;
		gridc.fill=GridBagConstraints.BOTH;
		gridc.weightx = 1;
		gridc.weighty = 1;
		mainPanel.add(drawPad,gridc);
		
		gridc.gridx = 0;
		gridc.gridy = 8;
		gridc.gridwidth =1;
		gridc.gridheight = 1;
		gridc.weightx = 0;
		gridc.weighty = 0;
		gridc.fill=GridBagConstraints.NONE;
		mainPanel.add(new Button("저장"),gridc);
		
		gridc.gridx = 1;
		gridc.gridy = 8;
		gridc.gridwidth =8;
		gridc.gridheight = 1;
		gridc.weightx = 0.3;
		gridc.weighty = 0;
		gridc.fill=GridBagConstraints.BOTH;
		mainPanel.add(panel, gridc);
		
		gridc.gridx = 9;
		gridc.gridy = 8;
		gridc.gridwidth =1;
		gridc.gridheight = 1;
		gridc.weightx = 0;
		gridc.weighty = 0;
		gridc.fill=GridBagConstraints.NONE;
		mainPanel.add(new Button("나가기"),gridc);
		textArea1.setSize(new Dimension(1000,100));
		gridc.gridx = 0;
		gridc.gridy = 9;
		gridc.gridwidth =10;
		gridc.gridheight = 1;
		gridc.weightx = 0.1;
		gridc.weighty = 0.1;
		gridc.fill=GridBagConstraints.HORIZONTAL;
		mainPanel.add(textArea1, gridc);
		
		gridc.gridx = 0;
		gridc.gridy = 10;
		gridc.gridwidth =10;
		gridc.gridheight =1;
		gridc.weightx = 0.1;
		gridc.weighty = 0.1;
		gridc.fill=GridBagConstraints.HORIZONTAL;
		mainPanel.add(textField1, gridc);

		mainp.add("main", mainPanel);
		
		this.textField1.addActionListener(this);
		this.textArea1.setEditable(false);
		
		cardLayout.show(mainp, "main");
	}
	
	public void setRight(String val){
		this.valGiveRight = val;
	}
}
