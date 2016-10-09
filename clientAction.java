/*
	date:2016.10.09
	author:Hunter
	description:		Supports for file ahring 
*/
import java.util.Scanner;
import java.io.*;
import java.io.File;
import java.util.*;
import java.awt.event.*;
import javax.swing.*;
import java.awt.*;
import java.net.*;
public class clientAction implements ActionListener{
		//variables-------------------------------------------------------//
		//Scanner reader=new Scanner(System.in);
		//File file_or_dir;				//opening for local file 
		//String path;					//setting local path
		String []fileList;				//set file list buff , return to client 
		JTextArea jtaa;				//used for displayping 
		
		//serverSocket sS;
		//JTextArea jtaa1;
		byte []nameBuf=new byte[1024];
		int port;
		String serverIP;
		JTextField portFiled;
		JTextField IPField;
		InetAddress serverAddr;
		Socket clientSocket=null;
		DataInputStream in=null;
		DataOutputStream out=null;
		//Thread server;
		//operations------------------------------------------------------//
		public void actionPerformed(ActionEvent ae){
			try{
				jtaa.setText(null);
				clientSocket=new Socket();
				//jtaa.append("0");
				serverIP=IPField.getText();
				//jtaa.append("1");
				port=Integer.parseInt(portFiled.getText());
				//jtaa.append("2");
				serverAddr=InetAddress.getByName(serverIP);
				//jtaa.append("3");
				InetSocketAddress socketAddr=new InetSocketAddress(serverAddr,port);
				//jtaa.append("4");
				clientSocket.connect(socketAddr);
				//jtaa.append("5");
				clientAction1.setSocket(clientSocket);
				in=new DataInputStream(clientSocket.getInputStream());
				out=new DataOutputStream(clientSocket.getOutputStream());
				
				in.read(nameBuf);
				String display=new String(nameBuf);
				String token=new String("#");		//sepcify the default token for seperating data
				StringTokenizer analysor=new StringTokenizer(display,token);
				while(analysor.hasMoreTokens()){
					String tmp=analysor.nextToken();
					jtaa.append(tmp+"\n");
				}
				
				/*out.write("bye".getBytes());
				clientSocket.close();*/
			}
			catch(Exception e){
				jtaa.append(e.toString()+"from exception");
			}
			
			
			
		}
		public void setIP(JTextField path){
			IPField=path;
		}
		public void setFileListBuff(String []buf){
			fileList=buf;
		}
		public void setDisplay(JTextArea jta){
			jtaa=jta;
		}
		public void setPort(JTextField port){
			portFiled=port;
		}
		
}
