package server.ServerProcessing;

import SharedServerComponents.ClientConnection;
import message.*;
import server.Database;
import server.ServerInternalMessage.ServerPacket;
import server.User;

import javax.xml.crypto.Data;
import java.io.IOException;
import java.sql.SQLException;

public class ServerProcessing implements Runnable {

    private ClientConnection serverConnection;

    public ServerProcessing() throws IOException, ClassNotFoundException {
        serverConnection = new ClientConnection("LogIn", "NewUser", "UpdateUser", "CloseSocket");
    }

    @Override
    public void run() {
        try {

            while(true)
            {

                Packet packet = (Packet) serverConnection.getPacket();

                System.out.println("*Server has received a message* : " + packet.getType());

                Message msg = packet.getMessage();

                if(msg instanceof SubscribeMessage)
                {
//                for(String channel:((SubscribeMessage) msg).getChannels())
//                {
//                    // find connection via userID or something
//
////                    Server.addConnectionToChannel(channel, connection);
//                }
                }
                else if(msg instanceof LogInMessage)
                {
                    // connection.update(packet -> userInfoMessage)
                    User user = Database.findUser(((LogInMessage) msg).getUserID());

                    if(user != null)
                        serverConnection.sendPacket(new Packet("LogInResponse" + packet.getFromID(), false, new LogInResponseMessage(user.getUserID(), user.getPassword(), user.getFirstName(), user.getLastName())));
                    else
                        serverConnection.sendPacket(new Packet("LogInResponse" + packet.getFromID(), false,null));

                }
                else if(msg instanceof UpdateUserMessage)
                {
                    Database.updateUser((UserInfoMessage) msg);

                    System.out.println("Database user has been updated");

//                serverConnection.sendPacket(new Packet("UpdateUserResponse" + packet.getFromID(), false, msg));

                }
                else if(msg instanceof NewUserMessage)
                {
                    Database.insertUser((NewUserMessage) msg);

//                serverConnection.sendPacket(new Packet("NewUserResponse" + packet.getFromID(), false, msg));
                    // no need to send back response to connection
                }
                else if(packet.getType().equals("CloseSocket"))
                {

                }
                // msg instanceof ConnectGameMessage

                // msg instanceof GameMoveMessage


            }

        } catch (SQLException | InterruptedException throwables) {
            throwables.printStackTrace();
        }
    }
}
