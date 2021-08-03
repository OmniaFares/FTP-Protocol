package ftp_project;


import java.io.FileInputStream;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.BufferedReader;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Scanner;
import java.util.Vector;

public class Server {
	public static void main(String[] args) {
		
		try {
			
			ServerSocket serverSocket = new ServerSocket(3600);
			ServerSocket serverSocket2 = new ServerSocket(4350);
			System.out.println("Server is booted up ");

			while (true) {
				Socket clientSocket = serverSocket.accept();
				System.out.println("a client has connected.");
				Thread client = new clientconnection(clientSocket, serverSocket2);
				client.start();
			}
		} catch (IOException e) {
			System.out.println("Problem with Server Socket.");
		}
	}
	static class clientconnection extends Thread {
		private Socket clientSocket;
		private ServerSocket serverSocket2;

		public clientconnection(Socket clientSocket, ServerSocket sends) 
		{
			this.clientSocket = clientSocket;
			this.serverSocket2 = sends;
		}
			
		
		
		public void directories(String username, String directory) {
			DataOutputStream output;
			try {
				
				output = new DataOutputStream(clientSocket.getOutputStream());
				File file = new File("C:\\Users\\PAV\\Desktop\\ftp_project\\directories\\" + username + "\\" + directory);
			String[] filearray = file.list();
			for(String dir:filearray){
	        	output.writeUTF(dir);
	        }
			} catch (IOException e) {
				System.out.println("Directory isn't avialable");
			}
		}
			
		@Override
		public void run() {
			try {
				String [] u = new String[8];
				
				DataInputStream input = new DataInputStream(clientSocket.getInputStream());
				DataOutputStream output = new DataOutputStream(clientSocket.getOutputStream());
				output.writeUTF("connected.");
			
					int flag = 0, flag1=0;
					output.writeUTF("Send your username ");
					String username = input.readUTF();
					
					try {
					      File myObj = new File("C:\\Users\\PAV\\Desktop\\ftp_project\\clients\\login.txt");
					      Scanner myReader = new Scanner(myObj);
					      int i=0;
					      while (myReader.hasNextLine()) {
					    	  String data = myReader.nextLine();
						        u[i] = data;
						        i++;
					      }
					      myReader.close();
					    } catch (FileNotFoundException e) {
					      System.out.println("An error occurred.");
					      e.printStackTrace();
					    }
                    String pass = null;
					for(int k=0;k<8;k++)
					{   
						if(u[k].equalsIgnoreCase(username))
						{
							output.writeUTF("Username is found, Please enter your password");
							flag = 1;
							pass = u[k+1];
							output.writeUTF("Send your Password ");
							String password = input.readUTF();
							if(password.equalsIgnoreCase(pass))
							{
								output.writeUTF("Login Successfully");
							}
							else
							{
								flag1=1;
							}
							break;
						}
					}	
					
					if(flag == 0 || flag1==1)
					{
						System.out.println("Not found, Login Failed and the connection will terminate");
						clientSocket.close();
					}
						boolean answer=true;
						while(answer)
						{
					String ShowDirec = input.readUTF();
					if(ShowDirec.equalsIgnoreCase("show my directories"))
					{
					directories(username, "");
					ShowDirec = input.readUTF();
					directories(username , ShowDirec);
					
					String send = input.readUTF();
					Socket sending = serverSocket2.accept();
					DataInputStream din = new DataInputStream(sending.getInputStream());
					DataOutputStream dout = new DataOutputStream(sending.getOutputStream());
					
					File file = new File("C:\\Users\\PAV\\Desktop\\ftp_project\\directories\\"+username+ "\\"+ShowDirec+"\\" +send);
                    FileInputStream fin = new FileInputStream(file);
					

					byte b[] = new byte[1024];

					int read;

					while ((read = fin.read(b)) != -1) {
						dout.write(b, 0, read);
					}
					
					
					output.flush();
					sending.close();
					din.close();
					dout.close();
				    output.writeUTF("connection is terminated");
						}
					else
					{
						clientSocket.close();
						input.close();
					    output.close();
						
					}
						}
						
				

			} catch (IOException e) {
				System.out.println("Connection with This client is terminated.");
			}
		}
		}
	}
