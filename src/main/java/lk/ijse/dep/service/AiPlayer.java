package lk.ijse.dep.service;

public class AiPlayer extends Player{
    private char[][] virtualBoard = new char[6][5];
    private char[][]convert(Piece[][]pieces){
        for (int i = 0; i < 6; i++) {
            for (int j = 0; j < 5; j++) {
                if(pieces[i][j] == Piece.EMPTY)
                    virtualBoard[i][j] = '-';//- represents empty
                if(pieces[i][j] == Piece.GREEN)
                    virtualBoard[i][j] = 'A';//A represents Green piece
                if(pieces[i][j] == Piece.BLUE)
                    virtualBoard[i][j] = 'H';//H represents Blue piece
            }
        }
        return virtualBoard;//returning the copy
    }
    public AiPlayer(Board board) {
        super(board);
    }
    @Override
    public void movePiece(int col){
        virtualBoard = convert(board.getPieces());//getting the pieces board and converting it the virtual board
        int bestScore = Integer.MIN_VALUE;
        int bestMove = 0;
        for (int i = 0; i < 6; i++) {
            for (int j = 0; j < 5; j++) {
                if(virtualBoard[i][j] == '-'){
                    virtualBoard[i][j] = 'A';
                    int score = minimax( 0, false);
                    //depth argument is 0 because we start from 0 and maximising is false because the next player is human
                    virtualBoard[i][j] = '-';//undo the move
                    if(score>bestScore) {
                        //find the best(max)score for AI
                        bestScore = score;
                        bestMove = i;//keep a track of bestmove(column)
                    }
                }
            }
        }
        col = bestMove;//assigning the best column to the column
        if(board.isLegalMove(col)) {
            board.updateMove(col, Piece.GREEN);
            board.getBoardUI().update(col, false);
            Winner winner = board.findWinner();
            if(winner != null){
                board.getBoardUI().notifyWinner(winner);
            }else{
                if(!board.existLegalMoves()){
                    board.getBoardUI().notifyWinner(new Winner(Piece.EMPTY));
                }
            }
        }
    }
    private int minimax(int depth, boolean maximisingPlayer) {
        String  winner = findWinner();
        if(depth == 4 || winner != null){
            if(winner == null){
                return 0;
            }
            if(winner.equals("tie"))
                return 0;
            if(winner.equals("ai"))
                return 1;
            if(winner.equals("human"))
                return -1;
        }

        if(maximisingPlayer){ //AI's turn

            int bestScore = Integer.MIN_VALUE;;

            for (int i = 0; i < 6; i++) {
                for (int j = 0; j < 5; j++) {
                    if(virtualBoard[i][j] == '-'){//find if the spot is available
                        virtualBoard[i][j] = 'A';
                        int score = minimax(depth+1, false);
                        //depth getting depth + 1 in order to increase the depth argument by 1
                        //false because the next turn is Human
                        virtualBoard[i][j] = '-';// undo  the move
                        bestScore = Math.max(bestScore,score);//Finding the min(best) score
                    }
                }
            }
            return bestScore;
        }
        else { //Human's turn

            int bestScore = Integer.MAX_VALUE;

            for (int i = 0; i < 6; i++) {//loop through the board
                for (int j = 0; j < 5; j++) {
                    if(virtualBoard[i][j] == '-'){//find if the spot is available
                        virtualBoard[i][j] = 'H';
                        int score = minimax( depth+1, true);
                        //depth getting depth + 1 in order to increase the depth argument by 1
                        //false because the next turn is AI
                        virtualBoard[i][j] = '-';//undo the move
                        bestScore = Math.min(bestScore,score);//Finding the min(best) score
                    }
                }
            }
            return bestScore;
        }
    }

    private boolean existsAvailableSpots(){
        for (int i = 0; i < 6; i++) {
            for (int j = 0; j < 5; j++) {
                if(virtualBoard[i][j] == '-')
                    return true;
            }
        }
        return false;
    }
        private String findWinner(){//Find winner for the virtual board
        ////////////// //For Horizontal win///////////////////////////////////////////////////////////////
        for (int c = 0; c < 5; c++) {
            for (int r = 0; r < 3; r++) {
                if (virtualBoard[r][c] == 'A' && virtualBoard[r +1][c] == 'A' &&
                        virtualBoard[r +2][c] == 'A' && virtualBoard[r +3][c] == 'A'){
                   return "ai";
                }
                if (virtualBoard[r][c] == 'H' && virtualBoard[r +1][c] == 'H' &&
                        virtualBoard[r +2][c] == 'H' && virtualBoard[r +3][c] == 'H'){
                    return "human";
                }
            }
        }

        //For vertical win///////////////////////////
        for (int c = 0; c < 6; c++) {
            for (int r = 0; r < 2; r++) {
                if (virtualBoard[c][r] == 'A' && virtualBoard[c][r +1] == 'A' &&
                        virtualBoard[c][r +2] == 'A' && virtualBoard[c][r +3] == 'A'){
                   return "ai";
                }
                if (virtualBoard[c][r] == 'H' && virtualBoard[c][r +1] =='H' &&
                        virtualBoard[c][r +2] =='H' && virtualBoard[c][r +3] == 'H'){
                   return "human";
                }
            }
        }
        if(!existsAvailableSpots())
            return "tie";
        return null;//return null if no winner and no tie
    }
}

