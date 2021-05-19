package message;

import java.util.ArrayList;

public class SubscribeMessage extends Message{
    private ArrayList<String> channels;

    public SubscribeMessage(ArrayList<String> channels)
    {
        this.channels = channels;
    }

    public ArrayList<String> getChannels() {
        return channels;
    }
}
