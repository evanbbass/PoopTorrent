build: bin update
	find src -type f -name "*.java" -print | xargs javac -d bin/

bin:
	mkdir bin

clean:
	rm -rf bin/

run:
	java -classpath "./bin" nuTorrent/PeerProcess

update:
	@git pull 1>/dev/null
