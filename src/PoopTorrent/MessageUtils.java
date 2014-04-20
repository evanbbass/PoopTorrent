package poopTorrent;

public class MessageUtils
{
	public static void choke(int peerID)
	{
		Message message = new NormalMessage( (byte)0, new byte[0] );

		
	}
	
	public static void unchoke(int peerID)
	{
		Message message = new NormalMessage( (byte)1, new byte[0] );

		
	}
}
