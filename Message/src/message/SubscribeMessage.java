package message;

import java.util.ArrayList;

public class SubscribeMessage extends Message{
    private ArrayList<String> channels;


    public SubscribeMessage(ArrayList<String> channels)
    {
        this.channels = channels;
    }
    public SubscribeMessage(String ... channels)
    {
        this.channels = new ArrayList<>();
        for(String channel: channels)
        {
            this.channels.add(channel);
        }
    }

    public ArrayList<String>  getChannels() {
        return channels;
    }
}
