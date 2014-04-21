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
import java.util.logging.FileHandler;
import java.util.logging.Logger;
import java.util.logging.SimpleFormatter;

public class Peer
{
	static Config myConfig;
	static PeerConfig peers;
	static Logger log;

	public static void main(String[] args)
	{
		System.out.println("Welcome to PoopTorrent! W00t!");
		
		// Testing Messages
//		Message message = new HandshakeMessage(1001);
//		message.getBytes();
//		message = new NormalMessage((byte)0, "asdfj".getBytes());
//		message.getBytes();
		
		FileHandler fh;
		System.out.println("Initializing config...");
		myConfig = new Config();
		System.out.println("Initializing Peer Info...");
		peers = new PeerConfig();
		System.out.println("Initializing logger...");
		log = Logger.getLogger(Peer.class.getName());

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
