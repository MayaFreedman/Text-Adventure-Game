public class Command {
  private String commandWord;
  private String secondWord;
  private String thirdWord;
  private String fourthWord;

  /**
   * Constructor: Create a command object consisting of a first, second, third and
   * fourth word.
   */
  public Command(String firstWord, String secondWord, String thirdWord, String forthWord) {
    commandWord = firstWord;
    this.secondWord = secondWord;
    this.thirdWord = thirdWord;
    this.fourthWord = forthWord;
  }

  /**
   * getCommandWord: Return the command word (the first word) of this command. If
   * the command was not understood, the result is null.
   */
  public String getCommandWord() {
    return commandWord;
  }

  /**
   * getSecondWord: Return the second word of this command. Returns null if there
   * was no second word.
   */
  public String getSecondWord() {
    return secondWord;
  }

  /**
   * getThirdWord: Return the third word of this command. Returns null if there
   * was no third word.
   */
  public String getThirdWord() {
    return thirdWord;
  }

  /**
   * getFourthWord: Return the fourth word of this command. Returns null if there
   * was no fourth word.
   */
  public String getFourthWord() {
    return fourthWord;
  }

  /**
   * isUnkown: Return true if this command was not understood.
   */
  public boolean isUnknown() {
    return (commandWord == null);
  }

  /**
   * hasSecondWord: Return true if the command has a second word.
   */
  public boolean hasSecondWord() {
    return (secondWord != null);
  }

  /**
   * hasThirdWord: Return true if the command has a third word.
   */
  public boolean hasThirdWord() {
    return (thirdWord != null);
  }
}
