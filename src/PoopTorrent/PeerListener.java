package poopTorrent;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

public class PeerListener implements Runnable {
	public void run() {
		System.out.println("Peer Listener started");
		
		ServerSocket s = null;
		
		try {
			// Listen on the port specified by this PeerProcess's peer info
			s = new ServerSocket(PeerProcess.myPeerInfo.getListeningPort());
			
			// Listen for incoming connections and spawn new threads when we receive them
			for (;;) {
				Socket incoming = s.accept();
				PeerProcess.es.execute(new PeerConnection(incoming));
			}
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			try {
				if (s != null)
					s.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}
}
