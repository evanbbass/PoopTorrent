package poopTorrent;

import java.util.ArrayList;

public class FileManager
{
	private ArrayList<Piece> pieces;
	
	public FileManager(String filepath)
	{
		pieces = new ArrayList<Piece>();
		
		
	}

	public Bitfield getBitfield()
	{
		

		return null;
	}

	public ArrayList<Piece> getPieces() {
		return pieces;
	}
	public void setPieces(ArrayList<Piece> pieces) {
		this.pieces = pieces;
	}
}
