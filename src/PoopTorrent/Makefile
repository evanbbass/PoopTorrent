JFLAGS = -g
JC = javac
#.SUFFIXES: .java .class
#.java.class:
#	$(JC) $(JFLAGS) $*.java

default: Config.class FileBits.class Peer.class PeerConfig.class PeerInfo.class RemotePeerInfo.class StartRemotePeers.class

Config.class: Config.java
	$(JC) $(JFLAGS) Config.java

FileBits.class: FileBits.java
	$(JC) $(JFLAGS) FileBits.java

Peer.class: Peer.java
	$(JC) $(JFLAGS) Peer.java

PeerConfig.class: PeerConfig.java
	$(JC) $(JFLAGS) PeerConfig.java

PeerInfo.class: PeerInfo.java
	$(JC) $(JFLAGS) PeerInfo.java

RemotePeerInfo.class: RemotePeerInfo.java
	$(JC) $(JFLAGS) RemotePeerInfo.java

StartRemotePeers.class: StartRemotePeers.java
	$(JC) $(JFLAGS) StartRemotePeers.java			

clean:
	$(RM) *.class
