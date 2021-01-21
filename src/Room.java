import java.util.ArrayList;
import java.util.HashMap;

public class Room {
  private String roomName;
  private String description;
  private HashMap<String, Room> exits; // stores exits of this room.
  private ArrayList<Item> itemList; // stores all items in this room
  private ArrayList<Pokemon> pokemonList; // stores all pokemon in this room
  private boolean hasLight; // represents if this room has a natural light source or not
  private boolean isLit; // represents the current light status of this room

  /*
   * Constructor: sets up a room with the defalt settings, including being lit and
   * having light
   */
  public Room() {
    roomName = "DEFAULT ROOM";
    description = "DEFAULT DESCRIPTION";
    pokemonList = new ArrayList<Pokemon>();
    exits = new HashMap<String, Room>();
    itemList = new ArrayList<Item>();
    isLit = true;
    hasLight = true;
  }

  /*
   * setExit: sets the exits of the room
   */
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

  /*
   * addItems: adds Items to the room by filling the ArrayList itemsList with
   * items
   */
  public void addItems(Item items[]) {
    for (int i = 0; i < items.length; i++) {
      itemList.add(items[i]);
    }
  }

  /*
   * addPokemon: adds Pokemon to the room by filling the ArrayList PokemonList
   * with Pokemon
   */
  public void addPokemon(Pokemon pokemon[]) {
    for (int i = 0; i < pokemon.length; i++) {
      pokemonList.add(pokemon[i]);
    }
  }

  /*
   * addPokemon: adds a single Pokemon to the room
   */
  public void addPokemon(Pokemon pokemon) {
    pokemonList.add(pokemon);
  }

  /*
   * removePokemon: removes a given pokemon from the room
   */
  public void removePokemon(Pokemon pokemon) {
    pokemonList.remove(pokemon);
  }

  /*
   * getPokemon: returns a pokemon based on a given index
   */
  public Pokemon getPokemon(int index) {
    return pokemonList.get(index);
  }

  /*
   * description: Return a long description of this room, on the form: You are in
   * the kitchen. Exits: north west
   */
  public String description() {
    return "Room: " + roomName + "\n\n" + description + " " + itemDescription() + " " + pokemonDescription();
  }

  /*
   * itemDescription: Creates a String of all the pickupable items in the room in
   * the form: You see a ____ sitting on the ground
   */
  public String itemDescription() {
    String itemDescription = "";
    int i;
    ArrayList<Item> pickupableItemList = new ArrayList<Item>();
    for (Item item : itemList) {
      if (item.getWeight() <= 5)
        pickupableItemList.add(item);
    }
    if (pickupableItemList.size() > 0) {
      itemDescription = "You see a";
      for (i = 0; i < pickupableItemList.size() - 1; i++) {
        itemDescription += " " + pickupableItemList.get(i).getName() + ",";
      }
      if (!(pickupableItemList.size() == 1))
        itemDescription += " and";
      itemDescription += " " + pickupableItemList.get(i).getName();
      itemDescription += " sitting on the ground.";
    }
    return itemDescription;
  }

  /*
   * pokemonDescription: Creates a String of all the pokemon in the room in the
   * form: There is a ____ sitting in front of you
   */
  public String pokemonDescription() {
    String pokemonDescription = "";
    int i;
    if (pokemonList.size() > 0) {
      pokemonDescription = "There is a";
      for (i = 0; i < pokemonList.size() - 1; i++) {
        pokemonDescription += " " + pokemonList.get(i).getName() + ",";
      }
      if (!(pokemonList.size() == 1))
        pokemonDescription += " and";
      pokemonDescription += " " + pokemonList.get(i).getName();
      pokemonDescription += " sitting in front of you.";
    }
    return pokemonDescription;
  }

  /*
   * darkDescription: Returns a description of this room in the event it is not
   * lit and the player cannot see anything
   */
  public String darkDescription() {
    return "Room: " + roomName + "\n\n" + "It is dark in this room, you cannot see anything";
  }

  /*
   * itemInRoom: Returns the index of the item with that name in the ArrayList
   * itemList, if an item with that name isn't in the room, returns -1
   */
  public int itemInRoom(String name) {
    for (int i = 0; i < itemList.size(); i++) {
      if (itemList.get(i).getName().equals(name))
        return i;
    }
    return -1;
  }

  /*
   * pokemonInRoom: Returns the index of a pokemon with that name in the ArrayList
   * pokemonList if it's in the room, if it's not in the room, returns -1
   */
  public int pokemonInRoom(String name) {
    for (int i = 0; i < pokemonList.size(); i++) {
      if (pokemonList.get(i).getName().equals(name))
        return i;
    }
    return -1;
  }

  /*
   * removeItem: removes a given item from the room
   */
  public void removeItem(Item item) {
    itemList.remove(item);
  }

  public ArrayList<Item> getItemList() {
    return itemList;
  }

  /*
   * takeItem: removes the item from a specific index in the ArrayList itemList
   * and returns it
   */
  public Item takeItem(int index) {
    Item item = itemList.get(index);
    itemList.remove(index);
    return item;
  }

  /*
   * addItem: Adds a specific item to the room
   */
  public void addItem(Item item) {
    itemList.add(item);
  }

  /*
   * getItem: Gets the item at given index in the ArrayList itemList
   */
  public Item getItem(int index) {
    return itemList.get(index);
  }

  /**
   * nextRoom: Return the room that is reached if we go from this room in
   * direction "direction". If there is no room in that direction, return null.
   */
  public Room nextRoom(String direction) {
    return (Room) exits.get(direction);
  }

  public String getRoomName() {
    return roomName;
  }

  /*
   * changeIsLit: changes the light status in the room (true meaning lit, false
   * meaning dark)
   */
  public void changeIsLit(boolean isLit) {
    this.isLit = isLit;
  }

  /*
   * getIsLit: Returns if the room is lit or not
   */
  public boolean getIsLit() {
    return isLit;
  }

  /*
   * getHasLight: Returns if the room has a natural light source or not
   */
  public boolean getHasLight() {
    return hasLight;
  }

  /*
   * setHasLight: does a thing
   */
  public void setHasLight(boolean hasLight) {
    this.hasLight = hasLight;
  }

  /*
   * setRoomName: sets the name of this room
   */
  public void setRoomName(String roomName) {
    this.roomName = roomName;
  }

  /*
   * getDescription: returns the description of this room
   */
  public String getDescription() {
    return description;
  }

  /*
   * setDescription: sets the description of this room
   */
  public void setDescription(String description) {
    this.description = description;
  }

  /*
   * getPokemon: returns an arrayList full of the pokemon in this room
   */
  public ArrayList<Pokemon> getPokemon() {
    return pokemonList;
  }
}
