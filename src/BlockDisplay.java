import java.awt.*;
import java.awt.event.*;
import javax.swing.*;

/**
 * BlockDisplay is used to display the contents of the
 * game board.
 *
 * @author Anu Datar
 * @version Feb 20, 2016
 * Changed block size and added a split panel display for next block and Score
 *
 * @author Ryan Adolf
 * @version Feb 16, 2016
 * Fixed the lag issue with block rendering
 * Removed the JPanel
 * 
 * @author  Dave Feinberg
 * @author  Richard Page
 * @author  Susan King     Added documentation
 * @version May 13, 2015
 * 
 * @author Angela Jia
 * @version May 21, 2019
 * Adapted to personal snake game
 */
// Used to display the contents of a game board
public class BlockDisplay extends JComponent implements KeyListener
{
    private static final Color BACKGROUND = Color.GREEN;
    private static final Color BORDER = Color.WHITE;

    private static final int OUTLINE = 2;
    private static final int BLOCKSIZE = 40;

    private MyBoundedGrid<Block> board;
    private JFrame frame;
    private Snake snake;

    /**
     * Constructs a new display for displaying the given board.
     * 
     * @param board   the grid on which the game is to be played
     */
    public BlockDisplay(MyBoundedGrid<Block> board, Snake snake)
    {
        this.board = board;
        this.snake = snake;
        /* 
         * Schedules a job for the event-dispatching thread, which
         * creates and shows this application's GUI.
         */
        SwingUtilities.invokeLater(new Runnable()
            {
                public void run()
                {
                    createAndShowGUI();
                }
            });

        /*
         * Waits until display has been drawn.
         */
        try
        {
            while (frame == null || !frame.isVisible())
                Thread.sleep(1);
        }
        catch(InterruptedException e)
        {
            e.printStackTrace();
            System.exit(1);
        }
    }

    /**
     * Create the GUI and show it.  For thread safety,
     * this method should be invoked from the
     * event-dispatching thread.
     */
    private void createAndShowGUI()
    {
        //Create and set up the window.
        frame = new JFrame();
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.getContentPane().add(this);
        frame.addKeyListener(this);

        //Display the window.
        this.setPreferredSize(new Dimension(
                BLOCKSIZE * board.getNumCols(),
                BLOCKSIZE * board.getNumRows()));

        frame.pack();
        frame.setVisible(true);
    }

    /**
     *  Redraws the board to include the pieces and border colors
     *  Uses fillRect instead of drawing JButton as earlier to 
     *  render each block cleanly and quickly.
     *  @param g the graphics object that lets you repaint the screen 
     */
    public void paintComponent(Graphics g)
    {
        g.setColor(BACKGROUND);
        g.fillRect(0, 0, getWidth(), getHeight());
        g.setColor(BORDER);
        g.fillRect(0, 0, BLOCKSIZE * board.getNumCols() + OUTLINE, BLOCKSIZE * board.getNumRows());
        for (int row = 0; row < board.getNumRows(); row++)
            for (int col = 0; col < board.getNumCols(); col++)
            {
                Location loc = new Location(row, col);

                Block square = board.get(loc);

                if (square == null)
                    g.setColor(BACKGROUND);
                else
                    g.setColor(square.getColor());

                g.fillRect(col * BLOCKSIZE + OUTLINE/2, row * BLOCKSIZE + OUTLINE/2,
                    BLOCKSIZE - OUTLINE, BLOCKSIZE - OUTLINE);
            }

    }

    /**
     * Redraws the board to include the pieces and border colors.
     */
    public void showBlocks()
    {
        repaint();
    }

    /**
     * Sets the title of the window.
     * 
     * @param title  the information to be placed at the
     *               top of the window
     */
    public void setTitle(String title)
    {
        frame.setTitle(title);
    }

    /**
     * Creates a skeleton method to respond when a key is typed in.
     * This event occurs when a key press is followed by a key release.
     * 
     * @param e an event which indicates that a keystroke occurred 
     *          in a component (such as the grid)
     */
    public void keyTyped(KeyEvent e)
    {
    }

    /**
     * Creates a skeleton method to respond when a
     * keyboard key is released.
     * 
     * @param e an event which indicates that a keystroke occurred 
     *          in a component (such as the grid)
     */
    public void keyReleased(KeyEvent e)
    {
    }

    /**
     * Sets up the action when a key is pressed
     * on the keyboard.
     * 
     * @param e an event which indicates that a keystroke occurred 
     *          in a component (such as the grid)
     */
    public void keyPressed(KeyEvent e)
    {
        snake.prevDirection = snake.currDirection;
        int code = e.getKeyCode();
        if (code == KeyEvent.VK_LEFT)
            snake.currDirection = Snake.Direction.LEFT;
        else if (code == KeyEvent.VK_RIGHT)
            snake.currDirection = Snake.Direction.RIGHT;
        else if (code == KeyEvent.VK_DOWN)
            snake.currDirection = Snake.Direction.DOWN;
        else if (code == KeyEvent.VK_UP)
            snake.currDirection = Snake.Direction.UP;
        //else if (code == KeyEvent.VK_SPACE)
            
    }
    
}
