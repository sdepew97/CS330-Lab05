import java.io.BufferedReader;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.FileSystems;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.HashMap;

public class Lab05 {
    //Global variables
    public static HashMap<String, ArrayList<String>> dictionary = new HashMap<>();
    public static String filePath = "/usr/share/dict/words";
    public static int NUM_CHARS = 256;
    public static ArrayList<String> anagrams = new ArrayList<>();

    public static void main(String args[]) {
        //for all words, read word, sort, and insert into hashmap
        readWords(filePath);

        //get a word and search for it
        anagrams = searchWord("pickel");

        //print list of anagrams
        if(anagrams.size()>0) {
            printList(anagrams);
        }

        //exit/done
    }

    private static void printList(ArrayList<String> anagrams) {
        for(int i=0; i<anagrams.size(); i++) {
            System.out.println(anagrams.get(i));
        }
    }

    public static ArrayList<String> searchWord(String word) {
        ArrayList<String> returnList = new ArrayList<>();
        returnList = dictionary.get(sortString(word));
        return returnList;
    }

    public static void readWords(String filePath) {
        Path path = FileSystems.getDefault().getPath("/usr/share/dict/", "words");
        try {
            BufferedReader reader = Files.newBufferedReader(path, StandardCharsets.US_ASCII);
            String line = reader.readLine();
            String sortedLine = new String();
            ArrayList<String> anagrams = new ArrayList<>();

            while (line != null) {
                line = line.trim(); //remove any whitespace that could mess us up
                sortedLine = sortString(line);
                if (!dictionary.containsKey(sortedLine)) {
                    anagrams.add(line);
                    dictionary.put(sortedLine, anagrams);
                } else {
                    anagrams = dictionary.get(sortedLine);
                    anagrams.add(line);
                    dictionary.put(sortedLine, anagrams);
                }

                line = reader.readLine();
            }
            reader.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private static String sortString(String unsortedInput) {
        int[] charCounts = new int[NUM_CHARS]; //array to count the number of characters
        StringBuilder sortedInput = new StringBuilder();

        for (int i = 0; i < unsortedInput.length(); i++) {
            charCounts[(int) unsortedInput.charAt(i)]++;
        }

        for (int i = 0; i < NUM_CHARS; i++) {
            for (int j = 0; j < charCounts[i]; j++) {
                sortedInput.append((char) i);
            }
        }

        return sortedInput.toString();
    }
}
