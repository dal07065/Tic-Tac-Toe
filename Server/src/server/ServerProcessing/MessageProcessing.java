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

                Packet packet = Server.packets.take();

                System.out.println("Packet received for channel: " + packet.getChannel() + " from " + packet.getFromID());

                if(Server.subscriptions.containsKey(packet.getChannel()))
                {
                    System.out.println("Subscriptions for this type: " + packet.getChannel());

                    ArrayList<Connection> subscribers = Server.subscriptions.get(packet.getChannel());

                    for(int i = 0; i < subscribers.size(); i++)
                    {
                        System.out.println("- " + subscribers.get(i).getFromID());
                    }


                    for (Connection c: subscribers) {

                        c.update(packet);

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
