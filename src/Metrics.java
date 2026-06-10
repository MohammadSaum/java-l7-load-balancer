import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public class Metrics {
    private static final Map<Integer, Integer> requestCounts = new ConcurrentHashMap<>(); 

    public static void incrementRequestCount(int port) {
        requestCounts.merge(port, 1, Integer::sum);
    }

    public static void printMetrics() {
        System.out.println("\n ---- METRICS ----");

        for(Map.Entry<Integer, Integer> entry : requestCounts.entrySet()) {
            System.out.println(
                "port " 
                + entry.getKey()
                + " -> "
                + entry.getValue()
                + " requests"
            );
        }

        System.out.println("-----------------\n");
    }
}
