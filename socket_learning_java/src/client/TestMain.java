package client;

public class TestMain {
    public static void main(String[] args) {
        Client client = new Client(-1);
        new Thread(client).start();
    }
}
