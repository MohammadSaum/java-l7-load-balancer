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

                String line = input.readLine();

                while((line = input.readLine()) != null) {
                    if(line.isEmpty()) {
                        break;
                    }
                    System.out.println(serverName + " -> " + line);
                }

                String responseBody = "Response from " + serverName;

                output.println("HTTP/1.1 200 OK");
                output.println("Content-Type: text/plain");
                output.println("Content-length: " + responseBody.length());

                output.println();
                output.println(responseBody);

                clientSocket.close();

            }

        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
