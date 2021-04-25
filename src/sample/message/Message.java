package sample.message;

import java.util.ArrayList;

public class Message {

    ArrayList<String> messages;
    String originalPacket;
    String type;

    public Message(String packet)
    {
        messages = new ArrayList<String>();
        originalPacket = packet;

        int index = packet.indexOf('/');
        type = packet.substring(0, index);

        if(packet.substring(index).length() == 1)
            return;
        else
            packet = packet.substring(index + 1);

        boolean loop = true;

        while(loop)
        {
            index = packet.indexOf('/');

            messages.add(packet.substring(0, index));

            if(packet.substring(index).length() == 1)
            {
                loop = false;
            }
            else
                packet = packet.substring(index + 1);

        }
    }

    public String getType()
    {
        return type;
    }

    public String get(int index)
    {
        return messages.get(index);
    }
}
