package Model;

public class Countries {
    private int id;
    private String name;

    public Countries(int id, String name){
        this.id = id;
        this.name = name;
    }
    /**
     *
     * @return
     */
    public int getId(){
        return this.id;
    }
    /**
     *
     * @return
     */
    public String getName(){
        return this.name;
    }
}
