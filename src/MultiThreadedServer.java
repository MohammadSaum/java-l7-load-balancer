import java.io.*;
import java.net.*;

public class MultiThreadedServer {
    public static void main(String[] args) {
        
        try{

            ServerSocket serverSocket = new ServerSocket(5000);

            System.out.println("Server started on port 5000.");

            while (true) {
                Socket clienSocket = serverSocket.accept();

                System.out.println("New client connected.");

                ClientHandler clientHandler = new ClientHandler(clienSocket);
                
                Thread thread = new Thread(clientHandler);

                thread.start();
            }
            
        } catch (IOException e) {
            e.printStackTrace();
        }
    }    
}
