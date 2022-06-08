package client;

import java.io.IOException;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.Scanner;

public class Client implements Runnable {
    private final int kind;
    public Client(int kind) {
        this.kind = kind;
    }
    private void init(int kind) throws IOException {
        Socket socket = new Socket("localhost", 8080);
        PrintWriter printWriter = new PrintWriter(socket.getOutputStream());
        Scanner scanner = new Scanner(socket.getInputStream());
        if (kind == -1) {
            printWriter.println("SEND_MESSAGE_TO_A_CLIENT-90-Hello Client! My name is benyamin!");
            printWriter.flush();
        } else {
            printWriter.println("This is a message from client");
            printWriter.flush();
        }


        while (true) {
            String input = scanner.nextLine();
            System.out.println("Message from server: " + input);
        }
    }

    @Override
    public void run() {
        try {
            init(kind);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
