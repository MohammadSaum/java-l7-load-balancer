import java.io.IOException;
import java.net.Socket;
import java.util.List;

public class HealthChecker implements Runnable{
    
    private final List<Integer> allServers;

    private final List<Integer> activeservers;

    public HealthChecker(
        List<Integer> allServers, 
        List<Integer> activeservers
    ) {
        this.allServers = allServers;
        this.activeservers = activeservers;
    }

    @Override
    public void run() {
        while (true) {
            for (Integer port : allServers) {
                boolean healthy = isServerHealthy(port);

                synchronized (activeservers) {
                    if (healthy && !activeservers.contains(port)) {
                        activeservers.add(port);
                        System.out.println("Server restored: " + port);
                    }

                    if (!healthy && activeservers.contains(port)) {
                        activeservers.remove(port);
                        System.out.println("Server removed: " + port);
                    }
                }
            }

            try {
                Thread.sleep(5000);
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
            }
        }
    }

    private boolean isServerHealthy(int port) {

    try (Socket socket = new Socket("localhost", port)) {
        return true;
    } catch (IOException e) {
        return false;
    }
}}