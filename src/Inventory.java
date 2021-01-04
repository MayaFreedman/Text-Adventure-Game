import java.util.ArrayList;

public class Inventory {
    private ArrayList<Item> inventory;
    private int maxWeight;

    public Inventory(int maxWeight) {
        this.maxWeight = maxWeight;
        inventory = new ArrayList<Item>();
    }

    /*
     * getTotalWeight: calculates the total weight of all the items in the player's
     * inventory
     */
    public int getTotalWeight() {
        int totalWeight = 0;
        for (int i = 0; i < inventory.size(); i++) {
            totalWeight += inventory.get(i).getWeight();
        }
        return totalWeight;
    }

    // removeItem: Removes a specified item from the players inventory
    public void removeItem(Item item) {
        for (int i = 0; i < inventory.size(); i++) {
            if (item.compareItems(inventory.get(i)))
                inventory.remove(i);
        }
    }

    
    // Gets the item at given index in the ArrayList itemList
    public Item getItem(int index){
        return inventory.get(index);
      }
    
    /*findItem: Returns the index of the item with a given name in the ArrayList inventory,
    if an item with that name isn't in inventory, returns -1
    */
    public int findItem(String name) {
        for (int i = 0; i < inventory.size(); i++) {
            if (inventory.get(i).getName().equals(name))
                return i;
        }
        return -1;
    }

    // addItem: adds a specified item to the players inventory
    public void addItem(Item item) {
        inventory.add(item);
    }

    // toString: prints out the names of all items in inventory
    public String showInventory() {
        if (inventory.size() == 0)
            return "You have no items in your inventory";

        String items = "Items in inventory:";
        for (Item item : inventory) {
            items += " " + item.getName();
        }
        return items;
    }

    // getMaxWeight: return the max weight the inventory can hold
    public int getMaxWeight() {
        return maxWeight;
    }
}
