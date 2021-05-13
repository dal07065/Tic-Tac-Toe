package message;

import java.util.ArrayList;

public class SubscribeResponseMessage extends Message{

    private ArrayList<String> subscribedChannels;

    public SubscribeResponseMessage(ArrayList<String> subscribedChannels)
    {
        this.subscribedChannels = subscribedChannels;
    }

    public ArrayList<String> getSubscribedChannels() {
        return subscribedChannels;
    }
}
