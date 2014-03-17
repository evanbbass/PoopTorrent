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
	/*
	public PeerInfo(int peerID, String hostName, int listeningPort, boolean hasFile)
	{
		this.peerID = peerID;
		this.hostName = hostName;
		this.listeningPort = listeningPort;
		this.hasFile = hasFile;
	}
	take this out eventually
	*/
	// Getters/setters
	/**
	 * Gets the peer's ID
	 * @return the peerID
	 */
	public int getPeerID()
	{
		return peerID;
	}

	/**
	 * Sets the peer's ID
	 * @param peerID the peerID to set
	 */
	public void setPeerID(int peerID)
	{
		this.peerID = peerID;
	}

	/**
	 * Gets the host name
	 * @return the hostName
	 */
	public String getHostName()
	{
		return hostName;
	}

	/*
	public String getHostName()
	{
		return hostName;
	}
	*/
	//take the above out 
	
	/**
	 * Sets the host file
	 * @param hostName the hostName to set
	 */
	public void setHostName(String hostName)
	{
		this.hostName = hostName;
	}

	/**
	 * Gets the listening port
	 * @return the listeningPort
	 */
	public int getListeningPort()
	{
		return listeningPort;
	}

	/**
	 * Sets the listening port
	 * @param listeningPort the listeningPort to set
	 */
	public void setListeningPort(int listeningPort)
	{
		this.listeningPort = listeningPort;
	}

	/**
	 * Gets whether the peer has the complete file
	 * @return the hasFile
	 */
	public boolean hasFile()
	{
		return hasFile;
	}

	/**
	 * Sets whether the peer has the file
	 * @param hasFile the hasFile to set
	 */
	public void setHasFile(boolean hasFile)
	{
		this.hasFile = hasFile;
	}

	/*
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
	take this out eventually
	*/
	// toString method
	/**
	 * @return A String representation of the info.
	 * Should look the same as the PeerInfo.cfg
	 */
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
