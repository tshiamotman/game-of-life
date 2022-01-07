package za.co.wethinkcode.game_of_life.database;
import net.lemnik.eodsql.EoDException;
import net.lemnik.eodsql.QueryTool;

import org.jetbrains.annotations.Nullable;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class DBConnect {
    private final String dbUrl = "jdbc:sqlite:gameOfLife.sqlite";

    public DBConnect() throws SQLException {
        try(Connection connection = DriverManager.getConnection(dbUrl)) {
            createTables(connection);
        }
    }

    private String serializeState(int[][] state){
        StringBuilder serial = new StringBuilder();
        for(int[] row:state){
            for(int i:row){
                serial.append(i);
            }
        }
        return serial.toString();
    }

    private int[][] deserialize(String state, int worldSize){
        int rows = state.length()/worldSize;
        List serial = new ArrayList<>();
        for(int i=0; i < rows; i++){
            List row = new ArrayList<Integer>();
            for(int j = 0; j < worldSize; j++){
                row.add(Integer.parseInt(
                        String.valueOf(state.charAt((i * worldSize) + j))
                ));
            }
            serial.add(row.toArray());
        }
        return (int[][]) serial.toArray();
    }

    public boolean addWorld(String name, int epoch, int size, int[][] state){
        try(Connection connection = DriverManager.getConnection(dbUrl)){

            WorldDAI dao = QueryTool.getQuery(connection, WorldDAI.class);

            int checkWorld = dao.worldExists(name);

            if(checkWorld == 0) {

                dao.addWorld(name, epoch, size);

                int worldId = dao.worldExists(name);

                System.out.println("worldID: " + worldId);

                dao.addState(epoch, worldId, serializeState(state));
                return true;
            } else {
                System.out.println("World name already exists");
                return false;
            }
        } catch (SQLException e){
            throw new RuntimeException( e );
        }
    }

    public WorldDO getWorld(int id){
        try (Connection connection = DriverManager.getConnection(dbUrl)){
            WorldDAI dao = QueryTool.getQuery( connection, WorldDAI.class );

            WorldDO world = dao.getWorld(id);

            return world;
        } catch (SQLException e){
            throw new RuntimeException( e );
        }
    }

    public List<WorldDO> getWorlds(){
        try (Connection connection = DriverManager.getConnection(dbUrl)){
            WorldDAI dao = QueryTool.getQuery( connection, WorldDAI.class );

            return dao.getAllWorlds();
        }  catch (EoDException eee){
            List<WorldDO> x = catchTableError();
            if (x != null) return x;
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        return null;
    }

    public int[][] getState(int worldId, int epoch, int worldSize){
        try (Connection connection = DriverManager.getConnection(dbUrl)) {
            WorldDAI dao = QueryTool.getQuery(connection, WorldDAI.class);

            StateDO state = dao.getState(worldId, epoch);

            return deserialize(state.getState(), worldSize);
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        return null;
    }

    @Nullable
    private List<WorldDO> catchTableError() {
        try( Connection connection = DriverManager.getConnection(dbUrl)) {

            createTables(connection);

            return new ArrayList<>();
        } catch ( SQLException ee ){
            System.err.println( ee.getMessage() );
        }
        return null;
    }

    private void createTables(Connection connection){
        try( final Statement stmt = connection.createStatement()) {
            System.out.println("Creating tables...");
            stmt.executeUpdate("CREATE TABLE IF NOT EXISTS worlds (" +
                    "id INTEGER NOT NULL PRIMARY KEY AUTOINCREMENT," +
                    "name TEXT NOT NULL," +
                    "epoch INTEGER NOT NULL," +
                    "size INTEGER NOT NULL" +
                    ")");
            System.out.println("Success creating worlds table!");

            stmt.executeUpdate("CREATE TABLE IF NOT EXISTS states (" +
                    "id INTEGER NOT NULL PRIMARY KEY AUTOINCREMENT," +
                    "worldId INTEGER NOT NULL," +
                    "epoch INTEGER NOT NULL," +
                    "state TEXT NOT NULL" +
                    ")");
            System.out.println("Success creating states table!");
        } catch (SQLException e){
            System.err.println( e.getMessage() );
        }
    }
}