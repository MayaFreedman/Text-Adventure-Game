import java.util.ArrayList;

public class Pokemon {
    private String type;
    private int rarity;
    private ArrayList<String> moves;

    public Pokemon(String type, int rarity){
        this.type = type;
        this.rarity = rarity;
    }

    public String getType(){
        return type;
    }

    public int getRarity(){
        return rarity;
    }
}
