import java.io.*;
import java.net.*;

public class LoadBalancerClientHandler implements Runnable {
    
    private final Socket clientSocket;
    private final int backendPort;
    
    public LoadBalancerClientHandler(Socket clientSocket, int backendPort){
        this.clientSocket = clientSocket;
        this.backendPort = backendPort;
    }

    @Override
    public void run() {
        try{
            BufferedReader clientInput = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));

            PrintWriter clientOutput = new PrintWriter(clientSocket.getOutputStream(), true);

            String clientMessage = clientInput.readLine();

            Socket backendSocket = new Socket("localhost", backendPort);

            BufferedReader backendInput = new BufferedReader(new InputStreamReader(backendSocket.getInputStream()));

            PrintWriter backendOutput = new PrintWriter(backendSocket.getOutputStream(), true);

            backendOutput.println(clientMessage);

            String backendResponse = backendInput.readLine();

            clientOutput.println(backendResponse);

            backendSocket.close();
            clientSocket.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
