package message;

import java.util.ArrayList;

public class GetAllActiveGamesResponseMessage extends Message{

    private ArrayList<String> list;
    public GetAllActiveGamesResponseMessage(ArrayList<String> list)
    {
        this.list = list;
    }

    public ArrayList<String> getAllGames() {return list;}
}
