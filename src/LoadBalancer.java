import java.io.*;
import java.net.*;
import java.util.*;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class LoadBalancer {

    private static final ExecutorService executor = Executors.newFixedThreadPool(20);
    
    private static final List<Integer> allServers = Arrays.asList(9001, 9002, 9003);

    private static final List<Integer> activeservers = new ArrayList<>(allServers);

    private static int currentIndex = 0;

    public static void main (String[] args) {

        try {

            ServerSocket loadBalancerSocket = new ServerSocket(8000);

            System.out.println("Load Balancer  running on port 8000..");

            Thread healthCheckerThread = new Thread(
                new HealthChecker(allServers, activeservers)
            );

            healthCheckerThread.start();

            while (true) {
                Socket clientSocket = loadBalancerSocket.accept();
                
                if(activeservers.isEmpty()) {
                    throw new RuntimeException("No active backend servers available");
                }
                int backendPort = getNextBackendPort();

                System.out.println("Forwarding request to backend server on port " + backendPort);

                executor.submit(new LoadBalancerClientHandler(clientSocket, backendPort));

            }

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private static synchronized int getNextBackendPort() {
        int port = activeservers.get(currentIndex);

        currentIndex = (currentIndex + 1) % activeservers.size();

        return port; 
    }
}
