package poopTorrent;

public class Piece
{
	private byte[] pieceData;

	public Piece()
	{
		pieceData = new byte[PeerProcess.myConfig.PieceSize];
	}	

	public Piece(byte[] pieceData)
	{
		this.pieceData = pieceData;
	}

	public byte[] getPieceData() {
		return pieceData;
	}
	public void setPieceData(byte[] pieceData) {
		this.pieceData = pieceData;
	}
}
