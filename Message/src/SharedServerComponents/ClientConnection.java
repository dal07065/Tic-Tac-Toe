package SharedServerComponents;

import message.Message;
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

import static java.lang.Thread.sleep;

/**
 * Communicates from Client to server.ServerProcessing.Server
 */

public class ClientConnection implements Runnable{

    private Socket socket;

    private ObjectOutputStream outputStream;
    private ObjectInputStream inputStream;

    private ArrayList<String> subscribedChannels;

    private BlockingQueue<Packet> packetsReceived = new ArrayBlockingQueue<>(30);

    private BlockingQueue<Packet> responsePackets = new ArrayBlockingQueue<>(30);

    private String fromID;

    public ClientConnection(String ... channels) throws IOException {
        this.socket = new Socket("localhost", 8000);
        outputStream = new ObjectOutputStream(socket.getOutputStream());
        inputStream = new ObjectInputStream(socket.getInputStream());

        fromID = IDGenerator.getNewID(this);
//        fromID = clientName;
        registerChannels(fromID, channels);

//        Thread clientThread = new Thread(this);
//        clientThread.start();
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

    public Packet getPacket() throws IOException, ClassNotFoundException {
        return (Packet) inputStream.readObject();
    }

//    public Packet getPacket(Class<?> aClass) throws InterruptedException {
//        return packetsReceived.take();
//    }

    public Packet getPacket(String type){
        try{
            boolean packetIsNotReceived = true;

            Packet packet = getPacketInternal(Class.forName("message." + type + "Message"));

            if(packet != null)
            {
                return packet;
            }

            while(packetIsNotReceived) {
                packet = (Packet) inputStream.readObject();

                if (packet.getMessage().getClass() == Class.forName("message." + type + "Message"))
                    return packet;
                else
                    packetsReceived.put(packet);
            }

        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }

        return null;
//        Class msgType = null;
//        try {
//            msgType = Class.forName("message." + type + "Message");
//        } catch (ClassNotFoundException e) {
//            e.printStackTrace();
//        }
//        Packet packet;
//
//        do
//        {
//            packet = getPacketInternal(msgType);
//        }while(packet == null);

//        return packet;
//
//        try {
//            return responsePackets.take();
//        } catch (InterruptedException e) {
//            e.printStackTrace();
//        }
//        return null;
    }

    public Packet getPacketInternal(Class<?> aClass) throws InterruptedException {

        for(Packet packet:packetsReceived)
        {
            if(packet.getMessage().getClass() == aClass)
            {
                packetsReceived.remove(packet);
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

        sendPacket(new Packet("registerMessage", new SubscribeMessage(subscribedChannels)));
    }

    public void disconnect() throws IOException {
        sendPacket(new Packet("CloseSocket",  null));
        socket.close();
    }

    @Override
    public void run() {
//        try {
//            while(true)
//            {
//                Packet packet = (Packet) inputStream.readObject();
//
//                responsePackets.put(packet);
//
//                // perform action with packetsreceived
//
//                // send the server a confirmed/verified message (server will be waiting for a response)
//
//                // on the other client side, when it sends a moveMessage and it does NOT receive a confirmed response back,
//                // then we know that a packet was lost.
//            }
//        } catch (SocketException e) {
//            System.out.println("Client disconnected from the server.");
//        } catch (IOException e) {
//            e.printStackTrace();
//        } catch (ClassNotFoundException e) {
//            e.printStackTrace();
//        } catch (InterruptedException e) {
//            e.printStackTrace();
//        }
    }

    public String getFromID() {
        return fromID;
    }
}
