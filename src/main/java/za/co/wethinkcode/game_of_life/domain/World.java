package za.co.wethinkcode.game_of_life.domain;

import kong.unirest.json.JSONObject;

public class World {
    private int[][] cells;
    private final String worldName;
    private Integer worldSize;
    private int epoch;

    public World(String worldName, Integer worldSize, int epoch) {
        this.worldName = worldName;
        this.worldSize = worldSize;
        this.epoch = epoch;
    }

    public static World define(String worldName, Integer worldSize, int[][] worldInitialState) {
        World world = new World(worldName, worldSize, 0);
        world.setCells(worldInitialState);

        return world;
    }

    public String getWorldName() {
        return worldName;
    }

    public int getEpoch() {
        return epoch;
    }

    public Integer getWorldSize() {
        return worldSize;
    }

    public void setCells(int[][] cells) {
        this.cells = cells;
    }

    public int[][] getState() {
        return this.cells;
    }

    public void next() {
        this.cells = new Generation().solve(cells);
        this.epoch++;
    }

    public JSONObject toJson(){
        JSONObject json = new JSONObject();
        json.put("name", worldName);
        json.put("epoch", epoch);
        json.put("state", cells);
        return json;
    }
}
