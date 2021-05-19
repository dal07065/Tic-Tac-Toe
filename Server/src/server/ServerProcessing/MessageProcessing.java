package server.ServerProcessing;

import message.Packet;
import server.ServerInternalMessage.ServerPacket;

import java.io.IOException;
import java.util.ArrayList;

public class MessageProcessing implements Runnable{
    @Override
    public void run() {
        try
        {
            while(true)
            {
                System.out.println("Checking for packets...");

                Packet serverMessage = Server.packets.take();

                System.out.println("Packet received: " + serverMessage.getType() + " from " + serverMessage.getFromID());

                if(Server.subscriptions.containsKey(serverMessage.getType()))
                {
                    System.out.println("Subscriptions for this type: " + serverMessage.getType());

                    ArrayList<Connection> subscribers = Server.subscriptions.get(serverMessage.getType());

                    for(int i = 0; i < subscribers.size(); i++)
                    {
                        System.out.println("- " + subscribers.get(i).hashCode());
                    }


                    for (Connection c: subscribers) {

                        c.update(serverMessage);

                    }
                }
                else
                {
                    System.out.println("Subscriptions do not exist for this type.");
                }


            }

        } catch (InterruptedException | IOException e ) {
            e.printStackTrace();
        }
    }
}
