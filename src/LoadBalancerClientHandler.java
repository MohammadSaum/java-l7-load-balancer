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

            Socket backendSocket = new Socket("localhost", backendPort);

            Metrics.incrementRequestCount(backendPort);

            BufferedReader backendInput = new BufferedReader(new InputStreamReader(backendSocket.getInputStream()));

            PrintWriter backendOutput = new PrintWriter(backendSocket.getOutputStream(), true);

            String line;

            while((line = clientInput.readLine()) != null) {
                backendOutput.println(line);
                if(line.isEmpty()) {
                    break; 
                }
            }

            String responseLine;

            while ((responseLine = backendInput.readLine()) != null) {
                clientOutput.println(responseLine);

                if(responseLine.isEmpty()) {
                    break;
                }
            }

            String body = backendInput.readLine();

            if(body != null){
                clientOutput.println(body);
            }

            backendSocket.close();
            clientSocket.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
