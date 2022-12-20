import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.HashSet;
import java.util.StringTokenizer;

public class s40165942_detector{

    public static void main(String[] args) throws FileNotFoundException {

        String str1 = new String();
        String str2 = new String();

        str1 = parseFile(args[0]);
        str1 = parseFile(args[1]);
        

        if(fileContainsCode(str1) && fileContainsCode(str2)){
            str1 = processFilesContainingCode(str1);
            str2 = processFilesContainingCode(str2);

        }else if(fileContainsCode(str1) || fileContainsCode(str2)){
            System.out.println(0);

        }else{
            str1 = processFiles(str1); 
            str2 = processFiles(str2); 
        }

        double hashValue = getHash(str1.split("\\s+"), str2.split("\\s+"));

        double lcsScore  = lcs(str1, str2);

        // Combination of lcs and frequency
        double ans = (hashValue + lcsScore) /2 ;


        if (ans > 55)
            System.out.println(1);
        else
            System.out.println(0);
            
}

    private static double lcs(String str1, String str2) {
        int m = str1.length();
        int n = str2.length();

        // DP array for memoizing the results
        int dp[][] = new int[m + 1][n + 1];

        // Calculating the longest subsequence
        for (int i = 0; i <= m; i++) {
            for (int j = 0; j <= n; j++) {
                if (i == 0 || j == 0)
                    dp[i][j] = 0;
                else if (str1.charAt(i - 1) == str2.charAt(j - 1))
                    dp[i][j] = dp[i - 1][j - 1] + 1;
                else
                    dp[i][j] = Math.max(dp[i - 1][j], dp[i][j - 1]);
            }
        }

        double len =  str2.length();

        // Generating the percentage for plagiarism
        double perc = dp[m][n] / len;

        return perc * 100;
    }

    // Frequency map for both the documents, returns the similiarity between documents
    private static double getHash(String[] str1, String[] str2) {
        HashMap<String, Integer> s1 = new HashMap<>();
        HashMap<String, Integer> s2 = new HashMap<>();

        // Store frequency for doc1
        for (String s : str1) {
            if (s1.isEmpty()) {
                s1.put(s, 1);
            } else {
                if (s1.containsKey(s)) {
                    s1.put(s, s1.get(s) + 1);
                } else {
                    s1.put(s, 1);
                }
            }
        }

        // Store frequency for doc2
        for (String x : str2) {
            if (s2.isEmpty()) {
                s2.put(x, 1);
            } else {
                if (s2.containsKey(x)) {
                    s2.put(x, s2.get(x) + 1);
                } else {
                    s2.put(x, 1);
                }
            }
        }

        double similarityCount = 0d;

        // Generate similarity between two docs
        if (s1.keySet().size() > s2.keySet().size()) {
            for (String s : s2.keySet()) {
                if (s1.containsKey(s) && s2.get(s).equals(s1.get(s))) {
                    similarityCount += 1;
                }
            }
        } else {
            for (String s : s1.keySet()) {
                if (s2.containsKey(s) && s1.get(s).equals(s2.get(s))) {
                    similarityCount += 1;
                }
            }

        }

    int len = s1.keySet().size() <= s2.keySet().size() ? s1.keySet().size() : s2.keySet().size();
   
    return (double) Math.ceil((similarityCount / len) * 100);
}

    private static String processFilesContainingCode(String str){
        String s = new String();
        StringTokenizer st = new StringTokenizer(str , "%&+=][{}><-/.,;:()'!?\"\t\n\r\f");
        while (st.hasMoreTokens()) {
            StringBuilder token = new StringBuilder(st.nextToken().toLowerCase());
            s += token;
        }
        return s;
    }

    // Pre processing normal text files by removing dates, brackets, numbers, website refrences
    private static String processFiles(String str){
        String string = String.join(" ", str
        .replaceAll("\\d{4}-\\d{2}-\\d{2}", "")
        .replaceAll("[0-9]", "")
        .replaceAll("((https?|http):((//)|(\\\\))+[\\w\\d:#@%/;$()~_?\\+-=\\\\\\.&]*)", "")
        .replaceAll("\n", "")
        .replaceAll("[^a-zA-Z ]", "")
        .replaceAll("[\\[\\](){}]", "").trim()
        .toLowerCase()
        .split("\\n"));

        String[] stopWords = { "a", "able", "about", "across", "after", "all", "almost", "also", "am", "an","into", "is", "it", "its", "just", "least", "let", "like", "likely", "may", "me", "might", "most", "must", "my", "neither",
        "and", "any", "are", "as", "at", "be", "because", "been", "but", "by", "can", "cannot", "could", 
        "else", "for", "from", "get", "got", "had", "has", "have", "he", "her", "hers", "him", "his", "how", "i", "Wikipedia", "http","www", ".org","wiki","wikipedia","#","google",".com","https",
        "if", "in", 
        "no", "nor", "not", "of", "off", "often", "one","on", "only", "or", "other", "own", "rather", "said", "say", "says", "she", "should",
        "since", "so", "some", "than", "that", "the", "their", "them", "then", "there", "these", "they", "this", "tis", "to", "too", "twas", "dear", "did", "do", "does", "either",
        "us", "wants", "was", "we", "were", "what", "when", "where", "which", "while",
        "who", "whom", "why", "will", "with", "would", "yet", "you", "your" };

        HashSet<String> StopWordsSet = new HashSet<>(Arrays.asList(stopWords));
        HashSet<String> wordWithStopWord1 = new HashSet<String>(Arrays.asList(string));

        wordWithStopWord1.removeAll(StopWordsSet);

        String retStr = new String();

        for(String keys : wordWithStopWord1){
            retStr += keys;
        }


        return retStr;
    }

    private static String parseFile(String fName) {
        StringBuilder str = new StringBuilder();
        try {
            BufferedReader br = new BufferedReader(new FileReader(fName));
            String s = new String();
            while((s = br.readLine())!= null){
                str.append(s + "\n");
            }
            br.close();
        } catch (Exception filException) {
            System.err.println("Error parsing File: " + fName);
        }
        return str.toString();
    }

    // Algorithm to determin if file has code or plain text
    private static boolean fileContainsCode(String text) {
        ArrayList<String> codeWords = new ArrayList<>(
                Arrays.asList("bool", "h>", "int", "char", "chr", "return", "char",
                         "static", "void", ">>", "==", "!=", ">=", "<=", "{", "}", "=", "++", "--", "<", ">", "++", "--","cout", "<<", "switch", "case", "()", "system", "out", "println", "print", "boolean", "#include", "elif", "class", "void" 
                        ));

        double val = 0.00;

        for (String s : text.split(" ")) {
            if (codeWords.contains(s)) {
                val++;
            }
            if (val / text.length() >= 0.01) {
                return true;
            }
        }
        if (val / text.length() >= 0.01) {
            return true;
        } else
            return false;
    }
}
