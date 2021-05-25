package message;

public class ConnectGameMessage extends Message{

    private int roomPassword;
    private String status;
    private int player;

    public ConnectGameMessage(int roomPassword)
    {
        this.roomPassword = roomPassword;
    }

    public void setStatus(String status)
    {
        this.status = status;
    }

    public int getPlayer() {
        return player;
    }

    public void setPlayer(int player) {
        this.player = player;
    }

    public String getStatus() { return status; }

    public int getRoomPassword() {
        return roomPassword;
    }
}
