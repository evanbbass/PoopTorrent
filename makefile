build: bin
	find src -type f -name "*.java" -print | xargs javac -d bin/

bin:
	mkdir bin

clean:
	rm -rf bin/

run:
	java -classpath "./bin" poopTorrent/
