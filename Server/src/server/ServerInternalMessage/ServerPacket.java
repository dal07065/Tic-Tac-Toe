package server.ServerInternalMessage;

import message.Packet;
import server.ServerProcessing.Connection;

import java.io.Serializable;


public class ServerPacket extends Packet implements Serializable {

    private Connection connection;

    public ServerPacket(Packet packet, Connection connection)
    {
        super(packet.getType(), false, packet.getMessage());
        this.connection = connection;
    }

    public Connection getConnection()
    {
        return connection;
    }

}
