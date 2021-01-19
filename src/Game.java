import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Scanner;

//import javax.lang.model.util.ElementScanner6;

/**
 * Class Game - the main class of the "Zork" game.
 *
 * Author: Michael Kolling Version: 1.1 Date: March 2000
 * 
 * This class is the main class of the "Zork" application. Zork is a very
 * simple, text based adventure game. Users can walk around some scenery. That's
 * all. It should really be extended to make it more interesting!
 * 
 * To play this game, create an instance of this class and call the "play"
 * routine.
 * 
 * This main class creates and initialises all the others: it creates all rooms,
 * creates the parser and starts the game. It also evaluates the commands that
 * the parser returns.
 */
public class Game {
  // Creates Strings used to change the color of the text in the terminal

  private Parser parser;
  private Room currentRoom;
  private Inventory inventory;
  // This is a MASTER object that contains all of the rooms and is easily
  // accessible.
  // The key will be the name of the room -> no spaces (Use all caps and
  // underscore -> Great Room would have a key of GREAT_ROOM
  // In a hashmap keys are case sensitive.
  // masterRoomMap.get("GREAT_ROOM") will return the Room Object that is the Great
  // Room (assuming you have one).
  private HashMap<String, Room> masterRoomMap;

  private void initRooms(String fileName) throws Exception {
    masterRoomMap = new HashMap<String, Room>();
    Scanner roomScanner;
    try {
      HashMap<String, HashMap<String, String>> exits = new HashMap<String, HashMap<String, String>>();
      roomScanner = new Scanner(new File(fileName));
      while (roomScanner.hasNext()) {
        Room room = new Room();
        // Read the Name
        String roomName = roomScanner.nextLine();
        room.setRoomName(roomName.split(":")[1].trim());
        // Read the Description
        String roomDescription = roomScanner.nextLine();
        room.setDescription(roomDescription.split(":")[1].replaceAll("<br>", "\n").trim());
        // Read the Exits
        String roomExits = roomScanner.nextLine();
        // An array of strings in the format E-RoomName
        String[] rooms = roomExits.split(":")[1].split(",");
        HashMap<String, String> temp = new HashMap<String, String>();
        for (String s : rooms) {
          temp.put(s.split("-")[0].trim(), s.split("-")[1]);
        }

        exits.put(roomName.substring(10).trim().toUpperCase().replaceAll(" ", "_"), temp);

        // This puts the room we created (Without the exits in the masterMap)
        masterRoomMap.put(roomName.toUpperCase().substring(10).trim().replaceAll(" ", "_"), room);

        // Now we better set the exits.
      }

      for (String key : masterRoomMap.keySet()) {
        Room roomTemp = masterRoomMap.get(key);
        HashMap<String, String> tempExits = exits.get(key);
        for (String s : tempExits.keySet()) {
          // s = direction
          // value is the room.

          String roomName2 = tempExits.get(s.trim());
          Room exitRoom = masterRoomMap.get(roomName2.toUpperCase().replaceAll(" ", "_"));
          roomTemp.setExit(s.trim().charAt(0), exitRoom);
        }
      }

      roomScanner.close();
    } catch (FileNotFoundException e) {
      e.printStackTrace();
    }
  }

  /**
   * Create the game and initialise its internal map.
   */
  public Game() {
    try {
      initRooms("data/rooms.dat");
      currentRoom = masterRoomMap.get("FIELD");
    } catch (Exception e) {
      // TODO Auto-generated catch block
      e.printStackTrace();
    }
    parser = new Parser();
    setItems();
  }

