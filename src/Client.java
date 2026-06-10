import java.io.*;
import java.net.*;

public class Client {
    public static void main(String[] args) {
        
        try {
            Socket socket = new Socket("localhost", 8000);

            BufferedReader input = new BufferedReader( new InputStreamReader(socket.getInputStream()));

            PrintWriter output = new PrintWriter(socket.getOutputStream(), true);

            output.println("GET / HTTP/1.1");
            output.println("Host: localhost");
            output.println();

            String line;

            while((line = input.readLine()) != null){
                System.out.println(line);
            }
            
            socket.close();

        } catch(IOException e){
            e.printStackTrace();
        }
    }
}
