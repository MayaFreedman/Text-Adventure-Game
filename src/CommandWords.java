
class CommandWords {
  // a constant array that holds all valid command words
  private static final String validCommands[] = { "go", "quit", "help", "eat", "yell", "look", "take", "drop", "view",
      "open", "read", "turn", "input", "catch", "shake", "release", "bulbasaur", "squirtle", "charmander" };

  /*
   * isCommand: Check whether a given String is a valid command word. Return true
   * if it is, false if it isn't.
   */
  public boolean isCommand(String aString) {
    for (int i = 0; i < validCommands.length; i++) {
      if (validCommands[i].equals(aString))
        return true;
    }
    // Returns false if the string was not found in the commands
    return false;
  }

  /*
   * showAll: Prints all valid commands
   */
  public void showAll() {
    for (int i = 0; i < validCommands.length - 3; i++) {
      System.out.print(validCommands[i] + "  ");
    }
    System.out.println();
  }
}
