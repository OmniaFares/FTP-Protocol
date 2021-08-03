package ftp_project;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.net.InetAddress;
import java.net.Socket;
import java.net.UnknownHostException;
import java.util.Scanner; 

public class Client {

	public static void main(String[] args) {
		try {
		InetAddress ip = InetAddress.getByName("localhost");
		Socket clientSocket = new Socket(ip,3600);		
		DataInputStream input = new DataInputStream(clientSocket.getInputStream());
		DataOutputStream output = new DataOutputStream(clientSocket.getOutputStream());
		
		String isConnected = input.readUTF();
		System.out.println("Server: " + isConnected);
		
		while(true) {
			
		String askforusername = input.readUTF();
		System.out.println("Server : " + askforusername);
		
		Scanner scanner = new Scanner (System.in);
		String username= scanner.nextLine();
		output.writeUTF(username);
		
		String reply=input.readUTF();
		System.out.println("Server: "+reply);
	
		String askforpass = input.readUTF();
		System.out.println("Server : " + askforpass);
	
		Scanner scanner1 = new Scanner (System.in);
		String password = scanner1.nextLine();
		output.writeUTF(password);
		
		reply=input.readUTF();
		System.out.println("Server: "+reply);
		
		boolean answer=true;
		while(answer)
		{
		Scanner scanner2 = new Scanner (System.in);
		String ShowDirec = scanner2.nextLine();
		output.writeUTF(ShowDirec);
		
		reply=input.readUTF();
		System.out.println(reply);
		reply=input.readUTF();
		System.out.println(reply);
		reply=input.readUTF();
		System.out.println(reply);
		

		ShowDirec = scanner2.nextLine();
		output.writeUTF(ShowDirec);
		
		reply=input.readUTF();
		System.out.println(reply);
		reply=input.readUTF();
		System.out.println(reply);
		reply=input.readUTF();
		System.out.println(reply);
		
		
		Scanner scanner3 = new Scanner (System.in);
		String send = scanner3.nextLine();
		output.writeUTF(send);
		
		Socket sending = new Socket(ip, 4350);
		DataInputStream din= new DataInputStream(sending.getInputStream());
		DataOutputStream dout = new DataOutputStream(sending.getOutputStream());
		byte b[] = new byte[1024];
		FileOutputStream fos = new FileOutputStream(new File("C:\\Users\\PAV\\Desktop\\Downloads\\" + send));
		long bytesRead;
		do {
			bytesRead = din.read(b);
			fos.write(b, 0, b.length);
			//fos.flush();
		} while (!(bytesRead < 1024));
		System.out.println("Completed");
		fos.close();
		
		sending.close();
		din.close();
		dout.close();	
		
		String reply1=input.readUTF();
		System.out.println("Server : " + reply1);
		
		}

		}
	}
	catch(IOException e) {
		System.out.println("problem in io of server socket.");
	
	}

	}
}