  /*
   * setItems: initializes and adds all the items of the game, changes the light
   * status to dark in dark rooms and declares if any rooms don't have natural
   * light sources
   */
  public void setItems() {
    Room room;

    // North Tree Top
    Item northTreeTopItems[] = { new Item("couch", 6), new Item("table", 6) };
    masterRoomMap.get("NORTH_TREE_TOP").addItems(northTreeTopItems);

    // South Tree Top
    Pokemon bulbasaur = new Pokemon("bulbasaur", "cut", "cut down trees blocking your path");
    masterRoomMap.get("SOUTH_TREE_TOP").addPokemon(bulbasaur);

    // Field
    String message = "Welcome to Diamond city!\nSome helpful hints for the game:\nEach pokemon has a move they specialize in. Once you catch a pokemon using a pokeball, type 'view pokemon' to see their move\nTo use that move, type '(Pokemon name) use (move name)'\nThe combined weight of the items in your inventory must be 5 or less\nTo view the specific weight and information about an item, type 'view (itemname)'\nAt any point if you need help, type 'help' to see a list of usable commands or 'look' to look around the room\nGood luck on your quest brave hero!";
    Item fieldItems[] = { new Item("sign", 6, message, ""),
        new Item("vine", 6, false, "You cut the vine and reveal an open path to a beach in the east", null, null, true)};
    masterRoomMap.get("FIELD").addItems(fieldItems);

    // Dusty Attic
    Item dustyAtticItems[] = { new Item("lantern", 2, true, false, "a rusty lantern that can light up dark rooms") };
    room = masterRoomMap.get("DUSTY_ATTIC");
    room.addItems(dustyAtticItems);

    String num1 = "" + (int) (Math.random() * 10);
    String num2 = "" + (int) (Math.random() * 10);
    String num3 = "" + (int) (Math.random() * 10);
    String num4 = "" + (int) (Math.random() * 10);

    String code = num1 + num2 + num3 + num4;

    System.out.println(code);

    // North East Corner of Library
    Item northEastItems[] = {
        new Item("red-book", 1, num1, "a red book with a single number written on the first page") };
    masterRoomMap.get("NORTH_EAST_CORNER_OF_LIBRARY").addItems(northEastItems);

    // South West Corner of Library
    Item southWestItems[] = {
        new Item("yellow-book", 1, num2, "a yellow book with a single number written on the first page") };
    masterRoomMap.get("SOUTH_WEST_CORNER_OF_LIBRARY").addItems(southWestItems);

    // North West Corner of Library
    Item northWestItems[] = {
        new Item("green-book", 1, num3, "a green book with a single number written on the first page") };
    masterRoomMap.get("NORTH_WEST_CORNER_OF_LIBRARY").addItems(northWestItems);

    // South East Corner of Library
    Item southEastItems[] = {
        new Item("orange-book", 1, num4, "an orange book with a single number written on the first page") };
    masterRoomMap.get("SOUTH_EAST_CORNER_OF_LIBRARY").addItems(southEastItems);

    // Hidden Hut
    Item hiddenHutItems[] = { new Item("keypad", code, 6, true),
        new Item("door", 6, false, "east door opened", null, null, true),
        new Item("engraving", 6, "red yellow green orange", "") };
    masterRoomMap.get("HIDDEN_HUT").addItems(hiddenHutItems);

    // West Hut
    Item westHutItems[] = {
        new Item("chest", 6, false, "You open the chest, there is a pokeball sitting inside the chest",
            new Item("pokeball", 1, "A red and white pokeball that can be used to catch pokemon"), null, false) };
    masterRoomMap.get("WEST_HUT").addItems(westHutItems);

    code = "" + (int) (Math.random() * 10) + (int) (Math.random() * 10) + (int) (Math.random() * 10)
        + (int) (Math.random() * 10);

    // North Dead End
    Item northDeadEndItems[] = { new Item("engraving", 6, code, null) };
    masterRoomMap.get("NORTH_DEAD_END").addItems(northDeadEndItems);

    // Stone Canvern
    masterRoomMap.get("STONE_CAVERN")
        .addPokemon(new Pokemon("squirtle", "surf", "allows you to breathe surf over water and onto the nearest land"));

    // Inside Building
    masterRoomMap.get("INSIDE_BUILDING").setHasLight(false);

    // Outside Building
    Item outsideBuildingItems[] = {
        new Item("mailbox", 6, false, "you open the mailbox to find a pokeball sitting inside",
            new Item("pokeball", 1, "A red and white pokeball that can be used to catch pokemon"), null, false),
        new Item("tree", 6, false, "Bulbasaur cuts the tree in half, revealing a south path to a parking lot", null,
            null, true) };
    masterRoomMap.get("OUTSIDE_BUILDING").addItems(outsideBuildingItems);

    // South Dead End
    Item SouthDeadEndItems[] = { new Item("keypad", code, 6, true),
        new Item("door", 6, false, "the stone wall shifts to reveal a path to the west", null, null, true) };
    masterRoomMap.get("SOUTH_DEAD_END").addItems(SouthDeadEndItems);

    // Dark Room
    Item darkRoomItems[] = { new Item("switch", 6, true, false, null) };
    masterRoomMap.get("DARK_ROOM").addItems(darkRoomItems);

    // Parking Lot
    Item parkingLotItems[] = { new Item("trunk", 6, false, "You open the trunk and find a cloak sitting inside.",
        new Item("cloak", 1,
            "this is a magical blue cloak. Whoever posseses it can walk through walls that have a blue detail on them"),
        null, false)};
    masterRoomMap.get("PARKING_LOT").addItems(parkingLotItems);

    // Beach
    Item beachItems[] = { new Item("sign", 6, "to travel over water, a pokemon's move might be helpful ...", "") };
    masterRoomMap.get("BEACH").addItems(beachItems);

    // North Side of Island
    Item northSideOfIslandItems[] = {
        new Item("tree", 6, true,
            new Item("coconut", 6, false, "you open the coconut and find the letters h, m and d painted on the inside",
                null, null, false),
            "you shake the tree, resulting in a coconut faling from one of the branches."),
        new Item("sign", 6, "to travel back ove water to the beach, try using the same way you got here ...", "") };
    masterRoomMap.get("NORTH_SIDE_OF_ISLAND").addItems(northSideOfIslandItems);

    // South Side of Island
    Item southSideOfIslandItems[] = { new Item("tree", 6, true,
        new Item("coconut", 6, false, "you open the coconut and find the letters c, r, n and r painted on the inside",
            null, null, false),
        "you shake the tree, resulting in a coconut faling from one of the branches.") };
    masterRoomMap.get("SOUTH_SIDE_OF_ISLAND").addItems(southSideOfIslandItems);

    // Center of Island
    Item centerOfIslandItems[] = { new Item("tree", 6, true,
        new Item("coconut", 6, false, "you open the coconut and find a pokeball sitting inside",
            new Item("pokeball", 1, "a red and white pokeball that can be used to catch pokemon"),
            null, false),
        "you shake the tree, resulting in a coconut faling from one of the branches.") };
    masterRoomMap.get("CENTER_OF_ISLAND").addItems(centerOfIslandItems);

    // West Side of Island
    Item westSideOfIslandItems[] = { new Item("tree", 6, true,
        new Item("coconut", 6, false, "you open the coconut and find the letters a, a and e painted on the inside",
            null, null, false),
        "you shake the tree, resulting in a coconut faling from one of the branches.") };
    masterRoomMap.get("WEST_SIDE_OF_ISLAND").addItems(westSideOfIslandItems);

    // East Side of Island
    Item eastSideOfIslandItems[] = { new Item("keypad", "charmander", 6, true),
        new Item("door", 6, false, "the ground shifts to reveal a stone staircase in the ground leading down", null,
            null, true),
        new Item("sign", 6,
            "To unlock the keypad, remember: one letter from south, one letter from north, one letter from west, repeat", "") };
    masterRoomMap.get("EAST_SIDE_OF_ISLAND").addItems(eastSideOfIslandItems);

    // Underground Bunker
    masterRoomMap.get("UNDERGROUND_BUNKER")
        .addPokemon(new Pokemon("charmander", "inferno", "allows you to burn down baracades of rocks"));

    // Base of Volcano
    Item baseOfVolcanoItems[] = { new Item("rocks", 6, false,
        "charmander burns down the rocks, leaving a path leading up to the peak of the volcano", null, null, true) };
    masterRoomMap.get("BASE_OF_VOLCANO").addItems(baseOfVolcanoItems);
  }

