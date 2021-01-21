public class Pokemon {
    private String name;
    private String move;
    private String moveDescription;

    /*
     * constructor: assigns this pokemon's name, move and move description
     */
    public Pokemon(String name, String move, String moveDescription) {
        this.name = name;
        this.move = move;
        this.moveDescription = moveDescription;
    }

    /*
     * getMove: returns this pokemon's move
     */
    public String getMove() {
        return move;
    }

    /*
     * getName: returns this pokemon's name
     */
    public String getName() {
        return name;
    }

    /*
     * getDescription: returns a description of the pokemon, including their name,
     * move and move description
     */
    public String getDescription() {
        return name + " description: A pokemon named " + name + "\nmove: " + move + "\n" + move + " allows you to "
                + moveDescription;
    }

    /*
     * comparePokemon: Returns a boolean describing if the two pokemon have the same
     * name
     */
    public boolean comparePokemon(Pokemon pokemon2) {
        return this.name.equals(pokemon2.name);
    }
}
