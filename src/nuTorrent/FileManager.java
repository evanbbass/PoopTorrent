package nuTorrent;

import java.io.File;
import java.io.FileInputStream;
import java.util.ArrayList;

public class FileManager
{
	private ArrayList<Piece> pieces;
	private Bitfield bitfield;
	private Integer numPiecesDownloaded = 0;
	
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
			pieces = new ArrayList<Piece>();
			
			for (int i = 0; i < numPieces; i++)
				pieces.add(new Piece());
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
	
	public void receivePiece(int index, byte[] data)
	{
		synchronized (pieces.get(index)) {
			pieces.get(index).setPieceData(data);
			bitfield.receivePiece(index);
			for (int i = 0; i < PeerProcess.connections.size(); i++) {
				if (PeerProcess.connections.get(i).getConnectionEstablished()) {
					PeerProcess.connections.get(i).sendHave(index);
					synchronized (PeerProcess.connections.get(i).getInterestingPieces()) {
						PeerProcess.connections.get(i).getInterestingPieces().remove(new Integer(index));
					}
				}
			}
		}
	}


	public boolean isComplete()
	{
		return bitfield.isComplete();
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
	
	public Piece getPiece(int index) {
		if (index > pieces.size())
			throw new IllegalArgumentException("Error tried to retrieve a piece at index "
					+ index + ", which doesn't exist");
		
		return pieces.get(index);
	}
	
	public void incrementNumPiecesDownloaded() {
		numPiecesDownloaded++;
	}
	
	public Integer getNumPiecesDownloaded() {
		return numPiecesDownloaded;
	}
}
