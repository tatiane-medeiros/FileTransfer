package filetransfer;
import java.net.Socket;
import java.awt.Component;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.util.Scanner;

import javax.swing.JOptionPane;

//atualmente só realiza upload para localhost
public class Cliente {
	private static FileInputStream in;
	private static BufferedWriter writer;
	private static Scanner sc;
	private static String IP;
	private static int PORT;

	public static void main(String[] args) throws Exception {
		sc = new Scanner(System.in);
		System.out.println("Selecione a opção:");
		System.out.println("\t Download: --down");
		System.out.println("\t Upload: --up");
		String op = sc.nextLine();
		System.out.println(op);
		op.intern();
		//upload
		if(op == "--up"){
			//Escolher arquivo.
			System.out.println("Digite o nome do arquivo a ser enviado:");
			String name = sc.nextLine();
			//arquivo na pasta do projeto
			File f = new File(name);
			if(f.exists()){
				in = new FileInputStream(f);
				System.out.println("Digite o endereço ip do servidor:");
				IP = sc.nextLine();
				System.out.println("Digite o número da porta:");
				PORT = sc.nextInt();
				Socket clientsocket = new Socket(IP, PORT);		// ip do servidor e porta
				OutputStream out = clientsocket.getOutputStream();
				OutputStreamWriter w = new OutputStreamWriter(out);
				BufferedWriter writer = new BufferedWriter(w);
				writer.write(op + "\n");
				writer.flush();
				writer.write(f.getName() + "\n");
				writer.flush();
				int size = 4096;		//4KB buffer  
			    byte[] buffer = new byte[size];  
			    int lidos = -1;  
			    while ((lidos = in.read(buffer, 0, size)) != -1) {  
			        out.write(buffer, 0, lidos);  
			    }  
		   
			    clientsocket.close();
				System.out.println("Arquivo "+name+" enviado para ["+IP+"]");
			}
			else{
				System.out.println("Arquivo não encontrado!");
			}
		}
		
		else if(op == "--down"){
			System.out.println("Digite o endereço ip do servidor:");
			IP = sc.nextLine();
			System.out.println("Digite o número da porta:");
			PORT = sc.nextInt();
			
			Socket clientsocket = new Socket(IP, PORT);	//interface: ip do servidor e porta
			OutputStream out = clientsocket.getOutputStream();
			OutputStreamWriter w = new OutputStreamWriter(out);
			writer = new BufferedWriter(w);
			writer.write(op + "\n");
			writer.flush();
			sc.nextLine();
			System.out.println("Digite o nome do arquivo a ser baixado:");
			String name = sc.nextLine();
			writer.write(name + "\n");
			writer.flush();
			//receber arquivo
			File f = new File(name);
			InputStream inp = clientsocket.getInputStream();
			InputStreamReader ir = new InputStreamReader(inp);
			BufferedReader reader = new BufferedReader(ir);
			//String nameReceived = reader.readLine();
			
			out = new FileOutputStream(f);
		    byte[] buffer = new byte[4096];  
		    int lidos = -1;  
		    while ((lidos = inp.read(buffer, 0, 4096)) != -1) {  
		    	System.out.println(lidos);
		        out.write(buffer, 0, lidos);  
		    }  
		    out.flush(); 
		    clientsocket.close();
		    System.out.println("Download de "+name+" efetuado");
		}
		
		   
	}
}