  /**
   * Main play routine. Loops until end of play.
   */
  public void play() {
    inventory = new Inventory(5);
    printWelcome();
    checkIfLit();
    // Enter the main command loop. Here we repeatedly read commands and
    // execute them until the game is over.

    boolean finished = false;
    while (!finished) {
      Command command = parser.getCommand();
      finished = processCommand(command);
      finished = checkWin();
    }
    System.out.println("Thank you for playing.  Good bye.");
  }

  /*
   * checkWin: Checks if the player has finished the game
   */
  private boolean checkWin() {
    if (currentRoom.getRoomName().equals("Top of Volcano") && currentRoom.pokemonInRoom("bulbasaur") >= 0
        && currentRoom.pokemonInRoom("squirtle") >= 0 && currentRoom.pokemonInRoom("charmander") >= 0) {
      System.out.println(
          "All three pokemon are on the volcano flash into balls of light that spread over the city, returning diamond city to it's former glory");
      System.out.println(
          "Congratulations!\nYou have rescued the three starter pokemon from the clutches of evil, restoring peace and order in the city\nThe whole town commends you brave hero\n\n");
      return true;
    } else
      return false;
  }

  private void checkIfLit() {
    if (!currentRoom.getHasLight()) {
      int index = inventory.findItem("lantern");
      if (index < 0 || !inventory.getItem(index).getIsOn())
        currentRoom.changeIsLit(false);
      else
        currentRoom.changeIsLit(true);
    } else
      currentRoom.changeIsLit(true);
  }

