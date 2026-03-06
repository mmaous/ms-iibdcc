import java.net.ServerSocket;
import java.net.Socket;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class MultiThreadServer {
    public static void main(String[] args) {
        ExecutorService pool = Executors.newFixedThreadPool(5);

        try (ServerSocket serverSocket = new ServerSocket(5001)) {
            System.out.println("Server started, see port5001!!!");
            while (true) {
                Socket clientSocket = serverSocket.accept();
                String clientIP = clientSocket.getInetAddress().getHostAddress();

                System.out.println("Client connecté: " + clientIP);
                pool.execute(new ClientHandler(clientSocket));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
