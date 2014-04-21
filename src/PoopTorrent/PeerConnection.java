package poopTorrent;

public class PeerConnection implements Runnable {
	private PeerInfo remotePeerInfo;
	
	public void run() {
		System.out.println("Starting a new peer connection from " + PeerProcess.myPeerId + 
				" to " + remotePeerInfo.getPeerID());
	}
	
	public PeerConnection(PeerInfo remotePeerInfo) {
		this.remotePeerInfo = remotePeerInfo;
	}
}
