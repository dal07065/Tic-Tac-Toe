package message;

public class NewGameMessage extends Message{

    private String userWhoStartedThisGame;

    public NewGameMessage(String userID)
    {
        userWhoStartedThisGame = userID;
    }

    public String getUserWhoStartedThisGame() {return userWhoStartedThisGame;}

}
