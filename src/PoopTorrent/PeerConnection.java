package poopTorrent;

import java.io.IOException;
import java.net.ConnectException;
import java.net.InetSocketAddress;
import java.net.Socket;
import java.net.SocketAddress;
import java.net.SocketTimeoutException;

public class PeerConnection implements Runnable {
	private PeerInfo remotePeerInfo = null;
	private Socket s = null;
	
	public void run() {
		System.out.println("Starting a new peer connection from " + PeerProcess.myPeerId + 
				" to " + remotePeerInfo.getPeerID());
		
		// if we know who to connect to and we haven't connected yet, then connect now
		if (remotePeerInfo != null && s == null) { 
			//SocketAddress remoteAddress = 
				//	new InetSocketAddress(remotePeerInfo.getHostName(), remotePeerInfo.getListeningPort());
					//new InetSocketAddress("google.com", 80);
			for (;;) {
				try {
					s = new Socket(remotePeerInfo.getHostName(), remotePeerInfo.getListeningPort());
				} catch (ConnectException c) {
					PeerProcess.log.info("Outgoing Connection to peer " + remotePeerInfo.getPeerID() + 
							" failed");
					try {
						Thread.sleep(5000);
					} catch (InterruptedException e) {
						e.printStackTrace();
					}
					continue;
				} catch (IOException e) {
					e.printStackTrace();
				}
				
				if (s.isConnected())
					PeerProcess.log.info("Outgoing Connection to peer " + remotePeerInfo.getPeerID() + 
							" succeeded :)");
				break;
			}
		}
		
		// TO-DO: WRITE THE ENTIRE STATE MACHINE FOR THE BIT-TORRENT PROTOCOL (NO BIG DEAL RIGHT???)
		
		
		try {
			s.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	public PeerConnection(PeerInfo remotePeerInfo) {
		this.remotePeerInfo = remotePeerInfo;
	}
	
	public PeerConnection(Socket s) {
		this.s = s;
	}
}
