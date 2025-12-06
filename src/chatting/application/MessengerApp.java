package chatting.application;

public class MessengerApp {
    public static void main(String[] args) {
        //Server Side
        new Thread(() -> {
            Server.main(null);
        }).start();
        try { 
            Thread.sleep(1000); 
        } catch(Exception e) {}
        //Client Side
        new Thread(() -> {
            Client.main(null);
        }).start();
    }
}
