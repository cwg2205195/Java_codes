/*
	date:2016.10.07
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
public class action implements ActionListener{
		//variables-------------------------------------------------------//
		//Scanner reader=new Scanner(System.in);
		File file_or_dir;				//opening for local file 
		String path;					//setting local path
		String []fileList;				//set file list buff , return to client 
		JTextArea jtaa;
		JTextField field;
		serverSocket sS;
		JTextArea jtaa1;
		int port;
		JTextField portFiled;
		Thread server;
		//operations------------------------------------------------------//
		public void actionPerformed(ActionEvent ae){
			//System.out.println("-----debug-----");
			//jtaa.setText(null);					//clear last written
			path=new String(field.getText());		//retrieve local path
			if(path==null){				//no path specified 
				return;
			}
			try{
				try{
					sS.setPort(Integer.parseInt(portFiled.getText()));
				}
				catch(NumberFormatException nfe){
					jtaa1.append("Specicy port number , please\n");
					return;
				}
				file_or_dir=new File(path);	//create local file
				
				if(file_or_dir.isDirectory()){	//it's a directory
					
						System.out.println("directory detectived");
						fileList=file_or_dir.list();
						int i=0;
					while(i<fileList.length){
						//jtaa.append("This is test from me ");
						jtaa.append(fileList[i]+"\n");
						//System.out.println("-----====-----");
						i++;
						}
					
				}
				else if(file_or_dir.exists()){						//it's a file
					jtaa.append(path);
				}
				else{
					jtaa.append("file not found ");
					return;
				}
				//int i=fileList.length();
				
			}
			catch(Exception ioe){
				jtaa.append(ioe.toString());
				
			}
			
				sS.setLog(jtaa1);			//setting for output  log
			//System.out.println("debug-----");
			
			
				//sS.setPort(Integer.parseInt(portFiled.getText()));		//setting up port to listen on
				
				sS.setFiles(fileList);			//passing argument of file lists
				sS=new serverSocket();
				server=new Thread(sS);
				server.start();
				//sS. serverSocket();
				if(sS==null){
					System.out.println("shit null pointer");
				}return;
			//System.out.println("port: "+Integer.parseInt(portFiled.getText()));
			
		}
		public void setPath(JTextField path){
			field=path;
		}
		public void setFileListBuff(String []buf){
			fileList=buf;
		}
		public void setDisplay(JTextArea jta){
			jtaa=jta;
		}
		public void setlog(JTextArea jta){
			jtaa1=jta;
		}
		public void setPort(JTextField port){
			portFiled=port;
		}
}
