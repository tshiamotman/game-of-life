package za.co.wethinkcode.game_of_life.database;

import net.lemnik.eodsql.BaseQuery;
import net.lemnik.eodsql.Select;
import net.lemnik.eodsql.Update;

import java.util.List;

public interface WorldDAI extends BaseQuery {

    @Select("SELECT * FROM worlds WHERE id = ?{1}")
    WorldDO getWorld(int id);

    @Select("SELECT * FROM worlds WHERE name = ?{1}")
    WorldDO getWorld(String name);

    @Select("SELECT id, name, epoch FROM worlds")
    List<WorldDO> getAllWorlds();

    @Select("SELECT * FROM states WHERE worldId = ?{1} AND epoch = ?{2}")
    StateDO getState(int worldId, int epoch);

    @Update("INSERT INTO worlds (name, epoch, size ) VALUES (?{1}, ?{2}, ?{3})")
    void addWorld(String name, int epoch, int size);

    @Update("INSERT INTO states (epoch, worldId, state ) VALUES (?{1}, ?{2}, ?{3})")
    void addState(int epoch, int worldId, String state);

    @Select("SELECT id FROM worlds WHERE name = ?{1}")
    int worldExists(String name);
}