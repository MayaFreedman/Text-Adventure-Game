import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.StringTokenizer;

/*
* Constructer: gets up to four words inputed by the user keyboard, sets the first word to null if it's unknown
* then creates a new command with the words
*/
public class Parser {
  private CommandWords commands; // holds all valid command words

  public Parser() {
    commands = new CommandWords();
  }

  public Command getCommand() {
    String inputLine = ""; // holds the full input line
    String word1;
    String word2;
    String word3;
    String word4;
    System.out.print("> "); // prints prompt
    BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
    try {
      inputLine = reader.readLine();
    } catch (java.io.IOException exc) {
      System.out.println("There was an error during reading: " + exc.getMessage());
    }
    StringTokenizer tokenizer = new StringTokenizer(inputLine);
    if (tokenizer.hasMoreTokens())
      word1 = tokenizer.nextToken().toLowerCase(); // get first word
    else
      word1 = null;
    if (tokenizer.hasMoreTokens())
      word2 = tokenizer.nextToken().toLowerCase(); // get second word
    else
      word2 = null;
    if (tokenizer.hasMoreTokens())
      word3 = tokenizer.nextToken().toLowerCase(); // get third word
    else
      word3 = null;
    if (tokenizer.hasMoreTokens())
      word4 = tokenizer.nextToken().toLowerCase(); // get fourth word
    else
      word4 = null;
    // Checks whether this word is known, returns null if it isn't and returns the
    // word if it is
    if (commands.isCommand(word1))
      return new Command(word1, word2, word3, word4);
    else
      return new Command(null, word2, word3, word4);
  }

  /*
   * showCommands: Prints out a list of valid command words.
   */
  public void showCommands() {
    commands.showAll();
  }
}
