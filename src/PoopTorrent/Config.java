package poopTorrent;

/**
 * @author Derek Poirier
 * @author Evan Bass
 * @author Stewart Reive
 * 
 * This class reads in the Common.cfg
 */

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.StringTokenizer;

public class Config
{
	int NumberOfPreferredNeighbors;
	int UnchokingInterval;
	int OptimisticUnchokingInterval;
	String FileName;
	int FileSize;
	int PieceSize;
	
	/**
	 * Initializes the configuration based on the information in the
	 * Common.cfg file. For now the path is hard coded.
	 */
	public Config()
	{
		this("Common.cfg");
	}
	
	/**
	 * Overloaded constructor that takes in the file path of the
	 * Common.cfg file.
	 * @param commonConfigPath The path to the Common.cfg file.
	 */
	public Config(String commonConfigPath)
	{
		//String commonConfigPath = "C:\\Users\\Owner\\Documents\\CNT 4007C\\Project\\PoopTorrent\\Common.cfg";
		BufferedReader reader = null;

		try
		{
			//we use a bufferedreader instead of a scanner to parse the file common.cfg 
			reader = new BufferedReader(new FileReader(commonConfigPath));
			String line = null;

			while ((line = reader.readLine()) != null)
			{
		    	StringTokenizer st = new StringTokenizer(line);

		    	// Check to make sure each line only has two items
			    if (st.countTokens() != 2)
			    {
			    	//TODO Make this more specific pls.
			    	throw new IOException("Wrong number of tokens");
			    }
			    else
			    {
			    	String tok = st.nextToken();
			    	
			    	if (tok.equals("NumberOfPreferredNeighbors"))
			    	{
			    		// Read in NumberOfPreferredNeighbors
			    		NumberOfPreferredNeighbors = Integer.parseInt(st.nextToken());
			    	}
	
			    	if (tok.equals("UnchokingInterval"))
			    	{
			    		// Read in UnchokingInterval
			    		UnchokingInterval = Integer.parseInt(st.nextToken());
			    	}
	
			    	if (tok.equals("OptimisticUnchokingInterval"))
			    	{
			    		// Read in OptimisticUnchokingInterval
			    		OptimisticUnchokingInterval = Integer.parseInt(st.nextToken());
			    	}
	
			    	if (tok.equals("FileName"))
			    	{
			    		// Read in FileName
			    		FileName = st.nextToken();
			    	}
	
			    	if (tok.equals("FileSize"))
			    	{
			    		// Read in FileSize
			    		FileSize = Integer.parseInt(st.nextToken());
			    	}
	
			    	if (tok.equals("PieceSize"))
			    	{
			    		// Read in PieceSize
			    		PieceSize = Integer.parseInt(st.nextToken());
			    	}
			    }
			}
		}
		catch (NumberFormatException e)
		{
			e.printStackTrace();
		}
		catch (IOException e)
		{
			e.printStackTrace();
		}
		finally
		{
			try
			{
				reader.close();
			}
			catch (IOException e)
			{
				e.printStackTrace();
			}
		}
	}

	public int getNumberOfPreferredNeighbors()
	{
		return NumberOfPreferredNeighbors;
	}
	public void setNumberOfPreferredNeighbors(int numberOfPreferredNeighbors)
	{
		NumberOfPreferredNeighbors = numberOfPreferredNeighbors;
	}

	public int getUnchokingInterval()
	{
		return UnchokingInterval;
	}
	public void setUnchokingInterval(int unchokingInterval)
	{
		UnchokingInterval = unchokingInterval;
	}

	public int getOptimisticUnchokingInterval()
	{
		return OptimisticUnchokingInterval;
	}
	public void setOptimisticUnchokingInterval(int optimisticUnchokingInterval)
	{
		OptimisticUnchokingInterval = optimisticUnchokingInterval;
	}

	public String getFileName()
	{
		return FileName;
	}
	public void setFileName(String fileName)
	{
		FileName = fileName;
	}

	public int getFileSize()
	{
		return FileSize;
	}
	public void setFileSize(int fileSize)
	{
		FileSize = fileSize;
	}

	public int getPieceSize()
	{
		return PieceSize;
	}
	public void setPieceSize(int pieceSize)
	{
		PieceSize = pieceSize;
	}

	public String toString()
	{
		/*
			here is where we are returning our variables
			from the tokens we received when parsing common.cfg
		*/
		return "NumberOfPreferredNeighbors " + NumberOfPreferredNeighbors + "\n" +
				"UnchokingInterval " + UnchokingInterval + "\n" +
				"OptimisticUnchokingInterval " + OptimisticUnchokingInterval + "\n" +
				"FileName " + FileName + "\n" +
				"FileSize " + FileSize + "\n" +
				"PieceSize " + PieceSize + "\n";
	}
}
