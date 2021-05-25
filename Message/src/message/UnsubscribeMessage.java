package message;

public class UnsubscribeMessage extends Message{

    private String channel;
    private String userID;

    public UnsubscribeMessage(String channel, String userID)
    {
        this.channel = channel;
        this.userID = userID;
    }

    public String getChannel() {
        return channel;
    }

    public String getUserID() {
        return userID;
    }
}
