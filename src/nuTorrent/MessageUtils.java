package nuTorrent;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.Socket;
import java.nio.ByteBuffer;

public class MessageUtils
{
	/**
	 * Sends message to the designated peer.
	 * @param socket ID of the peer to send the message to
	 * @param message Message with payload to send
	 */
	private static void sendMessage(DataOutputStream dos, Message message)
	{
		try {
			dos.write(message.getBytes(), 0, message.getBytes().length);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	/**
	 * Generates a message based on the data received
	 * @param messageData The byte array received
	 * @return The generated message
	 */
	public static Message receiveMessage(DataInputStream dis)
	{
		//synchronized (s) {
			try {
			    byte[] header = new byte[5];
			    dis.readFully(header);
			    
				if ( new String(header).equals("HELLO") )
				{
					ByteBuffer buffer = ByteBuffer.allocate(27);
					dis.readFully(buffer.array());
					
					int peerID = buffer.getInt(23);
		
					return new HandshakeMessage(peerID);
				}
				else
				{
					ByteBuffer buffer = ByteBuffer.allocate(5);
					buffer.put(header);
					buffer.position(0); // reset position
					int length = buffer.getInt();
					byte messageType = buffer.get();
					
					// length includes type, so the remaining bytes are length - 1
					byte[] payload = new byte[length - 1];
					dis.readFully(payload);
					
					return new NormalMessage(messageType, payload);
				}
			} catch (IOException e) {
				e.printStackTrace();
				PeerProcess.log.info("We had an error receiving a msg");
				return null;
			}
		//}
	}

	/**
	 * Creates and sends a handshake message to the designated peer.
	 * @param socket Socket of the peer FUCK MY LIFE
	 * @param peerID ID of the sender to put in the message
	 */
	public static void handshake(DataOutputStream dos, int peerID)
	{
		Message message = new HandshakeMessage( peerID );

		sendMessage(dos, message);
	}

	/**
	 * Creates and sends a choke message to the designated peer.
	 * @param socket ID of the peer to send the message to
	 */
	public static void choke(DataOutputStream dos)
	{
		Message message = new NormalMessage( (byte)0, new byte[0] );

		sendMessage(dos, message);
	}

	/**
	 * Creates and sends an unchoke message to the designated peer.
	 * @param socket ID of the peer to send the message to
	 */
	public static void unchoke(DataOutputStream dos)
	{
		Message message = new NormalMessage( (byte)1, new byte[0] );

		sendMessage(dos, message);
	}

	/**
	 * Creates and sends an interested message to the designated peer.
	 * @param socket ID of the peer to send the message to
	 */
	public static void interested(DataOutputStream dos)
	{
		Message message = new NormalMessage( (byte)2, new byte[0] );

		sendMessage(dos, message);
	}

	/**
	 * Creates and sends a not interested message to the designated peer.
	 * @param socket ID of the peer to send the message to
	 */
	public static void notInterested(DataOutputStream dos)
	{
		Message message = new NormalMessage( (byte)3, new byte[0] );

		sendMessage(dos, message);
	}

	/**
	 * Sends a have message to the designated peer along with the index of the possesed piece
	 * @param socket ID of the peer to send the message to
	 * @param index Index of the possesed piece
	 */
	public static void have(DataOutputStream dos, int index)
	{
		ByteBuffer buffer = ByteBuffer.allocate(4);
		buffer.putInt(index);
		Message message = new NormalMessage( (byte)4, buffer.array() );

		sendMessage(dos, message);
	}

	/**
	 * Sends a bitfield message to the designated peer. To be sent after the handshake message
	 * @param socket ID of the peer to send the message to
	 * @param bitfield Bitfield of pieces possesed
	 */
	public static void bitfield(DataOutputStream dos, byte[] bitfield)
	{
		Message message = new NormalMessage( (byte)5, bitfield );

		sendMessage(dos, message);
	}

	/**
	 * Sends a request message to the designated peer along with the index of the desired piece
	 * @param socket ID of the peer to send the message to
	 * @param index Index of the desired piece
	 */
	public static void request(DataOutputStream dos, int index)
	{
		// synchronize our access to the file pieces
		//synchronized(PeerProcess.fm.getPiece(index)) {
			// check to see if another PeerConnection already requested the file piece
			if (!PeerProcess.fm.getPiece(index).wasRequested()) {
				// if it hasn't been requested, let's request it!
				PeerProcess.fm.getPiece(index).setRequested();
				
				// let other threads know that we have requested it by removing from their interesting pieces
				for (int i = 0; i < PeerProcess.connections.size(); i++){
					//synchronized(PeerProcess.connections.get(i).getInterestingPieces()) {
						PeerProcess.connections.get(i).getInterestingPieces().remove(new Integer(index));
					//}
				}
				
				ByteBuffer buffer = ByteBuffer.allocate(4);
				buffer.putInt(index);
				Message message = new NormalMessage( (byte)6, buffer.array() );
				
				// send the request
				sendMessage(dos, message);
			}
		//}
	}

	/**
	 * Sends a piece of the file to the designated peer.
	 * @param socket ID of the peer to send the message to
	 * @param index Index of the piece being sent
	 * @param payload Piece of the file
	 */
	public static void piece(DataOutputStream dos, int index, byte[] payload)
	{
		ByteBuffer buffer = ByteBuffer.allocate(payload.length + 4);
		buffer.putInt(index);
		buffer.put(payload);
		Message message = new NormalMessage( (byte)7, buffer.array() );

		sendMessage(dos, message);
	}
}
