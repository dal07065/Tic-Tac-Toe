package message;

import java.io.Serializable;

public class Packet implements Serializable {

    private Message message;
    private String fromID;
    private String channel;

    /**
     *
     * @param channel where is this packet going to?
     * @param message the message itself?
     */
    public Packet(String channel, Message message)
    {
        this.message = message;
        this.channel = channel;
    }

    public Message getMessage() {
        return message;
    }

    public void setFromID(String ID)
    {
        fromID = ID;
    }

    public String getFromID() {return fromID;}

    public String getChannel() { return channel;}

}