  /**
   * Print out the opening message for the player.
   */
  private void printWelcome() {
    System.out.println("\nWelcome to Diamond City! We need your help.");
    System.out.println(
        "\nThe three starter pokemon have been stolen and hid around the city, resulting in terror among the citizens.\nWe need you to find all three starter pokemon, Bulbasaur, Squirtle and Charmander, and release them at the top of the volcano\nwhere they can then restore peace to diamond city");
    System.out.println("\nGood luck brave hero.\nType 'help' if you need help at any point\n");
    System.out.println(currentRoom.longDescription() + specialCases());
  }

  /**
   * Given a command, process (that is: execute) the command. If this command ends
   * the game, true is returned, otherwise false is returned.
   */
  private boolean processCommand(Command command) {
    if (command.isUnknown()) {
      int num = (int) (Math.random() * 4 + 0);
      if (num == 0)
        System.out.println("I don't know what you mean...");
      else if (num == 1)
        System.out.println("DOES NOT COMPUTE");
      else if (num == 2)
        System.out.println("What are you saying?");
      else if (num == 3)
        System.out.println("Please say something I can understand");
      return false;
    }

    String commandWord = command.getCommandWord();
    if (commandWord.equals("help"))
      printHelp();
    else if (commandWord.equals("go"))
      goRoom(command);
    else if (commandWord.equals("quit")) {
      if (command.hasSecondWord())
        System.out.println("Quit what?");
      else
        return true; // signal that we want to quit
    } else if (commandWord.equals("eat"))
      System.out.println("Do you really think you should be eating at a time like this?");
    else if (commandWord.equals("yell"))
      printYell(command);
    else if (commandWord.equals("turn"))
      turnItem(command);
    else if (!currentRoom.getIsLit())
      System.out.println("You cannot do that while this room is dark");
    else if (commandWord.equals("look"))
      System.out.println(currentRoom.longDescription());
    else if (commandWord.equals("take"))
      takeItem(command);
    else if (commandWord.equals("drop"))
      dropItem(command);
    else if (commandWord.equals("view"))
      viewInventory(command);
    else if (commandWord.equals("open"))
      openItem(command);
    else if (commandWord.equals("read"))
      readItem(command);
    else if (commandWord.equals("input"))
      inputCode(command);
    else if (commandWord.equals("shake"))
      shakeTree(command);
    else if (commandWord.equals("catch"))
      catchPokemon(command);
    else if (commandWord.equals("release"))
      releasePokemon(command);
    else if (commandWord.equals("bulbasaur") || commandWord.equals("squirtle") || commandWord.equals("charmander"))
      useMove(command);
    return false;
  }

  private void shakeTree(Command command) {
    if (!command.hasSecondWord())
      System.out.println("What do you want to shake");
    int index = currentRoom.itemInRoom(command.getSecondWord());
    if (index < 0)
      System.out.println("There is no item with that name that you can see");
    else {
      Item item = currentRoom.getItem(index);
      if (!item.getisShakable())
        System.out.println("You shake the " + command.getSecondWord() + ". It does nothing");
      else {
        System.out.println(item.getShakeMessage());
        currentRoom.addItem(item.getInnerItem());
        item.setIsShakable(false);
      }
    }
  }

