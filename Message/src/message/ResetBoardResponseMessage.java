package message;

public class ResetBoardResponseMessage extends Message{

    private boolean success;

    public ResetBoardResponseMessage(boolean success)
    {
        this.success = success;
    }

    public boolean isSuccess() {
        return success;
    }
}
