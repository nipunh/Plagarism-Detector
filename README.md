# Plagarism-Detector

The project is implemented using Java as a programming language. The plagiarism detector helps to determine if contents of two files are matching upto an extent or not. 

Steps:

1. Clasifying Documnet:
- The given document are classified into two types i.e Code file(Containing code) or Normal Text file based on the content of file.
- If common coding keywords are detected in the file it is classified as code file or else it is considered as a normal text file.

2. Pre processing of data:
- Based on type of file classified preprocessing is performed.
- If the given file is Code file, brackets and other mathematical keywords are removed.
- If the given file is Normal Text file, the whole string is converted into lower case, numbers, brackets and website refrences are removed to make comparison easier and shorter.
- Then the common stop words used in enlish language are removed from both the files to reduce the repetetion of less significant words.

3. Frequency Map:
- I have implement a frequency map for each document to store frequency of common words.
- The algorithm creates two seperated hash maps and store frequency of words and then compares the two hash maps to detemin common frequency of words.
- The frequency map is use to calculate the similiarity between given two documents.

4. Longest Common Subsequence 
- After testing common string comparision algorithms like Edit Distance, Rabin Karp, Knoth-Morris-Pratt and Cosine distance, i was able to generate most favourable results using LCS Algorithm.
- The LCS algorithm compares string character by character and stores the longest common sub sequence present in both the given files inot a matrix.
- With combination of LCS and Frequency Map, I was able to get satisfactory results on the given data set for a threshold value of 55.
- If the socre is greater than 55 the program returns 1 i.e Plagiarism found esle 0 i.e plagiarism not found. 
