import java.io.IOException;
import java.net.Socket;
import java.util.Scanner;


public class Client {
    private int port;
    private String ip;
    private Scanner scanner;
    private Connection connection;

    public Client(int port, String ip) {
        this.ip = ip;
        this.port = port;
        this.scanner = new Scanner(System.in);
        try {
            connection = new Connection(new Socket(ip, port));
        } catch (IOException e) {
            System.out.println("Connection error");
        }
    }

    public void start(){
        System.out.println("Enter u name");
        String userName = scanner.nextLine();
        String text;

        Thread reader = new Thread(){
            @Override
            public void run(){
                while (true){
                    readMessage();
                }
            }
        };
        reader.setDaemon(true);
        reader.start();

        while (true){
            System.out.println("Write u message");
            text = scanner.nextLine();
            if (text.equals("/exit")) break;
            sendMessage(Message.getMessage(userName, text));
        }
    }

    private void sendMessage(Message message){
        try{
            connection.sendMessage(message);

        }catch (IOException e){
            System.out.println("Writing - reading error(sending)");
        } catch (Exception e){
            System.out.println("Connection error");
        }
    }

    private void readMessage(){
        try {
            Message fromServer = connection.readMessage();
            System.out.println("from server: " + fromServer);
        }catch (IOException e){
            System.out.println("Writing - reading error()reading");
        }catch (ClassNotFoundException e){
            System.out.println("Reading error");
        }catch (Exception e){
            System.out.println("Connection error");
        }
    }

    public Connection getConnection() {
        return connection;
    }

    public static void main(String[] args) {
        new Client( 8999, "127.0.0.1").start();
    }
}
