package message;

import java.util.ArrayList;

public class GetRegisteredPlayersResponseMessage extends Message{
    private ArrayList<String> list;
    public GetRegisteredPlayersResponseMessage(ArrayList<String> list)
    {
        this.list = list;
    }

    public ArrayList<String> getRegisteredPlayers() {return list;}
}
