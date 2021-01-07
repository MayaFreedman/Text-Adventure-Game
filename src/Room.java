/*
 * Class Room - a room in an adventure game.
 *
 * Author:  Michael Kolling
 * Version: 1.1
 * Date:    August 2000
 * 
 * This class is part of Zork. Zork is a simple, text based adventure game.
 *
 * "Room" represents one location in the scenery of the game.  It is 
 * connected to at most four other rooms via exits.  The exits are labelled
 * north, east, south, west.  For each direction, the room stores a reference
 * to the neighbouring room, or null if there is no exit in that direction.
 */
import java.util.Set;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;

public class Room {
  private String roomName;
  private String description;
  private HashMap<String, Room> exits; // stores exits of this room.
  private ArrayList <Item> itemList; // stores all items in this room
  private ArrayList <Pokemon> pokemonList; // stores all pokemon in this room
  private boolean isLit;

  /**
   * Create a room described "description". Initially, it has no exits.
   * "description" is something like "a kitchen" or "an open court yard".
   */
  public Room(String description) {
    this.description = description;
    exits = new HashMap<String, Room>();
  }

  public Room() {
    // default constructor.
    roomName = "DEFAULT ROOM";
    description = "DEFAULT DESCRIPTION";
    exits = new HashMap<String, Room>();
    itemList = new ArrayList <Item>();
    isLit = true;
  }
  

  public void setExit(char direction, Room r) throws Exception {
    String dir = "";
    switch (direction) {
      case 'E':
        dir = "east";
        break;
      case 'W':
        dir = "west";
        break;
      case 'S':
        dir = "south";
        break;
      case 'N':
        dir = "north";
        break;
      case 'U':
        dir = "up";
        break;
      case 'D':
        dir = "down";
        break;
      default:
        throw new Exception("Invalid Direction");
    }

    exits.put(dir, r);
  }

  /**
   * Define the exits of this room. Every direction either leads to another room
   * or is null (no exit there).
   */
  public void setExits(Room north, Room east, Room south, Room west, Room up, Room down) {
    if (north != null)
      exits.put("north", north);
    if (east != null)
      exits.put("east", east);
    if (south != null)
      exits.put("south", south);
    if (west != null)
      exits.put("west", west);
    if (up != null)
      exits.put("up", up);
    if (up != null)
      exits.put("down", down);
  }

  /*
  addItems: adds Items to the room by filling the ArrayList itemsList with items
  */
  public void addItems(Item items[]){
    for(int i = 0; i < items.length; i++){
      itemList.add(items[i]);
    }
  }

  /*
  *addPokemon: adds Pokemon to the room by filling the ArrayList PokemonList with Pokemon
  */
  public void addPokemon(Pokemon pokemon[]){
    for(int i = 0; i < pokemon.length; i++){
      pokemonList.add(pokemon[i]);
    }
  }


  /**
   * Return the description of the room (the one that was defined in the
   * constructor).
   */
  public String shortDescription() {
    return "Room: " + roomName + "\n\n" + description;
  }

  /**
   * Return a long description of this room, on the form: You are in the kitchen.
   * Exits: north west
   */
  public String longDescription() {
    String x = "";
    for(Item i : itemList){
      x += " " + i.getName();
    }
    return "Room: " + roomName + "\n\n" + description + "\n" + exitString() + "\nItems in room :" + x;
  }

  /*
  * Returns a description of this room in the event it is not lit and the
  * player cannot see anything
  */
  public String darkDescription(){
    return "Room: " + roomName + "\n\n" + "It is dark in this room, you cannot see anything";
  }

  /**
   * Return a string describing the room's exits, for example "Exits: north west
   */
  private String exitString() {
    String returnString = "Exits:";
    Set keys = exits.keySet();
    for (Iterator iter = keys.iterator(); iter.hasNext();)
      returnString += " " + iter.next();
    return returnString;
  }

  /* 
  inRoom: Returns the index of the item with that name in the ArrayList itemList,
  if an item with that name isn't in itemList, returns -1
  */
  public int inRoom(String name){
    for(int i = 0; i < itemList.size(); i++){
      if(itemList.get(i).getName().equals(name))
        return i;
    }
    return -1;
  }
  
  //removeItem: removes a given item from the room
  public void removeItem(Item item){
      itemList.remove(item);
  }

  public ArrayList <Item> getItemList(){
    return itemList;
  }


  /*
  takeItem: removes the item from a specific index in the 
  ArrayList itemList and returns it
  */
  public Item takeItem(int index){
        Item item = itemList.get(index);
        itemList.remove(index);
        return item;
  }

  //Adds a specific item to the room 
  public void addItem(Item item){
    itemList.add(item);
  }

  /*
  Gets the item at given index in the ArrayList itemList
  */
  public Item getItem(int index){
    return itemList.get(index);
  }

  /**
   * Return the room that is reached if we go from this room in direction
   * "direction". If there is no room in that direction, return null.
   */
  public Room nextRoom(String direction) {
    return (Room) exits.get(direction);
  }

  public String getRoomName() {
    return roomName;
  }

  /*
  changes the light status in the room (true -> lit, false -> dark)
  */
  public void changeIsLit(boolean isLit){
    this.isLit = isLit;
  }

  /*
  * Returns if the room is lit or not (true -> it's lit, false -> it's not lit)
  */
  public boolean getIsLit(){
    return isLit;
  }

  public void setRoomName(String roomName) {
    this.roomName = roomName;
  }

  public String getDescription() {
    return description;
  }

  public void setDescription(String description) {
    this.description = description;
  }
}
