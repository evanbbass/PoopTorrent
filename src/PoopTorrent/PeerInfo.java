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

	public PeerInfo()
	{
		peerID = -1;
		hostName = null;
		listeningPort = -1;
		hasFile = false;
	}
	
	public PeerInfo(int peerID, String hostName, int listeningPort, boolean hasFile)
	{
		this.peerID = peerID;
		this.hostName = hostName;
		this.listeningPort = listeningPort;
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
