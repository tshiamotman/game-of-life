package za.co.wethinkcode.game_of_life.domain;

import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

public class WorldTest {

    @Test
    public void testDeathByUnderPopulation(){
        int[][] initialState = {
                {0, 0, 0},
                {0, 1, 0},
                {0, 0, 0},
        };

        int[][] nextState = {
                {0, 0, 0},
                {0, 0, 0},
                {0, 0, 0},
        };
        World world = World.define("test", 2, initialState);
        world.next();

        assertThat(world.getState()[1][1]).isEqualTo(0);
        assertThat(nextState).isEqualTo(world.getState());

    }

    @Test
    public void testStillAliveInNextStep(){
        int[][] initialState = {
                {0, 1, 0},
                {0, 1, 0},
                {0, 1, 0},
        };

        int[][] nextState = {
                {0, 0, 0},
                {1, 1, 1},
                {0, 0, 0},
        };
        World world = World.define("test", 2, initialState);
        world.next();

        assertThat(world.getState()[1][1]).isEqualTo(1);
        assertThat(nextState).isEqualTo(world.getState());
    }

    @Test
    public void testDeathByOverPopulation(){
        int[][] initialState = {
                {0, 1, 0},
                {1, 1, 1},
                {0, 1, 0},
        };

        int[][] nextState = {
                {1, 1, 1},
                {1, 0, 1},
                {1, 1, 1},
        };
        World world = World.define("test", 2, initialState);
        world.next();

        assertThat(world.getState()[1][1]).isEqualTo(0);
        assertThat(nextState).isEqualTo(world.getState());
    }

    @Test
    public void testBirthByReproduction(){
        int[][] initialState = {
                {0, 0, 1},
                {1, 0, 0},
                {0, 1, 0},
        };

        int[][] nextState = {
                {0, 0, 0},
                {0, 1, 0},
                {0, 0, 0},
        };
        World world = World.define("test", 2, initialState);
        world.next();

        assertThat(world.getState()[1][1]).isEqualTo(1);
        assertThat(nextState).isEqualTo(world.getState());
    }
}
