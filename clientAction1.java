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
public class clientAction1 implements ActionListener{
		//variables-------------------------------------------------------//
		//Scanner reader=new Scanner(System.in);
		File file;				//creating new file
		FileOutputStream fow;	//creating new file
		//String path;					//setting local path
		String requestFileName;			//sending to server your desiring file name
		JTextArea jtaa;				//used for displayping 	what file had been retrieved
		JTextField fileName;		//this is where we got the file name
		//serverSocket sS;
		//JTextArea jtaa1;
		byte []nameBuf=new byte[1024];
		byte []fileBuff=new byte[4096];
		
		static Socket clientSocket=null;
		DataInputStream in=null;
		DataOutputStream out=null;
		//Thread server;
		//operations------------------------------------------------------//
		public void actionPerformed(ActionEvent ae){
			try{
				requestFileName=fileName.getText();
				//############# to reduce the server's load , we compare the file name in client's machine####################
				
				in=new DataInputStream(clientSocket.getInputStream());
				out=new DataOutputStream(clientSocket.getOutputStream());
				
				//sending file name to server
				nameBuf=requestFileName.getBytes();
				out.write(nameBuf);
				
				//waiting for reply 
				int n=in.read(fileBuff);
				//System.out.println(""+n);
				String state=new String(fileBuff,0,n);
				if(state.equals("no")){		// #####no file found in server
					jtaa.append("Requested file "+requestFileName+" not found in server \n");
					out.write("bye".getBytes());
					clientSocket.close();
					return;
				}
				else
				file=new File(requestFileName);
				if(file.exists()){			//#####same file in current location
					jtaa.append("same name found in current location , can't retrieve file from server\n");
					out.write("bye".getBytes());
					clientSocket.close();
					return;
				}
				try{		//write bytes to local file
					file.createNewFile();
					fow=new FileOutputStream(file,true);
					fow.write(fileBuff,0,n);
					//System.out.println(""+n);
					while((n=in.read(fileBuff))!=-1){
						
						
						String t=new String(fileBuff,0,n);
						//System.out.println(t+" "+n+" bytes tran");
						if(t.endsWith("bye")){
							fow.write(fileBuff,0,n-3);
							jtaa.append("file  <"+requestFileName+">  downloaded\n");
							clientSocket.close();
							fow.close();
							return;
						}
						fow.write(fileBuff,0,n);
					}
							//close local file
					
					fow.close();
				}
				catch(IOException ioe){
					jtaa.append(ioe.toString()+"\n");
					
				}
				//in the end , say good bye to server ,close connection
				out.write("bye".getBytes());
				fow.close();
				
				clientSocket.close();
			}
			catch(Exception e){
				jtaa.append(e.toString()+"from exception");
			}
			
		}
		public static void setSocket(Socket s){
			clientSocket=s;
		}
		
		public void setDisplay(JTextArea jta){
			jtaa=jta;
		}
		public void setFileName(JTextField jtf){
			fileName=jtf;
		}
}
