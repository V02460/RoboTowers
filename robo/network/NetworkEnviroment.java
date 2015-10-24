package robo.network;

import java.util.List;
import java.util.ArrayList;
import java.nio.channels.DatagramChannel;
import java.nio.channels.SocketChannel;
import java.net.InetSocketAddress;

import java.nio.ByteBuffer;

import robo.Callback;

public class NetworkEnviroment
{
	private Callback creationCallback;
	private Callback deletionCallback;
	private Callback updateCallback;
	private Callback hitCallback;

	List<Player> players;
	String serverAdress;

	List<DatagramChannel> clientChannels;
	List<SocketChannel> serverChannels;

	public NetworkEnviroment(String serverAdress)
	{
		this.serverAdress = serverAdress;
		this.players = new ArrayList<Player>();

		DatagramChannel datagrammChannel;

		if (serverAdress != null)
		{
			// we are a client
			players.add(new Player("localhost"));

			try
			{
				datagrammChannel = DatagramChannel.open();
				datagrammChannel.connect(new InetSocketAddress("localhost", 1234));

				ByteBuffer bb = ByteBuffer.allocate(256);
				bb.putLong(1337);
				datagrammChannel.write(bb);
				System.out.println(bb);
			}
			catch(Exception e)
			{
				System.out.println(e);
				System.out.println("=== CAN'T FIND SERVER! ====");
			}
		}
		else
		{
			// we are the server

			try
			{
				datagrammChannel = DatagramChannel.open();
				datagrammChannel.connect(new InetSocketAddress("localhost", 1234));

				ByteBuffer bb = ByteBuffer.allocate(256);
				datagrammChannel.read(bb);
				System.out.println(bb.getLong());
			}
			catch(Exception e)
			{
				System.out.println("=== CAN'T FIND SERVER! ====");
			}
		}
	}

	long createEntity(Type type, byte[] param)
	{
		return 0;
	}

	void deleteEntity(long handle)
	{

	}

	void updateEntity(long handle)
	{

	}

	void sendFrameUpdate()
	{

	}

	public void setCreationCallback(Callback callback)
	{
		creationCallback = callback;
	}

	public void setDeletionCallback(Callback callback)
	{
		deletionCallback = callback;
	}

	public void setUpdateCallback(Callback callback)
	{
		updateCallback = callback;
	}

	public void setHitCallback(Callback callback)
	{
		hitCallback = callback;
	}
}