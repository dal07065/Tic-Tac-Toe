package message;

import java.io.Serializable;

public class Packet implements Serializable {
    private String type;
    private Message message;

    public Packet(String type, Message message)
    {
        this.type = type;
        this.message = message;
    }

    public String getType() {
        return type;
    }

    public Message getMessage() {
        return message;
    }
}
