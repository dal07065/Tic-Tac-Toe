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

    private ArrayList<AIController> listOfAI = new ArrayList<>();

    public GameController() throws IOException {
        serverConnection = new ClientConnection("GetAllGames",
                "NewGame", "JoinGame", "GameMove", "QuitGame", "NewGameAI", "GetCurrentGames", "ResetAIGame",
                "JoinGameAsViewer", "GetAllActiveGames", "ResetGame");

        Thread gameThread = new Thread(this);
        gameThread.start();
    }

    private AIController findAvailableAI()
    {
        for(AIController ai: listOfAI)
        {
            if(!ai.isPlaying())
                return ai;
        }
        return null;
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
                    Board board = new Board(3,3);

                    board.setPlayer1(((NewGameMessage) msg).getUserWhoStartedThisGame());

                    openGames.put(board.getBoardID(), board);

                    serverConnection.sendPacket(new Packet(packet.getFromID(), new NewGameResponseMessage(board.getBoardID())));
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

                    serverConnection.sendPacket(new Packet(board.getBoardID(), new GameMoveResponseMessage(board.boardStatus(), ((GameMoveMessage) msg).getGameMove(), ((GameMoveMessage) msg).getCurrentPlayer(), true)));


                    // if not, send GameMoveMessage to the other client and send GameMoveResponseMessage to this client
                    // if yes, send GameWonMessage to both clients (String whoWonUserID, String gameID/userID)
                }
                else if(msg instanceof QuitGameMessage)
                {
                    QuitGameMessage quitGameMessage = (QuitGameMessage) msg;

                    Board board = gamesInProgress.get(quitGameMessage.getGameID());

                    if(board == null) {
                        board = openGames.get(quitGameMessage.getGameID());

                        if (board != null) {
                            openGames.remove(board.getBoardID());
                            serverConnection.sendPacket(new Packet("Unsubscribe", new UnsubscribeMessage(quitGameMessage.getGameID(), quitGameMessage.getPlayerWhoQuitID())));
                            serverConnection.sendPacket(new Packet( quitGameMessage.getGameID(), new GameMoveResponseMessage('q', null, 'q', false)));
                        }
                    }
                    else
                    {
                        serverConnection.sendPacket(new Packet("Unsubscribe", new UnsubscribeMessage(quitGameMessage.getGameID(), quitGameMessage.getPlayerWhoQuitID())));

                        if(quitGameMessage.getPlayerWhoQuitID().equals(board.getPlayer1()) || quitGameMessage.getPlayerWhoQuitID().equals(board.getPlayer2()))
                        {
                            board.removePlayer(((QuitGameMessage) msg).getPlayerWhoQuitID());

                            openGames.put(board.getBoardID(), board);
                            gamesInProgress.remove(board.getBoardID());
                            serverConnection.sendPacket(new Packet( quitGameMessage.getGameID(), new GameMoveResponseMessage('q', null, 'q', false)));
                        }



                    }


                }
                else if(msg instanceof NewGameAIMessage)
                {
                    Board board = new Board(3,3);

                    board.setPlayer1(((NewGameAIMessage) msg).getUserWhoStartedThisGame());

                    openGames.put(board.getBoardID(), board);

                    ((NewGameAIMessage) msg).setBoardID(board.getBoardID());


                    AIController ai = findAvailableAI();

                    if(ai == null)
                    {
                        ai = new AIController(board, ((NewGameAIMessage) msg).getRole());
                        listOfAI.add(ai);
                    }
                    else
                    {
                        ai.reset(board, ((NewGameAIMessage) msg).getRole());
                    }

                    Thread AIthread = new Thread(ai);
                    AIthread.start();

                    serverConnection.sendPacket(new Packet("AI", msg));
                    serverConnection.sendPacket(new Packet(packet.getFromID(), new NewGameResponseMessage(board.getBoardID())));
                }
                else if (msg instanceof ResetBoardMessage)
                {
                    String boardID = ((ResetBoardMessage) msg).getGameID();
                    String player = ((ResetBoardMessage) msg).getUserID();

                    Board board = gamesInProgress.get(boardID);

                    if(board == null)
                       serverConnection.sendPacket(new Packet(packet.getFromID(), new ResetBoardResponseMessage(false)));
                    else
                    {
                        board.setPlayerReady(player);

                        if(board.playAgain()) {
                            serverConnection.sendPacket(new Packet(boardID, new ResetBoardResponseMessage(true)));
                            board.resetBoard();
                        }
                    }



                    // ask the other player to reset?
                }
                else if(msg instanceof JoinGameAsViewerMessage)
                {
                    String gameID = ((JoinGameAsViewerMessage) msg).getGameID();

                    Board game = gamesInProgress.get(gameID);

                    if(game == null)
                    {
                        serverConnection.sendPacket(new Packet(packet.getFromID(), new JoinGameAsViewerResponseMessage(false, null, 'q', null, null, null)));
                    }
                    else
                    {
                        serverConnection.sendPacket(new Packet(packet.getFromID(), new JoinGameAsViewerResponseMessage(true, gameID, game.getCurrentPlayer(), game.getPlayer1(), game.getPlayer2(),game.getBoard())));
//                        serverConnection.sendPacket(new Packet(gameID, new JoinGameAsViewerResponseMessage(true, gameID, game.getCurrentPlayer(), game.getPlayer1(), game.getPlayer2(), game.getBoard())));
                    }
                }
                else if(msg instanceof GetAllActiveGamesMessage)
                {

                    Set<String> set = gamesInProgress.keySet();
                    ArrayList<String> gamesList = new ArrayList<>(set);
                    serverConnection.sendPacket(new Packet( packet.getFromID(), new GetAllActiveGamesResponseMessage(gamesList)));

                }

            } catch (IOException e) {
                e.printStackTrace();
            } catch (ClassNotFoundException e) {
                e.printStackTrace();
            }


        }

    }
}
