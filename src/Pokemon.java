public class Pokemon {
    private String name;
    private String move;
    private String moveDescription;

    public Pokemon(String name, String move, String moveDescription){
        this.name = name;
        this.move = move;
        this.moveDescription = moveDescription;
    }

    public String getMove(){
        return move;
    }

    public String getName(){
        return name;
    }

    public String getDescription(){
        return name + " description: A pokemon named " + name + "\nmove: " + move + "\n" + move + " allows you to " + moveDescription;
    }

    /*
    comparePokemon: Returns a boolean describing if the two items are the same
    their name
    */
    public boolean comparePokemon(Pokemon pokemon2){
        return this.name.equals(pokemon2.name);
    }
}
