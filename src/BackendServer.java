import java.io.*;
import java.net.*;

public class BackendServer {
    
    public static void main(String[] args) {
        
        if(args.length != 2) {
            System.out.println("Usage: java backendserver <port> <serverName>");
            return;
        }

        int port = Integer.parseInt(args[0]);
        String serverName = args[1];

        try {

            ServerSocket serverSocket = new ServerSocket(port);

            System.out.println(serverName + " started on port " + port);

            while (true) {
                Socket clientSocket = serverSocket.accept();

                BufferedReader input = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));

                PrintWriter output = new PrintWriter(clientSocket.getOutputStream(), true);

                String request = input.readLine();

                if(request == null) {
                    clientSocket.close();
                    continue;
                }

                System.out.println(serverName + " received: " + request);

                output.println("Response from " + serverName);

                clientSocket.close();

            }

        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
