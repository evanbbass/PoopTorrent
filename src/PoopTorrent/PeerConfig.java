package poopTorrent;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.StringTokenizer;

/**
 * @author Derek Poirier
 * @author Evan Bass
 * @author Stewart Reive
 * 
 * This class reads in the PeerInfo.cfg
 */

public class PeerConfig
{
	ArrayList<PeerInfo> peers = new ArrayList<PeerInfo>();

	public PeerConfig()
	{
		this("PeerInfo.cfg");
	}
	
	public PeerConfig(String peerInfoPath)
	{
		BufferedReader reader = null;

		try
		{
			reader = new BufferedReader(new FileReader(peerInfoPath));
			String line = null;

			while ((line = reader.readLine()) != null)
			{
		    	StringTokenizer st = new StringTokenizer(line);

		    	// Check to make sure each line only has two items
			    if (st.countTokens() != 4)
			    {
			    	//TODO Make this more specific pls.
			    	throw new IOException("Wrong number of tokens");
			    }
			    else
			    {
			    	int peerID = Integer.parseInt(st.nextToken());
			    	String hostName = st.nextToken();
			    	int listeningPort = Integer.parseInt(st.nextToken());
			    	boolean hasFile = st.nextToken().equals("1");

			    	peers.add(new PeerInfo(peerID, hostName, listeningPort, hasFile));
			    }
			}
		}
		catch (NumberFormatException e)
		{
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		catch (IOException e)
		{
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		finally
		{
			try {
				reader.close();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}
	
	public String toString()
	{
		String str = "";

		for(PeerInfo info : peers)
		{
			str += info.toString() + "\n";
		}

		return str;
	}
}
