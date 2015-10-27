package robo.network;

import java.util.List;
import java.util.ArrayList;
import java.util.HashMap;
import java.nio.channels.DatagramChannel;
import java.nio.channels.SocketChannel;
import java.nio.channels.ServerSocketChannel;
import java.net.ServerSocket;

import java.net.SocketAddress;
import java.net.InetSocketAddress;
import java.net.DatagramSocket;
import java.net.Socket;
import java.net.InetAddress;

import java.nio.ByteBuffer;

import javax.vecmath.Point2d;

import robo.Callback;
import robo.graphics.Unit;
import robo.graphics.Projectile;

public class NetworkEnviroment
{
	private Callback creationCallback;
	private Callback deletionCallback;
	private Callback updateCallback;
	private Callback hitCallback;

	boolean server;

	String serverAddress;
	List<Player> players;

	HashMap<Long, NetworkEntity> handles;
	long handleCounter = 1;

	ByteBuffer receiveBuffer;

	// List<DatagramChannel> clientChannels;
	// List<SocketChannel> serverChannels;

	public NetworkEnviroment(String serverAddress, int port)
	{
		this.serverAddress = serverAddress;
		this.players = new ArrayList<Player>();
		this.handles = new HashMap<Long, NetworkEntity>();

		receiveBuffer = ByteBuffer.allocate(256);

		//DatagramChannel datagrammChannel;
		//System.out.println("Server address is: " + serverAddress);

		if (serverAddress != null)
		{
			// we are a client
			Player server = new Player(new InetSocketAddress("localhost", port));
			server.setSocket(port);

			players.add(server);
		}
		else
		{
			for (int i=0; i<2; i++) {
				// we are the server
				Player client = new Player(new InetSocketAddress("localhost", port+i));

				try {
					ServerSocket serverSocket = new ServerSocket(port+i);
					client.setSocket(serverSocket.accept());

				} catch (Exception e) {
					e.printStackTrace();
					System.out.println(e + ": " + e.getMessage());
				}

				players.add(client);
			}
		}



		if (serverAddress != null)
		{
			// we are a client

			// try
			// {
			// 	datagrammChannel = DatagramChannel.open();
			// 	//datagrammChannel.connect(new InetSocketAddress("localhost", port));

			// 	ByteBuffer bb = ByteBuffer.allocate(256);
			// 	bb.putLong(1337);
			// 	bb.flip();

			// 	System.out.println("Sending...");
			// 	datagrammChannel.send(bb, new InetSocketAddress("localhost", port));
			// 	System.out.println("Sending finished!");
			// }
			// catch(Exception e)
			// {
			// 	System.out.println("=== CAN'T FIND SERVER! ====");
			// }
		}
		else
		{
			// we are the server

			// try
			// {
			// 	datagrammChannel = DatagramChannel.open();
			// 	//datagrammChannel.connect(new InetSocketAddress("localhost", port));

			// 	DatagramSocket socket = datagrammChannel.socket();
			// 	SocketAddress address = new InetSocketAddress("localhost", port);
			// 	socket.bind(address);

			// 	ByteBuffer bb = ByteBuffer.allocate(256);

			// 	while(true) {
			// 		System.out.println("Receiving...");
			// 		SocketAddress client = datagrammChannel.receive(bb);
			// 		System.out.println("Received!");

			// 		bb.flip();
			// 		System.out.println(bb.getLong());
			// 	}


			// }
			// catch(Exception e)
			// {
			// 	System.out.println("=== CAN'T FIND SERVER! ====");
			// }
		}
	}

	private enum Message {
		DELETE,
		UPDATE,
		CREATE,
		HIT
	}

