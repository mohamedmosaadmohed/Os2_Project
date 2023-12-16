package make_a_square;

public class GameContent {

    public static int gridRows = 4;
    public static int gridCols = 4;
    public static int[] rotations = new int[]{
            4,  // L
            4,  // J
            4,  // T
            1,  // O
            2,  // I
            2,  // S
            2   // Z
    };
    public static int numberOfPieces = 5;
    public static int logNumberOfPieces = 3; //log2(numberOfPieces-1)+1

    public static int numberOfRotation = 19;
    public static int logNumberOfRotation = 2; //log2(numberOfMoves-1)+1

    public static int bitsPerPiece = (logNumberOfPieces + logNumberOfRotation);
    public static int bitsPerBoard = numberOfPieces * bitsPerPiece;
    public static int numberOfBoards = (1 << bitsPerBoard);

    public static int numberOfThreads;
    public static int sectionSize;

    // Constructor to set numberOfThreads based on totalRotations
    public GameContent(int totalRotations) {
        numberOfThreads = totalRotations;
        sectionSize = numberOfBoards / numberOfThreads;
    }
}

