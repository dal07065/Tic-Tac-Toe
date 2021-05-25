package server.ServerProcessing;

import message.Packet;
import server.Database;
import server.GameProcessing.GameController;
import server.ServerInternalMessage.ServerPacket;

import java.io.IOException;
import java.net.ServerSocket;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.BlockingQueue;


/**
 * Messaging service
 * - gets input from connection
 * - delivers that input to subscribed connections
 */
public final class Server {

    public static Map<String, ArrayList<Connection>> subscriptions = new HashMap<>();

    public static BlockingQueue<Packet> packets = new ArrayBlockingQueue<>(30);

    public static ServerSocket server;

    public static GameController gameController;


    public Server() throws IOException, ClassNotFoundException {

        server = new ServerSocket(8000);

        Thread messageProcessingThread = new Thread(new MessageProcessing());
        messageProcessingThread.start();

        Thread acceptConnectionThread = new Thread(new AcceptConnection());
        acceptConnectionThread.start();

        Thread serverProcessingThread = new Thread(new ServerProcessing());
        serverProcessingThread.start();

        GameController gameController = new GameController();

    }

    private static void addChannel(String channel)
    {
        subscriptions.put(channel, new ArrayList<Connection>());
    }

    public static void addConnectionToChannel(String channel, Connection thisConnection)
    {
        ArrayList<Connection> list = subscriptions.get(channel);
        if(list == null)
            addChannel(channel);
        subscriptions.get(channel).add(thisConnection);
    }

    public static void removeConnectionToChannel(String channel, Connection connection) {
        ArrayList<Connection> list = subscriptions.get(channel);
        list.remove(connection);
    }
}
