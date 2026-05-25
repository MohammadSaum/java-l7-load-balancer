import java.io.*;
import java.net.*;

public class ClientHandler implements Runnable {

    private Socket socket;

    public ClientHandler(Socket socket) {
        this.socket = socket;
    }

    @Override
    public void run() {

        try {

            BufferedReader input = new BufferedReader(
                    new InputStreamReader(socket.getInputStream())
            );

            PrintWriter output = new PrintWriter(
                    socket.getOutputStream(), true
            );

            String message = input.readLine();

            System.out.println("Client says: " + message);

            output.println("Hello from multithreaded server!");

            socket.close();

        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}