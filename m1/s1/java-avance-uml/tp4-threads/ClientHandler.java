import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.Date;

public class ClientHandler implements Runnable {
    private Socket socket;
    public ClientHandler(Socket socket) {
        this.socket = socket;
    }

    @Override
    public void run() {
        String clientIP = socket.getInetAddress().getHostAddress();
        String threadName = Thread.currentThread().getName();

        System.out.println("Thread " + threadName + " trait le client");

        try (
            BufferedReader in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            PrintWriter out = new PrintWriter(socket.getOutputStream(), true)
        ) {
            String message;
            while ((message = in.readLine()) != null) {
                System.out.println("Message reçu: " + message);

                if (message.equals("hello")) {
                    out.println("Bonjour client !");
                } else if (message.equals("time")) {
                    out.println(new Date().toString());
                } else if (message.equals("bye")) {
                    out.println("Connexion fermé");
                    break;
                } else {
                    out.println("Message recu: " + message);
                }
            }
        } catch (Exception e) {
            System.out.println("Connection error with " + clientIP);
        } finally {
            try {
                socket.close();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }
}
