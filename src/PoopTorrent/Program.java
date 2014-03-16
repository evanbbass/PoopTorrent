package poopTorrent;

/**
 * Title: CNT 4007C Project
 * @author Derek Poirier
 * @author Evan Bass
 * @author Stewart Reive
 * 
 * Project: Implement BitTorrent-like Peer-to-Peer software
 */

import java.util.logging.Logger;

public class Program
{
	private static Config myConfig;
	private static Logger log;
	
	public static void main(String[] args)
	{
		System.out.println("Hooray for PoopTorrent! W00t!");

		try
		{
			myConfig = new Config();
		}
		catch (Exception e)
		{
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		log = Logger.getLogger("MyLog");
	}

}
