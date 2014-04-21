package poopTorrent;

/**
 * @author Derek Poirier
 * @author Evan Bass
 * @author Stewart Reive
 * 
 * Contains a byte array 
 */

public class FileBits
{
	private byte[] bitfield;
	private int numBits;
	//private boolean complete;

	public FileBits(int numBits)
	{
		this(numBits, false);
	}

	public FileBits(int numBits, boolean hasFile)
	{
		createBitfield(numBits, hasFile);
		this.numBits = numBits;
		//complete = hasFile;
	}

	// Getters/Setters
	/**
	 * Getter for the bitfield
	 * @return the bitfield
	 */
	public byte[] getBitfield()
	{
		return bitfield;
	}

	/**
	 * Setter for the bitfield
	 * @param bitfield the bitfield to set
	 */
	public void setBitfield(byte[] bitfield)
	{
		this.bitfield = bitfield;
	}

	/**
	 * Getter for the number of bits
	 * @return the numBits
	 */
	public int getNumBits()
	{
		return numBits;
	}

	/**
	 * Setter for the number of bits
	 * @param numBits the numBits to set
	 */
	public void setNumBits(int numBits)
	{
		this.numBits = numBits;
	}

	/**
	 * Gets whether or not it has the entire file
	 * @return the hasEntireFile
	 */
	public boolean isComplete()
	{
		boolean complete = true;
		
		for(byte b : bitfield)
		{
			if (b == 0)
				complete = false;
		}
		
		return complete;
	}

	/**
	 * Initializes the bitfield property.
	 * @param size Size of the bitfield to create
	 * @param hasFile 
	 */
	private void createBitfield(int size, boolean hasFile)
	{
		bitfield = new byte[size + 1];
		
		for(int i = 0; i < bitfield.length; i++)
		{
			bitfield[i] = (byte) (hasFile ? 1 : 0);
		}
	}
}
