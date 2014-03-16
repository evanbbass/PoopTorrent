package poopTorrent;

/**
 * @author Derek Poirier
 * @author Evan Bass
 * @author Stewart Reive
 * 
 * This class reads in the Common.cfg and(?) PeerInfo.cfg
 */

import java.io.BufferedReader;
import java.io.FileReader;
import java.util.StringTokenizer;

public class Config
{
	int NumberOfPreferredNeighbors;
	int UnchokingInterval;
	int OptimisticUnchokingInterval;
	String FileName;
	int FileSize;
	int PieceSize;
	
	public Config() throws Exception
	{
		String commonConfigPath = "C:\\Users\\Owner\\Documents\\CNT 4007C\\Project\\PoopTorrent\\Common.cfg";
		BufferedReader reader = new BufferedReader(new FileReader(commonConfigPath));
		
		String line = null;
		while ((line = reader.readLine()) != null)
		{

	    	StringTokenizer st = new StringTokenizer(line);

		    if (st.countTokens() != 2)
		    {
		    	throw new Exception("FUCK");
		    }
		    else
		    {
		    	String tok = st.nextToken();
		    	
		    	if (tok.equals("NumberOfPreferredNeighbors"))
		    	{
		    		NumberOfPreferredNeighbors = Integer.parseInt(st.nextToken());
		    	}

		    	if (tok.equals("UnchokingInterval"))
		    	{
		    		UnchokingInterval = Integer.parseInt(st.nextToken());
		    	}

		    	if (tok.equals("OptimisticUnchokingInterval"))
		    	{
		    		OptimisticUnchokingInterval = Integer.parseInt(st.nextToken());
		    	}

		    	if (tok.equals("FileName"))
		    	{
		    		FileName = st.nextToken();
		    	}

		    	if (tok.equals("FileSize"))
		    	{
		    		FileSize = Integer.parseInt(st.nextToken());
		    	}

		    	if (tok.equals("PieceSize"))
		    	{
		    		PieceSize = Integer.parseInt(st.nextToken());
		    	}
		    	
		    }
		}
	}
}
