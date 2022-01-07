package za.co.wethinkcode.game_of_life;

import io.javalin.http.Context;
import kong.unirest.HttpStatus;
import za.co.wethinkcode.game_of_life.domain.World;
import za.co.wethinkcode.game_of_life.http.requests.CreateRequest;
import za.co.wethinkcode.game_of_life.http.responses.WorldResponse;

public class WorldApiHandler {

    public void createNew(Context context) {
        CreateRequest createRequest = context.bodyAsClass(CreateRequest.class);
        World world = World.define(createRequest.getWorldName(), createRequest.getWorldSize(), createRequest.getWorldInitialState());

        context.status(HttpStatus.CREATED);

        context.json(new WorldResponse(1, world.getState()));
    }
}
// <SOLUTION>
