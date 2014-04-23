package poopTorrent;

import java.io.IOException;
import java.net.ConnectException;
import java.net.Socket;
import java.nio.ByteBuffer;
import java.util.ArrayList;
import java.util.Random;

public class PeerConnection implements Runnable, Comparable<PeerConnection> {
	private PeerInfo remotePeerInfo = null;
	private Socket s = null;
	// List of pieces we are interested in from the remote peer. Can be updated by file manager
	// in the PeerProcess thread, so accesses must be synchronized
	private ArrayList<Integer> interestingPieces = new ArrayList<Integer>();
	private boolean connectionEstablished = false;
	private boolean areWeChoked = true; // if the remote peer is choking us this is true
										// until remote peer sends us an unchoked message
	private boolean programFinished = false;

	public void run() {
		
		// if we know who to connect to and we haven't connected yet, then connect now
		if (remotePeerInfo != null && s == null) { 
			for (;;) {
				try {
					s = new Socket(remotePeerInfo.getHostName(), remotePeerInfo.getListeningPort());
				} catch (ConnectException c) {
					PeerProcess.log.info("Outgoing Connection to peer " + remotePeerInfo.getPeerID() + 
							" failed");
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
		
		PeerProcess.log.info("Sending a handshake");
		
		// Send a handshake
		MessageUtils.handshake(s, PeerProcess.myPeerId);
		
		// wait until we receive one 
		for (;;) {
			Message handshakeMsg = MessageUtils.receiveMessage(s);
			
			if (handshakeMsg instanceof HandshakeMessage) {
				HandshakeMessage m = (HandshakeMessage)handshakeMsg;
				PeerProcess.log.info("Received a handshake message from " + m.getPeerID());
				
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
				PeerProcess.log.info("Expecting a handshake but received something else");
			}
		}
		
		PeerProcess.log.info("Sending a bitfield to " + remotePeerInfo.getPeerID());
		MessageUtils.bitfield(s, PeerProcess.fm.getBitfield().getBitfield());
		
		Message remoteBitfieldMsg = MessageUtils.receiveMessage(s);
		
		if (remoteBitfieldMsg instanceof NormalMessage)
		{
			if (((NormalMessage) remoteBitfieldMsg).getMessageType() == (byte)5) {
				PeerProcess.log.info("Received a bitfield from " + remotePeerInfo.getPeerID());
				remotePeerInfo.getBitfield().setBitfield(((NormalMessage) remoteBitfieldMsg).getMessagePayload());
			}
		} 
		
		// interestingPieces is a list of indices of pieces that we are interested in from remote peer
		synchronized (interestingPieces) {
			synchronized (PeerProcess.fm.getBitfield()) {
				interestingPieces = PeerProcess.fm.getBitfield().compareTo(
					remotePeerInfo.getBitfield().getBitfield());
				
				if (interestingPieces.size() > 0) {
					PeerProcess.log.info("Sending an interested message to " + remotePeerInfo.getPeerID());
					MessageUtils.interested(s);
				} else {
					PeerProcess.log.info("Sending a NOT interested message to " + remotePeerInfo.getPeerID());
					MessageUtils.notInterested(s);
				}
			}
		}
		
		Message doesHeLikeMe = MessageUtils.receiveMessage(s);
		
		if (doesHeLikeMe instanceof NormalMessage) {
			// If the remote peer is interested
			if (((NormalMessage) doesHeLikeMe).getMessageType() == (byte)2) {
				remotePeerInfo.interested(); // set remotePeerInfo to show they are interested
				PeerProcess.log.info("Peer " + remotePeerInfo.getPeerID() + " is interested in us <3");
			} else if (((NormalMessage) doesHeLikeMe).getMessageType() == (byte)3) {
				// if remote peer is not interested
				remotePeerInfo.notInterested();
				PeerProcess.log.info("Peer " + remotePeerInfo.getPeerID() + " is not interested in us :(");
			} else {
				PeerProcess.log.info("Peer " + remotePeerInfo.getPeerID() + " did not say whether or not" +
						" they were interested in me :(");
			}
		} 
		
		connectionEstablished = true;
		
		for (;;) {
			synchronized (interestingPieces) {
				// if the remote Peer has interesting pieces and we are not choked by them, request them
				if (interestingPieces.size() > 0 && !areWeChoked) {
					Random r = new Random();
					int requestedPieceIndex = Math.abs(r.nextInt()) % interestingPieces.size();
					PeerProcess.log.info("Requesting piece " + interestingPieces.get(requestedPieceIndex) + " from peer " 
							+ remotePeerInfo.getPeerID());
					MessageUtils.request(s, interestingPieces.get(requestedPieceIndex));
					try {
						Thread.sleep(2000);
					} catch (InterruptedException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
				}
			}
			
			Message incomingMsg = MessageUtils.receiveMessage(s);
			NormalMessage msg = null;
			if (incomingMsg instanceof NormalMessage) 
				msg = (NormalMessage) incomingMsg;
			
			// if we received a choke message
			if (msg.getMessageType() == (byte)0) {
				areWeChoked = true;
				PeerProcess.log.info("We received a choke msg from " + remotePeerInfo.getPeerID());
			}
			
			// if we received an unchoke message
			if (msg.getMessageType() == (byte)1) {
				areWeChoked = false;
				PeerProcess.log.info("We received an unchoke msg from " + remotePeerInfo.getPeerID());
			}
			
			// if we received an interested message
			if (msg.getMessageType() == (byte)2) {
				remotePeerInfo.interested();
				PeerProcess.log.info("We received an interested from " + remotePeerInfo.getPeerID());
			}
			
			// if we received a not-interested message
			if (msg.getMessageType() == (byte)3) {
				remotePeerInfo.notInterested();
				PeerProcess.log.info("We received a not interested msg from " + remotePeerInfo.getPeerID());
			}
			
			// if we received a have message
			if (msg.getMessageType() == (byte)4) {
				byte[] pay = msg.getMessagePayload();
				
				ByteBuffer buf = ByteBuffer.allocate(4);
				buf.put(pay);
				buf.position(0);
				
				int index = buf.getInt();
				// mark that the remote peer has that piece
				remotePeerInfo.getBitfield().receivePiece(index);
				PeerProcess.log.info("We received a have msg from " + remotePeerInfo.getPeerID()
						+ " for piece " + index);
				
				// we should see if we are now interested in remote peer if we weren't already
				synchronized(interestingPieces) {
					interestingPieces = PeerProcess.fm.getBitfield().compareTo(
						remotePeerInfo.getBitfield().getBitfield());

					if (interestingPieces.size() > 0) {
						remotePeerInfo.interested(); // we are interested in remote peer
						MessageUtils.interested(s); // let them know we're interested!
					} else {
						remotePeerInfo.notInterested(); // we aren't interested in remote peer
						MessageUtils.notInterested(s); // let them take the hint :P
					}
				}
			}
			
			// if we received a request message
			if (msg.getMessageType() == (byte)6) {
				// if we can upload to the remote peer
				if (!remotePeerInfo.isChoked()) {
					// figure out which piece they want 
					byte[] pay = msg.getMessagePayload();
					
					ByteBuffer buf = ByteBuffer.allocate(4);
					buf.put(pay);
					buf.position(0);
					
					int index = buf.getInt();
					
					// if we have that piece
					if (PeerProcess.fm.getBitfield().getBitfield()[index] == 1) {
						PeerProcess.log.info("We received a request for piece " + index + " from peer "
								+ remotePeerInfo.getPeerID() + " and are sending it.");
						
						// send it to the remote peer
						
						byte[] data = PeerProcess.fm.getPieces().get(index).getPieceData();
						MessageUtils.piece(s, index, data);
					} else {
						PeerProcess.log.info("Error: peer " + remotePeerInfo.getPeerID() + 
								" requested piece " + index + " which we don't have.");
					}
				} else {
					PeerProcess.log.info("Error: we received a request message from " + 
							remotePeerInfo.getPeerID() + " but they were choked");	
				}
			}
			
			// if we received a piece message
			if (msg.getMessageType() == (byte)7) {
				byte[] pay = msg.getMessagePayload();
				
				ByteBuffer buf = ByteBuffer.allocate(pay.length);
				buf.put(pay);
				buf.position(0);
				
				int index = buf.getInt();
				byte[] data = new byte[pay.length - 4];
				
				for (int i = 0; i < pay.length - 4; i++) {
					data[i] = buf.get();
				}
				
				PeerProcess.fm.receivePiece(index, data);
				
				//remotePeerInfo.setDownloadRate(downloadRate)
				
				PeerProcess.log.info("Received piece " + index + " from " + remotePeerInfo.getPeerID());
			}
			
			if (programFinished)
				break;
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

	public int compareTo(PeerConnection other) {
		if (other == null)
			return -1;
		if (this == null)
			return 1;
		
		// have it sort so that the highest download rate is first
		return other.remotePeerInfo.getDownloadRate() - this.remotePeerInfo.getDownloadRate();
	}
	
	public Socket getSocket() {
		return s;
	}
	
	public boolean getConnectionEstablished() {
		return connectionEstablished;
	}
	
	public void setProgramFinished(boolean b) {
		programFinished = b;
	}
	
	public void sendChoke() {
		MessageUtils.choke(s);
	}
	
	public void sendUnchoke() {
		MessageUtils.unchoke(s);
	}

	public void sendHave(int index) {
		MessageUtils.have(s, index);
	}
	
	public class Listener extends Thread {
		PeerConnection pc;
		
		public void run() {
			
		}
		
		public Listener(PeerConnection pc) {
			super();
			this.pc = pc;
		}
	}
}
