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
  public String reset = "\u001B[0m";
  public String red = "\u001B[31m";
  public String blue = "\u001B[34m";

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

  public void setItems() {
    Item items[] = { new Item("couch", 25), new Item("table", 25), new Item("cloak", 1), new Item("chest", 25, "false",
        "You open the chest, there is a cloak sitting inside the chest", new Item("cloak_V2", 1)) };
    masterRoomMap.get("NORTH_TREE_TOP").addItems(items);
  }

  /**
   * Main play routine. Loops until end of play.
   */
  public void play() {
    inventory = new Inventory(5);
    printWelcome();
    // Enter the main command loop. Here we repeatedly read commands and
    // execute them until the game is over.

    boolean finished = false;
    while (!finished) {
      Command command = parser.getCommand();
      finished = processCommand(command);
    }
    System.out.println("Thank you for playing.  Good bye.");
  }

  /**
   * Print out the opening message for the player.
   */
  private void printWelcome() {
    System.out.println();
    System.out.println(red + "W͎̿h͕̀ỳ͢ ̨̇d͚̍o͎̅ ͕̊ẁ͉e̢̚ ̳̄p͓͐l̺͝ạ̔y̼͐ ͔̋t̞͝h̬͞ȋ̫s̘͐ ̪͊g̻͑a̯͆m̹͂è͖" + reset);
    System.out.println("Welcome to Zork!");
    System.out.println("Zork is a new, incredibly boring adventure game.");
    System.out.println("Type 'help' if you need help.");
    System.out.println();
    System.out.println(currentRoom.longDescription());
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
    // if (commandWord.equals("test") && command.getSecondWord().equals("test2") &&
    // command.getThirdWord().equals("test3"))
    // System.out.println("This worked??? Yay!");
    if (commandWord.equals("help"))
      printHelp();
    else if (commandWord.equals("go"))
      goRoom(command);
    else if (commandWord.equals("quit")) {
      if (command.hasSecondWord())
        System.out.println("Quit what?");
      else
        return true; // signal that we want to quit
    } else if (commandWord.equals("eat")) {
      System.out.println("Do you really think you should be eating at a time like this?");
    } else if (commandWord.equals("yell")) {
      printYell(command);
    } else if (commandWord.equals("look"))
      System.out.println(currentRoom.longDescription());
    else if (commandWord.equals("take"))
      takeItem(command);
    else if (commandWord.equals("drop"))
      dropItem(command);
    else if (commandWord.equals("view"))
      viewInventory(command);
    else if (commandWord.equals("open"))
      openItem(command);
    return false;
  }

  private void openItem(Command command) {
    if (!command.hasSecondWord())
      System.out.println("What do you want to open?");
    else {
      int index = currentRoom.inRoom(command.getSecondWord());
      if (index < 0)
        System.out.println("There is no item with that name in this room");
      else {
        Item item = currentRoom.getItem(index);
        if (item.getIsOpened() == null)
          System.out.println("This item can't be oppened");
        else if (item.getIsOpened().equals("true"))
          System.out.println("This item is already oppened");
        else
          System.out.println(item.getOpenMessage());
        item.setIsOpened("true");
      }
    }
  }

  // viewInventory: Allows the user to view all items in their inventory
  private void viewInventory(Command command) {
    if (!command.hasSecondWord())
      System.out.println("What do you want to view?");
    else if (!command.getSecondWord().equals("inventory"))
      System.out.println("View what?");
    else
      System.out.println(inventory.showInventory());
  }

  /*
   * takeItem: Allows the user to remove an item from their inventory and drop it
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
        System.out.println("Item dropped");
      }

    }
  }

  /*
   * takeItem: Allows the user to take an item if it's in the room, not in a closed object, and not too
   * heavy that it's inventory weight is over a specific value (maxWeight)
   */
  private void takeItem(Command command) {
    if (!command.hasSecondWord())
      System.out.println("take what?");
    else {
      String secondWord = command.getSecondWord();
      int index = currentRoom.inRoom(secondWord);
      if (index < 0) {
        boolean itemTaken = false;
        ArrayList<Item> itemList = currentRoom.getItemList();
        for (int i = 0; i < itemList.size(); i++) {
          Item item = itemList.get(i);
          if (item.getIsOpened() != null && item.getIsOpened().equals("true") && item.getInnerItem().compareItemNames(secondWord)) {
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
   * Print out some help information. Here we print some stupid, cryptic message
   * and a list of the command words.
   */
  private void printHelp() {
    System.out.println("You are lost. You are alone. You wander");
    System.out.println("around at Monash Uni, Peninsula Campus.");
    System.out.println();
    System.out.println("Your command words are:");
    parser.showCommands();
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
    if (nextRoom == null)
      System.out.println("There is no door!");
      //Checks for specific cases where the user must possess an item in order to move from one room to another
    else if (currentRoom.getRoomName().equals("Room 1") && nextRoom.getRoomName().equals("Room 2")
        && inventory.findItem("cloak") < 0)
      System.out.println("You can't walk through walls, if only there was an item that let you do that ...");
    else {
      currentRoom = nextRoom;
      System.out.println(currentRoom.longDescription());
    }
  }
}