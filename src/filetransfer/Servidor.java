package filetransfer;
import java.net.Socket;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.net.ServerSocket;


// funcionou png e txt, executar servidor depois cliente
public class Servidor {
	private static FileOutputStream out;

	public static void main(String[] args) {
		try {
			ServerSocket serversocket = new ServerSocket(5000);
			String op;
			while(true){
				Socket clSocket = serversocket.accept();
				InputStream in = clSocket.getInputStream();
				InputStreamReader ir = new InputStreamReader(in);
				BufferedReader reader = new BufferedReader(ir);
				op = reader.readLine();
				System.out.println(op);
				//op.intern();
				if(op.intern() == "--up"){
				
					String name = reader.readLine();
					System.out.println(name);
					File f1 = new File("C:/Users/Tatiane/Desktop/" + name);
					out = new FileOutputStream(f1);
					    byte[] buffer = new byte[4096];  
					    int lidos = -1;  
					    while ((lidos = in.read(buffer, 0, 4096)) != -1) {  
					    	System.out.println(lidos);
					        out.write(buffer, 0, lidos);  
					    }  
					    out.flush();  
					    //serversocket.close();
				}
				else if(op.intern() == "--down"){
					System.out.println("Download solicitado");
					OutputStream out = clSocket.getOutputStream();
					OutputStreamWriter w = new OutputStreamWriter(out);
					BufferedWriter writer = new BufferedWriter(w);
					//in = clSocket.getInputStream();
					String name = reader.readLine();
					System.out.println(name);
					// arquivo a ser enviado
					File f1 = new File("C:/Users/Tatiane/Desktop/" + name);
					FileInputStream inp = new FileInputStream(f1);	//arquivo sera enviado do servidor
					    byte[] buffer = new byte[4096];  
					    int lidos = -1;    
					    while ((lidos = inp.read(buffer, 0, 4096)) != -1) {  
					        out.write(buffer, 0, lidos);  
					    }  
					writer.flush();
				}
				
				op = "";
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}
