package sample;

import GameLogic.Board;
import message.GameMoveMessage;
import message.GameResultMessage;
import message.Message;
import message.Packet;

import java.io.*;
import java.net.Socket;

public class LANConnection implements Runnable {
    private ClientConnection c1;
    private ClientConnection c2;

//    private ObjectOutputStream c1_outputStream;
//    private ObjectOutputStream c2_outputStream;

    private ServerProcess server;

    private int roomPassword;

    private Board board;

    private char winner;

    private GameResultMessage lastMoveMade;

    public LANConnection(int roomPassword, ClientConnection c1, ServerProcess server) throws Exception {

        board = new Board(3,3);

        this.c1 = c1;

        this.roomPassword = roomPassword;

        this.server = server;

//        c1_outputStream = new ObjectOutputStream(c1.getSocket().getOutputStream());

    }

    public void addClientConnection(ClientConnection c2) throws IOException, InterruptedException {
        this.c2 = c2;
//        c2_outputStream = new ObjectOutputStream(c2.getSocket().getOutputStream());
    }
    public int getRoomPassword() {
        return roomPassword;
    }

    public boolean isFull()
    {
        if (c1 != null & c2 != null)
            return true;
        return false;
    }

    /**
     * Sets the Board with given move, sets the last move made, update the other client with latest move
     * @param id
     * @param currentPlayer
     * @return
     * @throws IOException
     */

    public GameResultMessage setMove(String id, char currentPlayer, ClientConnection currentClient) throws IOException {
        board.setMove(id, currentPlayer);
        lastMoveMade = new GameResultMessage(checkWinner(), id, currentPlayer);
        return lastMoveMade;
    }

    public ClientConnection getOtherClient(ClientConnection thisClient)
    {
        return thisClient==c1? c2 : c1;
    }

    public GameResultMessage getLastMoveMade()
    {
        return lastMoveMade;
    }

    public void resetLastMoveMade()
    {
        lastMoveMade = null;
    }

    /**
     * Checks the board if anyone won and if so, determine who is the winner
     *
     */
    public char checkWinner() throws IOException {
        char winner = board.boardStatus();

        if(winner != '-')
        {
            System.out.println("The winner is : " + winner);
        }
        return winner;
    }

    public void run() {
        try {

            // run the game

            // client 1 makes a move -> Based on number pad keys i.e. 7 is top left

            while (true) {

                int move1 = c1.readObject();

//                c2.writeObject(move1);

                if(move1 == 0)
                    break;

                int move2 = c2.readObject();

//                c1.writeObject(move2);

                if(move2 == 0)
                    break;

            }

            System.out.println("Game terminated " + roomPassword);
            server.removeLANConnection(roomPassword);

        }
        catch (Exception ex) {
            ex.printStackTrace();
        }
    }
}
