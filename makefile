JFLAGS = -g
JC = javac
.SUFFIXES: .java .class
.java.class:
        $(JC) $(JFLAGS) $*.java

CLASSES = \
        Config.java \
        FileBits.java \
        Peer.java \
        PeerConfig.java \
        PeerInfo.java \
        RemotePeerInfo.java \
        StartRemotePeers.java

default: classes

classes: $(CLASSES:.java=.class)

clean:
        $(RM) *.class