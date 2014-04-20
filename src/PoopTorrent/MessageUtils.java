package poopTorrent;

import java.nio.ByteBuffer;

public class MessageUtils
{
	private static void sendMessage(int peerID, Message message) {
		//TODO Send the message here
		
	}
	
	public static void handshake(int peerID)
	{
		Message message = new HandshakeMessage( peerID );

		sendMessage(peerID, message);
	}
	
	public static void choke(int peerID)
	{
		Message message = new NormalMessage( (byte)0, new byte[0] );

		sendMessage(peerID, message);
	}

	public static void unchoke(int peerID)
	{
		Message message = new NormalMessage( (byte)1, new byte[0] );

		sendMessage(peerID, message);
	}
	
	public static void interested(int peerID)
	{
		Message message = new NormalMessage( (byte)2, new byte[0] );

		sendMessage(peerID, message);
	}
	
	public static void notInterested(int peerID)
	{
		Message message = new NormalMessage( (byte)3, new byte[0] );

		sendMessage(peerID, message);
	}
	
	public static void have(int peerID, int index)
	{
		ByteBuffer buffer = ByteBuffer.allocate(4);
		buffer.putInt(index);
		Message message = new NormalMessage( (byte)4, buffer.array() );

		sendMessage(peerID, message);
	}
	
	public static void bitfield(int peerID, byte[] bitfield)
	{
		Message message = new NormalMessage( (byte)5, bitfield );

		sendMessage(peerID, message);
	}
	
	public static void request(int peerID, int index)
	{
		ByteBuffer buffer = ByteBuffer.allocate(4);
		buffer.putInt(index);
		Message message = new NormalMessage( (byte)6, buffer.array() );

		sendMessage(peerID, message);
	}
	
	public static void piece(int peerID, int index, byte[] payload)
	{
		ByteBuffer buffer = ByteBuffer.allocate(payload.length + 4);
		buffer.putInt(index);
		buffer.put(payload);
		Message message = new NormalMessage( (byte)7, buffer.array() );

		sendMessage(peerID, message);
	}
}
