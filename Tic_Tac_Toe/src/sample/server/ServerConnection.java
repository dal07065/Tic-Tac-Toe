//package sample.server;
//
//import message.Message;
//import message.Packet;
//import message.SubscribeMessage;
//
//import java.io.*;
//import java.net.Socket;
//import java.util.ArrayList;
//
//public class ServerConnection implements Runnable{
//    private Socket socket;
//    private ObjectInputStream is;
//    private ObjectOutputStream os;
//
//    Thread connectionThread;
//
//    public void run()
//    {
//
//
//    }
//
//    public ServerConnection() throws IOException {
//
//        // initialize the socket connection
//
//        socket = new Socket("localhost", 8000);
//
//        os = new ObjectOutputStream(socket.getOutputStream());
//        is = new ObjectInputStream(socket.getInputStream());
//
//        // run ServerConnection thread
//
////        connectionThread = new Thread(this);
////        connectionThread.start();
//    }
//
//    public void sendPacket(Packet packet) throws IOException, ClassNotFoundException {
//        os.writeObject(packet);
//    }
//
//    public Packet getPacket() throws IOException, ClassNotFoundException {
//        Packet packet = (Packet) is.readObject();
//
//        return packet;
//    }
//
//    public void disconnect() throws IOException {
//        socket.close();
//    }
//
//}
