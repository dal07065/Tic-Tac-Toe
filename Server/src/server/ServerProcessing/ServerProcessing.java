package server.ServerProcessing;

import SharedServerComponents.ClientConnection;
import message.*;
import server.Database;
import server.ServerInternalMessage.ServerPacket;
import server.User;

import javax.xml.crypto.Data;
import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;

public class ServerProcessing implements Runnable {

    private ClientConnection serverConnection;

    public ServerProcessing() throws IOException, ClassNotFoundException {
        serverConnection = new ClientConnection("LogIn", "NewUser", "UpdateUser", "CloseSocket",
                "SubscribeMessage", "Unsubscribe", "DeleteUser");
    }

    @Override
    public void run() {
        try {

            while(true)
            {

                Packet packet = (Packet) serverConnection.getPacket();

                System.out.println("*Server has received a message* : " + packet.getChannel());

                Message msg = packet.getMessage();

                if(msg instanceof SubscribeMessage)
                {
                    ArrayList<Connection> connectionChannel = Server.subscriptions.get(packet.getFromID());


                    for(String channel:((SubscribeMessage) msg).getChannels())
                    {
                        // find connection via userID or something

                        connectionChannel.get(0).addChannel(channel);
                    }
                }
                else if(msg instanceof UnsubscribeMessage)
                {
                    ArrayList<Connection> connectionChannel = Server.subscriptions.get(packet.getFromID());

                    connectionChannel.get(0).removeChannel(((UnsubscribeMessage) msg).getChannel());

                }
                else if(msg instanceof LogInMessage)
                {
                    // connection.update(packet -> userInfoMessage)
                    User user = Database.findUser(((LogInMessage) msg).getUserID());

                    if(user != null)
                        serverConnection.sendPacket(new Packet(packet.getFromID(), new LogInResponseMessage(true, user.getUserID(), user.getPassword(), user.getFirstName(), user.getLastName(),
                                user.getWins(), user.getLoses(), user.getTies())));
                    else
                        serverConnection.sendPacket(new Packet(packet.getFromID(), new LogInResponseMessage(false, null, null, null, null, 0,0,0)));

                }
                else if(msg instanceof UpdateUserMessage)
                {
                    boolean success = Database.updateUser((UpdateUserMessage) msg);

                    if(success) {
                        serverConnection.sendPacket(new Packet(packet.getFromID(), new UpdateUserResponseMessage(true)));
                        System.out.println("Database user has been updated");
                    }
                    else
                        serverConnection.sendPacket(new Packet(packet.getFromID(), new UpdateUserResponseMessage(false)));


//                  serverConnection.sendPacket(new Packet("UpdateUserResponse" + packet.getFromID(), false, msg));

                }
                else if(msg instanceof NewUserMessage)
                {
                    User user = Database.findUser(((NewUserMessage) msg).getUserID());
                    if(user == null) {
                        Database.insertUser((NewUserMessage) msg);
                        serverConnection.sendPacket(new Packet(packet.getFromID(), new NewUserResponseMessage(true)));
                    }
                    else
                        serverConnection.sendPacket(new Packet(packet.getFromID(), new NewUserResponseMessage(false)));

//                serverConnection.sendPacket(new Packet("NewUserResponse" + packet.getFromID(), false, msg));
                    // no need to send back response to connection
                }
                else if(msg instanceof QuitGameMessage)
                {
                    ArrayList<Connection> connectionChannel = Server.subscriptions.get(packet.getFromID());


                    for(String channel:((SubscribeMessage) msg).getChannels())
                    {
                        // find connection via userID or something

                        connectionChannel.get(0).addChannel(channel);
                    }
                }
                else if(msg instanceof DeleteUserMessage)
                {
                    Database.deleteUser(((DeleteUserMessage) msg).getUserID());
                }
                else if(packet.getChannel().equals("CloseSocket"))
                {

                }
                // msg instanceof ConnectGameMessage

                // msg instanceof GameMoveMessage


            }

        } catch (SQLException throwables) {
            throwables.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
    }
}