  private void useMove(Command command) {
    String firstWord = command.getCommandWord();
    if (!command.hasThirdWord() || !command.hasSecondWord())
      System.out.println(
          "I don't understand what you mean.\nIf you're trying to use a pokemon's move, use the form '(pokemon name) use (move name)'");
    else {
      int index = inventory.findPokemon(firstWord);
      if (index < 0)
        System.out.println("There is no pokemon with that name");
      else {
        Pokemon pokemon = inventory.getPokemon(index);
        String move = command.getThirdWord();
        if (!move.equals(pokemon.getMove()))
          System.out.println("This pokemon does not know that move");
        else if (move.equals("cut")) {
          if (currentRoom.getRoomName().equals("Outside Building")
              && !currentRoom.getItem(currentRoom.itemInRoom("tree")).getIsOpened()) {
            Item tree = currentRoom.getItem(currentRoom.itemInRoom("tree"));
            tree.setIsOpened(true);
            System.out.println(tree.getOpenMessage());
          } else if (currentRoom.getRoomName().equals("Field")
              && !currentRoom.getItem(currentRoom.itemInRoom("vine")).getIsOpened()) {
            Item vine = currentRoom.getItem(currentRoom.itemInRoom("vine"));
            vine.setIsOpened(true);
            System.out.println(vine.getOpenMessage());
          } else
            System.out.println("Bulbasaur uses cut, it does nothing.");
        } else if (move.equals("surf")) {
          Room northSideofIsland = masterRoomMap.get("NORTH_SIDE_OF_ISLAND");
          Room beach = masterRoomMap.get("BEACH");
          if (currentRoom == beach) {
            System.out.println("Squirtle uses surf to surf across the lake onto the large island");
            currentRoom = northSideofIsland;
            System.out.println(currentRoom.longDescription());
          } else if (currentRoom == northSideofIsland) {
            System.out.println("Squirtle uses surf to surf across the lake back onto the beach");
            currentRoom = beach;
            System.out.println(currentRoom.longDescription());
          } else
            System.out.println("You cannot use surf when there is no water");
        } else if (move.equals("inferno")) {
          if (currentRoom.getRoomName().equals("Base of Volcano")
              && !currentRoom.getItem(currentRoom.itemInRoom("rocks")).getIsOpened()) {
            Item rocks = currentRoom.getItem(currentRoom.itemInRoom("rocks"));
            rocks.setIsOpened(true);
            System.out.println(rocks.getOpenMessage());
          } else
            System.out.println("charmander uses inferno, it does nothing");
        }
      }
    }
  }

  /*
   * catchPokemon: lets the user catch a pokemon if they have a pokeball and the
   * specified pokemon is in the room. Prints out the apropriate error message if
   * those criteria aren't met
   */
  private void catchPokemon(Command command) {
    String secondWord = command.getSecondWord();
    int PokeballIndex = inventory.findItem("pokeball");
    int pokemonIndex = currentRoom.pokemonInRoom(secondWord);
    if (pokemonIndex < 0)
      System.out.println("There is no pokemon with that name that you can see");
    else if (PokeballIndex < 0)
      System.out.println("You need a pokeball to catch a pokemon");
    else {
      inventory.removeItem(inventory.getItem(PokeballIndex));
      Pokemon pokemon = currentRoom.getPokemon(pokemonIndex);
      inventory.addPokemon(pokemon);
      currentRoom.removePokemon(pokemon);
      System.out.println(pokemon.getName() + " caught");
    }
  }

  /*
   * inputCode: lets the player input a code into a keypad and unlock a door if
   * it's the correct code
   */
  private void inputCode(Command command) {
    String thirdWord = command.getThirdWord();
    String forthWord = command.getFourthWord();
    int index = currentRoom.itemInRoom("keypad");
    if (!(thirdWord != null && thirdWord.equals("into") && forthWord != null && forthWord.equals("keypad")))
      System.out.println(
          "I don't understand what you're saying \nif you're trying to input a code into a keybad, type 'input (put code here) into keypad'");
    else if (index < 0)
      System.out.println("There is no keypad in this room");
    else {
      Item keypad = currentRoom.getItem(index);
      if (!keypad.getIsLocked())
        System.out.println("This keypad is already unlocked");
      else if (!(keypad.getCode().equals(command.getSecondWord())))
        System.out.println("Wrong code");
      else {
        Item door = currentRoom.getItem(currentRoom.itemInRoom("door"));
        System.out.println(door.getOpenMessage());
        keypad.changeIsLocked(false);
        door.setIsOpened(true);
      }
    }

  }

