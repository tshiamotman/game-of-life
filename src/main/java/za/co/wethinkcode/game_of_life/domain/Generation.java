package za.co.wethinkcode.game_of_life.domain;

public class Generation {

    private boolean save(int[][] grid,
                        int row, int col)
    {
        return (grid.length > row &&
                grid[0].length > col &&
                row >= 0 && col >= 0);
    }

    public int[][] solve(int[][] grid)
    {
        int p = grid.length,
                q = grid[0].length;

        // Possible neighboring
        // indexes
        int u[] = {1, -1, 0, 1,
                -1, 0, 1, -1};
        int v[] = {0, 0, -1, -1,
                -1, 1, 1, 1};

        for (int i = 0; i < p; i++)
        {
            for (int j = 0; j < q; j++)
            {
                // IF the initial value
                // of the grid(i, j) is 1
                if (grid[i][j] > 0)
                {
                    for (int k = 0; k < 8; k++)
                    {
                        if (save(grid, i + u[k],
                                j + v[k]) &&
                                grid[i + u[k]][j + v[k]] > 0)
                        {
                            // If initial value > 0,
                            // just increment it by 1
                            grid[i][j]++;
                        }
                    }
                }

                // IF the initial value
                // of the grid(i, j) is 0
                else
                {
                    for (int k = 0; k < 8; k++)
                    {
                        if (save(grid, i + u[k],
                                j + v[k]) &&
                                grid[i + u[k]][j + v[k]] > 0)
                        {
                            // If initial value <= 0
                            // just decrement it by 1
                            grid[i][j]--;
                        }
                    }
                }
            }
        }

        // Generating new Generation.
        // Now the magnitude of the
        // grid will represent number
        // of neighbours
        for (int i = 0; i < p; i++)
        {
            for (int j = 0; j < q; j++)
            {
                // If initial value was 1.
                if (grid[i][j] > 0)
                {
                    // Since Any live cell with
                    // < 2 live neighbors dies
                    if (grid[i][j] < 3)
                        grid[i][j] = 0;

                        // Since Any live cell with
                        // 2 or 3 live neighbors live
                    else if (grid[i][j] <= 4)
                        grid[i][j] = 1;

                        // Since Any live cell with
                        // > 3 live neighbors dies
                    else if (grid[i][j] > 4)
                        grid[i][j] = 0;
                }
                else
                {
                    // Since Any dead cell with
                    // exactly 3 live neighbors
                    // becomes a live cell
                    if (grid[i][j] == -3)
                        grid[i][j] = 1;
                    else
                        grid[i][j] = 0;
                }
            }
        }
        return grid;
    }
}
