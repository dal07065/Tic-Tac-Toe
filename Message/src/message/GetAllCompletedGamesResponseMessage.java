package message;

import java.util.ArrayList;

public class GetAllCompletedGamesResponseMessage extends Message{
    private ArrayList<String> completedGames;

    public GetAllCompletedGamesResponseMessage(ArrayList<String> list)
    {
        completedGames = list;
    }

    public ArrayList<String> getCompletedGames() {return completedGames;}
}
