package com.webcheckers.model.moves;

import com.webcheckers.model.Board;
import com.webcheckers.model.Piece;

public class JumpMove extends Move {
    public JumpMove(Position from, Position to){
        super(from, to);
    }

    @Override
    public void apply(Board board) {
        Piece movingPiece = this.getMovingPiece(board);
        board.getSpaceAtPosition(getEnd()).setPiece(movingPiece);
        board.getSpaceAtPosition(jumpedPosition()).setPiece(null); //Take the piece
        board.getSpaceAtPosition(getStart()).setPiece(null);
    }

    private Position jumpedPosition(){
        Position start = getStart();
        Position end = getEnd();
        return new Position((start.getRow() + end.getRow()) / 2, (start.getCell() + end.getCell()) / 2);
    }
}
