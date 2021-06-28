import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.HashSet;
import java.util.concurrent.ArrayBlockingQueue;

public class Server {
    private int port;
    private final HashSet<Connection> connections = new HashSet<>();
    private final ArrayBlockingQueue<Message> messages = new ArrayBlockingQueue<>(100);


    public Server(int port) {
        this.port = port;
    }

    public void start(){
        try(ServerSocket serverSocket = new ServerSocket(port)){
            System.out.println("Server is runin now");
            Thread writerThread = new Thread(new Writer(connections, messages));
            writerThread.setDaemon(true);
            writerThread.start();

            while (true) {
                Socket newClient = serverSocket.accept();
                Connection connection = new Connection(newClient);

                if (!connections.contains(connection)) {
                    new Thread(new Reader(connection, messages, connections)).start();
                }
            }
        }catch (IOException e){
            System.out.println("Server error");
        }

    }
    public static void main(String[] args) {
        new Server(8999).start();
    }
}
