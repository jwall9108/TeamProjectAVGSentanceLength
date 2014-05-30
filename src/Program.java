import java.io.BufferedReader;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;

public class Program {

	/**
	 * @param args
	 * @throws IOException
	 */
	public static void main(String[] args) {

		// command line args*******************************
		// C:\Users\jw190e\Desktop\Sentence.txt -d ,?. -l 3
		// ************************************************

		// Assumptions args[0] = input file
		// if args[x]==-d then Delimiters = args[X+1]
		// if args[x]==-l then WordLenth = args[X+1]
		// there are ~45 students per class unit up 6 units per semester ~270
		// students total

		int realWords = 0;
		int wordLenth = 0;
		String filePath;
		String delimiters = "";
		ArrayList<String> sentences;

		// Parse command line args
		filePath = args[0];
		for (int i = 0; i < args.length; i++) {
			// TODO Catch invalid command line input!!! students range in tech
			// skills
			if (args[i].equals("-d")) {
				delimiters = parseDelimiters(args[i + 1]);
			} else if (args[i].equals("-l")) {
				wordLenth = Integer.parseInt(args[i + 1]);
			}
		}

		
		// Get all sentences in the .txt file
		sentences = ReadTxtFile(filePath, delimiters);

		
		// total the number of "real" words if there is something to calculate
		if (sentences.size() != 0) {
			realWords = CalculateRealWords(sentences, wordLenth);
			// Calculate average number of real words per sentence
			System.out.print(realWords / sentences.size());
		} else {
			System.out.print("No text to proccess");
		}

	}

	/**
	 * Parses the .txt file
	 * 
	 * @param sFilePath
	 *            path to .txt file
	 * @param sDelimiters
	 *            delimiters used as regEx to split sentences in .txt file
	 * @return array of sentences within the .txt file.
	 * @exception IOException
	 *                if reader fails.
	 */
	private static ArrayList<String> ReadTxtFile(String sFilePath,
			String sDelimiters) {

		ArrayList<String> Sentences = new ArrayList<String>();
		// using try-with-resources statement eliminates the need for finally to
		// close the buffered reader
		try (BufferedReader reader = Files.newBufferedReader(
				Paths.get(sFilePath), StandardCharsets.UTF_8)) {
			String txtLine = null;
			while ((txtLine = reader.readLine()) != null) {
				// sDelimiters is in the form of a regEx
				// for example "\\.|\\?|\\," would split the sentences on
				// ".""?"","
				Collections.addAll(Sentences, txtLine.split(sDelimiters));
			}
			// Clean up sentences
			Sentences.removeAll(Arrays.asList(" ", null, ""));

		} catch (IOException e) {
			e.printStackTrace();
		}
		return Sentences;

	}

	/**
	 * Calculate the amount real words in an array of sentences
	 * 
	 * @param Sentences
	 *            array of sentences in .txt file
	 * @param WordLength
	 *            The the amount of combined characters that constitutes a
	 *            "real" word
	 * @return number of "real" words in the .txt file
	 */
	private static int CalculateRealWords(ArrayList<String> Sentences,
			int WordLength) {

		int realWords = 0;
		for (String Sentence : Sentences) {
			String[] Words = Sentence.split("\\s+");
			for (String Word : Words) {
				if (Word.length() >= WordLength) {
					realWords++;
				}
			}
		}
		return realWords;
	}

	/**
	 * Parses delimiters supplied in cmd args
	 * 
	 * @param rawDelimiters
	 *            string of delimiters supplied by the user via cmd args -d
	 * @return RegEx string to be used to split split sentences by supplied
	 *         delimiters
	 */
	private static String parseDelimiters(String rawDelimiters) {

		String regEx = "";
		String[] delimiters = rawDelimiters.split("");

		for (int i = 0; i < delimiters.length; i++) {
			if (!delimiters[i].equals("")) {
				if (i != delimiters.length - 1) {
					regEx += "\\" + delimiters[i] + "|";
				} else {
					regEx += "\\" + delimiters[i];
				}
			}
		}
		return regEx;

	}

}
