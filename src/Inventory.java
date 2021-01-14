import java.util.ArrayList;

public class Inventory {
    private ArrayList<Item> items;
    private ArrayList<Pokemon> pokemonList;
    private int maxWeight;

    public Inventory(int maxWeight) {
        this.maxWeight = maxWeight;
        items = new ArrayList<Item>();
        pokemonList = new ArrayList <Pokemon>();
    }

    /*
     * getTotalWeight: calculates the total weight of all the items in the player's
     * inventory
     */
    public int getTotalWeight() {
        int totalWeight = 0;
        for (int i = 0; i < items.size(); i++) {
            totalWeight += items.get(i).getWeight();
        }
        return totalWeight;
    }

    // removeItem: Removes a specified item from the players inventory
    public void removeItem(Item item) {
        for (int i = 0; i < items.size(); i++) {
            if (item.compareItems(items.get(i)))
                items.remove(i);
        }
    }

    // Gets the item at given index in the ArrayList itemList
    public Item getItem(int index) {
        return items.get(index);
    }

    /*
     * findItem: Returns the index of the item with a given name in the ArrayList
     * inventory, if an item with that name isn't in inventory, returns -1
     */
    public int findItem(String name) {
        for (int i = 0; i < items.size(); i++) {
            if (items.get(i).getName().equals(name))
                return i;
        }
        return -1;
    }

    // addPokemon: adds a specified item to the players inventory
    public void addPokemon(Pokemon pokemon) {
        pokemonList.add(pokemon);
    }

    // removePokemon: Removes a specified item from the players inventory
    public void removePokemon(Pokemon pokemon) {
        for (int i = 0; i < items.size(); i++) {
            if (pokemon.comparePokemon(pokemonList.get(i)))
                pokemonList.remove(i);
        }
    }

    // Gets the Pokemon at a given index in the ArrayList pokemonList
    public Pokemon getPokemon(int index) {
        return pokemonList.get(index);
    }

    /*
     * findPokemon: Returns the index of the pokemon with a given name in the
     * ArrayList inventory, if an item with that name isn't in inventory, returns -1
     */
    public int findPokemon(String name) {
        for (int i = 0; i < pokemonList.size(); i++) {
            if (pokemonList.get(i).getName().equals(name))
                return i;
        }
        return -1;
    }

    // addItem: adds a specified item to the players inventory
    public void addItem(Item item) {
        items.add(item);
    }

    // toString: prints out the names of all items in inventory
    public String showInventory() {
        if (items.size() == 0 && pokemonList.size() ==0)
            return "You have no items in your inventory";

        String message = "Things in inventory:";
        for (Item item : items) {
            message += " " + item.getName();
        }

        for (Pokemon pokemon : pokemonList){
            message += " " + pokemon.getName();
        }
        return message;
    }

    // getMaxWeight: return the max weight the inventory can hold
    public int getMaxWeight() {
        return maxWeight;
    }
}
