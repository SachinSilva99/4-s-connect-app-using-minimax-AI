package lk.ijse.dep.service;

public class BoardImpl implements  Board {
    private Piece[][] pieces = new Piece[NUM_OF_COLS][NUM_OF_ROWS];
    private BoardUI boardUI;

    public BoardImpl(BoardUI boardUI) {
        this.boardUI = boardUI;
        for (int c = 0; c < 6; c++) {
            for (int r = 0; r < 5; r++) {
                pieces[c][r] = Piece.EMPTY;
            }
        }
    }
    @Override
    public BoardUI getBoardUI() {
        return boardUI;
    }

    @Override
    public int findNextAvailableSpot(int col) {
            for (int j = 0; j < 5; j++) {
                if (pieces[col][j] == Piece.EMPTY) {
                    return j;
                }
        }
        return -1;
    }

    @Override
    public boolean isLegalMove(int col) {
            for (int j = 0; j < 5; j++) {
                if (pieces[col][j] == Piece.EMPTY) {
                    return true;
                }
            }
        return false;
    }

    @Override
    public boolean existLegalMoves() {
        for (int i = 0; i < 6; i++) {
            for (int j = 0; j < 5; j++) {
                if (pieces[i][j] == Piece.EMPTY) {
                    return true;
                }
            }
        }
        return false;
    }

    @Override
    public void updateMove(int col, Piece move) {
        int f = findNextAvailableSpot(col);
        pieces[col][f] = move;
    }

    @Override
    public void updateMove(int col, int row, Piece move) {
        pieces[col][row] = move;

    }

    @Override
    public Winner findWinner() {

       ////////////// //For Horizontal win///////////////////////////////////////////////////////////////
         for (int c = 0; c < 5; c++) {
            for (int r = 0; r < 3; r++) {
                if (pieces[r][c] == Piece.GREEN && pieces[r +1][c] == Piece.GREEN &&
                        pieces[r +2][c] == Piece.GREEN && pieces[r +3][c] == Piece.GREEN){
                    return new Winner(Piece.GREEN, r, c, r +3, c);
                }
                if (pieces[r][c] == Piece.BLUE && pieces[r +1][c] == Piece.BLUE &&
                        pieces[r +2][c] == Piece.BLUE && pieces[r +3][c] == Piece.BLUE){
                    return new Winner(Piece.BLUE, r, c, r +3, c);
                }
            }
         }

        //For vertical win///////////////////////////
            for (int c = 0; c < 6; c++) {
                for (int r = 0; r < 2; r++) {
                    if (pieces[c][r] == Piece.GREEN && pieces[c][r +1] == Piece.GREEN &&
                            pieces[c][r +2] == Piece.GREEN && pieces[c][r +3] == Piece.GREEN){
                        return new Winner(Piece.GREEN, c, r, c, r+3);
                    }
                    if (pieces[c][r] == Piece.BLUE && pieces[c][r +1] == Piece.BLUE &&
                            pieces[c][r +2] == Piece.BLUE && pieces[c][r +3] == Piece.BLUE){
                        return new Winner(Piece.BLUE, c, r, c, r+3);
                    }
                }
            }
        return null;
    }

    @Override
    public Piece[][] getPieces() {
        return pieces;
    }

}
