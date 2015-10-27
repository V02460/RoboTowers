package robo.network;

import java.net.InetSocketAddress;
import java.net.DatagramSocket;
import java.net.Socket;
import java.io.PrintWriter;
import java.io.BufferedReader;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.InputStreamReader;
import java.io.ByteArrayOutputStream;
import java.io.ByteArrayInputStream;
import java.net.InetAddress;

class Player
{
	public InetSocketAddress address;
	//public DatagramSocket datagrammSocket = null; 
	public Socket socket = null;

	public OutputStream out = null; 
    public InputStream in = null; 

    public void setSocket(int port)
    {
    	try
    	{
			socket = new Socket(InetAddress.getByName("localhost"), port);
			
    	} catch(Exception e) {
			e.printStackTrace();
			System.out.println(e + ": " + e.getMessage());
		}

		setSocket(socket);
    }

    public void setSocket(Socket socket)
    {
    	this.socket = socket;

    	try
    	{
	    	
	    	out = socket.getOutputStream();
			in = socket.getInputStream();
		} catch(Exception e) {
			e.printStackTrace();
			System.out.println(e + ": " + e.getMessage());
		}
    }

	public Player(InetSocketAddress address)
	{
		this.address = address;
	}
}