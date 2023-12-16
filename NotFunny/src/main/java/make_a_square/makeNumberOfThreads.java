package make_a_square;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;
public class makeNumberOfThreads {
    public static int[][] Square(HashMap<Integer, int[][]> sendPieces) throws InterruptedException {


        //pass pieces to the slaves area...
        Multithreading.allPieces = sendPieces;
        Multithreading.foundBoard = false;

        
        //preparing the slaves.
        Multithreading masterSlave = new Multithreading();
        ArrayList<Thread> slaves = new ArrayList<Thread>();
        for(int i = 0; i < GameContent.numberOfThreads; i++){
            Thread tmp = new Thread(masterSlave , Integer.toString(i));
            slaves.add(tmp);
        }
        
        //go slaves....
        for(int i = 0; i < GameContent.numberOfThreads; i++){
            slaves.get(i).start();
        }
        //wait slaves to finish...
        for(int i = 0; i < GameContent.numberOfThreads; i++){
            slaves.get(i).join();
        }

        if(Multithreading.foundBoard) return Multithreading.finallyBoard;
        else return null;
        
    }
}