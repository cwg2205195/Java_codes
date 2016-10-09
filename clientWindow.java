/*
	date:2016.10.07
	author:Hunter
	description:		creating for file sharing server's GUI
*/
import java.io.*;
import javax.swing.*;
import java.awt.*;
import java.util.*;
import java.awt.event.*;
import static javax.swing.JFrame.*;

public class clientWindow extends JFrame{
	//-----------------------define all necessary component--------------
	JTextField serverIP;		//used for setting up local path
	JTextField serverPort;		//used for setting up local port
	JTextField requestFile;		//for request to server
	JButton	   ok;
	JTextArea  showResult;	//used for displaying what file being transfered to client
	JTextArea fileStransfered;//display files that were been retrieved by user
	JScrollPane jsp;
	JScrollPane jsp1;
	JButton confirm;		//the button
	clientAction listener;		//monitor for the button
	clientAction1 listener1;		//monitor for the button
	String client_cmd;		//used for retrieve client's request
	String []file_list;		//used for sent back to client
	//-----------------------methods and operations------------------
	public clientWindow(){}
	public clientWindow(String s,int x,int y,int w,int h){
		init(s);
		setLocation(x,y);
		setSize(w,h);
		setVisible(true);
		setDefaultCloseOperation(EXIT_ON_CLOSE);
	}
	void init(String s){
		setTitle(s);
		setLayout(new FlowLayout());
		add(new JLabel("Server IP:"));
		
		serverIP=new JTextField(30);
		add(serverIP);
		
		add(new JLabel("Server Port:"));
		serverPort=new JTextField(30);
		add(serverPort);
		
		
		
		confirm=new JButton("OK");
		
		add(confirm);
		
		showResult=new JTextArea(30,30);
		jsp=new JScrollPane(showResult);
		add(new JLabel("Current Sharing Files:"));
		add(jsp);
		//listener.setDisplay(showResult);
		
		fileStransfered=new JTextArea(30,30);
		jsp1=new JScrollPane(fileStransfered);
		add(new JLabel("File retrieved:"));
		add(jsp1);
		//listener.setlog(fileStransfered);
		
		requestFile=new JTextField(30);
		add(new JLabel("chose a file:"));
		add(requestFile);
		
		ok=new JButton("OK");
		add(ok);
		listener=new clientAction();
		listener.setIP(serverIP);
		//listener.setDisplay(showResult);
		listener.setPort(serverPort);
		listener.setFileListBuff(file_list);
		listener.setDisplay(showResult);
		confirm.addActionListener(listener);
		
		listener1=new clientAction1();
		listener1.setDisplay(fileStransfered);
		listener1.setFileName(requestFile);
		ok.addActionListener(listener1);
	}
}
