build: 
	javac s40165942_detector.java

run: build
	java s40165942_detector $(FILE1) $(FILE2)
