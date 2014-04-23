package poopTorrent;

import java.io.File;
import java.io.FileInputStream;
import java.util.ArrayList;

public class FileManager
{
	private ArrayList<Piece> pieces;
	private Bitfield bitfield;
	
	public FileManager()
	{
		int numPieces = PeerProcess.myConfig.FileSize / PeerProcess.myConfig.PieceSize + 
				((PeerProcess.myConfig.FileSize % PeerProcess.myConfig.PieceSize != 0) ? 1 : 0);

		bitfield = new Bitfield(numPieces, PeerProcess.myPeerInfo.hasFile());

		if (PeerProcess.myPeerInfo.hasFile())
		{
			pieces = new ArrayList<Piece>();
			readFile(numPieces);
		}
		else
		{
			pieces = new ArrayList<Piece>(numPieces);
		}
	}


	private void readFile(int numPieces) {
		FileInputStream fis = null;
		byte[] data = new byte[0];
		try {
			fis = new FileInputStream(new File(PeerProcess.myConfig.FileName));
			data = new byte[PeerProcess.myConfig.FileSize];
			fis.read(data);

		} catch (Exception e) {
			e.printStackTrace();
		}
		finally {
			try {
				fis.close();
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		
		for (int i = 0; i < numPieces; i++)
		{
			byte[] pieceData = new byte[PeerProcess.myConfig.PieceSize];
			for (int j = 0; j < PeerProcess.myConfig.PieceSize; j++)
			{
				pieceData[j] = (i * pieceData.length + j >= data.length
									? (byte)0 : data[i * pieceData.length + j]);
			}
			pieces.add(new Piece(pieceData));
		}
	}


	public ArrayList<Piece> getPieces() {
		return pieces;
	}
	public void setPieces(ArrayList<Piece> pieces) {
		this.pieces = pieces;
	}

	public Bitfield getBitfield() {
		return bitfield;
	}
	public void setBitfield(Bitfield bitfield) {
		this.bitfield = bitfield;
	}
}
