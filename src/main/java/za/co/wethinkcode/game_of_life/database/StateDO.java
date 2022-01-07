package za.co.wethinkcode.game_of_life.database;

public class StateDO {
    private int id;
    private int epoch;
    private int worldId;
    private String state;

    public void setId(int id){
        this.id = id;
    }

    public int getId(){
        return this.id;
    }

    public void setEpoch(int epoch){
        this.epoch = epoch;
    }

    public int getEpoch() {
        return epoch;
    }

    public void setWorldId(int id){
        this.worldId = id;
    }

    public int getWorldId() {
        return worldId;
    }

    public void setState(String state) {
        this.state = state;
    }

    public String getState() {
        return state;
    }
}
