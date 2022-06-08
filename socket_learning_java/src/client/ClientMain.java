package client;

import java.io.IOException;

public class ClientMain {
    public static void main(String[] args) {
        for (int i = 0; i < 2; i++) {
            Client client = new Client(1);
            new Thread(client).start();
        }
    }
}
