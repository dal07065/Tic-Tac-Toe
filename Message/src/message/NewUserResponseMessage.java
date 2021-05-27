package message;

public class NewUserResponseMessage extends Message{

    private boolean isSuccessful;

    public NewUserResponseMessage(boolean isSuccessful)
    {
        this.isSuccessful = isSuccessful;
    }

    public boolean isSuccessful()
    {
        return isSuccessful;
    }


}
