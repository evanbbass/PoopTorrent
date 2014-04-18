package poopTorrent;

/**
 * @author Derek Poirier
 * @author Evan Bass
 * @author Stewart Reive
 * 
 * This class contains peer information
 */

public class PeerInfo
{
	int peerID;
	String hostName;
	int listeningPort;
	boolean hasFile;

	/**
	 * Default constructor for PeerInfo. Sets everything to 
	 * indicate that it is not initialized.
	 */
	public PeerInfo()
	{
		peerID = -1;
		hostName = null;
		listeningPort = -1;
		hasFile = false;
	}
	
	/**
	 * Constructor for PeerInfo. Sets the properties accordingly.
	 * @param peerID Number representing the peer
	 * @param hostName IP address or DNS host name of the peer
	 * @param listeningPort Port number to communicate with
	 * @param hasFile Whether or not the peer has the full file
	 */
	public PeerInfo(int peerID, String hostName, int listeningPort, boolean hasFile)
	{
		this.peerID = peerID;
		this.hostName = hostName;
		this.listeningPort = listeningPort;
		this.hasFile = hasFile;
	}

	public int getPeerID()
	{
		return peerID;
	}
	public void setPeerID(int peerID)
	{
		this.peerID = peerID;
	}

	public String getHostName()
	{
		return hostName;
	}
	public void setHostName(String hostName)
	{
		this.hostName = hostName;
	}

	public int getListeningPort()
	{
		return listeningPort;
	}
	public void setListeningPort(int listeningPort)
	{
		this.listeningPort = listeningPort;
	}

	public boolean hasFile()
	{
		return hasFile;
	}
	public void setHasFile(boolean hasFile)
	{
		this.hasFile = hasFile;
	}

	public String toString()
	{
		String str = "" + peerID + " " +
						hostName + " " +
						listeningPort + " ";
		if(hasFile)
			str += "1";
		else
			str += "0";

		return str;
	}
}