  /*
   * turnItem: lets the user turn on or off an item if that item can turn on or
   * off and displays the appropriate error message if the user can't perform that
   * action
   */
  private void turnItem(Command command) {
    String secondWord = command.getSecondWord();
    String thirdWord = command.getThirdWord();
    if (command.hasSecondWord() && command.hasThirdWord()) {
        int inventoryIndex = inventory.findItem(thirdWord);
        int roomIndex = currentRoom.itemInRoom(thirdWord);
        if (roomIndex < 0 && inventoryIndex < 0)
          System.out.println("There is no item with that name that you can see");
        else {
          Item item;
          if (inventoryIndex >= 0)
            item = inventory.getItem(inventoryIndex);
          else
            item = currentRoom.getItem(roomIndex);
          if (!item.getCanTurnOn())
            System.out.println("That item cannot be turned " + secondWord);
          else {
            boolean turnOn;
            if (secondWord.equals("on"))
              turnOn = true;
            else
              turnOn = false;
            if (item.getIsOn() && turnOn)
              System.out.println("This item is already on");
            else if (!item.getIsOn() && !turnOn)
              System.out.println("This item is already off");
            else {
              item.changeIsOn(turnOn);
              System.out.println(item.getName() + " turned " + secondWord);
              if (item.getName().equals("lantern") && !currentRoom.getHasLight()) {
                if (currentRoom.getIsLit() && !turnOn) {
                  currentRoom.changeIsLit(false);
                  System.out.println("This room is now dark");
                } else if (!currentRoom.getIsLit() && turnOn) {
                  currentRoom.changeIsLit(true);
                  System.out.println("This room is now lit\n" + currentRoom.longDescription());
                }
              }
            }
          }
        }
    } else {
      System.out.println(
          "I don't understand what you're saying. If you want to turn on an item, use the phrase 'turn item (off/on)");
    }
  }

  /*
   * Lets the player read any item that can be read, displays the appropriate
   * error message if that action is not possible
   */
  private void readItem(Command command) {
    Item item;
    String secondWord = command.getSecondWord();
    if (secondWord == null)
      System.out.println("What do you want to read?");
    int roomIndex = currentRoom.itemInRoom(secondWord);
    int inventoryIndex = inventory.findItem(secondWord);
    if (roomIndex < 0 && inventoryIndex < 0)
      System.out.println("There is no item with that name that you can see");
    else {
      if (inventoryIndex >= 0)
        item = inventory.getItem(inventoryIndex);
      else
        item = currentRoom.getItem(roomIndex);
      if (item.getReadMessage() == null)
        System.out.println("This item cannot be read");
      else
        System.out.println("The " + item.getName() + " says: " + item.getReadMessage());
    }
  }

  private void openItem(Command command) {
    if (!command.hasSecondWord())
      System.out.println("What do you want to open?");
    else {
      int index = currentRoom.itemInRoom(command.getSecondWord());
      if (index < 0)
        System.out.println("There is no item with that name in this room");
      else {
        Item item = currentRoom.getItem(index);
        if (item.getOpenMessage() == null || item.getIsLocked())
          System.out.println("You cannot open this item");
        else if (item.getIsOpened())
          System.out.println("This item is already opened");
        else {
          System.out.println(item.getOpenMessage());
          item.setIsOpened(true);
        }
      }
    }
  }