	public void createEntity(Type type, byte[] params, NetworkEntity entity, Player ignorePlayer)
	{
		System.out.println("createEntity called");

		ByteBuffer bb = ByteBuffer.allocate(4 + 8 + params.length + 4);
		bb.putInt(Message.CREATE.ordinal());
		bb.putLong(handleCounter);
		bb.putInt(type.ordinal());
		bb.put(params);

		handles.put(handleCounter, entity);

		// TODO: make sure same handle is not created simultaniously on two clients
		handleCounter++;

		// SERVER: Send to all, but sender
		// CLIENT: Send to server
		for (Player player : players)
		{
			if (player == ignorePlayer)
			{
				continue;
			}
			if (player.socket == null)
			{
				continue;
			}

			System.out.println("createEntity: server with socket found.");

			try
			{
				System.out.println("createEntity: array writen bytes: " + bb.array().length);
				player.out.write(bb.array());
			}
			catch(Exception e)
			{
				System.out.println(e);
			}
		}
	}

	public void deleteEntity(long handle)
	{
		ByteBuffer bb = ByteBuffer.allocate(4 + 8);
		bb.putInt(Message.DELETE.ordinal());
		bb.putLong(handleCounter++);
	}

	public void updateEntity(long handle)
	{
		NetworkEntity entity = handles.get(handle);
		if (entity == null) {
			System.out.println("bad handle");
		}

		ByteBuffer bb = ByteBuffer.allocate(4 + 8 + 16 + 4);
		bb.putInt(Message.UPDATE.ordinal());
		bb.putLong(handleCounter++);

		Point2d point = entity.getPosition();
		bb.putDouble(point.getX());
		bb.putDouble(point.getY());

		bb.putFloat(entity.getRotation());
	}

	public void sendFrameUpdate()
	{

	}

	public static ByteBuffer cloneByteBuffer(final ByteBuffer original) {
		// Create clone with same capacity as original.
		final ByteBuffer clone = (original.isDirect()) ?
				ByteBuffer.allocateDirect(original.capacity()) :
				ByteBuffer.allocate(original.capacity());

		// Create a read-only copy of the original.
		// This allows reading from the original without modifying it.
		final ByteBuffer readOnlyCopy = original.asReadOnlyBuffer();

		// Flip and read from the original.
		readOnlyCopy.flip();
		clone.put(readOnlyCopy);

		return clone;
	}

	public void receive() 
	{
		for (Player player : players)
		{
			if (player.socket == null) continue;

			int readData = 0;

			try
			{
				// read data from channel
				//receiveBuffer.flip(); // TODO: check if needed
				//byte[] buffer = new byte[256];

				readData = player.in.read(receiveBuffer.array());
				if (readData == -1)
				{
					System.out.println("Read went wrong");
				}
			}
			catch (Exception e) {}

			if (readData == 0) continue;
			System.out.println("receive");

			// decode type of message
			int msgType = receiveBuffer.getInt();
			if(msgType == Message.CREATE.ordinal())
			{
				System.out.println("CREATED OBJECT!");
				
				long handle = receiveBuffer.getLong();

				int type = receiveBuffer.getInt();

				NetworkEntity entity = null;

				ByteBuffer params = cloneByteBuffer(receiveBuffer);

				Type typeEnum = null;

				if (type == Type.PLAYER.ordinal())
				{
					typeEnum = Type.PLAYER;
					System.out.println("player");
					entity = Unit.createFromBlob(receiveBuffer);

				} else if (type == Type.BULLET.ordinal()) {
					typeEnum = Type.BULLET;
					System.out.println("bullet");
					entity = Projectile.createFromBlob(receiveBuffer);
				} else {
					System.out.println("switch Type missing...");
				}

				handles.put(handle, entity);

				createEntity(typeEnum, params.array(), entity, player);

			} else if (msgType == Message.UPDATE.ordinal()) {
				long handle = receiveBuffer.getLong();
				double x = receiveBuffer.getDouble();
				double y = receiveBuffer.getDouble();
				float rotation = receiveBuffer.getFloat();

				NetworkEntity entity = handles.get(handle);
				entity.setPositionNoUpdate(new Point2d(x, y));
				entity.setRotationNoUpdate(rotation);
			} else {
				System.out.println("switch Message missing...: ");
			}
		}
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