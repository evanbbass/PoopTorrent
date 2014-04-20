package poopTorrent;

import java.nio.ByteBuffer;

/**
 * @author Derek Poirier
 * @author Evan Bass
 * @author Stewart Reive
 *
 * Message containing handshake information.
 */

public class HandshakeMessage extends Message
{
	public HandshakeMessage(int peerID)
	{
		ByteBuffer buffer = ByteBuffer.allocate(32);
		// First five bytes are "HELLO"
		buffer.put( ("HELLO").getBytes() );
		// 23 bytes of zeros (there's probably a better way to do this (actually I'm not even sure if this will work)
		buffer.put(ByteBuffer.allocate(23));
		// Last four are the peer ID (not sure which one, but it doesn't matter here)
		buffer.putInt(peerID);
		
		messageData = buffer.array();
	}
}
