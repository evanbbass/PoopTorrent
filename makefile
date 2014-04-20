bin:
	mkdir bin

build: bin
#	javac "src/poopTorrent/*.java" -d bin/
	find src -type f -name "*.java" -print | xargs javac -d bin/
clean:
	rm -rf bin/

run:
	java -classpath "./bin" poopTorrent/
