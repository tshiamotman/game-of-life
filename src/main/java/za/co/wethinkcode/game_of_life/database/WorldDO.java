package za.co.wethinkcode.game_of_life.database;

public class WorldDO {
    private int id;
    private String name;
    private int epoch;
    private int size;

    public void setId(int id){
        this.id = id;
    }

    public int getId(){
        return this.id;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public void setSize(int size){
        this.size = size;
    }

    public int getSize() {
        return size;
    }

    public void setEpoch(int epoch){
        this.epoch = epoch;
    }

    public int getEpoch() {
        return epoch;
    }
}
