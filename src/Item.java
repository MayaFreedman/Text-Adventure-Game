public class Item {
    private String name;
    private int weight;
    private boolean isOpened;
    private String openMessage;
    private Item innerItem;
    private String description;
    private String readMessage;
    private boolean isOn;
    private boolean canTurnOn;
    private String code;
    private boolean isLocked;
    
    /*
    * Constructor: Used for ordinary items that only need a name and weight (items that cannot be picked up)
    */
    public Item(String name, int weight){
        this.name = name;
        this.weight = weight;
    }

    /*
    * Constructor: Used for ordinary items that need a name, weight, description and a message for 
    * when it's in a room (Items that can be picked up)
    */
    public Item(String name, int weight, String description){
        this.name = name;
        this.weight = weight;
        this.description = description;
    }

    /*
    * Constructor: Used for items that open and close
    */    
    public Item(String name, int weight, boolean isOpened, String openMessage, Item innerItem, String description){
        this.name = name;
        this.weight = weight;
        this.isOpened = isOpened;
        this.openMessage = openMessage;
        this.innerItem = innerItem;
        this.description = description;
    }

    /*
    * Constructor: Used for items that can be read by the user
    */
    public Item(String name, int weight, String readMessage, String description){
        this.name = name;
        this.weight = weight;
        this.readMessage = readMessage;
        this.description = description;
    }

    /*
    * Constructor: Used for items that can be turned on and off
    */
    public Item(String name, int weight, boolean canTurnOn, boolean isOn, String description){
        this.name = name;
        this.weight = weight;
        this.canTurnOn = true;
        this.isOn = isOn;
        this.description = description;
    }

    /*
    * Constructor: Used for items that can take in codes
    */
    public Item(String name, String code, int weight, boolean isLocked){
        this.code = code;
        this.name = name;
        this.weight = weight;
        this.isLocked = isLocked;
    }

    /*
    * Returns whether or not this item is locked, (returns false if this item
    * cannot lock)
    */
    public boolean getIsLocked(){
        return isLocked;
    }

    /*
    * Changes isLocked to ____
    */
    public void changeIsLocked(boolean isLocked){
        this.isLocked = isLocked;
    }

    /*
    * Returns the code of this item, returns null if this item does not 
    * have a code
    */
    public String getCode(){
        return code;
    }

    /*
    Returns the on (true) or off(false) status of this item, returns null if it can't
    turn on and off
    */
    public boolean getIsOn(){
        return isOn;
    }

    /*
    Changes the status of the item to off (false) or on (true)
    */
    public void changeIsOn(boolean isOn){
        this.isOn = isOn;
    }

    /*
    *Returns a boolean describing whether or not this item has the ability to turn on
    */
    public boolean getCanTurnOn(){
        return canTurnOn;
    }

    /*
    Returns this item's read message
    */
    public String getReadMessage(){
        return readMessage;
    }

    /*
    * Returns a description of this item
    */
    public String getDescription(){
        return name + " description: " + description;
    }

    public String getName(){
        return name;
    }

    public int getWeight(){
        return weight;
    }

    public boolean getIsOpened(){
        return isOpened;
    }

    public String getOpenMessage(){
        return openMessage;
    }

    public void setIsOpened(boolean isOpened){
        this.isOpened = isOpened;
    }

    public Item getInnerItem(){
        return innerItem;
    }

    /*
    comapreItems: Returns a boolean describing if the two items are the same
    (based on their name and weight)
    */
    public boolean compareItems(Item item2){
        return this.name.equals(item2.name) && this.weight == item2.weight;
    }

    /*
    * comapreItemNames: Returns a boolean describing if this item has the same name
    * as a given String
    */
    public boolean compareItemNames(String itemName){
        return name.equals(itemName);
    }
}
