package poopTorrent;

/**
 * Title: CNT 4007C Project
 * @author Derek Poirier
 * @author Evan Bass
 * @author Stewart Reive
 * 
 * Project: Implement BitTorrent-like Peer-to-Peer software
 */

import java.io.IOException;
import java.util.ArrayList;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.logging.FileHandler;
import java.util.logging.Logger;
import java.util.logging.SimpleFormatter;

public class PeerProcess
{
	static Config myConfig;
	static PeerConfig peers;
	static PeerInfo myPeerInfo;
	static Logger log;
	static int myPeerId;
	static ExecutorService es;
	static ArrayList<PeerConnection> connections;

	public static void main(String[] args)
	{
		System.out.println("Welcome to PoopTorrent! W00t!");
		
		connections = new ArrayList<PeerConnection>();
		
		if (args.length != 1)
			throw new IllegalArgumentException("You must specify the peer ID when starting a peer process");
		else
			myPeerId = Integer.parseInt(args[0]);
		
		System.out.println("Initializing config...");
		myConfig = new Config();
		System.out.println("Initializing Peer Info...");
		peers = new PeerConfig();
		
		for (int i = 0; i < peers.getPeers().size(); i++) {
			if (peers.getPeers().get(i).getPeerID() == myPeerId)
				myPeerInfo = peers.getPeers().get(i);
		}
		
		initLogger();
		
		es = Executors.newCachedThreadPool();
		
		// Listen for incoming connections
		es.execute(new PeerListener());
		
		// connect to all peers with an ID less than this one
		for (int i = 0; i < peers.getPeers().size(); i++) {
			if (peers.getPeers().get(i).getPeerID() < myPeerId)
				es.execute(new PeerConnection(peers.getPeers().get(i)));
		}
		
		// es.shutdownNow() once all peers have received the file ...
	}
	
	public static void initLogger() {
		System.out.println("Initializing logger...");
		FileHandler fh;
		log = Logger.getLogger(PeerProcess.class.getName());

		try
		{
			// This block configure the logger with handler and formatter  
			fh = new FileHandler("MyLogFile.log");  
			log.addHandler(fh);
			SimpleFormatter formatter = new SimpleFormatter();  
			fh.setFormatter(formatter);  

			// the following statement is used to log any messages  
			log.info("Initialized log");

		}
		catch (SecurityException e)
		{
			e.printStackTrace();  
		}
		catch (IOException e)
		{
			e.printStackTrace();  
		}

		log.info("Config file:\n" + myConfig.toString());
		log.info("Peer Info:\n" + peers.toString());
	}
}