  // viewInventory: Allows the user to view all items in their inventory or a
  // specific item in their inventory
  private void viewInventory(Command command) {
    if (!command.hasSecondWord())
      System.out.println("What do you want to view?");
    else if (command.getSecondWord().equals("inventory"))
      System.out.println(inventory.showInventory());
    else {
      Item item;
      Pokemon pokemon;
      String secondWord = command.getSecondWord();
      int itemIndex = inventory.findItem(secondWord);
      int pokemonIndex = inventory.findPokemon(secondWord);
      if (itemIndex < 0 && pokemonIndex < 0)
        System.out.println("You do not have that in your inventory");
      else if (itemIndex >= 0) {
        item = inventory.getItem(itemIndex);
        System.out.println(item.getDescription());
      } else {
        pokemon = inventory.getPokemon(pokemonIndex);
        System.out.println(pokemon.getDescription());
      }
    }
  }

  /*
   * dropItem: Allows the user to remove an item from their inventory and drop it
   * into the current room
   */
  private void dropItem(Command command) {
    if (!command.hasSecondWord())
      System.out.println("Drop what?");
    else {
      int index = inventory.findItem(command.getSecondWord());
      if (index < 0)
        System.out.println("There is no item with that name in your inventory");
      else {
        Item item = inventory.getItem(index);
        currentRoom.addItem(item);
        inventory.removeItem(item);
        System.out.println(item.getName() + " dropped");
      }

    }
  }

  /*
   * dropItem: Allows the user to remove an item from their inventory and drop it
   * into the current room
   */
  private void releasePokemon(Command command) {
    if (!command.hasSecondWord())
      System.out.println("Release what?");
    else {
      int index = inventory.findPokemon(command.getSecondWord());
      if (index < 0)
        System.out.println("There is no pokemon with that name that you have released");
      else {
        Pokemon pokemon = inventory.getPokemon(index);
        currentRoom.addPokemon(pokemon);
        inventory.removePokemon(pokemon);
        inventory.addItem(new Item("pokeball", 1, "a red and white pokeball that can be used to catch pokemon"));
        System.out.println(pokemon.getName() + " released");
      }

    }
  }

  /*
   * takeItem: Allows the user to take an item if it's in the room, not in a
   * closed object, and not too heavy that their inventory weight is over 5
   */
  private void takeItem(Command command) {
    if (!command.hasSecondWord())
      System.out.println("take what?");
    else {
      String secondWord = command.getSecondWord();
      int index = currentRoom.itemInRoom(secondWord);
      if (index < 0) {
        boolean itemTaken = false;
        ArrayList<Item> itemList = currentRoom.getItemList();
        for (int i = 0; i < itemList.size(); i++) {
          Item item = itemList.get(i);
          if (item.getInnerItem() != null && item.getIsOpened() && item.getInnerItem().compareItemNames(secondWord)) {
            Item innerItem = item.getInnerItem();
            currentRoom.removeItem(innerItem);
            inventory.addItem(innerItem);
            System.out.println(secondWord + " taken");
            itemTaken = true;
          }
        }
        if (!itemTaken)
          System.out.println("There is no item with that name that you can see");
      } else {
        Item item = currentRoom.getItem(index);
        if (inventory.getTotalWeight() + item.getWeight() > inventory.getMaxWeight())
          System.out.println("You do not have enough room in your inventory for this item");
        else {
          currentRoom.removeItem(item);
          inventory.addItem(item);
          System.out.println(secondWord + " taken");
        }
      }
    }
  }

  /*
   * PrintYell: Allows the user to simulate yelling
   */
  private void printYell(Command command) {
    if (command.hasSecondWord())
      System.out.println(command.getSecondWord().toUpperCase() + "!!!!!");
    else
      System.out.println("AHHHHH!!!!");
    System.out.println("Feel better?");
  }

  // implementations of user commands:
  /**
   * Print out some help information. Prints the goal of the game
   * and a list of the command words.
   */
  private void printHelp() {
    System.out.println("Hello brave hero\nYour goal is to try and find the three starter pokemon and bring them to the top of the volcano\n\n");
    System.out.println("Your command words are:");
    parser.showCommands();
  }

