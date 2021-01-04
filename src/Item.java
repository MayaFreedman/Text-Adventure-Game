public class Item {
    private String name;
    private int weight;
    private String isOpened;
    private String openMessage;
    private Item innerItem;
    
    
    /*
    * Constructor: Used for ordinary Items that only need a name and weight
    */
    public Item(String name, int weight){
        this.name = name;
        this.weight = weight;
    }

    /*
    * Constructor: Used for Items that open and close
    */    
    public Item(String name, int weight, String isOpened, String openMessage, Item innerItem){
        this.name = name;
        this.weight = weight;
        this.isOpened = isOpened;
        this.openMessage = openMessage;
        this.innerItem = innerItem;
    }

    public String getName(){
        return name;
    }

    public int getWeight(){
        return weight;
    }

    public String getIsOpened(){
        return isOpened;
    }

    public String getOpenMessage(){
        return openMessage;
    }

    public void setIsOpened(String isOpened){
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
