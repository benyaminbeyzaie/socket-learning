package server;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.List;

public class Server {
    private final List<ClientHandler> clientHandlers;
    private ServerStatus status;
    private final OrderHandler orderHandler;


    Server() {
        clientHandlers = new ArrayList<>();
        status = ServerStatus.WAITING;
        orderHandler = new OrderHandler(clientHandlers);
    }

    public void init() {
        System.out.println("Server is running...");
        try {
            ServerSocket serverSocket = new ServerSocket(8080);
            while (true) {
                System.out.println("Waiting for a connection...");
                Socket socket = serverSocket.accept();

                addNewClientHandler(socket);
                System.out.println("====> There are " + clientHandlers.size() + " clients on the server!");

            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void addNewClientHandler(Socket socket) throws IOException {
        ClientHandler clientHandler = new ClientHandler(clientHandlers.size(), socket, orderHandler);
        if (status == ServerStatus.WAITING) {
            System.out.println("New connection accepted!");

            clientHandlers.add(clientHandler);
            new Thread(clientHandler).start();

            if (clientHandlers.size() == 5) {
                startGame();
            }
        } else {
            clientHandler.sendMessage("Server is full!");
            clientHandler.kill();
        }

    }

    private void startGame() {
        System.out.println("Game is started!");
        status = ServerStatus.STARTED;
        // Start the game
    }
}

class OrderHandler {
    private final List<ClientHandler> clientHandlers;

    public  OrderHandler(List<ClientHandler> clientHandlers) {
        this.clientHandlers = clientHandlers;
    }

    public void sendMessageFromClientToAnotherClient(int from, int to, String message) {
        clientHandlers.get(to).sendMessage("New message from client " + from + ": " + message);
    }
}

enum ServerStatus {
    WAITING, STARTED
}
