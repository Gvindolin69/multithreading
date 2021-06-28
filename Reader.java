import java.io.IOException;
import java.util.HashSet;
import java.util.Objects;
import java.util.concurrent.ArrayBlockingQueue;

public class Reader implements Runnable{
    private final Connection connection;
    private final ArrayBlockingQueue<Message> messages;
    private final HashSet<Connection> connections;

    public Reader(Connection connection, ArrayBlockingQueue<Message> messages, HashSet<Connection> connections) {
        this.connection =  Objects.requireNonNull(connection);
        this.messages = Objects.requireNonNull(messages);
        this.connections = connections;
    }

    @Override
    public void run() {
        while (true){
            try {
                Message message = connection.readMessage();
                connection.setUserName(message.getSender());
                connections.add(connection);
                messages.put(message);

            } catch (IOException e) {
                System.out.println("Server error(reader)");
            } catch (ClassNotFoundException e) {
                System.out.println("Writing error");
            } catch (InterruptedException e) {
                System.out.println("Thread error");
            }
        }
    }
}
