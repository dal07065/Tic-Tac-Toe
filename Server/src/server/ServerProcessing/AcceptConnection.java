package server.ServerProcessing;

import java.net.Socket;

public class AcceptConnection implements Runnable{

    private boolean loop = true;

    @Override
    public void run() {
        try
        {
            System.out.println("Server started and listening for new connections...");
            while (loop) {

                Socket socketConnection = Server.server.accept();    // when a user simply just ran the client program (before logging in or anything)

                Connection connection = new Connection(socketConnection);
            }

            System.out.println("Shutting SERVER down");
        }
        catch (Exception ex) {
            ex.printStackTrace();
        }
    }
}
