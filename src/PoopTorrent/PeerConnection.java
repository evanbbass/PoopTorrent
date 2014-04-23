package poopTorrent;

import java.io.IOException;
import java.net.ConnectException;
import java.net.Socket;
import java.util.ArrayList;

public class PeerConnection implements Runnable {
	private PeerInfo remotePeerInfo = null;
	private Socket s = null;
	private ArrayList<Integer> interestingPieces = new ArrayList<Integer>();

	public void run() {
		
		// if we know who to connect to and we haven't connected yet, then connect now
		if (remotePeerInfo != null && s == null) { 
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
		
		PeerProcess.log.info("We are sending a handshake");
		
		// Send a handshake
		MessageUtils.handshake(s, PeerProcess.myPeerId);
		
		// wait until we receive one 
		for (;;) {
			Message handshakeMsg = MessageUtils.receiveMessage(s);
			
			if (handshakeMsg instanceof HandshakeMessage) {
				HandshakeMessage m = (HandshakeMessage)handshakeMsg;
				PeerProcess.log.info("We received a handshake message from " + m.getPeerID());
				
				// if we made the outbound connection and we knew who our peer was
				// we expect their peerID to be the one we have on record
				if (remotePeerInfo != null) {
					if (remotePeerInfo.getPeerID() == m.getPeerID())
						PeerProcess.log.info("We were expecting a message from " + m.getPeerID());
					else {
						PeerProcess.log.info("We were not expecting a handshake message from " + m.getPeerID());
						continue;
					}
				} else { 
					// figure out who the remote peer is based on the peer ID we received
					for (int i = 0; i < PeerProcess.peers.getPeers().size(); i++) {
						if (PeerProcess.peers.getPeers().get(i).getPeerID() == m.getPeerID()) {
							remotePeerInfo = PeerProcess.peers.getPeers().get(i);
						}
					}
				}
				break;
			} else {
				PeerProcess.log.info("We were expecting a handshake but received something else");
			}
		}
		
		PeerProcess.log.info("We are sending a bitfield to " + remotePeerInfo.getPeerID());
		MessageUtils.bitfield(s, PeerProcess.fm.getBitfield().getBitfield());
		
		Message remoteBitfieldMsg = MessageUtils.receiveMessage(s);
		
		if (remoteBitfieldMsg instanceof NormalMessage)
		{
			if (((NormalMessage) remoteBitfieldMsg).getMessageType() == (byte)5)
				PeerProcess.log.info("We received a bitfield from " + remotePeerInfo.getPeerID());
		} 
		
		// interestingPieces is a list of indices of pieces that we are interested in from remote peer
		interestingPieces = PeerProcess.fm.getBitfield().compareTo(
				remotePeerInfo.getBitfield().getBitfield());
		
		if (interestingPieces.size() > 0) {
			MessageUtils.interested(s);
		} else {
			MessageUtils.notInterested(s);
		}
		
		Message doesHeLikeMe = MessageUtils.receiveMessage(s);
		
		if (doesHeLikeMe instanceof NormalMessage) {
			// If the remote peer is interested
			if (((NormalMessage) doesHeLikeMe).getMessageType() == (byte)2) {
				remotePeerInfo.interested(); // set remotePeerInfo to show they are interested
			} else if (((NormalMessage) doesHeLikeMe).getMessageType() == (byte)3) {
				// if remote peer is not interested
				remotePeerInfo.notInterested();
			} else {
				PeerProcess.log.info("Peer " + remotePeerInfo.getPeerID() + " did not say whether or not" +
						" they were interested in me :(");
			}
		} 
		
		try {
			s.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	public PeerConnection(PeerInfo remotePeerInfo) {
		this.remotePeerInfo = remotePeerInfo;
		PeerProcess.connections.add(this);
	}
	
	public PeerConnection(Socket s) {
		this.s = s;
		PeerProcess.connections.add(this);
		PeerProcess.log.info("Incoming connection received :)");
	}

	public PeerInfo getRemotePeerInfo() {
		return remotePeerInfo;
	}
	
	public ArrayList<Integer> getInterestingPieces() {
		return interestingPieces;
	}

}
