package make_a_square;
import java.util.HashMap;
import java.util.Map;
public class Board {
    private final int[][] grid = new int[GameContent.gridRows][GameContent.gridCols];
    public final int sizeX = GameContent.gridRows;
    public final int sizeY = GameContent.gridCols;
    Map<Integer, int[][]> pieces = new HashMap<>();

    public Board(Map<Integer, int[][]> pieces) {
        this.pieces = pieces;
        for(int i = 0; i < GameContent.gridRows; i++){
            for(int j = 0; j < GameContent.gridCols; j++){
                grid[i][j] = -1;
            }
        }
    }
    // get pieces method returns 2D array  (id ,Rotation) of each piece used in a specific combination.
    private int[][] getPieces(int numericState) {
        int[][] arr2D = new int[pieces.size()][2];
        StringBuilder sB = convertDecToBinary(numericState , pieces.size());
           for (int i = 0; i < pieces.size(); i++) {
               try{
               arr2D[i][1] = Integer.parseInt(sB.substring(i * 5, i * 5 + 2), 2); //moves
               arr2D[i][0] = Integer.parseInt(sB.substring(i * 5 + 2, i * 5 + 5), 2); //id
               }catch (Exception exception)
               {
                   System.out.println(exception.toString());
               }
           }
        return arr2D;
    }
            /*
        to be a valid sequence we should
        1 - no two pieces are equal in id.
        2 - id of the move < numberOfallRotation
        3 - pieces < numberOfPieces we have
        */
    
    private boolean IsValidSeq(int[][] sequence) {
        for (int i = 0; i < sequence.length; i++) {
            boolean pieceValid = (sequence[i][0] < pieces.size());
            boolean moveIValid = sequence[i][1] < GameContent.numberOfRotation; 
            
            for (int j = i + 1; j < sequence.length; j++) {
                boolean pieceIUnique = sequence[i][0] != sequence[j][0];
                if(!pieceIUnique)
                       return false;
                 }
            
            if(!pieceValid || !moveIValid)
                return false;
        }
        return true;
    }

    private int[][] Rotation(int moveId, int[][] piece) {
        Piece piec = new Piece(piece);
        return piec.getPieceAfterRotation(moveId);
    }
    
    private int[] FirstEmptyCeilInBoard(int[][] ceils) {
        int[] indx = {-1 , -1};
        // give me the indx of the firt empty ceil
        for (int r = 0; r < ceils.length; r++) {
            boolean b = false;
            for (int c = 0; c < ceils[0].length; c++) {
                if (ceils[r][c] == -1) {
                    indx[0] = r;
                    indx[1] = c;
                    b = true;
                    break;
                }
            }
            if (b) {
                break;
            }
        }
        return indx;
    }

    private int FirstFullCeilInPiece(int[][] ceils) {
        int counter = 0;
        for (int j = 0; j < ceils[0].length; j++) {
            if (ceils[0][j] == 1) {
                break;
            }
            counter++;
        }
        return counter;
    }
    // where all other methods glued together => getPieces(),Rotation(),IsValidSeq()
    private int[][] boardState(int numericState) {
        int[][] board = new int[GameContent.gridRows][GameContent.gridCols];
        // Initialize the board with empty cells (-1).
        for(int i = 0; i < GameContent.gridRows; i++){
            for(int j = 0; j < GameContent.gridCols; j++){
                board[i][j] = -1;
            }
        }
        int[][] seq = getPieces(numericState);
        if (!IsValidSeq(seq)) {
            return null;
        }
        
        // try to put the piece in board
        for (int i = 0; i < seq.length; i++) {
            int[][] piec = pieces.get(seq[i][0]);
            int[][] piece = Rotation(seq[i][1], piec);

            int[] index = FirstEmptyCeilInBoard(board);
            //didn't find an empty cell in tbe board while we still have to put piece.
            if(index[0] == -1)
                   return null;
            
            int row = index[0], col = index[1];
            
            int counter = FirstFullCeilInPiece(piece);
            
            if (col >= counter) {
                col -= counter;
            }

            for (int r = 0; r < piece.length; r++) {
                for (int c = 0; c < piece[0].length; c++) {
                    int ro = r + row, co = c + col;
                    if (ro >= 4 || co >= 4) {
                        return null;
                    } else if (board[ro][co] > -1 && piece[r][c] == 1) {
                        return null;
                    } else if (piece[r][c] == 1) {
                        board[ro][co] = seq[i][0];
                    }
                    
                }
            }
        }
        return board;
    }
    
    public int[][] decompose(int numericState) {
        int[][] returnedBoard = boardState(numericState);
        if(returnedBoard == null)
            return null;
        if(isValidBoard(returnedBoard))
            return returnedBoard;
        return null;
    }
    
    public StringBuilder convertDecToBinary(int num,int pieces) {
        StringBuilder s = new StringBuilder();
        while (num != 0) {
            if (num % 2 == 0) {
                s.append('0');
            } else {
                s.append('1');
            }
            num /= 2;
        }
        int sz = s.length();
        for (int i = 0; i < 25 - sz; i++) {
            s.append('0'); // 25 - 5
             }
        s.reverse();
        return s;
    }

    public boolean isValidBoard(int[][] grid) {
        for (int row = 0; row < sizeX; row++) {
            for (int column = 0; column < sizeY; column++) {
                if (grid[row][column] == -1) {
                    return false;
                }
            }
        }

        return true;
    }

    public void setGrid(int[][] copyGrid) {
        for (int row = 0; row < sizeX; row++) {
            for (int column = 0; column < sizeY; column++) {
                grid[row][column] = copyGrid[row][column];
            }
        }
    }

    @Override
    public String toString() {
        StringBuilder str = new StringBuilder();
        for (int row = 0; row < sizeX; row++) {
            str.append("[");
            for (int column = 0; column < sizeY; column++) {
                str.append(grid[row][column]);
                if (column != sizeY - 1) {
                    str.append(", ");
                }
            }
            str.append("]\n");
        }
        return str.toString();
    }

    public String toString(int[][] grid) {
        StringBuilder str = new StringBuilder();
        for (int row = 0; row < sizeX; row++) {
            str.append("[");
            for (int column = 0; column < sizeY; column++) {
                str.append(grid[row][column]);
                if (column != sizeY - 1) {
                    str.append(", ");
                }
            }
            str.append("]\n");
        }
        return str.toString();
    }
}