package message;

public class UpdateUserResponseMessage extends Message{
    private boolean success;
    public UpdateUserResponseMessage(boolean success)
    {
        this.success = success;
    }

    public boolean isSuccessful()
    {
        return success;
    }

}
