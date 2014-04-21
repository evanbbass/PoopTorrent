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
	private int peerID;
	private String hostName;
	private int listeningPort;

	private FileBits bitfield;
	private double downloadRate;
	private boolean interested;
	private boolean choked;

	/**
	 * Default constructor for PeerInfo. Sets everything to 
	 * indicate that it is not initialized.
	 */
	public PeerInfo()
	{
		this(-1, null, -1, false);
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
		this.interested = false;
		this.downloadRate = 0.0;
		this.bitfield = new FileBits(PeerProcess.myConfig.FileSize, hasFile);
		this.choked = true;
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

	public FileBits getBitfield() {
		return bitfield;
	}
	public void setBitfield(FileBits bitfield) {
		this.bitfield = bitfield;
	}
	public boolean hasFile() {
		return bitfield.isComplete();
	}

	public double getDownloadRate() {
		return downloadRate;
	}
	public void setDownloadRate(double downloadRate) {
		this.downloadRate = downloadRate;
	}

	public boolean isInterested() {
		return interested;
	}
	public void interested() {
		this.interested = true;
	}
	public void notInterested() {
		this.interested = false;
	}

	public boolean isChoked() {
		return choked;
	}
	public void choke() {
		this.choked = true;
	}
	public void unchoke() {
		this.choked = false;
	}

	public String toString()
	{
		String str = "" + peerID + " " +
						hostName + " " +
						listeningPort + " ";
		if( hasFile() )
			str += "1";
		else
			str += "0";

		return str;
	}
}
