package server.GameProcessing;

import SharedServerComponents.ClientConnection;
import SharedServerComponents.IDGenerator;
import message.*;
import server.GameProcessing.AI.Minimax;

import java.io.IOException;

public class AIController implements Runnable{
    private ClientConnection connection;
    private Minimax minimax = new Minimax();

    private Board board;

    private char AI_role;

    private String AI_ID;

    private boolean isPlaying;

    public AIController(Board board, char role) throws IOException {
        connection = new ClientConnection("AI");
        isPlaying = true;
        this.board = board;
        AI_ID = IDGenerator.getNewID(this);
        AI_role = role;
    }

    @Override
    public void run() {
        while(isPlaying)
        {
            try {
                Packet packet = connection.getPacket();
                Message msg = packet.getMessage();
                // retrieve the gameID

                if (msg instanceof NewGameAIMessage)
                {
                    connection.sendPacket(new Packet("JoinGame", new JoinGameMessage(AI_ID, ((NewGameAIMessage) msg).getBoardID())));
                }
                else if(msg instanceof JoinGameResponseMessage)
                {
                    connection.sendPacket(new Packet("SubscribeMessage", new SubscribeMessage(((JoinGameResponseMessage)msg).getGameID())));
                }
                else if(msg instanceof GameMoveResponseMessage)
                {
                    if(((GameMoveResponseMessage) msg).isContinueGame())
                    {
                        String moveID = minimax.findBestMove(board, AI_role,false);
                        connection.sendPacket(new Packet( "GameMove", new GameMoveMessage(moveID, board.getBoardID(), AI_role)));
                        connection.getPacket("GameMoveResponse");
                    }
                    else
                    {
                        isPlaying = false;
                    }
                }


                // look at the board and decide a move

                // send the move to gamecontroller

            } catch (IOException e) {
                e.printStackTrace();
            } catch (ClassNotFoundException e) {
                e.printStackTrace();
            }
        }
    }

    public boolean isPlaying() {
        return isPlaying;
    }

    public void reset(Board board, char role) {
        isPlaying = true;
        this.board = board;
        AI_role = role;
    }
}
