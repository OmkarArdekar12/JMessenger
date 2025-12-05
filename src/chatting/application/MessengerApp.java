package chatting.application;

public class MessengerApp {
    public static void main(String[] args) {
        //Server
        new Thread(() -> {
            Server.main(null);
        }).start();

        try { 
            Thread.sleep(1000); 
        } catch(Exception e) {}

        //Client
        new Thread(() -> {
            Client.main(null);
        }).start();
    }
}
