package com.webcheckers.model.moves;

import com.webcheckers.model.Board;
import com.webcheckers.model.Piece;
import com.webcheckers.model.Space;

/**
 * Represents a simple move of a piece
 */
public class SimpleMove extends Move {

    /**
     * Create move using start and end positions
     * @param from start position
     * @param to end position
     */
    public SimpleMove(Position from, Position to){
        super(from, to);
    }

    /**
     * Applies this move to the board model
     * @param board the board model
     */
    @Override
    public void apply(Board board) {
        Piece movingPiece = this.getMovingPiece(board);
        board.getSpaceAtPosition(getEnd()).setPiece(movingPiece);
        board.getSpaceAtPosition(getStart()).setPiece(null);
    }

}
