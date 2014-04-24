/**
 * 
 */
package nuTorrent;

/**
 * @author Derek Poirier
 * @author Evan Bass
 * @author Stewart Reive
 *
 * Abstract class representing a message to be sent between peers.
 */

public abstract class Message {

	byte[] messageData;

	public byte[] getBytes() {
		return messageData;
	}
	public void setBytes(byte[] messageData) {
		this.messageData = messageData;
	}

}
