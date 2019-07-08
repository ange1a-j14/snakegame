import java.awt.Color;
/**
 * Block class maintains information about a block.
 * This class is extremely flexible to multiple games;
 * such as Tile Game, Tetris, and Tic-Tac-Toe.
 * 
 * @author  Dave Feinberg
 * @author  Richard Page
 * @author  Susan King     Added documentation
 * @author  Angela Jia  Adapted to personal snake game
 * @version May 13, 2015
 */
public class Block
{
    private MyBoundedGrid<Block> grid;
    private Location location;
    private Color color;
    private GridElement grEl;

    public enum GridElement{
        FOOD(1), SNAKEJOINT(0); 
        private int value;
        private GridElement(int value) {
            this.value = value;
        }
    }

    /**
     * Constructs a blue block.
     */
    public Block(GridElement grEl)
    {
        this.grEl = grEl;
        color = this.grEl == GridElement.FOOD ? Color.RED : Color.LIGHT_GRAY;
        grid = null;
        location = null;
    }

    /**
     * Gets the color of this block.
     * 
     * @return the color of this block
     */
    public Color getColor()
    {
        return color;
    }

    /**
     * Sets the color of this block to newColor.
     * 
     * @param newColor  the new color of this block
     */
    public void setColor(Color newColor)
    {
        color = newColor;
    }

    /**
     * Gets the grid of this block, or null if this block is not contained
     * in a grid.
     * 
     * @return the grid
     */
    public MyBoundedGrid<Block> getGrid()
    {
        return grid;
    }

    /**
     * Gets the location of this block, or null if this block is not contained
     * in a grid.
     * 
     * @return this block's location, or null if this block is not in the grid
     */
    public Location getLocation()
    {
        return location;
    }

    /**
     * Removes this block from its grid.
     *
     * @precondition  this block is contained in a grid
     */
    public void removeSelfFromGrid()
    {
        grid.remove(location);
        grid = null;
        location = null;
    }

    /**
     * Puts this block into location loc of grid gr.
     * If there is another block at loc, it is removed.
     * 
     * @precondition  (1) this block is not contained in a grid
     *                (2) loc is valid in gr
     *               
     * @param gr  the grid to place this block
     * @param loc the location to place this block
     */
    public void putSelfInGrid(MyBoundedGrid<Block> gr, Location loc)
    {
        grid = gr;
        location = loc;
        Object prev = grid.put(location, this);
        if(prev != null && prev instanceof Block)
        {
            Block block = (Block) prev;
            block.grid = null;
            block.location = null;
        }
    }

    /**
     * Moves this block to newLocation.
     * If there is another block at newLocation, it is removed.
     *
     * @precondition  (1) this block is contained in a grid
     *                (2) newLocation is valid in the grid of this block
     *                
     * @param newLocation  the location that the block is to be moved
     */
    public void moveTo(Location newLocation)
    {
        MyBoundedGrid gr = getGrid();
        gr.remove(getLocation());
        putSelfInGrid(gr, newLocation);
    }

    /**
     * Returns a string with the location and color of this block.
     * 
     * @return location and color information about the block
     */
    public String toString()
    {
        return "Block[location=" + location + ",color=" + color + "]";
    }

    public GridElement getGridElement() {
        return grEl;
    }
}