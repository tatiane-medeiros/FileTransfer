package deafault;
import java.net.Socket;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.ServerSocket;


// funcionou png e txt, executar servidor depois cliente
public class Servidor {
	private static FileOutputStream out;

	public static void main(String[] args) {
		try {
			ServerSocket serversocket = new ServerSocket(5000);
			Socket clSocket = serversocket.accept();
			InputStream in = clSocket.getInputStream();
			InputStreamReader ir = new InputStreamReader(in);
			BufferedReader reader = new BufferedReader(ir);
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
			//server.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}
