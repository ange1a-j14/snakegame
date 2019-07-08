public class SnakeGame {

    private BlockDisplay display;
    private Snake snake;
    private MyBoundedGrid<Block> grid;
    private boolean gameOver;
    private Location foodLocation;
    private Block food; 

    public SnakeGame() {
        grid = new MyBoundedGrid<>(15, 15);
        snake = new Snake(grid);
        display = new BlockDisplay(grid, snake);
        display.setTitle("Snake Game");
        gameOver = false;
        food = new Block(Block.GridElement.FOOD);
        setNextFoodLocation();
    }

    public Location getNextFoodLocation() {
        int randRow = (int) (Math.random() * grid.getNumRows());
        int randCol = (int) (Math.random() * grid.getNumCols());
        return new Location(randRow, randCol);
    }

    public void setNextFoodLocation() {
        foodLocation = getNextFoodLocation();
        food.putSelfInGrid(grid, foodLocation);
    }

    public void play() throws InterruptedException {
        while (!gameOver) {
            Thread.sleep(500);
            gameOver = !snake.oneSuccessfulStep();
            updateFood();
            display.showBlocks();
        }
    }

    public void updateFood() {
        if(snake.mustUpdateFood()) {
            setNextFoodLocation();
        }
    }

    public static void main(String[] args) throws InterruptedException {
        SnakeGame game = new SnakeGame();
        game.play();
        System.out.println("Game over.");
    }
    public MyBoundedGrid<Block> getBoundedGrid() {
        return grid;
    }

    public BlockDisplay getBlockDisplay() {
        return display;
    }

    public Snake getSnake() {
        return snake;
    }
}