package za.co.wethinkcode.game_of_life.http.responses;

import com.fasterxml.jackson.annotation.JsonCreator;

public class WorldResponse {
    private final int id;
    private final int epoch = 0;
    private final int[][] state;

    @JsonCreator
    public WorldResponse(final int worldId, final int[][] state) {
        this.id = worldId;
        this.state = state;
    }

    public int getId() {
        return id;
    }

    public int[][] getState() {
        return state;
    }
}
