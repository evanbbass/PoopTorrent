package poopTorrent;

import java.nio.ByteBuffer;

public class MessageUtils
{
	/**
	 * Sends message to the designated peer.
	 * @param peerID ID of the peer to send the message to
	 * @param message Message with payload to send
	 */
	private static void sendMessage(int peerID, Message message) {
		//TODO Send the message here
		
	}

	/**
	 * Creates and sends a handshake message to the designated peer.
	 * @param peerID ID of the sender to put in the message
	 * @param targetID ID of the peer to send the message to
	 */
	public static void handshake(int peerID, int targetID)
	{
		Message message = new HandshakeMessage( peerID );

		sendMessage(targetID, message);
	}

	/**
	 * Creates and sends a choke message to the designated peer.
	 * @param peerID ID of the peer to send the message to
	 */
	public static void choke(int peerID)
	{
		Message message = new NormalMessage( (byte)0, new byte[0] );

		sendMessage(peerID, message);
	}

	/**
	 * Creates and sends an unchoke message to the designated peer.
	 * @param peerID ID of the peer to send the message to
	 */
	public static void unchoke(int peerID)
	{
		Message message = new NormalMessage( (byte)1, new byte[0] );

		sendMessage(peerID, message);
	}

	/**
	 * Creates and sends an interested message to the designated peer.
	 * @param peerID ID of the peer to send the message to
	 */
	public static void interested(int peerID)
	{
		Message message = new NormalMessage( (byte)2, new byte[0] );

		sendMessage(peerID, message);
	}

	/**
	 * Creates and sends a not interested message to the designated peer.
	 * @param peerID ID of the peer to send the message to
	 */
	public static void notInterested(int peerID)
	{
		Message message = new NormalMessage( (byte)3, new byte[0] );

		sendMessage(peerID, message);
	}

	/**
	 * Sends a have message to the designated peer along with the index of the possesed piece
	 * @param peerID ID of the peer to send the message to
	 * @param index Index of the possesed piece
	 */
	public static void have(int peerID, int index)
	{
		ByteBuffer buffer = ByteBuffer.allocate(4);
		buffer.putInt(index);
		Message message = new NormalMessage( (byte)4, buffer.array() );

		sendMessage(peerID, message);
	}

	/**
	 * Sends a bitfield message to the designated peer. To be sent after the handshake message
	 * @param peerID ID of the peer to send the message to
	 * @param bitfield Bitfield of pieces possesed
	 */
	public static void bitfield(int peerID, byte[] bitfield)
	{
		Message message = new NormalMessage( (byte)5, bitfield );

		sendMessage(peerID, message);
	}

	/**
	 * Sends a request message to the designated peer along with the index of the desired piece
	 * @param peerID ID of the peer to send the message to
	 * @param index Index of the desired piece
	 */
	public static void request(int peerID, int index)
	{
		ByteBuffer buffer = ByteBuffer.allocate(4);
		buffer.putInt(index);
		Message message = new NormalMessage( (byte)6, buffer.array() );

		sendMessage(peerID, message);
	}

	/**
	 * Sends a piece of the file to the designated peer.
	 * @param peerID ID of the peer to send the message to
	 * @param index Index of the piece being sent
	 * @param payload Piece of the file
	 */
	public static void piece(int peerID, int index, byte[] payload)
	{
		ByteBuffer buffer = ByteBuffer.allocate(payload.length + 4);
		buffer.putInt(index);
		buffer.put(payload);
		Message message = new NormalMessage( (byte)7, buffer.array() );

		sendMessage(peerID, message);
	}
}
