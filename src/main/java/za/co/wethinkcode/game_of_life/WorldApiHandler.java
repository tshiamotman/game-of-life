package za.co.wethinkcode.game_of_life;

import io.javalin.http.Context;
import kong.unirest.HttpStatus;
import za.co.wethinkcode.game_of_life.database.DBConnect;
import za.co.wethinkcode.game_of_life.database.StateDO;
import za.co.wethinkcode.game_of_life.database.WorldDO;
import za.co.wethinkcode.game_of_life.domain.World;
import za.co.wethinkcode.game_of_life.http.requests.CreateRequest;
import za.co.wethinkcode.game_of_life.http.responses.WorldResponse;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class WorldApiHandler {

    public void createNew(Context context) {
        CreateRequest createRequest = context.bodyAsClass(CreateRequest.class);
        World world = World.define(
                createRequest.getWorldName(),
                createRequest.getWorldSize(),
                createRequest.getWorldInitialState()
        );

        DBConnect database = new DBConnect();

        System.out.print("Saving world to database...");
        if(database.addWorld(
                world.getWorldName(),
                world.getEpoch(),
                world.getWorldSize(),
                world.getState())) {
            System.out.println("success!");
        } else {
            System.out.println("Failed");
        }

        context.status(HttpStatus.CREATED);

        context.json(new WorldResponse(
                database.getWorld(world.getWorldName()).getId(),
                world.getState()
        ));
    }

    private Map toJson(WorldDO world){
        Map json = new HashMap();
        json.put("epoch", world.getEpoch());
        json.put("name", world.getName());
        json.put("id", world.getId());
        return json;
    }

    public void getAllWorlds(Context context) {
        DBConnect database = new DBConnect();

        context.status(HttpStatus.CREATED);

        List<Object> list = new ArrayList<>();
        for(WorldDO world:database.getWorlds()){
            list.add(toJson(world));
            System.out.println(list);
        }
        context.json(list);
    }

    public void handleNext(Context context) {
        int id = Integer.parseInt(context.pathParam("id"));

        DBConnect database = new DBConnect();

        WorldDO worldDO = database.getWorldId(id);

        World world = new World(worldDO.getName(), worldDO.getSize(), worldDO.getEpoch());
        world.setCells(database.getState(worldDO.getId(), world.getEpoch(), world.getWorldSize()));

        world.next();
        worldDO.setEpoch(world.getEpoch());

        Map response = toJson(worldDO);
        response.put("state", world.getState());

        context.status(HttpStatus.CREATED);
        context.json(response);
    }

    public void handleGetEpoch(Context context){
        int id = Integer.parseInt(context.pathParam("id"));
        int epoch = Integer.parseInt(context.pathParam("epoch"));

        DBConnect database = new DBConnect();

        WorldDO worldDO = database.getWorldId(id);

        World world;
        if(epoch <= worldDO.getEpoch()){
            world = new World(worldDO.getName(), worldDO.getSize(), epoch);
            world.setCells(database.getState(worldDO.getId(), epoch, world.getWorldSize()));
        } else {
            world = new World(worldDO.getName(), worldDO.getSize(), worldDO.getEpoch());
            world.setCells(database.getState(worldDO.getId(), world.getEpoch(), world.getWorldSize()));
            while (world.next()) {
                if(epoch == world.getEpoch()) break;
            }
        }
        worldDO.setEpoch(world.getEpoch());

        Map response = toJson(worldDO);
        response.put("state", world.getState());

        context.status(HttpStatus.CREATED);
        context.json(response);
    }
}
