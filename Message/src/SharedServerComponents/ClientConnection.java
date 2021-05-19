package SharedServerComponents;

import message.Packet;
import message.SubscribeMessage;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.net.SocketException;
import java.util.ArrayList;
import java.util.Queue;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.BlockingQueue;

/**
 * Communicates from Client to server.ServerProcessing.Server
 */

public class ClientConnection implements Runnable{

    private Socket socket;

    private ObjectOutputStream outputStream;
    private ObjectInputStream inputStream;

    private ArrayList<String> subscribedChannels;

    private BlockingQueue<Packet> packetsReceived = new ArrayBlockingQueue<>(30);

    private String fromID;

    public ClientConnection(String firstChannel, String ... channels) throws IOException {
        this.socket = new Socket("localhost", 8000);
        outputStream = new ObjectOutputStream(socket.getOutputStream());
        inputStream = new ObjectInputStream(socket.getInputStream());

        registerChannels(firstChannel, channels);

        fromID = IDGenerator.getNewID(this);

        Thread clientThread = new Thread(this);
        clientThread.start();
    }

    public ClientConnection(Socket socket, String firstChannel, String ... channels) throws IOException {
        this.socket = socket;
        outputStream = new ObjectOutputStream(socket.getOutputStream());
        inputStream = new ObjectInputStream(socket.getInputStream());

        registerChannels(firstChannel, channels);
    }

    public void sendPacket(Packet packet)
    {
        try {
            packet.setFromID(fromID);
            outputStream.writeObject(packet);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

//    public Packet getPacket() throws IOException, ClassNotFoundException {
//        return (Packet) inputStream.readObject();
//    }

    public Packet getPacket() throws InterruptedException {
        return packetsReceived.take();
    }

    public Packet getPacket(String type)
    {
        Packet packet;

        type = type + fromID;

        do
        {
            packet = getPacketInternal(type);
        }while(packet == null);

        return packet;

    }

    public Packet getPacketInternal(String type)
    {
        for(Packet packet:packetsReceived)
        {
            if(packet.getType().equals(type))
            {
                return packet;
            }
        }
        return null;
    }

    public void registerChannels(String firstChannel, String ... channels)
    {
        subscribedChannels = new ArrayList<>();

        subscribedChannels.add(firstChannel);

        for(String channel:channels)
        {
            subscribedChannels.add(channel);
        }

        sendPacket(new Packet("registerMessage", false, new SubscribeMessage(subscribedChannels)));
    }

    public void disconnect() throws IOException {
        sendPacket(new Packet("CloseSocket", false, null));
        socket.close();
    }

    @Override
    public void run() {
        try {
            while(true)
            {
                packetsReceived.put((Packet) inputStream.readObject());
            }
        } catch (SocketException e) {
            System.out.println("Client disconnected.");
        } catch (IOException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}
