package filetransfer;
import java.net.ConnectException;
import java.net.Socket;
import java.net.SocketException;
import java.net.UnknownHostException;
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
		
//upload
		if(op.intern() == "--up"){
			//Escolher arquivo.
			System.out.println("Digite o nome do arquivo a ser enviado:");
			String name = sc.nextLine();
			//Arquivo na pasta do projeto
			File f = new File(name);
			if(f.exists()){
				in = new FileInputStream(f);
				System.out.println("Digite o endereço ip do servidor:");
				IP = sc.nextLine();
				System.out.println("Digite o número da porta:");
				PORT = sc.nextInt();
				try{
					Socket clientsocket = new Socket(IP, PORT);		// ip do servidor e porta
					OutputStream out = clientsocket.getOutputStream();
					OutputStreamWriter w = new OutputStreamWriter(out);
					BufferedWriter writer = new BufferedWriter(w);
					//Envia mensagem ao servidor avisando que fará um upload
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
				    
				}catch(ConnectException e){
					System.err.println("Falha na conexão com servidor.");
				}catch(UnknownHostException e){
					System.err.println("Endereço incorreto.");
				}
			}
			else{
				System.out.println("Arquivo não encontrado!");
			}
			System.exit(0);
		}
		
//download
		else if(op.intern() == "--down"){
			try{
			System.out.println("Digite o endereço ip do servidor:");
			IP = sc.nextLine();
			System.out.println("Digite o número da porta:");
			PORT = sc.nextInt();
			
			Socket clientsocket = new Socket(IP, PORT);		// ip do servidor e porta
			OutputStream out = clientsocket.getOutputStream();
			OutputStreamWriter w = new OutputStreamWriter(out);
			writer = new BufferedWriter(w);
			//Envia mensagem ao servidor avisando que fará um download
			writer.write(op + "\n");
			writer.flush();
			sc.nextLine();
			//Arquivo na pasta do servidor
			System.out.println("Digite o nome do arquivo a ser baixado:");
			String name = sc.nextLine();
			writer.write(name + "\n");
			writer.flush();
			//Recebe arquivo na pasta do projeto
			File f = new File(name);
			InputStream inp = clientsocket.getInputStream();			
			out = new FileOutputStream(f);
		    byte[] buffer = new byte[4096];  
		    int lidos = -1;  
		    while ((lidos = inp.read(buffer, 0, 4096)) != -1) {  
		    	//System.out.println(lidos);
		        out.write(buffer, 0, lidos);  
		    }  
		    System.out.println("Download de "+name+" efetuado");
		    out.flush(); 
		    clientsocket.close();
		    System.out.println("Download de "+name+" efetuado");
		    System.exit(0);
			}catch(SocketException e){
				System.err.println("Arquivo não existe!");
			}catch(UnknownHostException e){
				System.err.println("Endereço incorreto.");
			}
		}
		
		   
	}
}
