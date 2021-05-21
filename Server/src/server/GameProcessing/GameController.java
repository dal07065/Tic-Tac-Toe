package server.GameProcessing;

import SharedServerComponents.ClientConnection;
import message.*;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

public class GameController implements Runnable{

    private ClientConnection serverConnection;

    // String - userID of player who started the game
    // Board  - the tic tac toe board of the game
    // (Inside Board, boardID is a hashcode of Board object itself - its not userID of player who started the game)
    private Map<String, Board> openGames = new HashMap<>();

    private Map<String, Board> gamesInProgress = new HashMap<>();

    public GameController() throws IOException {
        serverConnection = new ClientConnection("GameController","GetAllGames",
                "NewGame", "JoinGame", "GameMove", "QuitGame");

        Thread gameThread = new Thread(this);
        gameThread.start();
    }

    @Override
    public void run() {
        while(true)
        {
            try {
                Packet packet = serverConnection.getPacket();

                Message msg = packet.getMessage();

                if(msg instanceof GetAllGamesMessage) {
                    Set<String> set = openGames.keySet();
                    ArrayList<String> openGamesList = new ArrayList<>(set);
                    serverConnection.sendPacket(new Packet( packet.getFromID(), new GetAllGamesResponseMessage(openGamesList)));
                }
                else if(msg instanceof NewGameMessage)
                {
                    Board board = new Board(3,3, ((NewGameMessage) msg).getUserWhoStartedThisGame());

                    openGames.put(board.getPlayer1(), board);

                    serverConnection.sendPacket(new Packet( packet.getFromID(), new NewGameResponseMessage(board.getPlayer1())));
                }
                else if(msg instanceof JoinGameMessage)
                {
                    String gameID = ((JoinGameMessage) msg).getGameID();

                    Board game = openGames.remove(gameID);

                    if(game == null)
                    {
                        serverConnection.sendPacket(new Packet(packet.getFromID(), new JoinGameResponseMessage(false, gameID)));
                    }
                    else
                    {
                        game.setPlayer2(((JoinGameMessage) msg).getUserID());

                        gamesInProgress.put(gameID, game);
                        serverConnection.sendPacket(new Packet(packet.getFromID(), new JoinGameResponseMessage(true, gameID)));
                        serverConnection.sendPacket(new Packet(gameID, new JoinGameResponseMessage(true, gameID)));
                    }

                    // add this game to game history of both players on this board

                }
                else if(msg instanceof GameMoveMessage)
                {
                    // determine the String ID of the game (userID of initial player)
                    Board board = gamesInProgress.get(((GameMoveMessage) msg).getGameID());

                    // find the board

                    // try setting the move
                    board.setMove(((GameMoveMessage) msg).getGameMove(), ((GameMoveMessage) msg).getCurrentPlayer());
                    // check if anyone won

                    serverConnection.sendPacket(new Packet(board.getPlayer1(), new GameMoveResponseMessage(board.boardStatus(), ((GameMoveMessage) msg).getGameMove(), ((GameMoveMessage) msg).getCurrentPlayer(), true)));


                    // if not, send GameMoveMessage to the other client and send GameMoveResponseMessage to this client
                    // if yes, send GameWonMessage to both clients (String whoWonUserID, String gameID/userID)
                }
                else if(msg instanceof QuitGameMessage)
                {
                    QuitGameMessage quitGameMessage = (QuitGameMessage) msg;

                    Board board = gamesInProgress.get(quitGameMessage.getGameID());
                    serverConnection.sendPacket(new Packet("Unsubscribe", new UnsubscribeMessage(quitGameMessage.getGameID(), quitGameMessage.getPlayerWhoQuitID())));
                    serverConnection.sendPacket( new Packet( quitGameMessage.getGameID(), new GameMoveResponseMessage('q', null, 'q', false)));

                    board.resetBoard();

                    board.removePlayer(((QuitGameMessage) msg).getPlayerWhoQuitID());

                    openGames.put(board.getPlayer1(), board);
                    gamesInProgress.remove(((QuitGameMessage) msg).getGameID());
                }

            } catch (IOException e) {
                e.printStackTrace();
            } catch (ClassNotFoundException e) {
                e.printStackTrace();
            }


        }

    }
}
