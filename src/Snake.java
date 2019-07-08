import java.util.ArrayList;

public class Snake {

   public Direction currDirection;
   public Direction prevDirection;

   private MyBoundedGrid grid;
   private ArrayList<Block> snakeJoints;
   private boolean onFood;
   private boolean updateFood;

   public enum Direction {
      LEFT(0, -1), RIGHT(0, 1), UP(-1, 0), DOWN(1, 0);
      private int rPlus, cPlus;
      private Direction(int rPlus, int cPlus) {
         this.rPlus = rPlus;
         this.cPlus = cPlus;
      }
      public int returnRPlus() {
         return rPlus;
      }
      public int returnCPlus() {
         return cPlus;
      }
   }

   public Snake(MyBoundedGrid grid) {
      this.grid = grid;
      snakeJoints = new ArrayList<Block>();
      Block b = new Block(Block.GridElement.SNAKEJOINT);
      b.putSelfInGrid(grid, new Location(7, 7));
      snakeJoints.add(b);
      currDirection = Direction.RIGHT;
      prevDirection = currDirection;
      onFood = false;
   }

   public boolean oneSuccessfulStep() {
      boolean hit = false;
      updateFood = false;
      hit = !moveJoints();
      if(hit)
         return false;
      eat();
      return true;
   }

   public boolean moveJoints() { 
      directionCalibration();
      Location currHeadLoc = snakeJoints.get(0).getLocation();
      Location aboveJointLoc = currHeadLoc;
      Location newHeadLoc = new Location(currHeadLoc.getRow() + 
                                       currDirection.returnRPlus(),
                                       currHeadLoc.getCol() + 
                                       currDirection.returnCPlus());
      if(canHeadMoveTo(newHeadLoc)) {
         snakeJoints.get(0).moveTo(newHeadLoc);
      } else {
         return false;
      }
      for(int i = 1; i < snakeJoints.size(); i++) {
         Block currJoint = snakeJoints.get(i);
         Location temp = currJoint.getLocation();
         System.out.println(snakeJoints);
         currJoint.moveTo(aboveJointLoc);
         aboveJointLoc = temp;
         }
      return true;
   }

   private void directionCalibration() {
      if(currDirection == Direction.LEFT && prevDirection == Direction.RIGHT ||
         currDirection == Direction.RIGHT && prevDirection == Direction.LEFT ||
         currDirection == Direction.UP && prevDirection == Direction.DOWN ||
         currDirection == Direction.DOWN && prevDirection == Direction.UP)
         currDirection = prevDirection;
   }

   private boolean canHeadMoveTo(Location loc) {
      if(grid.isValid(loc)) {
         Block b = (Block) grid.get(loc);
         if(b == null)
            return true;
         if(b.getGridElement() == Block.GridElement.FOOD) {
            onFood = true;
         } 
         return b.getGridElement() != Block.GridElement.SNAKEJOINT;
      }
      return false;
   }

   private void eat() {
      if(onFood) {
         updateFood = true;
         System.out.println("eat");
         Block b = new Block(Block.GridElement.SNAKEJOINT);
         Block lastJoint = snakeJoints.get(snakeJoints.size()-1);
         Location bLoc = new Location(lastJoint.getLocation().getRow()- 
                                       currDirection.returnRPlus(),
                                       lastJoint.getLocation().getCol()-
                                       currDirection.returnCPlus());
         b.putSelfInGrid(grid, bLoc);
         snakeJoints.add(b);
         onFood = false;
      }
   }

   public ArrayList<Block> getSnakeJoints() {
      return snakeJoints;
   }

   public boolean mustUpdateFood() {
      return updateFood;
   }
}