import java.io.*;
import java.net.*;
import java.util.*;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Executor;

public class LoadBalancer {

    private static final ExecutorService executor = Executors.newFixedThreadPool(20);
    
    private static final List<Integer> backendPorts = Arrays.asList(9001, 9002, 9003);

    private static int currentIndex = 0;

    public static void main (String[] args) {

        try {

            ServerSocket loadBalancerSocket = new ServerSocket(8000);

            System.out.println("Load Balancer  running on port 8000..");

            while (true) {
                Socket clientSocket = loadBalancerSocket.accept();

                int backendPort = getNextBackendPort();

                System.out.println("Forwarding request to backend server on port " + backendPort);

                executor.submit(new LoadBalancerClientHandler(clientSocket, backendPort));

            }

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private static synchronized int getNextBackendPort() {
        int port = backendPorts.get(currentIndex);

        currentIndex = (currentIndex + 1) % backendPorts.size();

        return port; 
    }

    private static void handleClientRequest(Socket clientSocket, int backendPort) {

        try {

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
