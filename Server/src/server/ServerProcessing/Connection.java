package server.ServerProcessing;

import message.Message;
import message.Packet;
import message.SubscribeMessage;
import SharedServerComponents.IDGenerator;

import java.io.EOFException;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.net.SocketException;
import java.util.ArrayList;

/**
 * Communicates from server.ServerProcessing.Server to Client
 */

public class Connection implements Runnable{
    private Socket socket;
    private ArrayList<String> subscribedChannels = new ArrayList<>();

    private ObjectOutputStream outputStream;
    private ObjectInputStream inputStream;

    private String fromID;
    public String getFromID(){return fromID;}


    public Connection(Socket socket) throws IOException, ClassNotFoundException {
        this.socket = socket;
        outputStream = new ObjectOutputStream(socket.getOutputStream());
        inputStream = new ObjectInputStream(socket.getInputStream());



        // Get a list of channels to subscribe to

        initializeChannels();

        Thread connectionThread = new Thread(this);
        connectionThread.start();


    }

    @Override
    public void run() {
        try {
            while (true) {
                Packet packet = (Packet) inputStream.readObject();

                Server.packets.put(packet);
            }

        } catch (SocketException e) {
            System.out.println("Connection lost: " + socket.getInetAddress().getHostAddress());
            destroyThisConnection();
        } catch (EOFException e) {
            System.out.println("Connection is probably lost: " + socket.getInetAddress().getHostAddress());
            destroyThisConnection();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void addChannel(String channel)
    {
        subscribedChannels.add(channel);
        // Add the connection to the subscription list
        Server.addConnectionToChannel(channel, this);
    }

    public void removeChannel(String channel)
    {
        Server.removeConnectionToChannel(channel, this);
    }

    private void initializeChannels() throws IOException, ClassNotFoundException {

        Packet packet = (Packet) inputStream.readObject();

        SubscribeMessage msg = (SubscribeMessage) packet.getMessage();

        System.out.println("New Connection Accepted: " + socket.getInetAddress().getHostAddress() + " : " + msg.getChannels().get(0));

        for(String channel:msg.getChannels())
        {
            addChannel(channel);
        }

        fromID = subscribedChannels.get(0);

    }

    private void destroyThisConnection()
    {
        for(String channel: subscribedChannels)
            removeChannel(channel);

        try {
            socket.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void update(Packet packet) throws IOException {
        outputStream.writeObject(packet);

    }


}
