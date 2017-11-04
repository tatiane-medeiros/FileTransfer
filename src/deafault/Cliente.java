package deafault;
import java.net.Socket;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.util.Scanner;

//atualmente só realiza upload para localhost
public class Cliente {
	private static FileInputStream in;
	private static Scanner sc;

	public static void main(String[] args) throws Exception {
		sc = new Scanner(System.in);
		System.out.println("Digite o nome do arquivo a ser enviado:");
		String name = sc.nextLine();
		//arquivo na pasta padrão
		File f = new File("C:/Users/Tatiane/Documents/" + name);
		in = new FileInputStream(f);
		Socket clientsocket = new Socket("localhost", 5000);	//ip do servidor
		OutputStream out = clientsocket.getOutputStream();
		OutputStreamWriter w = new OutputStreamWriter(out);
		BufferedWriter writer = new BufferedWriter(w);
		writer.write(f.getName() + "\n");
		writer.flush();
		int size = 4096;		//4KB buffer  
	    byte[] buffer = new byte[size];  
	    int lidos = -1;  
	    while ((lidos = in.read(buffer, 0, size)) != -1) {  
	        out.write(buffer, 0, lidos);  
	    }  
	    clientsocket.close();
	}
}
