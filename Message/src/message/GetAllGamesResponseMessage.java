package message;

import java.util.ArrayList;

public class GetAllGamesResponseMessage extends Message{

    private ArrayList<String> openGames;

    public GetAllGamesResponseMessage(ArrayList<String> openGames)
    {
        this.openGames = openGames;
    }

    public ArrayList<String> getAllGames(){ return openGames;}
}
