package poopTorrent;

import java.util.ArrayList;

public class SeededFile
{
	private ArrayList<Piece> pieces;
	
	public SeededFile(String filepath)
	{
		pieces = new ArrayList<Piece>();
		
		
	}

	public ArrayList<Piece> getPieces() {
		return pieces;
	}
	public void setPieces(ArrayList<Piece> pieces) {
		this.pieces = pieces;
	}
}
