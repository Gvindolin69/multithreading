import java.io.IOException;
import java.util.HashSet;
import java.util.Objects;
import java.util.concurrent.ArrayBlockingQueue;

public class Writer implements Runnable{

    private HashSet<Connection> connections;
    private ArrayBlockingQueue<Message> messages;

    public Writer(HashSet<Connection> connections, ArrayBlockingQueue<Message> messages) {
        this.connections = Objects.requireNonNull(connections);
        this.messages = Objects.requireNonNull(messages);
    }

    @Override
    public void run() {
        while (true) {
            try {
                Message message = messages.take();

                for (Connection connection1 : connections) {
                    if(!connection1.getUserName().equals(message.getSender())) connection1.sendMessage(message);
                    System.out.println(Thread.currentThread().getName() + "Отправил сообщение");
                }

            } catch (InterruptedException | IOException e) {
                System.out.println("Writer error");

            }
        }
    }
}
