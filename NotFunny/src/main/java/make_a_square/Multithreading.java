package make_a_square;
import java.util.Map;
import java.util.concurrent.locks.ReentrantLock;

public class Multithreading implements Runnable {
    
    static boolean foundBoard;
    private final ReentrantLock lock; 
   /* used for synchronization to ensure that certain critical
    sections of code are executed atomically by only one thread at a time.*/
   
    public static int[][] finallyBoard; 
    static public Map<Integer, int[][]> allPieces; 
    // static likely used to share the solution among threads.
    
    public Multithreading(){
        lock = new ReentrantLock();
    }
    
    @Override
    // contains the code that will be executed by each thread.
    public void run() {
        int[][] finalBoard;
        int threadID = Integer.parseInt(Thread.currentThread().getName());
        // By dividing the total range of board masks into smaller sections, multiple threads can work on different sections simultaneously.
        int from = threadID * GameContent.sectionSize;
        int to = Math.min(from + GameContent.sectionSize - 1 , GameContent.numberOfBoards - 1);
        if(threadID == GameContent.numberOfThreads - 1)
            to = GameContent.numberOfBoards - 1;
        
        //last thread must complete to the end of the states.
        for(int mask = from; mask <= to; mask++){
            Board slaveBoard = new Board(allPieces);
            finalBoard = slaveBoard.decompose(mask);
            
            if(foundBoard)
                break;

            if(finalBoard != null){
                lock.lock();
                foundBoard = true;
                finallyBoard = finalBoard;
                lock.unlock();
            }
        }
    }
}
