bin:
	mkdir bin

build: bin
	javac src/poopTorrent/*.java -d bin/

clean:
	rm -rf bin/

run:
	java -classpath "./bin" poopTorrent/