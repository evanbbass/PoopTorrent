package poopTorrent;

import java.nio.ByteBuffer;

/**
 * @author Derek Poirier
 * @author Evan Bass
 * @author Stewart Reive
 *
 * Message containing standard message information.
 */

public class NormalMessage extends Message
{
	private byte messageType;
	private byte[] messagePayload;

	/**
	 * Standard message containing the payload to be sent.
	 * @param messageType One byte representing the type of message (choke, unchoke, etc.). See project description.
	 * @param messagePayload Payload of the message. Length is read from this
	 */
	public NormalMessage(byte messageType, byte[] messagePayload)
	{
		int capacity = messagePayload.length + 1;
		ByteBuffer buffer = ByteBuffer.allocate(capacity + 4);
		// First four bytes are the length (not including these four bytes)
		buffer.putInt(capacity);
		// Single-byte message type (choke, unchoke, etc.)
		buffer.put(messageType);
		// Payload
		buffer.put(messagePayload);
		
		this.messageData = buffer.array();
		this.messageType = messageType;
		this.messagePayload = messagePayload;
	}

	public byte getMessageType() {
		return messageType;
	}
	public byte[] getMessagePayload() {
		return messagePayload;
	}
}
