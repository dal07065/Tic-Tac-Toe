package sample.server;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.net.Socket;

class ClientConnection implements Runnable {
    Socket client1;
    Socket client2;
    DataInputStream inputStream1;
    DataOutputStream outputStream1;
    DataInputStream inputStream2;
    DataOutputStream outputStream2;
    int id;

    public ClientConnection(int id, Socket c1, Socket c2) throws Exception {
        this.id = id;
        this.client1 = c1;
        this.client2 = c2;

        inputStream1 = new DataInputStream(c1.getInputStream());
        outputStream1 = new DataOutputStream(c1.getOutputStream());
        inputStream2 = new DataInputStream(c2.getInputStream());
        outputStream2 = new DataOutputStream(c2.getOutputStream());

        outputStream1.writeInt(1);
        outputStream2.writeInt(2);

        Thread clientThread = new Thread(this);
        clientThread.start();
    }

    public void run() {
        try {

            // run the game

            // client 1 makes a move -> Based on number pad keys i.e. 7 is top left

            while (true) {

                int move1 = inputStream1.readInt();

                if(move1 == 0)
                    break;

                outputStream2.writeInt(move1);

                int move2 = inputStream2.readInt();

                if(move2 == 0)
                    break;

                outputStream1.writeInt(move2);


            }

            System.out.println("Client process terminated " + id);
        }
        catch (Exception ex) {
            ex.printStackTrace();
        }
    }
}