  /*
   * specialCases: Checks for special cases in which something needs to be added to the end of a
   * room description
   */
  public String specialCases() {
    String currentRoomName = currentRoom.getRoomName();
    Room darkRoom = masterRoomMap.get("DARK_ROOM");
    Room southDeadEnd = masterRoomMap.get("SOUTH_DEAD_END");
    if (currentRoom.getRoomName().equals("East Hut") && darkRoom.getItem(darkRoom.itemInRoom("switch")).getIsOn())
      return "There is now a hole in the wall in the east";
    else if (currentRoomName.equals("South Dead End")
        && southDeadEnd.getItem(southDeadEnd.itemInRoom("door")).getIsOpened())
      return "There is an open wall in the west";
    if (currentRoom.getRoomName().equals("Field")) {
      if (currentRoom.getItem(currentRoom.itemInRoom("vine")).getIsOpened())
        return "There is a path east to a beach";
      else
        return "There is a vine blocking the path east";
    }
    if (currentRoomName.equals("Base of Volcano")) {
      if (currentRoom.getItem(currentRoom.itemInRoom("rocks")).getIsOpened())
        return "There is a winding path up to the peak of the volcano";
      else
        return "There is a pile of rocks blocking the path up to the peak of the volcano";
    } if(currentRoomName.equals("Outside Building")){
        if(currentRoom.getItem(currentRoom.itemInRoom("tree")).getIsOpened())
          return "There is an open path to a parking lot in the south";
        else
          return "In the north you see a tree blocking your path to a parking lot.";
    }
    else if (currentRoom.getRoomName().equals("East Side of Island")
        && currentRoom.getItem(currentRoom.itemInRoom("door")).getIsOpened())
      return ("There is a hole in the ground with a staircase leading down");
    else
      return "";
  }

  /**
   * Try to go to one direction. If there is an exit, enter the new room,
   * otherwise print an error message.
   */
  private void goRoom(Command command) {
    if (!command.hasSecondWord()) {
      // if there is no second word, we don't know where to go...
      System.out.println("Go where?");
      return;
    }
    String direction = command.getSecondWord();
    // Try to leave current room.
    Room nextRoom = currentRoom.nextRoom(direction);
    // Checks for specific cases where the user must possess an item in order to
    // move from one room to another
    if (nextRoom == null)
      System.out.println("There is no path leading that direction");
    else {
      String currentRoomName = currentRoom.getRoomName();
      String nextRoomName = nextRoom.getRoomName();
      if (currentRoomName.equals("Outside Building") && nextRoomName.equals("Inside Building")
          && inventory.findItem("cloak") < 0)
        System.out.println("You can't walk through walls, if only there was an item that let you do that ...");
      else if (currentRoomName.equals("Hidden Hut") && nextRoomName.equals("South Tree Top")
          && !currentRoom.getItem(currentRoom.itemInRoom("door")).getIsOpened())
        System.out.println("That door is locked");
      else if (currentRoomName.equals("Outside Building") && nextRoomName.equals("Parking Lot")
          && (!currentRoom.getItem(currentRoom.itemInRoom("tree")).getIsOpened()))
        System.out.println("There is a tree blocking that path");
      else if (currentRoomName.equals("East Hut") && nextRoomName.equals("North West Corner of Library")
          && !masterRoomMap.get("DARK_ROOM").getItem(masterRoomMap.get("DARK_ROOM").itemInRoom("switch")).getIsOn())
        System.out.println("There is no path leading that direction");
      else if (currentRoomName.equals("South Dead End") && nextRoomName.equals("Secret Layer")
          && !currentRoom.getItem(currentRoom.itemInRoom("door")).getIsOpened())
        System.out.println("There is no path leading that direction");
      else if (currentRoomName.equals("Field") && nextRoomName.equals("Beach")
          && !currentRoom.getItem(currentRoom.itemInRoom("vine")).getIsOpened())
        System.out.println("There is a vine blocking that direction");
      else if (currentRoomName.equals("East Side of Island") && nextRoomName.equals("Underground Bunker")
          && !currentRoom.getItem(currentRoom.itemInRoom("door")).getIsOpened())
        System.out.println("There is no path leading that direction");
      else if (currentRoomName.equals("Base of Volcano") && nextRoomName.equals("Top of Volcano")
          && !currentRoom.getItem(currentRoom.itemInRoom("rocks")).getIsOpened())
        System.out.println("That path is blocked by rocks");
      else {
        currentRoom = nextRoom;
        checkIfLit();
        if (!currentRoom.getIsLit())
          System.out.println(currentRoom.darkDescription());
        else
          System.out.println(currentRoom.longDescription() + specialCases());
      }
    }
  }
}