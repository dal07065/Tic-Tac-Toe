package message;

public class ConnectGameResponseMessage extends Message{
    private int roomPassword;
    private String status;
    private char player;

    public ConnectGameResponseMessage(int roomPassword, String status, char player)
    {
        this.roomPassword = roomPassword;
        this.status = status;
        this.player = player;
    }

    public int getRoomPassword() {
        return roomPassword;
    }

    public String getStatus() {
        return status;
    }

    public char getPlayer() {
        return player;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public void setPlayer(char player) {
        this.player = player;
    }
}
