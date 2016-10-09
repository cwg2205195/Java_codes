/*
	date:2016.10.08
	author:Hunter
	description:	a part of file sharing program.  the core Socket file for server side
*/
import java.util.*;
import java.io.*;
import java.net.*;
import javax.swing.*;
import java.awt.*;

public class serverSocket implements Runnable{
	//variables-------------------------------------------------------//
		ServerSocket server=null;
		ServerThread thread;
		Socket client=null;
		static int port1;					//the port for server to listen on
		static String []files;				//current sharing files
		String reply;				//reply to client first
		static JTextArea jta1;				//display the log
		static JTextField path;			//getting file path
	//operations------------------------------------------------------//
	public void run(){		//constructor of server's socket
			try{
				server=new ServerSocket(port1);
			}
			catch(IOException ioe){
				String error="Can not bind to this port : "+ioe.toString();
				JOptionPane.showMessageDialog(null,error,"Error",JOptionPane.ERROR_MESSAGE);
				return;
			}
			//thread.setFirstReply(files);
			//jta1.append("Debug .... from socket222");
			//System.out.println("from server socket\n");
			//jta1.append("Debug .... from socket");
		while(true){
			try{
				client=server.accept();
				
				if(client!=null){
					new ServerThread(client).start();
					//System.out.println("on loop");
					InetAddress clientAddr=client.getInetAddress();
					String say="\nclient : "+clientAddr.getHostAddress()+" connected\n";
					jta1.append(say);
				}
			}
			catch(IOException ioe1){
			
			}
		}
		
		
	}
	public static void setLog(JTextArea jta){		//setting up log display window
		jta1=jta;
	}
	public static void setPort(int port){			//setting up local port
		port1=port;
	}
	public static void setFiles(String []str){		//for retrieving file names
		files=str;
	}
	class ServerThread extends Thread{
		//variables-------------------------------------------------------//
		Socket clientScoket;
		DataOutputStream out=null;
		DataInputStream in=null;
		//static String []files;
		//operations------------------------------------------------------//
		public void setFirstReply(String []a){
			files=a;
		}
		ServerThread(Socket t){
			clientScoket=t;
			try{					//opening stream for read client request and write back reply
				out=new DataOutputStream(clientScoket.getOutputStream());
				in =new DataInputStream(clientScoket.getInputStream());
			}
			catch(IOException ioe2){
				jta1.append(ioe2.toString()+"\n");
			}
		}
		public void run(){
			byte [] requestFileName=new byte[1024];//file name storage
			String trueName;
			byte content[]=new byte[2048];		//for sent file back to client
			StringBuffer sb=new StringBuffer();
			int num=files.length-1;
			File requestFile;
			FileInputStream fis;
			DataInputStream dis;
			InetAddress clientIP;
			clientIP=clientScoket.getInetAddress();
			
			
			//sending current sharing file name to client first !
			while(num>=0){
				sb.append(files[num]+"#");		//adding a seperator at the end
				num--;
			}
			trueName=sb.toString();
			content=trueName.getBytes();
			//content="hello from server".getBytes();
			try{
				//jta1.append(sb.toString());
				out.write(content);
			}
			catch(IOException ioe5){
				jta1.append(ioe5.toString()+"\n");
			}
			
			
			//waiting for client's request
			while(true){
				try{
					int n=in.read(requestFileName);//read file name from client's socket
					trueName=new String(requestFileName,0,n);		//becareful with n !!!!!!!!!!
					n=files.length-1;
					//debug---------------------------------
					if(trueName.equals("bye")){
						jta1.append(clientIP.toString()+" said goodbyte \n  ");
						return;
					}
					jta1.append(clientIP.toString()+" requested:  "+trueName+"\n");
					
					//finding requested file
					while(n>=0){
						//jta1.append("finding "+files[n]+"\n");
						if(files[n].equals(trueName)){	//found requested file
							//n=666;				//set a flag ~~
							break;
						}
						if(n!=0)
							n--;		//go back
					}
					if(files[n].equals(trueName)){		//file found
						byte []content1=new byte[4096];
						requestFile=new File(path.getText()+trueName);
						//jta1.append("file "+files[n]+" found in local directory 111\n");
						fis=new FileInputStream(requestFile);
						dis=new DataInputStream(fis);
						/*while((n=dis.read(content1,0,2048))!=-1){
							String s=new String(content1,0,n);
							System.out.print(s);
						}*/
						//jta1.append("file "+files[n]+" found in local directory 222\n");
						while((n=dis.read(content1,0,4096))!=-1){
							//jta1.append("file "+files[n]+" found in local directoryddd\n");
							try{
								//System.out.println(""+n);
								out.write(content1,0,n);
							}
							catch(IOException ioe4){
								jta1.append(ioe4.toString()+" ????\n");
							}
						}
						out.write("bye".getBytes());
						jta1.append("File "+trueName+" transfered\n");
						dis.close();
						fis.close();
						//reply=new String(files[n]+" file found \n");
					}
					else{
						reply=new String("no");
						jta1.append("file not found in local directory I said no\n");
						try{
							out.write(reply.getBytes());
						}
					catch(IOException ioe4){
							jta1.append(ioe4.toString()+"\n");
						}
					}
				}
				catch(IOException ioe3){
					jta1.append(ioe3.toString()+"\n");
					return;
				}
				//sent thing back to client------------------------
				
			}
			
		}
	}
}
