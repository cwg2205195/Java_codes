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

public class serverWindow extends JFrame{
	//-----------------------define all necessary component--------------
	JTextField path;		//used for setting up local path
	JTextField port;		//used for setting up local port
	JTextArea  showResult;	//used for displaying what file being transfered to client
	JTextArea fileStransfered;//display files that were been retrieved by user
	JScrollPane jsp;
	JScrollPane jsp1;
	JButton confirm;		//the button
	action listener;		//monitor for the button
	String client_cmd;		//used for retrieve client's request
	String []file_list;		//used for sent back to client
	//-----------------------methods and operations------------------
	public serverWindow(){}
	public serverWindow(String s,int x,int y,int w,int h){
		init(s);
		setLocation(x,y);
		setSize(w,h);
		setVisible(true);
		setDefaultCloseOperation(EXIT_ON_CLOSE);
	}
	void init(String s){
		setTitle(s);
		setLayout(new FlowLayout());
		add(new JLabel("Sharing path:"));
		
		path=new JTextField(30);
		add(path);
		
		add(new JLabel("Listen Port: "));
		port=new JTextField(30);
		add(port);
		
		listener=new action();
		listener.setPath(path);
		listener.setlog(fileStransfered);
		listener.setPort(port);
		listener.setFileListBuff(file_list);
		
		confirm=new JButton("OK");
		confirm.addActionListener(listener);
		add(confirm);
		
		showResult=new JTextArea(30,30);
		jsp=new JScrollPane(showResult);
		add(new JLabel("Current Sharing Files:"));
		add(jsp);
		listener.setDisplay(showResult);
		
		fileStransfered=new JTextArea(30,30);
		jsp1=new JScrollPane(fileStransfered);
		add(new JLabel("user retrieved:"));
		add(jsp1);
		listener.setlog(fileStransfered);
		serverSocket.path=path;
	}
}
