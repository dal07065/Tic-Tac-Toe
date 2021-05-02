package sample.server;

import sample.message.Message;

import java.io.*;
import java.net.Socket;

public class ServerConnection implements Runnable{
    private Socket socket;
    private ObjectInputStream is;
    private ObjectOutputStream os;

    Thread connectionThread;

    public void run()
    {
        try {
            is = new ObjectInputStream(socket.getInputStream());
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    public ServerConnection() throws IOException {
        socket = new Socket("localhost", 8000);

        os = new ObjectOutputStream(socket.getOutputStream());

        connectionThread = new Thread(this);
        connectionThread.start();
    }

    public Message sendMessage(Message msg) throws IOException, ClassNotFoundException {
        os.writeObject(msg);
        return (Message) is.readObject();
    }

    public void disconnect() throws IOException {
        socket.close();
    }

    public void writeInt(int integer) throws IOException {
        os.writeInt(integer);
    }

    public int readInt() throws IOException {
        return is.readInt();
    }

    public void pauseConnection() throws InterruptedException {
        synchronized (connectionThread)
        {
            connectionThread.wait();
        }

    }
    public void resumeConnection()
    {
//        synchronized (connectionThread)
//        {
//
//        }
        connectionThread.notify();
    }
}