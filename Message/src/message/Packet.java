package message;

import java.io.Serializable;

public class Packet implements Serializable {
    private String type;
    private Message message;
    private String fromID;
    private boolean needsResponse;

    public Packet(String type, boolean needsResponse, Message message)
    {
        this.type = type;
        this.message = message;
        this.needsResponse = needsResponse;
    }

    public String getType() {
        return type;
    }

    public Message getMessage() {
        return message;
    }

    public void setFromID(String ID)
    {
        fromID = ID;
    }

    public String getFromID() {return fromID;}

    public boolean getNeedsResponse() {return needsResponse;}
    public void setNeedsResponse(boolean response) {needsResponse = response;}
}
