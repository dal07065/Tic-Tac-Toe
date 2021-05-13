package sample;

import message.*;

import java.io.*;
import java.net.InetAddress;
import java.net.Socket;
import java.net.SocketException;

public class ClientConnection implements Runnable{

    Socket client;
    ObjectInputStream inputFromClient;
    ObjectOutputStream outputToClient;
    boolean keepRunning = true;

    User user = null;

    ServerProcess server;
    Thread clientThread;

    LANConnection currentGame;

    ClientConnection(ServerProcess server, Socket client) throws IOException
    {
        this.client = client;
        this.server = server;

        InetAddress inetAddress = client.getInetAddress();
        System.out.println("New Client Connection from " + inetAddress.getHostAddress());

        inputFromClient = new ObjectInputStream(client.getInputStream());

        outputToClient = new ObjectOutputStream(client.getOutputStream());

        clientThread = new Thread(this);
        clientThread.start();

    }

    public Socket getSocket()
    {
        return client;
    }

    @Override
    public void run() {

        try
        {
            while (keepRunning)
            {
                if(client.isClosed())
                    return;

                Packet packet = (Packet) inputFromClient.readObject();

                Packet returnPacket = null;

                String type = packet.getType();

                if (type.equals("signin"))
                {
                    LogInMessage message = (LogInMessage) packet.getMessage();

                    user = server.findUser(message.getUserID(), message.getPassword()); // get username

                    // if user is active, deny the signin
                    if(user == null)
                        returnPacket = new Packet("error/No User/", null);
                    else if(!user.getPassword().equals(message.getPassword()))
                        returnPacket = new Packet("error/Wrong Password/", null);
                    else
                        returnPacket = new Packet("userInfo", new UserInfoMessage(user.getUserID(), user.getPassword(), user.getFirstName(), user.getLastName()));

                    outputToClient.writeObject(returnPacket);

                }
                else if(type.equals("newUser"))
                {
                    user = new User((UserInfoMessage) packet.getMessage());
                    user.setSocket(client);

                    server.addUser(user);


                    // update the database based on the new user information
                }
                else if(type.equals("updateUser"))
                {
                    UserInfoMessage userInfoMessage = (UserInfoMessage) packet.getMessage();

                    user = server.findUser(userInfoMessage.getUserID(), userInfoMessage.getPassword());
                    user.update(userInfoMessage);
                    returnPacket = new Packet("userInfo", new UserInfoMessage(user.getUserID(), user.getPassword(), user.getFirstName(), user.getLastName()));
                    outputToClient.writeObject(returnPacket);

                    // update the database based on the new user information
                }
                else if(type.equals("newGame")) {
                    int roomPassword = ((ConnectGameMessage) packet.getMessage()).getRoomPassword();
                    currentGame = server.findLANConnection(roomPassword);

                    if (currentGame == null) {
                        currentGame = new LANConnection(roomPassword, this, server);
                        server.addLANConnection(currentGame);
                        returnPacket = new Packet("newGame", new ConnectGameReceivedMessage(roomPassword, "newGame", 'X'));
                    } else if (!currentGame.isFull()) {
                        currentGame.addClientConnection(this);
                        returnPacket = new Packet("newGame", new ConnectGameReceivedMessage(roomPassword, "gameJoined", 'O'));
                    } else {
                        returnPacket = new Packet("occupied", null);
                    }
                    outputToClient.writeObject(returnPacket);
                }
                else if(type.equals("makeGameMove"))
                {
                    // server.makeGameMove(moveID, currentPlayer)
                    // return gameResultMessage(winstatus, moveID)
                    GameMoveMessage gameMoveMsg = (GameMoveMessage) (packet.getMessage());

                    GameResultMessage gameResultMessage = currentGame.setMove(gameMoveMsg.getGameMove(), gameMoveMsg.getCurrentPlayer(), this);

                    returnPacket = new Packet("gameMoveMade", gameResultMessage);

                    outputToClient.writeObject(returnPacket);
                    currentGame.getOtherClient(this).writeObject(returnPacket);
                    // find currentGame
                    // send current message to other client in currentGame
                }

            }
        } catch (SocketException se)
        {
            System.out.println("(Client disconnected from " + client.getInetAddress().getHostAddress() + ")");
        } catch (EOFException e)
        {
            System.out.println("(Client suddenly disconnected from " + client.getInetAddress().getHostAddress() + ")");
        }
        catch (Exception e) {
            e.printStackTrace();
        }

    }

    public void writeObject(Packet packet) throws IOException {
        outputToClient.writeObject(packet);
    }
    public int readObject() throws IOException {
        return inputFromClient.readInt();
    }

    public void pauseThread() throws InterruptedException {

        synchronized (clientThread)
        {
            clientThread.wait();
        }
    }

    public void resumeThread()
    {
        clientThread = new Thread(this);
        clientThread.start();
    }
}
