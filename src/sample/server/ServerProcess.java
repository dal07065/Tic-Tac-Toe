package sample.server;

import java.net.InetAddress;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;

public class ServerProcess implements Runnable {
    private ServerSocket server;
    private boolean keepRunning = true;
    private ArrayList<ClientConnection> connections = null;
    private static int CONNECTION_COUNT = 0;

    @Override
    public void run() {

        connections = new ArrayList<>();

        try
        {
            server = new ServerSocket(8000);
            System.out.println("Server started and listening for new connections...");

            while (keepRunning) {
                Socket clientSocketConnection1 = server.accept();
                ++CONNECTION_COUNT;

                Socket clientSocketConnection2 = server.accept();
                ++CONNECTION_COUNT;

                ClientConnection newConnection = new ClientConnection(CONNECTION_COUNT, clientSocketConnection1, clientSocketConnection2);
                connections.add(newConnection);

                InetAddress inetAddress = clientSocketConnection1.getInetAddress();
                System.out.println("Accepted Connection from " + inetAddress.getHostAddress());
            }

            System.out.println("Shutting SERVER down");
        }
        catch (Exception ex) {
            ex.printStackTrace();
        }
        finally {
            try {
                server.close();
            }
            catch (Exception ex){}
        }

    }
}